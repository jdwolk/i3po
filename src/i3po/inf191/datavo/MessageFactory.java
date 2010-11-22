package i3po.inf191.datavo;

import java.io.StringWriter;
import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
import edu.harvard.i2b2.common.util.jaxb.JAXBUtilException;
import edu.harvard.i2b2.datavo.i2b2message.*;

public class MessageFactory {

	/** log * */
	protected final static Log log = LogFactory
	.getLog(MessageFactory.class);

	/**
	 * Function to build response message type using given bodytype and request
	 * xml
	 * 
	 * @param requestXml
	 * @param bodyType
	 * @return i2b2 response message xml
	 * @throws JAXBUtilException
	 */
	public static String buildResponseMessage(String requestXml,
			StatusType statusType, BodyType bodyType, boolean splCharFilterFlag)
	throws JAXBUtilException {
		JAXBUtil util = DEFJAXBUtil.getJAXBUtil();

		MessageHeaderType headerType = new MessageHeaderType();
		ApplicationType appType = new ApplicationType();
		appType.setApplicationName("i3po.inf191");
		appType.setApplicationVersion("1.5"); 

		if (requestXml != null) {
			RequestMessageType requestMsgType = getI2B2RequestMessageType(requestXml);
			MessageHeaderType clientMessageHeader = requestMsgType.getMessageHeader();
			// reverse sending and receiving app
			if (clientMessageHeader != null) {
				ApplicationType sendingApp = clientMessageHeader
				.getSendingApplication();
				headerType.setSendingApplication(appType);
				headerType.setReceivingApplication(sendingApp);
				//messageHeader.setProjectId(clientMessageHeader.getProjectId());
			}
		} else {   
			headerType = new MessageHeaderType();
			headerType.setSendingApplication(appType);
		}

		headerType.setI2B2VersionCompatible(new BigDecimal("1.1"));
		headerType.setHl7VersionCompatible(new BigDecimal("2.4"));

		MessageControlIdType messageControlIdType = new MessageControlIdType();
		messageControlIdType.setInstanceNum(1);
		headerType.setMessageControlId(messageControlIdType);

		FacilityType facility = new FacilityType();
		facility.setFacilityName("i2b2 Hive");
		headerType.setSendingFacility(facility);
		headerType.setReceivingFacility(facility);

		// :TODO statusType.setValue(sessionId);
		PollingUrlType pollingType = new PollingUrlType();
		pollingType.setIntervalMs(100);

		// :TODO value come from property file
		// pollingType.setValue("http://localhost:8080/QueryProcessor/getResult");
		ResultStatusType resultStatusType = new ResultStatusType();
		resultStatusType.setStatus(statusType);
		resultStatusType.setPollingUrl(pollingType);

		InfoType infoType = new InfoType();
		// :TODO value come from property file
		// infoType.setUrl("http://localhost:8080/QueryProcessor/getStatus");
		infoType.setValue("Log information");

		ResponseHeaderType responseHeader = new ResponseHeaderType();
		responseHeader.setResultStatus(resultStatusType);
		responseHeader.setInfo(infoType);

		ResponseMessageType responseMessageType = new ResponseMessageType();
		responseMessageType.setMessageHeader(headerType);
		responseMessageType.setResponseHeader(responseHeader);
		responseMessageType.setMessageBody(bodyType);

		edu.harvard.i2b2.datavo.i2b2message.ObjectFactory of = new edu.harvard.i2b2.datavo.i2b2message.ObjectFactory();
		StringWriter strWriter = new StringWriter();
		util.marshaller(of.createResponse(responseMessageType), strWriter);//,
		//splCharFilterFlag);
		return strWriter.toString();
	}

	/**
	 * Function to build response message type using given bodytype and request
	 * xml
	 * 
	 * @param requestXml
	 * @param bodyType
	 * @return i2b2 response message xml
	 * @throws JAXBUtilException
	 */
	public static String buildResponseMessage(String requestXml,
			StatusType statusType, BodyType bodyType) throws JAXBUtilException {
		return buildResponseMessage(requestXml, statusType, bodyType, false);
	}
	
	//public static String buildErrorResponse()

	/**
	 * Function to unmarshall i2b2 request message type
	 * 
	 * @param requestXml
	 * @return RequestMessageType
	 * @throws JAXBUtilException
	 */
	private static RequestMessageType getI2B2RequestMessageType(
			String requestXml) throws JAXBUtilException {
		JAXBUtil jaxbUtil = DEFJAXBUtil.getJAXBUtil();
		JAXBElement jaxbElement = jaxbUtil.unMashallFromString(requestXml);
		RequestMessageType requestMessageType = (RequestMessageType) jaxbElement
		.getValue();

		log.info("REQUEST MESSAGE TYPE: " + requestMessageType.toString());

		return requestMessageType;
	}

	/**
	 * Function marshall i2b2 response message type
	 * 
	 * @param responseMessageType
	 * @return
	 */
	public static String getResponseString(
		ResponseMessageType responseMessageType) {
		StringWriter strWriter = new StringWriter();

		try {
			edu.harvard.i2b2.datavo.i2b2message.ObjectFactory of = new edu.harvard.i2b2.datavo.i2b2message.ObjectFactory();
			JAXBUtil jaxbUtil = DEFJAXBUtil.getJAXBUtil();
			jaxbUtil.marshaller(of.createResponse(responseMessageType),
					strWriter);
		} catch (JAXBUtilException e) {
			log.error("Error while generating response message"
					+ e.getMessage());
		}

		return strWriter.toString();
	}

}

