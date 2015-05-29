function populateTemplate() {
	
	var context = new Object();
	context.userID = newfiObject.user.id;
	var data = {};
	$('#right-panel').html('');
	var saveContextObj = [];
	ajaxRequest("rest/admin/retrieveTemplates", "GET", "json", data, function(
			response) {
		if (response.error) {
			showToastMessage(response.error.message)
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
			"class" : "agent-customer-list-header-txt uppercase"
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
		container.append(leadsWrapper);
		tunrarounfDashboardMainContainer.append(header).append(container);
			for (i = 0; i < TemplateVO.length; i++) {
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
											showToastMessage(response.error.message);
										} else {

											// alert("Success");
											populateTemplate();
											showToastMessage("Successfully saved");

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
		"class" : "prof-form-row-desc float-left"
	}).html(name);

	var rowCol2 = $('<div>').attr({
		"class" : "prof-form-rc float-left"
	});

	var inputCont = $('<div>').attr({
		"class" : "prof-form-input-cont"
	});
	
	var emailInput = $('<input>').attr({
		"class" : "prof-form-input prof-form-input-lg",
		"value" : value,
		"id" : "create_" + id
	});
	
	inputCont.append(emailInput);
	rowCol2.append(inputCont);
	var rowCol3 = $('<div>').attr({
			"class" : "prof-form-rc float-left"
	});
	var col3Cont = $('<div>').attr({
			"class" : "prof-form-input-cont"
	});
	
	var smsInput = $('<input>').attr({
			"class" : "prof-form-input prof-form-input-lg",
			"value" : smsText,
			"id" : "sms_" + id
		});
	col3Cont.append(smsInput);
	rowCol3.append(col3Cont);
	row.append(rowCol1).append(rowCol2).append(rowCol3);
		$('#templateContainer').append(row);
		
	}
}