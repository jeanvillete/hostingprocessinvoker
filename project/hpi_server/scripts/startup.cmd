@echo off
rem ---------------------------------------------------------------------------
rem Script to invoke the Hosting Process Invoker Server on Win Platform
rem 2013 02 07
rem ---------------------------------------------------------------------------
if "%JAVA_HOME%" == "" goto jvmNotDefined
:gotExecute
set APP_DIR=%~dp0
set PARAMETERS=%* -current_directory "%APP_DIR% " -Dhpi.base="%APP_DIR%" -Djava.util.logging.config.file="%APP_DIR%conf\logging.properties" -Dlog4j.configuration="%APP_DIR%conf\log4j.properties"
call "%JAVA_HOME%\bin\java" -jar "%APP_DIR%hpi_server.jar" %PARAMETERS%
goto end
:jvmNotDefined
echo JAVA_HOME not defined
echo No valid value was defined to JAVA_HOME environment variable, please, do it before!
:end