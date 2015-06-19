package com.nexera.core.utility;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nexera.common.vo.lqb.LQBDocumentResponseListVO;
import com.nexera.common.vo.lqb.LQBResponseVO;
import com.nexera.common.vo.lqb.LQBedocVO;

public class LQBXMLHandler extends DefaultHandler {

	private LQBResponseVO lqbResponseVO = null;

	public LQBResponseVO getLqbResponseVO() {
		return lqbResponseVO;
	}

	private LQBDocumentResponseListVO listVO = null;
	private LQBedocVO lqBedocVO = null;
	private List<LQBedocVO> listDocumentListVO = null;
	private static final Logger LOG = LoggerFactory
	        .getLogger(LQBXMLHandler.class);

	@Override
	public void startElement(String uri, String localName, String qName,
	        Attributes attributes) throws SAXException {

		if ("result".equals(qName)) {
			lqbResponseVO = new LQBResponseVO();
			lqbResponseVO.setResult(attributes.getValue("status"));
		}

		if ("list".equals(qName)) {
			listDocumentListVO = new ArrayList<LQBedocVO>();
			listVO = new LQBDocumentResponseListVO();
		}

		if ("edoc".equals(qName)) {
			lqBedocVO = new LQBedocVO();

			lqBedocVO.setApplication(attributes.getValue("application"));
			lqBedocVO.setCreated_date(attributes.getValue("created_date"));
			lqBedocVO.setDescription(attributes.getValue("description"));
			lqBedocVO.setDoc_status(attributes.getValue("doc_status"));
			lqBedocVO.setDoc_type(attributes.getValue("doc_type"));
			lqBedocVO.setDocid(attributes.getValue("docid"));
			lqBedocVO.setFolder_name(attributes.getValue("folder_name"));

			listDocumentListVO.add(lqBedocVO);

		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
	        throws SAXException {
		// push in userList

		if ("list".equals(qName)) {
			listVO.setvBedocVO(listDocumentListVO);
			lqbResponseVO.setDocumentResponseListVOs(listVO);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
	        throws SAXException {

	}

}
