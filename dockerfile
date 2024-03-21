FROM openjdk:17
EXPOSE 8097
ADD target/chatbotOpenAi.jar chatbotOpenAi.jar
ENTRYPOINT ["java","-jar","/chatbotOpenAi.jar"]