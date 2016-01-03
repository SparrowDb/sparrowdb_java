import re
import sys
import datetime
sys.path.append('../py')

from sparrow import SparrowTransport
from sparrow.ttypes import *

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

description = "SPQL SparrowDB"
version = "0.0.1"

DEFAULT_HOST = '127.0.0.1'
DEFAULT_PORT = 9090

client = None
active = 1

try:
  transport = TSocket.TSocket(DEFAULT_HOST, DEFAULT_PORT)
  transport = TTransport.TBufferedTransport(transport)
  protocol = TBinaryProtocol.TBinaryProtocol(transport)
  client = SparrowTransport.Client(protocol)
  transport.open()
except Thrift.TException, tx:
  print '%s' % (tx.message)
  sys.exit()

def loadImage(path):
  with open(path, "rb") as f:
    data = f.read()
  return data

def insert_data(dbname, path, key):
	obj = DataObject()
	obj.dbname = dbname
	obj.key = key
	obj.data = loadImage(path)
	return client.insert_data(obj)
  
def spql_query(query):
	data = client.spql_query(query)
	result = ""
	rowStr = ""

	if data.rows is not None:
		rowStr =  "key\tsize\ttimestamp" + "\n"
		result = result + rowStr
		
		for row in data.rows:
			dth = datetime.datetime.fromtimestamp(int(row.timestamp)).strftime('%Y-%m-%d %H:%M:%S')
			result = result + (row.key + "\t" + str(row.size) + "\t" + str(row.timestamp) + "\n")
		return result
	else:
		return "Empty"
  
commandTable = {}
commandTable['^create database ([A-Za-z0-9]{3,20})\s*;$'] =  lambda x : client.create_database(x.group(1))
commandTable['^drop database ([A-Za-z0-9]{3,20})\s*;$'] = lambda x : client.drop_database(x.group(1))
commandTable['^drop database ([A-Za-z0-9]{3,20})\s*;$'] = lambda x : client.drop_database(x.group(1))
commandTable['^insert into ([A-Za-z0-9]{3,20})\s*\(\s*(.{1,}.{4}\s*),\s*([A-Za-z0-9]{3,20}\s*)\)\s*;$'] = lambda x : insert_data(x.group(1), x.group(2), x.group(3))
  
def sendCommand(cmd):
	found = False
	for key, val in commandTable.iteritems():
		match = re.search(key, cmd)
		if match:
			print val(match)
			found = True
	
	if found is False:
		print spql_query(cmd)
		
  
def main():
  print description + " v" + version
  try:
    while active:
      shell = raw_input(">>")
      sendCommand(shell)
  except KeyboardInterrupt:
    sys.exit("Connection closed;");

if __name__ == '__main__':
  main()