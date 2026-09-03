
<%! String pageTitle = "BPC Maintenance"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="bpcsTranForm" scope="session" class="abbott.ai.tcgm.action.form.BpcsTranForm" />
<a name="FilterView"></a><body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="bpcsTranForm" type="abbott.ai.tcgm.action.form.BpcsTranForm" action="/bpcsTranMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<%//Begin code for filter row%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">  
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">BP<br>Cur<br>Code</td>
				<td rowspan="2">Freeze<br>Cost</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>Inv Code</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			
			<% //Sridevi.K Code changed for fixing the toggle between the ascending and descending order of the data %>
			<nested:hidden property="sortObject.sortColumn" />
			<nested:hidden property="sortObject.sortOrder" />			
			<% String submitFilter = "submitFilter(document.bpcsTranForm,'filter', event);"; %>
			<nested:nest property="searchObject">
			<% //Sridevi.K %>
			
				<nested:hidden property="bpcs.modelId" />
				<nested:hidden property="bpcs.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=bpcsTranForm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpcsTranForm.getAutoTab(1)%>"/>
					</td>
					<nested:nest property="bpcs">
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
							onchange="<%=bpcsTranForm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpcsTranForm.getAutoTab(4)%>" 
							onblur="checkPadLeft(this,'0',4);" />
						</td>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
							onchange="<%=bpcsTranForm.getFltrChng()%>"
							onkeydown = "<%=submitFilter%>"
							onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
							onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="<%=bpcsTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcsTranForm.getAutoTab(1)%>"/>
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="<%=bpcsTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcsTranForm.getAutoTab(6)%>"/>
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpcsTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"/>
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="<%=bpcsTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"/>
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="<%=bpcsTranForm.getFltrChng()%>"
									onkeydown = "<%=submitFilter%>"
									onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"/>
							</td>
						</nested:nest>
						<td colspan="1">
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onblur="alertLength(this,10);" 
								onkeyup="<%=bpcsTranForm.getAutoTab(15)%>"/>
						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcsTranForm.getAutoTab(5)%>"/>
						</td>
						<td>
							<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcsTranForm.getAutoTab(1)%>"/>
						</td>

					</nested:nest>
				</tr>
				<tr class="fltrTblHdng">
					<td><br>cost</td>
					<td>Cost<br>Cur<br>Code</td>
					<td>Beg<br>Period</td>
					<td>End<br>Period</td>
					<td>Pub<br>Flag</td>
					<td>User<br>Id</td>
					<td class="bgWhiteRight" colspan="7">&nbsp;</td>
				</tr>
				<nested:nest property="bpcs">
					<tr class="oddRowCenter">
						<td>
							<nested:text property="costPrice" maxlength="15" styleClass="fltrWidth10"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onblur="alertLength(this,10);" 
								onkeyup="<%=bpcsTranForm.getAutoTab(15)%>"/>
						</td>
						<td>
							<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcsTranForm.getAutoTab(5)%>"/>
						</td>
						<td>
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"/>
						</td>
						<td>
							<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="<%=bpcsTranForm.getFltrChng()%>"
								onkeydown = "<%=submitFilter%>"
								onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"/>
						</td>
				</nested:nest>
					<td>
						<nested:text property="publishFlag" maxlength="1" styleClass="fltrWidth1"
							onchange="<%=bpcsTranForm.getFltrChng()%>" 
							onkeydown = "<%=submitFilter%>" />
					</td>
					<td>
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType="<">
							<%=TCGMUser.getUserid()%>
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the oprion to use the maintenance records of any user. Start
							-->
						<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<html:select property="userSelected" styleClass="commandOption" onchange="<%=bpcsTranForm.getFltrChng()%>" >
		          <html:option value="ALL">ALL</html:option>
       				<html:options name="TCGMUser" property="userlist" /></html:select> 
						</abbott:securePage>
							<!--
							*	Added by Uday on 02/04/2006 to provide the user(Analyst)
							* the oprion to use the maintenance records of any user. End
							-->
						<!--<abbott:securePage userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" comparisonType=">=">
							<nested:text property="bpcs.createLog.userName" maxlength="9" styleClass="fltrWidth9"
								onchange="<%=bpcsTranForm.getFltrChng()%>"/>
						</abbott:securePage>-->
					</td>
						<td colspan="7" class="bgWhiteRight">
							<a href="javascript:changeCmdAndSubmit(document.bpcsTranForm,'filter');" >
								<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.bpcsTranForm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>								
							<a href="javascript:changeCmdAndSubmit(document.bpcsTranForm,'clearfilter');" >
								<img src="images/btnClear.png" alt="Clear Filter"/></a>
						</td>
					</tr>
				</nested:nest>
		</table>
		<hr />

<%//Begin row for adding new record%>
		<table width="780" cellspacing="0">
			<tr class="fltrTblHdng">
				<td rowspan="2">Act<br>Code</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">Bill<br>Price</td>
				<td rowspan="2">Bp<br>Cur<br>Code</td>
				<td rowspan="2">Freeze<br>Cost</td>
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
			<% String submitAdd = "submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
						submitAdd = "submitAdd(document.bpcsTranForm,'add','bpcsTranSave.do', event);";
					}else{
						submitAdd = "";
					}
			%>
			<nested:nest property="addNew">
				<nested:hidden property="bpcs.modelId" />
				<nested:hidden property="bpcs.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<%=bpcsTranForm.dspAddNewMsg()%>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
									 onchange="makeAddNewDirty();"
									 onkeydown = "<%=submitAdd%>"
									 onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" />
					</td>
					<nested:nest property="bpcs">
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
										 onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(6)%>"
											 onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
											 onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
											 onchange="makeAddNewDirty();"
											 onkeydown = "<%=submitAdd%>"
											 onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
											 onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td>
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth10"
										 onchange="makeAddNewDirty();"
										 onblur="alertLength(this,10);" 
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" />
						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(5)%>" />
						</td>
						<td>
							<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" />
						</td>
					</nested:nest>
				</tr>
				<tr class="fltrTblHdng">
					<td><br>Cost</td>
					<td>Cost<br>Cur<br>Code</td>
					<td>Beg<br>Period</td>
					<td>End<br>Period</td>
					<td class="bgWhiteRight" colspan="9">&nbsp;</td>
				<tr/>
				<tr class="oddRowCenter">
					<nested:nest property="bpcs">
						<td>
							<nested:text property="costPrice" maxlength="15" styleClass="fltrWidth10"
										 onchange="makeAddNewDirty();"
										 onblur="alertLength(this,10);" 
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" />
						</td>
						<td>
							<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(5)%>" />
						</td>
						<td>
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
										 onblur="checkPadLeft(this,'0',2);" />
						</td>
						<td>
							<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
										 onchange="makeAddNewDirty();"
										 onkeydown = "<%=submitAdd%>"
										 onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
										 onblur="checkPadLeft(this,'0',2);" />
						</td>
					</nested:nest>
					<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
					<td class="bgWhiteRight" colspan="9">
						<a href="<%=bpcsTranForm.getAddBtnHref()%>" >
							<img src="images/btnAdd.png" alt="Add" /></a>
						<a href="<%=bpcsTranForm.getMassBtnHref()%>" >
							<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
						<a href="<%=bpcsTranForm.getClrBtnHref()%>" >
							<img src="images/btnClear.png" alt="Clear"/></a>
					</td>
				<%}%>	
				</tr>
			</nested:nest>
		</table>

		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/bpcsTranPaging.jsf" %></div>

<%// Start labels for bottom part of page. (subf) %>
		<table width="780" cellspacing="0">
			<tr class="mntTblHdng">
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_ACD)%>" >
						Act<br>Code<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_ACD)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_RPT_AFF)%>" >
						Rpt<br>Aff<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_RPT_AFF)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_AFF)%>" >
						Supp<br>Aff<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_AFF)%>
					</a>
				</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">
					Bill<br>Price<br>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_BP_CUR_CD)%>" >
						BP Cur<br>Code<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_BP_CUR_CD)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_FREEZE_COST)%>" >
						Freeze<br>Cost<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_FREEZE_COST)%>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_PUBLISH_FLAG)%>" >
						Pub<br>Flag<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_PUBLISH_FLAG)%>
					</a>
				</td>
				<td rowspan="2" valign="middle">
				
					<% //Sridevi.K code changed to toggle between select and deselect all the rows of data %>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('bpcsTranListItem','bpcs.selected','<%=bpcsTranForm.getBpcsTranListSize()%>');" />
					<% //Sridevi.K %>
					
				</td>
				<td colspan="1">&nbsp;</td>
			</tr>

			<tr class="mntTblHdng">
				<td>
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_INV_CD)%>" >
						Inv Cd<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_INV_CD)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_LIST)%>" >
						List<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_LIST)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_LABEL)%>" >
						Label<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_LABEL)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_SIZE)%>" >
						Size<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_SIZE)%>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_SUP_PACK)%>" >
						Pack<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_SUP_PACK)%>
					</a>
				</td>
				<td rowspan="2" colspan="4">&nbsp;</td>
			</tr>
			<tr class="mntTblHdng">
				<td colspan="1">&nbsp;</td>
				<!-- Adding blank width such that it assigns on right side of the page -->
				 <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				  <td>              
				 </td>
				<td>
			    </td>
				<td>
					<br>Cost<br>
				</td>
				<td>
					<a class="mntSort"
						href="<%=bpcsTranForm.getSrtHref(DBConst.COL_COST_CUR_CD)%>" >
						Cost Cur<br>Code<br>
						<%=bpcsTranForm.dspSort(DBConst.COL_COST_CUR_CD)%>
					</a>
				</td>
				<td>
					Beg<br>Period<br>
				</td>
				<td>
					End<br>Period<br>
				</td>
				<td colspan="9">&nbsp;</td>
			</tr>
			<%// End - Subf Lables %>

			<nested:hidden property="bpcsTranListSize" />
			<nested:notEqual property="bpcsTranListSize" value="0">
			<%//Sridevi.K Code changed to replace nested iterate tag with the JSTL tag%>
			<% int rowNumber = 0; %>

			<%// used the JSTL c:forEach tag to loop through bpcsTranList %>
			<c:forEach items="${sessionScope.bpcsTranForm.bpcsTranList}"
			           var="bpcsTranBean"
			           varStatus="bpcsTranStatus">
			           
			        <% // define the common part of the property tag of html in another string %>
		    		<% String bpcsTranItemArray = "bpcsTranListItem[" + rowNumber +"]."; %>

					<% // declare a String to notify when there is a change %>
					<% String onChangeCall = "makeEditDirty('" + "bpcsTranListItem[" + rowNumber++ + "].bpcs.selected" + "');"; %>		
					
					<% // tmpProperty is given a null to set its values compatible to the property %>
					<% String tmpProperty = "" ; %>
		
					<% //abbott is a custom tag to give some coloring effect to the alternate rows %>
					<%//Sridevi.K abbott tag modified to work fine without nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
					<%//Sridevi.K End..%>

						<td class="mntCenter">
						
							<% //Sridevi.K Added code to print the line numbers %>
							<c:out value="${sessionScope.bpcsTranForm.pagingFilter.startRecord + bpcsTranStatus.index}"/>
							<% //Sridevi.K End of code to print the line numbers %>
							
							<c:if test="${bpcsTranBean.bpcs.msg ne ''}" >
								<a class="error"
									href="#"
									id="anchor<c:out value="${bpcsTranStatus.index}"/>" 
									name="anchor<c:out value="${bpcsTranStatus.index}"/>"
									onclick="return false;"
									onmouseover="showMsgPopup('anchor<c:out value="${bpcsTranStatus.index}"/>', '<c:out value="${bpcsTranBean.bpcs.msg}"/>');"
									onmouseout='hideMsgPopup();' >
									<img src="images/exclamation.png" />
								</a>
							</c:if>

							<% //tmpProperty is initialized here as per the column actionCode %>
							<% tmpProperty = bpcsTranItemArray + "actionCode" ; %>	
							<html:text property="<%= tmpProperty %>" maxlength="1" styleClass="mntWidth1"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />							
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.rptAff %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.rptAff" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="4" styleClass="fltrWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.supAff %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supAff" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="4" styleClass="fltrWidth4"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.invCode %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.invCode" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="1" styleClass="fltrWidth1"
								       onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(1)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.list %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.list" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="6" styleClass="fltrWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(6)%>"
									   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.label %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.label" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="3" styleClass="fltrWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.size %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.size" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="3" styleClass="fltrWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(3)%>"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.pack %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.supProduct.pack" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="4" styleClass="fltrWidth4"
						               onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(4)%>"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />
						</td>
						<// end of property supProduct>

						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.billPrice %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.billPrice" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="15" styleClass="fltrWidth15"
								  	   onchange="<%=onChangeCall%>" 
								  	   onblur="alertLength(this,10);" 
									   onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.bpCurCode %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.bpCurCode" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="5" styleClass="fltrWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="<%=bpcsTranForm.getAutoTab(5)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.freezeCost %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.freezeCost" ; %>
							<html:text property="<%= tmpProperty %>" maxlength="1" styleClass="fltrWidth1"
								       onchange="<%=onChangeCall%>" onkeydown="restrSpace(event);" />
						</td>
					<// end of property bpcs>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column publishFlag %>
							<% tmpProperty = bpcsTranItemArray + "publishFlag" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="fltrWidth1" disabled="true"/>
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.selected %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.selected" ; %>
							<html:checkbox property="<%=tmpProperty%>" />
						</td>
						<td colspan="1">&nbsp;</td>
					</abbott:row>
					
					<%// Start next subf row for additional fields %>
					<%//Sridevi.K Abbott custom tag is modified to work fine without nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
					<%//Sridevi.K End..%>
					
						<td colspan="1">&nbsp;</td>
						<!-- Adding blank width such that it assigns on right side of the page -->
						 <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						  <td>              
						 </td>
						<td>              
						 </td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.costPrice %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.costPrice" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth10"
								       onchange="<%=onChangeCall%>"
								       onblur="alertLength(this,10);" 
								       onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.costCurCode %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.costCurCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="fltrWidth10"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(15)%>" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.begPeriod %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.begPeriod" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
								       onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
						</td>
						<td class="mntCenter">
							<% //tmpProperty is initialized here as per the column bpcs.endPeriod %>
							<% tmpProperty = bpcsTranItemArray + "bpcs.endPeriod" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="fltrWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="<%=bpcsTranForm.getAutoTab(2)%>"
								       onblur="checkPadLeft(this,'0',2);" onkeydown="restrSpace(event);" />
						</td>
						<% // End of bpcs nesting %>
						<td colspan="10">&nbsp;</td>
					</abbott:row>

		<%// Start the 1 - 13 billing and cost price period fields %>
		<% // Billing Price Periods %>
				<%//Sridevi.K Abbott custom tag is modified to work fine without nested iterate tag%>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
				<%//Sridevi.K End..%>
							<td colspan="1">&nbsp;</td>
							<td class="mntCenter">BP<br>Period</td>
							<td colspan="13" class="mntCenter">
								<table width="100%" cellspacing="0">
									<tr>
										<% // Using c:forEach for looping the bpcs.bpPeriodValues starting form 0 to 5 %>
										<c:forEach 	items="${bpcsTranBean.bpcs.bpPeriodValues}" 
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
										<c:forEach  items="${bpcsTranBean.bpcs.bpPeriodValues}"
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
				
				<%//Sridevi.K Abbott custom tag modified to work fine without nested iterate tag %>
				<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
				<%//Sridvi.K End..%>
				
							<td colspan="1">&nbsp;</td>
							<td class="mntCenter">
								Cost<br>Period
							</td>
							<td colspan="13" class="mntCenter">
								<table width="100%" cellspacing="0">
									<tr>
										<% // Using c:forEach for looping the bpcExBean.bpcs.costPeriodValues starting form 0 to 5 %>
										<c:forEach items="${bpcsTranBean.bpcs.costPeriodValues}"
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
										<% //Using c:forEach for looping the costPeriodValues starting form 6 to 11 %>
										<c:forEach  items="${bpcsTranBean.bpcs.costPeriodValues}" 									
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
				<%//Sridevi.K End of code added to replace nested iterate tag with JSTL tag %>

			<%// End - Subf Data Portion for Original flds	%>
			<div name="navigation" id="navigation" class="hidden">
				<%@ include file="/include/bpcsTranBtmPaging.jsf" %>
			</div>
			</nested:notEqual>
		</table>
		<nested:equal property="bpcsTranListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
	showObj('navigation');
	setFocusReposition('<%=bpcsTranForm.getFocusField()%>');
	/**
	*
	*/
	<%//Sridevi.K Script added to alert the user if he clicks copySelected without selecting any row%>
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
				for(i = 0; i < bpcsTranForm.bpcsTranListSize.value; i++) {
					var element = "bpcsTranListItem[" + i + "].bpcs.selected";
				
					if(!rowSelected){
						for(j = 0; j < bpcsTranForm.elements.length; j++) {
							if(bpcsTranForm.elements[j].name == element){

								if(bpcsTranForm.elements[j].checked == true ) {
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
	<%//Sridevi.K end of Script to alert the user if copy selected is clicked without selecting a row%>
	
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
				for(i = 0; i < bpcsTranForm.bpcsTranListSize.value; i++) {			
					var element = "bpcsTranListItem[" + i + "].bpcs.selected";				
					if(!rowSelected){				
						for(j = 0; j < bpcsTranForm.elements.length; j++) {
							if(bpcsTranForm.elements[j].name == element){
								if(bpcsTranForm.elements[j].checked == true ) {							
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
	
	<%//Sridevi.K added script to alert the user if delete selected is clicked without selecting any row%>	
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
			for(i = 0; i < bpcsTranForm.bpcsTranListSize.value; i++) {
				var element = "bpcsTranListItem[" + i + "].bpcs.selected";				
				if(!rowSelected){
					for(j = 0; j < bpcsTranForm.elements.length; j++) {
						if(bpcsTranForm.elements[j].name == element){
							if(bpcsTranForm.elements[j].checked == true ) {
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
	</script>
	<%//Sridevi.K end of Script %>
<%@ include file="/include/footer.jsf" %>