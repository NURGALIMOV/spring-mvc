FROM maven:3-openjdk-11-slim AS build
WORKDIR /app
COPY . .
RUN mvn -B package

FROM tomcat:9-jdk11-openjdk-slim
COPY --from=build /app/target/servlets-1.0-jar-with-dependencies.jar $CATALINA_HOME/webapps/ROOT.war
EXPOSE 8080