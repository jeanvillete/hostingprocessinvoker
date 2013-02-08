@echo off
rem ---------------------------------------------------------------------------
rem Script to invoke the Hosting Process Invoker Server on Win Platform
rem 2013 02 07
rem ---------------------------------------------------------------------------
if "%JAVA_HOME%" == "" goto jvmNotDefined
:gotExecute
set APP_DIR=%~dp0
call "%JAVA_HOME%\bin\java" "-Dhpi.base=%APP_DIR%.." "-Dlog4j.configuration=file:%APP_DIR%..\conf\log4j.properties" -jar "%APP_DIR%hpi_server.jar"
goto end
:jvmNotDefined
echo JAVA_HOME not defined
echo No valid value was defined to JAVA_HOME environment variable, please, do it before!
:end