<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="left-panel-wrapper float-left">
	<div class="left-panel">
		<div id="lp-customer-profile" class="lp-pic-wrapper lp-item clearfix" onclick="showCustomerProfilePage();">
			
			
     		<div id="myProfilePicture"></div>
      				
			<div class="lp-pic-txt float-left">
				<div class="lp-txt1" id ="profileNameId">${userVO.firstName}</div>
				<div class="lp-txt2" id="loanType"></div>
				<!-- NEXNF-711 -->
				<%-- <div class="lp-txt3" id= "profilePhoneNumId">${userVO.phoneNumber}</div> --%>
			</div>
		</div>
		<div id="lp-talk-wrapper" class="lp-talk-wrapper lp-item clearfix" onclick="changeLeftPanel(1);">
			<div class="lp-talk-txt">talk to your newfi team</div>
			<div class="lp-talk-pics clearfix">
			<div class="lp-talk-pic lp-talk-pic1"></div>
			<c:forEach begin="0" end="3" var="image" items="${loanTeamImage}" varStatus="count">
				<c:choose>
					<c:when test="${not empty image }">
						<div class="lp-talk-pic lp-talk-pic2" style="background-image:url(${image})"></div>
					</c:when>
					<c:otherwise>
						<div class="lp-initial-pic-small">${initialsList[(count.count)-1]}</div>						
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			</div>
		</div>
		<!-- <div id="lp-loan-wrapper" class="lp-loan-wrapper lp-item clearfix" onclick="changeLeftPanel(2);"> -->
		<div id="lp-loan-wrapper" class="lp-loan-wrapper lp-item clearfix">
			<div class="loan-txt1" onclick="changeLeftPanel(2);">Work on my loan</div>
		
			<!--NEXNF-636  -->
			<!-- <div class="loan-txt1">Work on your loan</div> -->
			<!-- <div class="loan-txt clearfix">
				<div class="float-left loan-pic"></div>
				<div class="float-left loan-txt2">
					Process Completed<br />
					<span class="txt-light">Lorem ipsum</span>
				</div>
			</div> -->
			 <div class="work-on-loan-active">
				Input, Shop, Upload and<br />Track your Loan Progress.
			</div>
			<div class="work-on-loan-inactive clearfix">
				<!--NEXNF-636  -->
				<!-- <div class="work-on-loan-item-cont lp-details-icn">Details</div> -->
				<div class="work-on-loan-item-cont lp-details-icn" onclick="window.location.href='#myLoan/my-application'">App</div>
				<div class="work-on-loan-item-cont lp-rates-icn" onclick="window.location.href='#myLoan/lock-my-rate'">Rates</div>
				<div class="work-on-loan-item-cont lp-upload-icn" onclick="window.location.href='#myLoan/upload-my-needs'">Upload</div>
				<div class="work-on-loan-item-cont lp-progess-icn" onclick="window.location.href='#myLoan/my-loan-progress'">Progress</div>
			</div>
		</div>
		<div id="lp-alert-wrapper" class="lp-alert-wrapper lp-item clearfix">
			<div class="lp-alert-header" id="alertHeder">important alerts</div>
			<div id="notificationParentContainer">
				
			</div>
		</div>
	</div>
</div>