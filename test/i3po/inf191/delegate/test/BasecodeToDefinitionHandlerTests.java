package i3po.inf191.delegate.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import i3po.inf191.delegate.BasecodeToDefinitionHandler;
import i3po.inf191.util.DefinitionUtil;
import i3po.inf191.xsd.DefResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasecodeToDefinitionHandlerTests {
	static DefinitionUtil.CONNECTIONS oldConnType = 
		DefinitionUtil.getInstance().getConnectionType();
	
	BasecodeToDefinitionHandler handler;
	String icd9 = "ICD9:401";

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
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("basecode", icd9);
		handler = new BasecodeToDefinitionHandler(params);
	}

	@After
	public void tearDown() throws Exception {
		handler = null;
	}

	@Test
	public final void testHandleRequest() {
		DefResponse blargh = (DefResponse) handler.handleRequest().getAny().get(0);
		assertEquals("getDefinition() did not return the correct definition", 
				"hypertension occurring without preexisting renal disease or known organic cause.",
				blargh.getDefinition());
	}

}
