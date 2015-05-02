#To Start H2 Server using h2*.jar :
------------------------------------
Server start: 

java -cp *.jar org.h2.tools.Server
               
java -cp h2-1.3.176.jar org.h2.tools.Server


#H2 Database URLs
------------------------

Embedded
============
	jdbc:h2:~/test 			'test' in the user home directory
	jdbc:h2:/data/test 		'test' in the directory /data
	jdbc:h2:test 			in the current(!) working directory

In-Memory
============
	jdbc:h2:mem:test		multiple connections in one process
	jdbc:h2:mem:			unnamed private; one connection

Server Mode
=============
	jdbc:h2:tcp://localhost/~/test 
	jdbc:h2:tcp://localhost//data/test


#To Start the H2 Console Tool (any of below method):
---------------------------------
- Double click the jar file
- Run -> Java -jar h2*.jar 
- Windows -> h2.bat
- H2.sh

To access H2 using intractive shell
------------------------------------
java -cp h2*.jar org.h2.tools.Shell

# Maven
-----------------------------------------------------------
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.187</version>
</dependency>

---------------------------------------------------------------
# options

Starts the H2 Console server, TCP, and PG server.
When running without options, -tcp, -web, -browser and -pg are started.
Options are case sensitive. Supported options are:
[-help] or [-?]         Print the list of options
[-web]                  Start the web server with the H2 Console
[-webAllowOthers]       Allow other computers to connect - see below
[-webDaemon]            Use a daemon thread
[-webPort <port>]       The port (default: 8082)
[-webSSL]               Use encrypted (HTTPS) connections
[-browser]              Start a browser connecting to the web server
[-tcp]                  Start the TCP server
[-tcpAllowOthers]       Allow other computers to connect - see below
[-tcpDaemon]            Use a daemon thread
[-tcpPort <port>]       The port (default: 9092)
[-tcpSSL]               Use encrypted (SSL) connections
[-tcpPassword <pwd>]    The password for shutting down a TCP server
[-tcpShutdown "<url>"]  Stop the TCP server; example: tcp://localhost
[-tcpShutdownForce]     Do not wait until all connections are closed
[-pg]                   Start the PG server
[-pgAllowOthers]        Allow other computers to connect - see below
[-pgDaemon]             Use a daemon thread
[-pgPort <port>]        The port (default: 5435)
[-properties "<dir>"]   Server properties (default: ~, disable: null)
[-baseDir <dir>]        The base directory for H2 databases (all servers)
[-ifExists]             Only existing databases may be opened (all servers)
[-trace]                Print additional trace information (all servers)
[-key <from> <to>]      Allows to map a database name to another (all servers)
-----------------------------------------------------------------------

# Example Use case

In this example we will be doing roll up kind of aggregation.
For example, for key 123 can have 3 sub query. They can be called 123-1,123-2,123-3.
each sub query can have information about number of chunks like 123-1 will receive 3 chunk of size up to 50.


123-1--> 50,50,50 (total 3 chunks) ===> total sum ==> 150
123-2--> 50,50 (total 2 chunks) ===> total sum ==> 100
123-2--> 50,50,50,50 (total 4 chunks) ===> total sum ==> 200
123--> 150,100,200 ==> 450 (three sub query)


Below is the example table which will help doing aggregation.

H2 Database SQL Language Command Syntax
----------Final Table Schema -----------

DROP TABLE IF EXISTS aggregationtable;
CREATE TABLE aggregationtable(
key VARCHAR(255) PRIMARY KEY,
queryXML VARCHAR(255) default '',
noOfSubDivision INT default 0,
runningCount INT default 0,
totalCount INT default 0
);

---------------------------

select * from aggregationtable;
----------------------------------

INSERT INTO aggregationtable(key,noOfSubDivision ) VALUES('123', 3);  
INSERT INTO aggregationtable(key,noOfChunkInSubQuery) VALUES('123-1', 3); 
-------------------------------------
DELETE FROM aggregationtable ;

-----------------------------------




