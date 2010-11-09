package org.i2b2.xsd.cell.def;

import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Main {

	public static void main(String[] args) {
		testMarshal();
	}
	
	public static void testMarshal() {
		try
		{
			JAXBContext context = JAXBContext.newInstance("org.i2b2.xsd.cell.def");
			System.out.println("context ok");
			ObjectFactory factory = new ObjectFactory();
			System.out.println("object factory ready");
			MessageBodyExample  example = (MessageBodyExample)(factory.createMessageBodyExample());
			
			RequestType type = factory.createRequestType();
			type.setIcd9Code("307.5");
			example.setDefinitionRequest(type);
			System.out.println("tree ready");
			
			Marshaller marshaller= context.createMarshaller();
			System.out.println("marshaller  ready");
			marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			marshaller.marshal(example, new FileOutputStream("test.xml"));

			System.out.println("java tree converted into xml & filed");
		}
		catch(Exception e1)
		{System.out.println(""+e1);    }
	}
}
