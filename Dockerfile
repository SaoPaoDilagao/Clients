FROM adoptopenjdk/openjdk8:alpine-slim
ADD target/clients-0.0.1-SNAPSHOT.jar app.jar
EXPOSE [8095, 27018]
ENTRYPOINT ["java", "-jar","/app.jar"]