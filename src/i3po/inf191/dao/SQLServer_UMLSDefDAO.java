package i3po.inf191.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class SQLServer_UMLSDefDAO extends UMLSDefinitionDAO {

	public SQLServer_UMLSDefDAO(DataSource ds) {
		super(ds);
	}
	
	// These values must be overridden so that the base class, UMLSDefinitionDAO, 
	// can use them when calling getICD9Title() & getICD9Definition()
	
	protected ResultSet runICD9toTitleQuery(String icd9) throws SQLException
	{
		return runQuery("SELECT TOP 1 STR FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "'");
	}

	
	protected ResultSet runICD9toDefQuery(String icd9) throws SQLException
	{
		return runQuery("SELECT DEF FROM MRDEF WHERE CUI LIKE" +
		"(SELECT TOP 1 CUI FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "')"); 
	}

}
