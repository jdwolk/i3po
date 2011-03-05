package i3po.inf191.util;

/**
 * Singleton utility class for the DEF cell
 *
 */
public class DefinitionUtil {
	
	private static DefinitionUtil thisInstance;
	
	/**
	 * For specifying whether db connections will run out of a servlet container like
	 * Tomcat or JBoss (i.e. through DataSource) or locally (by DriverManager).
	 *
	 */
	public enum CONNECTIONS {SERVER, LOCAL};
	private CONNECTIONS connectionType = CONNECTIONS.SERVER; //default
		
	// For local db connections, must specify a connection string.
	// If using a server db connection instead, modify your servlet 
	// container's context.xml or similar.
	private String connectionString = "jdbc:mysql://localhost/umls?" +
	".&user=UMLS&password=XXXXX";
	
	// Specifies whether IMO (Intelligent Medical Objects) web portal is
	// installed, allowing for better search results.
	// See http://www.e-imo.com/demo/problem_IT_portal.aspx
	private boolean IMO_ENABLED = false;
	
	//TODO Keep here for now; later, process as a Resource in Tomcat's context.xml 
	private String imoURL = "XXX.XXX.XXX.XXX";
	private int imoPort = 1967;
	
	private DefinitionUtil() {}
	
	public static DefinitionUtil getInstance() {
		if(thisInstance == null)
			thisInstance = new DefinitionUtil();
		return thisInstance;
	}
	
	/////////////////////////////
	//  ACCESSORS & MODIFIERS  //
	/////////////////////////////
	
	/**
	 * For setting the local connection's jdbc connection string.
	 * 
	 * Note that, if you are running a database out of a servlet container,
	 * you must modify your context.xml or similar. This method
	 * DOES NOT change these kinds of connections.
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	
	/**
	 * For getting the local connection's jdbc connection string.
	 */
	public String getConnectionString() {
		return thisInstance.connectionString;
	}
	
	public CONNECTIONS getConnectionType() {
		return thisInstance.connectionType;
	}
	
	public void setConnectionType(CONNECTIONS type) {
		this.connectionType = type;
	}
	
	
	// IMO Specific //
	
	public boolean isIMOEnabled() {
		return IMO_ENABLED;
	}
	
	public String getIMOUrl() {
		return imoURL;
	}
	
	public int getIMOPort() {
		return imoPort;
	}
}
