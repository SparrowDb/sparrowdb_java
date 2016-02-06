import re
import sys
import datetime
import argparse
sys.path.append('../py')

from sparrow import SparrowTransport
from sparrow.ttypes import *
from enum import Enum
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

description = "SPQL SparrowDB"
version = "0.0.1"

DEFAULT_HOST = ['127.0.0.1']
DEFAULT_PORT = 9090

client = None
active = 1

class State(Enum):
	ACTIVE = 0
	REMOVED = 1
	DESTROY = 2
		
class Client:
	client = None
	args = None
	commandTable = {}
	
	def __init__ (self, args):
		self.args = args
		self.commandTable['^create database ([A-Za-z0-9]{3,20})\s*;$'] =  lambda x : self.client.create_database(x.group(1))
		self.commandTable['^drop database ([A-Za-z0-9]{3,20})\s*;$'] = lambda x : self.client.drop_database(x.group(1))
		self.commandTable['^insert into ([A-Za-z0-9]{3,20})\s*\(\s*(.{1,}.{4}\s*),\s*([A-Za-z0-9]{3,20}\s*)\)\s*;$'] = lambda x : self.insert_data(x.group(1), x.group(2), x.group(3))
		
	def connect(self):
		try:
		  transport = TSocket.TSocket(self.args.host[0], self.args.port)
		  transport = TTransport.TBufferedTransport(transport)
		  protocol = TBinaryProtocol.TBinaryProtocol(transport)
		  self.client = SparrowTransport.Client(protocol)
		  transport.open()
		  print "Connected to", self.args.host[0], "on port", self.args.port
		except Thrift.TException, tx:
		  print '%s' % (tx.message)
		  sys.exit()

	def loadImage(self, path):
	  with open(path, "rb") as f:
		data = f.read()
	  return data

	def insert_data(self, dbname, path, key):
		counter = 0
		name = "key"
		vec = ["aaa.jpg", "original1.png", "original2.jpg"]
		import random
		for x in range(50000):
			img = vec[random.randint(0,2)]
			obj = DataObject()
			obj.dbname = dbname
			obj.key = name+str(x)
			obj.data = self.loadImage(img)
			self.client.insert_data(obj)
		return "aa"
	  
	def spql_query(self, query):
		data = self.client.spql_query(query)
		result = ""
		rowStr = ""

		if data.rows is not None:
			rowStr = "Key\tSize\tTimestamp\t\tStatus\n"
			rowStr = rowStr + "---------------------------------------------------------\n"
			result = result + rowStr
			
			for row in data.rows:
				dth = datetime.datetime.fromtimestamp(int(row.timestamp)).strftime('%Y-%m-%d %H:%M:%S')
				result = result + (row.key + "\t" + str(row.size) + "\t" + dth + "\t" + State(row.state).name + "\n")
			return result
		else:
			return "Empty"
	  
	def sendCommand(self, cmd):
		found = False
		for key, val in self.commandTable.iteritems():
			match = re.search(key, cmd)
			if match:
				print val(match)
				found = True
		
		if found is False:
			print self.spql_query(cmd)
			
class ArgParser:
	args = None
	
	def parse(self):
		parser = argparse.ArgumentParser(description='Sparrow Arguments')
		parser.add_argument('-t', '--host', nargs=1, default=DEFAULT_HOST, help='Define host')
		parser.add_argument('-p', '--port', nargs=1, type=int, default=DEFAULT_PORT, help='Define port')

		self.args = parser.parse_args()
		#print args.accumulate(args.integers)
		#print parser.print_help()
		#print args
	
	def getArgs(self):
		return self.args
  
def main():
	argp = ArgParser()
	argp.parse()
	client = Client(argp.getArgs())
	client.connect()
	print description + " v" + version
	try:
		while active:
			shell = raw_input(">>")
			client.sendCommand(shell)
	except KeyboardInterrupt:
		sys.exit("Connection closed;");

if __name__ == '__main__':
  main()