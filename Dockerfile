# Getting base image
FROM openjdk:11.0.2-jdk
VOLUME /tmp
ADD server/target/server-0.1-SNAPSHOT-task.jar app.jar
# This comend is used by Heroku in order to run the server
CMD java $JAVA_OPTS -Dserver.port=$PORT -Dspring.datasource.url=jdbc:postgresql://ec2-54-225-89-195.compute-1.amazonaws.com:5432/davodlfjcsueja -jar target/*.jar