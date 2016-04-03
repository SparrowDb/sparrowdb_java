#!/bin/bash

export SPARROW_HOME=$(dirname $(dirname $(readlink -f "$0")))
cd $SPARROW_HOME

SPARROW_JAR=$SPARROW_HOME/sparrow-server/target/sparrow-server-1.0.0.jar
SPARROW_CLASSPATH=$SPARROW_HOME/sparrow-server/target/lib/*
SIGAR_LIB=$SPARROW_HOME/sparrow-server/sigar-bin
SPARROW_MAIN=org.sparrow.service.SparrowDaemon

function exec_sparrow {
	eval $JAVA_HOME/bin/java -Xmx2G -Xms2G -Djava.library.path="$SIGAR_LIB" -cp "'$SPARROW_JAR:$SPARROW_CLASSPATH'" $SPARROW_MAIN
}

if [[ "$1" == "build" ]]; then
	eval mvn clean install
fi

if [[ "$1" == "client" ]]; then
    SPARROW_JAR_CLIENT=$SPARROW_HOME/sparrow-client/target/sparrow-client-1.0.0.jar
    SPARROW_CLASSPATH_CLIENT=$SPARROW_HOME/sparrow-client/target/lib/*
	eval $JAVA_HOME/bin/java -cp "'$SPARROW_JAR_CLIENT:$SPARROW_CLASSPATH_CLIENT'" org.sparrow.client.SparrowClient
fi

if [[ "$1" == "start" || "$1" == "" ]]; then
	exec_sparrow
fi

if [[ "$1" == "datatool" ]]; then
	SPARROW_MAIN=org.sparrow.tools.DataExtract
	exec_sparrow
fi
