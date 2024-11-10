# Step 1: Use Maven image for building the application
FROM maven:3.8.5-jdk-8 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies (caches dependencies for future builds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project and build the WAR file
COPY . .
RUN mvn package -DskipTests

# Step 2: Use Tomcat 8 for running the application
FROM tomcat:8.5-jdk8-openjdk-slim

# Install the required font libraries
RUN apt-get update && apt-get install -y libfreetype6 libfontconfig1 && rm -rf /var/lib/apt/lists/*

# Set JVM to headless mode
ENV JAVA_OPTS="-Djava.awt.headless=true"

# Remove the default Tomcat application and copy the built WAR file
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /app/target/TPI_Java_2019-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
