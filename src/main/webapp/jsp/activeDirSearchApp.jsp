<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%! String pageTitle = "App User Search & Selection Screen"; %>
<%@ include file="/include/header.jsf" %>

<head>
    <style>
        /* Exact layout button style matching sample image specs */
        .tcgm-action-btn {
            display: inline-block;
            padding: 6px 36px;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 15px;
            font-weight: bold;
            font-style: italic;
            color: #002266 !important;
            text-decoration: none;
            background: linear-gradient(to bottom, #ffea6c 0%, #ffbf1c 100%);
            border: 1px solid #cca11f;
            border-radius: 6px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.25);
            cursor: pointer;
            text-shadow: 0 1px 0 rgba(255,255,255,0.3);
            transition: all 0.1s ease-in-out;
        }
        .tcgm-action-btn:hover {
            background: linear-gradient(to bottom, #ffed85 0%, #ffc62d 100%);
            border-color: #b88f14;
            box-shadow: 0 2px 6px rgba(0,0,0,0.3);
        }
        .tcgm-action-btn:active {
            background: #ffbf1c;
            box-shadow: inset 0 2px 3px rgba(0,0,0,0.25);
        }

        /* Clean fixed-width layout grid to contain input elements cleanly */
        .tcgm-layout-grid {
            width: 400px;
            margin: 40px auto 20px auto;
            border-collapse: collapse;
        }
        .tcgm-label-column {
            width: 140px;
            padding: 10px 0;
            text-align: left;
            vertical-align: middle;
        }
        .tcgm-field-column {
            width: 260px;
            padding: 10px 0;
            text-align: left;
            vertical-align: middle;
        }
        .tcgm-label-text {
            font-family: Arial, sans-serif;
            font-size: 13px;
            font-weight: bold;
            color: #333333;
        }
        .tcgm-input-box {
            width: 180px !important;
            border: 1px solid #cccccc; 
            padding: 5px; 
            border-radius: 3px;
            font-family: Arial, sans-serif;
            font-size: 13px;
            box-sizing: border-box;
        }
        .tcgm-button-row {
            text-align: center;
            padding-top: 25px;
        }
        .tcgm-results-table {
            width: 100%;
            max-width: 950px;
            margin: 15px auto;
            border-collapse: collapse;
            font-family: Arial, sans-serif;
            font-size: 12px;
        }
        .tcgm-results-table td {
            padding: 8px;
            border: 1px solid #dcdcdc;
            text-align: left;
        }
    </style>
</head>

<body style="margin: 0; padding: 0;" onload="javascript:document.getElementById('usIdTextField').focus();">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	
<s:form id="activeDirSearchForm" name="activeDirSearchForm" namespace="/" action="ActiveDirSearch" method="post" theme="simple">
  <s:hidden name="cmd" id="cmd" />
  <s:hidden name="cmd2" id="cmd2" />
  
  <table class="tcgm-layout-grid" border="0">
      <tr>
          <td class="tcgm-label-column">
              <label class="tcgm-label-text">UserId</label>
          </td>
          <td class="tcgm-field-column">
              <s:textfield name="usId" id="usIdTextField" cssClass="tcgm-input-box" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
          </td>
      </tr>
      <tr>
          <td class="tcgm-label-column">
              <label class="tcgm-label-text">Last Name</label>
          </td>
          <td class="tcgm-field-column">
              <s:textfield name="lastName" id="lastNameTextField" cssClass="tcgm-input-box" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
          </td>
      </tr>
      <tr>
          <td class="tcgm-label-column">
              <label class="tcgm-label-text">First Name</label>
          </td>
          <td class="tcgm-field-column">
              <s:textfield name="firstName" id="firstNameTextField" cssClass="tcgm-input-box" onkeydown="if(event.keyCode == 13){document.getElementById('searchButton').click();}" />
          </td>
      </tr>
      <tr>
          <td colspan="2" class="tcgm-button-row">
              <button type="button" name="searchButton" id="searchButton" class="tcgm-action-btn" onClick="javascript:chgActCmdSubmit('AppGet');">Get Users</button>
          </td>
      </tr>
  </table>
  <br>

  <c:if test="${not empty activeDirUserList}">
   		<c:if test="${activeDirUserList.size() > 10}">
   			  <table style="border-spacing: 10px 0; margin-left: auto; margin-right: auto;">
		         <tr>
		            <td><button type="button" name="selectUserButtonTop" id="selectUserButtonTop" class="tcgm-action-btn" onClick="javascript:checkSelect();">Select User</button></td>
		         </tr>
		      </table>
   		</c:if>
   		
      <table class="tcgm-results-table">
        <tr class="mntTblHdng" bgcolor="#99CCFF">
          <th width="40" style="padding: 8px;">&nbsp;</th>
          <th align="left" style="padding: 8px;">User ID</th>
          <th align="left" style="padding: 8px;">First Name</th>
          <th align="left" style="padding: 8px;">Last Name</th>
          <th align="left" style="padding: 8px;">UPI</th>
          <th align="left" style="padding: 8px;">Division</th>
          <th align="left" style="padding: 8px;">Type</th>
          <th align="left" style="padding: 8px;">Email</th>
        </tr>
			
        <c:forEach items="${activeDirUserList}" var="activeBean" varStatus="activeStatus">  
          <tr bgcolor="${activeStatus.index % 2 == 0 ? '#ffffff' : '#f9f9f9'}">
            <td align="center">
              <input type="radio" name="blnSelected" value="<c:out value='${activeBean.userId}'/>" onclick="document.getElementById('selectUserButton').focus();"/>
            </td>
            <td>
              <c:out value="${activeBean.userId}"/>
            </td>
            <td>
              <c:out value="${activeBean.firstName}"/>
            </td>
            <td>
              <c:out value="${activeBean.lastName}"/>
            </td>
            <td>
              <c:out value="${activeBean.abtNotesId}"/>
            </td>
            <td>
              <c:out value="${activeBean.division}"/>
            </td>
            <td>
              <c:out value="${activeBean.employeeType}"/>
            </td>
            <td>
              <c:out value="${activeBean.email}"/>
            </td>
          </tr>
        </c:forEach>
      </table>     
       
      <table style="border-spacing: 10px 0; margin-left: auto; margin-right: auto;">
         <tr>
            <td><button type="button" name="selectUserButton" id="selectUserButton" class="tcgm-action-btn" onClick="javascript:checkSelect();">Select User</button></td>
         </tr>
      </table>
  </c:if> 
	
  <br><br>
  <hr>
  <font face="Arial" size="2" color="blue">Existing TCGM Application Users : </font>
  <br>
	
  <table width="760" cellspacing="0" cellpadding="5">
			<tr class="fltrTblHdng">
				<td>User Id</td>
				<td>First Name</td>
				<td>Last Name</td>
				<td>Phone</td>
				<td>UPI</td>
				<td>&nbsp;Role</td>
				<td>&nbsp;Email</td>
			</tr>
	
      <c:choose>
          <c:when test="${not empty userlist}">
              <c:forEach var="userBean" items="${userlist}" varStatus="userStatus">
                  <tr class="${userStatus.index % 2 == 0 ? 'evenRow' : 'oddRow'}" id="mntRow">
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
                  </tr>
              </c:forEach>	
          </c:when>
          <c:otherwise>
              <tr>
                  <td colspan="7">
                      <%@ include file="/include/recordsNotFound.jsf" %>
                  </td>
              </tr>
          </c:otherwise>
      </c:choose>
  </table>
</s:form>

<script language="JavaScript1.2" type="text/javascript">
    document.getElementById('usIdTextField').focus();
</script>

<script type="text/javascript" language="JAVASCRIPT">
function chgActCmdSubmit(cmdValue) {
    document.getElementById('cmd').value = cmdValue;
    var form = document.getElementById('activeDirSearchForm');
    form.action = "ActiveDirSearch.action";
    form.submit();
}

function checkSelect(){
    var radioObj = document.getElementsByName('blnSelected');
    var flag = false;
    var objvalue;
    
    if (radioObj != null && radioObj.length > 0) {
        for (var i = 0; i < radioObj.length; i++) {
            if (radioObj[i].checked) {
                flag = true;
                objvalue = radioObj[i].value;
                break;
            }
        }
        if (flag) {
            chgActCmdSubmit('appselect');
        } else {
            alert('Please Select a User');
        }
    } else {
        alert('Please Select a User');
    }
}

function callCancel(){
    document.getElementById('cmd').value = 'maint_create';
    var form = document.getElementById('activeDirSearchForm');
    form.action = "rptUserMaint.action";
    form.submit();
}
</script>

<%@ include file="/include/footer.jsf" %>