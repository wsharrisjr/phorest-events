FROM  anapsix/alpine-java:8_server-jre

MAINTAINER "Dev Team" <dev@phorest.com>

RUN apk add --update curl && \
    rm -rf /var/cache/apk/*

VOLUME /tmp
VOLUME $SERVICE_DIR/logs

ENV SERVICE_DIR /phorest/srv/events
RUN mkdir -p $SERVICE_DIR

ADD build/libs/phorest-events-test-application-*.jar $SERVICE_DIR/app.jar

EXPOSE 19980

HEALTHCHECK --retries=15 CMD curl --fail http://localhost:19980/health || exit 1
ENTRYPOINT java -jar $SERVICE_DIR/app.jar
