

function paintRatesTablePage(data){
	console.info(data.fileName);
    $('#main-container').html('');
    var wrapper = $('<div>').attr({
        "class" : "rate-table-wrapper row clearfix"
    });
    
    var ObjectKeys = Object.keys(data);
    
    for(var i=0; i<ObjectKeys.length;i++){
        var tableArray = data[ObjectKeys[i]];
        var title =ObjectKeys[i];
        var table = getRatesTable(tableArray,title);
        wrapper.append(table);
    }
    
    $('#main-container').html(wrapper);
    
    $('.rate-table-wrapper').masonry({
    	  itemSelector: '.rate-table-wrapper-cont'
    	  
    });
}

function getRatesTable(rateObjArray,title){
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
    
    for(var i=0;i<rateObjArray.length;i++){
    	if(i>=7){
    		break;
    	}
        var tableRow = getRatesTableRow(rateObjArray[i]);
        container.append(tableRow);
        
    }
    
    return wrapper.append(header).append(container);
    
}

function getRatesTableHeader(){
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
    return container.append(col1).append(col2).append(col3).append(col4).append(col5);
}

function getRatesTableRow(rateObj){
    var container = $('<div>').attr({
        "class" : "tr"
    });
    
    var fifteenYearValue="-";
    var thirtyYearValue="-";
    var fortyfiveYearValue="-";
    var sixtyYearValue="-";
    
    if(rateObj.col1Points!=undefined){
    	fifteenYearValue = rateObj.col1Points;
    }
    if(rateObj.col2Points!=undefined){
    	thirtyYearValue = rateObj.col2Points;
    }
    if(rateObj.col3Points!=undefined){
    	fortyfiveYearValue = rateObj.col3Points;
    }
    if(rateObj.col4Points!=undefined){
    	sixtyYearValue = rateObj.col4Points;
    }
    	
    
    
    var col1 = $('<div>').attr({
        "class" : "td"
    }).html(rateObj.rate);
    
    var col2 = $('<div>').attr({
        "class" : "td"
    }).html(fifteenYearValue);
    
    var col3 = $('<div>').attr({
        "class" : "td"
    }).html(thirtyYearValue);
    
    var col4 = $('<div>').attr({
        "class" : "td"
    }).html(fortyfiveYearValue);
    
    var col5 = $('<div>').attr({
        "class" : "td"
    }).html(sixtyYearValue);
    
    return container.append(col1).append(col2).append(col3).append(col4).append(col5);
}