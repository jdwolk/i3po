package i3po.inf191.delegate;

import java.io.StringWriter;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.harvard.i2b2.common.exception.I2B2Exception;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.datavo.i2b2message.RequestMessageType;
import edu.harvard.i2b2.datavo.i2b2message.ResponseMessageType;

import i3po.inf191.datavo.DEFJAXBUtil;


/**
 * Class to delegate i2b2 requests to appropriate {@link RequestHandler} Class
 * unwraps i2b2 request message and based on the request type, the request will
 * be delegated to appropriate request handler by calling handleRequest function. The
 * return value from execute function sent to I2B2MessageResponseFactory to be sent
 * back to the client 
 * 
 * adapted from edu.harvard.i2b2.crc.delegate.RequestHandlerDelegate
 */
public abstract class RequestDelegate {
	/** log **/
	protected final Log log = LogFactory.getLog(getClass());

	public static final String ERROR_TYPE = "ERROR";
	public static final String DONE_TYPE = "DONE";

	/**
	 * Function to delegate request to appropriate request handler class and
	 * passes back the response message output back to client
	 * 
	 * @param requestXml
	 * @return response message xml
	 * @throws I2B2Exception
	 */
	public abstract String delegateRequest(String requestXml)
	throws I2B2Exception;

	/**
	 * Function to unmarshall i2b2 request message type
	 * 
	 * @param requestXml
	 * @return RequestMessageType
	 * @throws JAXBUtilException
	 */
	protected RequestMessageType getI2B2RequestMessageType(String requestXml)
	throws JAXBUtilException {
		JAXBUtil jaxbUtil = DEFJAXBUtil.getJAXBUtil();
		JAXBElement jaxbElement = jaxbUtil.unMashallFromString(requestXml);
		RequestMessageType requestMessageType = 
			(RequestMessageType) jaxbElement.getValue();

		return requestMessageType;
	}

	/**
	 * Function marshall i2b2 response message type
	 * 
	 * @param responseMessageType
	 * @return
	 */
	protected String getResponseString(ResponseMessageType responseMessageType) {
		StringWriter strWriter = new StringWriter();

		try {
			edu.harvard.i2b2.datavo.i2b2message.ObjectFactory of = new edu.harvard.i2b2.datavo.i2b2message.ObjectFactory();
			JAXBUtil jaxbUtil = DEFJAXBUtil.getJAXBUtil();
			jaxbUtil.marshaller(of.createResponse(responseMessageType), strWriter);
		} catch (JAXBUtilException e) {
			log.error("Error while generating response message" + e.getMessage());
		}

		return strWriter.toString();
	}

}
