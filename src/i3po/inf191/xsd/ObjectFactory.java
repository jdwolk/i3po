//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.19 at 03:38:01 AM PST 
//


package i3po.inf191.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the i3po.inf191.xsd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DefinitionResponse_QNAME = new QName("http://www.i3po/inf191/xsd/", "definition_response");
    private final static QName _DefinitionRequest_QNAME = new QName("http://www.i3po/inf191/xsd/", "definition_request");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: i3po.inf191.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link MessageBodyExample }
     * 
     */
    public MessageBodyExample createMessageBodyExample() {
        return new MessageBodyExample();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.i3po/inf191/xsd/", name = "definition_response")
    public JAXBElement<ResponseType> createDefinitionResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_DefinitionResponse_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.i3po/inf191/xsd/", name = "definition_request")
    public JAXBElement<RequestType> createDefinitionRequest(RequestType value) {
        return new JAXBElement<RequestType>(_DefinitionRequest_QNAME, RequestType.class, null, value);
    }

}
