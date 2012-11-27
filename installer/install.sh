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
TMPDIR="/tmp/"
BASEDIR=$(dirname $0)
LOCANDAVERSION="3.0"
LOCANDADIST="${TMPDIR}locanda-${LOCANDAVERSION}_dist"


MAX=100
IDLE=30
WAIT=10000
USERNAME="LOCANDA"
PASSWORD="LOCANDA"
DRIVERCLASS="org.hsqldb.jdbcDriver"
URL="jdbc:hsqldb:hsql://localhost/locanda"

HSQLURL="jdbc:hsqldb:hsql://localhost/locanda"
HSQLDRIVERCLASS="org.hsqldb.jdbcDriver"

MYSQLURL="jdbc:mysql://localhost:3306/locanda"
MYSQLDRIVERCLASS="com.mysql.jdbc.Driver"





clean(){
    rm -rf ${LOCANDADIST}  > /dev/null 2>&1
}

extract(){
    echo "Extracting..."
    unzip ${BASEDIR}/locanda.war -d ${LOCANDADIST} > /dev/null 2>&1
}


warbuild(){
    echo "Creating War..."
    cd ${LOCANDADIST}
    zip -r ${TMPDIR}/locanda.war ./* > /dev/null 2>&1
}

edit(){
    echo "Editing configuration..."
    
    EURL=`echo ${URL} | sed -f ${BASEDIR}/data/f2b.sed`
    EUSERNAME=`echo ${USERNAME} | sed -f ${BASEDIR}/data/f2b.sed`
    EPASSWORD=`echo ${PASSWORD} | sed -f ${BASEDIR}/data/f2b.sed`
    EDRIVERCLASS=`echo ${DRIVERCLASS} | sed -f ${BASEDIR}/data/f2b.sed`
    
    cat  ${BASEDIR}/data/context.xml |
    sed -e "s/maxActive\=\"100\"/maxActive\=\"${MAX}\"/" |
    sed -e "s/maxIdle\=\"30\"/maxIdle\=\"${IDLE}\"/"     |
    sed -e "s/maxWait\=\"10000\"/maxWait\=\"${WAIT}\"/"  |
    sed -e "s/username\=\"SA\"/username\=\"${EUSERNAME}\"/"  |
    sed -e "s/password\=\"\"/password\=\"${EPASSWORD}\"/"  |
    sed -e "s/driverClassName\=\"org\.hsqldb\.jdbcDriver\"/driverClassName\=\"${EDRIVERCLASS}\"/"  |
    sed -e "s/url\=\"jdbc\:hsqldb\:hsql\:\/\/localhost\/locanda\"/url\=\"${EURL}\"/" > ${LOCANDADIST}/META-INF/context.xml
} 

install(){

    if [ ! -z ${CATALINA_HOME} ]; then
	echo "Install Locanda in $CATALINA_HOME/webapps/";
	mv -i ${TMPDIR}/locanda.war $CATALINA_HOME/webapps/;
	echo "\n Installation complete! Now you can run Locanda"
    else
	echo "Please install Tomcat and set your CATALINA_HOME"
	echo "The variable CATALINA_HOME don't set. Aborting installation.";
    fi
}

interactiveEdit(){
    IST=n
    while [ ! $IST = y ]; do {
	    echo "\nSetting Parameters"
	    
	    echo "Username: [LOCANDA]"
	    read USERNAME
	    USERNAME="${USERNAME:=LOCANDA}"

	    echo "Password: [LOCANDA]"
	    read PASSWORD
	    PASSWORD="${PASSWORD:=LOCANDA}"

	    echo "Max connections: Default [100]"
	    read MAX
	    MAX="${MAX:=100}"

	    echo "Idle connections: Default [30]"
	    read IDLE
	    IDLE="${IDLE:=30}"

	    echo "Wait connections: Default [10000]"
	    read WAIT
	    WAIT="${WAIT:=10000}"

	    echo "Set Database Driverclass: Default [$1]"
	    read DRIVERCLASS
	    DRIVERCLASS="${DRIVERCLASS:=$1}"
	    
	    echo "Set Url to Database: Default [$2]"
	    read URL
	    URL="${URL:=$2}"

	    echo "RESUME:"
	    echo "Max connections:\t${MAX}"
	    echo "Max idle:\t\t${IDLE}"
	    echo "Max wait:\t\t${WAIT}"
	    echo "Username:\t\t${USERNAME}"
	    echo "Password:\t\t${PASSWORD}"
	    echo "Driverclass:\t\t${DRIVERCLASS}"
	    echo "Url:\t\t\t${URL}"

	    echo ""
	    echo "Correct? y/[n]"
	    read IST
	    IST="${IST:="n"}"
	}
    done
}






echo "Welcome MESSAGE HERE"


DB="0"
while [  ! \( ${DB} = "1" -o ${DB} = "2" \) ]; do {

	echo ""
	echo "wich db do you want to use??"
	echo " [1]: HyperSQL. (you don't need to install other sw)"
	echo " [2]: MySql. (you need to install it!)"
	echo ""
	echo "Please chose 1 or 2: [1]Default "
	
	read DB
	DB="${DB:=1}"
}
done


case ${DB} in
    1)
	echo "HSQL"
	echo "Please install hsql service, you it can found in data/hsql dir"
	interactiveEdit $HSQLDRIVERCLASS $HSQLURL
	;;
    2)
	echo "MySQL"
	interactiveEdit  $MYSQLDRIVERCLASS $MYSQLURL
	;;
esac




clean
extract
edit
warbuild
install
#clean

exit 0



