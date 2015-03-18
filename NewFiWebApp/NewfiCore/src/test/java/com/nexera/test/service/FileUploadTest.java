package com.nexera.test.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.core.utility.NexeraUtility;


@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class FileUploadTest {

	@Autowired
	NexeraUtility nexeraUtility;
	
	@Test
	public void testConvertImageToPDF() {
		FileInputStream inputFile;
        try {
	        inputFile = new FileInputStream( "/apps/tmp/CCITT_1.tif");
	        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", inputFile);
	        nexeraUtility.convertImageToPDF(file );
        } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
		
	}

}
