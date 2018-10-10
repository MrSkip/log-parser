(1) Java program that can be run from command line
	

Please use `log-parser-1.0-jar-with-dependencies.jar` by path `target/log-parser-1.0-jar-with-dependencies.jar`.
When the command line argument `accesslog` is specified then two operations will be executed,
the first one is reading from the file and inserting into the table and second one is querying DB based on
other arguments as shown below:

    java -cp "log-parser-1.0-jar-with-dependencies.jar" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
     
When `accesslog` param is not present then the file parsing is not executing

    java -cp "log-parser-1.0-jar-with-dependencies.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100

(2) Source Code for the Java program

- at this repository :)

(3) MySQL schema used for the log data
    
- by path `resources/schema.sql`

(4) SQL queries for SQL test

- by path `resources/queries.sql`