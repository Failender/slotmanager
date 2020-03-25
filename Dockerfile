FROM openjdk:8-jre-slim
COPY ./rest/build/libs/rest-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
