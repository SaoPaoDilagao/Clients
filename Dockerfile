FROM adoptopenjdk/openjdk8:alpine-slim
ADD target/Clients-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8095
ENTRYPOINT ["java", "-jar","/app.jar"]