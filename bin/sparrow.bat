@echo off

if NOT DEFINED SPARROW_HOME set SPARROW_HOME=%CD%

IF /i "%1" == "build" GOTO build
IF /i "%1" == "start" GOTO start
IF /i "%1" == "" GOTO start
IF /i "%1" == "datatool" GOTO datatool

:build
mvn clean install
goto end

:start
set SPARROW_MAIN=org.sparrow.service.SparrowDaemon
set JAVA_OPTS=-Xmx2G^
 -Xms2G^
 -XX:+UseParNewGC^
 -XX:+UseConcMarkSweepGC^
 -XX:+HeapDumpOnOutOfMemoryError^
 -XX:+CMSParallelRemarkEnabled^
 -XX:SurvivorRatio=8
 
if EXIST target/sparrow-1.0-SNAPSHOT.jar (
	goto exec
) else (
	echo [ERRO] Sparrow main file does not exists, please try to run "sparrow build"
)

goto end

:datatool
set SPARROW_MAIN=org.sparrow.tools.DataExtract
goto exec
goto end

:exec
set SPARROW_CLASSPATH=target/lib/*
"%JAVA_HOME%\bin\java" %JAVA_OPTS% -cp "target/sparrow-1.0-SNAPSHOT.jar;%SPARROW_CLASSPATH%" %SPARROW_MAIN%
goto end

:end