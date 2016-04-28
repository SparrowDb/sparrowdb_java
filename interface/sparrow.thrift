namespace java org.sparrow.protocol
namespace csharp Sparrow
namespace py sparrow
namespace php sparrow
namespace perl sparrow

struct DataObject {
	1:required string dbname,
	2:required string key,
	3:optional binary data,
	4:optional string extension,
	5:optional i64 timestamp,
	6:optional i32 size,
	7:optional i32 state,
}

struct SpqlResult {
    1:optional list<DataObject> rows,
    2:optional i32 count,
}

service SparrowTransport {
	string authenticate(1:required string username, 2:required string password)

	string logout()
	
	list<string> show_databases()
	
	string create_database(1:required string dbname, 2:map<string,string> params)
	
	string drop_database(1:required string dbname)
	
	string insert_data(1:required DataObject object, 2:list<string> params)
	
	string delete_data(1:required string dbname, 2:string keyName, 3:string keyValue)
	
	SpqlResult spql_query(1:required string dbname, 2:string keyName, 3:string keyValue)
}