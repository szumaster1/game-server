@echo off
cd Server

.\mvnw.cmd package -DskipTests
xcopy /Y target\*-with-dependencies.jar server.jar*
java -jar server.jar
