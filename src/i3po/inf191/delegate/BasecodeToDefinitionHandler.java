package i3po.inf191.delegate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import i3po.inf191.dao.DAOFactory;
import i3po.inf191.dao.IMODefinitionDAO;
import i3po.inf191.dao.UMLSDefinitionDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import i3po.inf191.util.DefinitionUtil;
import i3po.inf191.xsd.DefResponse;
import i3po.inf191.xsd.Items.Item;

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
		ArrayList<DefResponse> responses = new ArrayList<DefResponse>();
		DefResponse firstResponse = myof.createDefResponse();
		
		try {			
			umlsDefDAO = new DAOFactory().createUMLSDefDAO();
			
			if(encoding != null && !("".equals(encoding))) {				
				if(encoding.equals("ICD9")) {
					log.info("Performing UMLS request(s) from BasecodeToDefinitionHandler");
					if(title == null) {
						title = umlsDefDAO.getICD9Title(code);
					}
					firstResponse.setTitle(title);
					firstResponse.setBasecode(basecode);
					String definition = umlsDefDAO.getICD9Definition(code);
					if(!definition.isEmpty()) {
						firstResponse.setDefinition(definition);
						responses.add(firstResponse);
					}
					else {
						// If nothing found, do IMO search if possible
						responses.addAll(performIMORequest());
					}
				}
				else if(encoding.equals("LOINC")) {
					//handle other encoding types this way
				}
				else {
					throw new I2B2Exception("Basecode type unrecognized");
				}
			}
			else {
				// No basecode provided; either user typed just a title or 
				// dragged item had no basecode.
				// Hand over to IMO if it is enabled
				responses.addAll(performIMORequest());
			}
		}
		catch (Exception sqe) {
			sqe.printStackTrace();
			/*
			firstResponse.setTitle(title == null ? "" : title);
			firstResponse.setBasecode(basecode == null ? "" : basecode);
			firstResponse.setDefinition("");
			responses.add(firstResponse);
			*/
		}
		finally {
			log.info("Adding responses to outgoing XML message");
			for(DefResponse response : responses) {
				bodyType.getAny().add(response);
			}
		}

		assert bodyType != null;
		return bodyType;
	}


	private ArrayList<DefResponse> performIMORequest() throws I2B2Exception {
		ArrayList<DefResponse> toReturn = new ArrayList<DefResponse>();
		
		if(DefinitionUtil.getInstance().isIMOEnabled()) {
			log.info("Performing IMO request from BasecodeToDefinitionHandler");
			
			IMODefinitionDAO imoDefDAO = new DAOFactory().createIMODefDAO();
			List<Item> results;
			//Try lookup by code first, then title
			if(code != null) {
				results = imoDefDAO.getIMOResults(code);
			}
			else if(title != null) {
				results = imoDefDAO.getIMOResults(title);
			}
			else {
				results = new ArrayList<Item>();
			}
			//TODO refactor; Leaky abstractions...
			for(Item item :results ) {
				DefResponse newResponse = new DefResponse();
				newResponse.setTitle(item.getTitle());
				newResponse.setBasecode(item.getKndgCode());
				newResponse.setDefinition(imoDefDAO.definitionFromPayload(item.getSearchpayload()));
				toReturn.add(newResponse);
			}
		}
		return toReturn;
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