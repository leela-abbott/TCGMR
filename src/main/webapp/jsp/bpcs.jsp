
<%! String pageTitle = "BPC Data"; %>
<%@ include file="/include/header.jsf" %>
<jsp:useBean id="bpcsForm" scope="session" class="abbott.ai.tcgm.action.form.BpcsForm" />
<a name="FilterView"></a><body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>	
	<%@ include file="/include/maintNav.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<nested:form method="post" name="bpcsForm" type="abbott.ai.tcgm.action.form.BpcsForm" action="/bpcsMaint.do" scope="session">
		<nested:hidden property="cmd" />
		<nested:hidden property="focusField" />
		<nested:hidden property="rowToCopy" />
		<table width="780" cellspacing="0">
			<tr align="right" class="fltrTblHdng">
			<nested:hidden property="bpcsErrorListSize" />
			<nested:notEqual property="bpcsErrorListSize" value="0">
			 	<td rowspan="2">Errors</td>
			</nested:notEqual>
				<td rowspan="2">Rpt<br>Aff</td>
				<td rowspan="2">Sup<br>Aff</td>
				<td colspan="5">Sup Prod</td>
				<td rowspan="2">BP<br>Cur<br>Code</td>
				<td rowspan="2">Freeze<br>Cost</td>
				<td rowspan="2">Cost<br>Cur<br>Code</td>				
			</tr>
			<tr class="fltrTblHdng">
				<td>InvCode</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<% // Sridevi.K code modified to toggle between the the order of the data in a row %>
			<nested:nest property="sortObject">
				<nested:hidden property="sortColumn" />
				<nested:hidden property="sortOrder" />
			</nested:nest>
						<% String submitFilter = "submitFilter(document.bpcsForm,'filter', event);"; %>
			<tr class="oddRowCenter">
			<nested:hidden property="bpcsErrorListSize" />
			<nested:notEqual property="bpcsErrorListSize" value="0">
					<td>
						<input type=checkbox name=errs value="on" onClick="javascript:changeCmdAndSubmit(document.bpcsForm,'filter');">
					</td>
			</nested:notEqual>
			<nested:nest property="searchObject">
				<nested:hidden property="modelId" />
				<nested:hidden property="datasetTableId" />
					<td>
						<nested:text property="rptAff" maxlength="4" size="4" styleClass="fltrWidth4"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
                            onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 4, event);" 
							onblur="checkPadLeft(this,'0',4);" />
					</td>
					<td>
						<nested:text property="supAff" maxlength="4" size="4" styleClass="fltrWidth4"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
                            onkeydown = "<%=submitFilter%>"							
							onkeyup="return autoTab(this, 4, event);" 
							onblur="checkPadLeft(this,'0',4);" />
					</td>
					<nested:nest property="supProduct">
						<td>
							<nested:text property="invCode" maxlength="1" size="1" styleClass="fltrWidth1"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 1, event);" />
						</td>
						<td>
							<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 6, event);" />
						</td>
						<td>
							<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 3, event);" />
						</td>
						<td>
							<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
								onchange="makeFilterDirty('pagingDiv','red','bold');"
								onkeydown = "<%=submitFilter%>"
								onkeyup="return autoTab(this, 4, event);" />
						</td>
					</nested:nest>
					<td>
						<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 5, event);" />
					</td>
					<td>
						<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 1, event);" />
					</td>
					<td>
						<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
							onchange="makeFilterDirty('pagingDiv','red','bold');"
							onkeydown = "<%=submitFilter%>"
							onkeyup="return autoTab(this, 5, event);" />
					</td>
		  </tr>
			</nested:nest>
			<tr>
				<td colspan="11" class="right">
					<a href="javascript:changeCmdAndSubmit(document.bpcsForm,'filter');" >
						<img src="images/btnFilter.png" alt="Filter" /></a>
					<a href="javascript:changeCmdAndSubmit(document.bpcsForm,'advancedfilter');" >
						<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>						
					<a href="javascript:changeCmdAndSubmit(document.bpcsForm,'clearfilter');" >
						<img src="images/btnClear.png" alt="Clear Filter" /></a>
				</td>
			</tr>
		</table>

		<hr />

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
				<td>InvCode</td>
				<td>List</td>
				<td>Label</td>
				<td>Size</td>
				<td>Pack</td>
			</tr>
			<% String submitSave = "submitSave(document.bpcsForm,'save','bpcsSave.do', event);"; 
			if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) {
					submitSave = "submitSave(document.bpcsForm,'save','bpcsSave.do', event);";
				}else{
					submitSave = "";
				}
			%>
			
			<nested:nest property="addNew">
				<nested:hidden property="bpcs.modelId" />
				<nested:hidden property="bpcs.datasetTableId" />
				<tr class="oddRowCenter">
					<td>
						<nested:notEqual property="bpcs.msg" value="">
							<a class="error"
								href="#"
								id="anchorAddNew"
								name="anchorAddNew"
								onclick="return false;"
								onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="bpcs.msg" />');"
								onmouseout='hideMsgPopup();' >
								<img src="images/exclamation.png" />
							</a>
						</nested:notEqual>
						<nested:text property="actionCode" maxlength="1" styleClass="fltrWidth1"
							onchange="makeAddNewDirty();"
                            onkeydown = "<%=submitSave%>"
							onkeyup="return autoTab(this, 1, event);" />
					</td>
					<nested:nest property="bpcs">
						<td>
							<nested:text property="rptAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<td>
							<nested:text property="supAff" maxlength="4" styleClass="fltrWidth4"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 4, event);"
								onblur="checkPadLeft(this,'0',4);" />
						</td>
						<nested:nest property="supProduct">
							<td>
								<nested:text property="invCode" maxlength="1" styleClass="fltrWidth1"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 1, event);" />
							</td>
							<td>
								<nested:text property="list" maxlength="6" styleClass="fltrWidth6"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 6, event);"
									onblur="checkPadLeft(this,'0',6);" />
							</td>
							<td>
								<nested:text property="label" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 3, event);"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="size" maxlength="3" styleClass="fltrWidth3"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 3, event);"
									onblur="checkPadLeft(this,'0',3);" />
							</td>
							<td>
								<nested:text property="pack" maxlength="4" styleClass="fltrWidth4"
									onchange="makeAddNewDirty();"
									onkeydown = "<%=submitSave%>"
									onkeyup="return autoTab(this, 4, event);"
									onblur="checkPadLeft(this,'0',4);" />
							</td>
						</nested:nest>
						<td colspan="1">
							<nested:text property="billPrice" maxlength="15" styleClass="fltrWidth8"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onblur="alertLength(this,10);" 
								onkeyup="return autoTab(this, 15, event);" />
						</td>
						<td>
							<nested:text property="bpCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 5, event);" />
						</td>
						<td>
							<nested:text property="freezeCost" maxlength="1" styleClass="fltrWidth1"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 1, event);" />
						</td>
					</nested:nest>
				</tr>
			</nested:nest>
			<tr class="fltrTblHdng">
				<td rowspan="1"><br>Cost</td>
				<td rowspan="1">Cost<br>Cur<br>Code</td>
				<td rowspan="1">Beg<br>Period</td>
				<td rowspan="1">End<br>Period</td>
			</tr>
			<nested:nest property="addNew">
				<nested:hidden property="bpcs.modelId" />
				<nested:hidden property="bpcs.datasetTableId" />
				<nested:nest property="bpcs">
					<tr class="oddRowCenter">
						<td colspan="1">
							<nested:text property="costPrice" maxlength="15" styleClass="fltrWidth8"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"   
								onblur="alertLength(this,10);" 
								onkeyup="return autoTab(this, 15, event);" />
						</td>
						<td colspan="1">
							<nested:text property="costCurCode" maxlength="5" styleClass="fltrWidth5"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 5, event);" />
						</td>
						<td colspan="1">
							<nested:text property="begPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);" />
						</td>
						<td colspan="1">
							<nested:text property="endPeriod" maxlength="2" styleClass="fltrWidth2"
								onchange="makeAddNewDirty();"
								onkeydown = "<%=submitSave%>"
								onkeyup="return autoTab(this, 2, event);"
								onblur="checkPadLeft(this,'0',2);" />
						</td>
						<% 
				if ((TCGMUser.getRole().getAccessLevel()) != (Role.Query.getAccessLevel())) { %>
						<td class="bgWhiteRight" colspan="12">
							<a href="javascript:chgActCmdSubmit(document.bpcsForm,'save','bpcsSave.do');" >
								<img src="images/btnSave.png" alt="Save" /></a>
							<a href="javascript:chgActCmdSubmit(document.bpcsForm,'massupdate','bpcsSave.do');">
								<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria" /></a>
							<a href="javascript:chgActCmdSubmit(document.bpcsForm,'clearaddnew','bpcsMaint.do');" >
								<img src="images/btnClear.png" alt="Clear"/></a>
						</td>
						<%}%>
					</tr>
				</nested:nest>
			</nested:nest>
		</table>

		<hr />

<a name="ChangeMultipleRowView"></a>
		<div name="navigation" id="navigation" class="hidden"><%@ include file="/include/bpcsPaging.jsf" %></div>

<%// Start 1st Row of Headers & Input fields for Bottom 3rd of Screen %>
		<table width="789" cellspacing="0">
			<tr class="mntTblHdng">
			  <td width="73" rowspan="2">&nbsp;</td>
				<td width="58" rowspan="2"><a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_RPT_AFF%>');" >Rpt<br>
Aff<br>
<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_RPT_AFF%>" > <img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" /> </nested:equal>
                </a>
			  <a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_RPT_AFF%>');" ></a>			  </td>
				<td width="63" rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_SUP_AFF%>');" >
						Sup<br>
						Aff<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_AFF%>" >
							<img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>			  </td>
				<td width="53" rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_SUP_INV_CD%>');" >
						Inv<br>
						Cd<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_INV_CD%>" >
							<img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>			  </td>
				<td colspan="4">
					Sup Prod				</td>
			  <td width="73" rowspan="2">
			    <a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_BP_CUR_CD%>');" ></a>			  Bill<br>
Price </td>
				<td width="74" rowspan="2">
				  <a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_FREEZE_COST%>');" ></a><a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_BP_CUR_CD%>');" >BP<br>
Cur<br>
Cd
<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_BP_CUR_CD%>" > <img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" /> </nested:equal>
              </a> </td>
			  <td width="69" rowspan="2" valign="middle"><a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_FREEZE_COST%>');" >Freeze<br>
Cost
    <nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_FREEZE_COST%>" > <img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" /> </nested:equal>
              </a></td>
				<td width="32" colspan="1">&nbsp;</td>
			</tr>
			<tr class="mntTblHdng">
				<td width="69">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_SUP_LIST%>');" >
						List<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LIST%>" >
							<img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>			  </td>
				<td width="73">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_SUP_LABEL%>');" >
						Label<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_LABEL%>" >
							<img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>			  </td>
				<td width="62">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_SUP_SIZE%>');" >
						Size<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_SIZE%>" >
							<img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
			  </a>			  </td>
			  <td width="62"><a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_SUP_PACK%>');" >Pack<br>
                  <nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_SUP_PACK%>" > <img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" /></nested:equal>
			  </a></td>
				<td colspan="1"><%//Sridevi.K code modified to fix the toggle between selecting and deselecting all the rows %>
                  <input name="image" type="image" onClick="return toggleSelectAll('bpcsListItem','selected','<%=bpcsForm.getBpcsListSize()%>');" src="images/btnCheck.png" alt="Toggle Select All" />
                <%//Sridevi.K End of code modification to fix the toggle between selecting and deselecting all the row %></td>
			</tr>

			<tr class="mntTblHdng">
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
				 
				<td colspan="1" rowspan="1">
					<br>Cost<br>				</td>
				<td colspan="1" rowspan="1">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.bpcsForm,'<%=DBConst.COL_COST_CUR_CD%>');" >
						Cost<br>CurCode<br>
						<nested:equal property="sortObject.sortColumn" value="<%=DBConst.COL_COST_CUR_CD%>" >
							<img alt="<%=bpcsForm.getSortObject().getSortImgAltTxt()%>" src="<%=bpcsForm.getSortObject().getSortImg()%>" align="center" />						</nested:equal>
					</a>				</td>
				<td colspan="1" rowspan="1">
					Beg<br>Period<br>				</td>
				<td colspan="1" rowspan="1">
					End<br>Period<br>				</td>
				<td colspan="7"><span class="mntCenter">
				  
				</span></td>
			</tr>
	  </table>
		<table width="789" cellspacing="0">

			
			<nested:hidden property="bpcsListSize" />
			<nested:notEqual property="bpcsListSize" value="0">
			
			<% //Sridevi.K JSTL tags are used to replace nested iterate tag of struts %>
			<% int rowNumber = 0; %>

			<%// used the JSTL c:forEach tag to loop through bpcsList %>
			<c:forEach items="${sessionScope.bpcsForm.bpcsList}"
			                		  var="bpcsBean"
			                	varStatus="bpcsStatus">

					<% //declare a String to notify when there is a change %>	
					<% String onChangeCall = "makeEditDirty('" + "bpcsListItem[" + rowNumber + "].selected" + "');"; %>

					<% // define the common part of the property tag of html in another string %>
		        	<% String bpcsListItemArray = "bpcsListItem[" + rowNumber +"]."; %>

					<%//String href encapsulates the call to a JavaScript copyRow %>
					<% String href = "javascript:copyRow(document.bpcsForm,'" + rowNumber++ + "','bpcsMaint.do');"; %>

					<% // tmpProperty is given a null to set its values compatible to the property%>
					<% String tmpProperty = "" ; %>
					
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
					
										
    
					<% // abbott is a custom tag to give some coloring effect to the alternate rows %>
					<%//Sridevi.K Abbott custom tag is changed to work properly without the nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" id="mntRow" rowNum="<%=rowNumber%>">
					<% //Sridevi.K End of code added to Abbott custom tag %>

						<%//Clicking this invokes a javaScript that has been encapsulated above in the String href %>
						<td width="69" colspan="1"  class="mntCenter">
							<% // Sridevi.K Coded added to print the line numbers %>
							<c:out value="${sessionScope.bpcsForm.pagingFilter.startRecord + bpcsStatus.index}"/>
							<% // Sridevi.K End of code added to print the line numbers %>
							<a href="<%=href%>">
								<img src="images/btnUpArrow.png" alt="Load Row" /></a>
							</a>
						
							<% // Using 'c:if' to check if the bpcsBean msg is not equal to "  " %>
							<c:if test="${bpcsBean.msg ne ''}" >
						<%	out.println("<a class=\"error\"");
									out.println(" href=\"#\"");
									out.print(" id=\"anchor");%><c:out value="${bpcsStatus.index}"/><% out.println("\" ");%>  
									<%out.print(" name=\"anchor");%><c:out value="${bpcsStatus.index}"/><% out.println("\" ");%>  
									<%out.println(" onclick=\"return false;\"");%>
	<%out.print(" onmouseover=\"showMsgPopup('anchor");%><c:out value="${bpcsStatus.index}"/><%out.print("' ");%>
		 <%out.print(" , '");%><c:out value="${bpcsBean.msg}"/> <% out.println("');\"");%>
									
				
					
									<%out.println(" onmouseout=\"hideMsgPopup();\" >");
									out.println(" <img src=\"images/exclamation.png\" />");
								out.println("  </a>");	%>																	
						</c:if>						</td>
						
						
				<td width="62" colspan="1" class="mntCenter"><% //tmpProperty is initialized here as per the column rptAff %>
				  <% tmpProperty = bpcsListItemArray + "rptAff" ; %>
				 
								

                <html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
								       onchange="<%=onChangeCall%>"
								       onkeyup="return autoTab(this, 4, event);"
								       onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" />  </td>

						<td width="60" colspan="1" class="mntCenter">
							<% //tmpProperty is initialized here as per the column supAff %>
							<% tmpProperty = bpcsListItemArray + "supAff" ; %>
							
							
							
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
							 		   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" /> 						</td>

						<td width="57" colspan="1" class="mntCenter">
						
						
							<%//tmpProperty is initialized here as per the column invCode of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.invCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 1, event);" onkeydown="restrSpace(event);" /> 						</td>
						<td width="72" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column list of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.list" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="6" styleClass="mntWidth6"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 6, event);"
									   onblur="checkPadLeft(this,'0',6);" onkeydown="restrSpace(event);" /> 						</td>
						<td width="70" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column label of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.label" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);" /> 						</td>

						<td width="64" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column size of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.size" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="3" styleClass="mntWidth3"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 3, event);"
									   onblur="checkPadLeft(this,'0',3);" onkeydown="restrSpace(event);"  /> 					</td>

						<td width="62" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column pack of supProduct%>
							<% tmpProperty = bpcsListItemArray + "supProduct.pack" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="4" styleClass="mntWidth4"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 4, event);"
									   onblur="checkPadLeft(this,'0',4);" onkeydown="restrSpace(event);" /> 						</td>

						<td width="65" colspan="1" class="mntCenter">
						
						
							<%//tmpProperty is initialized here as per the column billPrice%>
							<% tmpProperty = bpcsListItemArray + "billPrice" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
									   onchange="<%=onChangeCall%>"
									   onblur="alertLength(this,10);" 
									   onkeyup="return autoTab(this, 15, event);" onkeydown="restrSpace(event);" />					</td>

						<td width="78" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column BpCurCode%>
							<% tmpProperty = bpcsListItemArray + "bpCurCode" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 5, event);" onkeydown="restrSpace(event);" /> 						</td>

						<td width="47" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column freezeCost%>
							<% tmpProperty = bpcsListItemArray + "freezeCost" ; %>
							<html:text property="<%=tmpProperty%>" maxlength="1" styleClass="mntWidth1"
									   onchange="<%=onChangeCall%>" onkeydown="restrSpace(event);" /> 						</td>
						<td width="57" colspan="1" class="mntCenter">
						
							<%//tmpProperty is initialized here as per the column selected%>
							<% tmpProperty = bpcsListItemArray + "selected" ; %>
							<html:checkbox property="<%=tmpProperty%>" styleClass="mntWidth1" /> 				</td>
					</abbott:row>
					<%//Sridevi.K Abbott custom tag is modified to work without the nested iterate tag %>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" id="mntRow">
					<%//Sridevi.K end..%>
					
						  <td class="mntCenter" colspan="1">&nbsp;</td>
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
						  <td class="mntCenter" colspan="1">
						 
						  <%//tmpProperty is initialized here as per the column costPrice %>
                            <% tmpProperty = bpcsListItemArray + "costPrice" ; %>
                            <html:text property="<%=tmpProperty%>" maxlength="15" styleClass="mntWidth10"
									   onchange="<%=onChangeCall%>" 
									   onblur="alertLength(this,10);" 
									   onkeyup="return autoTab(this, 15, event);" onkeydown="restrSpace(event);" />  </td>
						  <td class="mntCenter" colspan="1">
						 
						  <%//tmpProperty is initialized here as per the column costCurCode %>
                            <% tmpProperty = bpcsListItemArray + "costCurCode" ; %>
                            <html:text property="<%=tmpProperty%>" maxlength="5" styleClass="mntWidth5"
									   onchange="<%=onChangeCall%>"
									   onkeyup="return autoTab(this, 5, event);"  onkeydown="restrSpace(event);" />  </td>
						  <td class="mntCenter" colspan="1">
								
						  <%//tmpProperty is initialized here as per the column begPeriod %>
                            <% tmpProperty = bpcsListItemArray + "begPeriod" ; %>
                           
							
							
							<html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
									onchange="<%=onChangeCall%>"
									onkeyup="return autoTab(this, 2, event);" onkeydown="restrSpace(event);" /> </td>
						  <td class="mntCenter">
						  
						  <%//tmpProperty is initialized here as per the column EndPeriod %>
						   <% tmpProperty = bpcsListItemArray + "endPeriod" ; %>
						  <html:text property="<%=tmpProperty%>" maxlength="2" styleClass="mntWidth2"
									onchange="<%=onChangeCall%>"
									onkeyup="return autoTab(this, 2, event);" onkeydown="restrSpace(event);" /> </td>
						  <td class="mntCenter">&nbsp;</td>
						<td colspan="7">&nbsp;</td>
					</abbott:row>

					<%// Start the 1 - 12 billing price and cost price period fields %>
					<Sridevi.K Abbott custom tag is modified to work without the nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>">
						  <td class="mntCenter">
							Bill1-6<br>
							Price7-12						</td>
						  <td colspan="12" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<% // Useing c:forEach for looping the bpPeriodValues starting form 0 to 5 %>								     
									<c:forEach 	items="${bpcsBean.bpPeriodValues}" 
											begin="0"
											end="5"
											step="1"
											var="bpPeriod1"
			                				varStatus="bpPeriodStatus1">
											<td class="mntRight" width="16%">
											<c:out value="${bpPeriod1.period}"/>										</td>
									</c:forEach>
								</tr>
								<tr>
									<% // Useing c:forEach for looping the bpPeriodValues starting form 6 to 11 %>
									<c:forEach  items="${bpcsBean.bpPeriodValues}"
											begin="6"
											end="11"
											step="1"
											var="bpPeriod2"
			                				varStatus="bpPeriodStatus2"> 
											<td class="mntRight">
											<c:out value="${bpPeriod2.period}"/>										</td>
									</c:forEach>
								</tr>
							</table>						</td>
					</abbott:row>

					<% // Cost Price Periods %>
					<%//Sridevi.K abbott custom tag is modified to work without the nested iterate tag%>
					<abbott:row evenStyleClass="evenRow" oddStyleClass="oddRow" rowNum="<%=rowNumber%>" >
					<% //Sridevi.K end of modification of the custom tag %>
						<td class="mntCenter">
							Cost1-6<br>						 
						  7-12						</td>
						  <td colspan="12" class="mntCenter">
							<table width="100%" cellspacing="0">
								<tr>
									<% // Using c:forEach for looping the costPeriodValues starting form 0 to 5 %>
									<c:forEach  items="${bpcsBean.costPeriodValues}"
										begin="0"
										end="5"
										step="1"
									 	var="costPeriodBean1"
			                			varStatus="costPeriodStatus1"> 
										<td class="mntRight" width="16%">
											<c:out value="${costPeriodBean1.period}"/>										</td>
									</c:forEach>
								</tr>
								<tr>
								   	<% // Using c:forEach for looping the costPeriodValues starting form 6 to 11 %>
									<c:forEach  items="${bpcsBean.costPeriodValues}" 									
										begin="6"
										end="11"
										step="1"
										var="costPeriod2"
										varStatus="costPeriodStatus2"> 
										<td class="mntRight">
											<c:out value="${costPeriod2.period}"/>										</td>
									</c:forEach>								
								</tr>
						</table>					</td>
				</abbott:row>

				<%// End of the iterations of the bpcsList%>
			  </c:forEach>
				<% //Sridevi.K end of replacement of code for nested iterate tag %>
			<%// End - Subf Data Portion for Original flds	%>
				<div name="navigation" id="navigation" class="hidden">
					<%@ include file="/include/bpcsPaging.jsf" %>
				</div>
			</nested:notEqual>
	</table>
	<nested:equal property="bpcsListSize" value="0">
		<%@ include file="/include/recordsNotFound.jsf" %>
	</nested:equal>
		<hr />
	</nested:form>
	<script language="JavaScript1.2" type="text/javascript">
		showObj('navigation');
		setFocusReposition('<%=bpcsForm.getFocusField()%>');
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
			for(i = 0; i < bpcsForm.bpcsListSize.value; i++) {
				var element = "bpcsListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcsForm.elements.length; j++) {
						if(bpcsForm.elements[j].name == element){
							if(bpcsForm.elements[j].checked == true ) {
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
	<%//Sridevi.K 7-16-05 End script..%>
	
	<% //Sridevi.K 7-16-05 script added to alert the user if he clicks deleteselected without selecting any row. %>
	/**
 	* Prompt the user to select atleast one record to Save Selected
 	*/
	function checkSave(form,cmd,action)
	{
		var rowSelected=false;
		for(i = 0; i < bpcsForm.bpcsListSize.value; i++) {
				var element = "bpcsListItem[" + i + "].selected";				
				if(!rowSelected){
					for(j = 0; j < bpcsForm.elements.length; j++) {
						if(bpcsForm.elements[j].name == element){
							if(bpcsForm.elements[j].checked == true ) {
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