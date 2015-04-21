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
		success : function(response){
			if(isPagination){
				removePaginationScrollIcon(div);
			}else{
				hideOverlay();
			}
			successCallBack(response);
		},
		complete:function(response){
			
			if(completeCallback){
				if(isPagination){
					removePaginationScrollIcon(div);
				}else{
					hideOverlay();
				}
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
	if(inputData){
		inputData=inputData+"";
		var negativeFlag=false;
		if(inputData.indexOf("(")>=0){
			negativeFlag=true;
		}
		if(negativeFlag){
			return (-1*parseFloat(removedDoller(removedComma(inputData))));
		}else{
			return parseFloat(removedDoller(removedComma(inputData)));	
		}
	}
	else
		return 0;
	
}

function getRoundValue(inputData){
	if(inputData)
		return Math.round(removedDoller(removedComma(inputData)));
	else
		return 0;
	
}

function numberWithCommasAndDoller(x) {
    return "$"+x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function showValue(number) {
    return numberWithCommasAndDoller(getRoundValue(number));

}




var closingCostHolder;
function objectKeyMakerFunction(item){
    switch(item){
       	case "Lender Fee":
			return "lenderFee813";
		case "Credit/Charge":
			return "creditOrCharge802" ;
		case "Estimated Lender Costs":
			return "TotEstLenCost" ;
		case "Appraisal Fee":
			return "appraisalFee804";
		case "Credit Report":
			return "creditReport805";
		case "Flood Certification":
			return "floodCertification807";
		case "Wire Fee":
			return "wireFee812";
		case "Owners Title Insurance":
			return "ownersTitleInsurance1103";
		case "Lenders Title Insurance":
			return "lendersTitleInsurance1104";
		case "Closing/Escrow Fee":
			return "closingEscrowFee1102";
		case "Recording Fee":
			return "recordingFees1201";
		case "City/County Tax stamps":
			return "cityCountyTaxStamps1204";
		case "Total Estimated Third Party Costs":
			return "totEstThdPtyCst";
		case "Interest":
			return "interest901";
		case "Homeowners Insurance":
			return "hazIns903";
		case "Total Prepaids":
			return "totPrepaids";
		case "Tax Reserve - Estimated 2 Month(s)":
			return "taxResrv1004";
		case "Haz ins. Reserve - Estimated 2 Month(s)":
			return "hazInsReserve1002";
		case "Total Estimated Reserves Deposited in Escrow Account":
			return "totEstResDepWthLen";
    }
    return undefined;
}
function getCalculationFunctionForItem(key){
    var fun=function(){
    	if(closingCostHolder.valueSet[key])
    		return closingCostHolder.valueSet[key];
    	else
    		return "0";
    };
    switch(key){
    	case "TotEstLenCost":
    		fun=function(){
    			var val1=getFloatValue(closingCostHolder.valueSet["lenderFee813"]);
    			var val2=getFloatValue(closingCostHolder.valueSet["creditOrCharge802"]);
    			var result=val1+val2;
    			return result;
    		};
    		break;
		case "recordingFees1201":
    		fun=function(){
    			if(closingCostHolder.valueSet[key]&&closingCostHolder.valueSet[key]!="0")
		    		return closingCostHolder.valueSet[key];
		    	else
		    		return "$ 125.00";
    		};
    		break;
    	case "hazIns903":
    		fun=function(){
    			if(closingCostHolder.valueSet[key]&&closingCostHolder.valueSet[key]!="0")
		    		return closingCostHolder.valueSet[key];
		    	else{
		    		if(closingCostHolder.loanType&&closingCostHolder.loanType=="Purchase"){		    			
		    			var purchaseValue=getFloatValue(closingCostHolder.housePrice);
		    			var result=Math.round(.0035*purchaseValue)
		    			return result;
		    		}else{
		    			var result=refinanceTeaserRate!=undefined?closingCostHolder.annualHomeownersInsurance:"";
		    			return result;
		    		}
		    	}
    		};
    		break;
    	case "taxResrv1004":
    		fun=function(){
    			if(closingCostHolder.valueSet[key]&&closingCostHolder.valueSet[key]!="0")
		    		return closingCostHolder.valueSet[key];
		    	else{
		    		if(closingCostHolder.loanType&&closingCostHolder.loanType=="Purchase"){
		    			var purchaseValue=getFloatValue(closingCostHolder.housePrice);
		    			var result=Math.round((.0125*purchaseValue)/6);
		    			return result;
		    		}else{
		    			var taxVal=getFloatValue(closingCostHolder.propertyTaxesPaid);
		    			var result=Math.round(taxVal/6);
		    			return result;
		    		}
		    	}
    		};
    		break;
    	case "hazInsReserve1002":
    		fun=function(){
    			if(closingCostHolder.valueSet[key]&&closingCostHolder.valueSet[key]!="0")
		    		return closingCostHolder.valueSet[key];
		    	else{
		    		return "$ 0";
		    	}
    		};
    		break;
    	case "totEstThdPtyCst":
    		fun=function(){
				var val1=getFloatValue(closingCostHolder.valueSet["appraisalFee804"]);
    			var val2=getFloatValue(closingCostHolder.valueSet["creditReport805"]);
    			var val3=getFloatValue(closingCostHolder.valueSet["floodCertification807"]);
    			var val4=getFloatValue(closingCostHolder.valueSet["wireFee812"]);
    			var val5=getFloatValue(closingCostHolder.valueSet["ownersTitleInsurance1103"]);
    			var val6=getFloatValue(closingCostHolder.valueSet["lendersTitleInsurance1104"]);
    			var val7=getFloatValue(closingCostHolder.valueSet["closingEscrowFee1102"]);
    			var val8=getFloatValue(closingCostHolder.valueSet["recordingFees1201"]);
    			var val9=getFloatValue(closingCostHolder.valueSet["cityCountyTaxStamps1204"]);
    			if (isNaN(val9))
    			{
    				val9=0;
    			}
    			var result=val1+val2+val3+val4+val5+val6+val7+val8+val9;
    			return result;
    		};
    		break;
    	case "totPrepaids":
    		fun=function(){
    			var val1=getFloatValue(closingCostHolder.valueSet["interest901"]);
    			var val2=getFloatValue(closingCostHolder.valueSet["hazIns903"]);
    			var result=val1+val2;
    			return result;
    		};
    		break;
    	case "totEstResDepWthLen":
    		fun=function(){
    			var val1=getFloatValue(closingCostHolder.valueSet["taxResrv1004"]);
    			var val2=getFloatValue(closingCostHolder.valueSet["hazInsReserve1002"]);
    			var result=val1+val2;
    			return result;
    		};
    		break;
    	case "cityCountyTaxStamps1204":
    		fun=function(){
        		if(closingCostHolder.valueSet[key] && closingCostHolder.valueSet[key]!= "0" )
	        		return closingCostHolder.valueSet[key];
	        	else
	        		return "Varies by Location";
	        };
    		break;
    }
    return fun;
}


function getRowHolderObject(container,value,key){
    var rw={
        container:container,
        value:value,
        key:key,
        updateFunction:getCalculationFunctionForItem(key),
        updateView:function(){
        	var ob=this;
        	var getVal=showValue(ob.updateFunction());
        	$(ob.container).text(getVal);
        }
    };
    return rw;
}
function getObContainer(){
	
	var obj={
		update:function(){
			var ob=this;
			for(var key in ob){
				var keyObj=ob[key];
				if(keyObj.updateView){
					keyObj.updateView();
				}
			}
		}
	};
	return obj;
}
function updateOnSlide(valueSet){
	if(valueSet){
        closingCostHolder.valueSet=valueSet;
    }else{
        closingCostHolder.valueSet={};
    }
    closingCostHolder.update();
}

function getLQBObj(yearValues){
    var rateVO = yearValues[yearValues.length-1].rateVO;
    var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
    return rateVO[index];
}
