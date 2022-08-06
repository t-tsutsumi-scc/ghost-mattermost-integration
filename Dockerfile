FROM gcr.io/distroless/java17-debian11

EXPOSE 8080
WORKDIR /app
CMD ["app.jar"]

COPY build/libs/ghost-mattermost-integration.jar app.jar
