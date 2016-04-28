#!/bin/bash

export SPARROW_HOME=$(dirname $(dirname $(readlink -f "$0")))
cd $SPARROW_HOME

SPARROW_JAR=$SPARROW_HOME/sparrow-server/target/sparrow-server-1.0.0.jar
SPARROW_CLASSPATH=$SPARROW_HOME/sparrow-server/target/lib/*
SIGAR_LIB=$SPARROW_HOME/sparrow-server/sigar-bin
SPARROW_MAIN=org.sparrow.service.SparrowDaemon

eval $JAVA_HOME/bin/java -Xmx2G -Xms2G -Djava.library.path="$SIGAR_LIB" -cp "'$SPARROW_JAR:$SPARROW_CLASSPATH'" $SPARROW_MAIN "${@}"