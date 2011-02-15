package i3po.inf191.messages;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.harvard.i2b2.common.exception.I2B2Exception;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.datavo.i2b2message.MessageHeaderType;
import edu.harvard.i2b2.datavo.i2b2message.RequestMessageType;

import i3po.inf191.datavo.DEFJAXBUtil;

/**
 * Abstract base class for other message types. Handles initial
 * unmarshalling and casting of xml.
 * 
 * @author jordaniel
 *
 */
public abstract class RequestMessage {
	private static Log log = LogFactory.getLog(RequestMessage.class);

	RequestMessageType requestMsgType = null;

	public RequestMessage(String requestXml) throws I2B2Exception {
		try {
			JAXBElement jaxbElement = DEFJAXBUtil.getJAXBUtil().unMashallFromString(requestXml);

			if (jaxbElement == null) {
				log.error("Null value from unmarshall: " + requestXml);
				throw new I2B2Exception(
						"Null value from unmarshall : " + requestXml);
			}

			this.requestMsgType = (RequestMessageType) jaxbElement.getValue();
		} catch (JAXBUtilException e) {
			log.error(e.getMessage(), e);
			throw new I2B2Exception("Umarshaller error: " + e.getMessage() +
					requestXml, e);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new I2B2Exception("Unspecified error: " + e.getMessage() +
					requestXml, e);
		}
	}


	public RequestMessageType getRequestMessageType() { 
		return requestMsgType;
	}

	public MessageHeaderType getMessageHeaderType() {
		return requestMsgType.getMessageHeader();
	}
}
