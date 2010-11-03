package i3po.inf191.axis2.test;

import static org.junit.Assert.*;
import i3po.inf191.axis2.DefinitionService;
import i3po.inf191.axis2.TestDefinitionClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefinitionServiceTests {
	DefinitionService service;

	@Before
	public void setUp() throws Exception {
		service = new DefinitionService();
	}

	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
	public final void testIcd9ToDefinitionRequest() {
		TestDefinitionClient tdc = new TestDefinitionClient();
		service.icd9ToDefinitionRequest(tdc.definitionPayload());
	}

}
