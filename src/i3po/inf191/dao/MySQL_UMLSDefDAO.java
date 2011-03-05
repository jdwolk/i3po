package i3po.inf191.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL_UMLSDefDAO extends UMLSDefinitionDAO {
	
	public MySQL_UMLSDefDAO(Connection conn) {
		super(conn);
	}

	// These values must be overridden so that the base class, UMLSDefinitionDAO, 
	// can use them when calling getICD9Title() & getICD9Definition()
	
	protected ResultSet runICD9toTitleQuery(String icd9) throws SQLException
	{
		return runQuery("SELECT STR FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "' LIMIT 1");
	}

	
	protected ResultSet runICD9toDefQuery(String icd9) throws SQLException
	{
		return runQuery("SELECT DEF FROM MRDEF WHERE CUI LIKE" +
		"(SELECT CUI FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "' LIMIT 1)"); 
	}

}
