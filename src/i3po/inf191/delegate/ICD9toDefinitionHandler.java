package i3po.inf191.delegate;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ICD9toDefinitionHandler {
	protected final Log log = LogFactory.getLog(getClass());


	public String handleRequest(OMElement request) {
		return "hi there";
	}
}