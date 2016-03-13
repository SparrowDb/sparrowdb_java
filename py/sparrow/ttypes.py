#
# Autogenerated by Thrift Compiler (0.9.1)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException

from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class DataObject:
  """
  Attributes:
   - dbname
   - key
   - data
   - extension
   - timestamp
   - size
   - state
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'dbname', None, None, ), # 1
    (2, TType.STRING, 'key', None, None, ), # 2
    (3, TType.STRING, 'data', None, None, ), # 3
    (4, TType.STRING, 'extension', None, None, ), # 4
    (5, TType.I64, 'timestamp', None, None, ), # 5
    (6, TType.I32, 'size', None, None, ), # 6
    (7, TType.I32, 'state', None, None, ), # 7
  )

  def __init__(self, dbname=None, key=None, data=None, extension=None, timestamp=None, size=None, state=None,):
    self.dbname = dbname
    self.key = key
    self.data = data
    self.extension = extension
    self.timestamp = timestamp
    self.size = size
    self.state = state

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.dbname = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.STRING:
          self.key = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.STRING:
          self.data = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.STRING:
          self.extension = iprot.readString();
        else:
          iprot.skip(ftype)
      elif fid == 5:
        if ftype == TType.I64:
          self.timestamp = iprot.readI64();
        else:
          iprot.skip(ftype)
      elif fid == 6:
        if ftype == TType.I32:
          self.size = iprot.readI32();
        else:
          iprot.skip(ftype)
      elif fid == 7:
        if ftype == TType.I32:
          self.state = iprot.readI32();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('DataObject')
    if self.dbname is not None:
      oprot.writeFieldBegin('dbname', TType.STRING, 1)
      oprot.writeString(self.dbname)
      oprot.writeFieldEnd()
    if self.key is not None:
      oprot.writeFieldBegin('key', TType.STRING, 2)
      oprot.writeString(self.key)
      oprot.writeFieldEnd()
    if self.data is not None:
      oprot.writeFieldBegin('data', TType.STRING, 3)
      oprot.writeString(self.data)
      oprot.writeFieldEnd()
    if self.extension is not None:
      oprot.writeFieldBegin('extension', TType.STRING, 4)
      oprot.writeString(self.extension)
      oprot.writeFieldEnd()
    if self.timestamp is not None:
      oprot.writeFieldBegin('timestamp', TType.I64, 5)
      oprot.writeI64(self.timestamp)
      oprot.writeFieldEnd()
    if self.size is not None:
      oprot.writeFieldBegin('size', TType.I32, 6)
      oprot.writeI32(self.size)
      oprot.writeFieldEnd()
    if self.state is not None:
      oprot.writeFieldBegin('state', TType.I32, 7)
      oprot.writeI32(self.state)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    if self.dbname is None:
      raise TProtocol.TProtocolException(message='Required field dbname is unset!')
    if self.key is None:
      raise TProtocol.TProtocolException(message='Required field key is unset!')
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class SpqlResult:
  """
  Attributes:
   - rows
   - count
  """

  thrift_spec = (
    None, # 0
    (1, TType.LIST, 'rows', (TType.STRUCT,(DataObject, DataObject.thrift_spec)), None, ), # 1
    (2, TType.I32, 'count', None, None, ), # 2
  )

  def __init__(self, rows=None, count=None,):
    self.rows = rows
    self.count = count

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.LIST:
          self.rows = []
          (_etype3, _size0) = iprot.readListBegin()
          for _i4 in xrange(_size0):
            _elem5 = DataObject()
            _elem5.read(iprot)
            self.rows.append(_elem5)
          iprot.readListEnd()
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I32:
          self.count = iprot.readI32();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('SpqlResult')
    if self.rows is not None:
      oprot.writeFieldBegin('rows', TType.LIST, 1)
      oprot.writeListBegin(TType.STRUCT, len(self.rows))
      for iter6 in self.rows:
        iter6.write(oprot)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    if self.count is not None:
      oprot.writeFieldBegin('count', TType.I32, 2)
      oprot.writeI32(self.count)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
