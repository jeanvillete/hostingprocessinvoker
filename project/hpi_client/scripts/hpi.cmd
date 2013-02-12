@echo off
rem ---------------------------------------------------------------------------
rem Script to invoke the Hosting Process Invoker Client on Win Platform
rem 2013 02 12
rem ---------------------------------------------------------------------------
if "%JAVA_HOME%" == "" goto jvmNotDefined
:gotoExecute
set APP_DIR=%~dp0
call "%JAVA_HOME%\bin\java" -jar "%APP_DIR%hpi_client.jar" %*
goto end
:jvmNotDefined
echo JAVA_HOME not defined
echo No valid value was defined to JAVA_HOME environment variable, please, do it before!
:end
