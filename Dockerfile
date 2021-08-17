FROM gradle:jdk16 as build-stage
COPY --chown=gradle:gradle . /app/gradle
WORKDIR /app/gradle
RUN gradle clean build -x test

FROM adoptopenjdk/openjdk16:alpine-slim as application-stage
RUN mkdir /app
WORKDIR app
COPY --from=build-stage /app/gradle/build/libs/banner-management-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/banner-management-0.0.1-SNAPSHOT.jar"]