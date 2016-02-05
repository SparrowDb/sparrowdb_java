namespace java org.sparrow.thrift
namespace csharp Sparrow
namespace py sparrow
namespace php sparrow
namespace perl sparrow

struct DataObject {
	1:required string dbname,
	2:required string key,
	3:optional string crc32,
	4:optional binary data,
	5:optional string extension,
	6:optional i64 timestamp,
	7:optional i32 size,
	8:optional i32 state,
}

struct SpqlResult {
    1:optional list<DataObject> rows,
    2:optional i32 count,
}

service SparrowTransport {
	string authenticate(1:required string username, 2:required string password)

	string logout()
	
	string create_database(1:required string dbname)
	
	string drop_database(1:required string dbname)
	
	string insert_data(1:required DataObject object)
	
	string delete_data(1:required DataObject object)
	
	SpqlResult spql_query(1:required string query)
}