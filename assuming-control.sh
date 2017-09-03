#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR
target="./build/install/massd2d-webs/lib/massd2d-webs-1.0-SNAPSHOT.jar"
path="/usr/lib/jvm/java-8-openjdk-armhf/bin/java -jar $(realpath $target) config-orange.properties"
echo $path

if [ "$1" = "start" ]
then
    echo "Starting... $(date)" >>output-log.txt
    echo "Starting... $(date)" >>error-log.txt
	nohup $path 2>>error-log.txt 1>>output-log.txt &
fi

if [ "$1" == "stop" ]
then
    pkill -f -x "$path"
fi