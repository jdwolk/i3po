#xjc -d ./src ./src/DEF_QRY.xsd
#if [ -n "${TOMCAT_HOME:+nonnull}" ]
if [ -z "$TOMCAT_HOME" ]; then
	echo "\nYou must set TOMCAT_HOME (in .bashrc or in the current shell) in order to deploy i3po\n"	
else
	I3PO=.
	xjc -p i3po.inf191.xsd -d $I3PO/src $I3PO/src/
	cp $I3PO/lib/*.jar $TOMCAT_HOME/webapps/axis2/WEB-INF/lib
	cd $I3PO/bin/i3po/inf191/axis2/
	jar -cvf DefinitionService.aar ./*
	mv DefinitionService.aar $TOMCAT_HOME/webapps/axis2/WEB-INF/services/
	cd ../../../
	cp -R ./* $TOMCAT_HOME/webapps/axis2/WEB-INF/classes/
	$TOMCAT_HOME/bin/shutdown.sh
	echo "Wait one moment while Tomcat restarts\n"
	ping -c 2 www.google.com
	$TOMCAT_HOME/bin/startup.sh
fi
