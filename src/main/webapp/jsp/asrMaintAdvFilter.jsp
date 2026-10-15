
<%! String pageTitle="Asr Data Advanced Filter";%>
<%@ include file="/include/header.jsf" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<%@ include file="/include/masthead.jsf" %>
	<%@ include file="/include/errorDisplay.jsf" %>
	<script type="text/javascript" src="include/common.js"></script>
	<s:form method="post" name="asrForm" action="asrMaintenance.action"  theme="simple">
		<s:hidden name="cmd" />
		<s:hidden name="focusField" />
		<s:hidden name="searchObject.modelId" />
		<s:hidden name="searchObject.datasetTableId" />
		<s:hidden name="searchObject.tranAdvFilter" value="true" />
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
					<a href="javascript:changeCmdAndSubmit(document.asrForm,'advFilter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
				</td>
			</tr>
			<!-- Product Origin -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Prod Orig</td>
				<td width="184">
					<s:textarea name="searchObject.productOrigin" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Rpt Aff -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Rpt Aff</td>
				<td width="184">
					<s:textarea name="searchObject.rptAff" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Rpt Product Header -->
			<tr>
				<td height="20" class="commandOption">Rpt Prod</td>
				<td height="20"></td>			  
				<td width="145" class="commandOption"></td>
				<td width="184"></td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Rpt Product - Inv Code -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Inv Code</td>
				<td width="184">
					<s:textarea name="searchObject.rptProduct.invCode" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Rpt Product - List -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">List</td>
				<td width="184">
					<s:textarea name="searchObject.rptProduct.list" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Rpt Product - Label -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Label</td>
				<td width="184">
					<s:textarea name="searchObject.rptProduct.label" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Rpt Product - Size -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Size</td>
				<td width="184">
					<s:textarea name="searchObject.rptProduct.size" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Rpt Product - Pack -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Pack</td>
				<td width="184">
					<s:textarea name="searchObject.rptProduct.pack" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Supp Aff -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Supp Aff</td>
				<td width="184">
					<s:textarea name="searchObject.supAff" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Sup Product Header -->
			<tr>
				<td height="20" class="commandOption">Sup Prod</td>
				<td height="20"></td>			  
				<td width="145" class="commandOption"></td>
				<td width="184"></td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Sup Product - Inv Code -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Inv Code</td>
				<td width="184">
					<s:textarea name="searchObject.supProduct.invCode" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Sup Product - List -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">List</td>
				<td width="184">
					<s:textarea name="searchObject.supProduct.list" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Sup Product - Label -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Label</td>
				<td width="184">
					<s:textarea name="searchObject.supProduct.label" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Sup Product - Size -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Size</td>
				<td width="184">
					<s:textarea name="searchObject.supProduct.size" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Sup Product - Pack -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Pack</td>
				<td width="184">
					<s:textarea name="searchObject.supProduct.pack" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Usage Factor -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Usage Factor</td>
				<td width="184">
					<s:textarea name="searchObject.usage" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Sup Key -->
			<tr>
				<td height="24"></td>
				<td height="24"></td>			  
				<td width="145" class="commandOption">Sup Key</td>
				<td width="184">
					<s:textarea name="searchObject.supKey" 
								rows="1" 
								cols="50" 
								cssClass="commandOption"
								theme="simple" />
				</td>
				<td width="142">&nbsp;</td>
			</tr>
			<!-- Filter Button (Bottom) -->
			<tr>
				<td height="23" class="right"></td>
				<td width="31" height="23" class="right"></td> 
				<td width="145" class="tableEntry"></td>
				<td class="tableEntry"></td>
				<td>
					<a href="javascript:changeCmdAndSubmit(document.asrForm,'advFilter');">
						<img src="images/btnFilter.png" alt="Filter" /></a>
				</td>
			</tr>
		</table>
	</s:form>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
<%@ include file="/include/footer.jsf" %>
</body>