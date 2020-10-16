#!/bin/sh
if [ "x$JavaOpts" = "x" ]
then
        JavaOpts="-Xmx1024m -Xms512m"
fi
if [ "x$UserName" = "x" ]
then
        echo "User name no set!"
        exit 0
fi
id $UserName >& /dev/null
if [ $? -ne 0 ]
then
        echo "$UserName not exits!"
fi
if [ "x$AppName" = "x" ]
then
    echo "App name not set!"
    exit 0
fi
if [ "x$Package" = "x" ]
then
    echo "App package not set!"
    exit 0
fi
# Set app home
SCRIPT=$0
APP_HOME=$(dirname "$SCRIPT")/..
APP_HOME=$(cd "$APP_HOME"; pwd)
LOG_HOME=$APP_HOME/logs
LOG_FILE="$AppName.log"
BASE=$1
shift
JAVA_PROPERTITES="-Dlog.home=$LOG_HOME -Dlog.file=$LOG_FILE"
if [ "x$NodeName" != "x" ]
then
        JAVA_PROPERTITES="$JAVA_PROPERTITES -Dnode.name=$NodeName"
fi
# Set classpath
if [ -r "$APP_HOME/bin/classpath.in.sh" ]
then
    . "$APP_HOME/bin/classpath.in.sh"
fi
checkingbooted()
{
    sleep 2
    pid=`cat ${APP_HOME}/pid`
    result=$(jps | grep "$pid")
    ret=$?
    if [ $ret -eq 0 ]
    then
        echo "The program[${pid}] started!!!"
    else
        echo "There is something wrong"
    fi
}
check_running() {
    pid=`cat ${APP_HOME}/pid`
    if [ "x$pid" = "x" ]
    then
        return
    fi
    result=$(jps | grep "$pid")
    ret=$?
    if [ $ret -eq 0 ]
    then
        echo "The program[${pid}] is running!!! Can not start again!!!"
        exit
    fi
}
usage() {
    echo "Usage: $BASE [-vdh] [stop]"
    echo "Start $AppName."
    echo "    -d            run in background"
    echo "    -h"
    echo "    --help        print command line options"
    echo "    -v            print version, then exit"
}
launch_service() {
    mkdir -p $LOG_HOME
    daemonized=$1
        command="java $JavaOpts -cp $APP_CLASSPATH $JAVA_PROPERTITES $Package.$AppName"
    if [ "x$daemonized" = "x" ]; then
        exec $command
        else
        exec $command > /dev/null 2>&1 &
        echo "$!" > "${APP_HOME}/pid"
        return $?
    fi
}
stop_service() {
    pid=`cat ${APP_HOME}/pid`
    result=$(jps | grep "${pid}")
    ret=$?
    if [ $ret -eq 0 ]
    then
        echo "The program[${pid}] is running!!! Now stop it!!!"
        kill -9 $pid
        kill -0 $pid > /dev/null 2>&1
        ret=$?
        while [ $ret -eq 0 ]
        do
            sleep 1
            kill -0 $pid > /dev/null 2>&1
            ret=$?
        done
        echo "The program[${pid}] stopped!!!"
    else
        echo "No program need to stop!!!"
    fi
}
ARGV=""
while [ $# -gt 0 ]
do
    case $1 in
        --help) ARGV="$ARGV -h"; shift;;
        *) ARGV="$ARGV $1" ; shift
    esac
done
args=`getopt -q vdh $ARGV`
eval set -- "$args"
while [ -n "$1" ]; do
    case $1 in
        stop)
            stop_service
            exit 0
        ;;
        -v)
            java $JavaOpts -cp $APP_CLASSPATH $Package.Version
            exit 0
        ;;
        -d)
            daemonized="yes"
            shift
        ;;
        -h)
            usage
            exit 0
        ;;
        --*)
            shift
        ;;
        *)
            echo "Error parsing argument #$1#!" >&2
            usage
            exit 1
        ;;
    esac
done
check_running
user=$(whoami)
if [ $user == "$UserName" ]
then
    cd $APP_HOME
    launch_service "$daemonized"
    checkingbooted
    exit
fi
echo "Start error. Can not use[${user}], please use[${UserName}] to start!"