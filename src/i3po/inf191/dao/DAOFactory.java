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

public class DAOFactory {
	protected final Log log = LogFactory.getLog(getClass());

	private DefinitionUtil util = DefinitionUtil.getInstance();
	//private DataSource datasource = null;
	//protected Connection conn = null;
	
	public DAOFactory() {}
	
	public UMLSDefinitionDAO createUMLSDefDAO() {
		Connection conn;
		UMLSDefinitionDAO toReturn = null;
		
		if(util.getConnectionType() ==  DefinitionUtil.CONNECTIONS.SERVER) {
			conn = serverUMLSConnection();
			
			try {
				String driverName = conn.getMetaData().getDriverName();
				String dbURL = conn.getMetaData().getURL();
				log.info("JDBC Driver Name: " + driverName);
				log.info("JDBC Connection URL: " + dbURL);
			} catch (SQLException e) {
				log.error("Problem getting connection metadata in createUMLSDefDAO(): " + e.getMessage());
				e.printStackTrace();
			}
			

		}
		else if(util.getConnectionType() == DefinitionUtil.CONNECTIONS.LOCAL) {
			localUMLSConnection();
		}
		else {
			throw new RuntimeException("DB connectionType unknown in DEFDAO");
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
}
