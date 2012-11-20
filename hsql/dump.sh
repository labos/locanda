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



# USAGE:
# dump.sh /my/outfile


USER="SA"
PASSWD="\"\""
OUTFILE=$1
BASEDIR=$(dirname $0)

usage(){
    app=`basename $0`
    echo "Usage: ${app} dumpFile";
    exit 1
}

if [ "$#" -ne 1 ]; then
    usage;
fi

if [ $1 = "-h" -o $1 = "--h" -o $1 = "-help" -o $1 = "--help" ]; then
    usage;
fi

echo java -jar ${BASEDIR}/lib/sqltool.jar --inlineRc=url=jdbc:hsqldb:hsql://localhost/locanda,user=SA,password="" --sql="SCRIPT '${OUTFILE}';"


java -jar ${BASEDIR}/lib/sqltool.jar --inlineRc=url=jdbc:hsqldb:hsql://localhost/locanda,user=SA,password="" --sql="SCRIPT '${OUTFILE}';"




