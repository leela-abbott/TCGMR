<%! String pageTitle="Asr Tran Maintenance Advanced Filter";%>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:useBean id="TCGMUser" scope="session" type="abbott.ai.tcgm.entities.User" />
<%-- <jsp:useBean id="Role" scope="session" class="abbott.ai.tcgm.entities.Role" /> --%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <%@ include file="/include/masthead.jsf" %>
    <%@ include file="/include/errorDisplay.jsf" %>
    <s:form method="post" 
            name="asrTranForm" 
            action="asrTranMaintenance" 
            namespace="/" 
            theme="simple">
        <s:hidden name="cmd" />
        <s:hidden name="focusField" />
        <table width="690" cellspacing="1">
            <tr>
                <td width="86">&nbsp;</td>
                <td colspan="5" class="tableHeading">Enter Advanced Filter Values</td>
            </tr>
            <tr>
                <td height="23" class="right"></td>
                <td width="31" height="23" class="right"></td> 
                <td width="145" class="tableEntry">Restriction</td>
                <td class="tableEntry">Value</td>
                <td>
                    <a href="javascript:changeCmdAndSubmit(document.asrTranForm,'advFilter');">
                        <img src="images/btnFilter.png" alt="Filter" /></a>
                </td>
            </tr>
            <!-- Hidden Fields - CRITICAL: Use full path -->
            <s:hidden name="searchObject.tranAdvFilter" value="true" />
            <s:hidden name="searchObject.asr.modelId" />
            <s:hidden name="searchObject.asr.datasetTableId" />
            <!-- Action Code - CORRECT: searchObject.actionCode -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Act Code</td>
                <td width="184">  
                    <s:textarea name="searchObject.actionCode" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Product Origin - CORRECT: searchObject.asr.productOrigin -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Prod Orig</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.productOrigin" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Report Affiliate - CORRECT: searchObject.asr.rptAff -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Rpt Aff</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.rptAff" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Report Product Section Header -->
            <tr>
                <td height="20" class="commandOption">Rpt Prod</td>
                <td height="20"></td>			  
                <td width="145" class="commandOption"></td>
                <td width="184"></td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Report Product Fields - CORRECT: Full path -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Inv Code</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.rptProduct.invCode" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">List</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.rptProduct.list" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Label</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.rptProduct.label" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Size</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.rptProduct.size" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Pack</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.rptProduct.pack" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Supplier Affiliate - CRITICAL FIX: searchObject.asr.supAff -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Supp Aff</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.supAff" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Supplier Product Section Header -->
            <tr>
                <td height="20" class="commandOption">Sup Prod</td>
                <td height="20"></td>			  
                <td width="145" class="commandOption"></td>
                <td width="184"></td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Supplier Product Fields - CORRECT: Full path -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Inv Code</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.supProduct.invCode" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">List</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.supProduct.list" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Label</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.supProduct.label" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Size</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.supProduct.size" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Pack</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.supProduct.pack" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Usage Factor - CORRECT: searchObject.asr.usage -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Usage Factor</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.usage" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Sup Key - CORRECT: searchObject.asr.supKey -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Sup Key</td>
                <td width="184">  
                    <s:textarea name="searchObject.asr.supKey" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Publish Flag - CORRECT: searchObject.publishFlag -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">Pub Flag</td>
                <td width="184">  
                    <s:textarea name="searchObject.publishFlag" 
                                rows="1" 
                                cols="50" 
                                cssClass="commandOption"
                                theme="simple" />
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- User ID -->
            <tr>
                <td height="24"></td>
                <td height="24"></td>			  
                <td width="145" class="commandOption">User ID</td>
                <td width="184">
                    <abbott:securePage 
                        userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" 
                        requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" 
                        comparisonType="<">
                        <%=TCGMUser.getUserid()%>
                    </abbott:securePage>
                    <abbott:securePage 
                        userAccessLevel="<%=TCGMUser.getRole().getAccessLevel()%>" 
                        requiredAccessLevel="<%=Role.Analyst.getAccessLevel()%>" 
                        comparisonType=">=">
                        <s:select name="userSelected" 
                                  cssClass="commandOption"
                                  list="userlist"
                                  headerKey="ALL"
                                  headerValue="ALL"
                                  theme="simple" />
                    </abbott:securePage>
                </td>
                <td width="142">&nbsp;</td>
            </tr>
            <!-- Bottom Filter Button -->
            <tr>
                <td height="23" class="right"></td>
                <td width="31" height="23" class="right"></td> 
                <td width="145" class="tableEntry"></td>
                <td class="tableEntry"></td>
                <td>
                    <a href="javascript:changeCmdAndSubmit(document.asrTranForm,'advFilter');">
                        <img src="images/btnFilter.png" alt="Filter" /></a>
                </td>
            </tr>
        </table>
    </s:form>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
<%@ include file="/include/footer.jsf" %>
</body>