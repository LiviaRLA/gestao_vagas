FROM ubuntu:latest AS build

RUN apt-get update && apt-get install openjdk-21-jdk -y 
COPY . .

RUN apt-get install maven -y && mvn clean install

FROM openjdk:21-jdk-slim
EXPOSE 8080

CMD --from=build /target/gestao_vagas-0.0.1.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]

