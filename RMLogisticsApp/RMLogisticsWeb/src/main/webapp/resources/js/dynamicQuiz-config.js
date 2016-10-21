// Setup your quiz text and questions here

// NOTE: pay attention to commas, IE struggles with those bad boys

var quizJSONStepOne = {

	"questions" : [ { // Question 1 - Sngle input string
		"q" : "What's your name?",
		"qType" : "inputTextBoxString",
		"dataName" : "name",
		"placeHolder" : "Name",
		"dataValidation" : "length",
		"validationLength" : 'min5',
		"onSelectEvent" : function() {
			console.log("On select inputTextBoxString");
		}
	}, { // Question 2 - yes no
		"q" : "Do you already have a loan active?",
		"qType" : "YesOrNoQuestion",
		"dataName" : "name",
		"dataValidation" : "length",
		"validationLength" : 'min5',
		"onSelectEvent" : function() {
			console.log("On select YesOrNoQuestion");
		}
	}, { // Question 3 - yes no
		"q" : "What is your age?",
		"qType" : "InputTextBoxNumber",
		"dataName" : "age",
		"placeHolder" : "Age",
		"dataValidationAllowing" : "range[1;110]",
		"onSelectEvent" : function() {
			console.log("On select InputTextBoxNumber");
		}
	}, { // Question 4 - yes no
		"q" : "What is your email ID?",
		"qType" : "InputTextBoxEmailId",
		"dataName" : "emailId",
		"placeHolder" : "EmailId@company.com",
		"onSelectEvent" : function() {
			console.log("On select InputTextBoxEmailId");
		}
	}, { // Question 5 - MultipleInputTextBox
		"q" : "What is your address",
		"qType" : "MultipleInputTextBox",
		"dataName" : "address",
		"inputBoxes" : [ {
			"placeHolder" : "Street",
			"type" : "text",
			"dataName" : "address-street",
			"data-validation" : "length",
			"validationLength" : "min3"
		}, {
			"placeHolder" : "City",
			"type" : "text",
			"dataName" : "address-city",
			"data-validation" : "length",
			"validationLength" : "min3"
		}, {
			"placeHolder" : "zipCode",
			"type" : "number",
			"dataName" : "zipCode",
			"data-validation" : "number",
			"dataValidationAllowing" : "range[1;99999]"
		} ]

	} ]
};

var quizJSONStepTwo = {
	"questions" : [ { // Question 1 - Multiple Choice, Single True Answer
		"q" : "What is the interest rate you are looking for?",
		"qType" : "SingleSelectRadioButton",
		"dataName" : "rate",
		"required" : true,
		"a" : [ {
			"option" : "8",
			"value" : 8,
			"onSelect" : {
				"event" : function() {
					console.log("Value 8 selected");
				},
				"addQuestions" : [ {
					"q" : "whats your last name?",
					"qType" : "inputTextBoxString",
					"dataName" : "lastname",
					"placeHolder" : "Last Name",
					"dataValidation" : "length",
					"validationLength" : 'min3',
				} ]
			}
		}, {
			"option" : "14",
			"value" : 14
		}, {
			"option" : "1",
			"value" : 1
		}, {
			"option" : "23",
			"value" : 23
		} // no comma here
		],
		"onSelectEvent" : function() {
			console.log("On select SingleSelectRadioButton");
		}
	}, { // Question 1 - Multiple Choice, Single True Answer
		"q" : "What type of a property is it?",
		"qType" : "SingleSelectDropDown",
		"dataName" : "multi",
		"required" : false,
		"a" : [ {
			"option" : "Home",
			"value" : "Independent home"
		}, {
			"option" : "Condo",
			"value" : 'Condo',
			"onSelect" : {
				"event" : function() {
					console.log("Value 10 selected");
				},
				"addQuestions" : [ { // Question 1 - Sngle input string
					"q" : "What's your Condo association's name?",
					"qType" : "inputTextBoxString",
					"dataName" : "condoAscName",
					"placeHolder" : "Condo Association Name",
					"dataValidation" : "length",
					"validationLength" : 'min5',
					"onSelectEvent" : function() {
						console.log("On select inputTextBoxString");
					}
				} ]
			}
		}, {
			"option" : "Apartment",
			"value" : "Apartment/Flat"
		} ],
		"onSelectEvent" : function() {
			console.log("On select SingleSelectDropDown");
		}
	}, { // Question 1 - Multiple Choice, Multiple Answers
		"q" : "What options are you looking for?",
		"qType" : "MultiSelectCheckBox",
		"dataName" : "checkBoxDemo",
		"required" : true,
		"a" : [ {
			"option" : "Consulting",
			"value" : 8,
			"onSelect" : {
				"event" : function() {
					console.log("Value 8 selected");
				},
				"addQuestions" : [ {
					"q" : "whats your age?",
					"qType" : "inputTextBoxNumber"
				} ]
			}
		}, {
			"option" : "Help",
			"value" : 14
		}, {
			"option" : "Mortgage",
			"value" : 1
		}, {
			"option" : "Insurance",
			"value" : 23
		} // no comma here
		],
		"onSelectEvent" : function() {
			console.log("On select MultiSelectCheckBox");
		}
	} ]
};

var newFiTeaserStart = {

	"questions" : [ // Question 1 - Multiple Choice, Single True Answer
	{
		"q" : "What do you want NewFi to help you with?",
		"qType" : "SingleSelectRadioButton",
		"dataName" : "serviceRequested",
		"required" : true,
		"a" : [ {
			"option" : "Mortgage home",
			"value" : "mortgageHome"
		}, {
			"option" : "Reverse Mortgage Home",
			"value" : "reverseMortgage"
		}, {
			"option" : "Lower monthly payment",
			"value" : "lowerPayment"
		} ],
		"onSelectEvent" : function() {
			console.log("On select SingleSelectRadioButton");
		}
	} ]
};

var newFiTeaserMyCurrentMortgage = {

	"questions" : [
			{
				"q" : "What is your age?",
				"qType" : "InputTextBoxCurrency",
				"dataName" : "cash-out-input",
				"placeHolder" : "Cash out Amount"
			},
			{
				"q" : "What is your age?",
				"qType" : "InputTextBoxCurrency",
				"dataName" : "cash-out-input",
				"placeHolder" : "Cash out Amount"
			},
			{
				"q" : "Does the payment entered above include property taxes and/or homeowners insurance?",
				"qType" : "SingleSelectRadioButton",
				"dataName" : "tni",
				"required" : true,
				"a" : [
						{
							"option" : "Yes",
							"value" : "true",
							"onSelect" : {
								"addQuestions" : [
										{
											"q" : "How much are your annual property taxes??",
											"qType" : "InputTextBoxCurrency",
											"dataName" : "taxes",
											"placeHolder" : "Tax Amount"
										},
										{
											"q" : "How much is your annual homeowners insurance?",
											"qType" : "InputTextBoxCurrency",
											"dataName" : "insurance",
											"placeHolder" : "Insurance Amount"
										} ]
							}
						}, {
							"option" : "No",
							"value" : "false"
						} ],
				"onSelectEvent" : function() {
					console.log("On select SingleSelectRadioButton");
				}
			}, {
				"q" : "What is your current mortgage balance?",
				"qType" : "InputTextBoxCurrency",
				"dataName" : "mortgage-balance",
				"placeHolder" : "Balance Amount"
			}, {
				"q" : "Approximately what is your home worth today?",
				"qType" : "InputTextBoxCurrency",
				"dataName" : "home-value",
				"placeHolder" : "Home Worth"
			}, {
				"q" : "What is the ZIP code of your home??",
				"qType" : "InputTextBoxNumber",
				"dataName" : "home-zipcode",
				"placeHolder" : "Zip Code"
			} ]
};

var newFiTeaserHomeAddress = {

	"questions" : [ { // Question 1 - Sngle input string
		"q" : "What's your name?",
		"qType" : "inputTextBoxString",
		"dataName" : "name",
		"placeHolder" : "Name",
		"dataValidation" : "length",
		"validationLength" : 'min5',
		"onSelectEvent" : function() {
			console.log("On select inputTextBoxString");
		}
	} ]
};