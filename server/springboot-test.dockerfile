FROM docker.io/maven:3-eclipse-temurin-17 as builder
ADD pom.xml .
RUN mvn dependency:go-offline
COPY . ./

FROM builder as test
CMD [ "mvn", "clean", "test", "-Duser.timezone=Europe/Lisbon"]
