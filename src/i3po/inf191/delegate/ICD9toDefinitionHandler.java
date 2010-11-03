package i3po.inf191.delegate;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.harvard.i2b2.datavo.i2b2message.BodyType;
import edu.harvard.i2b2.datavo.i2b2message.InfoType;
import edu.harvard.i2b2.datavo.i2b2message.ResultStatusType;
import edu.harvard.i2b2.datavo.i2b2message.SecurityType;
import edu.harvard.i2b2.datavo.i2b2message.StatusType;



public class ICD9toDefinitionHandler {
	protected final Log log = LogFactory.getLog(getClass());


	//TODO make real implementation
	public BodyType handleRequest() {

		edu.harvard.i2b2.datavo.i2b2message.ObjectFactory of = new edu.harvard.i2b2.datavo.i2b2message.ObjectFactory();
		BodyType newBodyType = of.createBodyType();
		ResultStatusType rst = of.createResultStatusType();
		StatusType st = new StatusType();
		st.setType("FUCKED");
		rst.setStatus(st);
		newBodyType.getAny().add(rst);
		
		return newBodyType;
	}
	
	@XmlRootElement
	private class FooBarType {
		public String value;
		public FooBarType(String val) {value = val;}
		
		public void setValue(String val) {
			value = val;
		}
	}
}