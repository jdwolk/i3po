package i3po.inf191.messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import i3po.inf191.xsd.DefRequest;
import edu.harvard.i2b2.common.exception.I2B2Exception;
import edu.harvard.i2b2.common.util.jaxb.JAXBUnWrapHelper;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.datavo.i2b2message.BodyType;

/**
 * Unmarshaller for RequestType messages. Will change the name
 * once .xsd changes so it's less confusing.
 * @author jordaniel
 *
 */

//TODO refactor w/ less confusing name once the .xsd is more settled
public class DefRequestMessage extends RequestMessage {
	private static Log log = LogFactory.getLog(DefRequestMessage.class);

	public DefRequestMessage(String requestXml) throws I2B2Exception {
		super(requestXml);
	}

	/**
	 * Function to get RequestType object from i2b2 request message type
	 * 
	 * @return
	 * @throws I2B2Exception
	 */
	public DefRequest getDefRequest() throws I2B2Exception {
		DefRequest reqType = null;
		try {
			JAXBUnWrapHelper helper = new JAXBUnWrapHelper();
			BodyType reqBodyType = requestMsgType.getMessageBody();
			log.info("In RequestTypeMessage.getRequestType(); reqBodyType.getAny() is " + reqBodyType.getAny());
			reqType = (DefRequest) helper.getObjectByClass(reqBodyType.getAny(),
					DefRequest.class);
		} catch (JAXBUtilException e) {
			throw new I2B2Exception("Unwrap error: " + e.getMessage(), e);
		} catch (Exception e) {
			throw new I2B2Exception("Unspecified error: " + e.getMessage());
		}
		return reqType;
	}
}
