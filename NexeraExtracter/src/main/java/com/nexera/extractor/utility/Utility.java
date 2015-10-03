package com.nexera.extractor.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.WHITE;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.google.gson.annotations.Until;
import com.nexera.extractor.constants.CommonConstant;
import com.nexera.extractor.entity.FileProductPointRate;
import com.nexera.extractor.entity.ProductPointRate;
import com.nexera.extractor.entity.RestResponse;
import com.nexera.extractor.entity.UIEntity;
import com.nexera.extractor.entity.YearBasedRate;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Component
public class Utility {

	private static HashMap<Long, Map<String, List<UIEntity>>> cache = new HashMap<Long, Map<String, List<UIEntity>>>();

	@Autowired
	private CommonConstant commonConstant;
	 static CellStyle startingCellStyle = null;
	 static CellStyle endingCellStyle = null;
	 static CellStyle regularCellStyling = null;
	
	private static final String[] FILE_KEY_INDEX = {
	        "10_YR_FIXED_CONFORMING-RS_FNMA_15DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_30DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_45DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_60DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_15DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_30DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_45DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_60DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_15DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_30DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_45DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_60DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FHLMC_15DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FHLMC_30DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FHLMC_45DAY_PRICE.csv",
	        "10_YR_FIXED_CONFORMING-RS_FHLMC_60DAY_PRICE.csv",
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
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_15DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_30DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_45DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_60DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_15DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_30DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_45DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_60DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_15DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_30DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_45DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_60DAY_PRICE.csv",	        		       		       
	        /*"15_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_15DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_30DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_45DAY_PRICE.csv",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_60DAY_PRICE.csv",*/
	        "20_YR_FIXED_CONFORMING-RS_FNMA_15DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_30DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_45DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_60DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_15DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_30DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_45DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_60DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_15DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_30DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_45DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_60DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FHLMC_15DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FHLMC_30DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FHLMC_45DAY_PRICE.csv",
	        "20_YR_FIXED_CONFORMING-RS_FHLMC_60DAY_PRICE.csv",
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
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_15DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_30DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_45DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_60DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_15DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_30DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_45DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_60DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_15DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_30DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_45DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_60DAY_PRICE.csv",
	       /* "30_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_15DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_30DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_45DAY_PRICE.csv",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_60DAY_PRICE.csv",*/
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
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_60DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_60DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_60DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_15DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_30DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_45DAY_PRICE.csv",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_60DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_15DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_30DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_45DAY_PRICE.csv",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_60DAY_PRICE.csv",
	        
	        
	       };

	private static final String[] FILE_PATTERN = {
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_",
	        "5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_",	       	       	       	        
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_",
	        "5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_",
	        "7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_",	       
	        "10_YR_FIXED_CONFORMING-RS_FNMA_",
	        "10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
	        "10_YR_FIXED_CONFORMING-RS_FHLMC_",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_",
	        "10_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_",
	        "15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
	        "15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_",
	        "15_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
	        "15_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_",
	        "15_YR_FIXED_CONFORMING-RS_FHLMC_",
	       /* "15_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_",*/
	        "20_YR_FIXED_CONFORMING-RS_FNMA_",
	        "20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_",
	        "20_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
	        "20_YR_FIXED_CONFORMING-RS_FHLMC_",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_",
	        "30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
	        "30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",
	        "30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_",
	        "30_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_",
	        "30_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_",

	       };
	

	private static final String[] KEY_INDEX = { "15DAY_PRICE.csv",
	        "30DAY_PRICE.csv", "45DAY_PRICE.csv", "60DAY_PRICE.csv" };

	static final Map<String, String> FILE_PATTERN_LABEL = new HashMap<String, String>() {
		{

			put("30_YR_FIXED_CONFORMING-RS_FNMA_", "Fannie Mae 30yr Fixed");
			put("20_YR_FIXED_CONFORMING-RS_FNMA_", "Fannie Mae 20yr Fixed");
			put("15_YR_FIXED_CONFORMING-RS_FNMA_", "Fannie Mae 15yr Fixed");
			put("10_YR_FIXED_CONFORMING-RS_FNMA_", "Fannie Mae 10yr Fixed");

			put("30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
			        "Fannie Mae 30yr Fixed High Balance");
			put("20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
			        "Fannie Mae 20yr Fixed High Balance");
			put("15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
			        "Fannie Mae 15yr Fixed High Balance");
			put("10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_",
			        "Fannie Mae 10yr Fixed High Balance");

			put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_",
			        "Fannie Mae 5/1 Libor ARM 2/2/5");
			put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_",
			        "Fannie Mae 7/1 Libor ARM 2/2/5");
			put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_",
			        "Fannie Mae 5/1 Libor ARM High Balance 2/2/5");
			put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_",
			        "Fannie Mae 7/1 Libor ARM High Balance 5/2/5");

			put("30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",
			        "Mammoth Jumbo 30 YR Fixed");
			put("15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_",
			        "Mammoth Jumbo 15 YR Fixed");
			put("5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_",
			        "Mammoth Non Agency Hybrid 5/1 ARM");
			put("5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_",
			        "Mammoth Non Agency Hybrid 5/1 ARM IO");

			put("30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_",
			        "Cascades Jumbo 30 YR Fixed");
			put("15_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_",
			        "Cascades Jumbo 15 YR Fixed");
			
			put("30_YR_FIXED_CONFORMING-RS_OLYMPIC_", "Fannie Mae 30 Yr Fixed");
			put("20_YR_FIXED_CONFORMING-RS_OLYMPIC_", "Fannie Mae 20 Yr Fixed");
			put("15_YR_FIXED_CONFORMING-RS_OLYMPIC_", "Fannie Mae 15 Yr Fixed");
			put("10_YR_FIXED_CONFORMING-RS_OLYMPIC_", "Fannie Mae 10 Yr Fixed");

			put("30_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
			        "Fannie Mae 30 Yr Fixed High Balance");
			put("20_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
			        "Fannie Mae 20 Yr Fixed High Balance");
			put("15_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
			        "Fannie Mae 15 Yr Fixed High Balance");
			put("10_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_",
			        "Fannie Mae 10 Yr Fixed High Balance");
			
			put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_",
			        "Fannie Mae 5/1 ARM 2/2/5");
			put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_",
			        "Fannie Mae 7/1 ARM 5/2/5");
			put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_",
			        "Fannie Mae 5/1 ARM High Balance 2/2/5");
			put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_",
			        "Fannie Mae 7/1 ARM High Balance 5/2/5");
			
			put("30_YR_FIXED_CONFORMING-RS_FHLMC_", "Freddie Mac 30 Yr Fixed");
			put("20_YR_FIXED_CONFORMING-RS_FHLMC_", "Freddie Mac 20 Yr Fixed");
			put("15_YR_FIXED_CONFORMING-RS_FHLMC_", "Freddie Mac 15 Yr Fixed");
			put("10_YR_FIXED_CONFORMING-RS_FHLMC_", "Freddie Mac 10 Yr Fixed");
			
			/*put("15_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_",
			        "Freddie Mac 15 Yr Fixed Super Conforming");
			put("30_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_",
			        "Freddie Mac 30 Yr Fixed Super Conforming");
			*/
			put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_",
			        "Freddie Mac 5/1 ARM 2/2/5");
			put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_",
			        "Freddie Mac 7/1 ARM 5/2/5");
			put("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_",
			        "Freddie Mac 5/1 ARM Super Conforming 2/2/5");
			put("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_",
			        "Freddie Mac 7/1 ARM Super Conforming 5/2/5");

		}
	};

	public static Integer currentIndex = 0;

	static final Map<String, String[]> FILE_PATTERN_LABEL_HEADER = new HashMap<String, String[]>() {
		{
			put("FNMA CONVENTIONAL FIXED RATE PRODUCTS" ,new String[]{
					FILE_PATTERN_LABEL.get("30_YR_FIXED_CONFORMING-RS_FNMA_"),
					FILE_PATTERN_LABEL.get("20_YR_FIXED_CONFORMING-RS_FNMA_"),
					FILE_PATTERN_LABEL.get("15_YR_FIXED_CONFORMING-RS_FNMA_"),
					FILE_PATTERN_LABEL.get("10_YR_FIXED_CONFORMING-RS_FNMA_"),
					FILE_PATTERN_LABEL.get("30_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_"),
					FILE_PATTERN_LABEL.get("20_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_"),
					FILE_PATTERN_LABEL.get("15_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_"),
					FILE_PATTERN_LABEL.get("10_YR_FIXED_CONFORMING-RS_FNMA_HIBAL_")
			});
			
			put("FNMA CONVENTIONAL ARM PRODUCTS" , new String[]{
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_"),
					FILE_PATTERN_LABEL.get("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_"),
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FNMA_HIBAL_"),
					FILE_PATTERN_LABEL.get("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_HIBAL_")
			});
			
			put("MAMMOTH JUMBO/ HYBRID FIXED AND ARM PRODUCTS" , new String[]{
					FILE_PATTERN_LABEL.get("30_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_"),
					FILE_PATTERN_LABEL.get("15_YR_FIXED_NONCONFORMING-RS_AMERIHOME_JUMBO_"),
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_"),
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_NONCONFORMING__2_2_5_30_YR_ARM-RS_AMERIHOME_JUMBO_IO_")
			});
			
			
			put("CASCADES JUMBO FIXED PRODUCTS" , new String[]{
				FILE_PATTERN_LABEL.get("30_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_"),
				FILE_PATTERN_LABEL.get("15_YR_FIXED_NONCONFORMING-RS_PMAC_JUMBO_")
			});
			put("OLYMPIC FIXED PIGGYBACK PRODUCTS" ,new String[]{
					FILE_PATTERN_LABEL.get("30_YR_FIXED_CONFORMING-RS_OLYMPIC_"),
					FILE_PATTERN_LABEL.get("20_YR_FIXED_CONFORMING-RS_OLYMPIC_"),
					FILE_PATTERN_LABEL.get("15_YR_FIXED_CONFORMING-RS_OLYMPIC_"),
					FILE_PATTERN_LABEL.get("10_YR_FIXED_CONFORMING-RS_OLYMPIC_"),
					FILE_PATTERN_LABEL.get("30_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_"),
					FILE_PATTERN_LABEL.get("20_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_"),
					FILE_PATTERN_LABEL.get("15_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_"),
					FILE_PATTERN_LABEL.get("10_YR_FIXED_CONFORMING-RS_OLYMPIC_HIBAL_")
			});
			
			put("OLYMPIC PIGGYBACK ARM PRODUCTS" , new String[]{
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_"),
					FILE_PATTERN_LABEL.get("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_"),
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_"),
					FILE_PATTERN_LABEL.get("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_OLYMPIC_HIBAL_")
			});
			
			
	
			put("FHLMC CONVENTIONAL FIXED RATE PRODUCTS" ,new String[]{
					FILE_PATTERN_LABEL.get("30_YR_FIXED_CONFORMING-RS_FHLMC_"),
					FILE_PATTERN_LABEL.get("20_YR_FIXED_CONFORMING-RS_FHLMC_"),
					FILE_PATTERN_LABEL.get("15_YR_FIXED_CONFORMING-RS_FHLMC_"),
					FILE_PATTERN_LABEL.get("10_YR_FIXED_CONFORMING-RS_FHLMC_"),
					/*FILE_PATTERN_LABEL.get("30_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_"),
					FILE_PATTERN_LABEL.get("15_YR_FIXED_CONFORMING-RS_FHLMC_SUPCONF_")*/
					
				
			});
			
			put("FHLMC CONVENTIONAL ARM PRODUCTS" , new String[]{
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_"),
					FILE_PATTERN_LABEL.get("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_"),
					FILE_PATTERN_LABEL.get("5_1_1_YR_LIBOR_CONFORMING__2_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_"),
					FILE_PATTERN_LABEL.get("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FHLMC_SUPCONF_")
			});
			
		}
	};
	
	@SuppressWarnings("unused")
	private Boolean isPresent(String pattern) {
		for (String pattrn : FILE_PATTERN) {
			if (pattern.equals(pattrn)) {
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
				// return actualFileName;
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

	public RestResponse buildUIMap(List<FileProductPointRate> list,
	        long fileTimeStamp) {

		
		RestResponse response = new RestResponse();
		Map<String, List<UIEntity>> fromCache = cache.get(fileTimeStamp);
		if (fromCache != null) {
			System.out.println("Returning from cache instance");
			response.setData(fromCache);
			response.setTimestamp(fileTimeStamp);
			return response;
		} else {
			Map<String, List<UIEntity>> lastData = new HashMap<String, List<UIEntity>>();
			if (cache.entrySet() != null) {
				try {
					Map.Entry<Long, Map<String, List<UIEntity>>> entry = cache
					        .entrySet().iterator().next();
					if (entry != null) {
						lastData = entry.getValue();
						System.out.println("Cache has data");
						long time = entry.getKey();
						response.setTimestamp(time);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			Map<String, List<UIEntity>> data = populateMap(list);
			System.out.println("New list size: " + data.size());
			System.out.println("lastData size: " + lastData.size());
			if (data.size() < lastData.size() && lastData.size() > 0) {
				// This is the case if there is any issue in extraction logic
				System.out
				        .println("Size is different, hence returning cached data");
				response.setTimestamp(fileTimeStamp);
				response.setData(lastData);
				return response;
			}
			System.out.println("Updating cache since there is a new data set");
			cache = new HashMap<Long, Map<String, List<UIEntity>>>();
			cache.put(fileTimeStamp, data);
			// If there are too many elements in the cache, remove the oldest.

			if (cache.size() > 10) {
				System.out.println("Too many elements in cache. Removing.");
				clearCache();
				System.out.println("After removing cache...................");
			}
			System.out.println("After removing cache outside if...................");
			response.setTimestamp(fileTimeStamp);
			System.out.println("Folder TimeStamp......................" + response.getTimestamp());
			response.setData(data);
			System.out.println("Folder data......................" + response.getData());
			return response;
		}

	}

	private void clearCache() {
		try {
			Set<Long> keys = cache.keySet();
			// TODO: Remove the old objects from cache
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private Map<String, List<UIEntity>> populateMap(
	        List<FileProductPointRate> list) {

		Map<String, List<UIEntity>> hashMap = new HashMap<String, List<UIEntity>>();

		for (FileProductPointRate fileProductPointRate : list) {

			String rowHeader = getFilePattern(fileProductPointRate
			        .getFileName());
			System.out.println(fileProductPointRate.getFileName());
			if (fileProductPointRate
			        .getFileName()
			        .equals("7_1_1_YR_LIBOR_CONFORMING__5_2_5_30_YR_ARM-RS_FNMA_30DAY_PRICE.csv")) {
				System.out.println("Here");
			}
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
	
	
	
	
	private void attachHeader(Row row , Cell cell , Integer cellNum) {
	    Object[] objects = new Object[]{"Rate" , "15-Day" , "30-Day" , "45-Day" , "60-Day"};
	    for (Object object : objects) {
	    	Cell cellTable = row.createCell(cellNum++);
	        writeValueToCell(object, (HSSFCell)cellTable);
        }
    }

	private Object[] getObjectRow(UIEntity entity){
		return new Object[]{
				entity.getRate() , entity.getCol1Points() , entity.getCol2Points() , 
				entity.getCol3Points() , entity.getCol4Points()
		};
	}
	
	/**
	 * Method to write data in sheet an provide styling to cells along with calculating formula based fields  
	 * @param sheet
	 * @param uiEntityList
	 * @param startRow
	 * @param startCell
	 * @param rowCounter
	 * @return
	 */
	private Integer writeDataTableToSheet(HSSFSheet sheet ,List<UIEntity> uiEntityList , 
				Integer startRow ,Integer startCell , int rowCounter) {
		Integer rowNum = startRow;
	    for (UIEntity uiEntity : uiEntityList) {
	    	Integer cellNum = startCell;
	    	HSSFRow row = null;
	    	
	    	if(rowCounter == 12){
	    		break;
	    	}
	    	
		    	if(sheet.getRow(++rowNum)== null){
		    		row = sheet.createRow(rowNum);
		    		rowCounter++;
	    		}else{
		    		row = sheet.getRow(rowNum);
		    		rowCounter++;
		    	}
			Object[] objects = getObjectRow(uiEntity);
	       		
		       	for (Object object : objects) {
		       			HSSFCell hssfCell = row.createCell(cellNum++);
		       			int tempCellNum = cellNum -1;
		       			if(tempCellNum == 2 || tempCellNum == 8 || tempCellNum == 14){
		       				hssfCell.setCellStyle(startingCellStyle);
		       			}else if (tempCellNum == 6 || tempCellNum == 12 || tempCellNum == 18){
		       				hssfCell.setCellStyle(endingCellStyle);
		       			}else{
		       				hssfCell.setCellStyle(regularCellStyling);
		       			}
		       			writeValueToCell(object , hssfCell);
		        }
			  }
		return rowNum;
    }
	
	/**
	 * MEthod to write data into cells along with values passed
	 * @param object Object
	 * @param cell HSSFCell
	 */
	private void writeValueToCell(Object object , HSSFCell cell){
		if(object instanceof String){
   			cell.setCellValue((String)object);
   		}
   		else if(object instanceof Double){
   			cell.setCellValue((Double)object);
   		}
        else if(object instanceof BigDecimal){
        	cell.setCellValue(String.valueOf(object));
        }
	}
	
	/**
	 * Method to fill the excel sheet with the data from UI
	 * @param list
	 * @param fileTimeStamp
	 * @return
	 */
	public HSSFWorkbook buildUIComponent(List<FileProductPointRate> list,
	        long fileTimeStamp, HSSFWorkbook workBook){
		
		System.out.println("Last modified time: " + fileTimeStamp);
		RestResponse response = new RestResponse();
		File newTempelate = null;
		try {
		Map<String, List<UIEntity>> fromCache = cache.get(fileTimeStamp);
		newTempelate = createCopyOfTempelate();
		
		if (fromCache != null) {
			System.out.println("Returning from cache instance");
			response.setData(fromCache);
			response.setTimestamp(fileTimeStamp);
		} else {
			Map<String, List<UIEntity>> lastData = new HashMap<String, List<UIEntity>>();
			if (cache.entrySet() != null) {
				try {
					Map.Entry<Long, Map<String, List<UIEntity>>> entry = cache
					        .entrySet().iterator().next();
					if (entry != null) {
						lastData = entry.getValue();
						System.out.println("Cache has data");
						long time = entry.getKey();
						response.setTimestamp(time);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			Map<String, List<UIEntity>> data = populateMap(list);
			System.out.println("New list size: " + data.size());
			System.out.println("lastData size: " + lastData.size());
			if (data.size() < lastData.size() && lastData.size() > 0) {
				// This is the case if there is any issue in extraction logic
				System.out
				        .println("Size is different, hence returning cached data");
				response.setData(lastData);
				response.setTimestamp(fileTimeStamp);
			}else{
			System.out.println("Updating cache since there is a new data set");
			cache = new HashMap<Long, Map<String, List<UIEntity>>>();
			cache.put(fileTimeStamp, data);
			// If there are too many elements in the cache, remove the oldest.

			if (cache.size() > 10) {
				System.out.println("Too many elements in cache. Removing.");
				clearCache();
			}
			response.setTimestamp(fileTimeStamp);
			response.setData(data);
							
	}
		}
		workBook = writeDataToExcelSheet(newTempelate, response, workBook);
		workBook.setForceFormulaRecalculation(true);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return workBook;
	}
	
	
	/**
	 * Method to make copy of the original tempelate
	 */
	private File createCopyOfTempelate() throws IOException{
		
		InputStream inStream = null;
		OutputStream outStream = null;
		File orgTempelate = null;
		File newTempelate = null;
		try{
			orgTempelate = new File(CommonConstant.PATH_FOR_ORIGINAL_TEMPELATE);
			File newDir = new File(CommonConstant.TEMP_PATH_FOR_COPIED_TEMPELATE);
			if(!newDir.exists()){
				newDir.mkdir();
			}
			newTempelate = new File(CommonConstant.COPIED_TEMPELATE);
			inStream = new FileInputStream(orgTempelate);
			outStream = new FileOutputStream(newTempelate);
			int i =0;
			while((i = inStream.read()) != -1){
				outStream.write(i);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null != inStream){
			inStream.close();
			inStream =null;
			}
			if(null != outStream){
			outStream.flush();
			outStream.close();
			outStream = null;
			}
		}
		
		return newTempelate;
	}
	
	
	/**
	 * Method to write data in excel sheet
	 */
	private HSSFWorkbook  writeDataToExcelSheet(File file, RestResponse restResponse, HSSFWorkbook workBook) throws IOException, java.text.ParseException{
		
		FileInputStream inStream = null;
		HSSFSheet sheet = null;
		try{
		inStream = new FileInputStream(file);
		workBook = new HSSFWorkbook(inStream);
		//sheet = workBook.getSheet("Sheet1");
		Map<String, List<UIEntity>> data = restResponse.getData();
		
		int startRowNum = 0;
		int newRowNum = 0;
		int rowCounter = 0;
		
		createCellStyleRightBorder(workBook);
		createCellStyleLeftBorder(workBook);
		createCellStyleRegular(workBook);
		
		
		Set<String> filePatternKeys = FILE_PATTERN_LABEL_HEADER.keySet();
		for (String filePatternKey : filePatternKeys) {
				String [] filePatternKeySets = FILE_PATTERN_LABEL_HEADER.get(filePatternKey);
				
				for(String fileHeading : filePatternKeySets){
					if("Fannie Mae 30yr Fixed".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15 ,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;
						writeCurrentDateAndTimeInExcel(sheet, restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Fannie Mae 20yr Fixed".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15 ,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 15yr Fixed".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 10yr Fixed".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 30yr Fixed High Balance".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						writeCurrentDateAndTimeInExcel(sheet,restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Fannie Mae 20yr Fixed High Balance".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 15yr Fixed High Balance".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 10yr Fixed High Balance".equals(fileHeading) && "FNMA CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 5/1 Libor ARM 2/2/5".equals(fileHeading) && "FNMA CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						writeCurrentDateAndTimeInExcel(sheet, restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Fannie Mae 7/1 Libor ARM 2/2/5".equals(fileHeading) && "FNMA CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 5/1 Libor ARM High Balance 2/2/5".equals(fileHeading) && "FNMA CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 7/1 Libor ARM High Balance 5/2/5".equals(fileHeading) && "FNMA CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FNMA Conventional Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Mammoth Jumbo 30 YR Fixed".equals(fileHeading) && "MAMMOTH JUMBO/ HYBRID FIXED AND ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Mammoth Jumbo_Hybrid Fix & Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						writeCurrentDateAndTimeInExcel(sheet,restResponse);
						}
					}
					if("Mammoth Jumbo 15 YR Fixed".equals(fileHeading) && "MAMMOTH JUMBO/ HYBRID FIXED AND ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Mammoth Jumbo_Hybrid Fix & Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Mammoth Non Agency Hybrid 5/1 ARM".equals(fileHeading) && "MAMMOTH JUMBO/ HYBRID FIXED AND ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Mammoth Jumbo_Hybrid Fix & Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Mammoth Non Agency Hybrid 5/1 ARM IO".equals(fileHeading) && "MAMMOTH JUMBO/ HYBRID FIXED AND ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Mammoth Jumbo_Hybrid Fix & Arm");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Cascades Jumbo 30 YR Fixed".equals(fileHeading) && "CASCADES JUMBO FIXED PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Cascades Jumbo Fixed");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						writeCurrentDateAndTimeInExcel(sheet, restResponse);
						}
					}
					if("Cascades Jumbo 15 YR Fixed".equals(fileHeading) && "CASCADES JUMBO FIXED PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Cascades Jumbo Fixed");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					
					if("Fannie Mae 30 Yr Fixed".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack Fixed");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15 ,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;
						writeCurrentDateAndTimeInExcel(sheet, restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Fannie Mae 20 Yr Fixed".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack Fixed");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15 ,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 15 Yr Fixed".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack Fixed");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 10 Yr Fixed".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack Fixed");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 30yr Fixed High Balance".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						writeCurrentDateAndTimeInExcel(sheet,restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Fannie Mae 20 Yr Fixed High Balance".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 15 Yr Fixed High Balance".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 10 Yr Fixed High Balance".equals(fileHeading) && "OLYMPIC FIXED PIGGYBACK PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack High Balance");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 5/1 ARM 2/2/5".equals(fileHeading) && "OLYMPIC PIGGYBACK ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack ARM");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						writeCurrentDateAndTimeInExcel(sheet, restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Fannie Mae 7/1 ARM 5/2/5".equals(fileHeading) && "OLYMPIC PIGGYBACK ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack ARM");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Fannie Mae 5/1 ARM High Balance 2/2/5".equals(fileHeading) && "OLYMPIC PIGGYBACK ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack ARM");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Fannie Mae 7/1 ARM High Balance 5/2/5".equals(fileHeading) && "OLYMPIC PIGGYBACK ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("Olympic PiggyBack ARM");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					
					
					if("Freddie Mac 30 Yr Fixed".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15 ,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;
						writeCurrentDateAndTimeInExcel(sheet, restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Freddie Mac 20 Yr Fixed".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15 ,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Freddie Mac 15 Yr Fixed".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Freddie Mac 10 Yr Fixed".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					/*if("Freddie Mac 30 Yr Fixed Super Conforming".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						writeCurrentDateAndTimeInExcel(sheet,restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}*/
				/*	if("Freddie Mac 20 Yr Fixed Super Conforming".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						writeCurrentDateAndTimeInExcel(sheet,restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}*/
					/*if("Freddie Mac 15 Yr Fixed Super Conforming".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}*/
				/*	if("Freddie Mac 10 Yr Fixed Super Conforming".equals(fileHeading) && "FHLMC CONVENTIONAL FIXED RATE PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional Fixed Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}*/
					
					if("Freddie Mac 5/1 ARM 2/2/5".equals(fileHeading) && "FHLMC CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional ARM Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						writeCurrentDateAndTimeInExcel(sheet, restResponse);
						updateFormulaCellsDependingOnDate(sheet);
						}
					}
					if("Freddie Mac 5/1 ARM Super Conforming 2/2/5".equals(fileHeading) && "FHLMC CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional ARM Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,8,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;	
						}
					}
					if("Freddie Mac 7/1 ARM 5/2/5".equals(fileHeading) && "FHLMC CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional ARM Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 15,14,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
					if("Freddie Mac 7/1 ARM Super Conforming 5/2/5".equals(fileHeading) && "FHLMC CONVENTIONAL ARM PRODUCTS".equals(filePatternKey)){
						sheet = workBook.getSheet("FHLMC Conventional ARM Rate");
						if(null != sheet){
						newRowNum = writeDataTableToSheet(sheet ,data.get(fileHeading), 40,2,rowCounter);
						startRowNum = newRowNum;
						rowCounter = 0;		
						}
					}
				}
        }
		
	}catch(IOException e){
		e.printStackTrace();
	}finally{
		if(null != inStream){
		inStream.close();
		inStream = null;
		}
		if(null != workBook){
			workBook.close();
		}
	}
		
		return workBook;
}
	
	/**
	 * Method to create cell styling
	 */
	private void createCellStyleRightBorder(HSSFWorkbook workbook){
		
		endingCellStyle = workbook.createCellStyle();
		endingCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		endingCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		endingCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return;
	}
	
	/**
	 * Method to create cell styling
	 */
	private void createCellStyleLeftBorder(HSSFWorkbook workbook){
		
		startingCellStyle = workbook.createCellStyle();
		startingCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		startingCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		startingCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return ;
	}
	
	
	/**
	 * Method to create cell styling
	 */
	private void createCellStyleRegular(HSSFWorkbook workbook){
		
		regularCellStyling = workbook.createCellStyle();
		regularCellStyling.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return ;
	}
	
	/**
	 * Method to write date in excel workbook
	 * @param workBook
	 */
	private void writeCurrentDateAndTimeInExcel(HSSFSheet sheet, RestResponse response){
		
		Integer startRow = 9;
		Integer startCell = 2;
		HSSFRow row = null;
		HSSFCell cell = null;
		String currentDate  = getCurrentDate();
		int indedxForTZ = currentDate.indexOf("+");
		currentDate = currentDate.substring(0,indedxForTZ)+ "GMT " + currentDate.substring(indedxForTZ,currentDate.length());
		StringBuilder sb = new StringBuilder("Lending Rates as of: ");
		sb.append(currentDate);
		String timeZone = getCurrentTimeZone();
		sb.append("(");
		sb.append(timeZone);
		sb.append(")");
		
		if(null == sheet.getRow(startRow)){
			row = sheet.createRow(startRow);
		}else{
			row = sheet.getRow(startRow);
		}
		
		if(null != row){
			cell = row.createCell(startCell);
		}
		
		writeValueToCell(sb.toString(), cell);	
		
		
	}
	
	/**
	 * Method to get current date in a string
	 */
	private String getCurrentDate(){
		
		DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm:ss Z");
		Date date = new Date();
		String textDate = dateFormat.format(date);
		return textDate;		
	}
	
	
	/**
	 * Method to add date to given date
	 * @param currentDate String 
	 * @param numOfDays	int
	 * @return String
	 * @throws java.text.ParseException
	 */
	private String addDaysToCurrentDate(String currentDate, int numOfDays) throws  java.text.ParseException{
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = formatter.parse(currentDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, numOfDays);
		date = cal.getTime();
		String newDate = formatter.format(date);
		return newDate;
		
	}
	
	/**
	 * Method to update formula cells dependent on date
	 * @param sheet HSSFSheet
	 * @throws java.text.ParseException
	 */
	private void updateFormulaCellsDependingOnDate(HSSFSheet sheet) throws java.text.ParseException{
		makeChangesInFormulaCell(116, 18, sheet);
		//makeChangesInFormulaCell(288, 18, sheet);
		
	}
	 /**
	  * Method to make changes in fields depending on date
	  * @param startRow
	  * @param startCell
	  * @param sheet
	  * @throws java.text.ParseException
	  */
	private void makeChangesInFormulaCell(int startRow, int startCell, HSSFSheet sheet) throws java.text.ParseException{
		
		int endRow = startRow + 3;
		int noOfDays =0;
		HSSFRow row = null;
		HSSFCell cell = null;
		
		while(startRow <= endRow){
			
			if(startRow == 116){
				noOfDays = 15;
			}else if(startRow == 117){
				noOfDays = 30;
			}else if(startRow == 118){
				noOfDays = 45;
			}else if(startRow == 119){
				noOfDays = 60;
			}
			
			if(null == sheet.getRow(startRow)){
				row = sheet.createRow(startRow);
			}else{
				row = sheet.getRow(startRow);
			}
			
			cell = row.getCell(startCell);
			String currentDate = getCurrentDateForFormulaCells();
			String newDate = addDaysToCurrentDate(currentDate, noOfDays);
			writeValueToCell(newDate, cell);
			startRow++;
		}
		
	}
	
	/**
	 * Method to get the current time zone 
	 * @return
	 */
	private String getCurrentTimeZone(){
		
		Calendar cal = Calendar.getInstance();
		  TimeZone timeZone = cal.getTimeZone();
		  String currentTimeZone = timeZone.getDisplayName();
		
		return currentTimeZone;
	}
	
	/**
	 * Method to get date in format MM/dd/yyyy
	 * @return
	 */
	private String getCurrentDateForFormulaCells(){
		
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String textDate = dateFormat.format(date);
		return textDate;	
		
	}
	
	
}
