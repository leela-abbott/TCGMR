<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>

<%!
    String pageTitle = "ASR Data";
%>

<a id="FilterView" name="FilterView"></a>

<%@ include file="/include/header.jsf" %>

<!--
    Keep this custom tag only if it is still available in the application.
    Ideally authentication should eventually move to a Struts interceptor.
-->

                   <!-- Added for -->
<abbott:checkLogon beanName="TCGMUser" forwardPage="login.jsp" />
<jsp:useBean id="TCGMUser"  scope="session" type="abbott.ai.tcgm.entities.User" />
<jsp:useBean id="DBConst"  scope="session" class="abbott.ai.tcgm.data.DBConst" />
<jsp:useBean id="asrForm" scope="session" class="abbott.ai.tcgm.action.form.AsrForm" />
<!-- end -->

<body leftmargin="0"
      topmargin="0"
      marginwidth="0"
      marginheight="0">

<%@ include file="/include/masthead.jsf" %>
<%@ include file="/include/maintNav.jsf" %>
<%@ include file="/include/errorDisplay.jsf" %>


<!-- ========================================================= -->
<!-- MAIN FORM                                                 -->
<!-- ========================================================= -->

<s:form method="post" name="asrForm" type="abbott.ai.tcgm.action.form.AsrForm" action="asrMaintenance.action" scope="session">

    <s:hidden name="cmd" id="cmd"/>
    <s:hidden name="focusField"/>
    <s:hidden name="rowToCopy"/>
    <s:hidden name="asrErrorListSize"/>
    <s:hidden name="asrListSize"/>

    <s:hidden name="sortObject.sortColumn"/>
    <s:hidden name="sortObject.sortOrder"/>


    <!-- ===================================================== -->
    <!-- FILTER SECTION                                        -->
    <!-- ===================================================== -->

    <table width="780" cellspacing="0">

        <tr class="fltrTblHdng">

            <s:if test="asrErrorListSize != 0">
                <td rowspan="2">Errors</td>
            </s:if>

            <td rowspan="2">
                Prod<br/>Orig
            </td>

            <td rowspan="2">
                Rpt<br/>Aff
            </td>

            <td rowspan="2">
                Inv<br/>Cd
            </td>

            <td colspan="4">
                Rpt Prod
            </td>

            <td rowspan="2">
                Supp<br/>Aff
            </td>

            <td rowspan="2">
                Inv<br/>Cd
            </td>

            <td colspan="4">
                Sup Prod
            </td>

            <td rowspan="2">
                Usage<br/>Factor
            </td>

            <td rowspan="2">
                Sup<br/>Key
            </td>

        </tr>


        <tr class="fltrTblHdng">

            <td>List</td>
            <td>Label</td>
            <td>Size</td>
            <td>Pack</td>

            <td>List</td>
            <td>Label</td>
            <td>Size</td>
            <td>Pack</td>

        </tr>


        <!-- Search object hidden fields -->

        <s:hidden name="searchObject.modelId"/>
        <s:hidden name="searchObject.datasetTableId"/>


        <tr class="oddRowCenter">

            <!-- Errors checkbox -->

            <s:if test="asrErrorListSize != 0">

                <td>

                    <input type="checkbox"
                           name="errs"
                           value="on"
                           onclick="changeCmdAndSubmit(
                               document.getElementById('asrForm'),
                               'filter'
                           );"/>

                </td>

            </s:if>


            <!-- Product Origin -->

            <td>

                <s:textfield
                    name="searchObject.productOrigin"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,1,event);"/>

            </td>


            <!-- Report Affiliate -->

            <td>

                <s:textfield
                    name="searchObject.rptAff"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,4,event);"
                    onblur="checkPadLeft(this,'0',4);"/>

            </td>


            <!-- ================================================= -->
            <!-- REPORT PRODUCT                                    -->
            <!-- ================================================= -->

            <td>

                <s:textfield
                    name="searchObject.rptProduct.invCode"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,1,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.rptProduct.list"
                    maxlength="6"
                    cssClass="fltrWidth6"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,6,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.rptProduct.label"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,3,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.rptProduct.size"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,3,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.rptProduct.pack"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,4,event);"/>

            </td>


            <!-- Supplier Affiliate -->

            <td>

                <s:textfield
                    name="searchObject.supAff"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,4,event);"
                    onblur="checkPadLeft(this,'0',4);"/>

            </td>


            <!-- ================================================= -->
            <!-- SUPPLIER PRODUCT                                  -->
            <!-- ================================================= -->

            <td>

                <s:textfield
                    name="searchObject.supProduct.invCode"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,1,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.supProduct.list"
                    maxlength="6"
                    cssClass="fltrWidth6"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,6,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.supProduct.label"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,3,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.supProduct.size"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,3,event);"/>

            </td>


            <td>

                <s:textfield
                    name="searchObject.supProduct.pack"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,4,event);"/>

            </td>


            <!-- Usage -->

            <td>

                <s:textfield
                    name="searchObject.usage"
                    maxlength="16"
                    cssClass="fltrWidth15"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onblur="alertLength(this,10);"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"
                    onkeyup="return autoTab(this,16,event);"/>

            </td>


            <!-- Supplier Key -->

            <td>

                <s:textfield
                    name="searchObject.supKey"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeFilterDirty('pagingDiv','red','bold');"
                    onkeydown="submitFilter(
                        document.getElementById('asrForm'),
                        'filter',
                        event
                    );"/>

            </td>

        </tr>


        <!-- ===================================================== -->
        <!-- FILTER BUTTONS                                        -->
        <!-- ===================================================== -->

        <tr class="evenRowCenter">
					<td colspan="15" class="right">
						<a href="javascript:changeCmdAndSubmit(document.asrForm,'filter');" >
							<img src="images/btnFilter.png" alt="Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.asrForm,'advancedfilter');" >
							<img src="images/btnAdvancedFilter.png" alt="Advanced Filter" /></a>
						<a href="javascript:changeCmdAndSubmit(document.asrForm,'clearfilter');" >
							<img src="images/btnClear.png" alt="Clear Filter" /></a>
					</td>
				</tr>

    </table>

    <hr/>


    <!-- ========================================================= -->
    <!-- ADD / MASS UPDATE                                         -->
    <!-- ========================================================= -->

    <a id="AddMassUpdateView"
       name="AddMassUpdateView"></a>

    <table width="780"
           cellspacing="0">

        <tr class="fltrTblHdng">

            <td rowspan="2">
                Act<br/>Code
            </td>

            <td rowspan="2">
                Prod<br/>Orig
            </td>

            <td rowspan="2">
                Rpt<br/>Aff
            </td>

            <td rowspan="2">
                Inv<br/>Cd
            </td>

            <td colspan="4">
                Rpt Prod
            </td>

            <td rowspan="2">
                Supp<br/>Aff
            </td>

            <td rowspan="2">
                Inv<br/>Cd
            </td>

            <td colspan="4">
                Sup Prod
            </td>

            <td rowspan="2">
                Usage<br/>Factor
            </td>

            <td rowspan="2">
                Sup<br/>Key
            </td>

        </tr>


        <tr class="fltrTblHdng">

            <td>List</td>
            <td>Label</td>
            <td>Size</td>
            <td>Pack</td>

            <td>List</td>
            <td>Label</td>
            <td>Size</td>
            <td>Pack</td>

        </tr>


        <s:hidden name="addNew.asr.modelId"/>
        <s:hidden name="addNew.asr.datasetTableId"/>


        <tr class="oddRowCenter">

            <!-- Action Code -->

            <td>

                <s:if test="addNew.asr.msg != null && addNew.asr.msg != ''">

                   <!--  #'
                       );"
                       onmouseout="hideMsgPopup();">

                        images/exclamation.png

                    </a> -->
                    
                    
                    
                    <a class="error"
								href="#"
								id="anchorAddNew"
								name="anchorAddNew"
								onclick="return false;"
								onmouseover="showMsgPopup('anchorAddNew', '<nested:write property="asr.msg" />');"
								onmouseout='hideMsgPopup();' >
								<img src="images/exclamation.png" />
							</a>

                </s:if>


                <s:textfield
                    name="addNew.actionCode"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="
                        submitAddNewSave(event);
                    "
                    onkeyup="return autoTab(this,1,event);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.productOrigin"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,1,event);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.rptAff"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,4,event);"
                    onblur="checkPadLeft(this,'0',4);"/>

            </td>


            <!-- RPT PRODUCT -->

            <td>

                <s:textfield
                    name="addNew.asr.rptProduct.invCode"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,1,event);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.rptProduct.list"
                    maxlength="6"
                    cssClass="fltrWidth6"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,6,event);"
                    onblur="checkPadLeft(this,'0',6);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.rptProduct.label"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,3,event);"
                    onblur="checkPadLeft(this,'0',3);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.rptProduct.size"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,3,event);"
                    onblur="checkPadLeft(this,'0',3);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.rptProduct.pack"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,4,event);"
                    onblur="checkPadLeft(this,'0',4);"/>

            </td>


            <!-- Supplier Affiliate -->

            <td>

                <s:textfield
                    name="addNew.asr.supAff"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,4,event);"
                    onblur="checkPadLeft(this,'0',4);"/>

            </td>


            <!-- Supplier Product -->

            <td>

                <s:textfield
                    name="addNew.asr.supProduct.invCode"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,1,event);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.supProduct.list"
                    maxlength="6"
                    cssClass="fltrWidth6"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,6,event);"
                    onblur="checkPadLeft(this,'0',6);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.supProduct.label"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,3,event);"
                    onblur="checkPadLeft(this,'0',3);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.supProduct.size"
                    maxlength="3"
                    cssClass="fltrWidth3"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,3,event);"
                    onblur="checkPadLeft(this,'0',3);"/>

            </td>


            <td>

                <s:textfield
                    name="addNew.asr.supProduct.pack"
                    maxlength="4"
                    cssClass="fltrWidth4"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onkeyup="return autoTab(this,4,event);"
                    onblur="checkPadLeft(this,'0',4);"/>

            </td>


            <!-- Usage -->

            <td>

                <s:textfield
                    name="addNew.asr.usage"
                    maxlength="16"
                    cssClass="fltrWidth16"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"
                    onblur="alertLength(this,10);"
                    onkeyup="return autoTab(this,16,event);"/>

            </td>


            <!-- Sup Key -->

            <td>

                <s:textfield
                    name="addNew.asr.supKey"
                    maxlength="1"
                    cssClass="fltrWidth1"
                    theme="simple"
                    onchange="makeAddNewDirty();"
                    onkeydown="submitAddNewSave(event);"/>

            </td>

        </tr>
        
        <tr>
        <td colspan="16" class="right">
					<a href="javascript:chgActCmdSubmit(document.asrForm,'save','asrSave.do');">
						<img src="images/btnSave.png" alt="Save"></a>
					<a href="javascript:chgActCmdSubmit(document.asrForm,'massupdate','asrSave.do');">
						<img src="images/btnMassUpdate.png" alt="Apply Changes to all records based on Filter criteria"></a>
					<a href="javascript:chgActCmdSubmit(document.asrForm,'clearaddnew','asrMaintenance.do');">
						<img src="images/btnClear.png" alt="Clear"></a>
				</td>
        </tr>


        <!--
            Recommended:
            calculate canEdit in the Struts Action rather than referencing
            Role.Query.getAccessLevel() directly from JSP.
        -->

        <s:if test="canEdit">

            <tr>

                <td colspan="16"
                    class="right">


                    <!-- SAVE -->

                    #                               'save',
                               'asrSave.action'
                           );
                           return false;
                       ">

                        images/btnSave.png

                    </a>


                    <!-- MASS UPDATE -->

                    #                               'massupdate',
                               'asrSave.action'
                           );
                           return false;
                       ">

                        images/btnMassUpdate.png

                    </a>


                    <!-- CLEAR -->

                    #                               'clearaddnew',
                               'asrMaintenance.action'
                           );
                           return false;
                       ">

                        images/btnClear.png

                    </a>

                </td>

            </tr>

        </s:if>

    </table>

    <hr/>


    <!-- ========================================================= -->
    <!-- TOP PAGING                                                -->
    <!-- ========================================================= -->

    <div id="navigationTop"
         class="hidden">

        <%@ include file="/include/asrPaging.jsf" %>

    </div>


    <!-- ========================================================= -->
    <!-- ASR LIST                                                  -->
    <!-- ========================================================= -->

    <table width="780" cellspacing="0" >


        <!-- ===================================================== -->
        <!-- FIRST HEADER ROW                                      -->
        <!-- ===================================================== -->

        <tr class="mntTblHdng">

            <td>&nbsp;</td>


            <!-- Product Origin -->

            <td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_PROD_ORIGIN%>');" >
						Prod<br>Orig<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_PROD_ORIGIN%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_AFF%>');" >
						Rpt<br>Aff<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_RPT_AFF%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_INV_CD%>');" >
						Inv<br>Cd<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_RPT_INV_CD%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>


            <td colspan="4">
                Rpt Prod
            </td>


            <!-- SUP AFF -->

            
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_AFF%>');" >
						Supp<br>Aff<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_SUP_AFF%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_INV_CD%>');" >
						Inv<br>Cd<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_SUP_INV_CD%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td colspan="4">
					Sup Prod
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_USAGE_FAC%>');" >
						Usage<br>Factor<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_USAGE_FAC%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td rowspan="2">
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_KEY%>');" >
						Sup<br>Key<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_SUP_KEY%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td rowspan="2" valign="middle">
					<input type="image" src="images/btnCheck.png" alt="Toggle Select All" onClick="return toggleSelectAll('asrListItem','selected','<%=asrForm.getAsrListSize()%>');" />
				</td>

        </tr>


        <!-- ===================================================== -->
        <!-- SECOND HEADER ROW                                     -->
        <!-- ===================================================== -->

        <tr class="mntTblHdng">

            <td>&nbsp;</td>

<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_LIST%>');" >
						List<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_RPT_LIST%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td> 	
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_LABEL%>');" >
						Label<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_RPT_LABEL%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_SIZE%>');" >
						Size<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_RPT_SIZE%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_RPT_PACK%>');" >
						Pack<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_RPT_PACK%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_LIST%>');" >
						List<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_SUP_LIST%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_LABEL%>');" >
						Label<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_SUP_LABEL%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_SIZE%>');" >
						Size<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_SUP_SIZE%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
				<td>
					<a class="mntSort"
						href="javascript:chgSrtSubEbcdic(document.asrForm,'<%=DBConst.COL_SUP_PACK%>');" >
						Pack<br>
						<c:if test="${sortObject.sortColumn == '<%=DBConst.COL_SUP_PACK%>'}"> >
							<img alt="<%=asrForm.getSortObject().getSortImgAltTxt()%>" src="<%=asrForm.getSortObject().getSortImg()%>" align="center" />
						</c:if>
					</a>
				</td>
            

        </tr>

        <!-- ===================================================== -->
        <!-- DATA ROWS                                             -->
        <!-- ===================================================== -->
        
        
       

        <%-- <s:if test="asrListItem != null && !asrListItem.isEmpty()"> --%>
        
         <s:if test="asrListItem != null">

            <s:iterator value="asrListItem" var="asrBean" status="asrStatus">
            
                <tr class="<s:property value='#asrStatus.even ? "evenRow" : "oddRow"'/>"
                    id="mntRow<s:property value='#asrStatus.index'/>">


                    <!-- ================================================= -->
                    <!-- ROW NUMBER + COPY                                 -->
                    <!-- ================================================= -->

                     <td class="mntCenter">

                        <c:out value="${sessionScope.asrForm.pagingFilter.startRecord + asrStatus.index}"/>
							
							<a href="#" >
								<img src="images/btnUpArrow.png" alt="Load Row" />
							</a>

                    </td>
                    
							


                    <!-- ================================================= -->
                    <!-- PRODUCT ORIGIN                                    -->
                    <!-- ================================================= -->

                    <td class="mntCenter">

                        <s:if test="#asrBean.msg != null && #asrBean.msg != ''">

                           <%--  #"
                               onclick="return false;"
                               title="<s:property value='#asrBean.msg'/>">

                                images/exclamation.png

                            </a> --%>
                            
                            <a class="error"
									href="#"
									id="anchor<c:out value="${asrStatus.index}"/>"
									name="anchor<c:out value="${asrStatus.index}"/>"
									onclick="return false;"
									onmouseover="showMsgPopup('anchor<c:out value="${asrStatus.index}"/>', '<c:out value="${asrBean.msg}"/>');"
									onmouseout='hideMsgPopup();' >
									<img src="images/exclamation.png" />
								</a>

                        </s:if>


                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].productOrigin"
                            maxlength="1"
                            cssClass="mntWidth1"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,1,event);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- ================================================= -->
                    <!-- RPT AFF                                           -->
                    <!-- ================================================= -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].rptAff"
                            maxlength="4"
                            cssClass="mntWidth4"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,4,event);"
                            onblur="checkPadLeft(this,'0',4);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- ================================================= -->
                    <!-- RPT PRODUCT INV CODE                              -->
                    <!-- ================================================= -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].rptProduct.invCode"
                            maxlength="1"
                            cssClass="mntWidth1"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,1,event);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- RPT LIST -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].rptProduct.list"
                            maxlength="6"
                            cssClass="mntWidth6"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,6,event);"
                            onblur="checkPadLeft(this,'0',6);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- RPT LABEL -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].rptProduct.label"
                            maxlength="3"
                            cssClass="mntWidth3"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,3,event);"
                            onblur="checkPadLeft(this,'0',3);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- RPT SIZE -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].rptProduct.size"
                            maxlength="3"
                            cssClass="mntWidth3"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,3,event);"
                            onblur="checkPadLeft(this,'0',3);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- RPT PACK -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].rptProduct.pack"
                            maxlength="4"
                            cssClass="mntWidth4"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,4,event);"
                            onblur="checkPadLeft(this,'0',4);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- ================================================= -->
                    <!-- SUP AFF                                           -->
                    <!-- ================================================= -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].supAff"
                            maxlength="4"
                            cssClass="mntWidth4"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,4,event);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- ================================================= -->
                    <!-- SUP PRODUCT INV CODE                              -->
                    <!-- ================================================= -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].supProduct.invCode"
                            maxlength="1"
                            cssClass="mntWidth1"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,1,event);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- SUP LIST -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].supProduct.list"
                            maxlength="6"
                            cssClass="mntWidth6"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,6,event);"
                            onblur="checkPadLeft(this,'0',6);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- SUP LABEL -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].supProduct.label"
                            maxlength="3"
                            cssClass="mntWidth3"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,3,event);"
                            onblur="checkPadLeft(this,'0',3);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- SUP SIZE -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].supProduct.size"
                            maxlength="3"
                            cssClass="mntWidth3"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,3,event);"
                            onblur="checkPadLeft(this,'0',3);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- SUP PACK -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].supProduct.pack"
                            maxlength="4"
                            cssClass="mntWidth4"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeyup="return autoTab(this,4,event);"
                            onblur="checkPadLeft(this,'0',4);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- ================================================= -->
                    <!-- USAGE                                             -->
                    <!-- ================================================= -->

                    <td class="mntLeft">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].usage"
                            maxlength="16"
                            cssClass="mntWidth16"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onblur="alertLength(this,10);"
                            onkeyup="return autoTab(this,16,event);"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- ================================================= -->
                    <!-- SUP KEY                                           -->
                    <!-- ================================================= -->

                    <td class="mntCenter">

                        <s:textfield
                            name="asrListItem[%{#asrStatus.index}].supKey"
                            maxlength="1"
                            cssClass="mntWidth1"
                            theme="simple"
                            onchange="markRowDirty(%{#asrStatus.index});"
                            onkeydown="return restrSpace(event);"/>

                    </td>


                    <!-- ================================================= -->
                    <!-- SELECTED                                          -->
                    <!-- ================================================= -->

                    <td class="mntCenter">

                        <s:checkbox
                            name="asrListItem[%{#asrStatus.index}].selected"
                            cssClass="rowSelected"
                            fieldValue="true"
                            theme="simple"/>

                    </td>

                </tr>

            </s:iterator>


            <!-- ================================================= -->
            <!-- BOTTOM PAGING                                     -->
            <!-- ================================================= -->

            <tr>

                <td colspan="17">

                    <div id="navigationBottom">

                        <%@ include file="/include/asrPaging.jsf" %>

                    </div>

                </td>

            </tr>

        </s:if>

    </table>


    <!-- ========================================================= -->
    <!-- NO RECORDS                                                -->
    <!-- ========================================================= -->
    <s:if test="asrListItem == null || asrListItem.isEmpty()">

        <%@ include file="/include/recordsNotFound.jsf" %>

    </s:if>


    <hr/>

</s:form>


<!-- ============================================================= -->
<!-- JAVASCRIPT                                                    -->
<!-- ============================================================= -->

<script type="text/javascript">

    function getAsrForm() {
        return document.getElementById("asrForm");
    }


    /*
     * Replaces references such as:
     *
     * document.asrForm
     *
     * Using getElementById is more reliable.
     */


    function markRowDirty(index) {

        var form = getAsrForm();

        var fieldName =
            "asrListItem[" + index + "].selected";

        var field = form.elements[fieldName];

        if (field) {

            /*
             * Preserve the behavior of the old
             * makeEditDirty(...) function.
             */

            makeEditDirty(fieldName);
        }
    }


    /*
     * Enter on Add/Mass Update row.
     *
     * canEdit should be exposed by the Action.
     */

    function submitAddNewSave(event) {

        if (!event) {
            return true;
        }

        <s:if test="canEdit">

        submitSave(
            getAsrForm(),
            'save',
            'asrSave.action',
            event
        );

        </s:if>

        return true;
    }


    /*
     * DELETE SELECTED / DELETE ALL
     */

    function doDelete(form, cmd, action) {

        var rowSelected = false;

        if (!form) {
            form = getAsrForm();
        }

        if (cmd === 'deleteall') {

            rowSelected = true;

        } else {

            var checkboxes =
                form.querySelectorAll(
                    'input[name^="asrListItem["][name$="].selected"]'
                );

            for (var i = 0; i < checkboxes.length; i++) {

                if (checkboxes[i].checked) {
                    rowSelected = true;
                    break;
                }
            }
        }


        if (rowSelected) {

            chgActCmdSubmit(
                form,
                cmd,
                action
            );

        } else {

            alert(
                'You must select at least one row to delete'
            );
        }

        return false;
    }


    /*
     * SAVE SELECTED
     */

    function checkSave(form, cmd, action) {

        var rowSelected = false;

        if (!form) {
            form = getAsrForm();
        }

        var checkboxes =
            form.querySelectorAll(
                'input[name^="asrListItem["][name$="].selected"]'
            );


        for (var i = 0; i < checkboxes.length; i++) {

            if (checkboxes[i].checked) {

                rowSelected = true;
                break;
            }
        }


        if (rowSelected) {

            chgActCmdSubmit(
                form,
                cmd,
                action
            );

        } else {

            alert(
                'You must select at least one row to Save Selected'
            );
        }

        return false;
    }


    /*
     * TOGGLE SELECT ALL
     */

    function toggleSelectAllStruts2() {

        var form = getAsrForm();

        var checkboxes =
            form.querySelectorAll(
                'input[name^="asrListItem["][name$="].selected"]'
            );


        var selectAll = false;


        for (var i = 0; i < checkboxes.length; i++) {

            if (!checkboxes[i].checked) {

                selectAll = true;
                break;
            }
        }


        for (var j = 0; j < checkboxes.length; j++) {

            checkboxes[j].checked = selectAll;
        }

        return false;
    }


    /*
     * PREVENT SPACE
     */

    function restrSpace(event) {

        if (!event) {
            return true;
        }

        if (event.key === ' ' ||
            event.keyCode === 32) {

            event.preventDefault();

            return false;
        }

        return true;
    }


    /*
     * SORT
     *
     * Used by the second-level table headers.
     */

    function sortAsr(column) {

        chgSrtSubEbcdic(
            getAsrForm(),
            column
        );

        return false;
    }


    /*
     * Initial page setup
     */

    document.addEventListener(
        "DOMContentLoaded",
        function () {

            var topNavigation =
                document.getElementById(
                    "navigationTop"
                );

            if (topNavigation) {

                topNavigation.style.display =
                    "block";
            }


            var focusField =
                '<s:property value="focusField" escapeJavaScript="true"/>';


            if (focusField) {

                setFocusReposition(
                    focusField
                );
            }
        }
    );

</script>


<%@ include file="/include/footer.jsf" %>

</body>