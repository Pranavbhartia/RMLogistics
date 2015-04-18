package com.nexera.core.helper.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nexera.common.dao.GenericDao;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.exception.NonFatalException;
import com.nexera.core.helper.USStateLookUp;

@Component
public class USStateLookupImpl implements USStateLookUp {

	@Autowired
	@Qualifier("genericDaoImpl")
	private GenericDao genericDao;
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(USStateLookupImpl.class);

	@Override
	@Transactional
	public void saveStatesInDB() throws NonFatalException {
		try {

			JsonFactory jfactory = new JsonFactory();

			/*** read from file ***/

			URL arg0 = new URL(
			        "https://gist.githubusercontent.com/mshafrir/2646763/raw/bfb35f17bc5d5f86ec0f10b80f7b80e823e9197f/states_titlecase.json");
			JsonParser jParser = jfactory.createParser(arg0);
			// loop until token equal to "}"
			HashMap<String, String> hashMap = new HashMap<String, String>();

			String stateName = null;
			String stateAbbr = null;
			while (jParser.nextToken() != JsonToken.END_ARRAY) {

				String fieldname = jParser.getCurrentName();
				if ("name".equals(fieldname)) {

					// current token is "name",
					// move to next, which is "name"'s value
					jParser.nextToken();
					stateName = jParser.getText();


				}

				if ("abbreviation".equals(fieldname)) {

					// current token is "age",
					// move to next, which is "name"'s value
					jParser.nextToken();
					stateAbbr = jParser.getText();
					hashMap.put(stateAbbr, stateName);

				}

			}
			Set<String> set = hashMap.keySet();
			List<StateLookup> stateLookups = new ArrayList<StateLookup>();

			for (String key : set) {
				StateLookup lookup = new StateLookup();
				lookup.setStatecode(key);
				lookup.setStatename(hashMap.get(key));
				stateLookups.add(lookup);
			}
			genericDao.saveAll(stateLookups);
			jParser.close();

		} catch (JsonGenerationException e) {

			LOGGER.error("Exception in convertImageToPDF : " + e.getMessage());

		} catch (JsonMappingException e) {

			LOGGER.error("Exception in convertImageToPDF : " + e.getMessage());

		} catch (IOException e) {

			LOGGER.error("Exception in convertImageToPDF : " + e.getMessage());

		}

	}

}
