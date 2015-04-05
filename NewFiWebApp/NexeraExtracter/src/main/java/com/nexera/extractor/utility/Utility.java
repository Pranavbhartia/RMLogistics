package com.nexera.extractor.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.nexera.extractor.entity.FileProductPointRate;
import com.nexera.extractor.entity.ProductPointRate;
import com.nexera.extractor.entity.YearBasedRate;

@Component
public class Utility {

	private static final String[] FILE_PATTERN = {
		"5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_",
		"5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_",
		"5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_",
		"5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_",
		"7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_",
		"7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_",
		"10_YR_FIXED_CONFORMING-RS_FNMA_",
		"10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
		"15_YR_FIXED_CONFORMING-RS_FNMA_",
		"15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
		"15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",
		"20_YR_FIXED_CONFORMING-RS_FNMA_",
		"20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
		"30_YR_FIXED_CONFORMING-RS_FNMA_",
		"30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
		"30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",
		"30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_"
	};
	
	public static Integer currentIndex = 0;
	
	
	public List<Map<String, List<ProductPointRate>>> getCompleteProductRateList(List<FileProductPointRate> fileProductPointRates){
		
		List<Map<String , List<ProductPointRate>>> listMap = new ArrayList<Map<String,List<ProductPointRate>>>();
		Integer index = 0;
		System.out.println("looping : "+fileProductPointRates.size());
		while(currentIndex!=FILE_PATTERN.length){
			index++;
			System.out.println("checking for file pattern : "+FILE_PATTERN[currentIndex]);
			Map<String , List<ProductPointRate>> map = getFileBasedResult(FILE_PATTERN[currentIndex] ,fileProductPointRates );
			listMap.add(map);
			if(index %4 == 0){
				currentIndex++;
			}
		}
		return listMap;
	}
	
	
	private Map<String , List<ProductPointRate>> getFileBasedResult(String fileStartChar , List<FileProductPointRate> fileProductPointRates ){
		Map<String , List<ProductPointRate>> map = new HashMap<String, List<ProductPointRate>>();
		List<ProductPointRate> prl = new ArrayList<ProductPointRate>();
		
		for(FileProductPointRate fpprList : fileProductPointRates){
			System.out.println(""+fpprList.getFileName());
			
			if(fpprList.getFileName().startsWith(fileStartChar)){
				prl.addAll(fpprList.getProductPointRate());
				
			}
			
		}
		
		
		map.put(fileStartChar, prl);
		return map;
			
	}
	
	private FileProductPointRate fetchObejctByFileName(String fileStartChar ,List<FileProductPointRate> fileProductPointRates ){
		FileProductPointRate objectToReturn = null;
		for (FileProductPointRate fileProductPointRate : fileProductPointRates) {
			if(fileProductPointRate.getFileName().startsWith(fileStartChar)){
				objectToReturn = fileProductPointRate;
				fileProductPointRates.remove(fileProductPointRate);
				return objectToReturn;
			}
		}
		return objectToReturn;
	}
	
	public List<FileProductPointRate> getFileProductlist(final File folder){
		
		List<FileProductPointRate> list = new ArrayList<FileProductPointRate>();
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	           System.out.println("This is a directory");
	        } else {
	           
	            FileProductPointRate pointRate = new FileProductPointRate();
	            List<ProductPointRate> productPointRate = readCSVFileContent(fileEntry);
	            pointRate.setFileName(fileEntry.getName());
	            pointRate.setProductPointRate(productPointRate);
	            list.add(pointRate);
	        }
		}
		return list;
	}
	
	
	

	public List<Map<String , List<YearBasedRate>>> readFilesFromDestination(final File folder){
	
		if(folder.isDirectory()){
			System.out.println(folder.lastModified());
		}
		Map<String, List<YearBasedRate>> mapRatePointTable = null;
		YearBasedRate yearBasedRate = null;
		List<YearBasedRate> listYearBasedRate = null;
		Integer counter = 0;
		Integer index=0;
		System.out.println();
		List<Map<String , List<YearBasedRate>>> listMap = new ArrayList<Map<String,List<YearBasedRate>>>();
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	           System.out.println("This is a directory");
	        } else {
	            System.out.println(fileEntry.getName());
	           // readCSVFileIntoMap(fileEntry);
	            List<ProductPointRate> contentFile = readCSVFileContent(fileEntry);
	            
	           
	            
	    		
	            for (ProductPointRate productPointRate : contentFile) {	
	            		
		            	if(fileEntry.getName().contains("15DAY_PRICE")){
		            		yearBasedRate = new YearBasedRate();
		            		yearBasedRate.setPoint(productPointRate.getPoint());
		            		yearBasedRate.setFifteenYearRate(productPointRate.getRate());
		            		
		            	}else if(fileEntry.getName().contains("30DAY_PRICE")){
		            		yearBasedRate.setThirtyYearRate(productPointRate.getRate());
		            	}else if(fileEntry.getName().contains("45DAY_PRICE")){
		            		yearBasedRate.setFortyFiveYearRate(productPointRate.getRate());
		            	}else if(fileEntry.getName().contains("60DAY_PRICE")){
		            		yearBasedRate.setSixtyYearRate(productPointRate.getRate());
		            	}else{
		            		System.out.println("Not found");
		            	}
		            	
		            if(counter % 4 == 0){
				            listYearBasedRate = new ArrayList<YearBasedRate>();
				            listYearBasedRate.add(yearBasedRate);
				            
			            	mapRatePointTable = new HashMap<String , List<YearBasedRate>>();
			            	mapRatePointTable.put(FILE_PATTERN[index++], listYearBasedRate);
			            	listMap.add(mapRatePointTable);
			        
		            }
		            
		            counter++;
	            }
	        }
	    }
		
		return listMap;
	}

	
	
	
	
	
	private List<ProductPointRate> readCSVFileContent(File file){
	
		List<ProductPointRate> content = null;
		FileReader reader = null;
		CSVReader csvReader = null;
		try {
			CsvToBean<ProductPointRate> bean = new CsvToBean<ProductPointRate>();
			ColumnPositionMappingStrategy<ProductPointRate> strategy = new ColumnPositionMappingStrategy<ProductPointRate>();
			strategy.setType(ProductPointRate.class);
			strategy.setColumnMapping(new String [] { "rate", "point","seperator", "product" });
			
			
			reader = new FileReader(file);
			csvReader = new CSVReader(reader);
			
			content = bean.parse(strategy, csvReader);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
				if(csvReader != null){
					csvReader.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return content;
		
	}
	
}
