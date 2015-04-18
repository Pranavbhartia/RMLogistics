package com.nexera.core.helper.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.nexera.common.entity.ZipCodeLookup;
import com.nexera.common.exception.NonFatalException;
import com.nexera.core.helper.USZipCodeLookup;

@Component
public class USZipCodeLookupImpl implements USZipCodeLookup {

	@Autowired
	@Qualifier("genericDaoImpl")
	private GenericDao genericDao;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(USZipCodeLookupImpl.class);

	@Override
	@Transactional
	public void saveZipCodeInDB() throws NonFatalException {
		List<StateLookup> stateLookups = genericDao.loadAll(StateLookup.class);
		// List<StateLookup> stateLookups = new ArrayList<StateLookup>();
		// StateLookup lookup = new StateLookup();
		// lookup.setStatecode("IL");
		// stateLookups.add(lookup);

		for (StateLookup stateLookup : stateLookups) {

			String url = "http://gomashup.com/json.php?fds=geo/usa/zipcode/state/"
			        + stateLookup.getStatecode();

			try {
				URL url2 = new URL(url);
				InputStream inputStream = url2.openConnection()
				        .getInputStream();

				StringBuilder builder = new StringBuilder();
				String line = "";
				BufferedReader bufferedReader = new BufferedReader(
				        new InputStreamReader(inputStream));
				while ((line = bufferedReader.readLine()) != null) {
					builder.append(line);

				}
				String finalContent = builder.toString();
				finalContent = finalContent.replace("({", "");
				finalContent = finalContent.replace("\"result\":", "");
				finalContent = finalContent.replace("]}", "]");
				finalContent = finalContent.replace(")", "");
				JsonFactory jfactory = new JsonFactory();

				JsonParser jParser = jfactory.createParser(finalContent);

				// loop until token equal to "}"
				HashMap<String, String> hashMap = new HashMap<String, String>();

				String zipCode = null;
				String cityName = null;
				String countyName = null;
				String stateName = null;
				List<ZipCodeLookup> codeLookups = new ArrayList<ZipCodeLookup>();
				while (jParser.nextToken() != JsonToken.END_ARRAY) {

					String fieldname = jParser.getCurrentName();
					if ("Zipcode".equals(fieldname)) {

						// current token is "name",
						// move to next, which is "name"'s value
						jParser.nextToken();
						zipCode = jParser.getText();

					}
					if ("County".equals(fieldname)) {

						// current token is "age",
						// move to next, which is "name"'s value
						jParser.nextToken();
						countyName = jParser.getText();

						// hashMap.put(stateAbbr, stateName);

					}
					if ("City".equals(fieldname)) {

						// current token is "age",
						// move to next, which is "name"'s value
						jParser.nextToken();
						cityName = jParser.getText();

						// hashMap.put(stateAbbr, stateName);

					}
					if ("State".equals(fieldname)) {

						// current token is "age",
						// move to next, which is "name"'s value
						jParser.nextToken();
						stateName = jParser.getText();
						ZipCodeLookup zipCodeLookup = new ZipCodeLookup();
						zipCodeLookup.setCityname(cityName);
						zipCodeLookup.setCountyname(countyName);
						zipCodeLookup.setZipcode(zipCode);
						zipCodeLookup.setStateLookup(stateLookup);
						codeLookups.add(zipCodeLookup);

					}

				}

				genericDao.saveAll(codeLookups);
				jParser.close();

			} catch (JsonGenerationException e) {

				LOGGER.error("Exception caught " + e.getMessage());

			} catch (JsonMappingException e) {

				LOGGER.error("Exception caught " + e.getMessage());

			} catch (IOException e) {

				LOGGER.error("Exception caught " + e.getMessage());

			}

		}

	}

	public static void main(String[] args) {
		USZipCodeLookupImpl codeLookupImpl = new USZipCodeLookupImpl();
		try {
			codeLookupImpl.saveZipCodeInDB();
		} catch (NonFatalException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Exception caught " + e.getMessage());
		}
	}

}
