FROM ubuntu:20.04

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    git \
    default-jre

ADD scripts/cron-docker.sh /usr/local/bin/cron-docker.sh

RUN ["chmod", "+x", "/usr/local/bin/cron-docker.sh"]

WORKDIR /tmp

USER nobody

ENTRYPOINT ["/usr/local/bin/cron-docker.sh"]
