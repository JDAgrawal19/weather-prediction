FROM openjdk:8
COPY target/weather-prediction-0.0.1-SNAPSHOT.jar /weather-prediction.jar
EXPOSE 28082/tcp
ENTRYPOINT ["java", "-jar", "/weather-prediction.jar"]