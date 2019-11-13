#!/bin/sh
echo $JAVA_HOME
JAVA_EXT_DIRS=$JAVA_HOME/jre/lib/ext

SERVICE_BIN="${BASH_SOURCE-$0}"
SERVICE_DIR="$(dirname ${SERVICE_BIN})"
LOG_DIR=../${SERVICE_DIR}/log
#jvm parameters
JAVA_OPTS="
-server
-Xmx2g
-Xms2g
-Xmn1g
-Xss256k
-DLANG="zh_CN.GBK"
-Xloggc:${LOG_DIR}/${SERVER_NAME}.gc
-XX:+UseParallelGC
-XX:ParallelGCThreads=8
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=${LOG_DIR}/dump
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps"


SPRING_CONFIG_LOCATION="${SERVICE_DIR}/../config/application.properties"

LOGGIN_CONFIG="${SERVICE_DIR}/../config/logback.xml"

nohup java $JAVA_OPTS -jar -Dspring.config.location=${SPRING_CONFIG_LOCATION} -Dlogging.config=${LOGGIN_CONFIG} >/dev/null ../receive.jar 2>&1 &