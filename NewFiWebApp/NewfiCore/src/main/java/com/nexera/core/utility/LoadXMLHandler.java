package com.nexera.core.utility;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.lqb.LoadResponseVO;

public class LoadXMLHandler extends DefaultHandler {

	private static final Logger LOG = LoggerFactory
	        .getLogger(LoadXMLHandler.class);

	private List<LoadResponseVO> loadResponseVOList = new ArrayList<LoadResponseVO>();
	private LoadResponseVO loadResponseVO;

	boolean field = false;
	String tempVal = "";

	@Override
	public void startElement(String uri, String localName, String qName,
	        Attributes attributes) throws SAXException {

		LOG.info("StartElement  qName # " + qName);

		if ("field".equals(qName)) {
			loadResponseVO = new LoadResponseVO();
			loadResponseVO.setFieldId(attributes.getValue("id"));
			field = true;

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	        throws SAXException {
		// push in userList
		if ("field".equals(qName)) {
			loadResponseVOList.add(loadResponseVO);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
	        throws SAXException {
		tempVal = new String(ch, start, length);
		if (field)
			loadResponseVO.setFieldValue(tempVal);

	}

	public List<LoadResponseVO> getLoadResponseVOList() {
		return loadResponseVOList;
	}

}