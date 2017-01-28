
# Schibsted web-app test

## How to run

### Run with logs (using maven-assembly-plugin)
mvn clean package<br/>
java -jar target/webapp-0.0.1-SNAPSHOT-jar-with-dependencies.jar<br/>
<br/>
** with colored output (perl required) **<br/>
mvn clean package | perl colorTail.pl<br/>
java -jar target/webapp-0.0.1-SNAPSHOT-jar-with-dependencies.jar | perl colorTail.pl

### Quick run (using exec-maven-plugin)
mvn exec:java


