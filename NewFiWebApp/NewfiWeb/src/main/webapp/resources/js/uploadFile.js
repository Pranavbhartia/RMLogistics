/*
 * Functions for upload items module
 */
var SPLIT_DOC = "Split Document";
var needIdtoAssign = null;


function uploadNeededItemsPage() {
	var userId = newfiObject.user.id;
	var activeLoanId = newfiObject.user.defaultLoanId;
	currentUserAndLoanOnj.userId = userId;
	currentUserAndLoanOnj.activeLoanId = activeLoanId;
	getRequiredDocuments();
}

function getRequiredDocuments( ) {
	
	ajaxRequest("rest/fileupload/needlist/get/" + currentUserAndLoanOnj.userId  + "/" + currentUserAndLoanOnj.activeLoanId,
			"GET", "json", "", getRequiredDocumentData);
}

function getRequiredDocumentData(neededItemList) {
	neededItemListObject = neededItemList;
	$('#uploadedNeedContainer').remove();
	paintUploadNeededItemsPage(neededItemListObject);
	
	$("#knobUpload").knob({
			"data-width" : "50",
			"data-displayInput": false,
			"readOnly":true,
			'draw' : function () { 
				$(this.i).val(this.cv + '/' + this.o.max);
		      }		
	});
}


function saveAssignmentonFile() {
	getRequiredDocuments();
}

function checkForSplitOption(select){
	
		if($(select).val()== SPLIT_DOC){
			var fileName = $(select).attr('fileName');
			var fileId = $(select).attr('fileId');
			var object = new Object();
			object.fileId = fileId;
			object.fileName = fileName;
			
			showDialogPopup("Confirm File Split" , 
					"Are you sure want to split the current PDF document." , function(){splitPDFDocument(object);} );
		}
		
}


function splitPDFDocument(dataObject){
	console.info(dataObject.fileName +" and "+dataObject.fileId);
	var url = "rest/fileupload/split/"
										+dataObject.fileId+"/"
										+currentUserAndLoanOnj.activeLoanId
										+"/"+currentUserAndLoanOnj.userId
										+"/"+newfiObject.user.id;
	ajaxRequest(url,
			"GET", "json", "", afterPDFSplit);
}

function afterPDFSplit(response){
	console.info("pdf splitting done : "+response);
	getRequiredDocuments();
}


function getDocumentUploadColumn(listUploadedFiles) {
	var column = $('<div>').attr({
		"class" : "document-cont-col float-left"
	});
	var docImg = $('<div>').attr({
		"class" : "doc-img showAnchor"
	});
	
	var deactivete = $("<div>").attr({
		"class" : "deactiveteIcon showAnchor",
		"id" : "deactivate_"+listUploadedFiles.id
		//"onclick" : deactivate(listUploadedFiles.id)
	}).click(function(event){
		event.stopImmediatePropagation();
		deactivate($(this));
	});
	
	
	
	docImg.append(deactivete);
	
	var img = $("<img>").attr({
		 		"src" : listUploadedFiles.s3ThumbPath
	}).load(function(){
		docImg.css({
			"background" : "url('"+listUploadedFiles.s3ThumbPath+"') no-repeat center",
			"background-size" : "cover"
			
		});
	});
	
	//var deleteLink = $("<p class='showAnchor' onclick=deactivate('"+listUploadedFiles.id+"')>").html("(  delete )");
	
	
	var docDesc = $('<div>').attr({
		"class" : "doc-desc showAnchor"
	}).append(listUploadedFiles.fileName);
	
	
	var ahrefFile = $("<a>").attr({
					"href" : "readFileAsStream.do?s3FileId="+listUploadedFiles.uuidFileId,
					"target" : "_blank"
	});
	
	
	
	
	var docAssign = $("<select>").attr({
		"class" : "assign",
		"fileId" : listUploadedFiles.id,
		"fileName" : listUploadedFiles.fileName,
		"onchange" : ""
	}).change(function(){
		checkForSplitOption(this);
	});

	var assignOption = $("<option>").attr({
		"value" : "Assign"
	}).html("Assign");
	var splitOption = $("<option>").attr({
		"value" : SPLIT_DOC
		}).html(SPLIT_DOC);
	docAssign.append(assignOption);

	if(listUploadedFiles.totalPages>1){
		docAssign.append(splitOption);
	}
	
	var neededItemListObj = neededItemListObject.resultObject.listLoanNeedsListVO;

	for (i in neededItemListObj) {
		var needsListMasterobj = neededItemListObj[i];
		var option = $("<option>").attr({
			"value" : needsListMasterobj.id
		}).html(needsListMasterobj.needsListMaster.label);

		if (needsListMasterobj.id == listUploadedFiles.needType) {
			option.attr('selected', 'selected');
		}
		docAssign.append(option);
	}

	
	if(newfiObject.user.userRole.roleDescription == "Realtor"){
		if(listUploadedFiles.assignedByUser.userId == newfiObject.user.id ){
			docImg.click(function(){
				window.open("readFileAsStream.do?s3FileId="+listUploadedFiles.uuidFileId, '_blank');
			});
			ahrefFile.append(docDesc);
		}else{
			docImg.addClass("unlink");
			ahrefFile = docDesc;
			docAssign.hide();
		}
	}else{
		
		docImg.click(function(){
			window.open("readFileAsStream.do?s3FileId="+listUploadedFiles.uuidFileId, '_blank');
		});
		ahrefFile.append(docDesc);
	}
	column.append(docImg).append(ahrefFile);
	
	return column.append(docAssign);
}


function deactivate(Obj){
	var fileString = Obj.attr("id");
	var fileID = fileString.substring(11);
	ajaxRequest("rest/fileupload/deactivate/file/"+fileID, "GET", "json", "", getRequiredDocuments);
}

function showFileLink(uploadedItems) {

	$.each(uploadedItems, function(index, value) {
		var needId = value.needType;
		$('#needDoc' + needId).removeClass('hide');
		$('#needDoc' + needId).addClass('doc-link-icn');
		$('#needDoc' + needId).click(function() {
			window.open("readFileAsStream.do?s3FileId="+value.uuidFileId, '_blank');
		});
	});
}




function saveUserDocumentAssignments() {
	showOverlay();
	console.info("user assignemnt");
	var fileAssignMentVO = new Array();

	$(".assign").each(function(index) {
		console.info($(this).val())
		if($(this).val()!="Assign"){
			var fileAssignMent = new Object();
			fileAssignMent.fileId = $(this).attr('fileid');
			fileAssignMent.needListId = $(this).val();
			fileAssignMentVO.push(fileAssignMent);
		}
		
		
		
	});
	console.info(fileAssignMentVO);

	$.ajax({
		url : "rest/fileupload/assignment/"+currentUserAndLoanOnj.activeLoanId+"/"+currentUserAndLoanOnj.userId+"/"+newfiObject.user.id,
		type : "POST",
		data : JSON.stringify(fileAssignMentVO),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			saveAssignmentonFile(data);
			hideOverlay();
		},
		error : function(error) {
		}
	});
	// ajaxRequest("" , "POST" , "application/json" ,
	// JSON.stringify(fileAssignMentVO) , saveAssignmentonFile);
}



function getNeedItemsWrapper(neededItemListObject) {
	var wrapper = $('<div>').attr({
		"class" : "needed-items-wrapper"
	});
	var header = $('<div>').attr({
		"class" : "needed-items-header uppercase"
	}).html("needed items");
	var container = $('<div>').attr({
		"class" : "needed-items-container clearfix"
	});
	var leftContainer = $('<div>').attr({
		"class" : "needed-items-lc float-left"
	});

	addNeededDocuments(neededItemListObject, leftContainer, container);

	return wrapper.append(header).append(container);
}

function addNeededDocuments(neededItemListObject, leftContainer, container) {

	var hasNeeds = false;

	var needType = neededItemListObject.resultObject.listLoanNeedsListMap.Income_Assets;
	if (needType != undefined && needType.length != 0) {
		leftContainer.append(createdNeededList("Income/Assets", needType));
		hasNeeds = true;
	}

	var needType = neededItemListObject.resultObject.listLoanNeedsListMap.Credit_Liabilities;
	if (needType != undefined && needType.length != 0) {
		leftContainer.append(createdNeededList("Credit/Liabilities", needType));
		hasNeeds = true;
	}

	var needType = neededItemListObject.resultObject.listLoanNeedsListMap.Property;
	if (needType != undefined && needType.length != 0) {
		leftContainer.append(createdNeededList("Property", needType));
		hasNeeds = true;
	}

	var needType = neededItemListObject.resultObject.listLoanNeedsListMap.Other;
	if (needType != undefined && needType.length != 0) {
		leftContainer.append(createdNeededList("Other", needType));
		hasNeeds = true;
	}

	if (!hasNeeds) {
		var incomeDocCont = $('<div>').attr({
			"class" : "needed-doc-container"
		});

		var incDocHeading = $('<div>').attr({
			"class" : "needed-doc-heading"
		}).html(newfiObject.i18n.nl_noneeds);
		incomeDocCont.append(incDocHeading);
		leftContainer.append(incomeDocCont);
		container.append(leftContainer);
		return;
	}

	var rightContainer = $('<div>').attr({
		"class" : "needed-items-rc float-right"
	});
	var knobScore = neededItemListObject.resultObject.neededItemScoreVO;
	
	var inputBox = $("<input>").attr({
				"class" : "dial",
				"id" : "knobUpload",
				"data-min" : "0",
				"data-width" : "150",
				"data-thickness" : "0.1",
				"data-fgColor" : "#F47521",
				"data-bgColor" : "#EFE1DB",
				"data-max" : knobScore.neededItemRequired,
				"value" : knobScore.totalSubmittedItem
	});
	
	rightContainer.append(inputBox);
	container.append(leftContainer).append(rightContainer);

}


function uploadDocument(event){
	var needIdData=$(event.target).data("needId");
	var myDropzone = Dropzone.forElement("div#drop-zone");
	//myDropzone.params("needId" ,needIdData );
	$("#file-upload-icn").click();
	myDropzone.on("sending", function(file, xhr, formData) {
		  // add headers with xhr.setRequestHeader() or
		  // form data with formData.append(name, value);
			formData.append("needId" , needIdData);
	});
	myDropzone.on("processing", function(file) {
		 this.options.url = "rest/fileupload/documentUploadWithNeed";
	});
}



function paintUploadNeededItemsPage(neededItemListObject) {
	var uploadedNeedContainer = $("<div>").attr({
				"id" :"uploadedNeedContainer"
	});
	var header = $('<div>').attr({
		"class" : "upload-item-header uppercase"
	}).html("Upload needed items");
	var container = $('<div>').attr({
		"class" : "upload-item-container"
	});
	var fileDragDropCon = getFileDragAndDropContainer();
	var showSave = false;
	var documentContainer = getDocumentContainer();
	var submitBtn = $("<div>").attr({
		"class" : "submit-btn"
	}).click(saveUserDocumentAssignments).html("Save");

	var neededItemsWrapper = getNeedItemsWrapper(neededItemListObject);
	// var uploadedItemsWrapper = getUploadedItemsWrapper();

	container.append(fileDragDropCon).append(documentContainer).append(
			submitBtn).append(neededItemsWrapper);

	uploadedNeedContainer.append(header).append(container);
	$('#center-panel-cont').append(uploadedNeedContainer);
	// using dropzone js for file upload
	createDropZone();
	
	var uploadedItems = neededItemListObject.resultObject.listUploadedFilesListVO;
	showFileLink(uploadedItems);

	if ($('.document-cont-col').length == undefined
			|| $('.document-cont-col').length == 0) {
		$('.submit-btn').addClass('hide');
	}

}



function createDropZone(needID){
	
	
	var needId = null;
	if(needID != undefined){
		needId = needID;
	}
	
	var unSupportedFile = new Array();
	var myDropZone = new Dropzone("#drop-zone", {
		url : "rest/fileupload/documentUpload",
		clickable : "#file-upload-icn",
		params : {
			userID : currentUserAndLoanOnj.userId,
			loanId : currentUserAndLoanOnj.activeLoanId ,
			assignedBy : newfiObject.user.id,
			
		},
		drop : function() {

		},
		complete : function(file, response) {
			hideOverlay();
			
			$('#file-upload-icn').removeClass('file-upload-hover-icn');
			getRequiredDocuments();
			
		},
		success : function(file, response){
			console.info(response);
			if(response.error != undefined){
				showDialogPopup("User Session " , "Session logged out! Click ok to continue" , function(){
					location.reload();
					return false;
				}); 
				
				return false;	
			}
			if(response[0]!= undefined){
				unSupportedFile.push(response[0]);
			}
			
		},
		dragenter : function() {
			$('#file-upload-icn').addClass('file-upload-hover-icn');
		},
		dragleave : function() {
			$('#file-upload-icn').removeClass('file-upload-hover-icn');
		},
		dragover : function() {
			$('#file-upload-icn').addClass('file-upload-hover-icn');
		},
		queuecomplete : function() {
			
			var list = $("<div>");
			for(i in unSupportedFile){
				list.append("<p>").append(unSupportedFile[i]);
			}
			if(unSupportedFile.length>0){
				showDialogPopup("Unsupported File" , list , function(){
					return false;
				});
			}
			unSupportedFile = new Array();
			console.info(unSupportedFile);
			
		},
		addedfile : function(){
			showOverlay();
			$('#file-upload-icn').addClass('file-upload-loading');
		}
	});

}
