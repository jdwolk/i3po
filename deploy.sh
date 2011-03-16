#xjc -d ./src ./src/DEF_QRY.xsd
#if [ -n "${TOMCAT_HOME:+nonnull}" ]
if [ -z "$TOMCAT_HOME" ] || [ ! -d $TOMCAT_HOME ] || [ ! -w $TOMCAT_HOME ]; then
	echo ""
	echo "You must set TOMCAT_HOME (in .bashrc, /etc/environment, or in the current shell) in order to deploy i3po. Be sure to omit the trailing '/'."
	echo ""
	echo "If your TOMCAT_HOME is not world-writeable, try executing 'sudo env TOMCAT_HOME=\$TOMCAT_HOME ./deploy.sh'."
	echo ""
else
	I3PO=`pwd`
	xjc -p i3po.inf191.xsd -d $I3PO/src $I3PO/src/
	cp $I3PO/lib/*.jar $TOMCAT_HOME/webapps/axis2/WEB-INF/lib
	cd $I3PO/bin/i3po/inf191/axis2/
	jar -cvf DefinitionService.aar ./*
	mv DefinitionService.aar $TOMCAT_HOME/webapps/axis2/WEB-INF/services/
	cd $I3PO/bin/
	cp -R ./* $TOMCAT_HOME/webapps/axis2/WEB-INF/classes/
	$TOMCAT_HOME/bin/shutdown.sh > /dev/null
	echo "Wait one moment while Tomcat restarts\n"
	ping -c 2 www.google.com > /dev/null
	$TOMCAT_HOME/bin/startup.sh
fi
