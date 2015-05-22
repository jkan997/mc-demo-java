# Introduction

This is POC that mboucher mc-demo package can be run in purely Java enviroment in place in Node.JS
This demo uses brand new Nashorn JavaScript Engine from Java 8, so you need to Java 8 installed.
Please have in my mind that right now only one path is tested.

To run demo download JAR file:

And run it:
java -jar mc-demo-java-1.0-SNAPSHOT.jar

Video demo:

# Building
In empty folder clone two GitHub repos:
git clone https://github.com/mboucher/mc-apac-demo.git ./mc-demo
git clone https://github.com/jkan997/mc-demo-java.git ./mc-demo-java

Go into mc-demo-java and do
mvn build

You will get 50MB jar file as the output
