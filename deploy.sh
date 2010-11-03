cd ./bin/i3po/inf191/axis2/
jar -cvf DefinitionService.aar ./*
mv DefinitionService.aar /opt/apache-tomcat-7.0.4/webapps/axis2/WEB-INF/services/
cd ../../../
cp -R ./* /opt/apache-tomcat-7.0.4/webapps/axis2/WEB-INF/classes/
/opt/apache-tomcat-7.0.4/bin/shutdown.sh
ping -c 2 www.google.com
/opt/apache-tomcat-7.0.4/bin/startup.sh