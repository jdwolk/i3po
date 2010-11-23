package i3po.inf191.delegate.test;

import static org.junit.Assert.*;
import i3po.inf191.axis2.DefinitionService;
import i3po.inf191.delegate.ICD9toDefinitionHandler;
import i3po.inf191.util.DefinitionUtil;
import i3po.inf191.xsd.ResponseType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ICD9toDefHandlerTests {
	static DefinitionUtil.CONNECTIONS oldConnType = 
		DefinitionUtil.getInstance().getConnectionType();
	
	ICD9toDefinitionHandler handler;
	String icd9 = "401";

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
		handler = new ICD9toDefinitionHandler(icd9);
	}

	@After
	public void tearDown() throws Exception {
		handler = null;
	}

	@Test
	public final void testHandleRequest() {
		ResponseType blargh = (ResponseType) handler.handleRequest().getAny().get(0);
		assertEquals("getDefinition() did not return the correct definition", 
				"hypertension occurring without preexisting renal disease or known organic cause.",
				blargh.getDefinition());
	}

}
