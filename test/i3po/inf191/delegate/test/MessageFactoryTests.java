package i3po.inf191.delegate.test;


import javax.xml.bind.JAXBElement;

import i3po.inf191.datavo.DEFJAXBUtil;
import i3po.inf191.datavo.MessageFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.datavo.i2b2message.RequestMessageType;

public class MessageFactoryTests {

	MessageFactory imrf;
	
	@Before
	public void setUp() throws Exception {
		imrf = new MessageFactory();
	}

	@After
	public void tearDown() throws Exception {
		imrf = null;
	}

}
