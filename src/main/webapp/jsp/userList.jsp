<%! String pageTitle = "User Maintenance"; %>
<%@ include file="/include/header.jsf" %>

<jsp:useBean id="userForm" scope="session" class="abbott.ai.tcgm.action.form.UserForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>

	<nested:form method="post" name="userForm" type="abbott.ai.tcgm.action.form.UserForm" action="/userMaint.do" scope="session">
		<nested:hidden property="searchObject.userid" />
		<nested:hidden property="cmd" />

		<table width="760" cellspacing="0" >
			<tr>
				<td colspan="8" class="right">
					<logic:equal name="TCGMUser" property="role.accessLevelString" scope="session" value="<%=Role.Administrator.getAccessLevelString()%>">
						<a href="javascript:chgActCmdSubmit(document.userForm,'deleteselected','deleteUser.do');">
							<img src="images/btnDeleteSelected.png" alt="Delete Selected" />
						</a>
					</logic:equal>
				</td>
			</tr>
			<tr class="fltrTblHdng">
				
				<td>User Id</td>
				<td>First Name</td>
				<td>Last Name</td>
				<td>Phone</td>
				<td>UPI</td>
				<td>&nbsp;Role</td>
				<td>&nbsp;Email</td>
				<td>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('userlist','selected','<%=userForm.getUserListSize()%>');" />
				</td>
			</tr>
			
			<nested:hidden property="userListSize" />	
			<nested:notEqual property="userListSize" value="0">
			  <% int rowNumber=0; %>

				<c:forEach var="userBean" items="${userForm.userlist}"  varStatus="userStatus">
		               			
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%= rowNumber %>" id="mntRow">	

						<input type="hidden" name="userList[<c:out value="${userStatus.index}"/>].userinfoid" value="<c:out value="${userBean.userinfoid}" />">
						
						<td class="mntLeft">
							<c:out value="${userBean.userid}" />
						</td>		
						<td class="mntLeft">
							<c:out value="${userBean.firstName}" />
						</td>
						<td class="mntLeft">
							<c:out value="${userBean.lastName}" />
						</td>

						<td class="mntLeft">
							<c:out value="${userBean.phone}" />
						</td>
						
						<td class="mntLeft">
							<c:out value="${userBean.abtNotesId}" />
						</td>

						<td class="mntCenter">
							&nbsp;&nbsp;<c:out value="${userBean.userRole}" />
						</td>

						<td class="mntLeft">
							&nbsp;&nbsp;<c:out value="${userBean.email}" />
						</td>
						<td class="mntCenter">
							<input type="checkbox" name="userList[<c:out value="${userStatus.index}"/>].selected" value="on">
						</td>

					</abbott:row>
		  	<% rowNumber++; %>			
				</c:forEach>	
				
			</nested:notEqual>
		</table>
		<nested:equal property="userListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
	</nested:form>
<%@ include file="/include/footer.jsf" %>