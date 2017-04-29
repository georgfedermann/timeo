#!/usr/bin/env bash
JAVA_OPTS="$JAVA_OPTS -Djavax.net.ssl.trustStore=src/test/resources/testcertificates/timeo-taskservice.truststore"
JAVA_OPTS="$JAVA_OPTS -Djavax.net.debug=ssl"
echo "using JAVA_OPTS: $JAVA_OPTS"
mvn -Pprod $JAVA_OPTS clean package

