FROM maven:3.6-jdk-11 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-slim
COPY --from=builder /app/target/*.jar /app/application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]


# FROM python:3.9-slim
# WORKDIR /app
# COPY . /app
# RUN pip install --no-cache-dir -r requirements.txt
# ENV AWS_ACCESS_KEY_ID=""
# ENV AWS_SECRET_ACCESS_KEY=""
# ENV S3Bucketname=""
# CMD ["python", "app.py"]