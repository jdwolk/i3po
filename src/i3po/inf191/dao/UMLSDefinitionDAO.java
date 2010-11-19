package i3po.inf191.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UMLSDefinitionDAO extends DEFDAO {
	
	public UMLSDefinitionDAO() {
		super();
	}
	
	public UMLSDefinitionDAO(String string) {
		super(string);
	}
	
	public UMLSDefinitionDAO(CONNECTIONS type) {
		super(type);
	}
	
	public UMLSDefinitionDAO(String connectionString, CONNECTIONS type) {
		super(connectionString, type);
	}

	
	
	private ResultSet runUMLSQuery(String icd9) throws SQLException
	{
		Statement stmt = getConnection().createStatement();
		String sqlText = "SELECT DEF FROM MRDEF WHERE CUI LIKE" +
		"(SELECT CUI FROM MRCONSO WHERE SAB='ICD9CM' AND CODE LIKE '" + icd9 + "' LIMIT 1)";
		
		ResultSet rs = stmt.executeQuery(sqlText);

		if(stmt.execute(sqlText))
		{
			rs = stmt.getResultSet();
		}
		return rs;  
	}
	
	public String getDefinition(String icd9) throws SQLException
	{
		ResultSet rs = runUMLSQuery(icd9);
		String toReturn = rs.next() ? rs.getString("DEF") : "(no definition found)";
		return toReturn;
	}

}
