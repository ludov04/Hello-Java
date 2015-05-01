FROM resin/rpi-raspbian:jessie


RUN echo "deb http://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list

# Install OpenJDK 7 & SBT
RUN apt-get update && apt-get install -y --force-yes openjdk-7-jdk dropbear sbt python python-dev libraspberrypi-bin python-pip && rm -rf /var/lib/apt/lists/*

#RUN sbt update -mem 128

RUN pip install picamera

ADD . /App/
RUN mv /App/run.sh /run.sh

CMD modprobe bcm2835-v4l2
CMD dropbear -E -F
CMD ["bash", "run.sh"]
