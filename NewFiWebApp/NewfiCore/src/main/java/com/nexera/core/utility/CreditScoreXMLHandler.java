package com.nexera.core.utility;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.lqb.CreditScoreResponseVO;

public class CreditScoreXMLHandler extends DefaultHandler {

	private CreditScoreResponseVO creditScoreResponseVO;
	private List<CreditScoreResponseVO> creditScoreResponseVOList = new ArrayList<CreditScoreResponseVO>();

	private static final Logger LOG = LoggerFactory
	        .getLogger(CreditScoreXMLHandler.class);

	boolean field = false;
	String tempVal = "";

	@Override
	public void startElement(String uri, String localName, String qName,
	        Attributes attributes) throws SAXException {

		LOG.info("StartElement  qName # " + qName);

		if ("field".equals(qName)) {
			creditScoreResponseVO = new CreditScoreResponseVO();
			creditScoreResponseVO.setFieldId(attributes.getValue("id"));
			field = true;

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	        throws SAXException {
		// push in userList
		if ("field".equals(qName)) {
			creditScoreResponseVOList.add(creditScoreResponseVO);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
	        throws SAXException {
		tempVal = new String(ch, start, length);
		if (field)
			creditScoreResponseVO.setFieldValue(tempVal);

	}

	public List<CreditScoreResponseVO> getCreditScoreResponseList() {
		return creditScoreResponseVOList;
	}

}
