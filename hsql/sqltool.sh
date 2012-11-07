#!/bin/sh
USER="SA"
PASSWD="\"\""

java -jar ./lib/sqltool.jar --inlineRc=url=jdbc:hsqldb:hsql://localhost/locanda,user=SA,password="" $1


