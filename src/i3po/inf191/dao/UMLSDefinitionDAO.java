package i3po.inf191.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UMLSDefinitionDAO extends DEFDAO {
	
	public UMLSDefinitionDAO() {
		super();
	}
	
	
	private ResultSet runQuery(String queryString) throws SQLException
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
		return runQuery("SELECT STR FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "' LIMIT 1;");
	}
	
	
	private ResultSet runICD9toDefQuery(String icd9) throws SQLException
	{
		return runQuery("SELECT DEF FROM MRDEF WHERE CUI LIKE" +
		"(SELECT CUI FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "' LIMIT 1);"); 
	}
	
	//TODO refactor
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

}
