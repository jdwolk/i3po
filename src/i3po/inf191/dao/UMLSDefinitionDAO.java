package i3po.inf191.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class UMLSDefinitionDAO {
	protected final Log log = LogFactory.getLog(getClass());
	private DataSource ds;
	private Connection currentConnection;

	public UMLSDefinitionDAO(DataSource ds){
		this.ds = ds;
	}


	private void establishConnection() {
		log.info("Establishing db connection");
		try {
			if(currentConnection != null) {
				log.info("db connection was not null");
				closeConnection();
			}
			currentConnection = ds.getConnection();
		}
		catch(SQLException sqe) {
			sqe.printStackTrace();
		}
	}


	private void closeConnection() throws SQLException {
		log.info("Closing db connection");
		currentConnection.close();
		currentConnection = null;
	}
	
	/**
	 * YOU SHOULD NOT CALL THIS DIRECTLY EXCEPT WHEN OVERRIDING runICD9toTitleQuery() and
	 * runICD9toDefQuery() in concrete subclasses of UMLSDefinitionDAO.
	 * 
	 * @see {@link MySQL_UMLSDefinitionDAO}, {@link SQLServer_UMLSDefDAO} for examples
	 * 
	 */
	protected ResultSet runQuery(String queryString) throws SQLException
	{
		ResultSet rs;
		establishConnection();
		Statement stmt = currentConnection.createStatement();
		rs = stmt.executeQuery(queryString);

		return rs;
	}


	/**
	 * YOU SHOULD NOT CALL THIS DIRECTLY -- it is a hook method that must be
	 * redefined in subclasses of UMLSDefinitionDAO.
	 * 
	 * It is called internally by getICD9Title(), so use that instead.
	 */
	protected ResultSet runICD9toTitleQuery(String icd9) throws SQLException
	{
		throw new SQLException("Must override runICD9toTitleQuery() in subclass of UMLSDefinitionDAO");
	}


	/**
	 * YOU SHOULD NOT CALL THIS DIRECTLY -- it is a hook method that must be
	 * redefined in subclasses of UMLSDefinitionDAO.
	 * 
	 * It is called internally by getICD9Definition(), so use that instead.
	 */
	protected ResultSet runICD9toDefQuery(String icd9) throws SQLException
	{
		throw new SQLException("Must override runICD9toDefQuery() in subclass of UMLSDefinitionDAO"); 
	}


	/**
	 * For running queries against UMLS of the form icd9 --> term name.
	 * Note that the name/title returned is the UMLS title and not the
	 * i2b2 title.
	 * 
	 * Returns the name/title or, if none is found, the empty string('').
	 */
	public String getICD9Title(String icd9) throws SQLException
	{
		log.info("Getting ICD9 title");
		ResultSet rs = runICD9toTitleQuery(icd9);
		return handleResultSet(rs, "STR");
	}


	/**
	 * For running queries against UMLS of the form icd9 --> definition.
	 * 
	 * Returns the definition if found, or else the empty string('')
	 */
	public String getICD9Definition(String icd9) throws SQLException
	{
		log.info("Getting ICD9 definition");
		ResultSet rs = runICD9toDefQuery(icd9);
		return handleResultSet(rs, "DEF");
	}
	
	
	private String handleResultSet(ResultSet rs, String toGet) throws SQLException{
		String definition = rs.next() ? rs.getString(toGet) : "";
		rs.getStatement().close();
		rs.close();
		closeConnection();
		return definition;
	}

}
