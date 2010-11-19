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
	
	private DefinitionUtil() {}
	
	public DefinitionUtil getInstance() {
		if(thisInstance == null)
			thisInstance = new DefinitionUtil();
		//serviceLocator = ServiceLocator.getInstance();
		return thisInstance;
	}
}
