function getLoanNeedsManagerContext(loanId){
	
	var loanNeedsListContext={
		loanId:loanId,
		needsList:[],
		selectedNeeds:[],
		customList:{},
		needLookup:{},
		initialNeeds:false,
		ajaxRequest	:function (url,type,dataType,data,successCallBack){
			$.ajax({
				url : url,
				type : type,
				dataType : dataType,
				data : data,
				success : function(response){
					if(response.status&&response.status==="Session Expired"){
						var component=$("#right-panel");
						if(component&&component.length==0){
							component=$(document.body)
						}
						var content="<div class='rp-agent-dashboard float-left'><div class='center-text-session-expire'>"+response.message+"</div></div>"
						$(component).html(content);
					}else{
						successCallBack(response);
					}
				},
				error : function(){
					
				}
			});
		},
		getCustomNeedList:function(callback){
			var ob=this;
			var data={};
			ob.customList={};
			ob.ajaxRequest("rest/loanneeds/custom","GET","json",data,function(response){
				if(response.error){
					showToastMessage(response.error.message)
				}else{
					var customList=response.resultObject;
					for(var i=0;i<customList.length;i++){
						ob.addCustomNeedToList(customList[i]);
					}
					if(callback){
						callback(ob);
					}
				}
				
			});
		},
		addCustomNeedToList:function(need){
			var ob=this;
			var category=need.needCategory;
			switch(category){
				case "Credit/Liabilities":
					if(!ob.customList.liability){
						ob.customList.liability=[];
					}
					ob.customList.liability.push(need);
				    break;
			    case "Property":
			    	if(!ob.customList.property){
						ob.customList.property=[];
					}
					ob.customList.property.push(need);
				    break;
			    case "Income/Assets":
			    	if(!ob.customList.income){
						ob.customList.income=[];
					}
					ob.customList.income.push(need);
				    break;
			    case "Other":
			   		if(!ob.customList.Other){
						ob.customList.Other=[];
					}
					ob.customList.Other.push(need);
				    break;
			}
		},
		mapNeedCategory : function (category){
			switch(category){
				case "liability":
				        return "Credit/Liabilities";
			    case "property":
			    	return "Property";
			    case "income":
			    	return "Income/Assets";
			    case "other":
			   		return "Other"
			}
		},
		mapNeedCategoryBack : function (category){
			switch(category){
				case "Credit/Liabilities":
				        return "liability";
			    case "Property":
			    	return "property";
			    case "Income/Assets":
			    	return "income";
			    case "Other":
			   		return "other";
			}
		},
		saveCustomNeed:function(callback){
			var ob=this;
			var f_category=$("#need_doc_type").val();
			var category=ob.mapNeedCategory($("#need_doc_type").val());
			var label=$("#need_doc_title").val();
			var desc=$("#need_doc_desc").val();
			if(label==""){
				showErrorToastMessage(invalidDocumentTitle);
			}else if(desc==""){
				showErrorToastMessage(invalidDocumentDescription);
			}else if(category==""){
				showErrorToastMessage(invalidDocumentType);
			}else{
				var data={};
				data.category=category;
				data.label=label;
				data.description=desc;
				var exist;
				var categoryList=ob.customList[f_category];
				if(categoryList){
					for(var i=0;i<categoryList.length;i++){
						if(categoryList[i].title==label){
							exist=categoryList[i];
							break;
						}
					}
				}

				if(exist){
						if(!ob.needLookup[exist.title]){
							var document = exist;
							document.isChecked=true;
							ob.addCustomNeedToList(document);
							var newNeedRow = getNeededDocumentRow(document);
							
							$('.initial-list-doc-wrapper[data-doc-type="'+f_category+'"]').find('.initial-list-doc-container').append(newNeedRow);
							clearAddNeedForm();
							if(callback){
								callback();
							}
						}else{
							showErrorToastMessage(needAlreadyExists);
						}
				}else{
					ob.ajaxRequest("rest/loanneeds/custom","POST","json",data,function(response){
						if(response.error){
							showToastMessage(response.error.message)
						}else{
							var componentId=response.resultObject;
							var document = {
								"isChecked" : true,
								"title" : label,
								"desc" : desc,
								"needType" : componentId,
								"needCategory" : category
							};
							ob.addCustomNeedToList(document);
							var newNeedRow = getNeededDocumentRow(document);
							
							$('.initial-list-doc-wrapper[data-doc-type="'+f_category+'"]').find('.initial-list-doc-container').append(newNeedRow);
							clearAddNeedForm();
							if(callback){
								callback();
							}
						}
						
					});
				}
				
			}
		},
		getNeedsList:function(callback){
			var data={};
			data.loanId=this.loanId;
			var ob=this;

			ob.ajaxRequest("rest/loanneeds/"+this.loanId,"GET","json",data,function(response){
				if(response.error){
					showToastMessage(response.error.message)
				}else{
					ob.initialNeeds=response.resultObject.initialCreation;
					ob.needsList=response.resultObject.result;
					if(callback){
						callback(ob);
					}
				}
				
			});
		},
		cleanDocData:function(){
			docData.liability=[];
			docData.property=[];
			docData.asset=[];
			docData.other=[];
			this.needLookup={};
		},
		populateNeedsList:function(callback){
			this.cleanDocData();
			var ob=this;
			var arrayLength = this.needsList.length;
			for (var i = 0; i < arrayLength; i++) {
				var category=this.needsList[i].needCategory;
				ob.needLookup[this.needsList[i].title]=this.needsList[i];
			    switch (category) {
				    case "Credit/Liabilities":
				        //append in Credit/Liabilities div
				        docData.liability.push(this.needsList[i]);
				       // $('#check').append('<input type="checkbox" name="needsCheckBox" needId="'needsList[i].needType'" title="'+needsList[i].description+'" checked="'+needsList[i].required+'">' + needsList[i].label+'</input>');
				        break;
				    case "Property":
				    	docData.property.push(this.needsList[i]);
				       //$('#check').append('<input type="checkbox" name="needsCheckBox" needId="'needsList[i].needType'" title="'+needsList[i].description+'" checked="'+needsList[i].required+'">' + needsList[i].label+'</input>');
				        break;
				    case "Income/Assets":
				    	docData.asset.push(this.needsList[i]);
				       //$('#check').append('<input type="checkbox" name="needsCheckBox" needId="'needsList[i].needType'" title="'+needsList[i].description+'" checked="'+needsList[i].required+'">' + needsList[i].label+'</input>');
				        break;
				    case "Other":
				   		docData.other.push(this.needsList[i]);
				       // $('#check').append('<input type="checkbox" name="needsCheckBox" needId="'needsList[i].needType'" title="'+needsList[i].description+'" checked="'+needsList[i].required+'">' + needsList[i].label+'</input>');
				        break;
				}
			}
			if(callback){
				callback(this);
			}
		},
		updateNeedsList:function(callback){
			var checkboxes = $(".doc-checked");
			var checkboxesChecked = [];

			for (var i=0; i<checkboxes.length; i++) {
				
					var ob=checkboxes[i].getAttribute("needId");
					checkboxesChecked.push(ob);
				
			}
			this.selectedNeeds=checkboxesChecked;

			if(callback){
				callback();
			}
		},
		saveSelectedNeedsList:function(callback){
			//API call TO save Updated Needs List
			var ob=this;
			var data={};
			data.needs=JSON.stringify(this.selectedNeeds);
			var ob=this;
			ob.ajaxRequest("rest/loanneeds/"+ob.loanId,"POST","json",data,function(response){
				if(response.error){
					showToastMessage(response.error.message);
				}else{
					showToastMessage(savesuccessfull);
					window.location.reload();
					if(callback){
						callback(ob);
					}
				}
				
			});
		},
		init:function(callback){
			this.getNeedsList(function(ob){
				ob.populateNeedsList(callback);
			});
			this.getCustomNeedList();
		}
	};
	return loanNeedsListContext;
}

var contxt;
function paintAgentNeedsListPage(callback){

	var loanNeedContext=getLoanNeedsManagerContext(selectedUserDetail.loanID);//Insert Proper Loan Id here
	loanNeedContext.init(function(){
		appendDocumentToolTip();
		appendCustomerDetailHeader(selectedUserDetail);
		if(!userIsRealtor()){
			appendInitialNeedsListWrapper();	
		}
		
		if(callback){
			callback();
		}
		
		currentUserAndLoanOnj.userId = selectedUserDetail.userID;
		currentUserAndLoanOnj.activeLoanId = selectedUserDetail.loanID;
		
		getRequiredDocuments();
	})
	contxt=loanNeedContext;
	
}

function appendInitialNeedsListWrapper(){
	if(userIsRealtor()){
		return ;
	}
	var wrapper = $('<div>').attr({
		"id" : "initial-needs-wrapper",
		"class" : "initial-needs-wrapper"
	});

	var header = $('<div>').attr({
		"class" : "initial-needs-header"
	}).html("initial need list");
	
	var container = $('<div>').attr({
		"class" : "initial-needs-container clearfix"
	});
	
	var incomeDocContainer = getNeedsListDocumentContainer("income",docData.asset).addClass('float-left');
	
	var propertyDocContainer = getNeedsListDocumentContainer("property",docData.property).addClass('float-right');
	
	var assetDocContainer = getNeedsListDocumentContainer("liability",docData.liability).addClass('float-right');
	
	var otherDocContainer = getNeedsListDocumentContainer("other",docData.other).addClass('float-left');
	
	container.append(incomeDocContainer).append(propertyDocContainer).append(assetDocContainer).append(otherDocContainer);
	
	wrapper.append(header).append(container);
	$('#center-panel-cont').append(wrapper);
	
	appendAddNeedsContainer();
	
	//append save button
	var savebtnWrapper = $('<div>').attr({
		"class" : "need-list-save-btn-wrapper"
	});
	var saveBtnText="Save Needs";
	if(contxt.initialNeeds){
		saveBtnText="Create Initial Needs"
	}
	
	var savebtn = $('<div>').attr({
		"id" : "saveNeedsButton",
		"class" : "need-list-save-btn",
		"onclick": "saveLoanNeeds()"
	}).html(saveBtnText);
	
	savebtnWrapper.append(savebtn);
	
	$('#center-panel-cont').append(savebtnWrapper);
}
function getNeedsListDocumentContainer(docType,documents){
	var docWrapper = $('<div>').attr({
		"class" : "initial-list-doc-wrapper",
		"data-doc-type" : docType
	});
	
	var header = $('<div>').attr({
		"class" : "initial-list-doc-header"
	}).html(docType + " Documents"); 
	
	var container = $('<div>').attr({
		"class" : "initial-list-doc-container"
	});
	
	for(var i=0; i<documents.length; i++){
		var row = getNeededDocumentRow(documents[i]);
		container.append(row);
	}
	
	return docWrapper.append(header).append(container);
}
function getNeededDocumentRow(document){
	var row = $('<div>').attr({
		"class" : "initial-list-doc-row clearfix"
	});
	
	var checkBox = $('<div>').attr({
		"class" : "doc-checkbox float-left",
		"needId": document.needType
	});
	if(document.isChecked == true){
		checkBox.addClass('doc-checked');
	}else{
		checkBox.addClass('doc-unchecked');
	}
	
	var docTitle = $('<div>').attr({
		"class" : "doc-title float-left",
		"title" : document.desc//need to add id data here
	}).html(document.title);
	
	/*docTitle.bind('mouseenter',{"desc" : document.desc},function(event){
		var leftOffset = $(this).offset().left;
		var topOffset = $(this).offset().top;
		var desc = event.data.desc;
		showDocumentToolTip(desc, topOffset, leftOffset);
	});
	
	docTitle.bind('mouseleave',function(event){
		hideDocumentToolTip();
	});*/
	
	return row.append(checkBox).append(docTitle);
}
function saveLoanNeeds(){
	contxt.updateNeedsList(function(){
		contxt.saveSelectedNeedsList();
	});
}
function saveCustomNeed(){
	contxt.saveCustomNeed();
}