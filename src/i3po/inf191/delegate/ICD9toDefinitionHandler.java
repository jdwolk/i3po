package i3po.inf191.delegate;

import java.sql.SQLException;

import i3po.inf191.dao.UMLSDefinitionDAO;
import i3po.inf191.datavo.DEFJAXBUtil;
import i3po.inf191.datavo.MessageFactory;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import i3po.inf191.xsd.RequestType;
import i3po.inf191.xsd.ResponseType;

import edu.harvard.i2b2.common.exception.I2B2Exception;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.core.datavo.pdo.PatientDataType;
import edu.harvard.i2b2.datavo.i2b2message.BodyType;
import edu.harvard.i2b2.datavo.i2b2message.InfoType;
import edu.harvard.i2b2.datavo.i2b2message.ResultStatusType;
import edu.harvard.i2b2.datavo.i2b2message.SecurityType;
import edu.harvard.i2b2.datavo.i2b2message.StatusType;



public class ICD9toDefinitionHandler {
	protected final Log log = LogFactory.getLog(getClass());
	
	private String icd9Code;
	
	/**
	 *  DAO for getting icd9->definition
	 */
	private UMLSDefinitionDAO defDAO;
	
	public ICD9toDefinitionHandler(String icd9Code) {
		if(icd9Code == "")
			throw new RuntimeException("ICD9 code was blank");
			//TODO do Something
		this.icd9Code = icd9Code;
	}
	
	
	public BodyType handleRequest() {
		edu.harvard.i2b2.datavo.i2b2message.ObjectFactory of = new edu.harvard.i2b2.datavo.i2b2message.ObjectFactory();
		BodyType bodyType = of.createBodyType();
		
		i3po.inf191.xsd.ObjectFactory myof = new i3po.inf191.xsd.ObjectFactory();
		ResponseType response = myof.createResponseType();
		
		try {
			defDAO = new UMLSDefinitionDAO();
			String definition = defDAO.getDefinition(icd9Code);
			response.setDefinition(definition);
		}
		catch (Exception sqe) { //SQLException
			//TODO do this for real
			//bodyType.getAny().add
			response.setDefinition(sqe.getLocalizedMessage());
		}
		
		bodyType.getAny().add(response);
		
		assert bodyType != null;
		return bodyType;
	}
	
}