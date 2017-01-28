#!/bin/bash
mvn clean package | perl colorTail.pl
#mvn exec:java
java -jar target/webapp-0.0.1-SNAPSHOT-jar-with-dependencies.jar | perl colorTail.pl
