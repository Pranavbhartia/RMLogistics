package com.nexera.core.utility;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.lqb.ModifiedLoanListResponseVO;

public class ModifiedLoanListXMLHandler extends DefaultHandler {

	private static final Logger LOG = LoggerFactory
	        .getLogger(ModifiedLoanListXMLHandler.class);

	private List<ModifiedLoanListResponseVO> modifiedLoanList = new ArrayList<ModifiedLoanListResponseVO>();
	private ModifiedLoanListResponseVO modifiedLoanListResponseVO;

	boolean field = false;
	String tempVal = "";

	@Override
	public void startElement(String uri, String localName, String qName,
	        Attributes attributes) throws SAXException {

		LOG.info("StartElement  qName # " + qName);

		if ("loan".equals(qName)) {
			modifiedLoanListResponseVO = new ModifiedLoanListResponseVO();
			modifiedLoanListResponseVO.setLoanName(attributes.getValue("name"));
			modifiedLoanListResponseVO.setOldName(attributes
			        .getValue("old_name"));
			modifiedLoanListResponseVO.setValid(Boolean.valueOf(attributes
			        .getValue("valid")));
			modifiedLoanListResponseVO.setBorrowerName(attributes
			        .getValue("aBNm"));
			modifiedLoanListResponseVO.setBorrowerSSN(attributes
			        .getValue("aBSsn"));
			modifiedLoanListResponseVO.setLoanStatus(attributes
			        .getValue("sStatusT"));
			field = true;

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
	        throws SAXException {
		// push in userList
		if ("loan".equals(qName)) {
			modifiedLoanList.add(modifiedLoanListResponseVO);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
	        throws SAXException {
		tempVal = new String(ch, start, length);

	}

	public List<ModifiedLoanListResponseVO> getModifiedLoanList() {
		return modifiedLoanList;
	}

}