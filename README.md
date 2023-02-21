## Overview
- Objective: implementation of highly scalable HTTP-server and its client
- Language: Java - JDK 17 (LTS)
- Updated at 25 Oct 2022

## Authors
- Guilherme Marcelo - [@guilherme-marcello](https://github.com/guilherme-marcello)
- Rafael Correia - [@Rafacorreia0611](https://github.com/Rafacorreia0611)
- João Ferreira - [@JFerreira03](https://github.com/JFerreira03)


***** Instructions for compiling, running and testing ***** 

## Deployment with docker and docker-compose
In order to make it easier to make it easier for us to control 
project dependencies and code sharing among the group members, 
we decided to have a "write once, run everywhere" approach. 
Having, therefore, a docker image to compile and run the server.

[STEP 1 - Execute deployment script]
To build the image and start-up the service with docker-compose, you must run it on your machine:
(make sure you have docker and docker-compose installed on your machine, preferably the newer versions)
[current working directory -> inside project's folder]
```bash
chmod +x ./docker/deploy_server.sh && ./docker/deploy_server.sh
```

## Deployment without Docker

[STEP 0 - Extra notes]
In case you don't want to run in docker container, you can compile
the project normally and run it from the terminal. 
(Note that this will require a JDK 17 version)

[STEP 1 - Compile each class and its dependencies]
- Compile MyHttpServer.java and its dependencies
```bash
    javac -d . MyHttpServer.java
```
- Compile MyHttpClient.java and its dependencies
```bash
    javac MyHttpClient.java
```

[STEP 2 - Start HTTP server]
Open a terminal and launch the server class:
(the TCP port number where the server will be accepting 
connections is the value of SERVER_PORT environment variable - that is 8081 by default).   
```bash
    java mp1.MyHttpServer
```
