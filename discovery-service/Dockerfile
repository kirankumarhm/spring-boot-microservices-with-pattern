FROM maven:3.9.4-eclipse-temurin-20 as builder
RUN addgroup demogroup; adduser  --ingroup demogroup --disabled-password demo
USER demo
WORKDIR /opt/app
#COPY .mvn/ ./.mvn
COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN mvn clean install

FROM eclipse-temurin:20-jre-jammy
WORKDIR /opt/app
EXPOSE 8761
COPY --from=builder /opt/app/target/discovery-service-0.0.1-SNAPSHOT.jar /opt/app/discovery-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/opt/app/discovery-service-0.0.1-SNAPSHOT.jar" ]