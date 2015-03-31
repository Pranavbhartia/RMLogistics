package com.nexera.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	
	@Test
	public void test() {
		try {
			usZipLookup.saveZipCodeInDB();
        } catch (NonFatalException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

}
