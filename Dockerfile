FROM openjdk:21
WORKDIR /app
COPY ./target/UrlShortner-0.0.1-SNAPSHOT.jar /app/appBackend.jar
EXPOSE 8012
ENTRYPOINT ["java","-jar","appBackend.jar"]