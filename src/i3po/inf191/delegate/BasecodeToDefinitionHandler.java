package i3po.inf191.delegate;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import i3po.inf191.dao.DAOFactory;
import i3po.inf191.dao.UMLSDefinitionDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import i3po.inf191.xsd.DefResponse;

import edu.harvard.i2b2.common.exception.I2B2Exception;
import edu.harvard.i2b2.datavo.i2b2message.BodyType;


public class BasecodeToDefinitionHandler {
	protected final Log log = LogFactory.getLog(getClass());
	
	private String title;
	private String basecode;
	private String code;
	private String encoding; //i.e. ICD9, LOINC, etc. Derived from basecode.
	
	// Data Access Object for querying UMLS
	private UMLSDefinitionDAO umlsDefDAO;
	
	public BasecodeToDefinitionHandler(HashMap<String, String> params) {
		log.info("In BasecodeToDefinitionHandler");
		title = params.get("title");
		basecode = params.get("basecode");
		setEncodingAndCode(basecode);
		log.info("title: " + title + "\tencoding: " + encoding + "\tcode: " + code);
	}
	
	
	public BodyType handleRequest() {
		edu.harvard.i2b2.datavo.i2b2message.ObjectFactory of = new edu.harvard.i2b2.datavo.i2b2message.ObjectFactory();
		BodyType bodyType = of.createBodyType();
		
		i3po.inf191.xsd.ObjectFactory myof = new i3po.inf191.xsd.ObjectFactory();
		DefResponse response = myof.createDefResponse();
		
		try {			
			umlsDefDAO = new DAOFactory().createUMLSDefDAO();
			
			if(encoding != null) {				
				if(encoding.equals("ICD9")) {
					if(title == null) {
						title = umlsDefDAO.getICD9Title(code);
					}
					response.setTitle(title);
					response.setBasecode(basecode);
					response.setDefinition(umlsDefDAO.getICD9Definition(code));
				}
				else if(encoding.equals("LOINC")) {
					//handle other encoding types this way
				}
				else {
					throw new I2B2Exception("Basecode type unrecognized");
				}
			}
			else { //No basecode provided; either user typed just a title or dragged item had no basecode
				// This is where we'd hand over to IMO...
			}
		}
		catch (Exception sqe) {
			//bodyType.getAny().add
			response.setTitle(title == null ? "" : title);
			response.setBasecode(basecode == null ? "" : basecode);
			response.setDefinition(sqe.getLocalizedMessage());
		}
		
		bodyType.getAny().add(response);
		
		assert bodyType != null;
		return bodyType;
	}
	
	
	/**
	 * Parses a basecode (i.e. ICD9:740.2) into an encoding (ICD9) and value (740.2)
	 */
	private void setEncodingAndCode(String basecode) {
		log.info("basecode to parse: " + basecode);

		Pattern regex = Pattern.compile("(.+):(.+)");
		Matcher m = regex.matcher(basecode);
		
		if(m.find()) {
			this.encoding = m.group(1);
			this.code = m.group(2);
		}
		else {
			log.info("No encoding or code found when parsing basecode");
		}
	}
	
}