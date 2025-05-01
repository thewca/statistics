FROM ubuntu:24.04

RUN apt-get update
RUN apt-get install -y git
RUN apt-get install -y curl 
RUN apt-get install -y python3 
RUN apt-get install -y python3-pip 
RUN apt-get install -y wget 
RUN apt-get install -y python3-virtualenv 
RUN apt-get install -y unzip 
RUN apt-get install -y mysql-client
RUN apt-get install -y openjdk-21-jdk

ADD scripts/cron-docker.sh /usr/local/bin/cron-docker.sh

RUN ["chmod", "+x", "/usr/local/bin/cron-docker.sh"]

ENTRYPOINT ["/usr/local/bin/cron-docker.sh"]
