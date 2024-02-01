FROM openjdk:20
RUN ls -al target
COPY SpringBootFakeAPI/target/SpringBootFakeAPI-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
