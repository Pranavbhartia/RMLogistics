

function paintRatesTablePage(data){
	console.info(data.fileName);
    $('#main-container').html('');
    var wrapper = $('<div>').attr({
        "class" : "rate-table-wrapper row"
    });
    
    
    for(var i=0; i<17;i++){
        var tableArray = ratesTempArray.rates;
        var title = data.fileName;
        //var table = getRatesTable(tableArray,title);
        wrapper.append(table);
    }
    
    $('#main-container').html(wrapper);
}

function getRatesTable(rateObjArray,title){
    var wrapper = $('<div>').attr({
        "class" : "rate-table-wrapper-cont"
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
    
    var col1 = $('<div>').attr({
        "class" : "td"
    }).html(rateObj.rate);
    
    var col2 = $('<div>').attr({
        "class" : "td"
    }).html(rateObj.value[0]);
    
    var col3 = $('<div>').attr({
        "class" : "td"
    }).html(rateObj.value[1]);
    
    var col4 = $('<div>').attr({
        "class" : "td"
    }).html(rateObj.value[2]);
    
    var col5 = $('<div>').attr({
        "class" : "td"
    }).html(rateObj.value[3]);
    
    return container.append(col1).append(col2).append(col3).append(col4).append(col5);
}