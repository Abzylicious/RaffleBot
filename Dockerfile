# Build
FROM maven:3.6.3-openjdk-15-slim AS Build
ADD . /code
WORKDIR /code
RUN ["mvn", "package"]

# Run
FROM openjdk:15.0.1-slim
COPY --from=Build code/target/rafflebot-jar-with-dependencies.jar /rafflebot/rafflebot.jar
WORKDIR /rafflebot/
ENTRYPOINT ["java", "-jar", "rafflebot.jar"]
