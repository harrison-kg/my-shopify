FROM openjdk:8-jdk-alpine
ADD target/my-shopify-app.jar my-shopify-app.jar
EXPOSE 8085
ENTRYPOINT  ["sh","-c","java -jar my-shopify-app.jar"]