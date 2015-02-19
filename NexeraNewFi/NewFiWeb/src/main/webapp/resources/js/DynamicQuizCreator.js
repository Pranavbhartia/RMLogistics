/*!
 * Dynamic Quiz jQuery Plugin
 *
 * @updated February 17, 2015
 * @version 1.0.0
 *
 * @author Balaji Mohan
 * @copyright (c) 2015 Balaji  Mohan
 */

(function($) {
	$.dynamicQuiz = function(element, quizJSON, options) {
		console.log("Dynamic quiz");
		var plugin = this, $element = $(element), _element = '#'
				+ $element.attr('id'),

		defaults = {
			checkAnswerText : 'Check My Answer!',
			nextQuestionText : 'Next &raquo;',
			backButtonText : '',
			completeQuizText : '',
			tryAgainText : '',
			questionCountText : 'Question %current of %total',
			preventUnansweredText : 'You must select at least one answer.',
			questionTemplateText : '%count. %text',
			scoreTemplateText : '%score / %total',
			nameTemplateText : '<span>Quiz: </span>%name',
			skipStartButton : false,
			numberOfQuestions : null,
			randomSortQuestions : false,
			randomSortAnswers : false,
			preventUnanswered : false,
			disableScore : false,
			disableRanking : false,
			scoreAsPercentage : false,
			perQuestionResponseMessaging : true,
			perQuestionResponseAnswers : false,
			completionResponseMessaging : false,
			displayQuestionCount : true, // Deprecate?
			displayQuestionNumber : true, // Deprecate?
			animationCallbacks : { // only for the methods that have jQuery
				// animations offering callback
				setupQuiz : function() {
				},
				startQuiz : function() {
				},
				resetQuiz : function() {
				},
				checkAnswer : function() {
				},
				nextQuestion : function() {
				},
				backToQuestion : function() {
				},
				completeQuiz : function() {
				}
			},
			events : {
				onStartQuiz : function(options) {
				},
				onCompleteQuiz : function(options) {
				} // reserved: options.questionCount, options.score
			}
		};

		// Reassign user-submitted deprecated options
		var depMsg = '';

		// End of deprecation reassignment

		plugin.config = $.extend(defaults, options);

		// Set via json option or quizJSON variable (see dynamicQuiz-config.js)
		var quizValues = (plugin.config.json ? plugin.config.json
				: typeof quizJSON != 'undefined' ? quizJSON : null);

		// Get questions, possibly sorted randomly
		var questions = quizValues.questions;

		// Count the number of questions
		var questionCount = questions.length;

		// Select X number of questions to load if options is set
		if (plugin.config.numberOfQuestions
				&& questionCount >= plugin.config.numberOfQuestions) {
			questions = questions.slice(0, plugin.config.numberOfQuestions);
			questionCount = questions.length;
		}

		// some special private/internal methods
		var internal = {
			method : {
				// get a key whose notches are "resolved jQ deferred" objects;
				// one per notch on the key
				// think of the key as a house key with notches on it
				getKey : function(notches) { // returns [], notches >= 1
					var key = [];
					for (i = 0; i < notches; i++)
						key[i] = $.Deferred();
					return key;
				},

				// put the key in the door, if all the notches pass then you can
				// turn the key and "go"
				turnKeyAndGo : function(key, go) { // key = [], go = function
					// ()
					// when all the notches of the key are accepted (resolved)
					// then the key turns and the engine (callback/go) starts
					$.when.apply(null, key).then(function() {
						go();
					});
				},

				// get one jQ
				getKeyNotch : function(key, notch) { // notch >= 1, key = []
					// key has several notches, numbered as 1, 2, 3, ... (no
					// zero notch)
					// we resolve and return the "jQ deferred" object at
					// specified notch
					return function() {
						key[notch - 1].resolve(); // it is ASSUMED that you
						// initiated the key with
						// enough notches
					};
				}
			}
		};

		plugin.method = {
			// Sets up the questions and answers based on above array
			setupQuiz : function(options) {

				console.log("Setup quiz called");

				var questsContainer = $('<div>').attr({
					"class" : "questsConainter"
				});
				$(_element).append(questsContainer);
				for (i in questions) {
					// console.log("Question : " +
					// JSON.stringify(questions[i]));

					var question = questions[i];
					var questWrapper = $('<fieldset>').attr({
						class : "questWrapper",
						questId : i

					});
					questsContainer.append(questWrapper);
					var questionRenderer = plugin.questionRenderer[question.qType];
					if (questionRenderer != null
							|| questionRenderer != undefined)
						questionRenderer.render(question, questWrapper);

				}

			},
			// independant method to add questions dynamically other than init
			questRendererHidden : function(question, questionId,
					questWrapperParent, displayToggleGroup, displayWhenSelected) {
				var questWrapper = $('<div>').attr({
					class : "questWrapper hide",
					questId : questionId,
					"displayToggleGroup" : displayToggleGroup,
					"displayWhenSelected" : displayWhenSelected

				});
				questWrapperParent.append(questWrapper);
				var questionRenderer = plugin.questionRenderer[question.qType];
				if (questionRenderer != null || questionRenderer != undefined)
					questionRenderer.render(question, questWrapper);
			},

			// Starts the quiz (hides start button and displays first question)
			startQuiz : function(options) {
				console.log("Starting quiz");
			},

			// Resets (restarts) the quiz (hides results, resets inputs, and
			// displays first question)
			resetQuiz : function(startButton, options) {
				console.log("Reset quiz");
			},

			// Validates the response selection(s), displays explanations & next
			// question button
			checkAnswer : function(checkButton, options) {
				console.log("Check answer");
			},

			// Moves to the next question OR completes the quiz if on last
			// question
			nextQuestion : function(nextButton, options) {
				console.log("Next question");
			},

			// Go back to the last question
			backToQuestion : function(backButton, options) {
				console.log("Back to question");
			},

			// Hides all questions, displays the final score and some conclusive
			// information
			completeQuiz : function(options) {
				console.log("complete quiz");
			},

			// Compares selected responses with true answers, returns true if
			// they match exactly
			compareAnswers : function(trueAnswers, selectedAnswers, selectAny) {
				console.log("Compare answers");
			},

			// Calculates knowledge level based on number of correct answers
			calculateLevel : function(correctAnswers) {
				console.log("Calc level");
			},

			// Determines if percentage of correct values is within a level
			// range
			inRange : function(start, end, value) {
				return (value >= start && value <= end);
			}
		};

		plugin.questionRenderer = {
			"MultiSelectCheckBox" : {
				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);

					var questOptions = $('<fieldset>').attr({
						class : "questionOptions"
					});

					for (i in question.a) {

						var cbWrapper = $('<div>').attr({
							class : "cbWrapper"
						});

						var optionCb = $('<input>').attr({
							class : "optionCb",
							"type" : "checkbox",
							"name" : question.dataName,
							"id" : question.dataName + '-' + i,
							"value" : question.a[i].value
						});

						var cbLabel = $('<label>').attr({
							class : "cbLabel",
							"for" : question.dataName + '-' + i
						});
						cbLabel.text(question.a[i].option);

						cbWrapper.append(optionCb).append(cbLabel);
						questOptions.append(cbWrapper);
					}

					questWrapper.append(quest).append(questOptions);
					// onselect
					if (question.onSelectEvent) {
						questOptions.click(function() {
							question.onSelectEvent();
						});
					}

				}

			},
			"InputTextBoxNumber" : {

				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);

					var questOptions = $('<input>')
							.attr(
									{
										"class" : "questionInputTextbox",
										"type" : "number",
										"name" : question.dataName,
										"id" : question.dataName + '-number',
										"placeholder" : question.placeHolder,
										"data-validation" : "number",
										"data-validation-allowing" : question.dataValidationAllowing
									});

					questWrapper.append(quest).append(questOptions);
					// onselect
					if (question.onSelectEvent) {
						questOptions.click(function() {
							question.onSelectEvent();
						});
					}

				}

			},
			"InputTextBoxEmailId" : {

				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);

					var questOptions = $('<input>').attr({
						"class" : "questionInputTextboxEmail",
						"type" : "email",
						"name" : question.dataName,
						"id" : question.dataName + '-email',
						"placeholder" : question.placeHolder,
						"data-validation" : "email"
					});

					questWrapper.append(quest).append(questOptions);
					// onselect
					if (question.onSelectEvent) {
						questOptions.click(function() {
							question.onSelectEvent();
						});
					}

				}

			},
			"inputTextBoxString" : {

				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);

					var questOptions = $('<input>').attr({
						"class" : "questionInputTextbox",
						"name" : question.dataName,
						"id" : question.dataName + '-string',
						"placeholder" : question.placeHolder,
						"data-validation" : question.dataValidation,
						"data-validation-length" : question.validationLength
					});

					questWrapper.append(quest).append(questOptions);

					// onselect
					if (question.onSelectEvent) {
						questOptions.click(function() {
							question.onSelectEvent();
						});
					}

				}

			},
			"MultipleInputTextBox" : {

				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);

					var questOptWrapper = $('<fieldset>').attr({
						class : "questionInputBoxesWrapper"
					});
					for (i in question.inputBoxes) {
						var inputBox = question.inputBoxes[i];
						var questOptionsWrapper = $('<div>').attr({
							class : "questionInputTextboxWrapper"
						});
						var questOptions = $('<input>').attr({
							"class" : "questionInputTextbox",
							"name" : inputBox.dataName,
							"id" : inputBox.dataName + '-inputbox-' + i,
							"placeholder" : inputBox.placeHolder,
							"data-validation" : inputBox.dataValidation,
							"type" : inputBox.type
						});
						questOptionsWrapper.append(questOptions);
						if (inputBox.type) {
							if (inputBox.type == "number") {
								questOptions.attr("dataValidationAllowing",
										question.dataValidationAllowing);
							} else if (inputBox.type == "text") {
								questOptions.attr("data-validation-length",
										question.validationLength);
							}
						}
						questOptWrapper.append(questOptionsWrapper);

					}
					questWrapper.append(quest).append(questOptWrapper);

					// onselect
					if (question.onSelectEvent) {
						questOptions.click(function() {
							question.onSelectEvent();
						});
					}

				}

			},
			"SingleSelectDropDown" : {
				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);
					var questOptions = $('<select>').attr({
						class : "questionOptions",
						"name" : question.dataName,
						"id" : question.dataName + '-id'
					});

					for (i in question.a) {

						var optionCb = $('<option>').attr({
							class : "optionDropDown",
							"id" : question.dataName + '-cb-' + i,
							"value" : question.a[i].value
						});

						optionCb.text(question.a[i].option);

						questOptions.append(optionCb);

						questWrapper.append(quest).append(questOptions);

						// add additional questions to parent of this question
						// based on value selected

						if (question.a[i].onSelect
								&& question.a[i].onSelect.addQuestions) {
							var additionalQuestions = question.a[i].onSelect.addQuestions;
							for (j in additionalQuestions)
								plugin.method.questRendererHidden(
										additionalQuestions[j],
										question.dataName + '-cb-' + i + '-'
												+ j, questWrapper.parent(),
										question.dataName, question.dataName
												+ '-cb-' + i);
						}

					}

					// onselect
					if (question.onSelectEvent) {

						questOptions.click(function() {
							question.onSelectEvent();

							questWrapper.parent().find(
									'[displayToggleGroup=' + question.dataName
											+ ']').hide();
							var selected = $(
									"#" + question.dataName + '-id'
											+ " option:selected").attr('id')
							questWrapper.parent().find(
									'[displayWhenSelected=' + selected + ']')
									.show();
						});
					}

				}

			},
			"SingleSelectRadioButton" : {

				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);

					var questOptions = $('<fieldset>').attr({
						class : "questionOptions"
					});

					for (i in question.a) {

						var rbWrapper = $('<div>').attr({
							class : "rbWrapper"
						});

						var optionRb = $('<input>').attr({
							class : "optionRb",
							"type" : "radio",
							"name" : question.dataName,
							"id" : question.dataName + '-radio-' + i,
							"value" : question.a[i].value
						});

						var rbLabel = $('<label>').attr({
							class : "rbLabel",
							"for" : question.dataName + '-radio-' + i
						});
						rbLabel.text(question.a[i].option);

						rbWrapper.append(optionRb).append(rbLabel);

						questOptions.append(rbWrapper);
						// adding additional quests
						if (question.a[i].onSelect
								&& question.a[i].onSelect.addQuestions) {
							var additionalQuestions = question.a[i].onSelect.addQuestions;
							for (j in additionalQuestions)
								plugin.method.questRendererHidden(
										additionalQuestions[j],
										question.dataName + '-radio-' + i + '-'
												+ j, questWrapper.parent(),
										question.dataName, question.dataName
												+ '-radio-' + i);
						}

					}

					questWrapper.append(quest).append(questOptions);
					// onselect event
					if (question.onSelectEvent) {

						questOptions.click(function() {
							question.onSelectEvent();
							console.log("Rb clicked");
							questWrapper.parent().find(
									'[displayToggleGroup=' + question.dataName
											+ ']').hide();
							var selected = $(
									"form input[name=" + question.dataName
											+ "]:checked").attr("id");
							questWrapper.parent().find(
									'[displayWhenSelected=' + selected + ']')
									.show();
						});
					}

				}

			},
			"YesOrNoQuestion" : {

				"render" : function(question, questWrapper) {

					if (questWrapper == null)
						return;

					var quest = $('<div>').attr({
						class : "questionString"
					});
					quest.html(question.q);

					var questOptions = $('<fieldset>').attr({
						class : "questionOptions"
					});

					var rbWrapperYes = $('<div>').attr({
						class : "rbWrapperYes"
					});

					var optionRbYes = $('<input>').attr({
						class : "optionRbYes",
						"type" : "radio",
						"name" : question.dataName,
						"id" : question.dataName + '-yes',
						"value" : true
					});

					var rbLabelYes = $('<label>').attr({
						class : "rbLabel",
						"for" : question.dataName + '-yes'
					});
					rbLabelYes.text('Yes');

					rbWrapperYes.append(optionRbYes).append(rbLabelYes);
					questOptions.append(rbWrapperYes);

					var rbWrapperNo = $('<div>').attr({
						class : "rbWrapperNo"
					});

					var optionRbNo = $('<input>').attr({
						class : "optionRbNo",
						"type" : "radio",
						"name" : question.dataName,
						"id" : question.dataName + '-no',
						"value" : false
					});

					var rbLabelNo = $('<label>').attr({
						class : "rbLabelNo",
						"for" : question.dataName + '-no'
					});
					rbLabelNo.text('No');

					rbWrapperNo.append(optionRbNo).append(rbLabelNo);
					questOptions.append(rbWrapperNo);

					// onselect event
					if (question.onSelectEvent) {
						console.log('Adding click handler-yesNoQuestion');
						questOptions.click(function() {
							question.onSelectEvent();
						});
					}
					questWrapper.append(quest).append(questOptions);

				}

			}

		};

		plugin.init = function() {
			// Setup quiz
			plugin.method.setupQuiz.apply(null, [ {
				callback : plugin.config.animationCallbacks.setupQuiz
			} ]);

		};

		plugin.init();
	};

	$.fn.dynamicQuiz = function(quizJSON, options) {
		return this.each(function() {
			if (undefined === $(this).data('dynamicQuiz')) {
				var plugin = new $.dynamicQuiz(this, quizJSON, options);
				$(this).data('dynamicQuiz', plugin);
			}
		});
	};
})(jQuery);

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};