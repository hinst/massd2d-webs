#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR
target="./build/install/massd2d-webs/lib/massd2d-webs-1.0-SNAPSHOT.jar"
path="/usr/lib/jvm/java-8-openjdk-armhf/bin/java -jar $(realpath $target) config-orange.properties"
echo $path

if [ "$1" = "start" ]
then
	pid=$(pidof "$path")
	if [[ $? == 0 ]]
	then
		echo probably already running $path
	else
		nohup $path 2>>error-log.txt 1>>output-log.txt &
	fi
fi

if [ "$1" == "stop" ]
then
	pid=$(pidof "$path")
	if [[ $? == 0 ]]
	then
		kill -s SIGINT $pid
		while kill -s 0 $pid
		do
			sleep 1
		done
		echo stopped
	else
		echo probably not running $path
	fi
fi