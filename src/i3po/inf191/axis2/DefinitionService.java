package i3po.inf191.axis2;

import i3po.inf191.delegate.ICD9toDefinitionHandler;
import i3po.inf191.delegate.ICD9toDefinitionDelegate;
import i3po.inf191.delegate.RequestDelegate;

import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringReader;

public class DefinitionService {	
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private final String ICD9_TO_DEF_REQUEST = "ICD9_TO_DEF_REQUEST";
	
	
	/**
	 *  Service operation to retrieve a definition for the given icd9 code, if such
	 *  a definition exists.
	 *  
	 *  @param icd9element
	 *  @return OMElement
	 */
	public OMElement icd9ToDefinitionRequest(OMElement icd9Element) {
		if (icd9Element == null) {
			log.error("icd9element was null");
			return null;
			//TODO construct error response message, and return OMElement from buildOMElementFromString()
		}
		
		System.out.println("Inside the definition request " + icd9Element);
		log.info("Inside the defintion request " + icd9Element);
		return delegateRequest(ICD9_TO_DEF_REQUEST, icd9Element);
	}


	
	private OMElement delegateRequest(String requestType, OMElement request) {
		RequestDelegate delegate = null;
		log.info("Inside handleRequest for " + requestType);

		if (requestType.equals(ICD9_TO_DEF_REQUEST)) {
			log.info("Inside handleRequest for DefinitionService");
			delegate = new ICD9toDefinitionDelegate();
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
