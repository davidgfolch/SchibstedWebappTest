
# Schibsted web-app test

## How to run

### Run with logs (using maven-assembly-plugin)
	mvn clean package
	java -jar target/webapp-0.0.1-SNAPSHOT-jar-with-dependencies.jar

#### With colored output (perl required)
	./run.sh
That is the same as:<br/>

	mvn clean package | perl colorTail.pl
	java -jar target/webapp-0.0.1-SNAPSHOT-jar-with-dependencies.jar | perl colorTail.pl

### Quick run (using exec-maven-plugin)
	mvn exec:java

## Technical description

Server configuration via annotations, handlers can be overrided in server.properties<br/>
Using filters (com.sun.net.httpserver.Filter): see [AuthFilter](./src/main/java/com/schibsted/webapp/server/filter/AuthFilter.java) & [ParamsFilter](./src/main/java/com/schibsted/webapp/server/filter/ParamsFilter.java) implementations.<br/>
MVCHandler implementation<br/>
Using Apache org.apache.httpcomponents.httpclient to parse params and response status constants.<br/>
Using Apache log4j2 as default logger.<br/>
Using Apache log4j2 JUL bridge to log httpserver logs.<br/>
Using jtwig for templates.<br/>

## Sonar build via Travis

I've set a sonar build via Travis, you can see the [dashboard here](https://sonarcloud.io/dashboard?id=davidgfolch_SchibstedWebappTest)
