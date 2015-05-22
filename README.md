# Introduction
This package is proof of concept of runnint [Marcel Boucher Marketing Cloud Demo Tools](/mboucher/mc-apac-demo) in purely Java enviroment in place in Node.JS
This demo uses brand new Nashorn JavaScript Engine from Java 8, so you need to Java 8 installed.
Please have in my mind that right now only one path is tested.

Here you can download JAR file and view  screencast

http://bit.ly/1HmSMc3

And run it:
```bash
java -jar mc-demo-java-1.0-SNAPSHOT.jar
```

Video demo:

# Building
In empty folder clone two GitHub repos:
```bash
git clone https://github.com/mboucher/mc-apac-demo.git ./mc-demo
git clone https://github.com/jkan997/mc-demo-java.git ./mc-demo-java
cd ./mc-demo-java
mvn build
```

You will get 50MB jar file as the output
