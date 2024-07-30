FROM maven:3.9-eclipse-temurin-22-alpine AS build

COPY pom.xml .

RUN mvn -B dependency:go-offline

COPY src src

RUN mvn -B package -DskipTests

FROM amazoncorretto:22-alpine

# Copy jar and access token from maven build
COPY --from=build target/json-dict-*.jar /json-dict.jar

# Add curl command to pull down file
RUN apk add curl
COPY curl_data.sh /

ENV INPUT_DIR="/in/input.json"
ENV OUTPUT_DIR="/out/output.txt"

ENTRYPOINT /curl_data.sh && java -jar /json-dict.jar "--input.path=$INPUT_DIR" "--output.path=$OUTPUT_DIR"
