package i3po.inf191.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLServer_UMLSDefDAO extends UMLSDefinitionDAO {

	public SQLServer_UMLSDefDAO(Connection conn) {
		super(conn);
	}
	
	// These values must be overridden so that the base class, UMLSDefinitionDAO, 
	// can use them when calling getICD9Title() & getICD9Definition()
	
	public ResultSet runICD9toTitleQuery(String icd9) throws SQLException
	{
		return runQuery("SELECT TOP 1 STR FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "'");
	}

	
	public ResultSet runICD9toDefQuery(String icd9) throws SQLException
	{
		return runQuery("SELECT DEF FROM MRDEF WHERE CUI LIKE" +
		"(SELECT TOP 1 CUI FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "')"); 
	}

}
