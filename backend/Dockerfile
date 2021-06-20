FROM openjdk:16-alpine3.13 as build                         

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src
RUN ./mvnw package -DskipTests

FROM openjdk:16-alpine3.13 

COPY --from=build target/.security-0.0.1-SNAPSHOT.jar .security-0.0.1-SNAPSHOT.jar   

EXPOSE 8080
ENTRYPOINT ["java", "-jar", ".security-0.0.1-SNAPSHOT.jar"]