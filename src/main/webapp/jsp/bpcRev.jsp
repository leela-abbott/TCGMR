<%! String pageTitle = "BPC Revision Data"; %>
<a name="FilterView"></a>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="bpcRevForm" scope="session" class="abbott.ai.tcgm.action.form.BpcRevForm" />
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
<nested:form method="post" name="bpcRevForm" type="abbott.ai.tcgm.action.form.BpcRevForm" action="/bpcRevMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<nested:hidden property="rowToCopy" />
		<table width="780" cellspacing="0">
			<tr align="right" class="fltrTblHdng">
			<nested:hidden property="bpcRevErrorListSize" />
			<nested:notEqual property="bpcRevErrorListSize" value="0">
			 	<td rowspan="2">Errors</td>
			</nested:notEqual>
				<td rowspan="2">Rev<br>Type</td>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">BP<br>Cur<br>Code</td>
			</tr>
			<tr class="fltrTblHdng">
				<td>InvCode</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<%//Sridevi.K code modified to fix to toggle between the order of the data in a row %>
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>
			<% String submitFilter = "submitFilter(document.bpcRevForm,'filter', event);"; %>
			<tr class="oddRowCenter">
				<nested:hidden property="bpcRevErrorListSize" />
				<nested:notEqual property="bpcRevErrorListSize" value="0">
					<td>
						<input type=checkbox name=errs value="on" onClick="javascript:changeCmdAndSubmit(document.bpcRevForm,'filter');">					</td>
				</nested:notEqual>
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
					<td>
						<nested:text property="revType" maxlength="1" size="1" styleClass="fltrWidth1"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
           					onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 1, event);" />					</td>
					<td>
						<nested:text property="rptAff" maxlength="4" size="4" styleClass="fltrWidth4"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 4, event);" 
							onblur="checkPadLeft(this,'0',4);" />					</td>
					<td>
						<nested:text property="supAff" maxlength="4" size="4" styleClass="fltrWidth4"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 4, event);" 
							onblur="checkPadLeft(this,'0',4);" />					</td>
					<nested:nest property="supProduct">
						<td>
							<nested:text property="invCode" maxlength="1" size="1" styleClass="fltrWidth1"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 1, event);" />						</td>
						<td>
							<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 6, event);" />						</td>
						<td>
							<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 3, event);" />						</td>
						<td>
							<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 3, event);" />						</td>
						<td>
							<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 4, event);" />						</td>
					</nested:nest>
					<td>
						<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 5, event);" />					</td>
		  </tr>
			</nested:nest>
			<tr>
				<td colspan="12" class="right">
					<a href="javascript:changeCmdAndSubmit(document.bpcRevForm,'filter');" >
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.bpcRevForm,'advancedfilter');" >
						<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.bpcRevForm,'clearfilter');" >
						<img src="images/btnClear.png" alt="Clear Filter" /></a>				</td>
			</tr>
		</table>

		<hr />

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
			</tr>
			<tr class="fltrTblHdng">
				<td>InvCode</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<nested:nest property="addNew">
				<nested:hidden property="bpcRev.modelId" />
				<nested:hidden property="bpcRev.datasetTableId" />
				<% String submitSave = "submitSave(document.bpcRevForm,'save','bpcRevSave.do', event);"; 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
					submitSave = "submitSave(document.bpcRevForm,'save','bpcRevSave.do', event);";
				}else{
					submitSave = "";
				}
				%>
				<tr class="oddRowCenter">
					<td>
						<nested:notEqual property="bpcRev.msg" value="">
							<a class="error"
								href="#"
								id="anchorAddNew"
								name="anchorAddNew"
								onclick="return false;"
								onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="bpcRev.msg" />');"
								onmouseout='hideMsgPopup();' >
								<img src="images/exclamation.png" />
							</a>						</nested:notEqual>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
							onkeydown = "<%=submitSave%>"
							onkeyup="return autoTab(this, 1, event);" />					</td>
					<nested:nest property="bpcRev">					
						<td>
							<nested:text property="revType" maxlength="1" styleClass="fltrWidth1"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 1, event);" />						</td>
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" />						</td>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" />						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 1, event);" />							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 6, event);"
									onblur="checkPadLeft(this,'0',6);" />							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 3, event);"
									onblur="checkPadLeft(this,'0',3);" />							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 3, event);"
									onblur="checkPadLeft(this,'0',3);" />							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 4, event);"
									onblur="checkPadLeft(this,'0',4);" />							</td>
						</nested:nest>
						<td colspan="1">
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth8"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onblur="alertLength(this,10);" 
								onkeyup="return autoTab(this, 15, event);" />						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 5, event);" />						</td>
						<td colspan="1">
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);" />						</td>
						<td colspan="1">
							<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);" />						</td>
					</nested:nest>
				</tr>
			</nested:nest>
			<tr class="oddRowCenter">
			<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
				<td class="bgWhiteRight" colspan="16">
					<a href="javascript:chgActCmdSubmit(document.bpcRevForm,'save','bpcRevSave.do');" >
						<img src="images/btnSave.png" alt="Save" /></a>
					<a href="javascript:chgActCmdSubmit(document.bpcRevForm,'massupdate','bpcRevSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
					<a href="javascript:chgActCmdSubmit(document.bpcRevForm,'clearaddnew','bpcRevMaint.do');" >
						<img src="images/btnClear.png" alt="Clear"/></a>				</td>
			<%}%>			
			</tr>
		</table>

		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/bpcRevPaging.jsf" %></div>

<%// Start 1st Row of Headers & Input fields for Bottom 3rd of Screen %>
		<table width="806" cellspacing="0">
			<tr class="mntTblHdng">
				<td width="134">&nbsp;</td>		
				<td width="57" rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_REV_TYPE%>');" >
						Rev<br>
						Type<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_REV_TYPE%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td width="55" rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>
						Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td width="48" rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_SUP_AFF%>');" >
						Sup<br>
						Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_AFF%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td width="44" rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_SUP_INV_CD%>');" >
						Inv<br>
						Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_INV_CD%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td colspan="4">
					&nbsp;&nbsp;Sup Prod				</td>
				<td width="39" rowspan="2">
					Billing<br>
			  Price				</td>
				<td width="60" rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_BP_CUR_CD%>');" >
						BP<br>
						Cur<br>Cd
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_BP_CUR_CD%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td width="68" rowspan="2">
					Beg<br>
			  Period<br>				</td>
				<td width="55" rowspan="2">
					End<br>
			  Period<br>				</td>
				<td width="49" rowspan="2" valign="middle">
				<%//Sridevi.K code modified to toggle between the selectAll and deselectAll the all the rows %>
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('bpcRevListItem','selected','<%=bpcRevForm.getBpcRevListSize()%>');" />
			  <%//Sridevi.K End of code modification%>				</td>
			</tr>
			<tr class="mntTblHdng">
				<td width="134">&nbsp;</td>
				<td width="41">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_SUP_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LIST%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td width="42">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_SUP_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LABEL%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td width="42">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_SUP_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_SIZE%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
				<td width="42">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcRevForm,'<%=DBConst.COL_SUP_PACK%>');" >
						Pack<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_PACK%>" >
							<img alt="<%=bpcRevForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcRevForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>				</td>
			</tr>
			
			
			<!-- Data Rows Start Herer Gain 04-14-06 for formatting-->
			
			
			<nested:hidden property="bpcRevListSize" />
			<nested:notEqual property="bpcRevListSize" value="0">
			
			<%//Sridevi.K code added to replace the nested iterate tag with JSTL tags %>
			<% int rowNumber = 0; %>

			<%// used the JSTL c:forEach tag to loop through bpcsList %>
			<c:forEach items="${sessionScope.bpcRevForm.bpcRevList}"
			                		  var="bpcRevBean"
				               	      varStatus="bpcRevStatus">

					<% // declare a String to notify when there is a change %>							
					<% String onChangeCall = "makeEditDirty('" + "bpcRevListItem[" + rowNumber + "].selected" + "');"; %>

					<% // define the common part of the property tag of html in another string %>
		        	<% String bpcRevListItemArray = "bpcRevListItem[" + rowNumber +"]."; %>

					<%// String href encapsulates the call to a JavaScript copyRow %>
					<% String href = "javascript:copyRow(document.bpcRevForm,'" + rowNumber++ + "','bpcRevMaint.do');"; %>
					
					<% // tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>
					
					<% String tmpRevTp = "RevTp"+rowNumber ; %>
					
					<% String tmpRpt = "Rpt"+rowNumber ; %>
					
					<% String tmpSupAff = "SupAff"+rowNumber ; %>
					
					<% String tmpInvCode = "invCode"+rowNumber ; %> 
					
					<% String tmpList = "list"+rowNumber ; %>
					
					<% String tmpLabel = "label"+rowNumber ; %>
					
					<% String tmpSize = "size"+rowNumber ; %>
					
					<% String tmpPack = "pack"+rowNumber ; %>
					
					<% String tmpBillPrice = "billPrce"+rowNumber ; %>
					
					<% String tmpCurCode = "curCode"+rowNumber ; %>
					
					<% String tmpFrzCost = "frzCost"+rowNumber ; %>
					
					<% String tmpSlChk = "slChk"+rowNumber ; %>
					
					<% String tmpCstPrce = "CstPr"+rowNumber ; %>
					
					<% String tmpCstCur = "cstCur"+rowNumber ; %>
					
					<% String tmpBegPd = "begPd"+rowNumber ; %>
					
					<% String tmpEndPd = "endPd"+rowNumber ; %>

					<%//Sridevi.K Modified Abbott row to make the tag work without the nested iterate tag %>
					<% // abbott is a custom tag to give some coloring effect to the alternate rows %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
					<%//Sridevi.K End of modification of Abbott row to make the tag work without the nested iterate tag %>
										
					<tr><td colspan="1" class="mntCenter">
					
						<% //Sridevi.K Code added to print the line numbers %>
							<c:out value="${sessionScope.bpcRevForm.pagingFilter.startRecord + bpcRevStatus.index}"/>
						<% //Sridevi.K end of code added to print the line numbers%>
					
						<%//Clicking this invokes a javaScript that has been encapsulated above in the String href %>
						<a href="<%=href%>">
							<img src="images/btnUpArrow.png" alt="Load Row" />						</a>
						
						<% // Using 'c:if' to check if the bpcRevBean msg is not equal to "  " %>
						<!--Dynamically creating the  script and image tag to save space 04-14-06 - Gain-->
								
				<c:if test="${bpcRevBean.msg ne ''}" >
			<%	out.println("<a class=\"error\"");
			    out.println(" href=\"#\"");
				out.print(" id=\"anchor");%><c:out value="${bpcRevStatus.index}"/><% out.println("\" ");%>  
			<%out.print(" name=\"anchor");%><c:out value="${bpcRevStatus.index}"/><% out.println("\" ");%>  
			<%out.println(" onclick=\"return false;\"");%>
	        <%out.print(" onmouseover=\"showMsgPopup('anchor");%><c:out value="${bpcRevStatus.index}"/><%out.print("' ");%>
		    <%out.print(" , '");%><c:out value="${bpcRevBean.msg}"/> <% out.println("');\"");%>
			<%out.println(" onmouseout=\"hideMsgPopup();\" >");
			out.println(" <img src=\"images/exclamation.png\" />");
			out.println("  </a>");	%>																	
					  </c:if></td>
					<td colspan="1" class="mntCenter"><% //tmpProperty is initialized here as per the column revType %>
                      <% tmpProperty = bpcRevListItemArray + "revType" ; %>
					  
					  
					 
					  
								
                      <html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
							       onchange="<%=onChangeCall%>"
							       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />    </td>
								   
					<td colspan="1" class="mntCenter"><% //tmpProperty is initialized here as per the column rptAff %>
                        <% tmpProperty = bpcRevListItemArray + "rptAff" ; %> 
						 
                        <html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
							       onchange="<%=onChangeCall%>"
							       onkeyup="return autoTab(this, 4, event);"
							       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />            </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column supAff %>
                        <% tmpProperty = bpcRevListItemArray + "supAff" ; %>
						
						
                        <html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
							       onchange="<%=onChangeCall%>"
							       onkeyup="return autoTab(this, 4, event);"
							       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />               </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column supProduct.invCode %>
                        <% tmpProperty = bpcRevListItemArray + "supProduct.invCode" ; %>
						
					
								
                        <html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
							       onchange="<%=onChangeCall%>"
							       onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" />                    </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column supProduct.list %>
                        <% tmpProperty = bpcRevListItemArray + "supProduct.list" ; %>
						
                        <html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
							       onchange="<%=onChangeCall%>"
							       onkeyup="return autoTab(this, 6, event);"
							       onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" />                 </td>
					<td class="mntCenter">
						<% //tmpProperty is initialized here as per the column supProduct.label %>
						<% tmpProperty = bpcRevListItemArray + "supProduct.label" ; %>
						
						<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
								   onchange="<%=onChangeCall%>"
								   onkeyup="return autoTab(this, 3, event);"
								   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />				</td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column supProduct.size %>
                        <% tmpProperty = bpcRevListItemArray + "supProduct.size" ; %>
						
						
                        <html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"								
								   onchange="<%=onChangeCall%>"
								   onkeyup="return autoTab(this, 3, event);"
								   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" />                 </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column supProduct.pack %>
                        <% tmpProperty = bpcRevListItemArray + "supProduct.pack" ; %>
						
						
                        <html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
							       onchange="<%=onChangeCall%>"
							       onkeyup="return autoTab(this, 4, event);"								
							       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />            </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column billPrice %>
                        <% tmpProperty = bpcRevListItemArray + "billPrice" ; %>
						
                        <html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
							       onchange="<%=onChangeCall%>"
							       onblur="alertLength(this,10);" 
							       onkeyup="return autoTab(this, 15, event);" onkeydown="restrSpace(event);" />        </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column bpCurCode %>
                        <% tmpProperty = bpcRevListItemArray + "bpCurCode" ; %>
						
                        <html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 5, event);" onkeydown="restrSpace(event);" />             </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column begPeriod %>
                        <% tmpProperty = bpcRevListItemArray + "begPeriod" ; %>
						
                        <html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 2, event);" onkeydown="restrSpace(event);" />              </td>
					<td class="mntCenter"><% //tmpProperty is initialized here as per the column endPeriod %>
                        <% tmpProperty = bpcRevListItemArray + "endPeriod" ; %>
						
						
                        <html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 2, event);" onkeydown="restrSpace(event);" />                 </td>
					<td class="mntCenter"><%//tmpProperty is initialized here as per the column selected%>
                        <% tmpProperty = bpcRevListItemArray + "selected" ; %>
					
								
                        <html:checkbox property="<%=tmpProperty%>" /> 
                    </td>
											
					</abbott:row>						
					
					<%// Start the 1 - 12 billing price and cost price period fields %>
					<%//Sridevi.K Abbott custom tag is modified to work without nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
					<%//Sridevi.K end of code modification of the abbott %>
						<td class="mntCenter">
							Bill 1-6<br>Price 7-12						</td>
						<td colspan="18" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<% // Useing c:forEach for looping the bpPeriodValues starting form 0 to 5 %>
									<c:forEach  items="${bpcRevBean.bpPeriodValues}"
											   	begin="0"
												end="5"
												step="1"
												var="bpPeriod1"
												varStatus="bpPeriodStatus1">
									  			<td class="mntRight" width="16%">
													<c:out value="${bpPeriod1.period}" />												</td>
									</c:forEach>
								</tr>
								<tr>
									<% // Useing c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
									<c:forEach items="${bpcRevBean.bpPeriodValues}"
									 		   begin="6"
												end="11"
												step="1"
												var="bpPeriod2"
												varStatus="bpPeriodStatus2">
									  			<td class="mntRight" width="16%">
													<c:out value="${bpPeriod2.period}" />												</td>
								  </c:forEach>
								</tr>
							</table>						</td>
					</abbott:row>
					<% // Cost Price Periods %>
					<%//Sridevi.K Abott custom tag is modified to work with out the nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
					<%// Sridevi.K end..%>
						<td class="mntCenter">
							1-6<br>Cost 7-12						</td>
						<td colspan="18" class="mntCenter">
				    	  <table width="100%" cellspacing="0">
							<tr>
								<% // Useing c:forEach for looping the bpPeriodValues starting form 0 to 5 %>
						  		<c:forEach items="${bpcRevBean.costPeriodValues}" 
									begin="0"
									end="5"
									step="1"
									var="costPeriodBean1"
									varStatus="costPeriodStatus1">
									<td class="mntRight" width="16%">
										<c:out value="${costPeriodBean1.period}" />									</td>
								</c:forEach>			
						  	</tr>
							<tr>
								<% // Useing c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
								<c:forEach items="${bpcRevBean.costPeriodValues}"
									begin="6"
									end="11"
									step="1"
									var="costPeriod2"
									varStatus="costPeriodStatus2">
									<td class="mntRight" width="16%">
										<c:out value="${costPeriod2.period}" />									</td>
								</c:forEach>									
							</tr>
						 </table>					   </td>						  
					</abbott:row>
<%// End the 1 - 12 billing price and cost price period fields %>
			  </c:forEach>
				<%//Sridevi.K end of changes made to replace nested iterate tag with JSTL tags%>

<%// End - Subf Data Portion for Original flds	%>
					<div name="navigation" id="navigation" class="hidden">
						<%@ include file="/include/bpcRevPaging.jsf" %>
					</div>
			</nested:notEqual>
		
		<nested:equal property="bpcRevListSize" value="0">
			<%@ include file="/include/recordsNotFound.jsf" %>
		</nested:equal>	
		
		<%// End of- Data rows Gain -04-14-06 	%>
  </table>
		
			
	    <hr />
</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=bpcRevForm.getFocusField()%>');		
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record and confirm the deletion of records
 	*/
	function doDelete(form,cmd,action)
	{
		var rowSelected=false;
		if ( cmd == 'deleteall'){		
			rowSelected=true;
		}
		else {
			for(i = 0; i < bpcRevForm.bpcRevListSize.value; i++) {
				var element = "bpcRevListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcRevForm.elements.length; j++) {
						if(bpcRevForm.elements[j].name == element){
							if(bpcRevForm.elements[j].checked == true ) {
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
			alert( 'You must select at least one row to delete' );
		}	
	}							
	<%//Sridevi.K 7-16-05 End of script..%>
	
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < bpcRevForm.bpcRevListSize.value; i++) {
				var element = "bpcRevListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcRevForm.elements.length; j++) {
						if(bpcRevForm.elements[j].name == element){
							if(bpcRevForm.elements[j].checked == true ) {
								rowSelected=true;
							}
						}
					}
				}			
		}			
		if( rowSelected ) {	
	
			chgActCmdSubmit(form,cmd,action);
		}
		else {	
			alert( 'You must select at least one row to SaveSelected' );
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
	<%//Sridevi.K 7-16-05 End of script..%>					
	
  </script>
<%@ include file="/include/footer.jsf" %>