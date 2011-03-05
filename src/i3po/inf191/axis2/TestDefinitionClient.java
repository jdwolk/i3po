package i3po.inf191.axis2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.namespace.QName;
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
		OMElement operation = fac.createOMElement("basecodeToDefinitionRequest", omNs);
		//operation.addChild(fac.createOMText(operation, "This is where a request argument would go"));
		OMElement definition = fac.createOMElement(new QName("definition"));
		definition.setText("401");
		operation.addChild(definition);
		msgBody.addChild(operation);

		request.addChild(msgHeader);
		request.addChild(rqstHeader);
		request.addChild(msgBody);

		return request;        
	}

	public static OMElement definitionPayload(String filename) {
		OMElement toReturn = null;
		try {
			toReturn = AxisUtils.buildOMElementFromString(readXMLFile(filename));
		}
		catch (XMLStreamException xse) {
			xse.printStackTrace();
		}
		return toReturn;
		
	}

	private static String readXMLFile(String filename) {
		File file = new File(filename);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		StringBuffer buffer = new StringBuffer();

		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			
			Scanner s = new Scanner(bis);

			while (s.hasNextLine()) {
				String thing = s.nextLine();
				buffer.append(thing);
				//System.out.println(thing);
			}

			fis.close();
			bis.close();
			s.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		try {
			OMElement payload = definitionPayload("./src/i3po/inf191/axis2/test.xml");//definitionPayload();
			Options options = new Options();
			options.setTo(targetEPR);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			options.setAction("basecodeToDefinitionRequest"); //MUY IMPORTANTE!!!

			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			OMElement result = sender.sendReceive(payload);

			Iterator<OMElement> children = result.getFirstChildWithName(new QName("message_body")).getChildElements();
			for(Iterator<OMElement> i = children; children.hasNext(); ) {
				OMElement next = i.next();
				if(next.getLocalName().equals("defResponse")) {
					for(Iterator<OMElement> j = next.getChildElements(); j.hasNext(); ) {
						System.out.println(j.next().getText());
					}
				}
			}
			
		} catch (Exception e) { //(XMLStreamException e) {
			e.printStackTrace();
		}
	}

}


