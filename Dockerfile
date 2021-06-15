FROM adoptopenjdk/openjdk11:alpine-jre as builder

WORKDIR application

COPY target/online-store.jar online-store.jar
RUN java -Djarmode=layertools -jar online-store.jar extract

FROM adoptopenjdk/openjdk11:alpine-jre

ENV JAVA_TOOL_OPTIONS="-XX:InitialRAMPercentage=20.0 -XX:MaxRAMPercentage=70.0"

WORKDIR /service

COPY --from=builder application/dependencies/ /service
COPY --from=builder application/snapshot-dependencies/ /service
COPY --from=builder application/spring-boot-loader/ /service
COPY --from=builder application/application/ /service

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
