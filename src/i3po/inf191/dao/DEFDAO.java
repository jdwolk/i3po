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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DEFDAO {
	protected final Log log = LogFactory.getLog(getClass());

	
	protected String connectionString = "jdbc:mysql://localhost/umls?" +
	".&user=inf191&password=i2b2_wtf";
	
	private DataSource datasource = null;
	protected Connection conn = null;
	
	public DEFDAO() {
		establishDBConnection();
	}
	
	public DEFDAO(String connectionString) {
		this.connectionString = connectionString;
		establishDBConnection();
	}
	
	//TODO remove once steps described above are in place
	private void establishDBConnection()
	{
			Context initCtx = null;
			Context envCtx = null;

			try {
				initCtx = new InitialContext();
			} catch (NamingException e) {
				System.out.println("InitContext Exception : " + e.getMessage());
				e.printStackTrace();
			}

			try {
				envCtx = (Context) initCtx.lookup("java:comp/env");
			} catch (NamingException e) {
				System.out.println("envContext Exception : " + e.getMessage());
				e.printStackTrace();
			}

			try {
				log.info("ENVIRONMENT: #elements = " + envCtx.getEnvironment().size());
				while(envCtx.getEnvironment().keys().hasMoreElements()) {
					log.info("\t" + envCtx.getEnvironment().keys().nextElement());
				}
				
				
				datasource = (DataSource) envCtx.lookup("jdbc/DB");
			} catch (NamingException e) {
				System.out.println("DataSource Exception : " + e.getMessage());
				e.printStackTrace();
			}

			try {
				conn = datasource.getConnection();
			} catch (SQLException e) {
				System.out.println("SQLException while trying to get connection : "
						+ e.getMessage());
				e.printStackTrace();
			}

			assert conn!= null && datasource != null;
		}

	
	//TODO remove once steps described above are in place
	private void establishLocalDBConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("JDBC loaded ok\n");
			conn = DriverManager.getConnection(connectionString);
		}
		catch(ClassNotFoundException cnf)
		{
			System.err.println("ClassNotFoundException: "
					+ cnf.getMessage());
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	protected Connection getConnection() throws SQLException {//I2B2Exception {
		if(conn == null) {
			throw new SQLException("Connection has not yet been initialized; " +
					"call DEFDAO.establishDBConnection<connectionString>)");
		}
		else {
			return conn;
		}
		/*
		try {
			return datasource.getConnection();
		}
		catch (SQLException i2b2Ex) {
			throw new I2B2Exception("Could not get datasource connection", i2b2Ex);
		}
		*/
	}
	
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
}
