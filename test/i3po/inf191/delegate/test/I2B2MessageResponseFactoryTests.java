package i3po.inf191.delegate.test;


import javax.xml.bind.JAXBElement;

import i3po.inf191.datavo.DEFJAXBUtil;
import i3po.inf191.datavo.I2B2MessageResponseFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.datavo.i2b2message.RequestMessageType;

public class I2B2MessageResponseFactoryTests {

	I2B2MessageResponseFactory imrf;
	
	@Before
	public void setUp() throws Exception {
		imrf = new I2B2MessageResponseFactory();
	}

	@After
	public void tearDown() throws Exception {
		imrf = null;
	}

}
