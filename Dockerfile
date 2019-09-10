# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8081 available to the world outside this container
EXPOSE 8081

RUN pwd

# The application's jar file
ARG JAR_FILE=./target/conichi-1.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} conichi.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/conichi.jar"]
