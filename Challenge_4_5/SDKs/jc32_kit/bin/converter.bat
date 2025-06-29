
@echo off

@REM Copyright (c) 1998, 2025, Oracle and/or its affiliates. All rights reserved.

setlocal
set JC_HOME_TOOLS=%~dp0\..
rem Print warning if no JAVA_HOME set
if not defined JAVA_HOME goto nojavahome
set JC_LIBS=%JC_HOME_TOOLS%


rem set classpath to all jars

set JC_CLASSPATH=%JC_LIBS%\lib\commons-cli-1.9.0.jar;%JC_CLASSPATH%
                
set JC_CLASSPATH=%JC_LIBS%\lib\jctasks_tools.jar;%JC_CLASSPATH%
                
set JC_CLASSPATH=%JC_LIBS%\lib\json.jar;%JC_CLASSPATH%
                
set JC_CLASSPATH=%JC_LIBS%\lib\tools.jar;%JC_CLASSPATH%
                

rem execute converter's Main class
"%JAVA_HOME%\bin\java" "-Djc.home=%JC_HOME_TOOLS%" -classpath "%JC_CLASSPATH%" com.sun.javacard.converter.Main %*
goto done
:nojavahome
echo JAVA_HOME is not set. Please set it to point to JDK 17

:done
endlocal

rem Send the error code to the command interpreter
cmd /c Exit /B %errorlevel%
        