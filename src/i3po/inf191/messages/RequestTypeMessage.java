package i3po.inf191.messages;

import i3po.inf191.xsd.RequestType;
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
public class RequestTypeMessage extends RequestMessage {

	public RequestTypeMessage(String requestXml) throws I2B2Exception {
		super(requestXml);
	}

	/**
	 * Function to get RequestType object from i2b2 request message type
	 * 
	 * @return
	 * @throws I2B2Exception
	 */
	public RequestType getRequestType() throws I2B2Exception {
		RequestType reqType = null;
		try {
			JAXBUnWrapHelper helper = new JAXBUnWrapHelper();
			BodyType reqBodyType = requestMsgType.getMessageBody();
			reqType = (RequestType) helper.getObjectByClass(reqBodyType.getAny(),
					RequestType.class);
		} catch (JAXBUtilException e) {
			throw new I2B2Exception("Unwrap error: " + e.getMessage(), e);
		}
		return reqType;
	}
}
