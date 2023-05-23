FROM docker.io/maven:3-eclipse-temurin-17 as builder
ADD pom.xml .
RUN mvn dependency:go-offline
COPY . ./
RUN mvn clean package -Duser.timezone="Europe/Lisbon"

FROM docker.io/eclipse-temurin:17
VOLUME /tmp
EXPOSE 8082
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
COPY --from=builder target/democracia2-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-Duser.timezone=Europe/Lisbon", "-jar", "/app/app.jar"]
