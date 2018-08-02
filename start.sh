#!/bin/sh
ulimit -s 256
export JAVA_HOME=/home/jdk1.8.0/jdk1.8.0_111
APP_HOME=${PWD}
programdir="."

num=$#
temp=$CLASSPATH:$APP_HOME/lib:./
#setting libs path
libs=lib/*
append(){
    temp=$temp":"$1
}

basepath=${PWD}/*
for file in $basepath; do
	#echo ${file##*.}
	if [ "${file##*.}" = "jar" ];then
       append $file
    fi
done

for file in $libs;    do
    append $file
done
export CLASSPATH=$temp
#export LC_ALL="en_US.UTF-8"
#export CLASSPATH=$CLASSPATH:$APP_HOME/lib
#echo $CLASSPATH
echo "Running [Trading] Service ..."
JAVA_OPTS="-server -Xms256m -Xmx256m -Xss256K -XX:-UseGCOverheadLimit"
nohup java $JAVA_OPTS -classpath $CLASSPATH com.zw.service.boot.ServiceBoot > nohup.out 2>&1 &
tail -f nohup.out