package com.nexera.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.exception.NonFatalException;
import com.nexera.core.helper.USStateLookUp;
import com.nexera.core.helper.USZipCodeLookup;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class USStateLookupTest {

	@Autowired
	USStateLookUp usStateLookup;

	@Autowired
	USZipCodeLookup usZipLookup;

	@Autowired
	UserProfileDao dao;

	// @Test
	public void test() {

		/*
		 * TODO: Uncomment if you want to populate the state and zip codes in ur
		 * local DB. It takes a while though
		 */

		try {

			usStateLookup.saveStatesInDB();
			usZipLookup.saveZipCodeInDB();
		} catch (NonFatalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testUserProfile() {
		dao.updateLMID(1, 25);
	}
}
