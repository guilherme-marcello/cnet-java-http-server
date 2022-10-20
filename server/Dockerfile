 FROM openjdk:17-jdk-slim

 WORKDIR /app

 COPY . .

 RUN javac -d . MyHttpServer.java

 ENTRYPOINT ["java", "mp1.MyHttpServer"]


