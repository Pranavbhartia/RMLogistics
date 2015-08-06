<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="left-panel-wrapper float-left">
	<div class="left-panel">
	
		<div id="lp-loan-manager-profile" class="lp-pic-wrapper lp-item clearfix"  onclick="showLoanManagerProfilePage();" >
		
				<div id="myProfilePicture"></div>
			<div class="lp-pic-txt float-left">
				<div class="lp-txt1" id ="profileNameId">${userVO.firstName}</div>

				<c:choose>
					<c:when test="${userVO.userRole.roleCd eq 'INTERNAL' }">
					<c:choose>
					<c:when test="${userVO.internalUserDetail.internalUserRoleMasterVO.id eq 2}">
						<div class="lp-txt2">Manager</div>
					</c:when>
					<c:otherwise>
						<div class="lp-txt2">${userVO.internalUserDetail.internalUserRoleMasterVO.roleDescription }</div>
					</c:otherwise>	
					</c:choose>
						<!--NEXNF-810  -->
							<%-- <div class="lp-txt2">${userVO.internalUserDetail.internalUserRoleMasterVO.roleDescription }</div> --%>
					</c:when>

					<c:otherwise>
						<div class="lp-txt2">${userVO.userRole.roleDescription }</div>
					</c:otherwise>
				</c:choose>
				
				<!-- jira-711 -->
				<%-- <div class="lp-txt3" id= "profilePhoneNumId">${userVO.phoneNumber }</div> --%>
			</div>
		</div>
		<!-- jira- 858-->
		<c:choose>
			<c:when test="${userVO.userRole.roleCd eq 'INTERNAL' }">
				<div class="lp-item lp-item-quick-quote" id="lp-quick-quote" onclick="">Quick Quote</div>
			</c:when>
		</c:choose>
		<%-- <div class="lp-item lp-item-agent clearfix" id="lp-work-on-loan" onclick="paintAgentDashboard('workloans');">
			<div class="lp-item-header">work on your loan</div>
			<div class="lp-item-body lp-agent-item1">
				<div class="lp-item-body-row lp-item-body-top-row">
					<span id="lp-new-prospects">${progressVO.newProspects }</span> <span>
						New Prospects</span>
				</div>
				<div class="lp-item-body-row">
					<span id="lp-total-leads">${progressVO.totalLeads }</span> <span>
						Total leads</span>
				</div>
			</div>
		</div> --%>
		<div class="lp-item lp-item-agent lp-item-adj clearfix" id="lp-my-loans" onclick="paintAgentDashboard('myloans');">
			<div class="lp-item-header" id="lp-item-header"></div>
			<!--NEXNF-810  -->
			<%-- <!--  NEXNF-660--->
			<c:choose>
				<c:when test="${userVO.userRole.id eq 2 }">
					<div class="lp-item-header">my pipeline</div>
				</c:when>
				<c:otherwise>
					<div class="lp-item-header">my loans</div>
				</c:otherwise>
			</c:choose> --%>
			<div class="lp-item-body lp-agent-item2">
				<div class="lp-item-body-row lp-item-body-top-row">
				<!--  NEXNF-660--->
					<c:choose>
						<c:when test="${userVO.userRole.id eq 2 }">
							<span id="lp-new-loans">${progressVO.newLoans }</span> 
							<span>Transactions</span>
						</c:when>
						<c:otherwise>
							<span id="lp-new-loans">${progressVO.newLoans }</span> 
							<span>New loans</span>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="lp-item-body-row">
					<span id="lp-in-progress">${progressVO.inProgress }</span> <span>
						In Progress</span>
				</div>
			</div>
		</div>
		<div class="lp-item lp-item-agent clearfix" id="lp-my-archives" onclick="paintAgentDashboard('archivesloans');">
			<div class="lp-item-header" id="lp-item-header-archives"></div>
			<!--NEXNF-810  -->
			<!-- <div class="lp-item-header">my archives</div> -->
			<div class="lp-item-body lp-agent-item3">
			<c:choose>
				<c:when test="${userVO.userRole.id eq 2 }">
					<div class="lp-item-body-row lp-item-body-top-row">
						<span id="lp-past-transaction"></span>  
						<span>Past Transactions</span>
							
					</div>
				</c:when>
				<c:otherwise>
					<div class="lp-item-body-row lp-item-body-top-row">
						<span id="lp-closed">${progressVO.closed }</span> <span>
							Closed</span>
					</div>
					<div class="lp-item-body-row">
						<span id="lp-withdrawn">${progressVO.withdrawn }</span> <span>
							Withdrawn</span>
					</div>
					<div class="lp-item-body-row">
						<span id="lp-declined">${progressVO.declined }</span> <span>
							Declined</span>
					</div>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
		<div class="lp-alert-wrapper lp-item clearfix" id="lp-imp-alerts">
			<div class="lp-alert-header" id="alertHeder">important alerts</div>
			<div id="notificationParentContainer">
			
			</div>
		</div>
	</div>
</div>