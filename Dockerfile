FROM openjdk:17-jdk-alpine
LABEL MAINTAINER="juicy"
ARG JAR_FILE=./target/juicy-recipes-0.0.1-SNAPSHOT.jar
COPY . /app
COPY ${JAR_FILE} /app/juicy-recipes/target/juicy-recipes-0.0.1-SNAPSHOT.jar
EXPOSE 8080
USER 1001
CMD ["java", "-jar","-Dmaven.tes0t.skip=true", "./app/juicy-recipes/target/juicy-recipes-0.0.1-SNAPSHOT.jar"]