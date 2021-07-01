# Requirements
```
* sbt 1.5.4 
* java 11
```

# Steps to run the app
```
* sbt assembly
* cd ./build
* java -Dconfig.file=application.conf -jar -Dlog4j.configurationFile=file://log4j.xml matejovic.jar
```