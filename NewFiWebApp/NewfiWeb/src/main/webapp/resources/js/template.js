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
					TemplateVO[i].smsText);
		}

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

											// alert("Success");
											populateTemplate();
											showToastMessage(savesuccessfull);

										}

									});

						});

		var saveBtnDiv = $('<div>').attr({
			"class" : "template-save-button-div"
		});
		saveBtnDiv.append(saveBtn);
		$(saveBtnDiv).insertAfter('#container');
	}

	context.appendTextField = function(id, name, value, smsText) {

	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var rowCol1 = $('<div>').attr({
		"class" : "template-column-val float-left"
	}).html(name);

	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg col-one",
		"value" : value,
		"id" : "create_" + id
	});
	
	inputCont.append(emailInput);
	rowCol2.append(inputCont);
	var rowCol3 = $('<div>').attr({
			"class" : "prof-form-rc float-left "
	});
	var col3Cont = $('<div>').attr({
			"class" : "prof-form-input-cont"
	});
	
	var smsInput = $('<input>').attr({
			"class" : "prof-form-input prof-form-input-lg col-two",
			"value" : smsText,
			"id" : "sms_" + id,
			"maxlength":50
		});
	col3Cont.append(smsInput);
	rowCol3.append(col3Cont);
	row.append(rowCol1).append(rowCol2).append(rowCol3);
		$('#templateContainer').append(row);
		
	}
}

function appendHeaderColumn(){
	var row = $('<div>').attr({
		"class" : "prof-form-row clearfix"
	});
	var Column1 = $('<div>').attr({
		"class" : "prof-form-row header-col-1 float-left"
	}).html("Template Name");
	var Column2 = $('<div>').attr({
		"class" : "prof-form-row header-col-2 float-left"
	}).html("Template ID");
	var Column3 = $('<div>').attr({
		"class" : "prof-form-row header-col-3 float-left"
	}).html("SMS Text");
	return row.append(Column1).append(Column2).append(Column3);
}