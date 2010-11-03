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

public abstract class DEFDAO {
	
	//private DataSource datasource = null;
	private static Connection conn = null;
	
	public DEFDAO() {}
	
	public DEFDAO(String connectionString) {
		establishDBConnection(connectionString);
	}
	
	//TODO remove once steps described above are in place
	private void establishDBConnection(String connectionURL)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JDBC loaded ok\n");
			conn = DriverManager.getConnection(connectionURL);
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
	
}
