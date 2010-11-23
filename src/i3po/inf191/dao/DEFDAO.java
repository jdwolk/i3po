/*See the following:
 * 
 * http://docs.jboss.org/jbossas/docs/Server_Configuration_Guide/4/html/Connectors_on_JBoss-Configuring_JDBC_DataSources.html
 * http://javahowto.blogspot.com/2006/08/access-jboss-datasource-remotely-from.html
 * http://tomcat.apache.org/tomcat-5.5-doc/jndi-datasource-examples-howto.html
 * http://tomcat.apache.org/tomcat-5.5-doc/config/context.html
 * http://java.sun.com/developer/Books/JDBCTutorial/index.html
 * 
 * TODO: implement the CRC DAO pattern, using DEFDAO, IDAOFactory, 
 *  MySqlDAOFactory, DAOFactoryHelper, DSLookup, and ServiceLocator/QueryProcessorUtil
 *  
 *  this will allow for using the web server's context to a) identify data sources, and
 *  b) pool remote db connections instead of relying on hardcoded db data here
 */

package i3po.inf191.dao;

import i3po.inf191.util.DefinitionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.harvard.i2b2.common.exception.I2B2Exception;
import edu.harvard.i2b2.common.util.ServiceLocator;

public abstract class DEFDAO {

	protected final Log log = LogFactory.getLog(getClass());

	private DefinitionUtil util = DefinitionUtil.getInstance();
	private DataSource datasource = null;
	protected Connection conn = null;

	public DEFDAO() {
		establishDBConnection();
	}
	
	private void establishDBConnection() {
		if(util.getConnectionType() ==  DefinitionUtil.CONNECTIONS.SERVER)
			establishServerDBConnection();
		else if(util.getConnectionType() == DefinitionUtil.CONNECTIONS.LOCAL)
			establishLocalDBConnection();
		else
			throw new RuntimeException("DB connectionType unknown in DEFDAO");
	}
	
	private void establishServerDBConnection() {
		Context initCtx = null;
		Context envCtx = null;

		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			datasource = (DataSource) envCtx.lookup("jdbc/DB");
			
			conn = datasource.getConnection();
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		assert conn!= null && datasource != null;
	}

	private void establishLocalDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("JDBC loaded ok\n");
			conn = DriverManager.getConnection(util.getConnectionString());
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	protected Connection getConnection() throws SQLException {
		if(conn == null) {
			throw new SQLException("Connection has not yet been initialized; " +
			"call DEFDAO.establishDBConnection<connectionString>)");
		}
		else {
			return conn;
		}
	}
}
