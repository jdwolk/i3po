package i3po.inf191.datavo;

import java.util.List;

import edu.harvard.i2b2.common.util.jaxb.JAXBUtil;
//import edu.harvard.i2b2.crc.util.QueryProcessorUtil;

/**
 * Factory class to create jaxb context Since jaxb context is tread safe, only
 * one instance is created for this cell. The package used for jaxb context is
 * read from spring config file $Id: CRCJAXBUtil.java,v 1.6 2007/09/11 20:05:40
 * rk903 Exp $
 * 
 * @author rkuttan
 */
public class DEFJAXBUtil {
	private static edu.harvard.i2b2.common.util.jaxb.JAXBUtil jaxbUtil = null;
	private static edu.harvard.i2b2.common.util.jaxb.JAXBUtil queryDefjaxbUtil = null;
	private static edu.harvard.i2b2.common.util.jaxb.JAXBUtil analysisDefjaxbUtil = null;

	private DEFJAXBUtil() {
	}

	@SuppressWarnings("unchecked")
	public static edu.harvard.i2b2.common.util.jaxb.JAXBUtil getJAXBUtil() {
		if (jaxbUtil == null) {
			String[] jaxbPackageNameArray = {"edu.harvard.i2b2.datavo.i2b2message"};
			jaxbUtil = new edu.harvard.i2b2.common.util.jaxb.JAXBUtil(
					jaxbPackageNameArray);
		}

		return jaxbUtil;
	}
/*
	@SuppressWarnings("unchecked")
	public static edu.harvard.i2b2.common.util.jaxb.JAXBUtil getQueryDefJAXBUtil() {
		if (queryDefjaxbUtil == null) {
			queryDefjaxbUtil = new edu.harvard.i2b2.common.util.jaxb.JAXBUtil(
					edu.harvard.i2b2.crc.datavo.setfinder.query.QueryDefinitionType.class);
		}
		return queryDefjaxbUtil;
	}

	@SuppressWarnings("unchecked")
	public static edu.harvard.i2b2.common.util.jaxb.JAXBUtil getAnalysisDefJAXBUtil() {
		if (analysisDefjaxbUtil == null) {
			analysisDefjaxbUtil = new edu.harvard.i2b2.common.util.jaxb.JAXBUtil(
					edu.harvard.i2b2.crc.datavo.setfinder.query.AnalysisDefinitionRequestType.class);
		}
		return analysisDefjaxbUtil;
	}
	*/
}

