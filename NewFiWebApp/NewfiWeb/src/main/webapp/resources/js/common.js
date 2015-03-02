/**
* This file contains JavaScript functions used across the application
*/

var overlayCount = 0;

function ajaxRequest(url,type,dataType,data,successCallBack){
	$.ajax({
		url : url,
		type : type,
		dataType : dataType,
		data : data,
		contentType: "application/json",
		success : successCallBack,
		error : function(){
			
		}
	});
}


function formatPhoneNumberToUsFormat(text){
	if(text == undefined || text ==null){
		return "";
	}
    text = text.replace(/(\d\d\d)(\d\d\d)(\d\d\d\d)/, "($1) $2-$3");
    return text;
}

function convertStringToId(str){
	str = str.trim().toLowerCase();
	str = str.split(' ').join('-');
	return str;
}

$(document).on('click','.small-screen-menu-icon',function(e){
	e.stopImmediatePropagation();
	$('.left-panel').toggle('slide','left');
	if($(this).hasClass('small-screen-menu-icon-clicked')){
		$(this).removeClass('small-screen-menu-icon-clicked');
	}else{
		$(this).addClass('small-screen-menu-icon-clicked');
	}
});
$(document).click(function(){
	if($(window).width() <= 768){
		if($('.left-panel').css("display") == "block"){
			$('.left-panel').toggle('slide','left');
			$('.small-screen-menu-icon').removeClass('small-screen-menu-icon-clicked');
		}
	}
});

//function to show overlay
function showOverlay(){
	if(overlayCount == 0){
		$('#overlay-loader').show();
	}
	overlayCount++;
}

//function to hide overlay
function hideOverlay(){
	overlayCount--;
	if(overlayCount == 0){
		$('#overlay-loader').hide();
	}
}

//Function to show toast message
function showToastMessage(message){
	$('#overlay-toast-txt').html(message);
	$('#overlay-toast').fadeIn("slow",function(){
		$('#overlay-toast').fadeOut("slow");
	});
}