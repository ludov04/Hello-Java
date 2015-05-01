FROM resin/rpi-raspbian:jessie


RUN echo "deb http://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list

# Install OpenJDK 7 & SBT
RUN apt-get update && apt-get install -y openjdk-7-jdk dropbear sbt && rm -rf /var/lib/apt/lists/*

RUN sbt update

ADD . /App/
RUN mv /App/run.sh /run.sh

CMD ["bash", "run.sh"]
