# MQTT Java Broker using Moquette library

The java code included here creates an mqtt broker using [Moquette](https://github.com/andsel/moquette).
It also uses Paho to send a test message.

Install Java
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer
java -version
javac -version
```
Install Maven
```
sudo apt-get install maven
```
Run the code:
```
mvn exec:java -Dexec.mainClass=com.example.user.mqtt.Main  
```


---------

Adopted from: [www.hascode.com](http://www.hascode.com/2016/06/playing-around-with-mqtt-and-java-with-moquette-and-eclipse-paho/)
