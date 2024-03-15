FROM mcr.microsoft.com/playwright/java:jammy
# Add Maintainer Info
LABEL maintainer="ysargin"

# Set the current working directory inside the image
WORKDIR /app

# Copy pom.xml and src directory to the image
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn -f pom.xml clean install -DskipTests

RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"

ENTRYPOINT ["mvn", "test"]