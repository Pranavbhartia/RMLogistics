function getLoanNeedsManagerContext(loanId){
	
	var loanNeedsListContext={
		loanId:loanId,
		needsList:[],
		selectedNeeds:[],
		getNeedsList:function(callback){
			var data={};
			data.loanId=1;
			var ob=this;
			ajaxRequest("http://localhost:8080/NewfiWeb/rest/loanneeds/get","GET","json",data,function(response){
				if(response.error){

				}else{
					ob.needsList=response.resultObject;
					if(callback){
						callback(ob);
					}
				}
				
			});
		},
		cleanDocData:function(){
			docData.income=[];
			docData.property=[];
			docData.asset=[];
			docData.other=[];
		},
		populateNeedsList:function(callback){
			this.cleanDocData();
			var arrayLength = this.needsList.length;
			for (var i = 0; i < arrayLength; i++) {
				var category=this.needsList[i].needCategory;

			    switch (category) {
				    case "Credit/Liabilities":
				        //append in Credit/Liabilities div
				        docData.income.push(this.needsList[i]);
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
			var data={};
			data.loanId=this.loanId;
			data.needs=JSON.stringify(this.selectedNeeds)
			var ob=this;
			ajaxRequest("http://localhost:8080/NewfiWeb/rest/loanneeds/save","POST","json",data,function(response){
				if(response.error){
					alert(response.error.message);
				}else{
					alert("Save Successful")
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
		}
	};
	return loanNeedsListContext;
}
var contxt;
function paintAgentNeedsListPage(){

	var loanNeedContext=getLoanNeedsManagerContext(1);//Insert Proper Loan Id here
	loanNeedContext.init(function(){
		appendDocumentToolTip();
		appendCustomerDetailHeader();
		appendInitialNeedsListWrapper();
		paintUploadNeededItemsPage();
	})
	contxt=loanNeedContext;
	
}

function appendInitialNeedsListWrapper(){
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
	
	var incomeDocContainer = getNeedsListDocumentContainer("income",docData.income).addClass('float-left');
	
	var propertyDocContainer = getNeedsListDocumentContainer("property",docData.property).addClass('float-right');
	
	var assetDocContainer = getNeedsListDocumentContainer("asset",docData.asset).addClass('float-right');
	
	var otherDocContainer = getNeedsListDocumentContainer("other",docData.other).addClass('float-left');
	
	container.append(incomeDocContainer).append(propertyDocContainer).append(assetDocContainer).append(otherDocContainer);
	
	wrapper.append(header).append(container);
	$('#center-panel-cont').append(wrapper);
	
	appendAddNeedsContainer();
	
	//append save button
	var savebtnWrapper = $('<div>').attr({
		"class" : "need-list-save-btn-wrapper"
	});
	
	var savebtn = $('<div>').attr({
		"id" : "",
		"class" : "need-list-save-btn",
		"onclick": "testFun()"
	}).html("Save Needs");
	
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
function testFun(){
	contxt.updateNeedsList(function(){
		contxt.saveSelectedNeedsList();
	});
}