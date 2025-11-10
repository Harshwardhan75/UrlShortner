FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY ./target/UrlShortner-0.0.1-SNAPSHOT.jar /app/appBackend.jar
EXPOSE 8012
ENTRYPOINT ["java","-jar","appBackend.jar","--spring.profiles.active=docker"]