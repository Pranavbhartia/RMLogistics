var tableData = [];
var currentFolderTime = null;
function paintRatesTablePage(data) {
	currentFolderTime = data.folderTimeStamp;
	var folderDate = new Date(currentFolderTime);
	$("#folderCurrentTimeStamp").html(folderDate);

	tableData = data.fileDetailList;
	console.info(data.fileName);
	$('#main-container').html('');
	var wrapper = $('<div>').attr({
		"class" : "rate-table-wrapper row clearfix"
	});

	// var ObjectKeys = Object.keys(data);

	/*
	 * for(var i=0; i<ObjectKeys.length;i++){ var tableArray =
	 * data[ObjectKeys[i]]; var title =ObjectKeys[i]; var table =
	 * getRatesTable(tableArray,title); wrapper.append(table); }
	 */

	$('#main-container').html(wrapper);
	appendFNMAConventionalFIXEDTableWrapper(wrapper);
	appendFNMAConventionalARMTableWrapper(wrapper);
	appendMAMMOTHTableWrapper(wrapper);
	appendCASCADESTableWrapper(wrapper)
	/*
	 * $('.rate-table-wrapper').masonry({ itemSelector:
	 * '.rate-table-wrapper-cont'
	 * 
	 * });
	 */
}



// function to append FNMA Fixed Rate tables
function appendFNMAConventionalFIXEDTableWrapper(element) {
	var wrapper = $('<div>').attr({
		"class" : "table-wrapper clearfix"
	});

	var header = $('<div>').attr({
		"class" : "table-wrapper-header"
	}).html("FNMA CONVENTIONAL FIXED RATE PRODUCTS");

	var tableCont = $('<div>').attr({
		"class" : "table-container"
	});

	var table1 = getRatesTable(tableData["Fannie Mae 30yr Fixed"],
			"Fannie Mae 30yr Fixed");
	var table2 = getRatesTable(tableData["Fannie Mae 20yr Fixed"],
			"Fannie Mae 20yr Fixed");
	var table3 = getRatesTable(tableData["Fannie Mae 15yr Fixed"],
			"Fannie Mae 15yr Fixed");
	var table4 = getRatesTable(tableData["Fannie Mae 10yr Fixed"],
			"Fannie Mae 10yr Fixed");
	var table5 = getRatesTable(tableData["Fannie Mae 30yr Fixed High Balance"],
			"Fannie Mae 30yr Fixed High Balance");
	var table6 = getRatesTable(tableData["Fannie Mae 20yr Fixed High Balance"],
			"Fannie Mae 20yr Fixed High Balance");
	var table7 = getRatesTable(tableData["Fannie Mae 15yr Fixed High Balance"],
			"Fannie Mae 15yr Fixed High Balance");
	var table8 = getRatesTable(tableData["Fannie Mae 10yr Fixed High Balance"],
			"Fannie Mae 10yr Fixed High Balance");

	tableCont.append(table1).append(table2).append(table3).append(table4)
			.append(table5).append(table6).append(table7).append(table8);
	wrapper.append(header).append(tableCont);

	// TODO:Price Adjustment tables

	$(element).append(wrapper);

	var priceWrapper = getPriceAdjustmentWrapper();

	$(element).append(priceWrapper);
}

function getPriceAdjustmentWrapper() {
	var wrapper = $('<div>').attr({
		"class" : "price-table-wrapper price-table-page-1"
	});

	var header = $('<div>').attr({
		"class" : "price-table-wrapper-header"
	}).html("PRICE ADJUSTMENTS");

	var container = $('<div>').attr({
		"class" : "price-table-cont-wrapper"
	});

	container.append(getLTVTable(false));

	return wrapper.append(header).append(container);

}

function getPriceAdjustmentWrapper1() {
	var wrapper = $('<div>').attr({
		"class" : "price-table-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "price-table-wrapper-header"
	}).html("PRICE ADJUSTMENTS");

	var container = $('<div>').attr({
		"class" : "price-table-cont-wrapper"
	});

	container.append(getLTVTable(true));
	return wrapper.append(header).append(container);

}

function getLTVTable(addHighBalArm) {
	var tableWrapper = $('<div>').attr({
		"class" : "price-table"
	});

	var header = $('<div>').attr({
		"class" : "price-table-header"
	}).html("LTV / FICO (Terms > 15 years only)");

	var row1 = $('<div>').attr({
		"class" : "clearfix"
	});
	var table1 = getLTVTable1();
	row1.append(table1);
	var ltvDescTable = getLTVDescTable(addHighBalArm);
	row1.append(ltvDescTable);

	var row2 = $('<div>').attr({
		"class" : "clearfix price-table-wrap-row"
	});
	var refinanceTable = getLTVTable3();
	row2.append(refinanceTable);

	var secondaryFinancingTable = getLTVTable4();
	row2.append(secondaryFinancingTable);

	var lockExpirationTable = getLTVTable5();
	row2.append(lockExpirationTable);

	var otherAdjustmentsTable = getLTVTable6();
	row2.append(otherAdjustmentsTable);
	
	if(!addHighBalArm){
		var row3 = $('<div>').attr({
			"class" : "clearfix price-table-wrap-row"
		});
	
	
	var table1 = $('<div>').attr({
		"class" : "adjuster-table-wrapper float-left"
	}).html(getLTVTable7());
	
	var blueStreamAdv = $('<div>').attr({
		"class" : "adjuster-table-wrapper float-left ltv-table8"
	}).html(getLTVTable8());
	
	row3.append(table1).append(blueStreamAdv);
	}

	var note = "All loan level price adjustments are cumulative\n"
			+ "Prices are indicative and subject to change without notice. Please log into Blustream Lending portal to obtain live lock pricing\n"
			+ "Not all price adjustments are effective for all products. Please refer to Blustream Lending product guide for complete eligibility rules.\n"
			+ "Intended for use by mortgage professionals only and should not be distributed to borrowers, as defined by Section 226.2 of Regulation Z";

	var noteCont = $('<div>').attr({
		"class" : "note-txt"
	}).html(note);

	return tableWrapper.append(header).append(row1).append(row2).append(row3).append(
			noteCont);
}

function getLTVTable8(){

	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container-red-border"
	});
	var row1 = "<tr  class='th1Bold'>" + "<th colspan=8><b>Blustream Advantage</b></th>" + "<td></td>";
	var row2 = "<tr  class='th1Bold'>" + "<th colspan=8><= 75% LTV & Credit Score >= 720</th>";
	tableCont.append(row1).append(row2);
	var tableArray = 
	
	[ [ "Conforming", "(0.500)" ], 
      [ "High Balance", "(0.250)" ]
      ];
	

	var dataRows = getCascadeTableDataRows(tableArray);
	tableCont.append(dataRows);
	var row3 = "<tr  class='th1Bold'>" + "<th colspan=8><b>2 Years Transcripts Required</b></th>" + "<td></td>";
	var row4 = "<tr  class='th1Bold'>" + "<th colspan=8><b>High Balance Max DTI 43%</b></th>" + "<td></td>";
	var row5 = "<tr  class='th1Bold'>" + "<th colspan=8><b>High Balance SFR O/O & 2nd Home Only</b></th>" + "<td></td>";
	var row6 = "<tr  class='th1Bold'>" + "<th colspan=8><b>High Balance Tradeline Requirement:</b></th>" + "<td></td>";
	var row7 = "<tr  class='th1Bold'>" + "<th colspan=8><b>3 Tradelines for 24 Months</b></th>" + "<td></td>";
	tableCont.append(row3).append(row4).append(row5).append(row6).append(row7);
	return tableCont;
	
}

function getLTVTable1() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left"
	});

	var tableHeaderArray = [ "", "<60", "60.01-70", "70.01-75", "75.01-80",
			"80.01-85", "85.01-90", "90.01-95", "95.01-97" ];

	var tableArray = [
			[ ">=740", "0.250", "0.000", "0.000", "(0.250)", "(0.250)",
					"(0.250)", "(0.250)", "(0.250)" ],
			[ "720-739", "0.250", "0.000", "(0.250)", "(0.500)", "(0.500)",
					"(0.500)", "(0.500)", "(0.500)" ],
			[ "700-719", "0.250", "(0.500)", "(0.750)", "(1.000)", "(1.000)",
					"(1.000)", "(1.000)", "(1.000)" ],
			[ "680-699", "0.000", "(0.500)", "(1.250)", "(1.750)", "(1.500)",
					"(1.250)", "(1.250)", "(1.000)" ],
			[ "660-679", "0.000", "(1.000)", "(2.000)", "(2.500)", "(2.750)",
					"(2.250)", "(2.250)", "(1.750)" ],
			[ "640-659", "(0.500)", "(1.250)", "(2.500)", "(3.000)", "(3.250)",
					"(2.750)", "(2.750)", "(2.250)" ],
			[ "620-639", "(0.500)", "(1.500)", "(3.000)", "(3.000)", "(3.250)",
					"(3.250)", "(3.250)", "(3.000)" ] ];

	var tableHeaderRow = getLTVTableHeaderRow(tableHeaderArray);
	tableCont.append(tableHeaderRow);
	
	var reverseParenthesisArray = getReverseParenthesisArray(tableArray)
	
	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	for (var i = 0; i < reverseParenthesisArray.length; i++) {
		var tableRow = getLTVTableRow(reverseParenthesisArray[i]);
		
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;
}


function getReverseParenthesisArray(array){
	var newArray = new Array();
	for (var i = 0; i < array.length; i++) {
		var rowObject = array[i];
		for(var j in rowObject){
			if(j==0) continue;
			rowObject[j] = inverseParanthesis(rowObject[j]);
		}
		newArray.push(rowObject)
	}
	return newArray;
}
function inverseParanthesis(input){
	if(input == null ){
		return null;
	}
	if(input.indexOf('(')!=-1 && input.indexOf(')')!=-1){
		input = input.replace('(','');
		input = input.replace(')','');
	}else{
		input = '('+input+')';
	}
	return input;
}

function getLTVDescTable(addHighBalArm) {

	var tableArray = [ {
		"desc" : "LTV>95",
		"value" : "0.500"
	}, {
		"desc" : "Investment Property LTV <= 75 ",
		"value" : "1.750"
	}, {
		"desc" : "Investment Property LTV >75 and <=80",
		"value" : "3.000"
	}, {
		"desc" : "Investment Property LTV >80",
		"value" : "3.750"
	}, {
		"desc" : "2-4 Units",
		"value" : "1.000"
	}, {
		"desc" : "Attached Condo >75 LTV & Term > 15yrs",
		"value" : "0.750"
	}, {
		"desc" : "HighBal Cashout Refi",
		"value" : "1.000"
	} ];
	if(addHighBalArm){
		var array = new Object(); 
		array.desc = "HighBal ARM";
		array.value = "0.750";
		
		tableArray.push(array);
	}
	
	var table = $('<div>').attr({
		"class" : "ltv-desc-table float-right"
	});

	for (var i = 0; i < tableArray.length; i++) {
		var row = $('<div>').attr({
			"class" : "ltv-desc-table-row"
		});

		var col1 = $('<div>').attr({
			"class" : "ltv-desc-table-td"
		}).html(tableArray[i].desc);

		var col2 = $('<div>').attr({
			"class" : "ltv-desc-table-td"
		}).html(tableArray[i].value);
		row.append(col1).append(col2);
		table.append(row);
	}

	return table;

}

function getLTVTable3() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("Cash Out Refinance");
	tableCont.append(hedaer);
	var tableHeaderArray = [ "", "<=60", "60.01-70", "70.01-75", "75.01-80",
			"80.01-85" ];

	var tableArray = [
			[ ">=740", "0.000", "(0.250)", "(0.250)", "(0.500)", "(0.625)" ],
			[ "720-739", "0.000", "(0.625)", "(0.625)", "(0.750)", "(1.500)" ],
			[ "700-719", "0.000", "(0.625)", "(0.625)", "(0.750)", "(1.500)" ],
			[ "680-699", "0.000", "(0.750)", "(0.750)", "(1.375)", "(2.500)" ],
			[ "660-679", "(0.250)", "(0.750)", "(0.750)", "(1.500)", "(2.500)" ],
			[ "640-659", "(0.250)", "(1.250)", "(1.250)", "(2.250)", "(3.000)" ],
			[ "620-639", "(0.250)", "(1.250)", "(1.250)", "(2.250)", "(3.000)" ] ];

	var tableHeaderRow = getLTVTableHeaderRow(tableHeaderArray);
	tableCont.append(tableHeaderRow);

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	var reverseTableArray = getReverseParenthesisArray(tableArray);
	
	for (var i = 0; i < reverseTableArray.length; i++) {
		var tableRow = getLTVTableRow(reverseTableArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;
}

function getLTVTable4() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left ltv-table4"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("Loans with Secondary Financing");
	tableCont.append(hedaer);

	var tableArray = [ [ "LTV", "CLTV", "<720", ">=720" ],
			[ "<=65", "80.01-95", "(0.500)", "(0.250)" ],
			[ "65.01-75", "80.01-95", "(0.750)", "(0.500)" ],
			[ "75.01-95", "90.01-95", "(1.000)", "(0.750)" ],
			[ "75.01-90", "75.01-90", "(1.000)", "(0.750)" ],
			[ "<=95", "95.01-97", "(1.500)", "(1.500)" ] ];

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});
	var reverseTableArray = getReverseParenthesisArray(tableArray);
	for (var i = 0; i < reverseTableArray.length; i++) {
		var tableRow = getLTVTableRow(reverseTableArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;
}

function getLTVTable5() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left ltv-table5"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("Lock Expiration");
	tableCont.append(hedaer);

	var tableArray = [ [ "15 Days", getDateByNumberOfDaysFolderDays(15) ],
			[ "30 Days", getDateByNumberOfDaysFolderDays(30) ],
			[ "45 Days", getDateByNumberOfDaysFolderDays(45) ],
			[ "60 Days", getDateByNumberOfDaysFolderDays(60) ],

	];

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	for (var i = 0; i < tableArray.length; i++) {
		var tableRow = getLTVTableRow(tableArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;
}

function addDays(theDate, days) {
	return new Date(theDate.getTime() + days * 24 * 60 * 60 * 1000);
}

function getDateByNumberOfDaysFolderDays(days) {
	var someDate = addDays(new Date(currentFolderTime), days);

	var dd = someDate.getDate();
	var mm = someDate.getMonth() + 1;
	var y = someDate.getFullYear();

	var someFormattedDate = mm + '/' + dd + '/' + y;
	return someFormattedDate;
}

function getLTVTable6() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left ltv-table6"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("Other Adjustments");
	tableCont.append(hedaer);

	var tableArray = [ [ "Escrow Waiver Fee", "0.125" ] , 
	                   [ "Loan amount <$100k", "0.75" ]
	                   ];

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	for (var i = 0; i < tableArray.length; i++) {
		var tableRow = getLTVTableRow(tableArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;
}

function getLTVTable7(){
	
	var table = "<table class='adjuster-table' align='center'>";
	var row1 = "<tr><th colspan=8 class : 'price-table-header'>"
			+ "LPMI >20 Year Fixed Rate Purchase, Primary Residence" + "</th></tr>";
	
		var row2 = "<tr  class='th1Bold'>"
		 + "<th><b>LTV</b></th><th><b>760+</b></th><th><b>740-759</b></th><th><b>720-739</b></th><th><b>700-719</b></th><th><b>680-699</b></th><th><b>660-679</b></th><th><b>640-659</b></th>";
		var tableValuesArray1 = [
	                         ["97% to 95.01%", "1.70", "2.45", "3.10", "3.75", "4.70", "6.25", "7.00"],
	                         ["95% to 90.01%","1.40", "1.95", "2.45", "2.90", "3.60", "4.80", "5.35"],
	                         ["90% to 85.01%","1.00", "1.35", "1.65", "1.95", "2.40", "3.25", "3.609"],
	                         ["85% and below%","0.52", "0.62", "0.70", "0.81", "0.95", "1.25", "1.40"]];
							 
		var dataRows = getCascadeTableDataRows(tableValuesArray1);
			table += row1 + row2 + dataRows;
			
			row1 = "<tr><th colspan=8 class : 'price-table-header'>"
				+ "LPMI >LPMI <= 20 Year Fixed Rate Purchase, Primary Residence" + "</th></tr>";
			
			 tableValuesArray1 =  [
				                         ["97% to 95.01%", "1.15", "1.60", "2.00", "2.45", "3.15", "4.25", "5.00"],
				                         ["95% to 90.01%","0.95", "1.30", "1.60", "1.90", "2.40", "3.15", "3.70"],
				                         ["90% to 85.01%","0.70", "0.90", "1.10", "1.35", "1.60", "2.10", "2.55"],
				                         ["85% and below%","0.45", "0.53", "0.60", "0.65", "0.80", "1.00", "1.15"]];
			 
			 
			dataRows = getCascadeTableDataRows(tableValuesArray1);
			
			table += row1 + row2 + dataRows;
			
			row2 = "<tr class='th1Bold'>" + "<th><b>Adjustments</b></th><th><b>760+</b></th><th><b>740-759</b></th><th><b>720-739</b></th><th><b>700-719</b></th><th><b>680-699</b></th><th><b>660-679</b></th><th><b>640-659</b></th>";
			
			tableValuesArray1 =  [
				                         ["Rate / Term Refinance", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00"],
				                         ["Loan Size > $417,000", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00"],
				                         ["Second Home","0.25", "0.25", "0.49", "0.70", "0.70", "1.23", "1.23"]];
			
			var dataRows = getCascadeTableDataRows(tableValuesArray1);
			var row3 = "<tr><td colspan=8  class : 'note-txt'>"
				+ "*LPMI PRICING ABOVE IS NOT AVAILABLE ON YOSEMITE PRODUCT" + "</td></tr>";
			table += row2 + dataRows + row3 + "</table>";
			
			return table;
	
}


function getLTVTableHeaderRow(tableHeaderArray) {
	var row = $('<div>').attr({
		"class" : "price-table-row price-table-header-row"
	});

	for (var i = 0; i < tableHeaderArray.length; i++) {
		var col = $('<div>').attr({
			"class" : "price-table-th"
		}).html(tableHeaderArray[i]);
		row.append(col);
	}

	return row;
}

function getLTVTableRow(rowObj) {
	var row = $('<div>').attr({
		"class" : "price-table-row"
	});
	
	for (var i = 0; i < rowObj.length; i++) {
		var col = $('<div>').attr({
			"class" : "price-table-td"
		}).html(rowObj[i]);
		row.append(col);
	}

	return row;
}

// function to append FNMA ARM Rate tables
function appendFNMAConventionalARMTableWrapper(element) {
	var wrapper = $('<div>').attr({
		"class" : "table-wrapper clearfix"
	});

	var pageHeader = $('<div>').attr({
		"class" : "hide print-page-header"
	}).html($('#header-wrapper').html());
	
	var header = $('<div>').attr({
		"class" : "table-wrapper-header"
	}).html("FNMA CONVENTIONAL ARM PRODUCTS");

	var tableCont = $('<div>').attr({
		"class" : "table-container"
	});

	var table1 = getRatesTable(tableData["Fannie Mae 5/1 Libor ARM 2/2/5"],
			"Fannie Mae 5/1 Libor ARM 2/2/5");
	var table2 = getRatesTable(
			tableData["Fannie Mae 5/1 Libor ARM High Balance 2/2/5"],
			"Fannie Mae 5/1 Libor ARM High Balance 2/2/5");
	var table3 = getRatesTable(tableData["Fannie Mae 7/1 Libor ARM 2/2/5"],
			"Fannie Mae 7/1 Libor ARM 2/2/5");
	var table4 = getRatesTable(
			tableData["Fannie Mae 7/1 Libor ARM High Balance 5/2/5"],
			"Fannie Mae 7/1 Libor ARM High Balance 5/2/5");

	tableCont.append(table1).append(table2).append(table3).append(table4);
	wrapper.append(pageHeader).append(header).append(tableCont);

	// TODO:Price Adjustment tables
	$(element).append(wrapper);

	var priceWrapper = getPriceAdjustmentWrapper1();
	$(element).append(priceWrapper);
}

// function to append MAMMOTH Rate tables
function appendMAMMOTHTableWrapper(element) {
	var wrapper = $('<div>').attr({
		"class" : "table-wrapper clearfix mamoth-table-wrapper"
	});

	var pageHeader = $('<div>').attr({
		"class" : "hide print-page-header"
	}).html($('#header-wrapper').html());
	
	var header = $('<div>').attr({
		"class" : "table-wrapper-header"
	}).html("MAMMOTH JUMBO/ HYBRID FIXED AND ARM PRODUCTS");

	var tableCont = $('<div>').attr({
		"class" : "table-container"
	});

	var table1 = getRatesTable(tableData["Mammoth Jumbo 30 YR Fixed"],
			"Mammoth Jumbo 30 YR Fixed");
	var table2 = getRatesTable(tableData["Mammoth Jumbo 15 YR Fixed"],
			"Mammoth Jumbo 15 YR Fixed");
	var table3 = getRatesTable(tableData["Mammoth Non Agency Hybrid 5/1 ARM"],
			"Mammoth Non Agency Hybrid 5/1 ARM");
	var table4 = getRatesTable(
			tableData["Mammoth Non Agency Hybrid 5/1 ARM IO"],
			"Mammoth Non Agency Hybrid 5/1 ARM IO");

	tableCont.append(table1).append(table2).append(table3).append(table4);
	wrapper.append(pageHeader).append(header).append(tableCont);

	// TODO:Price Adjustment tables
	$(element).append(wrapper);

	var adjustmentTableWrapper = getMammothAdjusters();
	$(element).append(adjustmentTableWrapper);
}

function getMammothAdjusters() {
	var container = $('<div>').attr({
		"class" : "mammoth-adjustment-wrapper"
	});

	var fixedAdjusterCont = $('<div>').attr({
		"class" : "mammoth-adjustment-cont"
	});

	var fixedAdjusterHeader = $('<div>').attr({
		"class" : "price-table-wrapper-header"
	}).html("MAMMOTH FIXED PRICE ADJUSTMENTS");

	var fixedAdjusterRow = $('<div>').attr({
		"class" : "mammoth-adjustment-row clearfix"
	});

	var table1 = getMammothFixedAdjustersTable1();
	var table2 = getMammothFixedAdjustersTable2();

	fixedAdjusterRow.append(table1);
	fixedAdjusterRow.append(table2);
	fixedAdjusterCont.append(fixedAdjusterHeader).append(fixedAdjusterRow);

	var armAdjusterCont = $('<div>').attr({
		"class" : "mammoth-adjustment-cont"
	});

	var armAdjusterHeader = $('<div>').attr({
		"class" : "price-table-wrapper-header"
	}).html("MAMMOTH ARM PRICE ADJUSTMENTS");

	var armAdjusterRow = $('<div>').attr({
		"class" : "mammoth-adjustment-row clearfix"
	});

	var table3 = getMammothARMAdjustersTable1();
	var table4 = getMammothARMAdjustersTable2();

	armAdjusterRow.append(table3).append(table4);

	armAdjusterCont.append(armAdjusterHeader).append(armAdjusterRow);

	return container.append(fixedAdjusterCont).append(armAdjusterCont);
}

function getMammothFixedAdjustersTable1() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left mammoth-table1"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("FICO / LTV/CLTV Adjustment");
	tableCont.append(hedaer);
	var tableHeaderArray = [ "", "<=60", "60.01-65", "65.01-70", "70.01-75","75.01-80" ];

	var tableArray = [
			[ ">=760", "0.500", "0.375", "0.250", "0.000", "(0.375)" ],
			[ "740-759", "0.375", "0.250", "0.125", "(0.250)", "(0.500)" ],
			[ "720-739", "0.250", "0.125", "0.000", "(0.500)", "(1.000)" ],
			[ "700-719", "0.000", "(0.250)", "(0.250)", "(0.875)", "(1.500)" ] ];

	var tableHeaderRow = getLTVTableHeaderRow(tableHeaderArray);
	tableCont.append(tableHeaderRow);

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	var reverseParenthesisArray = getReverseParenthesisArray(tableArray);
	
	for (var i = 0; i < reverseParenthesisArray.length; i++) {
		var tableRow = getLTVTableRow(reverseParenthesisArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;

}

function getMammothFixedAdjustersTable2() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left mammoth-table2"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("Price Adjustment");
	tableCont.append(hedaer);

	var tableArray = [ 
	        [ "Purchase", "(.375)" ],
			[ "Refinance - Non-Cashout", "0.00" ],
			[ "Refinance - Cash-out", ".500" ], 
			[ "2 Unit", ".500" ], 
			[ "Second Home", ".500" ],
			[ "Non-CA State Adjuster", "(.250)" ] 
	];

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	for (var i = 0; i < tableArray.length; i++) {
		var tableRow = getLTVTableRow(tableArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;

}

function getMammothARMAdjustersTable1() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left mammoth-table3"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("FICO / LTV/CLTV Adjustment");
	tableCont.append(hedaer);
	var tableHeaderArray = [ "", "<=60", "60.01-65", "65.01-70", "70.01-75",
			"75.01-80","80.01-85", "85.01-89.90" ];

	var tableArray = [
			[ ">=760", "0.000", "0.000", "0.000", "0.000", "0.000",
					"2.250", "3.250" ],
			[ "740-759", "0.000", "0.000", "0.000", "0.000", "0.000",
			  "2.250", "3.250" ],
			[ "720-739", "0.000", "0.000", "0.000", "0.000", "0.000", "", "" ],
			[ "700-719", "0.000", "0.000", "0.000", "0.000", "0.000",
					"", "" ] ];

	var tableHeaderRow = getLTVTableHeaderRow(tableHeaderArray);
	tableCont.append(tableHeaderRow);

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	//var reverseParenthesisArray = getReverseParenthesisArray(tableArray);
	
	for (var i = 0; i < tableArray.length; i++) {
		var tableRow = getLTVTableRow(tableArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;

}

function getMammothARMAdjustersTable2() {
	var tableCont = $('<div>').attr({
		"class" : "ltv-table-container float-left mammoth-table4"
	});

	var hedaer = $('<div>').attr({
		"class" : "price-table-header"
	}).html("Price / LTV/CLTV Adjustments");
	tableCont.append(hedaer);
	var tableHeaderArray = [ "", "<=60", "60.01-70", "65.01-70", "70.01-75",
			"75.01-80", "80.01-85", "85.01-89.90" ];

	var tableArray = [
			[ "Purchase", "0.000", "0.000", "0.000", "0.000", "0.000", "0.000",
					"0.000" ],
			[ "Cash Out", "0.000", "0.000", "0.120", "", "", "", "" ],
			[ "2 Unit", "0.250", "0.250", "0.375", "0.500", "", "", "" ],
			[ "2nd Home", "0.125", "0.125", "0.125", "0.125",
					"0.125", "", "" ],
			[ "Investor", "0.000", "0.250", "0.375", "", "", "", "" ],
			[ "DTI > 40%", "0.125", "0.125", "0.125", "0.125", "0.125",
					"0.250", "0.375" ] ];

	var tableHeaderRow = getLTVTableHeaderRow(tableHeaderArray);
	tableCont.append(tableHeaderRow);

	var tableRowCont = $('<div>').attr({
		"class" : "price-tr-wrapper"
	});

	//var reverseParenthesisArray = getReverseParenthesisArray(tableArray);
	
	for (var i = 0; i < tableArray.length; i++) {
		var tableRow = getLTVTableRow(tableArray[i]);
		tableRowCont.append(tableRow);
	}

	tableCont.append(tableRowCont);

	return tableCont;

}

// function to append CASCADES Rate tables
function appendCASCADESTableWrapper(element) {
	var wrapper = $('<div>').attr({
		"class" : "table-wrapper clearfix"
	});

	var pageHeader = $('<div>').attr({
		"class" : "hide print-page-header"
	}).html($('#header-wrapper').html());
	
	var header = $('<div>').attr({
		"class" : "table-wrapper-header"
	}).html("CASCADES JUMBO FIXED PRODUCTS");

	var tableCont = $('<div>').attr({
		"class" : "table-container"
	});

	var table1 = getRatesTable(tableData["Cascades Jumbo 30 YR Fixed"],
			"Cascades Jumbo 30 YR Fixed");
	var table2 = getRatesTable(tableData["Cascades Jumbo 15 YR Fixed"],
			"Cascades Jumbo 15 YR Fixed");

	tableCont.append(table1).append(table2);
	wrapper.append(pageHeader).append(header).append(tableCont);

	// TODO : Price Adjustment tables
	$(element).append(wrapper);

	var adjusters = getCascadeAdjusters();
	$(element).append(adjusters);
}

function getCascadeAdjusters() {
	var container = $('<div>').attr({
		"class" : "cascade-adjustment-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "price-table-wrapper-header"
	}).html("CASCADES PRICE ADJUSTMENTS");

	var row1 = $('<div>').attr({
		"class" : "clearfix cascade-row1"
	});

	var table1 = $('<div>').attr({
		"class" : "adjuster-table-wrapper float-left"
	}).html(getCascaseAdjusterTable1());

	var table2 = $('<div>').attr({
		"class" : "adjuster-table-wrapper float-right"
	}).html(getCascaseAdjusterTable2());

	row1.append(table1).append(table2);

	var row2 = $('<div>').attr({
		"class" : "clearfix cascade-row2"
	});

	var table3 = $('<div>').attr({
		"class" : "adjuster-table-wrapper float-left"
	}).html(getCascaseAdjusterTable3());

	var table4 = $('<div>').attr({
		"class" : "adjuster-table-wrapper float-left cascade-adjustment-table4"
	}).html(getCascaseAdjusterTable4());

	var table5 = $('<div>').attr({
		"class" : "adjuster-table-wrapper float-right"
	}).html(getCascaseAdjusterTable5());

	row2.append(table3).append(table4).append(table5);

	return container.append(header).append(row1).append(row2);
}

function getCascaseAdjusterTable1() {
	var table = "<table class='adjuster-table'>";

	var row1 = "<tr><th colspan=7 class='th1'>"
			+ "FICO/LTV LLPAs (Loan Amount <= $1.0M)" + "</th></tr>";
	var row2 = "<tr><th rowspan=2>FICO Range</th>"
			+ "<th colspan=6>LTV Range</th></tr>";
	var row3 = "<tr><th><= 60.00%</th><th>60.01-65.00%</th><th>65.01-70.00%</th><th>70.01-75.00%</th><th>75.01-80.00%</th><th>80.01-85.00%</th></tr>";

	var tableData = [
			[ ">=760", "0.375", "0.25", "(0.000)", "(0.125)", "(0.250)", "(0.000)" ],
			[ "740-759", "0.125", "(0.000)", "(0.125)", "(0.375)", "(0.500)",
					"(0.250)" ],
			[ "720-739", "(0.000)", "(0.250)", "(0.375)", "(0.625)", "(0.750)", "(n/a)" ],
			[ "700-719", "(0.375)", "(0.625)", "(0.875)", "(n/a)", "(n/a)", "(n/a)" ] ];

	var reverseParenthesisArray = getReverseParenthesisArray(tableData);
	
	var dataRows = getCascadeTableDataRows(tableData);

	table += row1 + row2 + row3 + dataRows + "</table>"

	return table;
}

function getCascaseAdjusterTable2() {
	var table = "<table class='adjuster-table'>";

	var row1 = "<tr><th colspan=7 class='th1'>" + "LLPAs by Product Feature"
			+ "</th></tr>";
	var row2 = "<tr><th rowspan=2>Product Feature</th>"
			+ "<th colspan=6>LTV Range</th></tr>";
	var row3 = "<tr><th><= 60.00%</th><th>60.01-65.00%</th><th>65.01-70.00%</th><th>70.01-75.00%</th><th>75.01-80.00%</th><th>80.01-85.00%</th></tr>";

	var tableData = [
			[ "2-Unit Property", "0.000", "0.250", "n/a", "n/a", "n/a", "n/a" ],
			[ "Second Home", "0.000", "0.375", "0.500", "0.625", "n/a", "n/a" ],
			[ "Condo", "0.000", "0.250", "0.375", "0.500", "0.750", "0.750" ],
			[ "Cash Out", "0.000", "0.375", "n/a", "n/a", "n/a", "n/a" ] ];

	//var reverseParenthesisArray = getReverseParenthesisArray(tableData);
	
	var dataRows = getCascadeTableDataRows(tableData);

	table += row1 + row2 + row3 + dataRows + "</table>"

	return table;
}

function getCascaseAdjusterTable3() {
	var table = "<table class='adjuster-table'>";

	var row1 = "<tr><th colspan=7 class='th1'>" + "DTI Ratio (Back-End)"
			+ "</th></tr>";
	var row2 = "<tr><th rowspan=2>DTI Range</th>"
			+ "<th colspan=6>LTV Range</th></tr>";
	var row3 = "<tr><th><= 60.00%</th><th>60.01-65.00%</th><th>65.01-70.00%</th><th>70.01-75.00%</th><th>75.01-80.00%</th><th>80.01-85.00%</th></tr>";

	var tableData = [
			[ "< 35.00", "0.000", "0.000", "0.000", "0.000", "0.000", "0.000" ],
			[ "35.00 - 39.99", "0.000", "0.000", "0.000", "0.000", "0.000",
			  "0.000" ],
			[ "40.00 - 43.00", "0.000", "0.000", "0.000", "0.250", "0.375",
					"0.375" ] ];

	//var reverseParenthesisArray = getReverseParenthesisArray(tableData);
	var dataRows = getCascadeTableDataRows(tableData);

	table += row1 + row2 + row3 + dataRows + "</table>"

	return table;
}

function getCascaseAdjusterTable4() {
	var table = "<table class='adjuster-table'>";

	var row1 = "<tr><th colspan=7 class='th1'>"
			+ "State Adjustments for All Loans" + "</th></tr>";
	var row2 = "<tr><th>Jumbo State Tier</th>"
			+ "<th>Eligible States</th><th></th></tr>";

	var tableData = [ [ "1", "CA", "(0.120)" ], [ "3", "WA", "(0.030)" ],
			[ "4", "OR", "0.020" ] ];

	var dataRows = getCascadeTableDataRows(tableData);

	table += row1 + row2 + dataRows + "</table>"

	return table;
}

function getCascaseAdjusterTable5() {
	var table = "<table class='adjuster-table'>";

	var row1 = "<tr><th colspan=7 class='th1'>" + "Other Adjustments"
			+ "</th></tr>";
	var row2 = "<tr><th>Escrow Waiver Fee</th>" + "<td>.125</td></tr>";
	
	table += row1 + row2 + "</table>"

	return table;
}

function getCascadeTableDataRows(tableData) {
	var dataRows = "";
	for (var i = 0; i < tableData.length; i++) {
		var row = "<tr>";

		for (var j = 0; j < tableData[i].length; j++) {
			if (j == 0) {
				row += "<th>" + tableData[i][j] + "</th>";
			} else {
				row += "<td>" + tableData[i][j] + "</td>";
			}

		}
		row += "</tr>"
		dataRows += row;
	}
	return dataRows;
}

function getRatesTable(rateObjArray, title) {
	var wrapper = $('<div>').attr({
		"class" : "rate-table-wrapper-cont "
	});
	var header = $('<div>').attr({
		"class" : "rate-table-header"
	}).html(title);
	var container = $('<div>').attr({
		"class" : "rate-table-container"
	});

	var tableHeader = getRatesTableHeader();
	container.append(tableHeader);

	for (var i = 0; i < rateObjArray.length; i++) {
		
		if(title == "Mammoth Jumbo 15 YR Fixed" && i==0)
			continue;
		
		if (i >= 12) {
			break;
		}
		var tableRow = getRatesTableRow(rateObjArray[i]);
		container.append(tableRow);

	}

	return wrapper.append(header).append(container);

}

function getRatesTableHeader() {
	var container = $('<div>').attr({
		"class" : "tr table-header"
	});

	var col1 = $('<div>').attr({
		"class" : "th"
	}).html("Rate");

	var col2 = $('<div>').attr({
		"class" : "th"
	}).html("15-Day");

	var col3 = $('<div>').attr({
		"class" : "th"
	}).html("30-Day");

	var col4 = $('<div>').attr({
		"class" : "th"
	}).html("45-Day");

	var col5 = $('<div>').attr({
		"class" : "th"
	}).html("60-Day");
	return container.append(col1).append(col2).append(col3).append(col4)
			.append(col5);
}

function getRatesTableRow(rateObj) {
	var container = $('<div>').attr({
		"class" : "tr"
	});

	var fifteenYearValue = "-";
	var thirtyYearValue = "-";
	var fortyfiveYearValue = "-";
	var sixtyYearValue = "-";

	if (rateObj.col1Points != undefined) {
		fifteenYearValue = rateObj.col1Points;
	}
	if (rateObj.col2Points != undefined) {
		thirtyYearValue = rateObj.col2Points;
	}
	if (rateObj.col3Points != undefined) {
		fortyfiveYearValue = rateObj.col3Points;
	}
	if (rateObj.col4Points != undefined) {
		sixtyYearValue = rateObj.col4Points;
	}

	var col1 = $('<div>').attr({
		"class" : "td"
	}).html(rateObj.rate);

	if(rateObj.rate){
		col1.html(parseFloat(rateObj.rate).toFixed(3) + "%");
	}
	
	var col2 = $('<div>').attr({
		"class" : "td"
	}).html(fifteenYearValue);

	if(fifteenYearValue != "-"){
		col2.html(parseFloat(fifteenYearValue).toFixed(3));
	}
	
	var col3 = $('<div>').attr({
		"class" : "td"
	}).html(thirtyYearValue);

	if(thirtyYearValue != "-"){
		col3.html(parseFloat(thirtyYearValue).toFixed(3));
	}
	
	var col4 = $('<div>').attr({
		"class" : "td"
	}).html(fortyfiveYearValue);

	if(fortyfiveYearValue != "-"){
		col4.html(parseFloat(fortyfiveYearValue).toFixed(3));
	}
	
	var col5 = $('<div>').attr({
		"class" : "td"
	}).html(sixtyYearValue);

	if(sixtyYearValue != "-"){
		col5.html(parseFloat(sixtyYearValue).toFixed(3));
	}
	
	return container.append(col1).append(col2).append(col3).append(col4)
			.append(col5);
}