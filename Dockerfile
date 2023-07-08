FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/TVM-spring-*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar"]
