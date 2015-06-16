/*
 * Functions for upload items module
 */
var SPLIT_DOC = "Split Document";
var needIdtoAssign = null;
var needIdDirectEvent = false;
var loanIdAssigned = new Array();

function uploadNeededItemsPage() {
	var userId = newfiObject.user.id;
	var activeLoanId = newfiObject.user.defaultLoanId;
	currentUserAndLoanOnj.userId = userId;
	currentUserAndLoanOnj.activeLoanId = activeLoanId;
	getRequiredDocuments();
}

function getRequiredDocuments() {

	ajaxRequest("rest/fileupload/needlist/get/" + currentUserAndLoanOnj.userId
			+ "/" + currentUserAndLoanOnj.activeLoanId, "GET", "json", "",
			getRequiredDocumentData);
}
function resetDragDrop(){
	if(!needIdDirectEvent){
		needIdtoAssign = 'reset';	
		var myDropzone = Dropzone.forElement("div#drop-zone");
		myDropzone.on("processing", function(file) {
			this.options.url = "rest/fileupload/documentUpload";
			
		});
		
	}
}
function getRequiredDocumentData(neededItemList) {
	neededItemListObject = neededItemList;
	needIdtoAssign = null;
	$('#uploadedNeedContainer').remove();
	paintUploadNeededItemsPage(neededItemListObject);

	$('#drop-zone').click(function(e){
		resetDragDrop();
		
	});
	$("#knobUpload").knob({
		"data-width" : "50",
		"data-displayInput" : false,
		"readOnly" : true,
		'draw' : function() {
			$(this.i).val(this.cv + '/' + this.o.max);
		}
	});
}

function saveAssignmentonFile() {
	getRequiredDocuments();
}

function checkForSplitOption(select) {

	if ($(select).val() == SPLIT_DOC) {
		var fileName = $(select).attr('fileName');
		var fileId = $(select).attr('fileId');
		var object = new Object();
		object.fileId = fileId;
		object.fileName = fileName;

		showDialogPopup("Confirm File Split",
				"Are you sure want to split the current PDF document.",
				function() {
					splitPDFDocument(object);
				});
	}

}

function splitPDFDocument(dataObject) {
	console.info(dataObject.fileName + " and " + dataObject.fileId);
	var url = "rest/fileupload/split/" + dataObject.fileId + "/"
			+ currentUserAndLoanOnj.activeLoanId + "/"
			+ currentUserAndLoanOnj.userId + "/" + newfiObject.user.id;
	ajaxRequest(url, "GET", "json", "", afterPDFSplit);
}

function afterPDFSplit(response) {
	console.info("pdf splitting done : " + response);
	getRequiredDocuments();
}

function checkforSimilarNeed(Object) {
	select = $(Object);
	console.info(select.attr("ismiscellaneous"));
	if (select.val() != "Assign") {
		$(".assign")
				.each(
						function() {
							if ($(this).val() != "Assign"
									&& !select.is($(this))) {

								if (!(select.attr("ismiscellaneous") == "true" && $(
										this).attr("ismiscellaneous") == "true")) {
									//if (select.val() == $(this).val() && select.attr("masterId")!=$(this).val() ) {
									if (select.val() == $(this).val()) {
										select.val('Assign');
										showDialogPopup(
												"Document Assignment",
												"Cannot assign same need to two or more document",
												function() {
													return false;
												});
									}
								}

							}
						});
	}
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
		"id" : "deactivate_" + listUploadedFiles.id
	// "onclick" : deactivate(listUploadedFiles.id)
	}).click(function(event) {
		event.stopImmediatePropagation();
		deactivate($(this));
	});

	// docImg.append(deactivete);

	if (listUploadedFiles.isMiscellaneous) {
		var img = $("<img>").attr(
				{
					"src" : "readImageAsStream.do?uuid="
							+ listUploadedFiles.uuidFileId + "&isThumb=1"
				}).load(
				function() {
					docImg.css({
						"background" : "url('readImageAsStream.do?uuid="
								+ listUploadedFiles.uuidFileId
								+ "&isThumb=1') no-repeat center",
						"background-size" : "cover"
					});
				});
	}

	// var deleteLink = $("<p class='showAnchor'
	// onclick=deactivate('"+listUploadedFiles.id+"')>").html("( delete )");

	var docDesc = $('<div>').attr({
		"class" : "doc-desc showAnchor"
	}).append(listUploadedFiles.fileName);

	var ahrefFile = $("<a>").attr(
			{
				"href" : "readFileAsStream.do?uuid="
						+ listUploadedFiles.uuidFileId + "&isThumb=0",
				"target" : "_blank"
			});

	var docAssign = $("<select>").attr({
		"class" : "assign",
		"fileId" : listUploadedFiles.id,
		"fileName" : listUploadedFiles.fileName,
		"isMiscellaneous" : listUploadedFiles.isMiscellaneous,
		
		"onchange" : "checkforSimilarNeed(this)"
	}).change(function() {
		checkForSplitOption(this);
	});

	var assignOption = $("<option>").attr({
		"value" : "Assign"
	}).html("Assign");
	var splitOption = $("<option>").attr({
		"value" : SPLIT_DOC
	}).html(SPLIT_DOC);
	docAssign.append(assignOption);

	if (listUploadedFiles.isMiscellaneous && listUploadedFiles.totalPages > 1) {
		docAssign.append(splitOption);
	}

	var neededItemListObj = neededItemListObject.resultObject.listLoanNeedsListVO;

	for (i in neededItemListObj) {

		if (neededItemListObj[i].fileId == undefined) {
			
				var needsListMasterobj = neededItemListObj[i];
				var option = $("<option>").attr({
					"value" : needsListMasterobj.id
				}).html(needsListMasterobj.needsListMaster.label);

				if (needsListMasterobj.id == listUploadedFiles.needType) {
					option.attr('selected', 'selected');
				}
				if (neededItemListObj[i].needsListMaster.id == 40 ) {
					if(userIsInternal()){
						docAssign.append(option);	
					}
						
				}else{
					docAssign.append(option);
				}
				

			
		}else{
			if (userIsInternal() && neededItemListObj[i].needsListMaster.id == 40 ) {
				var needsListMasterobj = neededItemListObj[i];
				var option = $("<option>").attr({
					"value" : needsListMasterobj.id,
					"data" : needsListMasterobj.needsListMaster.id
				}).html(needsListMasterobj.needsListMaster.label);
				docAssign.attr("masterId",needsListMasterobj.id);

					docAssign.append(option);	
				
					
			}
		}

	}

	if (newfiObject.user.userRole.roleDescription == "Realtor") {
		if (listUploadedFiles.assignedByUser.userId == newfiObject.user.id) {
			docImg.attr({
				"data-toggle" : "tooltip",
				"data-placement" : "top",
				"title" : "Click here to view file."
			});
			docImg.click(function() {
				window
						.open("readFileAsStream.do?uuid="
								+ listUploadedFiles.uuidFileId + "&isThumb=0",
								'_blank');
			});
			ahrefFile.append(docDesc);
		} else {
			docImg.attr({
				"data-toggle" : "tooltip",
				"data-placement" : "top",
				"title" : "Cannot access file."
			});
			docImg.addClass("unlink");
			ahrefFile = docDesc;
			docAssign.hide();
		}
	} else {
		docImg.attr({
			"data-toggle" : "tooltip",
			"data-placement" : "top",
			"title" : "Click here to view file."
		});
		docImg.click(function() {
			window.open("readFileAsStream.do?uuid="
					+ listUploadedFiles.uuidFileId + "&isThumb=0", '_blank');
		});
		ahrefFile.append(docDesc);
	}
	column.append(docImg).append(ahrefFile);

	return column.append(docAssign);
}

function deactivate(Obj) {
	var fileString = Obj.attr("id");
	var fileID = fileString.substring(11);
	ajaxRequest("rest/fileupload/deactivate/file/" + fileID, "GET", "json", "",
			getRequiredDocuments);
}

function showFileLink(uploadedItems) {

	var loanNeed = neededItemListObject.resultObject.listLoanNeedsListVO;

	$
			.each(
					uploadedItems,
					function(index, value) {
						var needId = value.needType;
						$('#needDoc' + needId).removeClass('hide');
						$('#needDoc' + needId).addClass('doc-link-icn');
						$('#needDoc' + needId)
								.click(
										function() {
											if (newfiObject.user.userRole.roleDescription == "Realtor") {
												if (value.assignedByUser.userId == newfiObject.user.id) {
													window
															.open(
																	"readFileAsStream.do?uuid="
																			+ value.uuidFileId
																			+ "&isThumb=0",
																	'_blank');
												} else {
													showDialogPopup(
															"File Access",
															"You dont have access to file.",
															function() {
																return false;
															});
												}
											} else {
												window
														.open(
																"readFileAsStream.do?uuid="
																		+ value.uuidFileId
																		+ "&isThumb=0",
																'_blank');
											}

										});
						$("#doc-uploaded-icn_" + needId).addClass("hide");

						for (i in loanNeed) {
							var needIdAssigned = loanNeed[i].needsListMaster.id;
							if (needIdAssigned == needId) {
								var loanAssignments = new Object();
								loanAssignments.needId = needId;
								loanAssignments.loanId = loanNeed[i].id;

							}

						}

					});

}

function saveUserDocumentAssignments() {
	showOverlay();
	console.info("user assignemnt");
	var fileAssignMentVO = new Array();

	$(".assign").each(function(index) {
		console.info($(this).val());
		if ($(this).val() != "Assign") {
			var fileAssignMent = new Object();
			fileAssignMent.fileId = $(this).attr('fileid');
			fileAssignMent.isMiscellanous = $(this).attr('ismiscellaneous');
			fileAssignMent.needListId = $(this).val();
			fileAssignMentVO.push(fileAssignMent);
		}

	});
	console.info(fileAssignMentVO);

	$.ajax({
		url : "rest/fileupload/assignment/"
				+ currentUserAndLoanOnj.activeLoanId + "/"
				+ currentUserAndLoanOnj.userId + "/" + newfiObject.user.id,
		type : "POST",
		data : JSON.stringify(fileAssignMentVO),
		dataType : "json",
		cache : false,
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

	var needType = neededItemListObject.resultObject.listLoanNeedsListMap.System;
	if (needType != undefined && needType.length != 0) {
		leftContainer.append(createdNeededList("System", needType));
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

function uploadDocument(event) {
	var appCreated = false;
	if (selectedUserDetail) {
		if (selectedUserDetail.lqbFileId && selectedUserDetail.lqbFileId != "") {
			appCreated = true;
		}
	} else {
		var appDetails = JSON.parse(newfiObject.appUserDetails);
		if (appDetails.loan && appDetails.loan.lqbFileId
				&& appDetails.loan.lqbFileId != "") {
			appCreated = true;
		}
	}
	if (appCreated) {
		var needIdData = $(event.target).data("needId");
		var myDropzone = Dropzone.forElement("div#drop-zone");
		// myDropzone.params("needId" ,needIdData );
		needIdDirectEvent = true;
		$("#file-upload-icn").click();
		needIdDirectEvent = false;
		myDropzone.on("sending", function(file, xhr, formData) {
			// add headers with xhr.setRequestHeader() or
			// form data with formData.append(name, value);
			if(needIdtoAssign==null){
				formData.append("needId", needIdData);	
			}
			
		});
		myDropzone.on("processing", function(file) {
			this.options.url = "rest/fileupload/documentUploadWithNeed";
		});
	} else {
		showErrorToastMessage(completeYourLoanProfile);
	}
}

function paintUploadNeededItemsPage(neededItemListObject) {
	var uploadedNeedContainer = $("<div>").attr({
		"id" : "uploadedNeedContainer"
	});
	var header = $('<div>').attr({
		"class" : "upload-item-header uppercase"
	}).html("Upload needed items");
	var container = $('<div>').attr({
		"class" : "upload-item-container"
	});
	var fileDragDropCon = getFileDragAndDropContainer(neededItemListObject.resultObject.loanEmailID);
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

function createDropZone(needID) {

	var appCreated = false;
	if (selectedUserDetail) {
		if (selectedUserDetail.lqbFileId && selectedUserDetail.lqbFileId != "") {
			appCreated = true;
		}
	} else {
		var appDetails = JSON.parse(newfiObject.appUserDetails);
		if (appDetails.loan && appDetails.loan.lqbFileId
				&& appDetails.loan.lqbFileId != "") {
			appCreated = true;
		}
	}
	if (appCreated) {
		var needId = null;
		if (needID != undefined) {
			needId = needID;
		}

		var unSupportedFile = new Array();

		var myDropZone = new Dropzone(
				"#drop-zone",
				{
					url : "rest/fileupload/documentUpload",
					clickable : "#file-upload-icn",
					params : {
						userID : currentUserAndLoanOnj.userId,
						loanId : currentUserAndLoanOnj.activeLoanId,
						assignedBy : newfiObject.user.id,

					},
					drop : function() {
						resetDragDrop();
					},
					complete : function(file, response) {
						clearOverlayMessage();

						$('#file-upload-icn').removeClass(
								'file-upload-hover-icn');
						getRequiredDocuments();

					},
					success : function(file, response) {
						console.info(response);
						if (response.error != undefined) {
							showDialogPopup("User Session ",
									"Sorry, we were unable to upload the file. Please try later.",
									function() {
										window.location.reload();
										return false;
									});
							return false;
						}
						if (response[0] != undefined) {
							unSupportedFile.push(response[0]);
						}

					},
					dragenter : function() {
						$('#file-upload-icn').addClass('file-upload-hover-icn');
					},
					dragleave : function() {
						$('#file-upload-icn').removeClass(
								'file-upload-hover-icn');
						resetDragDrop();
					},
					dragover : function() {
						$('#file-upload-icn').addClass('file-upload-hover-icn');
					},
					queuecomplete : function() {

						var list = $("<div>");
						for (i in unSupportedFile) {
							list.append("<p>").append(unSupportedFile[i]);
						}
						if (unSupportedFile.length > 0) {
							showDialogPopup("Unsupported File", list,
									function() {
										return false;
									});
						}
						unSupportedFile = new Array();
						console.info(unSupportedFile);
						$('#overlay-loader').hide();
					},
					addedfile : function() {
						showOverleyMessage(overlayWaitMessage);
						$('#file-upload-icn').addClass('file-upload-loading');
						$('#overlay-loader').show();
					}
				});
	} else {
		$("#drop-zone").on("click", function() {
			showErrorToastMessage(completeYourLoanProfile);
		});
	}
}
