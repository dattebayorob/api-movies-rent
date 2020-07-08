FROM maven:3-jdk-11 as builder
WORKDIR /app
COPY pom.xml .
COPY . .
RUN mvn clean package -B -DskipTests

FROM adoptopenjdk/openjdk11-openj9:alpine-slim
LABEL author="dattebayoRob <robson.william65@gmail.com>"
WORKDIR /app
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
EXPOSE 5432
EXPOSE 8080
