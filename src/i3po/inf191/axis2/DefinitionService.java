package i3po.inf191.axis2;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefinitionService {
	protected final Log log = LogFactory.getLog(getClass());

	public OMElement icd9ToDefinitionRequest(OMElement element) 
	throws XMLStreamException {
		//assert (element != null);
		element.build();
		element.detach();

		String rootName = element.getLocalName();
		log.debug("Inside icd9ToDefinitionRequest; rootname=" + rootName);

		OMElement childElement = element.getFirstElement();
		System.out.println("REQUEST:\n" + element.toString());

		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace(
				"http://i3po.inf191.com", "icd9Todef");
		OMElement method = fac.createOMElement("icd9ToDefinitionResponse", omNs);
		OMElement value = fac.createOMElement("definition", omNs);
		value.addChild(fac.createOMText(value, "This is where the definition would go"));
		method.addChild(value);
		
		System.out.println("MESSAGE:");
		System.out.println(method.toString());

		return method;
	}

}
