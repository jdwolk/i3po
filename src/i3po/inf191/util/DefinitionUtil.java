package i3po.inf191.util;

/**
 * Singleton utility class for the DEF cell
 *
 */
public class DefinitionUtil {
	
	private static DefinitionUtil thisInstance;

	
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
