package i3po.inf191.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class UMLSDefinitionDAO implements DEFDAO {
	
	public UMLSDefinitionDAO() { }
	
	
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
	
	
	private ResultSet runICD9toTitleQuery(String icd9) throws SQLException
	{
		throw new SQLException("Must override runICD9toTitleQuery() in subclass of UMLSDefinitionDAO");
	}
	
	
	private ResultSet runICD9toDefQuery(String icd9) throws SQLException
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
		return null;
		/*
		if(conn == null) {
			throw new SQLException("Connection has not yet been initialized; " +
			"call DEFDAO.establishDBConnection<connectionString>)");
		}
		else {
			return conn;
		}
		*/
	}
	
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub

	}
	

}
