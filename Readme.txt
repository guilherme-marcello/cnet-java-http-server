## Overview
- LEI 2022-23 2nd year 1 semester
- unidade curricular: Redes de Computadores
- objective: implementation of highly scalable HTTP-server and its client
- language: Java - JDK 17 (LTS)
- updated at 25 Oct 2022

## Authors

- Guilherme Marcelo \<fc58173@alunos.fc.ul.pt\> - [@guilherme-marcello](https://github.com/guilherme-marcello)
- Rafael Correia \<fc58256@alunos.fc.ul.pt\> - [@Rafacorreia0611](https://github.com/Rafacorreia0611)
- João Ferreira \<fc58191@alunos.fc.ul.pt\> - [@JFerreira03](https://github.com/JFerreira03)


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

[STEP 2 - Go inside the server's container in interactive mode and start interactive menu]
To run the test command, you can enter the container with 
interactive mode in another console as follows:
```bash
    sudo docker exec -it http-server bash
```
and run the test program as follows:
(inside container bash session)
```bash
    java TestMP1 http-server 8081
```

[STEP 3 - Execute test scenarios]
Use the interactive menu of the test class to generate the client requests and server responses, 
and after each request/response exchange analyze the respective logs in the console (stdout) to 
verify that the behaviors conform to those expected.   


[STEP 4 - 1.f feature]
To test the 1.f functionality of the server. We suggest launching different instances 
of the TestMp1 class until reaching the limit specified above and try to launch and use 
another instance of the class to interact with the server. A server should react with an error to requests 
from the last launched instance until all the others are active.

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
- Compile TestMP1.java and its dependencies
```bash
    javac TestMP1.java
```

[STEP 2 - Start HTTP server]
Open a terminal and launch the server class:
(the TCP port number where the server will be accepting 
connections is the value of SERVER_PORT environment variable - that is 8081 by default).   
```bash
    java mp1.MyHttpServer
```

[STEP 3 - Start tests]
```bash
    java TestMP1 localhost 8081
```

[STEP 4 - Execute test scenarios]
Use the interactive menu of the test class to generate the client requests and server responses, 
and after each request/response exchange analyze the respective logs in the console (stdout) to 
verify that the behaviors conform to those expected.   


[STEP 5 - 1.f feature]
To test the 1.f functionality of the server. We suggest launching different instances 
of the TestMp1 class until reaching the limit specified above and try to launch and use 
another instance of the class to interact with the server. A server should react with an error to requests 
from the last launched instance until all the others are active.
