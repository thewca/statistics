FROM ubuntu:20.04

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    git \
    default-jre \
    curl \
    python3 \
    python3-pip \
    wget \
    sudo \
    python3-virtualenv

ADD scripts/cron-docker.sh /usr/local/bin/cron-docker.sh

RUN ["chmod", "+x", "/usr/local/bin/cron-docker.sh"]

WORKDIR /tmp

USER nobody

RUN mkdir /tmp/gradle
ENV GRADLE_USER_HOME=/tmp/gradle

ENTRYPOINT ["/usr/local/bin/cron-docker.sh"]
