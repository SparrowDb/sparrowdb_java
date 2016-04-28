#!/bin/bash

export SPARROW_HOME=$(dirname $(dirname $(readlink -f "$0")))
cd $SPARROW_HOME

SPARROW_JAR=$SPARROW_HOME/sparrow-client/target/sparrow-client-1.0.0.jar
SPARROW_CLASSPATH_CLIENT=$SPARROW_HOME/sparrow-client/target/lib/*


eval $JAVA_HOME/bin/java -cp "'$SPARROW_JAR:$SPARROW_CLASSPATH_CLIENT'" org.sparrow.client.SparrowClient "${@}"