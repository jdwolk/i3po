package i3po.inf191.delegate;

import java.util.Iterator;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ICD9toDefinitionHandler {
	protected final Log log = LogFactory.getLog(getClass());


	public String handleRequest(OMElement request) {
		for(Iterator<OMElement> i = request.getAllAttributes(); i.hasNext(); ) {
			OMElement next = i.next();
			log.info("[INFO] " + next.toString());
			System.out.println(next.toString());
		}
		return "ok. Done.";
	}
}