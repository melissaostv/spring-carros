FROM amazoncorretto:17
WORKDIR /app
COPY target/carros-0.0.1-SNAPSHOT.jar carros.jar
EXPOSE 8090
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "carros.jar"]
