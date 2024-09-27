FROM openjdk:20-jdk-slim

WORKDIR /app

COPY target/blog-app-apis-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENTRYPOINT [ "java","-jar","app.jar" ]