package i3po.inf191.delegate;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import i3po.inf191.xsd.DefRequest;

import edu.harvard.i2b2.datavo.i2b2message.BodyType;
import edu.harvard.i2b2.datavo.i2b2message.StatusType;

import edu.harvard.i2b2.common.exception.I2B2Exception;

import i3po.inf191.datavo.MessageFactory;
import i3po.inf191.messages.DefRequestMessage;

public class BasecodeToDefinitionDelegate extends RequestDelegate{
	/** log **/
	protected final Log log = LogFactory.getLog(getClass());

	public BasecodeToDefinitionDelegate() {
		log.info("BasecodeToDefinitionDelegate created");
	}

	/**
	 * @see edu.harvard.i2b2.RequestDelegate.delegate.RequestHandlerDelegate#handleRequest(java.lang.String)
	 */
	public String delegateRequest(String requestXml) throws I2B2Exception {
		log.info("Inside delegateRequest for BasecodeToDefinitionDelegate");
		String response = null;
		
		if (requestXml == null) {
            log.error("Incoming request is null");
            throw new I2B2Exception("Incoming request is null");
        }
		
		try {
			DefRequest defReq = new DefRequestMessage(requestXml).getDefRequest();
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("title", defReq.getTitle());
			params.put("basecode", defReq.getBasecode());
			
			log.info("Title: " + params.get("title"));
			log.info("Basecode: " + params.get("basecode"));
			
			BasecodeToDefinitionHandler handler = new BasecodeToDefinitionHandler(params);
			BodyType bodyType = handler.handleRequest();
			
			if (bodyType == null) {
				log.error("null value in body type");
				throw new I2B2Exception("null value in body type");
			}

			//TODO REALLY process based on actual info in body type, message header, etc.
			StatusType procStatus = new StatusType();
			procStatus.setType("DONE");
			response = MessageFactory.buildResponseMessage(
					requestXml, procStatus, bodyType);
			
		}
		catch (I2B2Exception e) {
			e.printStackTrace();
		}
		catch (edu.harvard.i2b2.common.util.jaxb.JAXBUtilException e) {
			log.error("In delegateRequest of BasecodeToDefinitionRequest: " + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
