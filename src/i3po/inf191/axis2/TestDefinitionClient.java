package i3po.inf191.axis2;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.namespace.QName;


import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMText;
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
            		"http://www.i2b2.org/xsd/hive/msg/", "i2b2");
            OMNamespace omNs2 = fac.createOMNamespace(
            		"www.i3po.inf191.def", "i3po");
            OMElement request = fac.createOMElement("request", omNs);
            request.declareNamespace(omNs2);
            
            OMElement msgHeader = fac.createOMElement(new QName("message_header"));
            msgHeader.addChild(fac.createOMText(msgHeader, "Hi"));
            
            OMElement rqstHeader = fac.createOMElement(new QName("request_header"));
            rqstHeader.addChild(fac.createOMText(rqstHeader, "There"));
            
            OMElement msgBody = fac.createOMElement(new QName("message_body"));
            OMElement operation = fac.createOMElement("icd9ToDefinitionRequest", omNs);
            operation.addChild(fac.createOMText(operation, "This is where a request argument would go"));
            msgBody.addChild(operation);
            
            request.addChild(msgHeader);
            request.addChild(rqstHeader);
            request.addChild(msgBody);
            
            return request;            
        }

        public static void main(String[] args) {
            try {
                OMElement payload = definitionPayload();
                Options options = new Options();
                options.setTo(targetEPR);
                options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
                options.setAction("icd9ToDefinitionRequest"); //MUY IMPORTANTE!!!

                ServiceClient sender = new ServiceClient();
                sender.setOptions(options);
                OMElement result = sender.sendReceive(payload);

                Iterator<OMElement> children = result.getFirstChildWithName(new QName("message_body")).getChildElements();
                for(Iterator<OMElement> i = children; children.hasNext(); ) {
                	OMElement next = i.next();
                	System.out.println(next.getText());
                }

            } catch (Exception e) { //(XMLStreamException e) {
                e.printStackTrace();
            }
        }
    
}


