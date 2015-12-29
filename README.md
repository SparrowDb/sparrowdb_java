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


Using Sparrow
====================
Creating a database:
	
	>>create database database_name;

Clear database:

	>>clear database_name;

Listing all images in database (this query will return the hashed key):

	>>select from database_name;
    key             size    timestamp
    -1596528915     558001  2015-12-27 17:26:23
    -763656453      558001  2015-12-27 17:26:26
    -819873158      558001  2015-12-27 17:26:53
    -307797267      558001  2015-12-27 17:26:57
    236401485       558001  2015-12-27 17:26:29
    -1277804125     558001  2015-12-27 17:26:41
	
Sending an image to database:

	>>insert into database_name (image_path_with_extension, image_key);

Deleting image:

	>>delete database_name.image_key;


Accessing image from browser:
	
	http://localhost:8081/database_name/image_key

License
====================
This software is under MIT license.
