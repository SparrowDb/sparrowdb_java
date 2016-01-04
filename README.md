Whats is SparrowJDB?
====================
SparrowJDB is an image database that works like a simple append-only object store containing data definitions representing the stored images. Sparrow has a HTTP server so images can be accessed in browser or using client to make queries.


Sparrow Object Store
====================
Sparrow consists of two files – the actual Sparrow store file containing the images data, plus an index file.

There is a corresponding data definition record followed by the image bytes for each image in the storage file. The index file provides the offset of the data definition in the storage file.


Requirements
====================
1. Java >= 1.8 (OpenJDK and Oracle JVMS have been tested)
2. Python 2.7

Getting started
====================
This short guide will walk you through getting a basic one node cluster up and running, and demonstrate some simple reads and writes.

First, download Sparrow repository:

* Extract zip file
* Go to Sparrow directory
* Run: mvn clean install

After that we start the server.  Running the startup script; it can be stopped with ctrl-C.

	$ cd bin
	$ sparrow

Running client.

	$ python client.py


Using Sparrow
====================
Creating a database:
	
	>>create database database_name;

Clear database:

	>>clear database_name;

Listing all images in database (this query will return the hashed key):

	>>select from database_name;
	Key             Size    Timestamp               Status
	---------------------------------------------------------
	-763656453      558001  2016-01-03 22:14:21     ACTIVE
	-1277804125     61148   2016-01-03 22:14:40     ACTIVE
	236401485       558001  2016-01-03 22:14:31     ACTIVE
	-307797267      558001  2016-01-03 22:16:06     REMOVED
	-819873158      95889   2016-01-03 22:14:47     ACTIVE
    
You also can use query like:
	
	>>select from database_name where key = image_key;

	
Sending an image to database:

	>>insert into database_name (image_path_with_extension, image_key);

Deleting image:

	>>delete database_name.image_key;


Accessing image from browser:
	
	http://localhost:8081/database_name/image_key

License
====================
This software is under MIT license.
