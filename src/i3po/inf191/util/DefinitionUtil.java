package i3po.inf191.util;

import edu.harvard.i2b2.common.util.ServiceLocator;

/**
 * Singleton utility class for the DEF cell;
 * Will play a more prominent role once we figure out how
 * to work Spring into the deployment.
 * @author jordaniel
 *
 */
public class DefinitionUtil {
	
	private static DefinitionUtil thisInstance;
	
	/**
	 * For specifying whether db connections will run out of a server (i.e. through
	 * DataSource) or locally (by DriverManager). Should be removed once 
	 * configuration through Spring is set up
	 * @author jordaniel
	 *
	 */
	public enum CONNECTIONS {SERVER, LOCAL};
	private CONNECTIONS connectionType = CONNECTIONS.SERVER; //default
	
	private String connectionString = "jdbc:mysql://localhost/umls?" +
	".&user=inf191&password=i2b2_wtf"; 
	
	private DefinitionUtil() {}
	
	public static DefinitionUtil getInstance() {
		if(thisInstance == null)
			thisInstance = new DefinitionUtil();
		//serviceLocator = ServiceLocator.getInstance();
		return thisInstance;
	}
	
	/////////////////////////////
	//  ACCESSORS & MODIFIERS  //
	/////////////////////////////
	
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
	public String getConnectionString() {
		return thisInstance.connectionString;
	}
	
	public CONNECTIONS getConnectionType() {
		return thisInstance.connectionType;
	}
	
	public void setConnectionType(CONNECTIONS type) {
		this.connectionType = type;
	}
}
