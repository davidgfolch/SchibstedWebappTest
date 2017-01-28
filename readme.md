
# Schibsted web-app test

## How to run

### Run with logs (using maven-assembly-plugin)
	mvn clean package
	java -jar target/webapp-0.0.1-SNAPSHOT-jar-with-dependencies.jar

**With colored output (perl required)**<br/>
	./run.sh
That is the same as:<br/>
	mvn clean package | perl colorTail.pl
	java -jar target/webapp-0.0.1-SNAPSHOT-jar-with-dependencies.jar | perl colorTail.pl

### Quick run (using exec-maven-plugin)
	mvn exec:java

## Technical description

Using jtwig for templates.<br/>
Using log4j2 as default logger.<br/>
Using log4j2 JUL bridge to log httpserver logs.<br/>
