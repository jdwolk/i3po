package i3po.inf191.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class UMLSDefinitionDAO implements DEFDAO {
	private Connection conn;

	public UMLSDefinitionDAO(Connection conn){
		this.conn = conn;
	}


	/**
	 * YOU SHOULD NOT CALL THIS DIRECTLY EXCEPT WHEN OVERRIDING runICD9toTitleQuery() and
	 * runICD9toDefQuery() in concrete cubclasses of UMLSDefinitionDAO.
	 * 
	 * @see {@link MySQL_UMLSDefinitionDAO}, {@link SQLServer_UMLSDefDAO} for examples
	 * 
	 */
	protected ResultSet runQuery(String queryString) throws SQLException
	{
		Statement stmt = getConnection().createStatement();

		ResultSet rs = stmt.executeQuery(queryString);

		if(stmt.execute(queryString))
		{
			rs = stmt.getResultSet();
		}
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
		ResultSet rs = runICD9toTitleQuery(icd9);
		return rs.next() ? rs.getString("STR") : "";
	}


	/**
	 * For running queries against UMLS of the form icd9 --> definition.
	 * 
	 * Returns the definition if found, or else the empty string('')
	 */
	public String getICD9Definition(String icd9) throws SQLException
	{
		ResultSet rs = runICD9toDefQuery(icd9);
		return rs.next() ? rs.getString("DEF") : "";
	}


	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}


}
