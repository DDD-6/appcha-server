FROM openjdk:16-alpine3.13

WORKDIR /app
COPY run-java.sh /app/run-java.sh
RUN chmod +x run-java.sh
ENTRYPOINT ["./run-java.sh"]

ENV TZ Asia/Seoul
ENV JAVA_MAIN_CLASS com.editor.appcha.AppchaServerApplicationKt

ADD lib /app
ADD project-lib/*.jar /app
ADD *.jar /app

