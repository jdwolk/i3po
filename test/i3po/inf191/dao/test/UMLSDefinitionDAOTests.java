package i3po.inf191.dao.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import i3po.inf191.dao.DEFDAO;
import i3po.inf191.dao.UMLSDefinitionDAO;
import i3po.inf191.delegate.ICD9toDefinitionHandler;
import i3po.inf191.util.DefinitionUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UMLSDefinitionDAOTests {
	
	private UMLSDefinitionDAO defDAO = null;
	
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
		defDAO = new UMLSDefinitionDAO();
	}

	@After
	public void tearDown() throws Exception {
		defDAO = null;
	}

	@Test
	public final void testGetDefinition() {
		try {
		assertEquals("getDefinition() did not return the correct definition", 
				"hypertension occurring without preexisting renal disease or known organic cause.",
				defDAO.getDefinition("401"));
		} catch (SQLException sqe) {
			fail(sqe.getLocalizedMessage());
		}
	}

}
