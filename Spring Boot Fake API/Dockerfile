FROM openjdk:20
WORKDIR target/SpringBootFakeAPI-0.0.1-SNAPSHOT.jar /app.jar
COPY . .
EXPOSE 4581
ENTRYPOINT ["java","-jar", "/app.jar"]
