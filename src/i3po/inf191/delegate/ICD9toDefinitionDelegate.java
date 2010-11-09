package i3po.inf191.delegate;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i2b2.xsd.cell.def.RequestType;

import edu.harvard.i2b2.common.util.jaxb.JAXBUnWrapHelper;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.datavo.i2b2message.RequestMessageType;
import edu.harvard.i2b2.datavo.i2b2message.BodyType;
import edu.harvard.i2b2.datavo.i2b2message.StatusType;

import edu.harvard.i2b2.common.exception.I2B2Exception;

import i3po.inf191.datavo.I2B2MessageResponseFactory;
import i3po.inf191.datavo.DEFJAXBUtil;

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
		JAXBUtil jaxbUtil = DEFJAXBUtil.getJAXBUtil();
		JAXBUnWrapHelper helper = new JAXBUnWrapHelper();

		try {
			JAXBElement jaxbElement = jaxbUtil.unMashallFromString(requestXml);
			RequestMessageType requestMsgType = (RequestMessageType) jaxbElement.getValue();
			BodyType reqBodyType = requestMsgType.getMessageBody();
			//log.info("BodyType.getAny() -> " + reqBodyType.getAny());
			RequestType reqType = (RequestType) helper.getObjectByClass(reqBodyType.getAny(),
					RequestType.class);
			
			//log.info("RequestType.getIcd9Code() -> " + reqType.getIcd9Code());
						
			ICD9toDefinitionHandler handler = new ICD9toDefinitionHandler();
			//BodyType bodyType = handler.handleRequest(reqType.getIcd9Code());
			BodyType bodyType = handler.handleRequest("400");
			
			if (bodyType == null) {
				log.error("null value in body type");
				throw new I2B2Exception("null value in body type");
			}

			//TODO REALLY process based on actual info in body type, message header, etc.
			StatusType procStatus = new StatusType();
			procStatus.setType("DONE");
			response = I2B2MessageResponseFactory.buildResponseMessage(
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
