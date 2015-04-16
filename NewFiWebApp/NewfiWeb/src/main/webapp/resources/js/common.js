/**
* This file contains JavaScript functions used across the application
*/

var overlayCount = 0;
var emailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]\.[0-9]\.[0-9]\.[0-9]\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]+))$/;
var zipcodeRegex = /^\d{5}$/;
var phoneRegex = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s\.]{0,1}[0-9]{3}[-\s\.]{0,1}[0-9]{4}$/;

function ajaxRequest(url,type,dataType,data,successCallBack, isPagination , div,completeCallback){
	if(isPagination===undefined){
		showOverlay();
	}else if(isPagination==true){
		showPaginationScrollIcon(div);
	}	
	
	$.ajax({
		url : url,
		type : type,
		dataType : dataType,
		data : data,
		contentType: "application/json",
		success : successCallBack,
		complete:function(response){
			if(isPagination){
				removePaginationScrollIcon(div);
			}else{
				hideOverlay();
			}
			if(completeCallback){
				var data={};
				if(response.responseJSON)
					data=response.responseJSON;
				completeCallback(data);
			}
			adjustCenterPanelWidth();
			adjustRightPanelOnResize();
            adjustCustomerApplicationPageOnResize();
            adjustAgentDashboardOnResize();
		},
		error : function(){
			
		}
	});
}

function synchronousAjaxRequest(url,type,dataType,data,successCallBack, isPagination , div,completeCallback){
	if(isPagination===undefined){
		showOverlay();
	}else if(isPagination==true){
		showPaginationScrollIcon(div);
	}	
	
	$.ajax({
		url : url,
		type : type,
		dataType : dataType,
		async:false,
		data : data,
		contentType: "application/json",
		success : successCallBack,
		complete:function(response){
			if(isPagination){
				removePaginationScrollIcon(div);
			}else{
				hideOverlay();
			}
			if(completeCallback){
				var data={};
				if(response.responseJSON)
					data=response.responseJSON;
				completeCallback(data);
			}
			adjustCenterPanelWidth();
			adjustRightPanelOnResize();
            adjustCustomerApplicationPageOnResize();
            adjustAgentDashboardOnResize();
		},
		error : function(){
			
		}
	});
}

function formatPhoneNumberToUsFormat(text){
	if(text == undefined || text ==null){
		return "";
	}
    text = text.replace(/(\d\d\d)(\d\d\d)(\d\d\d\d)/, "+1 ($1) $2-$3");
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

function showPaginationScrollIcon(div){
	removePaginationScrollIcon(div);
	var img = '<img id="loaderPagination" src="resources/images/loading.gif" >';
	$("#"+div).append(img);
}

function removePaginationScrollIcon(div){
	$("#"+div+" > #loaderPagination").remove();
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
	$('#overlay-toast-txt').html(message).removeClass('overlay-toast-success');
	$('#overlay-toast').fadeIn("slow",function(){
		setTimeout(function(){
			$('#overlay-toast').fadeOut("slow");
		},3000);
	});
}

//Function to show toast message
function showErrorToastMessage(message){
	$('#overlay-toast-txt').html(message).addClass('overlay-toast-success');
	$('#overlay-toast').fadeIn("slow",function(){
		setTimeout(function(){
			$('#overlay-toast').fadeOut("slow");
		},3000);
	});
}


function photoUpload(form, action_url, img_div_id,message_div_id,suffix,userId) {
    // Create the iframe...
    var iframe = document.createElement("iframe");
    iframe.setAttribute("id", "upload_"+suffix);
    iframe.setAttribute("name", "upload_"+suffix);
    iframe.setAttribute("width", "0");
    iframe.setAttribute("height", "0");
    iframe.setAttribute("border", "0");
    iframe.setAttribute("style", "width: 0; height: 0; border: none;");
 
    // Add to document...
    form.parentNode.appendChild(iframe);
    window.frames['upload_'+suffix].name = "upload_"+suffix;
 
    iframeId = document.getElementById("upload_"+suffix);
 
    // Add event...
    var eventHandler = function () {
 
            if (iframeId.detachEvent) iframeId.detachEvent("onload", eventHandler);
            else iframeId.removeEventListener("load", eventHandler, false);
 
            // Message from server...
            if (iframeId.contentDocument) {
                content = iframeId.contentDocument.body.innerHTML;
            } else if (iframeId.contentWindow) {
                content = iframeId.contentWindow.document.body.innerHTML;
            } else if (iframeId.document) {
                content = iframeId.document.body.innerHTML;
            }
            
           //here is content
          // alert("content=="+content);
		  if(message_div_id!=""){
		  document.getElementById(message_div_id).innerHTML = content;
		  }
           if(content!="error" && img_div_id!=""){
		
	          $("#cusProfPicID").css('background', 'url(' + content  + ')');
			  $("#custprofuploadicnID").css('background', 'url(' + content  + ')');
		 
		  
		  $('#loaderWrapper').hide();
		  } 
            // Del the iframe...
            setTimeout('iframeId.parentNode.removeChild(iframeId)', 250);
           // iframeId.parentNode.removeChild(iframeId);
        }
 
    if (iframeId.addEventListener) iframeId.addEventListener("load", eventHandler, true);
    if (iframeId.attachEvent) iframeId.attachEvent("onload", eventHandler);
 
    // Set properties of form...
    form.setAttribute("target", "upload_"+suffix);
    form.setAttribute("action", action_url);
    form.setAttribute("method", "post");
    form.setAttribute("enctype", "multipart/form-data");
    form.setAttribute("encoding", "multipart/form-data");
 
    // Submit the form...
    form.submit();
    /*
    form.removeAttribute("target");
    form.removeAttribute("action");
    form.removeAttribute("method");
    form.removeAttribute("enctype");
    form.removeAttribute("encoding");
    */
	if(message_div_id!=""){
    document.getElementById(message_div_id).innerHTML = "Uploading...";
	}
		
}

//Functions related to window resize

function adjustCustomerApplicationPageOnResize(){
    if(window.innerWidth > 992 || window.innerWidth <= 1199){
        //Calcute application right panel width
        var appRightPanel = $('#app-right-panel');
        var parentWidth  = appRightPanel.parent().width();
        appRightPanel.width(parentWidth - 290);
    }
}

function adjustCenterPanelWidth() {
	if (window.innerWidth <= 1200 && window.innerWidth >= 768) {
		var leftPanelWidth = $('.left-panel').width();
		var leftPanelTab2Width = $('.lp-t2-wrapper').width();
		var centerPanelWidth = $(window).width()
				- (leftPanelWidth + leftPanelTab2Width) - 35;
		$('.center-panel').width(centerPanelWidth);
	} else if (window.innerWidth < 768) {
		var leftPanelTab2Width = $('.lp-t2-wrapper').width();
		var centerPanelWidth = $(window).width() - (leftPanelTab2Width) - 20;
		$('.center-panel').width(centerPanelWidth);
	}
}

function adjustRightPanelOnResize() {
	if(window.innerWidth <= 1200 && window.innerWidth >= 768){
		var leftPanelWidth = $('.left-panel').width();
		var centerPanelWidth = $(window).width() - (leftPanelWidth) - 15;
		$('.right-panel-messageDashboard').width(centerPanelWidth);
	}
}

function adjustAgentDashboardOnResize() {
	if (window.innerWidth <= 1200 && window.innerWidth >= 768) {
		var leftPanelWidth = $('.left-panel').width();
		var centerPanelWidth = $(window).width() - (leftPanelWidth) - 15;
		$('.rp-agent-dashboard').width(centerPanelWidth);
	}
	adjustCustomerNameWidth();
}

function scrollToTop(){
	$(window).scrollTop(0);
}


function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}


function removedDoller(inputData) {
    var processData = inputData+"";
   
    if(processData == undefined)
 	   return null;
    
    if (processData.indexOf('$') >= 0)     
     processData = processData.split("$")[1];

      return processData;
}

function removedComma(inputData) {
	
	var processData = inputData+"";
	if(processData == undefined)
	   return null;
	
	if (processData.indexOf(',') >= 0) 
		processData = processData.replace(/,/g, "");
     
	return processData;
}


function getFloatValue(inputData){
	
	return parseFloat(removedDoller(removedComma(inputData)));
	
}


function getRoundValue(inputData) {

    return Math.round(removedDoller(removedComma(inputData)));

}

function numberWithCommasAndDoller(x) {
    return "$"+x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function showValue(number) {
    return numberWithCommasAndDoller(getRoundValue(number));

}
