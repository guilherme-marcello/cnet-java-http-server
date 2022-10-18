 FROM openjdk:8-jdk

 WORKDIR /app

 COPY . .

 RUN javac -d . MyHttpServer.java

 ENTRYPOINT ["java", "mp1.MyHttpServer"]


