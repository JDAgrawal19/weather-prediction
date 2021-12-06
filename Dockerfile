FROM openjdk:8
COPY target/weather-0.0.1-SNAPSHOT.jar /weather-prediction.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/weather-prediction.jar"dok