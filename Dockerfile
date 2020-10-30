FROM openjdk:latest
ADD target/theam-crm-resource-server.jar theam-crm-resource-server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "theam-crm-resource-server.jar"]
