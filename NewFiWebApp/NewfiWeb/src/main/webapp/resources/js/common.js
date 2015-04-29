/**
* This file contains JavaScript functions used across the application
*/

var overlayCount = 0;
var emailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]\.[0-9]\.[0-9]\.[0-9]\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]+))$/;
var zipcodeRegex = /^\d{5}$/;
var phoneRegex = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s\.]{0,1}[0-9]{3}[-\s\.]{0,1}[0-9]{4}$/;

function ajaxRequest(url,type,dataType,data,successCallBack, isPagination , div,completeCallback , showOverlayText){
	if(isPagination===undefined){
		showOverlay(showOverlayText);
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
			if(isPagination){
				removePaginationScrollIcon(div);
			}else{
				hideOverlay();
			}
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
function showOverlay(showOverlayText){
	if(overlayCount == 0){
		$('#overlay-loader').show();
	}
	overlayCount++;
	if(showOverlayText){
		$("#overlay-loader-text").html(showOverlayText);
	}
	
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
	setTimeout(function() {
		 $("#overlay-loader-text").html('');
	}, 3000);
   
}

//Function to show toast message
function showToastMessage(message){
	$('#overlay-toast-txt').html(message).removeClass('overlay-toast-success');
	$('#overlay-toast').fadeIn("slow",function(){
		setTimeout(function(){
			$('#overlay-toast').fadeOut("slow");
		},2000);
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
function adjustCustomerNameWidth() {
	var cusNameColWidth = $('.leads-container-tc1').width();
	var statusIcnWidth = $('.onl-status-icn').width();
	var cusImgWidth = $('.cus-img-icn').width();
	var cusNameWidth = cusNameColWidth - (statusIcnWidth + cusImgWidth) - 5;
	$('.cus-name').outerWidth(cusNameWidth);
}

//Function to adjust center panel in customer Engagement Page
function adjustCustomerEngagementPageOnResize() {
	if (window.innerWidth <= 1200 && window.innerWidth >= 993) {
		var leftPanelWidth = $('.ce-lp').width();
		var centerPanelWidth = $(window).width() - (leftPanelWidth) - 100;
		$('#ce-refinance-cp').width(centerPanelWidth);
	}
	else if (window.innerWidth <= 992 && window.innerWidth >= 768) {
		var leftPanelWidth = $('.ce-lp').width();
		var centerPanelWidth = $(window).width() - (leftPanelWidth) - 40;
		$('#ce-refinance-cp').width(centerPanelWidth);
	}
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
function getDecimalValue(inputData){
	if(inputData){
		var num=removedDoller(removedComma(inputData));
		var val;
		try{
			val=parseFloat(num).toFixed(2);
		}catch(exception){
			val=num;
		}
		return val;
	} else
		return 0;
	
}

function numberWithCommasAndDoller(x) {
    return "$"+x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function showValue(number,showDecimal) {
	var temp=removedDoller(removedComma(number));
	if(number&&number!=""&&!isNaN(temp))
		if(!showDecimal)
    		return numberWithCommasAndDoller(getRoundValue(number));
    	else{
			return numberWithCommasAndDoller(getDecimalValue(number));
    	}
	else{
		if(isNaN(temp))
			return number;
		else
			return "";
	}
}

function markNegative(val){
	if(!isNaN(val)){
		val=showValue(Math.abs(val),true);
	}
	return "("+val+")";
}

function getClosingCostLabel(item){
	//Note case string should not be changed if text need to be changed then string being returned need to be modified
	switch(item){
       	case "Lender Fee":
			return "Lender Fee";
		case "This is your cost or credit based on rate selected":
			return "This is your cost or credit based on rate selected" ;
		case "Estimated Lender Costs":
			return "Estimated Lender Costs" ;
		case "Appraisal Fee":
			return "Appraisal Fee";
		case "Credit Report":
			return "Credit Report";
		case "Flood Certification":
			return "Flood Certification";
		case "Wire Fee":
			return "Wire Fee";
		case "Owners Title Insurance":
			return "Owners Title Insurance";
		case "Lenders Title Insurance":
			return "Lenders Title Insurance";
		case "Closing/Escrow Fee":
			return "Closing/Escrow Fee";
		case "Recording Fee":
			return "Recording Fee";
		case "City/County Tax stamps":
			return "City/County Tax stamps";
		case "Total Estimated Third Party Costs":
			return "Total Estimated Third Party Costs";
		case "Interest":
			return "Interest";
		case "Homeowners Insurance":
			return "Homeowners Insurance";
		case "Total Prepaids":
			return "Total Prepaids";
		case "Tax Reserve - Estimated 2 Month(s)":
			return "Tax Reserve - Estimated 2 Month(s)";
		case "Homeowners Insurance Reserve - Estimated 2 Month(s)":
			return "Homeowners Insurance Reserve - Estimated 2 Month(s)";
		case "Total Estimated Reserves Deposited in Escrow Account":
			return "Total Estimated Reserves Deposited in Escrow Account";
		case "Total Estimated Closing Cost":
			return "Total Estimated Closing Cost";	
    }
}

var closingCostHolder;
function objectKeyMakerFunction(item){
    switch(item){
       	case getClosingCostLabel("Lender Fee"):
			return "lenderFee813";
		case getClosingCostLabel("This is your cost or credit based on rate selected"):
			return "creditOrCharge802" ;
		case getClosingCostLabel("Estimated Lender Costs"):
			return "TotEstLenCost" ;
		case getClosingCostLabel("Appraisal Fee"):
			return "appraisalFee804";
		case getClosingCostLabel("Credit Report"):
			return "creditReport805";
		case getClosingCostLabel("Flood Certification"):
			return "floodCertification807";
		case getClosingCostLabel("Wire Fee"):
			return "wireFee812";
		case getClosingCostLabel("Owners Title Insurance"):
			return "ownersTitleInsurance1103";
		case getClosingCostLabel("Lenders Title Insurance"):
			return "lendersTitleInsurance1104";
		case getClosingCostLabel("Closing/Escrow Fee"):
			return "closingEscrowFee1102";
		case getClosingCostLabel("Recording Fee"):
			return "recordingFees1201";
		case getClosingCostLabel("City/County Tax stamps"):
			return "cityCountyTaxStamps1204";
		case getClosingCostLabel("Total Estimated Third Party Costs"):
			return "totEstThdPtyCst";
		case getClosingCostLabel("Interest"):
			return "interest901";
		case getClosingCostLabel("Homeowners Insurance"):
			return "hazIns903";
		case getClosingCostLabel("Total Prepaids"):
			return "totPrepaids";
		case getClosingCostLabel("Tax Reserve - Estimated 2 Month(s)"):
			return "taxResrv1004";
		case getClosingCostLabel("Homeowners Insurance Reserve - Estimated 2 Month(s)"):
			return "hazInsReserve1002";
		case getClosingCostLabel("Total Estimated Reserves Deposited in Escrow Account"):
			return "totEstResDepWthLen";
		case getClosingCostLabel("Total Estimated Closing Cost"):
			return "totEstimatedClosingCost";	
    }
    return undefined;
}
function getCalculationFunctionForItem(key){
    var fun=function(){
    	if(closingCostHolder.valueSet[key])
    		return closingCostHolder.valueSet[key];
    	else
    		return "$0.00";
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
		    		return "$125.00";
    		};
    		break;
    	case "hazIns903":
    		fun=function(){
    			if(closingCostHolder.valueSet[key]&&getFloatValue(closingCostHolder.valueSet[key])!=0)
		    		return closingCostHolder.valueSet[key];
		    	else{
		    		if(closingCostHolder.loanType&&closingCostHolder.loanType=="Purchase"){		    			
		    			var purchaseValue=getFloatValue(closingCostHolder.housePrice);
		    			var result=Math.round(.0035*purchaseValue)
		    			return result;
		    		}else{
		    			var result=closingCostHolder!=undefined?closingCostHolder.annualHomeownersInsurance:"";
		    			return result;
		    		}
		    	}
    		};
    		break;
    	case "taxResrv1004":
    		fun=function(){
    			if(closingCostHolder.valueSet[key]&&getFloatValue(closingCostHolder.valueSet[key])!=0)
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
    			if(closingCostHolder.valueSet[key]&&getFloatValue(closingCostHolder.valueSet[key])!=0)
		    		return closingCostHolder.valueSet[key];
		    	else{
		    		return "$0.00";
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
    			var val1=getFloatValue(closingCostHolder["interest901"].getValueForItem());
    			var val2=getFloatValue(closingCostHolder["hazIns903"].getValueForItem());
    			var result=val1+val2;
    			return result;
    		};
    		break;
    	case "totEstResDepWthLen":
    		fun=function(){
    			var val1=getFloatValue(closingCostHolder["taxResrv1004"].getValueForItem());
    			var val2=getFloatValue(closingCostHolder["hazInsReserve1002"].getValueForItem());
    			var result=val1+val2;
    			return result;
    		};
    		break;
    	case "cityCountyTaxStamps1204":
    		fun=function(){
        		if(closingCostHolder.valueSet[key] && getFloatValue(closingCostHolder.valueSet[key])!= 0 )
	        		return closingCostHolder.valueSet[key];
	        	else
	        		return "Varies by Location";
	        };
    		break;
    	case "totEstimatedClosingCost":
    		fun=function(){
    			var val1=getFloatValue(closingCostHolder["TotEstLenCost"].getValueForItem());
    			var val2=getFloatValue(closingCostHolder["totEstThdPtyCst"].getValueForItem());
    			var result=val1+val2;
        		return result;
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
        getValueForItem:function(){
			var ob=this;
        	var getVal=ob.updateFunction();
        	if(!isNaN(getVal)){
        		var negativeFlag=false;
        		if(getVal<0){
        			negativeFlag=true;
        			getVal=Math.abs(getVal)
        		}
        		getVal=showValue(getVal,true);
        		if(negativeFlag){
        			getVal=markNegative(getVal);
        		}
        	}
        	return getVal;
        },
        updateView:function(){
        	var ob=this;
        	var getVal=ob.getValueForItem();
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
	if(!appUserDetails.loan||!appUserDetails.loan.isRateLocked){
		try{
		    var rateVO = yearValues[yearValues.length-1].rateVO;
		    var index = parseInt(yearValues[yearValues.length-1].rateVO.length / 2);
		    var ob=rateVO[index];
		    ob.yearData=yearValues[yearValues.length-1].value;
		    return rateVO[index];
		}catch(exception){
			var ob={
				"payment":0,
				"yearData":"",
				"teaserRate":"",
				"APR":"",
				"lLpTemplateId":"",
				"point":"",
				"closingCost":"",
				"dummyData":true
			};
			return ob;
		}
	}else{
		return JSON.parse(appUserDetails.loan.lockedRateData);
	}
}



function clickEnter(name){
	
	$('input[name='+name+']').on("keypress",function(e){
		if(e.keyCode == 13){
	        $(".ce-save-btn").click();
		}
		
	});
}

function checkIfIE(){
	var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");
    if (msie > 0)
        return true;
    else
        return false;
}

function getInitialsFromFullName (fullName)
{
	var initials  = fullName.split(" ");
	var initialsText = initials[0].charAt(0).toUpperCase() + initials[1].charAt(0).toUpperCase();
	return initialsText;
}

function getInitials (firstName, lastName)
{
	var initialsText = firstName.charAt(0).toUpperCase() + lastName.charAt(0).toUpperCase();
	return initialsText;
}



//Soft Menu click events
$(document).on('click','.soft-menu-icon',function(e){
    e.stopPropagation();
    $('.soft-menu-wrapper').slideToggle();
});

$(document).on('click','.soft-menu-wrapper',function(e){
    e.stopPropagation();
});

$(document).on('click',function(e){
    if($('.soft-menu-wrapper').css("display") == "block"){
        $('.soft-menu-wrapper').slideToggle();
    }
});

