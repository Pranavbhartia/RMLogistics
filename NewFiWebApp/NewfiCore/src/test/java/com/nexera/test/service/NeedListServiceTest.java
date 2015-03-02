package com.nexera.test.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.core.service.NeedsListService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NeedListServiceTest {
	
	@Autowired
	NeedsListService needListService;

	@Test
	public void testGetLoanNeedsMap() {
		needListService.getLoanNeedsMap(1);
	}

}
