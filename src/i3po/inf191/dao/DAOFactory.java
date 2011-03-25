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
		UMLSDefinitionDAO toReturn = null;
		try {
			toReturn = getCorrectUMLSDefDao(getServerDataSource());
		}
		catch (SQLException e) {
			log.error("Problem creating UMLSDefDAO(): " + e.getMessage());
			e.printStackTrace();
		}

		return toReturn;
	}


	private DataSource getServerDataSource() {
		Context initCtx;
		Context envCtx;
		DataSource datasource = null;

		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			//!!! Presupposes a  <Resource name="jdbc/UMLS"> in Servlet container's context
			datasource = (DataSource) envCtx.lookup("jdbc/UMLS");					
		}
		catch (NamingException e) {
			e.printStackTrace();
		}

		assert datasource != null;
		return datasource;
	}


	private UMLSDefinitionDAO getCorrectUMLSDefDao(DataSource ds) throws SQLException {
		UMLSDefinitionDAO dao;

		Connection testConnection = ds.getConnection();
		try {
			String driverName = testConnection.getMetaData().getDriverName().toLowerCase();
			log.info("JDBC Driver Name: " + driverName);
			if(driverName.startsWith("mysql")) {
				dao = new MySQL_UMLSDefDAO(ds);
			}
			else if(driverName.startsWith("microsoft sql server")) {
				dao = new SQLServer_UMLSDefDAO(ds);
			}
			else {
				throw new SQLException("JDBC Driver Name (" + driverName + ") was not recognized");
			}
		}
		finally {
			testConnection.close();
		}
	
		return dao;
	}
	
	
	public IMODefinitionDAO createIMODefDAO() throws I2B2Exception {
		return new IMODefinitionDAO();
	}
}
