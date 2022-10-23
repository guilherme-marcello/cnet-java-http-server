FROM openjdk:17-jdk-slim
WORKDIR /app

COPY . .

RUN javac -d . MyHttpServer.java
RUN javac MyHttpClient.java
RUN javac TestMP1.java

ENTRYPOINT ["java", "mp1.MyHttpServer"]


