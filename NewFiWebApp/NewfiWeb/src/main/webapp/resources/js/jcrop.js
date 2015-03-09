var imageMaxWidth = 500;
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
			$('#pu-img').attr('src', e.target.result);
			ratio = $('#pu-img').width() / imageMaxWidth;
			$('#pu-img').css("display", "block");
			$('#pu-img').width(imageMaxWidth);

			
			$('#pu-img').Jcrop({
				setSelect : [ 100, 100, 50, 50 ],
				onSelect : updatePreview,
				onChange : updatePreview,
				aspectRatio: 1,
				trackDocument: true
			});
		};
		reader.readAsDataURL(input.files[0]);

		var canvas = $("#pu-canvas")[0];
		$(document).on('click','#btn-pu-save',function() {
				$("#popup-overlay").hide();
					var dataurl = canvas.toDataURL("image/png");
					$('#pu-img').attr('src', dataurl);
					// $('#prof-image').attr('src', dataurl);
					// $('#overlay-main').hide();

					var formData = new FormData();
					formData.append("imageBase64", dataurl);
					formData.append("imageFileName", $('#prof-image').prop("files")[0].name);

					formData.append("userid",newfiObject.user.id);
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
							
							
							$("#myProfilePicture").css({"background-image": "url("+data+")","background-size": "cover"});
							 
							$("#profilePic").css({"background-image": "url("+data+")","background-size": "cover"});
							//
						},
						complete : function() {
							hideOverlay();
						},
						error : function(e) {
						}
					});

				});
	}
}

function updatePreview(c) {
	if (parseInt(c.w) > 0) {
		var imageObj = $("#pu-img")[0];
		var canvas = $("#pu-canvas")[0];
		var context = canvas.getContext("2d");
		context.drawImage(imageObj, (c.x) * ratio, (c.y) * ratio, (c.w)
						* ratio, (c.h) * ratio, 0, 0, canvas.width,
						canvas.height);
	}
}

function createUploadPhotoContent(){
	
	var overlayDiv = $('<div>').attr({
		"class" : "overlay-container"
	});
	
	var img =  $('<img>').attr({
		"alt" : "upload profile image",
		"src":"",
		"id":"pu-img"
	});
	var canvas = $('<canvas>').attr({
		"id" : "pu-canvas"
	});
	
	var btnContainerDiv = $('<div>').attr({
		"class" : "btn-container"
	});
	var btnSaveDiv = $('<div>').attr({
		"id" : "btn-pu-save"
	}).html("Save");
	
	var btnCancelDiv = $('<div>').attr({
		"id" : "btn-pu-cancel",
		"onclick":"cancelUploadPhoto()"
	}).html("Cancel");
	
	btnContainerDiv.append(btnSaveDiv).append(btnCancelDiv);
	return overlayDiv.append(img).append(canvas).append(btnContainerDiv);
	
}

function cancelUploadPhoto(){

	$("#popup-overlay").empty();
	$("#popup-overlay").css("display", "none");
	return false;
}


function validatePhotoExtention(filename){
	
	var extension = filename.replace(/^.*\./, '');
	extension = extension.toLowerCase();
	if(extension != "jpg" ||extension != "jpeg" || extension != "png" ){
		cancelUploadPhoto();
	}   		
}