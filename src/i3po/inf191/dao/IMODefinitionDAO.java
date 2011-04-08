package i3po.inf191.dao;

import i3po.inf191.util.DefinitionUtil;
import i3po.inf191.xsd.Items;
import i3po.inf191.xsd.Items.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;

import edu.harvard.i2b2.common.exception.I2B2Exception;


public class IMODefinitionDAO {
	private DefinitionUtil util = DefinitionUtil.getInstance();
	private Log log = LogFactory.getLog(getClass());

	public IMODefinitionDAO() throws I2B2Exception {
		if(! util.isIMOEnabled()) {
			throw new I2B2Exception("IMO is not enabled; cannot create an instance of IMODefinitionDAO");
		}
	}

	public List<Item> getIMOResults(String query) {
		String results = runIMOQuery(query);
		log.info("IMO results for query '" + query + "':\n" + results);	
		return unMarshallIMOQuery(results);
	}


	private String runIMOQuery(String query) {
		Socket imoSocket = null;
		PrintWriter out = null;
		String result = "";

		try {
			imoSocket = new Socket(util.getIMOUrl(), util.getIMOPort());

			out = new PrintWriter(imoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(imoSocket.getInputStream(),
					"US-ASCII"));
			InputStream in2 = imoSocket.getInputStream();

			out.println("search^25|5|1^" + query + "^0123456789abcdef");
			int L = in2.read() * (256 * 256 * 256) + in2.read() * (256 * 256) + in2.read() * (256) +
			in2.read();
			char res[] = new char[L];
			for (int i = 0; i < L; i++)
				res[i] = (char)in.read();
			out.close();
			in.close();
			in2.close();
			result = new String(res, 0, L);
		}
		catch(ConnectException e) {
			log.error("Could not connect to IMO; make sure that the url and port are correct, and that " +
			"this server is on the IMO whitelist.");
			e.printStackTrace();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return result;
	}


	private List<Item> unMarshallIMOQuery(String queryResults) {
		List<Item> toReturn = null;
		log.info("Unmarshalling IMO results");
		try {
			JAXBContext jc = JAXBContext.newInstance( "i3po.inf191.xsd" ); 
			Unmarshaller u = jc.createUnmarshaller();
			Items itemsWrapper = (Items) u.unmarshal(new InputSource(new StringReader(queryResults)));
			toReturn = itemsWrapper.getItem();
			log.info(toReturn.size() + " definitions found via IMO");
		}
		catch (JAXBException e) {
			log.error("JAXBException while unmarshalling IMO query: " + e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	
	public String definitionFromPayload(String payload) {
		Pattern p = Pattern.compile(".*\\s-\\s\\w{1,3}\\.\\w{1,2}\\s-\\s(.+)");
		Matcher m = p.matcher(payload);
		if(m.find()) {
			//log.info("Definition found: " + m.group(1));
			return m.group(1);
		}
		else {
			log.error("Regex was off while parsing IMO payload: " + payload);
			return payload;
		}
	}
}
