#!/bin/sh

FILE=/tmp/mydb
NDB=0
DBNAME=locanda

java -cp ./lib/hsqldb.jar org.hsqldb.server.Server --database.${NDB} file:${FILE} --dbname.${NDB} ${DBNAME}
