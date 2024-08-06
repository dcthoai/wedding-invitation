# Stage 1: Build stage
FROM maven:3.8.3-openjdk-11 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package

# Stage 2: Package stage
FROM tomcat:9.0.65-jdk11-openjdk-slim
WORKDIR /usr/local/tomcat/webapps/

RUN rm -rf ./*
COPY --from=build /app/target/*.war ./ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
