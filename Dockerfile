FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG EXTRACTED=/src
COPY ${EXTRACTED}/dependencies/ ./
COPY ${EXTRACTED}/spring-boot-loader/ ./
COPY ${EXTRACTED}/snapshot-dependencies/ ./
COPY ${EXTRACTED}/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.launch.JarLauncher"]

