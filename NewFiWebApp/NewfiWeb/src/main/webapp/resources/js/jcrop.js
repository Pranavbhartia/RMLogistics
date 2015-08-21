var width_red = 400;
var height_red = 400;
var ratio = 1;
var selected_x = 50, selected_y = 0, selected_h = 100, selected_w = 100;

function initiateJcrop(input) {

	var divToShowImage = createUploadPhotoContent();

	$("#popup-overlay").html(divToShowImage);

	$("#popup-overlay").css("display", "block");

	if (input.files && input.files[0]) {

		var reader = new FileReader();
		reader.onload = function(e) {
			// $('#pu-img').attr('src', e.target.result);
			// ratio = 2.08;
			var myImage = new Image();
			myImage.src = e.target.result;
			// console.log("success width"+myImage.width);
			// console.log("success height"+myImage.height);
			if (myImage.width > myImage.height) {
				// console.log("success it is a landscape image");
				$('#pu-img').width(width_red);

			} else {
				// console.log("success it is a portrait image");
				$('#pu-img').height(height_red);

			}
			$('#pu-img').attr('src', e.target.result);

			// console.log("ratio"+ratio);
			$('#pu-img').css("display", "block");
			// $('#pu-img').width(imageMaxWidth);
			// var tempRatio = actWidth/newWidth;
			ratio = $('#pu-img').width() / myImage.width;

			$('#pu-img').Jcrop({
				/*
				 * setSelect : [
				 * ratio,ratio,$('#pu-img').width()/2,$('#pu-img').height()/2]
				 */
				setSelect : [ 50, 0, 300, 300 ],
				onSelect : updatePreview,
				onChange : updatePreview,
				aspectRatio : 1,
				trackDocument : true
			});
		};
		reader.readAsDataURL(input.files[0]);

		$(document).on('click', '#btn-pu-save', function(event) {

		});
	}
}

function updatePreview(c) {

	// console.log("in updatepreview the ratio is"+ratio);
	// console.log("c value"+c.x);
	// console.log("c value
	// 2:"+c.y+":w:"+c.w+"c.h:"+c.h+"c.x2:"+c.x2+"c.y2:"+c.y2);
	var imageObj = $("#pu-img")[0];
	var canvas = $("#pu-canvas")[0];
	/*
	 * var context = canvas.getContext("2d"); context.drawImage(imageObj, (c.x),
	 * (c.y), (c.w) , (c.h), 0, 0, canvas.width, canvas.height);
	 * console.log("context value:"+context.drawImage(imageObj, (c.x) * ratio,
	 * (c.y) * ratio, (c.w) ratio, (c.h) * ratio, 0, 0, canvas.width,
	 * canvas.height));
	 */
	var ctx = canvas.getContext("2d");
	console.log('ratio'+ratio);
	
		ratio =1;
	
	canvas.width = c.w;
	canvas.height = c.h;
	console.log(c.x / ratio + " " + c.y / ratio + " " + c.w / ratio + " " + c.h
			/ ratio);
	selected_x = Math.round(c.x / ratio);
	selected_y =Math.round( c.y / ratio);
	selected_w = Math.round(c.w / ratio);
	selected_h = Math.round(c.h / ratio);

	ctx.drawImage(imageObj, c.x / ratio, c.y / ratio, c.w / ratio, c.h / ratio,
			0, 0, canvas.width, canvas.height);

}

function createUploadPhotoContent() {

	$('.overlay-container').empty();
	var overlayDiv = $('<div>').attr({
		"class" : "overlay-container"
	});

	var img = $('<img>').attr({
		"class" : "img-class",
		"alt" : "upload profile image",
		"src" : "",
		"id" : "pu-img"
	});
	var canvas = $('<canvas>').attr({
		"id" : "pu-canvas"
	});
	// New addition 11th march 2015

	var imgDiv = $('<div>').attr({
		"class" : "img-div-class"
	});

	var headerContainerDiv = $('<div>').attr({
		"class" : "header-container-div"
	});

	var title = $('<p>').attr({
		"class" : "text-class",
		"id" : "text-data"
	}).html("Drag Over Image to Crop It");

	var imgCenter = $('<center>').attr({
		"id" : "img-center"
	});

	// End of changes
	var btnContainerDiv = $('<div>').attr({
		"class" : "btn-container"
	});

	var btnSaveDiv = $('<div>').attr({
		"class" : "btn-save-class",
		"id" : "btn-pu-save"
	}).html("Save");
	btnSaveDiv
			.click(function() {
				$('#overlay-loader').show();             
				var canvas = $('#pu-img')[0];
				$("#popup-overlay").hide();				
				var dataurl = canvas.src;
				$('#pu-img').attr('src', dataurl);

				// $('#prof-image').attr('src', dataurl);
				// $('#overlay-main').hide();
				var userid = document.getElementById("prof-image").name;

				var formData = new FormData();
				
				if (isNaN(selected_x)) {
					selected_x = 50;
				}
				if (isNaN(selected_y)) {
					selected_y = 50;
				}
				if (isNaN(selected_w)) {
					selected_w = 100;
				}
				if (isNaN(selected_h)) {
					selected_h = 100;
				}
				formData.append("selected_x", Math.round(selected_x));
				formData.append("selected_y", Math.round(selected_y));
				formData.append("selected_w", Math.round(selected_w));
				formData.append("selected_h", Math.round(selected_h));
				formData.append("width", Math.round($('#pu-img').width()));
				formData.append("height", Math.round($('#pu-img').height()));
				
				console.log(formData);

				formData.append("userid", userid);
				formData.append("imageBase64", dataurl);

				formData.append("imageFileName",
						$('#prof-image').prop("files")[0].name);
				
				$
						.ajax({
							url : "uploadCommonImageToS3.do",
							type : "POST",
							// dataType : "text",
							contentType : false,
							processData : false,
							cache : false,
							data : formData,
							success : function(data) {
								$('#overlay-loader').hide();
								console.log('URL from S3:' + data);
								if (data == null || data.trim() == '') {
									showErrorToastMessage(uploadFailed);
									return;
								}
								if (checkIfSafari()) {
									window.location.reload(true);
								}
									if (newfiObject.user.id == userid) {
									newfiObject.user.photoImageUrl = data;
									$("#myProfilePicture").css(
											{
												"background-image" : "url("
														+ data + ")",
												"background-size" : "cover"
											}).text('');

									$("#profilePic").css(
											{
												"background-image" : "url("
														+ data + ")",
												"background-size" : "cover"
											});							
									
								
									
									showToastMessage(ProfileImageSuccessMessage);
									
								} else {
                                    //NEXNF-695
									if($("#cusProfPicID").hasClass('cus-img-icn-default')){
										$("#cusProfPicID").removeClass('cus-img-icn-default');
										$("#cusProfPicID").text('');
										$("#cusProfPicID").addClass('cus-img-icn');
									}
									
									$("#cusProfPicID").css(
											{
												"background-image" : "url("
														+ data + ")",
												"background-size" : "cover"
											});

									$("#custprofuploadicnID").css(
											{
												"background-image" : "url("
														+ data + ")",
												"background-size" : "cover"
											});
									
									if($("#userProfilePic").hasClass('adminUM-agent-default-img')){
										$("#userProfilePic").removeClass('adminUM-agent-default-img');
										$("#userProfilePic").text('');
										$("#userProfilePic").addClass('adminUM-agent-assigned-img');
									}
									
									$("#userProfilePic").css(
											{
												"background-image" : "url("
														+ data + ")",
												"background-size" : "cover"
									});
									
									
									showToastMessage(ProfileImageSuccessMessage);
								}
								//
							},
							complete : function() {
								// alert('cpmplte');
								$('#overlay-loader').hide();
								//window.location.reload();
							},
							error : function(e) {
								// alert('error');
								$('#overlay-loader').hide();
							}
						});
			});

	var btnCancelDiv = $('<div>').attr({
		"class" : "btn-cancel-class",
		"id" : "btn-pu-cancel",
		"onclick" : "cancelUploadPhoto()"
	}).html("Cancel");

	btnContainerDiv.append(btnSaveDiv).append(btnCancelDiv);
	// New addition 11th march 2015
	headerContainerDiv.append(title);
	imgDiv.append(imgCenter).append(img);
	imgCenter.append(img);
	return overlayDiv.append(headerContainerDiv).append(imgDiv).append(canvas)
			.append(btnContainerDiv);

}

function cancelUploadPhoto() {

	$("#popup-overlay").empty();
	$("#popup-overlay").css("display", "none");
	return false;
}

function validatePhotoExtention(filename) {

	// console.log("filename:"+filename);
	var extension = filename.replace(/^.*\./, '');
	// console.log("extension"+extension);
	var extensionsArray = new Array("jpg", "jpeg", "gif", "png", "bmp");
	var final_ext = extension.toLowerCase();
	for (i = 0; i < extensionsArray.length; i++) {
		if (extensionsArray[i] == final_ext) {
			return true;
		}
	}
	showErrorToastMessage(photoUploadErrorMessage
			+ extensionsArray.join(', ') + ".");
	// alert("You must upload an image file with one of the following
	// extensions:"+ extensionsArray.join(', ')+".");
	return false;
}