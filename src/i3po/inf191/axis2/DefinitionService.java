package i3po.inf191.axis2;

import i3po.inf191.delegate.ICD9toDefinitionRequestDelegate;
import i3po.inf191.delegate.RequestHandlerDelegate;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringReader;
import java.util.logging.*;

public class DefinitionService {	

	/*
	public OMElement icd9ToDefinitionRequest(OMElement icd9Element) 
	throws XMLStreamException {
		assert (icd9Element != null);
		icd9Element.build();
		icd9Element.detach();

		String rootName = icd9Element.getLocalName();
		Logger.getLogger(getClass().getName()).info(
				"Inside icd9ToDefinitionRequest; rootname=" + rootName);

		OMElement childElement = icd9Element.getFirstElement();
		Logger.getLogger(getClass().getName()).info(
				"REQUEST:\n" + icd9Element.toString());

		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace(
				"http://i3po.inf191.com", "icd9Todef");
		OMElement method = fac.createOMElement("icd9ToDefinitionResponse", omNs);
		OMElement value = fac.createOMElement("definition", omNs);
		value.addChild(fac.createOMText(value, "Test"));
		method.addChild(value);
		
		Logger.getLogger(getClass().getName()).info(
				"MESSAGE:\n" + method.toString());

		return method;
	}
	*/
	
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
		log.debug("Inside setfinder request " + icd9Element);
		return handleRequest(ICD9_TO_DEF_REQUEST, icd9Element);
	}

	
	private OMElement handleRequest(String requestType, OMElement request) {
		RequestHandlerDelegate requestHandlerDelegate = null;

		if (requestType.equals(ICD9_TO_DEF_REQUEST)) {
			log.debug("Inside handleRequest for DefinitionService");
			requestHandlerDelegate = new ICD9toDefinitionRequestDelegate();
		}
		OMElement returnElement = null;
		try {
			// call delegate's handleRequest function
			String response = requestHandlerDelegate.handleRequest(request.toString());
			log.info("Response in service" + response);
			returnElement = buildOMElementFromString(response);
		} catch (XMLStreamException e) {
			log.error("xml stream exception: " + e);
		} catch (Throwable e) {
			log.error("throwable: " + e);
		}
		return returnElement;
	}
	
	
	private OMElement buildOMElementFromString(String xmlString)
		throws XMLStreamException {
		XMLInputFactory xif = XMLInputFactory.newInstance();
		StringReader strReader = new StringReader(xmlString);
		XMLStreamReader reader = xif.createXMLStreamReader(strReader);
		StAXOMBuilder builder = new StAXOMBuilder(reader);
		OMElement element = builder.getDocumentElement();
		return element;
	}	

}
