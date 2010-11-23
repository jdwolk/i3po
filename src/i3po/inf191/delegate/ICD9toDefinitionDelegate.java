package i3po.inf191.delegate;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import i3po.inf191.xsd.RequestType;

import edu.harvard.i2b2.common.util.jaxb.JAXBUnWrapHelper;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.datavo.i2b2message.RequestMessageType;
import edu.harvard.i2b2.datavo.i2b2message.BodyType;
import edu.harvard.i2b2.datavo.i2b2message.StatusType;

import edu.harvard.i2b2.common.exception.I2B2Exception;

import i3po.inf191.datavo.MessageFactory;
import i3po.inf191.datavo.DEFJAXBUtil;
import i3po.inf191.messages.RequestTypeMessage;

public class ICD9toDefinitionDelegate extends RequestDelegate{
	/** log **/
	protected final Log log = LogFactory.getLog(getClass());

	public ICD9toDefinitionDelegate() {
		log.info("ICD9toDefinitionRequestDelegate created");
	}

	/**
	 * @see edu.harvard.i2b2.RequestDelegate.delegate.RequestHandlerDelegate#handleRequest(java.lang.String)
	 */
	public String delegateRequest(String requestXml) throws I2B2Exception {
		log.info("Inside delegateRequest for ICD9toDefinitionDelegate");
		String response = null;
		
		if (requestXml == null) {
            log.error("Incoming PFT request is null");
            throw new I2B2Exception("Incoming PFT request is null");
        }
		
		JAXBUtil jaxbUtil = DEFJAXBUtil.getJAXBUtil();
		JAXBUnWrapHelper helper = new JAXBUnWrapHelper();
		
		try {
			RequestType reqType = new RequestTypeMessage(requestXml).getRequestType();
			
			log.info("Requested icd9 code: " + reqType.getIcd9Code());
			ICD9toDefinitionHandler handler = new ICD9toDefinitionHandler(reqType.getIcd9Code());
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
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
