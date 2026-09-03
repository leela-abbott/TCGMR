<%! String pageTitle = "BPC Revision Maintenance"; %>
<a name="FilterView"></a>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="bpcRevTranForm" scope="session" class="abbott.ai.tcgm.action.form.BpcRevTranForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="bpcRevTranForm" type="abbott.ai.tcgm.action.form.BpcRevTranForm" action="/bpcRevTranMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<%//Begin code for filter row%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rev<br>Type</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">BP<br>Cur<br>Code</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>
				<td rowspan="2">Pub<br>Flag</td>
				<td rowspan="2">User<br>Id</td>								
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Code</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<%//Sridevi.K code modified to fix to toggle between the order of the row %>
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" />
			<% String submitFilter = "submitFilter(document.bpcRevTranForm,'filter', event);"; %>
			<nested:nest property="searchObject">
				<nested:hidden property="bpcRev.modelId" />
				<nested:hidden property="bpcRev.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=bpcRevTranForm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>"/>
					</td>
					<td>
						<nested:text property="revType" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=bpcRevTranForm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>"/>
					</td>
					<nested:nest property="bpcRev">
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
							onchange="<%=bpcRevTranForm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
							onblur="checkPadLeft(this,'0',4);" />
						</td>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
							onchange="<%=bpcRevTranForm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
							onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=bpcRevTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>"/>
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=bpcRevTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(6)%>"/>
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpcRevTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(3)%>"/>
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpcRevTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(3)%>"/>
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=bpcRevTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"/>
							</td>
						</nested:nest>
						<td colspan="1">
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
								onchange="<%=bpcRevTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onblur="alertLength(this,10);" 
								onkeyup="<%=bpcRevTranForm.getAutoTab(15)%>"/>
						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="<%=bpcRevTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(5)%>"/>
						</td>
						<td>
							<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="<%=bpcRevTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(5)%>"/>
						</td>
						<td>
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="<%=bpcRevTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(2)%>"/>
						</td>
					</nested:nest>
					<td>
						<nested:text property="publishFlag" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=bpcRevTranForm.getFltrChng()%>" 
							onkeydown = "<%=submitFilter%>" />
					</td>
					<td>
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
							<%=TCGMUser.getUserid()%>
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. Start
							-->
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<html:select property="userSelected" styleClass="commandOption" onchange="<%=bpcRevTranForm.getFltrChng()%>" >
		          <html:option value="ALL">ALL</html:option>
       				<html:options name="TCGMUser" property="userlist" /></html:select> 
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the option to use the maintenance records of any user. End
							-->
					</td>					
				</tr>
			</nested:nest>
			<tr>
				<td colspan="18" class="bgWhiteRight">
					<a href="javascript:changeCmdAndSubmit(document.bpcRevTranForm,'filter');" >
						<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.bpcRevTranForm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>						
					<a href="javascript:changeCmdAndSubmit(document.bpcRevTranForm,'clearfilter');" >
						<img src="images/btnClear.png" alt="Clear Filter"/></a>
				</td>
			</tr>
		</table>
		<hr />

<%//Begin row for adding new record%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rev<br>Type</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">Bp<br>Cur<br>Code</td>
				<td rowspan="2">Beg<br>Period</td>
				<td rowspan="2">End<br>Period</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Code</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" value="ASC" />
			<nested:nest property="addNew">
				<nested:hidden property="bpcRev.modelId" />
				<nested:hidden property="bpcRev.datasetTableId" />
				<% String submitAdd = "submitAdd(document.bpcRevTranForm,'add','bpcRevTranSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
						submitAdd = "submitAdd(document.bpcRevTranForm,'add','bpcRevTranSave.do', event);";
					}else{
						submitAdd = "";
					}
				%>
				<tr class="oddRowCenter">
					<td>
						<%=bpcRevTranForm.dspAddNewMsg()%>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
							onkeydown = "<%=submitAdd%>"
							onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>" />
					</td>
					<nested:nest property="bpcRev">
						<td>
							<nested:text property="revType" maxlength="1" styleClass="fltrWidth1"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>" />
						</td>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(6)%>"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(3)%>"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitAdd%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onblur="alertLength(this,10);" 
								onkeyup="<%=bpcRevTranForm.getAutoTab(15)%>" />
						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(5)%>" />
						</td>
						<td>
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(2)%>"
								onblur="checkPadLeft(this,'0',2);" />
						</td>
						<td>
							<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitAdd%>"
								onkeyup="<%=bpcRevTranForm.getAutoTab(2)%>"
								onblur="checkPadLeft(this,'0',2);" />
						</td>
					</nested:nest>
				</tr>
				<tr>
				<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
					<td class="bgWhiteRight" colspan="18">
						<a href="<%=bpcRevTranForm.getAddBtnHref()%>" >
							<img src="images/btnAdd.png" alt="Add" /></a>
						<a href="<%=bpcRevTranForm.getMassBtnHref()%>" >
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="<%=bpcRevTranForm.getClrBtnHref()%>" >
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
					<%}%>
				</tr>
			</nested:nest>
		</table>

		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/bpcRevTranPaging.jsf" %></div>

<%// Start labels for bottom part of page. (subf) %>
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_ACD)%>" >
						Act<br>Code<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_ACD)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_REV_TYPE)%>" >
						Rev<br>Type<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_REV_TYPE)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_RPT_AFF)%>" >
						Rpt<br>Aff<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_RPT_AFF)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_SUP_AFF)%>" >
						Supp<br>Aff<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_SUP_AFF)%>
					</a>
				</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">
					Bill<br>Price<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_BP_CUR_CD)%>" >
						BP Cur<br>Code<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_BP_CUR_CD)%>
					</a>
				</td>
				<td rowspan="2">
					Beg<br>Period<br>
				</td>
				<td rowspan="2">
					End<br>Period<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_PUBLISH_FLAG)%>" >
						Pub<br>Flag<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_PUBLISH_FLAG)%>
					</a>
				</td>
				<td rowspan="2" valign="middle">
				<%// Sridevi.K code modified to fix to toggle between the select and deselect between the rows%>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('bpcRevTranListItem','bpcRev.selected','<%=bpcRevTranForm.getBpcRevTranListSize()%>');" />
				<% //Sridevi.K end..%>
				</td>
			</tr>

			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_SUP_INV_CD)%>" >
						Inv Cd<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_SUP_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_SUP_LIST)%>" >
						List<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_SUP_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_SUP_LABEL)%>" >
						Label<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_SUP_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_SUP_SIZE)%>" >
						Size<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_SUP_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcRevTranForm.getSrtHref(DBConst.COL_SUP_PACK)%>" >
						Pack<br>
						<%=bpcRevTranForm.dspSort(DBConst.COL_SUP_PACK)%>
					</a>
				</td>

			</tr>
<%// End - Subf Lables %>

			<nested:hidden property="bpcRevTranListSize" />
			<nested:notEqual property="bpcRevTranListSize" value="0">

			<%//Sridevi.K code modified to replace the nested iterate tag with the JSTL tags%>
			<% int rowNumber = 0; %>
			
			<%// used the JSTL c:forEach tag to loop through bpcsList %>
			<c:forEach items="${sessionScope.bpcRevTranForm.bpcRevTranList}"
			                		  var="bpcRevTranBean"
			                	varStatus="bpcRevTranStatus">
			                	
			        <% //define the common part of the property tag of html in another string %>
		        	<% String bpcRevTranListItemArray = "bpcRevTranListItem[" + rowNumber +"]."; %> 
			                					
					<% //declare a String to notify when there is a change %>	
					<% String onChangeCall = "makeEditDirty('" + "bpcRevTranListItem[" + rowNumber++ + "].bpcRev.selected" + "');"; %>
															
					<% //tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>
					
					<%//Sridevi.K rowNumber attribute is added to Abbott custom tag to make it work without nested iterate tag%>
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
					<% //Sridevi.K end of custom tag fix %>
					
						<td class="mntCenter">
						<% //To print the line numbers %>
							<c:out value="${sessionScope.bpcRevTranForm.pagingFilter.startRecord + bpcRevTranStatus.index}"/>
							
							<% //Using 'c:if' to check if the bpcsBean msg is not equal to "  " %>
							<c:if test="${bpcRevTranBean.bpcRev.msg ne ''}" >
								<a class="error"
									href="#"
									id="anchor<c:out value="${bpcRevTranStatus.index}"/>"
									name="anchor<c:out value="${bpcRevTranStatus.index}"/>"
									onclick="return false;"
									onmouseover="showMsgPopup('anchor<c:out value="${bpcRevTranStatus.index}"/>', '<c:out value="${bpcRevTranBean.bpcRev.msg}"/>');"
									onmouseout='hideMsgPopup();' >
									<img src="images/exclamation.png" />
								</a>																	
							</c:if>	
							
							<% //tmpProperty is initialized here as per the column actionCode %>
							<% tmpProperty = bpcRevTranListItemArray + "actionCode" ; %>
							<html:text property="<%=tmpProperty%>" readonly="true" maxlength="1" styleClass="mntWidth1"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />							
						</td>
						<% // <nested:nest property="bpcRev"> %>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.revType %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.revType" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
									       onchange="<%=onChangeCall%>"
									       onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.rptAff %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.rptAff" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
									       onchange="<%=onChangeCall%>"
									       onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
									       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.supAff %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.supAff" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
									       onchange="<%=onChangeCall%>"
									       onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
									       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
							</td>
							<% // Start the nesting for supProduct %>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.supProduct.invCode %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.supProduct.invCode" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1"
									       onchange="<%=onChangeCall%>"
									       onkeyup="<%=bpcRevTranForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.supProduct.list %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.supProduct.list" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="fltrWidth6"
										onchange="<%=onChangeCall%>"
										onkeyup="<%=bpcRevTranForm.getAutoTab(6)%>"
										onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.supProduct.label %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.supProduct.label" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
										   onchange="<%=onChangeCall%>"
										   onkeyup="<%=bpcRevTranForm.getAutoTab(3)%>"
										   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.supProduct.size %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.supProduct.size" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="fltrWidth3"
									       onchange="<%=onChangeCall%>"
									       onkeyup="<%=bpcRevTranForm.getAutoTab(3)%>"
										   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.supProduct.pack %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.supProduct.pack" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="fltrWidth4"
										   onchange="<%=onChangeCall%>"
										   onkeyup="<%=bpcRevTranForm.getAutoTab(4)%>"
										   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
							</td>							
							<%// end of supProduct nesting %>

						    <td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.billPrice %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.billPrice" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth15"
									   	   onchange="<%=onChangeCall%>"
									   	   onblur="alertLength(this,10);" 
									       onkeyup="<%=bpcRevTranForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.bpCurCode %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.bpCurCode" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="fltrWidth5"
									onchange="<%=onChangeCall%>"
									onkeyup="<%=bpcRevTranForm.getAutoTab(5)%>" onkeydown="restrSpace(event);" />
							</td>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.begPeriod %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.begPeriod" ; %>
								<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
										   onchange="<%=onChangeCall%>"
										   onkeyup="<%=bpcRevTranForm.getAutoTab(2)%>"
										   onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcRev.endPeriod %>
							<% tmpProperty = bpcRevTranListItemArray + "bpcRev.endPeriod" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
								       onchange="<%=onChangeCall%>"
								      onkeyup="<%=bpcRevTranForm.getAutoTab(2)%>"
								      onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
						</td>						
						<%// end of bpcRev nesting %>
						
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column publishFlag %>
							<% tmpProperty = bpcRevTranListItemArray + "publishFlag" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1" disabled="true"/>
						</td>
						<%// beganing of bpcRev nesting %>
							<td class="mntCenter">
								<% //tmpProperty is initialized here as per the column bpcRev.selected %>
								<% tmpProperty = bpcRevTranListItemArray + "bpcRev.selected" ; %>
								<html:checkbox property="<%=tmpProperty%>" />
							</td>
						<%// End nesting of bpcRev  %>
					</abbott:row>
                   <%// Start next subf row for additional fields %>


         <%// Start the 1 - 13 billing and cost price period fields %>
		<% // Billing Price Periods %>
		<%// Sridevi.K abbott custom tag is modified to work fine without nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
			<% //Sridevi.K End of abbott custom tag %>
					<% //<nested:nest property="bpcRev"> %>
							<td colspan="1">&nbsp;</td>
							<td class="mntCenter">
								BP<br>Period
							</td>
							<td colspan="16" class="mntCenter">
								<table width="100%" cellspacing="0">
									<tr>
										<% // Using c:forEach for looping the bpPeriodValues starting form 0 to 5 %>								     
										<c:forEach items="${bpcRevTranBean.bpcRev.bpPeriodValues}" 
												begin="0"
												end="5"
												step="1"
												var="bpPeriod1"
			                					varStatus="bpPeriodStatus1">
												<td class="mntRight" width="16%">
													<c:out value="${bpPeriod1.period}"/>
												</td>
										</c:forEach>										
									</tr>
									<tr>
										<% // Using c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
										<c:forEach items="${bpcRevTranBean.bpcRev.bpPeriodValues}"
											begin="6"
											end="11"
											step="1"
											var="bpPeriod2"
			                				varStatus="bpPeriodStatus2"> 
											<td class="mntRight">
												<c:out value="${bpPeriod2.period}"/>
											</td>
										</c:forEach>										
									</tr>
								</table>
							</td>
					</abbott:row>
				<% // Cost Price Periods %>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
                			<td colspan="1">&nbsp;</td>
							<td class="mntCenter">
								Cost<br>Period
							</td>
							<td colspan="16" class="mntCenter">
								<table width="100%" cellspacing="0">
									<tr>
										<% // Useing c:forEach for looping the costPeriodValues starting form 0 to 5 %>
										<c:forEach items="${bpcRevTranBean.bpcRev.costPeriodValues}"
											begin="0"
											end="5"
											step="1"
									 		var="costPeriodBean1"
			                				varStatus="costPeriodStatus1"> 
											<td class="mntRight" width="16%">
												<c:out value="${costPeriodBean1.period}"/>
											</td>
										</c:forEach>										
									</tr>
									<tr>
										<% // Useing c:forEach for looping the costPeriodValues starting form 6 to 11 %>
										<c:forEach items="${bpcRevTranBean.bpcRev.costPeriodValues}" 									
													begin="6"
													end="11"
													step="1"
													var="costPeriod2"
													varStatus="costPeriodStatus2"> 
													<td class="mntRight">
														<c:out value="${costPeriod2.period}"/>
													</td>
										</c:forEach>											
									</tr>
								</table>
							</td>
					</abbott:row>
				<%// End the 1 - 13 bill and cost period fields %>
				</c:forEach>
			<%// End - Subf Data Portion for Original flds	%>
			<div name="navigation" id="navigation" class="hidden">
				<%@ include file="/include/bpcRevTranBtmPaging.jsf" %>
			</div>
			</nested:notEqual>
		</table>
		<nested:equal property="bpcRevTranListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
	showObj('navigation');
	setFocusReposition('<%=bpcRevTranForm.getFocusField()%>');
	/**
	*
	*/
	<%//Sridevi.K Script added to alert the user if he clicks rowSelected without selecting any row%>
	function checkCopy(selectedModel,form,cmd,action) {

		if(selectedModel == 'none')	{
	
			alert('You must select a model to copy');
		} 
		else {
	
			var rowSelected=false;
			if ( cmd == 'copyall'){		
			
				rowSelected=true;
			}
			else {
				for(i = 0; i < bpcRevTranForm.bpcRevTranListSize.value; i++) {
				
					var element = "bpcRevTranListItem[" + i + "].bpcRev.selected";
				
					if(!rowSelected){
					
						for(j = 0; j < bpcRevTranForm.elements.length; j++) {
						
							if(bpcRevTranForm.elements[j].name == element){

								if(bpcRevTranForm.elements[j].checked == true ) {
								
									rowSelected=true;
								}
							}
						}
					}				
				}	
			}
			if( rowSelected ) {	
				chgActCmdSubmit(form,cmd,action);
			}
			else {	
				alert( 'You must select at least one row to copy' );
			}	
		}
	}	
	<%//Sridevi.K end of code to alert the user if he does not selects any row to copy%>
	
    <%//Sridevi.K added script to alert the user to select atleast one row to publish%>	
    /**
    * Prompt the user to select atleast one records
    */  	
	function checkPublish(form,cmd,action) {

			var rowSelected=false;
			if ( cmd == 'publishall' || cmd == 'unpublishall'){				
				rowSelected=true;
			}
			else {
				for(i = 0; i < bpcRevTranForm.bpcRevTranListSize.value; i++) {			
				
					var element = "bpcRevTranListItem[" + i + "].bpcRev.selected";				
					if(!rowSelected){				
						for(j = 0; j < bpcRevTranForm.elements.length; j++) {
							if(bpcRevTranForm.elements[j].name == element){
								if(bpcRevTranForm.elements[j].checked == true ) {							
									rowSelected=true;
								}
							}
						}
					}				
				}	
			}
			if( rowSelected ) {	
				chgActCmdSubmit(form,cmd,action);
			}
			else {	
				alert( 'You must select at least one row to publish' );
			}	
	}		
	<%//Sridevi.K end of Script to alert the user if he clicks publish selected without selecting any rows%>
	
	<%//Sridevi.K added script to alert the user if he does not select any row to delete and also to alert the conformation of delete%>	
	/**
 	* Prompt the user to select atleast one record and confirm the deletion of records
 	*/
	function confirmDelete(form,cmd,action)
	{

		var rowSelected=false;
		if ( cmd == 'deleteall'){		
			rowSelected=true;
		}
		else {
			for(i = 0; i < bpcRevTranForm.bpcRevTranListSize.value; i++) {
				var element = "bpcRevTranListItem[" + i + "].bpcRev.selected";				
				if(!rowSelected){
					for(j = 0; j < bpcRevTranForm.elements.length; j++) {
						if(bpcRevTranForm.elements[j].name == element){
							if(bpcRevTranForm.elements[j].checked == true ) {
								rowSelected=true;
							}
						}
					}
				}			
			}	
		}
		if( rowSelected ) {	
	
			if (confirm('Records will be permanently deleted?'))
			{
				chgActCmdSubmit(form,cmd,action)
			}		
		}
		else {	
			alert( 'You must select at least one row to delete' );
		}	
	}
function restrSpace(event){

     if(event.keyCode == 32){
		event.returnValue = false;
		return false;
		}
	else{
return true;
	}
        
}
  <%//Sridevi.K end of script%>	
</script>
<%@ include file="/include/footer.jsf" %>