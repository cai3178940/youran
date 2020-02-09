#!/bin/bash
APP_HOME=..
APP_MAIN_CLASS="com.youran.generate.GenerateApp"
CLASS_PATH="$APP_HOME/conf:$APP_HOME/lib/*:$APP_HOME/resource"
java -classpath $CLASS_PATH $APP_MAIN_CLASS
