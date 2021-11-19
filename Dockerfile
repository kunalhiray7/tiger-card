FROM openjdk:11-jre-slim
COPY . .
EXPOSE 8080
ADD /build/libs/tiger-card-0.0.1-SNAPSHOT.jar tiger-card.jar
ENTRYPOINT ["java", "-jar", "tiger-card.jar"]
