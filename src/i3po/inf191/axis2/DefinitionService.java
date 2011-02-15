package i3po.inf191.axis2;

import i3po.inf191.delegate.BasecodeToDefinitionDelegate;
import i3po.inf191.delegate.RequestDelegate;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DefinitionService {	
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private final String BASECODE_TO_DEF_REQUEST = "BASECODE_TO_DEF_REQUEST";
	
	
	/**
	 *  Service operation to retrieve a definition for the given icd9 code, if such
	 *  a definition exists.
	 *  
	 *  @param icd9element
	 *  @return OMElement
	 */
	public OMElement basecodeToDefinitionRequest(OMElement basecodeElement) {
		if (basecodeElement == null) {
			log.error("basecode was null");
			return null;
			//TODO construct error response message, and return OMElement from buildOMElementFromString()
		}
		
		log.info("Inside the defintion request " + basecodeElement);
		return delegateRequest(BASECODE_TO_DEF_REQUEST, basecodeElement);
	}


	
	private OMElement delegateRequest(String requestType, OMElement request) {
		RequestDelegate delegate = null;
		log.info("Inside handleRequest for " + requestType);

		if (requestType.equals(BASECODE_TO_DEF_REQUEST)) {
			log.info("Inside handleRequest for DefinitionService");
			delegate = new BasecodeToDefinitionDelegate();
		}
		OMElement returnElement = null;
		try {
			String response = delegate.delegateRequest(request.toString());
			log.info("Response in service " + response);
			returnElement = AxisUtils.buildOMElementFromString(response);
		} catch (XMLStreamException e) {
			log.error("xml stream exception: " + e);
		} catch (Throwable e) {
			log.error("throwable: " + e);
		}
		return returnElement;
	}
}
