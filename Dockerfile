# Родительский образ контейнера с maven внутри
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests=true --file pom.xml

# Родительский образ контейнера с java внутри
FROM eclipse-temurin AS run
# Копирование jar-файла в контейнер
COPY --from=build /app/target/*.jar /app.jar
# Открытие порта 8080
EXPOSE 8080
# Команда, которая будет запущена при старте контейнера java -jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]