package i3po.inf191.delegate;

import edu.harvard.i2b2.datavo.i2b2message.BodyType;

public abstract class RequestHandler {
	public RequestHandler() {}

	public abstract BodyType handleRequest();
	//public abstract BodyType handleRequest(String s);

}
