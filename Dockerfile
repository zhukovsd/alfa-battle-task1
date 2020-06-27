# stage one - build an app in 11.0.4-jdk-slim using mvnw
# stage two - copy built app on top of 11.0.4-jre-slim image
FROM openjdk:11.0.4-jdk-slim as maven

WORKDIR /build

# download all necessary Maven dependencies
# this will a create cached Docker image layer until pom.xml changes
# https://medium.com/@nieldw/caching-maven-dependencies-in-a-docker-build-dca6ca7ad612

## copy mvnw first
COPY mvnw .
ADD .mvn ./.mvn

## copy pom.xml and fetch all dependencies by switching to offline mode
COPY pom.xml .
RUN ./mvnw dependency:go-offline

## copy all sources and build the project
COPY ./ .
RUN ./mvnw clean package spring-boot:repackage


FROM openjdk:11.0.4-jre-slim

# use an environment variable for convenience/correctness: avoid multiple copies of a literal;
# unlike an ARG variable, this will be available at runtime, e.g. CMD usage below
ENV name=task1

# set the working directory; this creates the directory if nonexistent
WORKDIR /srv/${name}

# copy jar from stage one image to working directory
COPY --from=maven /build/target/${name}.jar .

EXPOSE 8081

CMD java ${JAVA_OPTS} -jar ${name}.jar