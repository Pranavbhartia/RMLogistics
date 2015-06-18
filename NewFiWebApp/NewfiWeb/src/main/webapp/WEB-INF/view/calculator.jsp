<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>newfi</title>
<link rel="shortcut icon" type="image/x-icon" href="${initParam.resourcesPath}/resources/images/title-logo.png">
<link href="${initParam.resourcesPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/jquery-ui.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">
<link href="${initParam.resourcesPath}/resources/css/footer.css" rel="stylesheet">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="main-body-wrapper">
		<div class="home-container container">
			<div class="container-row row clearfix">
				<jsp:include page="customerViewLeftPanel.jsp"></jsp:include>
				<div id="conv-main-container"
					class="right-panel-messageDashboard float-left">

					<!-- Wrapper for selecting loan type -->
					<div class="loan-type-wrapper calculator-step-wrapper">
						<div class="loan-type-header">Loan Type</div>
						<div class="calculator-step-container">
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Select Loan
									Type</div>
								<div class="loan-type-sel-rc float-left">
									<form>
										<input type="radio" name ="LoanType" checked value="Purchase"/> Purchase 
										<input type="radio" name ="LoanType" value="Refinance" />Refinance
									</form>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Home Buying</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id ="HomeBuying">
										<option selected="selected">Select One</option>
										<option>Signed a purchase Agreement</option>
										<option>Get PreApproved</option>
										<option>What Can I Afford</option>
									</select>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">First Time
									Home Buyer</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id ="firstTimeHomeBuyerId">
										<option selected="selected">Select One</option>
										<option>No</option>
										<option>Yes</option>
									</select>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Preferred Loan
									Type</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id = "preferredLoanType">
										<option selected="selected">Site Chooses</option>
										<option>Fixed Rate</option>
										<option>Adjustable Rate</option>
									</select>
								</div>
							</div>
							
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Estimated Purachse Price</div>
								<div class="loan-type-sel-rc float-left">
									<input type="text" class="calc-input" id = "estimatedPurachsePrice">
								</div>
							</div>
							
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">loan Amount</div>
								<div class="loan-type-sel-rc float-left">
									<input type="text" class="calc-input" id="loanAmount">
								</div>
							</div>
							
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Cust Credit Score</div>
								<div class="loan-type-sel-rc float-left">
									<input type="text" class="calc-input" id="custCreditScore">
								</div>
							</div>
							
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Occupancy Type</div>
								<div class="loan-type-sel-rc float-left">
									<input type="text" class="calc-input" id="occupancyType">
								</div>
							</div>
							
							
							
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">How soon would
									you like to close</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id="days">
										<option selected="selected">30 days</option>
										<option>25 days</option>
										<option>40 days</option>
										<option>55 days</option>
									</select>
								</div>
							</div>
						</div>
						<div class="calc-btn calc-nxt-btn">Next</div>
					</div>


					<!-- Wrapper to add personal info -->
					<div class="personal-info-wrapper calculator-step-wrapper hide">
						<div class="personal-info-header">Personal Information</div>
						<div class="calculator-step-container">
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">What is your
									current Employment Status?</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id="employmentStatus">
										<option selected="selected">Salaried Employee</option>
										<option>Active Military Duty</option>
										<option>Self Employed</option>
										<option>Retired</option>
										<option>Other/Unemployed</option>
									</select>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">What is your
									current Credit score?</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select">
										<option selected="selected">Select One</option>
										<option>Active Military Duty</option>
										<option>Self Employed</option>
										<option>Retired</option>
										<option>Other/Unemployed</option>
									</select>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Please enter
									your personal contact</div>
								<div class="loan-type-sel-rc float-left clearfix">
									<div class="calc-input-cont float-left">
										<div>First Name</div>
										<input type="text" class="calc-input" id ="firstName">
									</div>
									<div class="calc-input-cont float-left">
										<div>Last Name</div>
										<input type="text" class="calc-input" id ="lastName">
									</div>
									<div class="calc-input-cont float-left">
										<div>Email</div>
										<input type="text" class="calc-input" id = "email">
									</div>
									<div class="clearfix">
										<div class="calc-input-cont float-left">
											<div>Preferred Phone</div>
											<input type="text" class="calc-input" id ="preferredPhone">
											 <select
												class="calc-select">
												<option selected="selected">Home</option>
												<option>Cell</option>
												<option>Work</option>
												<option>Other</option>
											</select>
										</div>
										<div class="calc-input-cont float-left">
											<div>Alternate Phone</div>
											<input type="text" class="calc-input" id="alternatePhone"> <select
												class="calc-select">
												<option>Home</option>
												<option selected="selected">Cell</option>
												<option>Work</option>
												<option>Other</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">How did you
									hear about us?</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id="aboutus">
										<option selected="selected">Select One</option>
										<option>Advanced Medical Management</option>
										<option>Buckingham Asset Management</option>
										<option>Craig Wright</option>
										<option>Family</option>
										<option>Friend</option>
										<option>Heroes Mortgage</option>
										<option>Ideal Living</option>
										<option>Internet</option>
										<option>Social Media</option>
									</select>
								</div>
							</div>
						</div>
						<div class="calc-btn calc-nxt-btn">Next</div>
					</div>

					<!-- Wrapper for property and loan information -->
					<div class="poperty-loan-wrapper calculator-step-wrapper hide">
						<div class="poperty-loan-header">Property and Loan
							Information</div>
						<div class="calculator-step-container">
							<!-- <div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">What is the
									loan amount you are looking for?</div>
								<div class="loan-type-sel-rc float-left">
									<input type="text" class="calc-input">
								</div>
							</div> -->
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Do you prefer
									to include Taxes and Insurance as part of your loan payment?</div>
								<div class="loan-type-sel-rc float-left">
									<input type="radio" checked name="preferTaxes" /> Yes 
									<input type="radio" name="preferTaxes"/>No
								</div>
							</div>
							<!-- <div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">What is the
									loan amount you are looking for?</div>
								<div class="loan-type-sel-rc float-left">
									<input type="text" class="calc-input">
								</div>
							</div> -->
							<!-- <div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Occupancy
									type?</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select">
										<option selected="selected">Primary Residence</option>
										<option>Investment Property</option>
										<option>Second/Vacation Home</option>
									</select>
								</div>
							</div> -->
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">What type of
									property is this?</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id="typeProperty">
										<option selected="selected">Single Family</option>
										<option>Condo</option>
										<option>Coop</option>
										<option>Manufactured/Double-wide</option>
										<option>Manufactured/Single-wide</option>
										<option>Modular</option>
										<option>PUD</option>
										<option>Townhouse</option>
									</select>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Number of
									units?</div>
								<div class="loan-type-sel-rc float-left">
									<select class="calc-select" id="numOfunits">
										<option selected="selected">1</option>
										<option>2</option>
										<option>3</option>
										<option>4</option>
									</select>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">What is the
									property address?</div>
								<div class="loan-type-sel-rc float-left">
									<div class="prop-addr-edit-row clearfix">
										<div class="prop-addr-edit-cont float-left">
											<div>Address</div>
											<input type="text" class="calc-input" id="address">
										</div>
										<div class="prop-addr-edit-cont float-left">
											<div>Unit#</div>
											<input type="text" class="calc-input" id="unit">
										</div>
										<div class="prop-addr-edit-cont float-left">
											<div>Zip</div> 
											<input type="text" class="calc-input" id="zip">
										</div>
									</div>
									<div class="prop-addr-edit-row">
										<div class="prop-addr-edit-cont float-left">
											<div>City</div>
											<input type="text" class="calc-input" id="city">
										</div>
										<div class="prop-addr-edit-cont float-left">
											<div>State</div>
											<select class="calc-select" id="state">
												<option>CA</option>
											</select>
										</div>
										<div class="prop-addr-edit-cont float-left">
											<div>County</div>
											<select class="calc-select" id="county">
												<option>Calaveras</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Do you have a
									real estate agent?</div>
								<div class="loan-type-sel-rc float-left">
									<form>
										<input type="radio" checked name="agent" /> Yes 
										<input type="radio" name="agent" />No
									</form>
								</div>
							</div>
						</div>
						<div class="calc-btn calc-nxt-btn">Next</div>
					</div>


					<!-- Wrapper for VA information -->
					<div class="va-wrapper calculator-step-wrapper hide">
						<div class="va-header">VA Information</div>
						<div class="calculator-step-container">
							<div class="loan-type-sel-row clearfix">
								<div class="loan-type-sel-label float-left">Are you a
									Veteran?</div>
								<div class="loan-type-sel-rc float-left">
									<form>
										<input type="radio" checked name="veteran" /> Yes
										 <input type="radio" name="veteran" />No
									</form>
								</div>
							</div>
						</div>
						<div class="calc-btn" id ="submitID">Submit</div>
					</div>

					<div class ="hide" id="slider"></div>

                 <div class="teaserresult" id ="teaserresult"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="inlineFooter.jsp"></jsp:include>
	<jsp:include page="footer.jsp"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function() {
			globalBinder();
			$(document).on('click','.calc-nxt-btn',function(){
				$(this).parent().hide();
				$(this).parent().next().show();
			});
			
			$(document).on('click','#submitID',function(){
				$(this).parent().hide();
				$(this).parent().next().show();
			});
			
			var loanDuration = [15,20,35,40];
			var sliderValue ;
			
			$(function() {
			    $( "#slider" ).slider({
			    	//value: 5,
			    	min: loanDuration[0],
			    	max: loanDuration[loanDuration.length-1],
			    	change: function (event, ui){
			    		var value1 = ui.value;
			    		
			    		for (var i in loanDuration) {
			    		    if (loanDuration[i] < value1) {
			    		        sliderValue = loanDuration[++i];
			    		    } 
			    		}
						if(sliderValue!=value1)
			    		$( "#slider" ).slider( "option", "value", sliderValue );
			    	}
			    });				
			  
			  });
			
			
			 $("#submitID").click(function(){      
			       
				 var teaseRate = new Object(); 
				   var  LoanType =  ($("input[name='LoanType']:checked").val());
			       var  HomeBuying =  $( "#HomeBuying option:selected" ).text();
			       var  firstTimeHomeBuyerId =  $( "#firstTimeHomeBuyerId option:selected" ).text();
			       var preferredLoanType = $("#preferredLoanType option:selected" ).text(); 
			       var estimatedPurachsePrice = $("#estimatedPurachsePrice").val();
			       
			       var days = $("#days option:selected" ).text(); 
			       var employmentStatus = $("#employmentStatus option:selected" ).text(); 
			       var loanAmount = $("#loanAmount").val();
			       var custCreditScore = $("#custCreditScore").val();
			       var occupancyType = $("#occupancyType").val();
			       var firstName = $("#firstName").val();
			       var lastName = $("#lastName").val();
			       var email = $("#email").val();
			       var preferredPhone = $("#preferredPhone").val();
			       var aboutus = $("#aboutus option:selected" ).text(); 
			       var alternatePhone = $("#alternatePhone").val();
			       var preferTaxes =  ($("input[name='preferTaxes']:checked").val());
			       
			       var agent = $("input[name='agent']:checked").val();
			       var veteran = $("input[name='veteran']:checked").val();
			       
			       var address = $("#address").val();
			       var unit = $("#unit").val();
			       var zip = $("#zip").val();
			       var city = $("#city").val();

			       var typeProperty = $("#typeProperty option:selected" ).text(); 
			       var numOfunits = $("#numOfunits option:selected" ).text();
			       var state = $("#state option:selected" ).text();
			       var county = $("#county option:selected" ).text();
			       
			       
			       teaseRate.LoanType = LoanType;
			       teaseRate.HomeBuying = HomeBuying;
			       teaseRate.firstTimeHomeBuyerId = firstTimeHomeBuyerId;
			       teaseRate.preferredLoanType = preferredLoanType;
			       teaseRate.estimatedPurachsePrice = estimatedPurachsePrice;
			       teaseRate.days = days;
			       teaseRate.employmentStatus = employmentStatus;
			       teaseRate.loanAmount = loanAmount;
			       teaseRate.custCreditScore = custCreditScore;
			       teaseRate.firstName = firstName;
			       teaseRate.lastName = lastName;
			       teaseRate.email= email;
			       
			       teaseRate.preferredPhone=preferredPhone;
			       teaseRate.aboutus=aboutus;
			       teaseRate.occupancyType = occupancyType;
			       teaseRate.alternatePhone = alternatePhone;
			       teaseRate.preferTaxes =preferTaxes;
			       teaseRate.agent =agent;
			       teaseRate.veteran =veteran;
			       teaseRate.address =address;
			       teaseRate.unit =unit;
			       teaseRate.zip =zip;
			       teaseRate.city =city;
			       teaseRate.typeProperty =typeProperty;
			       teaseRate.numOfunits =numOfunits;
			       teaseRate.state =state;
			       teaseRate.county =county;
			       			       
			        $.ajax({

			    	   url:"rest/userprofile/findteaserate",
			    	   type:"POST",
			    	   cache:false,
			    	   data:{"teaseRate":JSON.stringify(teaseRate)},
			    	   datatype:"application/json",
			    	   success : function(){
			    		   alert("success");
			    	   },
			    	   error :function(){
			    		   alert("error");
			    	   }
			    	   
			       });
			       alert(JSON.stringify(teaseRate));
			    });
			
			 $('#footer-wrapper').show();
		});
	</script>

</body>
</html>