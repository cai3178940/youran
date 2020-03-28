@echo off
set APP_HOME=%~dp0\..
set CLASS_PATH=%APP_HOME%/lib/*;%APP_HOME%/resource;%APP_HOME%/conf;
set APP_MAIN_CLASS=com.youran.generate.GenerateApp
java -classpath %CLASS_PATH% %APP_MAIN_CLASS%
