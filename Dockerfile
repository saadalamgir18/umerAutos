FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8083

CMD ["java", "-jar", "app.jar"]