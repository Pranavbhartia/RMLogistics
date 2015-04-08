package com.nexera.extractor.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.nexera.extractor.entity.FileProductPointRate;
import com.nexera.extractor.entity.ProductPointRate;
import com.nexera.extractor.entity.UIEntity;
import com.nexera.extractor.entity.YearBasedRate;

@Component
public class Utility {

	private static final String[] FILE_KEY_INDEX = {
	        "10_YR_FIXED_CONFORMING-RS_FNMA_15DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_30DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_45DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_60DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_15DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_30DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_45DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_60DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_15DAY_PRICE.csv",
	        "15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_30DAY_PRICE.csv",
	        "15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_45DAY_PRICE.csv",
	        "15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_60DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_15DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_30DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_45DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_60DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_15DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_30DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_45DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_60DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_15DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_30DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_45DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_60DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_15DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_30DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_45DAY_PRICE.csv",
	        "30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_60DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_60DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_60DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_60DAY_PRICE.csv" };

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
	        "30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_",
	        "15_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_"};

	private static final String[] KEY_INDEX = { "15DAY_PRICE.csv",
	        "30DAY_PRICE.csv", "45DAY_PRICE.csv", "60DAY_PRICE.csv" };
	
	
	static final Map<String , String> FILE_PATTERN_LABEL = new HashMap<String , String>() {{
		
		
		
	    put("30_YR_FIXED_CONFORMING-RS_FNMA_",   "Fannie Mae 30yr Fixed");
	    put("20_YR_FIXED_CONFORMING-RS_FNMA_",   "Fannie Mae 20yr Fixed");
	    put("15_YR_FIXED_CONFORMING-RS_FNMA_",   "Fannie Mae 15yr Fixed");
	    put("10_YR_FIXED_CONFORMING-RS_FNMA_",   "Fannie Mae 10yr Fixed");
	  
	    put("30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",   "Fannie Mae 30yr Fixed High Balance");
	    put("20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",   "Fannie Mae 20yr Fixed High Balance");
	    put("15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",   "Fannie Mae 15yr Fixed High Balance");
	    put("10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",   "Fannie Mae 10yr Fixed High Balance");
	    
	    put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_",   "Fannie Mae 5/1 Libor ARM 2/2/5");
	    put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_",   "Fannie Mae 20yr Fixed High Balance");
	    put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_",   "Fannie Mae 5/1 Libor ARM High Balance 2/2/5");
	    put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_",   "Fannie Mae 7/1 Libor ARM High Balance 5/2/5");
	    
	    put("30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",   "Mammoth Jumbo 30 YR Fixed");
	    put("15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",   "Mammoth Jumbo 15 YR Fixed");
	    put("5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_",   "Mammoth Non Agency Hybrid 5/1 ARM");
	    put("5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_",   "Mammoth Non Agency Hybrid 5/1 ARM IO");
	    
	    put("30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_",   "Cascades Jumbo 30 YR Fixed");
	    put("15_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_",   "Cascades Jumbo 15 YR Fixed");
	}};
	

	public static Integer currentIndex = 0;

	public List<Map<String, List<ProductPointRate>>> getCompleteProductRateList(
	        List<FileProductPointRate> fileProductPointRates) {
		return null;
		// Map<String, List<ProductPointRate>> rateMap = new HashMap<String,
		// List<ProductPointRate>>();
		// List<Map<String, List<ProductPointRate>>> rateMapList = new
		// ArrayList<Map<String, List<ProductPointRate>>>();
		//
		// // Iterate through each file, Find, which Map it belongs and add that
		// // Map to the corresponding list
		// for (FileProductPointRate fileProductPointRate :
		// fileProductPointRates) {
		//
		// String fileName = fileProductPointRate.getFileName();
		// String patternIndex = getFilePattern(fileName);
		// String keyIndex = getKeyIndex(fileName);
		// if (keyIndex == null || patternIndex == null) {
		// // If either is null, it means the file is not in the PATTERn we
		// // are looking at
		// System.out.println("Invalid file: " + fileName);
		// continue;
		// }
		//
		// if (rateMap.get(patternIndex) == null) {
		// fileProductPointRate.getProductPointRate();
		// rateMap.put(patternIndex,
		// fileProductPointRate.getProductPointRate());
		// } else {
		// List<ProductPointRate> list = rateMap.get(patternIndex);
		// list.addAll(fileProductPointRate.getProductPointRate());
		// rateMap.put(patternIndex, list);
		// }
		// }
		// Set<String> keySet = rateMap.keySet();
		// Map<String, Map<String, List<String>>> uiMap = new HashMap<String,
		// Map<String, List<String>>>();
		//
		// for (String key : keySet) {
		// List<ProductPointRate> list = rateMap.get(key);
		// System.out.println("Key: " + key);
		// // System.out.println("Key: " + key);
		// // System.out.println("---");
		// Map<String, List<String>> tableMap = new HashMap<String,
		// List<String>>();
		//
		// for (ProductPointRate productPointRate : list) {
		// String rate = productPointRate.getRate();
		//
		// if (rate.equalsIgnoreCase("Rate")
		// || rate.equalsIgnoreCase("Point:")) {
		// continue;
		// }
		// if (tableMap.get(rate) == null) {
		// List<String> rateList = new ArrayList<String>();
		// if (!productPointRate.getPoint().equalsIgnoreCase("point")) {
		// rateList.add(productPointRate.getPoint());
		//
		// tableMap.put(rate, rateList);
		// }
		//
		// } else {
		// List<String> rateList = tableMap.get(rate);
		// if (!productPointRate.getPoint().equalsIgnoreCase("point")) {
		// rateList.add(productPointRate.getPoint());
		//
		// tableMap.put(rate, rateList);
		// }
		//
		// }
		//
		// }
		// uiMap.put(key, tableMap);
		//
		// }
		// Set<String> keys = uiMap.keySet();
		// for (String key : keys) {
		//
		// Map<String, List<String>> mapList = uiMap.get(key);
		// System.out.println("Key: " + key);
		// System.out.println("---");
		// Set<String> allKeys = mapList.keySet();
		//
		// for (String string : allKeys) {
		// System.out.println("Rate: " + string);
		// List<String> valueList = mapList.get(string);
		// for (String string2 : valueList) {
		// System.out.println(string2);
		// }
		// }
		// System.out.println("---");
		// }
		//
		// List<Map<String, List<ProductPointRate>>> listMap = new
		// ArrayList<Map<String, List<ProductPointRate>>>();
		//
		// return listMap;
	}
	
	@SuppressWarnings("unused")
	private Boolean isPresent(String pattern){
		for (String pattrn : FILE_PATTERN){
			if(pattern.equals(pattrn)){
				return true;
			}
		}
		return false;
	}

	private int getKeyIndex(String fileName) {
		
		
		
		for (int i = 0; i < KEY_INDEX.length; i++) {
			if (fileName.contains(KEY_INDEX[i])) {

				return i;
			}
		}
		return 0;
	}

	private String getFilePattern(String fileName) {
		String actualFileName = "";
		for (String key : KEY_INDEX) {
			if (fileName.contains(key)) {
				actualFileName = fileName.replace(key, "");
				break;
			}

		}
		for (String pattern : FILE_PATTERN) {
			if (actualFileName.equals(pattern)) {
				return FILE_PATTERN_LABEL.get(pattern);
				//return actualFileName;
			}
		}
		return null;
	}

	private Map<String, List<ProductPointRate>> getFileBasedResult(
	        String fileStartChar,
	        List<FileProductPointRate> fileProductPointRates) {
		Map<String, List<ProductPointRate>> map = new HashMap<String, List<ProductPointRate>>();
		List<ProductPointRate> prl = new ArrayList<ProductPointRate>();

		for (FileProductPointRate fpprList : fileProductPointRates) {
			System.out.println("" + fpprList.getFileName());

			if (fpprList.getFileName().startsWith(fileStartChar)) {
				prl.addAll(fpprList.getProductPointRate());

			}

		}

		map.put(fileStartChar, prl);
		return map;

	}

	private FileProductPointRate fetchObejctByFileName(String fileStartChar,
	        List<FileProductPointRate> fileProductPointRates) {
		FileProductPointRate objectToReturn = null;
		for (FileProductPointRate fileProductPointRate : fileProductPointRates) {
			if (fileProductPointRate.getFileName().startsWith(fileStartChar)) {
				objectToReturn = fileProductPointRate;
				fileProductPointRates.remove(fileProductPointRate);
				return objectToReturn;
			}
		}
		return objectToReturn;
	}

	public List<FileProductPointRate> getFileProductlist(final File folder) {

		List<FileProductPointRate> list = new ArrayList<FileProductPointRate>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println("This is a directory");
			} else {
				if (fileEntry.getName().startsWith(".")) {
					// Hidden file so ignore
					continue;
				}

				FileProductPointRate pointRate = new FileProductPointRate();
				List<ProductPointRate> productPointRate = readCSVFileContent(fileEntry);
				pointRate.setFileName(fileEntry.getName());
				pointRate.setProductPointRate(productPointRate);
				int fileIndex = getKeyIndex(fileEntry.getName());
				pointRate.setFileIndex(fileIndex);
				list.add(pointRate);
			}
		}
		return list;
	}

	public List<Map<String, List<YearBasedRate>>> readFilesFromDestination(
	        final File folder) {

		if (folder.isDirectory()) {
			System.out.println(folder.lastModified());
		}
		Map<String, List<YearBasedRate>> mapRatePointTable = null;
		YearBasedRate yearBasedRate = null;
		List<YearBasedRate> listYearBasedRate = null;
		Integer counter = 0;
		Integer index = 0;
		System.out.println();
		List<Map<String, List<YearBasedRate>>> listMap = new ArrayList<Map<String, List<YearBasedRate>>>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println("This is a directory");
			} else {
				System.out.println(fileEntry.getName());
				// readCSVFileIntoMap(fileEntry);
				List<ProductPointRate> contentFile = readCSVFileContent(fileEntry);

				for (ProductPointRate productPointRate : contentFile) {

					if (fileEntry.getName().contains("15DAY_PRICE")) {
						yearBasedRate = new YearBasedRate();
						yearBasedRate.setPoint(productPointRate.getPoint());
						yearBasedRate.setFifteenYearRate(productPointRate
						        .getRate());

					} else if (fileEntry.getName().contains("30DAY_PRICE")) {
						yearBasedRate.setThirtyYearRate(productPointRate
						        .getRate());
					} else if (fileEntry.getName().contains("45DAY_PRICE")) {
						yearBasedRate.setFortyFiveYearRate(productPointRate
						        .getRate());
					} else if (fileEntry.getName().contains("60DAY_PRICE")) {
						yearBasedRate.setSixtyYearRate(productPointRate
						        .getRate());
					} else {
						System.out.println("Not found");
					}

					if (counter % 4 == 0) {
						listYearBasedRate = new ArrayList<YearBasedRate>();
						listYearBasedRate.add(yearBasedRate);

						mapRatePointTable = new HashMap<String, List<YearBasedRate>>();
						mapRatePointTable.put(FILE_PATTERN[index++],
						        listYearBasedRate);
						listMap.add(mapRatePointTable);

					}

					counter++;
				}
			}
		}

		return listMap;
	}

	private List<ProductPointRate> readCSVFileContent(File file) {

		List<ProductPointRate> content = null;
		FileReader reader = null;
		CSVReader csvReader = null;
		try {
			CsvToBean<ProductPointRate> bean = new CsvToBean<ProductPointRate>();
			ColumnPositionMappingStrategy<ProductPointRate> strategy = new ColumnPositionMappingStrategy<ProductPointRate>();
			strategy.setType(ProductPointRate.class);
			strategy.setColumnMapping(new String[] { "rate", "point",
			        "seperator", "product" });

			reader = new FileReader(file);
			csvReader = new CSVReader(reader);

			content = bean.parse(strategy, csvReader);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (csvReader != null) {
					csvReader.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return content;

	}

	public Map<String, List<UIEntity>> buildUIMap(
	        List<FileProductPointRate> list) {

		// Key is fileName, value is list of all the 15 days, 30 days etc.
		// values to
		// be displayed

		Map<String, List<UIEntity>> hashMap = new HashMap<String, List<UIEntity>>();

		for (FileProductPointRate fileProductPointRate : list) {

			String rowHeader = getFilePattern(fileProductPointRate
			        .getFileName());
			List<UIEntity> uiEntityList = null;

			if (hashMap.get(rowHeader) == null) {
				uiEntityList = new ArrayList<UIEntity>();
				populateUIEntity(uiEntityList, fileProductPointRate);
				hashMap.put(rowHeader, uiEntityList);
			} else {
				uiEntityList = hashMap.get(rowHeader);
				populateUIEntity(uiEntityList, fileProductPointRate);

				hashMap.put(rowHeader, uiEntityList);
			}

		}
		// Sort the list of UI Elements in the decending order of rates:

		Set<String> keySet = hashMap.keySet();
		int tableCount = 0;
		for (String key : keySet) {
			tableCount++;
			List<UIEntity> uiList = hashMap.get(key);
			Collections.sort(uiList, new Comparator<UIEntity>() {
				public int compare(UIEntity p1, UIEntity p2) {
					return p1.getRate().compareTo(p2.getRate());
				}
			});
			System.out.println("Key:" + key + "Table: " + tableCount);
			for (UIEntity uiEntity : uiList) {
				System.out.println(uiEntity.getRate() + " 15: "
				        + uiEntity.getCol1Points() + " 30: "
				        + uiEntity.getCol2Points() + " 45: "
				        + uiEntity.getCol3Points() + " 60: "
				        + uiEntity.getCol4Points());
			}
		}

		return hashMap;
	}

	private void populateUIEntity(List<UIEntity> uiEntityList,
	        FileProductPointRate fileProductPointRate) {

		int keyIndex = fileProductPointRate.getFileIndex();
		switch (keyIndex) {
		case 0:
			addToCol(1, uiEntityList, fileProductPointRate);
			break;

		case 1:
			addToCol(2, uiEntityList, fileProductPointRate);
			break;

		case 2:
			addToCol(3, uiEntityList, fileProductPointRate);
			break;

		case 3:
			addToCol(4, uiEntityList, fileProductPointRate);
			break;

		default:
			break;
		}
	}

	private void addToCol(int i, List<UIEntity> uiEntityList,
	        FileProductPointRate fileProductPointRate) {
		List<ProductPointRate> oneFileContents = fileProductPointRate
		        .getProductPointRate();
		for (ProductPointRate productPointRate : oneFileContents) {

			if (!productPointRate.getRate().equalsIgnoreCase("rate")) {

				UIEntity uiEntity = getUIEntityForRate(
				        productPointRate.getRate(), uiEntityList);
				if (1 == i) {
					uiEntity.setCol1Points(productPointRate.getPoint());
				} else if (2 == i) {
					uiEntity.setCol2Points(productPointRate.getPoint());
				} else if (3 == i) {
					uiEntity.setCol3Points(productPointRate.getPoint());
				} else if (4 == i) {
					uiEntity.setCol4Points(productPointRate.getPoint());
				}
			}
		}
	}

	private UIEntity getUIEntityForRate(String rate, List<UIEntity> uiEntityList) {
		BigDecimal floatRate = new BigDecimal(rate);

		for (UIEntity uiEntity : uiEntityList) {

			if (floatRate.equals(uiEntity.getRate())) {
				return uiEntity;
			}
		}
		UIEntity uiEntity = new UIEntity();
		uiEntity.setRate(floatRate);
		uiEntityList.add(uiEntity);
		return uiEntity;
	}
}
