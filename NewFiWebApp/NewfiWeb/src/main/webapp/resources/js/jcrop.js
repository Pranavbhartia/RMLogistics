var width_red=400;
var height_red=400;
var ratio = 1;

function initiateJcrop(input) {
	
	var divToShowImage= createUploadPhotoContent();
	
	$("#popup-overlay").html(divToShowImage);
	
	$("#popup-overlay").css("display", "block");
	
	if (input.files && input.files[0]) {

		var reader = new FileReader();
		reader.onload = function(e) {
			//$('#pu-img').attr('src', e.target.result);
			//ratio = 2.08;
			var myImage = new Image(); 
			myImage.src = e.target.result;
			//console.log("success width"+myImage.width);
			//console.log("success height"+myImage.height);
			if(myImage.width > myImage.height){
			//console.log("success it is a landscape image");
            $('#pu-img').width(width_red);
			
			}else{
			//console.log("success it is a portrait image");
            $('#pu-img').height(height_red); 
			
			
			}
			$('#pu-img').attr('src', e.target.result);
			
			//console.log("ratio"+ratio);
			$('#pu-img').css("display", "block");
			// $('#pu-img').width(imageMaxWidth);
			 //var tempRatio = actWidth/newWidth;
            ratio = $('#pu-img').width() / myImage.width;

			$('#pu-img').Jcrop({
				/* setSelect : [ ratio,ratio,$('#pu-img').width()/2,$('#pu-img').height()/2]*/
			 setSelect : [50, 0, 300,300],
				onSelect : updatePreview,
				onChange : updatePreview,
				aspectRatio: 1,
				trackDocument: true
			});
		};
		reader.readAsDataURL(input.files[0]);

		
		$(document).on('click','#btn-pu-save',function(event) {

				});
	}
}

function updatePreview(c) {


	//console.log("in updatepreview the ratio is"+ratio);
//console.log("c value"+c.x);
//console.log("c value 2:"+c.y+":w:"+c.w+"c.h:"+c.h+"c.x2:"+c.x2+"c.y2:"+c.y2);
		var imageObj = $("#pu-img")[0];
		var canvas = $("#pu-canvas")[0];
		/* var context = canvas.getContext("2d");
		context.drawImage(imageObj, (c.x), (c.y), (c.w)
						, (c.h), 0, 0, canvas.width,
						canvas.height);
						console.log("context value:"+context.drawImage(imageObj, (c.x) * ratio, (c.y) * ratio, (c.w)
						* ratio, (c.h) * ratio, 0, 0, canvas.width,
						canvas.height)); */
	  var ctx=canvas.getContext("2d");
	  canvas.width=c.w;
	  canvas.height=c.h;
	 
	  ctx.drawImage(imageObj, c.x/ratio, c.y/ratio, c.w/ratio, c.h/ratio, 0, 0, canvas.width, canvas.height);

}

function createUploadPhotoContent(){
	
	$('.overlay-container').empty();
	var overlayDiv = $('<div>').attr({
		"class" : "overlay-container"
	});
	
	var img =  $('<img>').attr({
	    "class":"img-class",
		"alt" : "upload profile image",
		"src":"",
		"id":"pu-img"
	});
	var canvas = $('<canvas>').attr({
		"id" : "pu-canvas"
	});
	//New addition 11th march 2015
	
	var imgDiv=$('<div>').attr({
		"class" : "img-div-class"
	});
	
	var headerContainerDiv=$('<div>').attr({
		"class" : "header-container-div"
	});
	
	var title=$('<p>').attr({
	 "class":"text-class",
	 "id" : "text-data"
	}).html("Drag Over Image to Crop It");
	
	var imgCenter=$('<center>').attr({
	"id":"img-center"
	});
	
	//End of changes
	var btnContainerDiv = $('<div>').attr({
		"class" : "btn-container"
	});
	
	var btnSaveDiv = $('<div>').attr({
	 "class":"btn-save-class",
		"id" : "btn-pu-save"
	}).html("Save");
	btnSaveDiv.click(function(){

		var canvas = $("#pu-canvas")[0];
			$("#popup-overlay").hide();
				var dataurl = canvas.toDataURL("image/png");
				$('#pu-img').attr('src', dataurl);
				
				// $('#prof-image').attr('src', dataurl);
				// $('#overlay-main').hide();
                var userid=document.getElementById("prof-image").name;

				var formData = new FormData();
				formData.append("imageBase64", dataurl);
				formData.append("imageFileName", $('#prof-image').prop("files")[0].name);


				formData.append("userid",userid);
				showOverlay();
				$.ajax({
					url : "uploadCommonImageToS3.do",
					type : "POST",
					//dataType : "text",
					contentType : false,
					processData : false,
					cache : false,
					data : formData,
					success : function(data) {
						console.log('URL from S3:' +data);
						if(newfiObject.user.id==userid){
						newfiObject.user.photoImageUrl = data;
						$("#myProfilePicture").css({"background-image": "url("+data+")","background-size": "cover"}).text('');
						 
						$("#profilePic").css({"background-image": "url("+data+")","background-size": "cover"});}
						else{
						
						$("#cusProfPicID").css({"background-image": "url("+data+")","background-size": "cover"});
						 
						$("#custprofuploadicnID").css({"background-image": "url("+data+")","background-size": "cover"});
						
						
						}
						//
					},
					complete : function() {
					//alert('cpmplte');
						hideOverlay();
					},
					error : function(e) {
					//alert('error');
					}
				});
	});
	
	var btnCancelDiv = $('<div>').attr({
	  "class":"btn-cancel-class",
		"id" : "btn-pu-cancel",
		"onclick":"cancelUploadPhoto()"
	}).html("Cancel");		

	btnContainerDiv.append(btnSaveDiv).append(btnCancelDiv);
	//New addition 11th march 2015
	headerContainerDiv.append(title);
	imgDiv.append(imgCenter).append(img);
	imgCenter.append(img);
	return overlayDiv.append(headerContainerDiv).append(imgDiv).append(canvas).append(btnContainerDiv);
	

	
}

function cancelUploadPhoto(){

	$("#popup-overlay").empty();
	$("#popup-overlay").css("display", "none");
	return false;
}


function validatePhotoExtention(filename){

	//console.log("filename:"+filename);
	var extension = filename.replace(/^.*\./, '');
	//console.log("extension"+extension);
	var extensionsArray = new Array("jpg","jpeg","gif","png","bmp");
	var final_ext = extension.toLowerCase();
    for (i = 0; i < extensionsArray.length; i++)
    {
    if(extensionsArray[i] == final_ext)
    {
    return true;
    }
    }
    showToastMessage("You must upload an image file with one of the following extensions:"+ extensionsArray.join(', ')+".");
  //alert("You must upload an image file with one of the following extensions:"+ extensionsArray.join(', ')+"."); 
   return false;
}