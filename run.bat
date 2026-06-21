@echo off
javac -cp ".;lib\mysql-connector-j-9.7.0.jar" OrderTrackPro.java
java -cp ".;lib\mysql-connector-j-9.7.0.jar" OrderTrackPro
pause