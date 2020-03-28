#!/bin/bash
PRGDIR=`dirname "$PRG"`
APP_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`
APP_MAIN_CLASS="com.youran.generate.GenerateApp"
CLASS_PATH="$APP_HOME/conf:$APP_HOME/lib/*:$APP_HOME/resource"
java -classpath $CLASS_PATH $APP_MAIN_CLASS
