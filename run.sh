#!/bin/bash

cd /App

export PASSWD=${PASSWD:=root}
#Set the root password
echo "root:$PASSWD" | chpasswd

python /App/picam/serv.py