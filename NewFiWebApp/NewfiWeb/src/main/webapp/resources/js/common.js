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