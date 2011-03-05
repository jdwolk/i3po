/*See the following:
 * 
 * http://docs.jboss.org/jbossas/docs/Server_Configuration_Guide/4/html/Connectors_on_JBoss-Configuring_JDBC_DataSources.html
 * http://javahowto.blogspot.com/2006/08/access-jboss-datasource-remotely-from.html
 * http://tomcat.apache.org/tomcat-5.5-doc/jndi-datasource-examples-howto.html
 * http://tomcat.apache.org/tomcat-5.5-doc/config/context.html
 * http://java.sun.com/developer/Books/JDBCTutorial/index.html
 * 
 */

package i3po.inf191.dao;

import java.sql.Connection;
import java.sql.SQLException;


public interface DEFDAO {
	public Connection getConnection() throws SQLException;
	
	public void setConnection(Connection conn);
}
