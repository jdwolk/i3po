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

public class DAOFactory {
	protected final Log log = LogFactory.getLog(getClass());

	private DefinitionUtil util = DefinitionUtil.getInstance();

	public DAOFactory() {}

	
	public UMLSDefinitionDAO createUMLSDefDAO() {
		Connection conn;
		UMLSDefinitionDAO toReturn = null;
		try {
			if(util.getConnectionType() ==  DefinitionUtil.CONNECTIONS.SERVER) {
				conn = serverUMLSConnection();
				toReturn = getCorrectUMLSDefDao(conn);
			}
			else if(util.getConnectionType() == DefinitionUtil.CONNECTIONS.LOCAL) {
				conn = localUMLSConnection();
				toReturn = getCorrectUMLSDefDao(conn);
			}
			else {
				throw new SQLException("DB connectionType unknown in DAOFactory");
			}
		}
		catch (SQLException e) {
			log.error("Problem creating UMLSDefDAO(): " + e.getMessage());
			e.printStackTrace();
		}

		return toReturn;
	}


	private Connection serverUMLSConnection() {
		Context initCtx = null;
		Context envCtx = null;
		DataSource datasource = null;
		Connection conn = null;

		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			//!!! Presupposes a  <Resource name="jdbc/UMLS"> in Servlet container
			datasource = (DataSource) envCtx.lookup("jdbc/UMLS");		

			conn = datasource.getConnection();
			
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		assert conn!= null;
		return conn;
	}


	private Connection localUMLSConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(util.getConnectionString());
			util.setConnectionString(conn.getMetaData().getURL());
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

		assert conn!= null;
		return conn;
	}


	private UMLSDefinitionDAO getCorrectUMLSDefDao(Connection conn) throws SQLException {
		UMLSDefinitionDAO dao = null;
		String driverName = conn.getMetaData().getDriverName().toLowerCase();
		log.info("JDBC Driver Name: " + driverName);

		if(driverName.startsWith("mysql")) {
			dao = new MySQL_UMLSDefDAO(conn);
		}
		else if(driverName.startsWith("microsoft sql server")) {
			dao = new SQLServer_UMLSDefDAO(conn);
		}
		else {
			throw new SQLException("JDBC Driver Name (" + driverName + ") was not recognized");
		}
		//util.setConnectionString(conn.getMetaData().getURL());
		return dao;
	}
	
	
	public IMODefinitionDAO createIMODefDAO() throws I2B2Exception {
		return new IMODefinitionDAO();
	}
}
