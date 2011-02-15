package i3po.inf191.axis2.test;

import static org.junit.Assert.*;
import i3po.inf191.axis2.DefinitionService;
import i3po.inf191.axis2.TestDefinitionClient;
import i3po.inf191.delegate.BasecodeToDefinitionHandler;
import i3po.inf191.util.DefinitionUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefinitionServiceTests {
	DefinitionService service;
	
	static DefinitionUtil.CONNECTIONS oldConnType = 
		DefinitionUtil.getInstance().getConnectionType();

	@BeforeClass
	public static void setUpAll() throws Exception {
		DefinitionUtil.getInstance().setConnectionType(DefinitionUtil.CONNECTIONS.LOCAL);
	}
	
	@AfterClass
	public static void restore() throws Exception {
		DefinitionUtil.getInstance().setConnectionType(oldConnType);
	}

	@Before
	public void setUp() throws Exception {
		service = new DefinitionService();
	}

	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
	public void testIcd9ToDefinitionRequest() {
		TestDefinitionClient tdc = new TestDefinitionClient();
		service.basecodeToDefinitionRequest(tdc.definitionPayload("/home/jordaniel/workspaces/i2b2/i3po/src/i3po/inf191/axis2/test.xml"));
	}

}
