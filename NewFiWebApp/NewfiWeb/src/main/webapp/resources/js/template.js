function populateTemplate() {
	
	var context = new Object();
	context.userID = newfiObject.user.id;
	var data = {};
	$('#right-panel').html('');
	var saveContextObj = [];
	ajaxRequest("rest/admin/retrieveTemplates", "GET", "json", data, function(
			response) {
		if (response.error) {
			showErrorToastMessage(response.error.message)
		} else {

			if (response.resultObject == undefined
					|| response.resultObject == "") {
				// context.createTitleTurnAround();
			} else {
				TemplateVO=response.resultObject;
				context.createTitleTemplate(response.resultObject);
			}

		}

	});
	context.createTitleTemplate = function(TemplateVO) {
		var tunrarounfDashboardMainContainer = $('<div>').attr({
			"id" : "template_container",
			"class" : "rp-agent-dashboard float-left"
		});
		$('#right-panel').append(tunrarounfDashboardMainContainer);
		var header = $('<div>').attr({
			"class" : "agent-customer-list-header clearfix"

		});
		var leftCon = $('<div>').attr({
			"class" : "agent-customer-list-header-txt page-header-loan"
		});
		leftCon.html("Templates");
		var container = $('<div>').attr({
			"class" : "customer-list-contianer clearfix",
			"id" : "container"
		});

		var leadsWrapper = $('<div>').attr({
			"class" : "cutomer-leads-wrapper ",
			"id" : "templateContainer"
		});

	

		header.append(leftCon);
		var column=appendHeaderColumn();
		container.append(column).append(leadsWrapper);
		tunrarounfDashboardMainContainer.append(header).append(container);		
			for (var i = 0; i < TemplateVO.length; i++) {
			this.appendTextField(
					TemplateVO[i].id,
					TemplateVO[i].desc, 
					TemplateVO[i].value,
					TemplateVO[i].smsText,
					TemplateVO[i].subject);
		}	  
	}

	context.appendTextField = function(id, name, value, smsText,subject) {

	var row = $('<div>').attr({
	"class" : "prof-form-row template-row clearfix",
	"id" : "template-row-id"
	});
	
	var rowCol1 = $('<div>').attr({
	"class" : "template-column-val template-header-row-one float-left"
	}).html(name);
	
	var col2 = $('<div>').attr({
	"class" : "template-column-val template-header-row-two float-left",
	"title" : value
	}).html(value);
	
	var col3 = $('<div>').attr({
	"class" : "template-column-val template-header-row-three float-left",
	"title" : smsText
	}).html(smsText);
	
	var col4 = $('<div>').attr({
	"class" : "template-column-val template-header-row-four float-left"
		}).html(subject);
	

	
	var col5 = $('<div>').attr({
		"class" : "template-column-val template-header-row-five float-left"
	}).bind('click',function(e){
		var wrap=$('<div>').attr({
			"class":"template-edit-profile-wrapper clearfix ",
			"id":"template-edit-profile-id"
			});
		var editContainer = appendRow(value,id,smsText,subject);
		wrap.append(editContainer);

		var element=$(".prof-form-row ").find(".template-edit-profile-wrapper");
		$(element).toggle();
		$("#template-edit-profile-id").remove();

			    var saveBtn = $('<div>')
		.attr({
			"class" : "prof-cust-save-btn float-left"
		})
		.html("save")
		.on(
				'click',
		function(event) {
			event.stopImmediatePropagation();
			for (i = 0; i < TemplateVO.length; i++) {
				if ($("#create_"+ TemplateVO[i].id).val() != "") {
			TemplateVO[i].value = $("#create_"+ TemplateVO[i].id).val();
			TemplateVO[i].smsText = $("#sms_"+ TemplateVO[i].id).val();
			TemplateVO[i].subject=$("#subject_"+TemplateVO[i].id).val();
				saveContextObj.push(TemplateVO[i]);
			}
		}
		ajaxRequest(
				"rest/admin/saveTemplates",
			"POST",
			"json",
			JSON.stringify(saveContextObj),
			function(response) {
				if (response.error) {
					showErrorToastMessage(response.error.message);
				} else {
							populateTemplate();
							showToastMessage(savesuccessfull);
		
						}
		
					});		
		});
		var saveBtnDiv = $('<div>').attr({
		"class" : "template-save-button-div"
		});
		saveBtnDiv.append(saveBtn);
		//$(saveBtnDiv).insertAfter('#container');
		wrap.append(saveBtnDiv);
		$('#template-row-id').after(wrap);
	});
	row.append(rowCol1).append(col2).append(col3).append(col4).append(col5);
	$('#templateContainer').append(row);
			
	}
}
function appendRow(value,id,smsText,subject){
	
		var div = $('<div>').attr({
			
		});
		var templateRow = $('<div>').attr({
		 "class" : "template-form-row clearfix"
		});
		
		var templateID = $('<div>').attr({
		 "class" : "template-form-label float-left"
		}).html("Template ID");
		 
		var inputCont = $('<div>').attr({
		 "class" : "template-form-input-cont float-left"
		});
		
		var templateIDInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : value,
		"id" : "create_" + id
		});

		inputCont.append(templateIDInput);
		templateRow.append(templateID).append(inputCont);
		
		var smsTextRow = $('<div>').attr({
		"class" : "template-form-row clearfix"
		});
		
		var smsText = $('<div>').attr({
			"class" : "template-form-label float-left"
		}).html("SMS Text");
		
		var smsTextCont = $('<div>').attr({
		"class" : "template-form-input-cont float-left"
		});
		
		var smsInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : smsText,
		"id" : "sms_" + id,
		"maxlength":50
		});

		smsTextCont.append(smsInput);
		smsTextRow.append(smsText).append(smsTextCont);
		
		var subjectRow = $('<div>').attr({
			"class":"template-form-row clearfix"
		});
		
		var subject = $('<div>').attr({
		"class" : "template-form-label float-left"
		}).html("Subject");
		
	    var subjectCont=$('<div>').attr({
	    	"class":"template-form-input-cont float-left"
		});
		var subInput=$('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : subject,
		"id" : "subject_" + id,
			
		});
	 
		subjectCont.append(subInput);
		subjectRow.append(subject).append(subjectCont);
		div.append(templateRow).append(smsTextRow).append(subjectRow);
        return div;
}
function appendHeaderColumn(){
	var row = $('<div>').attr({
		"class" : "template-list-th clearfix"
	});
	var Column1 = $('<div>').attr({
		"class" : "template-header-col-one float-left"
	}).html("Template Name");
	var Column2 = $('<div>').attr({
		"class" : "template-header-col-two float-left"
	}).html("Template ID");
	var Column3 = $('<div>').attr({
		"class" : "template-header-col-three float-left"
	}).html("SMS Text");
	var Column4=$('<div>').attr({
		"class" : "template-header-col-four float-left"
	}).html("Subject");
	
	var Column5=$('<div>').attr({
		"class" : "template-header-col-five float-left"
	}).html("Edit");
	return row.append(Column1).append(Column2).append(Column3).append(Column4).append(Column5);
}