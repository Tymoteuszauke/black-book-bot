FROM openjdk:8-jdk-alpine

VOLUME /tmp
EXPOSE 8103

ADD target/czytamplscraper-1.0-exec.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
