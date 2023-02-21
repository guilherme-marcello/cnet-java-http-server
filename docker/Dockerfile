FROM openjdk:17-jdk-slim
WORKDIR /app

COPY . .

RUN javac -d . MyHttpServer.java
RUN javac MyHttpClient.java

ENTRYPOINT ["java", "mp1.MyHttpServer"]


