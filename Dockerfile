# Этап maven сборки
FROM maven:latest AS build
WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Этап разделения на слои
# https://habr.com/ru/articles/522122/
FROM bellsoft/liberica-openjre-alpine:21 AS layers

WORKDIR /application

COPY --from=build /build/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Этап подготовки окружения
FROM bellsoft/liberica-openjre-alpine:21
VOLUME /tmp
RUN adduser -S spring-user
USER spring-user
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./

# Копируем папку миграций в образ
COPY db ./db

# Указываем специальный JarLauncher, который знает кого запускать
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]