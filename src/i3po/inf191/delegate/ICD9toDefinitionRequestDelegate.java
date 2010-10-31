package i3po.inf191.delegate;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.datavo.i2b2message.RequestMessageType;
import edu.harvard.i2b2.datavo.i2b2message.BodyType;
import edu.harvard.i2b2.datavo.i2b2message.StatusType;

import edu.harvard.i2b2.common.exception.I2B2Exception;

import i3po.inf191.datavo.I2B2MessageResponseFactory;
import i3po.inf191.datavo.DEFJAXBUtil;

public class ICD9toDefinitionRequestDelegate extends RequestHandlerDelegate{
	/** log **/
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * @see edu.harvard.i2b2.crc.delegate.RequestHandlerDelegate#handleRequest(java.lang.String)
	 */
	public String handleRequest(String requestXml) throws I2B2Exception {
		//PdoQryHeaderType headerType = null;
		String response = null;
		JAXBUtil jaxbUtil = DEFJAXBUtil.getJAXBUtil();

		try {
			JAXBElement jaxbElement = jaxbUtil.unMashallFromString(requestXml);
			RequestMessageType requestMessageType = (RequestMessageType) jaxbElement.getValue();
			BodyType bodyType = requestMessageType.getMessageBody();

			if (bodyType == null) {
				log.error("null value in body type");
				throw new I2B2Exception("null value in body type");
			}
			
			StatusType procStatus = new StatusType();
			procStatus.setType("DONE");
			//response = "!!!!!!!HELLO THERE!!!!!!!!";
			response = I2B2MessageResponseFactory.buildResponseMessage(
					requestXml, procStatus, bodyType);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
