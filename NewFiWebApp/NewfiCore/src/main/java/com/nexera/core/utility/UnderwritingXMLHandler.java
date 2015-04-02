package com.nexera.core.utility;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.lqb.UnderwritingConditionResponseVO;

public class UnderwritingXMLHandler extends DefaultHandler {

	private UnderwritingConditionResponseVO underwritingConditionVO;
	private List<UnderwritingConditionResponseVO> underwritingConditionResponseVOList = new ArrayList<UnderwritingConditionResponseVO>();

	private static final Logger LOG = LoggerFactory
	        .getLogger(UnderwritingXMLHandler.class);

	boolean field = false;
	String tempVal = "";

	@Override
	public void startElement(String uri, String localName, String qName,
	        Attributes attributes) throws SAXException {

		LOG.info("StartElement  qName # " + qName);

		if ("field".equals(qName)) {
			underwritingConditionVO = new UnderwritingConditionResponseVO();
			underwritingConditionVO.setFieldId(attributes.getValue("id"));
			field = true;

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	        throws SAXException {
		// push in userList
		if ("field".equals(qName)) {
			underwritingConditionResponseVOList.add(underwritingConditionVO);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
	        throws SAXException {
		tempVal = new String(ch, start, length);
		if (field)
			underwritingConditionVO.setFieldValue(tempVal);

	}

	public List<UnderwritingConditionResponseVO> getUnderWriConditionResponseList() {
		return underwritingConditionResponseVOList;
	}

}
