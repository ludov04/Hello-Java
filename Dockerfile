FROM resin/rpi-raspbian:jessie


# Install OpenJDK 7 & SBT
RUN apt-get update && apt-get install -y openjdk-7-jdk dropbear wget && rm -rf /var/lib/apt/lists/*

RUN wget http://scalasbt.artifactoryonline.com/scalasbt/sbt-native-packages/org/scala-sbt/sbt/0.13.1-RC5/sbt.deb

RUN dpkg -i sbt.deb

RUN sbt update -mem 128

ADD . /App/
RUN mv /App/run.sh /run.sh

CMD ["bash", "run.sh"]
