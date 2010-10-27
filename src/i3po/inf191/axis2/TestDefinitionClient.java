package i3po.inf191.axis2;

import java.util.Iterator;

import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.Constants;
import org.apache.axis2.client.ServiceClient;

public class TestDefinitionClient {

       private static EndpointReference targetEPR = 
             new EndpointReference("http://localhost:8080/axis2/services/DefinitionService");

        public static OMElement definitionPayload() {
            OMFactory fac = OMAbstractFactory.getOMFactory();
            OMNamespace omNs = fac.createOMNamespace(
            		"http://i3po.inf191.com", "icd9Todef");
            OMElement method = fac.createOMElement("icd9ToDefinitionRequest", omNs);
            OMElement value = fac.createOMElement("icd9", omNs);
            value.addChild(fac.createOMText(value, "Hi?"));
            method.addChild(value);
            return method;
        }

        public static void main(String[] args) {
            try {
                OMElement payload = definitionPayload();
                Options options = new Options();
                options.setTo(targetEPR);
                options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

                ServiceClient sender = new ServiceClient();
                sender.setOptions(options);
                OMElement result = sender.sendReceive(payload);

                String response = result.getFirstElement().getText();
                System.out.println(response);
                
                for(Iterator<OMElement> i = result.getChildren(); i.hasNext(); ) {
                	OMElement next = i.next();
                	System.out.println(next.toString());
                }

            } catch (Exception e) { //(XMLStreamException e) {
                System.out.println(e.toString());
            }
        }
    
}


