#!/bin/sh

#     Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
#   
#    Licensed under the EUPL, Version 1.1.
#    You may not use this work except in compliance with the Licence.
#    You may obtain a copy of the Licence at:
#   
#     http://www.osor.eu/eupl
#   
#    Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the Licence for the specific language governing permissions and limitations under the Licence.
#    In case of controversy the competent court is the Court of Cagliari (Italy).


#FILE=/tmp/mydb

CFG=/etc/hsqldaemon.cfg

BASEDIR=$(dirname $0)

FILE=`cat $CFG | grep FILE | sed -e 's/FILE\=//'`
NDB=`cat $CFG | grep NDB | sed -e 's/NDB\=//'`
DBNAME=`cat $CFG | grep DBNAME | sed -e 's/DBNAME\=//'`
USER=`cat $CFG | grep USER | sed -e 's/USER\=//'`
PASSWORD=`cat $CFG | grep PASSWORD | sed -e 's/PASSWORD\=//'`
URL=`cat $CFG | grep URL | sed -e 's/URL\=//'`

echoCFG(){
    echo $FILE
    echo $NDB
    echo $DBNAME
    echo $USER
    echo $PASSWORD
    echo $URL
}

usage(){
    app=`basename $0`
    echo "Usage: "
    echo "  - ${app} start | stop ";
    exit 1
}

if [ "$#" -ne 1 ]; then
    usage;
fi

if [ $1 = "-h" -o $1 = "--h" -o $1 = "-help" -o $1 = "--help" ]; then
    usage;
fi


if [ $1 = "stop" ]; then
    echo ${PASSWORD}
    java -jar ${BASEDIR}/lib/sqltool.jar --sql="SHUTDOWN;"  --inlineRc=url=${URL},user=${USER} 
    exit 0
fi


if [ $1 = "start" ]; then
    java -cp ${BASEDIR}/lib/hsqldb.jar org.hsqldb.server.Server --database.${NDB} file:${FILE} --dbname.${NDB} ${DBNAME} &
    exit 0 
fi
    
