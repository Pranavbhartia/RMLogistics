/*
 * Functions for upload items module
 */
var SPLIT_DOC = "Split Document";

function uploadNeededItemsPage() {
	getRequiredDocuments();
}

function getRequiredDocuments() {
	var userId = newfiObject.user.id;
	var activeLoanId = newfiObject.user.defaultLoanId;
	ajaxRequest("rest/fileupload/needlist/get/" + userId + "/" + activeLoanId,
			"GET", "json", "", getRequiredDocumentData);
}

function getRequiredDocumentData(neededItemList) {
	neededItemListObject = neededItemList;
	$('#center-panel-cont').empty();
	paintUploadNeededItemsPage(neededItemListObject);
}




function checkForSplitOption(select){
	
		if($(select).val()== SPLIT_DOC){
			var fileName = $(select).attr('fileName');
			var fileId = $(select).attr('fileId');
			var object = new Object();
			object.fileId = fileId;
			object.fileName = fileName;
			
			showDialogPopup("Confirm File Split" , 
					"Are you sure want to split the current PDF document." , function(){splitPDFDocument(object)} );
		}
		
}


function splitPDFDocument(dataObject){
	console.info(dataObject.fileName +" and "+dataObject.fileId);
	ajaxRequest("rest/fileupload/split/"+dataObject.fileId+"/"+  newfiObject.user.defaultLoanId,
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
	var docDesc = $('<div>').attr({
		"class" : "doc-desc showAnchor"
	}).html(listUploadedFiles.fileName);
	docImg.click(function(){
		window.open(listUploadedFiles.s3path, '_blank');
	});
	var docAssign = $("<select>").attr({
		"class" : "assign",
		"fileId" : listUploadedFiles.id,
		"fileName" : listUploadedFiles.fileName,
		"onchange" : "checkForSplitOption(this)"
	});

	var assignOption = $("<option>").attr({
		"value" : "Assign"
	}).html("Assign");
	var splitOption = $("<option>").attr({
		"value" : SPLIT_DOC
		}).html(SPLIT_DOC);
	docAssign.append(assignOption).append(splitOption);

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

	return column.append(docImg).append(docDesc).append(docAssign);
}


function showFileLink(uploadedItems) {

	$.each(uploadedItems, function(index, value) {
		var needId = value.needType;
		$('#needDoc' + needId).removeClass('hide');
		$('#needDoc' + needId).addClass('doc-link-icn');
		$('#needDoc' + needId).click(function() {
			window.open(value.s3path, '_blank');
		});
	});
}




function saveUserDocumentAssignments() {
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
		url : "rest/fileupload/assignment/"+newfiObject.user.defaultLoanId,
		type : "POST",
		data : JSON.stringify(fileAssignMentVO),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			saveAssignmentonFile(data);
		},
		error : function(error) {
		}
	});
	// ajaxRequest("" , "POST" , "application/json" ,
	// JSON.stringify(fileAssignMentVO) , saveAssignmentonFile);
}





function paintUploadNeededItemsPage(neededItemListObject) {
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

	$('#center-panel-cont').append(header).append(container);

	// using dropzone js for file upload
	var myDropZone = new Dropzone("#drop-zone", {
		url : "documentUpload.do",
		clickable : "#file-upload-icn",
		params : {
			userID : newfiObject.user.id,
			loanId : newfiObject.user.defaultLoanId
		},
		drop : function() {

		},
		complete : function(response) {

			$('#file-upload-icn').removeClass('file-upload-hover-icn');
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
			getRequiredDocuments();
		}
	});
	var uploadedItems = neededItemListObject.resultObject.listUploadedFilesListVO;
	showFileLink(uploadedItems);

	if ($('.document-cont-col').length == undefined
			|| $('.document-cont-col').length == 0) {
		$('.submit-btn').addClass('hide');
	}

}
