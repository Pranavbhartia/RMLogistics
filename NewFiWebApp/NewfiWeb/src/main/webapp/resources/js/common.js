/**
* This file contains JavaScript functions used across the application
*/

function ajaxRequest(url,type,dataType,data,successCallBack){
	$.ajax({
		url : url,
		type : type,
		dataType : dataType,
		data : data,
		success : successCallBack,
		error : function(){
			
		}
	});
}


function formatPhoneNumberToUsFormat(text){
    text = text.replace(/(\d\d\d)(\d\d\d)(\d\d\d\d)/, "($1) $2-$3");
    return text;
}