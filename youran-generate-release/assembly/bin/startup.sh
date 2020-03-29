#!/bin/bash
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
APP_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`
APP_MAIN_CLASS="com.youran.generate.GenerateApp"
CLASS_PATH="$APP_HOME/conf:$APP_HOME/lib/*:$APP_HOME/resource"
java -classpath $CLASS_PATH $APP_MAIN_CLASS
