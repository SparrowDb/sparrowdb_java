import re
import sys
import datetime
import argparse
import os.path
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
		self.commandTable['^delete data ([A-Za-z0-9]{3,20}).([A-Za-z0-9]{3,20})\s*;$'] = lambda x : self.client.delete_data(x.group(1),x.group(2))
		self.commandTable['^show databases\s*;$'] = lambda x : self.show_databases()
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

	def show_databases(self):
		c = 0
		dbs = self.client.show_databases()
		for l in dbs:
			if c == 6: 
				print '\n'
				c = 0
			print l,'\t',
			c = c + 1
		print '\n'
			
			
		  
	def loadImage(self, path):
		with open(path, "rb") as f:
			data = f.read()
			return data

	def insert_data(self, dbname, path, key):
		if os.path.exists(path):
			obj = DataObject()
			obj.dbname = dbname
			obj.key = key
			obj.data = self.loadImage(path)
			self.client.insert_data(obj)
		else:
			print "File:",path,"does not exists"
	  
	def spql_query(self, query):
		data = self.client.spql_query(query)
		result = ""
		rowStr = ""

		if data.rows is not None:
			rowStr = "Key\tSize\tTimestamp\t\t\tStatus\n"
			rowStr = rowStr + "---------------------------------------------------------"
			result = result + rowStr
			print result

			lineIndex = 0
			lineRange = 20
			
			while lineIndex < len(data.rows):
				rows = data.rows[lineIndex:lineIndex+lineRange]
				for row in rows:
					dth = datetime.datetime.fromtimestamp(int(row.timestamp)).strftime('%Y-%m-%d %H:%M:%S+0000')
					print (row.key + "\t" + str(row.size) + "\t" + dth + "\t" + State(row.state).name)
				if len(data.rows) < lineRange: break
				input = raw_input("[%d:%d]:" % (lineIndex, lineIndex+lineRange))
				if input == "stop" or input == "s": break
				lineIndex += lineRange
		else:
			print "Empty"
	  
	def sendCommand(self, cmd):
		found = False
		for key, val in self.commandTable.iteritems():
			match = re.search(key, cmd)
			if match:
				val(match)
				found = True
		
		if found is False:
			self.spql_query(cmd)
			
class ArgParser:
	args = None
	
	def parse(self):
		parser = argparse.ArgumentParser(description='Sparrow Arguments')
		parser.add_argument('-t', '--host', nargs=1, default=DEFAULT_HOST, help='Define host')
		parser.add_argument('-p', '--port', nargs=1, type=int, default=DEFAULT_PORT, help='Define port')

		self.args = parser.parse_args()
	
	def getArgs(self):
		return self.args
  
def main():
	print description + " v" + version
	argp = ArgParser()
	argp.parse()
	client = Client(argp.getArgs())
	client.connect()
	
	while active:
		shell = raw_input(">>")
		client.sendCommand(shell)
	
if __name__ == '__main__':
	try:
		main()
	except:
		sys.exit('\n\nConnection aborted.')