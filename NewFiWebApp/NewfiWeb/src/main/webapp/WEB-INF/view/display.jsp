<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
    <title>newfi</title>
    <link rel="shortcut icon" type="image/x-icon" href="${initParam.resourcesPath}/resources/images/newfiHome.ico">
    <link href="${initParam.resourcesPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${initParam.resourcesPath}/resources/css/styles.css" rel="stylesheet">
    <link href="${initParam.resourcesPath}/resources/css/style-resp.css" rel="stylesheet">
	<link href="${initParam.resourcesPath}/resources/css/jquery-ui.css" rel="stylesheet">
</head>
<body>
    
    
    <div class="body-wrapper">
        
        <div class="header-wrapper">
            <div class="header-container container">
                <div class="header-row row clearfix">
                    <div class="header-logo float-left"></div>
                    <div class="header-btns-wrapper float-right clearfix">
                        <div class="float-left btn-alarm hdr-btn-item"></div>
                        <div class="float-left btn-settings hdr-btn-item"></div>
                        <div class="float-left btn-logout hdr-btn-item"><span class="logout-btn-txt">Logout</span></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="home-container container">
            <div class="container-row row clearfix">
                
                <div class="left-panel-wrapper float-left">
                    <div class="left-panel">
                        <div class="lp-pic-wrapper lp-item clearfix">
                            <div class="lp-pic float-left"></div>
                            <div class="lp-pic-txt float-left">
                                <div class="lp-txt1">Jane Doe</div>
                                <div class="lp-txt2">Home Buyer</div>
                                <div class="lp-txt3">+1 (888) 555-1875</div>
                            </div>
                        </div>
                        <div class="lp-talk-wrapper lp-item clearfix">
                            <div class="lp-talk-txt">talk to your newfi team</div>
                            <div class="lp-talk-pics clearfix">
                                <div class="lp-talk-pic lp-talk-pic1"></div>
                                <div class="lp-talk-pic lp-talk-pic2"></div>
                                <div class="lp-talk-pic lp-talk-pic3"></div>
                                <div class="lp-talk-pic lp-talk-pic4"></div>
                            </div>
                        </div>
                        <div class="lp-loan-wrapper lp-item clearfix">
                            <div class="loan-txt1">Work on your loan</div>
                            <div class="loan-txt clearfix">
                                <div class="float-left loan-pic"></div>
                                <div class="float-left loan-txt2">Process Completed<br/><span class="txt-light">Lorem ipsum</span></div>
                            </div>
                        </div>
                        <div class="lp-alert-wrapper lp-item clearfix">
                            <div class="lp-alert-header">important alerts</div>
                            <div class="lp-alert-item">Salaried-W-2 forms- Pending</div>
                            <div class="lp-alert-item">Salaried-W-2 forms- Pending</div>
                        </div>
                    </div>
                </div>
                
                <div class="right-panel float-left">
					<div class="lp-t2-wrapper">
						<div class="small-screen-menu-icon lp-t2-item"></div>
                        <div class="lp-t2-item">
                            <div class="lp-t2-img lp-t2-img1"></div>
                            <div class="lp-t2-txt">Getting to know newfi</div>
                        </div>
                        <div class="lp-t2-item">
                            <div class="lp-t2-img lp-t2-img2"></div>
                            <div class="lp-t2-txt">complete your application</div>
                        </div>
                        <div class="lp-t2-item t2-active">
                            <div class="lp-t2-img lp-t2-img3"></div>
                            <div class="lp-t2-txt">lock<br/>your rate</div>
							<div class="arrow-right"></div>
                        </div>
                        <div class="lp-t2-item">
                            <div class="lp-t2-img lp-t2-img4"></div>
                            <div class="lp-t2-txt">upload<br/>needed items</div>
                        </div>
                        <div class="lp-t2-item">
                            <div class="lp-t2-img lp-t2-img5"></div>
                            <div class="lp-t2-txt">loan<br/>progress</div>
                        </div>
                    </div>
					<div class="center-panel float-left">
						<div class="rate-program-wrapper">
							<div class="rate-program-header uppercase">RATES & PROGRAM</div>
							<div class="rate-program-container clearfix">
								<div class="rate-program-container-col1 float-left">
									<div class="cp-rate-header-text">Interest Rate</div>
									<div class="cp-rate-btn">3.375%</div>
								</div>
								<div class="rate-program-container-col2 float-left">
									<div class="rate-slider">
										<div class="slider-text-cont clearfix">
											<div class="slider-text-left float-left">Reduce Rate</div>
											<div class="slider-text-right float-right">Reduce Cost</div>
										</div>
										<div id="rate-slider" class="rate-slider-icon"></div>
									</div>
									<div class="tenure-slider">
										<div class="slider-text-cont clearfix">
											<div class="slider-text-left float-left">Length of loan</div>
											<div class="slider-text-right float-right">30 Years</div>
										</div>
										<div id="tenure-slider" class="tenure-slider-icon"></div>
									</div>
								</div>
								<div class="rate-program-container-col3 float-left">
									<div class="cp-est-header-text">Estimated Closing Cost</div>
									<div class="cp-est-cost-btn">$ 8,185.75</div>
								</div>
								<!--Container for slider in mobile screen -->
								<div class="mobile-slider-container clearfix">
									<div class="rate-program-container-rs float-left">
										<div class="cp-rate-header-text">Interest Rate</div>
										<div class="cp-rate-btn">3.375%</div>
									</div>
									<div class="rate-program-container-ts float-left">
										<div class="cp-est-header-text">Estimated Closing Cost</div>
										<div class="cp-est-cost-btn">$ 8,185.75</div>
									</div>
								</div>
							</div>
						</div>
						<div class="loan-summary-wrapper">
							<div class="loan-summary-header clearfix">
								<div class="loan-summary-header-col1 float-left capitalize">My Loan Summary</div>
								<div class="loan-summary-header-col2 float-left">Rates as of 1/16/2015 8:13:52 AM</div>
							</div>
							<div class="loan-summary-container clearfix">
								<div class="loan-summary-lp float-left">
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Loan Details</div>
										<div class="loan-summary-col-detail apply-btn float-left">Apply</div>
									</div>
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Loan Type</div>
										<div class="loan-summary-col-detail float-left">Refinance - No Cash Out</div>
									</div>
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Loan Program</div>
										<div class="loan-summary-col-detail float-left">30 Years Fixed</div>
									</div>
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Interest Rate</div>
										<div class="loan-summary-col-detail float-left">3.375%</div>
									</div>
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Loan Amount</div>
										<div class="loan-summary-col-detail float-left">$ 373,000.000</div>
									</div>
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">ARP</div>
										<div class="loan-summary-col-detail float-left">3.547%</div>
									</div>
									<div class="loan-summary-last-row clearfix">
										<div class="loan-summary-col-desc float-left">Estimated<br/>Closing Cost</div>
										<div class="loan-summary-col-detail float-left">$8,185.75</div>
									</div>
								</div>
								<div class="loan-summary-rp float-right">
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Monthly Payment</div>
										<div class="loan-summary-col-detail float-left"></div>
									</div>
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Principal Interest</div>
										<div class="loan-summary-col-detail float-left">$ 1,649.20</div>
									</div>
									<div class="loan-summary-row clearfix no-border-bottom">
										<div class="loan-summary-col-desc float-left">Tax</div>
										<div class="loan-summary-col-detail calculate-btn float-left">Calculate</div>
									</div>
									<div class="loan-summary-row clearfix">
										<div class="loan-summary-col-desc float-left">Insurance</div>
										<div class="loan-summary-col-detail calculate-btn float-left">Calculate</div>
									</div>
									<div class="loan-summary-row-text">
										Your tax and insurance payment above will be included with your principal 																			& interest payment
									</div>
									<div class="loan-summary-last-row clearfix">
										<div class="loan-summary-col-desc float-left">Total Est.<br/>Monthly Payment</div>
										<div class="loan-summary-col-detail float-left">$ 1,649.02</div>
									</div>
								</div>
							</div>
							<div class="cp-header-text">
								Rate and APR quoted are based on the information you provided, are not guaranteed, and are subject to change. Actual rate and APR will be available on your Good Faith Estimate after loan amount and income are verified.
							</div>
						</div>
						<div class="closing-cost-wrapper">
							<div class="closing-cost-header uppercase">Based on the information you have provided, below is a summary of your estimated closing costs:</div>
							<div class="cp-header-text">
								Based on the loan you selected your application, credit report and the estimated closing date of 													<span class="bold">02/09/2015</span>,your estimated lender and third party costs are:
							</div>
							<div class="closing-cost-cont-wrapper-top">
								<div class="closing-cost-cont-heading">Total Estimated Losing Cost</div>
								<div class="closing-cost-container">
									<div class="closing-cost-cont-desc-header">Estimated Lender Cost</div>
									<div class="closing-cost-cont-desc-row clearfix">
										<div class="closing-cost-desc float-left">Administration Fee</div>
										<div class="closing-cost-detail float-left">$ 1,495.00</div>
									</div>
									<div class="closing-cost-cont-desc-row closing-cost-cont-desc-row-even clearfix">
										<div class="closing-cost-desc float-left">Loan Points</div>
										<div class="closing-cost-detail float-left">$ 5,128.75</div>
									</div>
									<div class="closing-cost-cont-desc-row no-border-bottom clearfix">
										<div class="closing-cost-desc float-left">Total Estimated Lender Costs</div>
										<div class="closing-cost-detail float-left semi-bold">$ 6,622.75</div>
									</div>
								</div>
								<div class="closing-cost-container">
									<div class="closing-cost-cont-desc-header">Estimated Third Party Cost</div>
									<div class="closing-cost-cont-desc-row clearfix">
										<div class="closing-cost-desc float-left">Appraisal Fee</div>
										<div class="closing-cost-detail float-left">$ 455.00</div>
									</div>
									<div class="closing-cost-cont-desc-row closing-cost-cont-desc-row-even clearfix">
										<div class="closing-cost-desc float-left">Lenders Title Insurance</div>
										<div class="closing-cost-detail float-left">$ 450.00</div>
									</div>
									<div class="closing-cost-cont-desc-row clearfix">
										<div class="closing-cost-desc float-left">Escrow/Closing Fee</div>
										<div class="closing-cost-detail float-left">$ 500.00</div>
									</div>
									<div class="closing-cost-cont-desc-row closing-cost-cont-desc-row-even clearfix">
										<div class="closing-cost-desc float-left">Government Recording</div>
										<div class="closing-cost-detail float-left">$ 107.00</div>
									</div>
									<div class="closing-cost-cont-desc-row no-border-bottom clearfix">
										<div class="closing-cost-desc float-left">Total Estimated Third Party Costs</div>
										<div class="closing-cost-detail float-left semi-bold">$ 1,562.00</div>
									</div>
								</div>
							</div>
							<div class="closing-cost-cont-wrapper-bottom no-border-bottom">
								<div class="closing-cost-cont-heading">Total Estimated Closing Cost</div>
								<div class="closing-cost-container">
									<div class="closing-cost-cont-desc-header">Prepaids</div>
										<div class="closing-cost-cont-desc-row clearfix">
										<div class="closing-cost-desc float-left">
											<div class="semibold">Prepaid Interest</div>
											<div class="subtext">
												This amount is $34.9700 perday for 20 days(if your settlement os 2/9/2015).<br/>*Prepaid interest is 													an estimate and will adjust based on the confirmed final closing date
											</div>
										</div>
										<div class="closing-cost-detail float-left">$ 699.40</div>
									</div>
									<div class="closing-cost-cont-desc-row no-border-bottom clearfix">
										<div class="closing-cost-desc float-left">Total Prepaids</div>
										<div class="closing-cost-detail float-left semi-bold">$ 699.40</div>
									</div>
								</div>
								<div class="closing-cost-container">
									<div class="closing-cost-cont-desc-header">Estimated Reserves Deposited with Lender</div>
									<div class="closing-cost-cont-desc-row clearfix">
										<div class="closing-cost-desc float-left">
											<div class="semibold">Property Taxes - Estimated 2 Month(s)</div>
											<div class="subtext">(Varies based on calendar month of closing)</div>
										</div>
										<div class="closing-cost-detail calculate-btn float-left">Calculate</div>
									</div>
									<div class="closing-cost-cont-desc-row closing-cost-cont-desc-row-even clearfix">
										<div class="closing-cost-desc float-left">
											<div class="semibold">Homeowner's Insurance - Estimated 2 Month(s)</div>
											<div class="subtext">(Provided you have 6 months of remaining coverage)</div>
										</div>
										<div class="closing-cost-detail calculate-btn float-left">Calculate</div>
									</div>
									<div class="closing-cost-cont-desc-row clearfix">
										<div class="closing-cost-desc float-left">Total Estimated Reserves Deposited with Lender</div>
										<div class="closing-cost-detail float-left semi-bold">$ 0.00</div>
									</div>
									<div class="closing-cost-bot-row">
										Note :-Property Taxes for both 1st and 2nd half installments must be paid or will be collected at 														closing
									</div>
								</div>
							</div>
						</div>
					</div>
                </div>
				
            </div>
        </div>
        
    </div>
        
    
    <script src="${initParam.resourcesPath}/resources/js/jquery-2.1.3.min.js"></script>
	<script src="${initParam.resourcesPath}/resources/js/jquery-ui.js"></script>
    <script src="${initParam.resourcesPath}/resources/js/bootstrap.min.js"></script>
    <script src="${initParam.resourcesPath}/resources/js/script.js"></script>
    <script src="${initParam.resourcesPath}/resources/js/message.js"></script>
    <script src="${initParam.resourcesPath}/resources/js/include/historySupport.js"></script>
	<script>
			
		$(document).ready(function(){
			globalBinder();
			adjustCenterPanelWidth();
			$(window).resize(function(){
				adjustCenterPanelWidth();
			});
			
			$('#rate-slider').slider({
				orientation: "horizontal",
      			range: "min",
				max : 100,
				value: 40
			});
			$('#tenure-slider').slider({
				orientation: "horizontal",
      			range: "min",
				max : 30,
				value: 10
			});
			function adjustCenterPanelWidth(){
				if(window.innerWidth <= 1200 && window.innerWidth >= 768){
					var leftPanelWidth = $('.left-panel').width();
					var leftPanelTab2Width = $('.lp-t2-wrapper').width();
					var centerPanelWidth = $(window).width() - (leftPanelWidth + leftPanelTab2Width) - 35;
					$('.center-panel').width(centerPanelWidth);
				}
				else if(window.innerWidth < 768){
					var leftPanelTab2Width = $('.lp-t2-wrapper').width();
					var centerPanelWidth = $(window).width() - (leftPanelTab2Width) - 35;
					$('.center-panel').width(centerPanelWidth);
				}
			}
			$('.small-screen-menu-icon').click(function(e){
				e.stopImmediatePropagation();
				$('.left-panel').toggle();
			});
			$(document).click(function(){
				if($(window).width() <= 768){
					if($('.left-panel').css("display") == "block"){
						$('.left-panel').toggle();
					}
				}
			});
			
		});
	</script>
</body>
</html>