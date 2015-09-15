function turnAroundTime() {
	
	var context = new Object();
	context.userID = newfiObject.user.id;
	var data = {};
	$('#right-panel').html('');
	var saveContextObj = [];
	ajaxRequest("rest/admin/retrieveTurnAround", "GET", "json", data, function(
			response) {
		if (response.error) {
			showErrorToastMessage(response.error.message)
		} else {

			if (response.resultObject == undefined
					|| response.resultObject == "") {
				// context.createTitleTurnAround();
			} else {
				context.createTitleTurnAround(response.resultObject);
			}

		}

	});

	context.createTitleTurnAround = function(MileStoneTurnAroundTimeVO) {

		if($('#turn_around_times_container').length > 0){
			return;
		}
		var tunrarounfDashboardMainContainer = $('<div>').attr({
			"id" : "turn_around_times_container",
			"class" : "rp-agent-dashboard float-left"
		});
		$('#right-panel').append(tunrarounfDashboardMainContainer);
		var header = $('<div>').attr({
			"class" : "agent-customer-list-header  clearfix"

		});
		var leftCon = $('<div>').attr({
			"class" : "agent-customer-list-header-txt page-header-loan float-left"
		});
		leftCon.html("Turn Times");
		var container = $('<div>').attr({
			"class" : "customer-list-contianer clearfix",
			"id" : "container"
		});

		var leadsWrapper = $('<div>').attr({
			"class" : "cutomer-leads-wrapper ",
			"id" : "customer_milestones"
		});

		var customerHeader = $('<div>').attr({
			"class" : "agent-wrapper-header agent-wrapper-header-active"
		}).html("Customer Milestones");

		header.append(leftCon);
		leadsWrapper.append(customerHeader);
		container.append(leadsWrapper);
		tunrarounfDashboardMainContainer.append(header).append(container);
		for (i = 0; i < 4; i++) {
			this.appendTextField(
					MileStoneTurnAroundTimeVO[i].workflowItemMasterId,
					MileStoneTurnAroundTimeVO[i].name, "customer",
					MileStoneTurnAroundTimeVO[i].hours);
			// this.appendTextField("Needs List","customer");
			// this.appendTextField("Application fee","customer");
			// this.appendTextField("Lock Rates","customer");
		}

		var managerLeadsWrapper = $('<div>').attr({
			"class" : "cutomer-leads-wrapper ",
			"id" : "manager_milestones"
		});

		var managerHeader = $('<div>').attr({
			"class" : "agent-wrapper-header"
		}).html("Loan Advisor Milestones");

		managerLeadsWrapper.append(managerHeader);
		container.append(managerLeadsWrapper);
		tunrarounfDashboardMainContainer.append(container);
		for (i = 4; i < 12; i++) {
			this.appendTextField(
					MileStoneTurnAroundTimeVO[i].workflowItemMasterId,
					MileStoneTurnAroundTimeVO[i].name, "manager",
					MileStoneTurnAroundTimeVO[i].hours);
			/*
			 * this.appendTextField("System Education","manager",1);
			 * this.appendTextField("1003 Complete / Application
			 * Complete","manager",2); this.appendTextField("Credit
			 * Score","manager",3); this.appendTextField("AUS (Automated
			 * Underwriting)","manager"); this.appendTextField("Disclosures/
			 * Intent To Proceed","manager");
			 * this.appendTextField("Appraisal","manager");
			 * this.appendTextField("Underwriting Status","manager");
			 * this.appendTextField("Loan: Closed/Declined","manager");
			 */
		}
		var saveBtn = $('<div>')
				.attr({
					"class" : "cep-button-color prof-cust-save-btn float-left"
				})
				.html("save")
				.on(
						'click',
						function(event) {
							event.stopImmediatePropagation();
							for (i = 0; i < 12; i++) {
								if ($(
										"#create_"
												+ MileStoneTurnAroundTimeVO[i].workflowItemMasterId)
										.val() != "") {
									MileStoneTurnAroundTimeVO[i].hours = $(
											"#create_"
													+ MileStoneTurnAroundTimeVO[i].workflowItemMasterId)
											.val();
									MileStoneTurnAroundTimeVO[i].createdby = context.userID;
									MileStoneTurnAroundTimeVO[i].modifiedby = context.userID;
									saveContextObj
											.push(MileStoneTurnAroundTimeVO[i]);
								}
							}
							ajaxRequest(
									"rest/admin/saveMileStoneTurnArounds",
									"POST",
									"json",
									JSON.stringify(saveContextObj),
									function(response) {
										if (response.error) {
											showErrorToastMessage(response.error.message);
										} else {

											// alert("Success");
											turnAroundTime();
											showToastMessage(savesuccessfull);

										}

									});

						});

		// $('#turn_around_times_container').append(saveBtn);
		var saveBtnDiv = $('<div>').attr({
			"class" : "save-button-div"
		});
		saveBtnDiv.append(saveBtn);
		$(saveBtnDiv).insertAfter('#container');
	}
	context.appendText = function(name, appendDiv) {

		var label = $('<div>').attr({
			"class" : "create-turn-around-label float-left"
		}).html(name);
		if (appendDiv == "customer")
			$('#customer_milestones').append(label);
		else
			$('#manager_milestones').append(label);
	}
	context.appendTextField = function(id, name, appendDiv, value) {
		if (name == "Application") {
			name = "Application Complete";
		}
		if (name == "Needs") {
			name = "Needs List";
		}
		if (name == "Lock Rate") {
			name = "Lock Rates";
		}
		if (name == "1003 Complete") {
			name = "1003 Complete / Application Complete";
		}
		if (name == "Underwriting") {
			name = "Underwriting Status";
		}
		if (name == "Closing Status") {
			name = "Loan: Closed/Declined";
		}
		var row = $('<div>').attr({
			"class" : "create-turn-around-cont clearfix float-left"
		});
		var label = $('<div>').attr({
			"class" : "create-turn-around-label float-left"
		}).html(name);

		var inputBox = $('<input>').attr({
			"class" : "create-turn-around-input float-left",
			"id" : "create_" + id
		}).val(value);
		var hour = $('<div>').attr({
			"class" : "create-turn-around-label-hours float-left"
		}).html("hrs");
		row.append(label).append(inputBox).append(hour);
		if (appendDiv == "customer")
			$('#customer_milestones').append(row);
		else
			$('#manager_milestones').append(row);

		$('.create-turn-around-input')
				.bind(
						'keypress',
						function(e) {
							return (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) ? false
									: true;
						});
	}

	context.appendSubTextField = function(name) {

		var row = $('<div>').attr({
			"class" : "prof-form-rc float-left"
		});
		var label = $('<div>').attr({
			"class" : "prof-form-input-cont"
		}).html(name);

		var inputBox = $('<input>').attr({
			"class" : "prof-form-input",
			"id" : "create_" + name
		}).val("");
		row.append(label).append(inputBox);
		$('#customer_milestones').append(row);
	}

}
