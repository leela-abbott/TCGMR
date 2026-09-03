// JavaScript Document
var isNN = (navigator.appName.indexOf("Netscape") != -1);
var isIE = (navigator.appName.indexOf("Microsoft") != -1);

// Autotab functionality ---- Begin
function autoTab(input,len, e)
{
	var keyCode = (isNN) ? e.which : e.keyCode;
	var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];
	if(input.value.length >= len && !containsElement(filter,keyCode))
	{
		input.value = input.value.slice(0, len);
		input.onchange();
		input.form[(getIndex(input)+1) % input.form.length].focus();
		input.form[(getIndex(input)+1) % input.form.length].select();
	}
	return true;
}
function containsElement(arr, ele)
{
	var found = false, index = 0;
	while(!found && index < arr.length)
		if(arr[index] == ele)
			found = true;
	else
		index++;
	return found;
}
function getIndex(input)
{
	var index = -1, i = 0, found = false;
	while (i < input.form.length && index == -1)
		if (input.form[i] == input)
			index = i;
	else
		i++;
	return index;
}
// Autotab functionality --- End

function submitFilter(form, action, event)
{
		//alert("with in submitFilter");
		//alert(event.keyCode);
		if(event.keyCode == 13){
		//alert(event.keyCode);
		changeCmdAndSubmit(form,action);
		}else{
		return false;
		}
}
function submitSave(form, cmd, action, event)
{
		//alert("with in submitSave");
		//alert(event.keyCode);
		if(event.keyCode == 13){
		//alert(event.keyCode);
		//chgActCmdSubmit(form,cmd,action);
		event.keyCode = 9; 
		}
		else if(event.keyCode == 32){
		event.returnValue = false;
		return false;
		}
		else{
		return false;
		}
}
function submitAdd(form, cmd, action, event)
{
		if(event.keyCode == 13){
		//chgActCmdSubmit(form,cmd,action);
		event.keyCode = 9; 
		}
		else if(event.keyCode == 32){
		event.returnValue = false;
		return false;
		}
		else{
		return false;
		}
}
function getRandomString(slen) {
    alert("Howdy from Random");
    var k = Math.random();
    k = k.toString();
    k = k.substring( 2, k.length );
    alert("Random String: " + k);
    if (k.length > slen)
        return k.substring(0, slen);
    else
        return k
}

function viewReport(sReportId)
{
// This was previously used for Crystal Reports  - bd 8/25/05
	var sWindowParms;
	sWindowParms = "height=550,width=750,scrollbars=yes,resizable";
	newWindow = window.open("viewReport.do?reportId=" + sReportId, "TCGMReportViewer", sWindowParms);
	newWindow.focus();
}

function viewReportNetReports(reportsUrl)
{

	var sWindowParms;
	sWindowParms = "height=550,width=750,scrollbars=yes,resizable";
	newWindow = window.open(reportsUrl);
	newWindow.focus();
}

function notComplete()
{
	alert("This functionality is not yet ready.");
}
//------------Begin code to capture enter key and convert to tab to next field ---------------------------------//
//-------------------------------------------------------------------
// filterReturnKey()
//   detect <return> keycode and tab to the next input field
//-------------------------------------------------------------------
function filterReturnKey(e)
{
	if (isIE)
	{
		if (event.keyCode == 13)
		{
			event.keyCode = 9;
			tabNext(event.srcElement);
		}
	}
	else if (isNN)
	{
		if (e.which == 13)
		{
			e.which = 9;
			tabNext(e.target);
		}
	}
	return true;
}
//-------------------------------------------------------------------
// getElementIndex(input_object)
//   Pass an input object, returns index in form.elements[] for the object
//   Returns -1 if error
//-------------------------------------------------------------------
function getElementIndex(obj)
{
	var theform = obj.form;
	for (var i=0; i<theform.elements.length; i++)
	{
		if (obj.name == theform.elements[i].name)
		{
		return i;
	}
	}
	return -1;
}
// -------------------------------------------------------------------
// tabNext(input_object)
//   Pass an form input object. Will focus() the next field in the form
//   after the passed element.
//   a) Will not focus to hidden or disabled fields
//   b) If end of form is reached, it will loop to beginning
//   c) If it loops through and reaches the original field again without
//      finding a valid field to focus, it stops
// -------------------------------------------------------------------
function tabNext(obj)
{
	if(navigator.platform.toUpperCase().indexOf("SUNOS") != -1)
	{
		obj.blur();
		return; // Sun's onFocus() is messed up
	}
	var theform = obj.form;
	var i = getElementIndex(obj);
	var j=i+1;
	if (j >= theform.elements.length)
	{
		j=0;
	}
	if (i == -1)
	{
		return;
	}
	while (j != i)
	{
		if ((theform.elements[j].type!="hidden") &&
			(theform.elements[j].name != theform.elements[i].name) &&
			(!theform.elements[j].disabled))
				{
			theform.elements[j].focus();
			break;
		}
		j++;
		if (j >= theform.elements.length)
		{
			j=0;
		}
	}
}
//------------End code to capture enter key and convert to tab to next field ---------------------------------//

var filterDirtyFlag = false;  // set to true if the user changes a value in one of the filter fields.
var editDirtyFlag = false;  // set to true if the user changes a value in the edit portion of a page.
var addNewDirtyFlag = false; // set to true if the user types a value into the row for adding a new record
/**
 * If the user clicks the next or prev page buttons and the dirty flag is true then
 * the hidden field with the startRecord should be set back to 1 so that
 * the page displays correctly.
 */
function resetStartRecord(form)
{
	if(filterDirtyFlag == true)
	{
		alert("The filter criteria specified has been changed since the last fetch.  A new query will be executed and you will be returned to row 1");
		form.pagingFilter.startRecord = 1;
	}
}

function deleteDataset(se, action)
{
	if (se.value == 0)
	{
	 alert ("You must select a dataset to delete.");
	 return;
   }
   var datasetName = getSelectedOptionText(se);
   if ( confirm("Are you sure that you would like to delete " + datasetName + "?\nThis will delete all attributes and can not be undone.") )
	{
	   changeActionAndSubmit(se.form, action);
   }
}

function saveDataset(se, action)
{
	if (se.value == 0)
	{
	 alert ("You must select a dataset to save.");
	 return;
   }
   var datasetName = getSelectedOptionText(se);
   if ( confirm("Are you sure that you would like to save " + datasetName + "?\nThis will save all attributes and can not be undone.") )
	{
	   changeActionAndSubmit(se.form, action);
   }
}

/**
*
*/
function copyRow(form,rowNumber,action)
{
	form.rowToCopy.value = rowNumber;
	form.action = action;
	form.cmd.value = 'copyrow';
	form.submit();
}

/**
 * Call this method from the onclick event using something like this...
 * onclick="changeActionAndSubmit(document.asrForm,'asrMaintenance.do');"
 */
function changeActionAndSubmit(form,action)
{
	form.action = action;
	form.submit();
}
/**
 * Call this method from the onclick event using something like this...
 * onclick="changeCmdAndSubmit(document.asrForm,'fetch');"
 */
function changeCmdAndSubmit(form,cmd)
{
	form.cmd.value = cmd;
	//alert("with in changeCmdAndSubmit");
	form.submit();
}
/**
 * Call this method when you need to change the cmd value on the form as well as the form action
 */
function chgActCmdSubmit(form,cmd,action)
{
	form.cmd.value = cmd;
	form.action = action;
	//alert("with in chgActCmdSubmit");
	form.submit();
}
/**
 * Call this method when you need to change the job value on the form as well as the form action
 */
function chgActJobSubmit(form,jobName,action)
{
	form.jobName.value = jobName;
	form.action = action;
	form.submit();
}
//Sridevi.K Scripts added alert the user and remove the selected model.

/**
 * Call this function when you need Alert the user if he enters data crossing the limit
 */
function checkLength(input,len)
{
	if(input.value.length >= len )
	{
		input.value = input.value.slice(0, len);		
	}
	return true;
}
function removeOption(selectElement, removeOption )
{   
	//Create an array for storing the required options
	var optionsArray = new Array();
		
	//Loop thru the select options and add the required options to the array
	for( var len = 0; len < selectElement.options.length; len++)
	{
		//Check if the current option doesn't have the same text or value as removeOption
		if( removeOption != selectElement.options[len].text &&
		    removeOption != selectElement.options[len].value  )
		{
			//Create new Option with for the current option to add to the array
			var addOption = new Option(selectElement.options[len].text,selectElement.options[len].value);
                        
			//If the current option is selected make the addOption also selected
			if( selectElement.options[len].selected == true )
			{
				addOption.selected=true;
            }		
            //add the new option to array
			optionsArray.push(addOption);
		}				
	}
	//Loop thru the selectElement Options and remove them
	for( var len = 0; len < selectElement.options.length; len++)
	{
		selectElement.options[len] = null;
	}
	
        //Add the options in the array to the selectElement
	for( var len = 0; len < optionsArray.length; len++)
	{
		selectElement.options[len]=optionsArray[len];			
	}
 }
 
        
/**
 * Call this function when you need Alert the user if he enters data crossing the limit
 */
function alertLength(input,len) {

	var ind = input.value.indexOf(".");		
	if( (ind > len) || (ind == -1 && input.value.length > len) ) {

		alert('No.of digits before the decimal must not be more than'+ len );
		input.focus();
	}						
}


/**
 * Call this function when you need Alert the user if he leaves the column empty
 */
function emptyAlert(field, input)
{
	if(input.value.trim() == "" )
	{
		alert( field + " cannot be a empty or null!");
		return false;
	}
	else {
		return true ;
	}
}

//Sridevi.K End of changes.

/**
 * Call this method from the onclick event of the column headings to change the sort order and submit the form
 */
function changeSortAndSubmit(form,elementId,sortColumn)
{
	document.getElementById(elementId).value = sortColumn;
	form.submit();
}

/**
*
*/
function chgUserAndSubmit(form,user,action,cmd,elementId)
{
	document.getElementById('searchObject.userid').value = user;
	document.getElementById('userToEdit.userInfoId').value = '';
	document.getElementById('userToEdit.userid').value = '';
	document.getElementById('userToEdit.firstName').value = '';
	document.getElementById('userToEdit.lastName').value = '';
	document.getElementById('userToEdit.phone').value = '';
	document.getElementById('userToEdit.email').value = '';

	chgActCmdSubmit(form,cmd,action);
}

/**
*
*/
function chgAffAndSubmit(form,aff,action,cmd,elementId)
{
	document.getElementById('searchObject.aff').value = aff;
	document.getElementById('affCstCurToEdit.newAffCstCur').value = 'false';
	document.getElementById('affCstCurToEdit.aff').value = '';
	document.getElementById('affCstCurToEdit.affDesc').value = '';
	document.getElementById('affCstCurToEdit.curCode').value = '';

	chgActCmdSubmit(form,cmd,action);
}

/**
*
*/
function chgNotesCodeAndSubmit(form,notesCode,action,cmd,elementId)
{
	document.getElementById('searchObject.notesCode').value = notesCode;
	document.getElementById('notesToEdit.newNotes').value = 'false';
	document.getElementById('notesToEdit.notesCode').value = '';
	document.getElementById('notesToEdit.notesName').value = '';

	chgActCmdSubmit(form,cmd,action);
}

/**
*
*/
function chgCurCodeAndSubmit(form,curCode,action,cmd,elementId)
{
	document.getElementById('searchObject.curCode').value = curCode;
	document.getElementById('currencyCodeToEdit.newCurrencyCode').value = 'false';
	document.getElementById('currencyCodeToEdit.curCode').value = '';
	document.getElementById('currencyCodeToEdit.curName').value = '';

	chgActCmdSubmit(form,cmd,action);
}

var SORT_ASC = "ASC";
var SORT_DESC = "DESC";
/**
 * This method will check the values stored in the hidden fields
 * "sortObject.sortColumn" and "sortObject.sortOrder"
 * If the column will be changed, then set the sortOrder to ascending
 * If the column will not be changing then just toggle the sortOrder
 */
function chgSrtSub(form,newSortColumn)
{
	var currentSortColumn = document.getElementById('sortObject.sortColumn');
	var currentSortOrder = document.getElementById('sortObject.sortOrder');
	var newSortOrder = SORT_ASC;

	//if sorting on the same column then toggle the sortOrder
	if(newSortColumn == currentSortColumn.value)
	{
		if(currentSortOrder.value == SORT_ASC)
		{
			newSortOrder = SORT_DESC;
		}
		else
		{
			newSortOrder = SORT_ASC;
		}
	}

	currentSortColumn.value = newSortColumn;
	currentSortOrder.value = newSortOrder;

	form.submit();
}
/**
 * This method will check the values stored in the hidden fields
 * "sortObject.sortColumn" and "sortObject.sortOrder"
 * If the column will be changed, then set the sortOrder to ascending
 * If the column will not be changing then just toggle the sortOrder
 */
function chgSrtSubEbcdic(form,newSortColumn)
{
	var currentSortColumn = document.getElementById('sortObject.sortColumn');
	var currentSortOrder = document.getElementById('sortObject.sortOrder');
	var newSortOrder = SORT_ASC;

	//if sorting on the same column then toggle the sortOrder
	if(newSortColumn == currentSortColumn.value)
	{
		if(currentSortOrder.value == SORT_ASC)
		{
			newSortOrder = SORT_DESC;
		}
		else
		{
			newSortOrder = SORT_ASC;
			newSortColumn = "DEF";
		}
	}

	currentSortColumn.value = newSortColumn;
	currentSortOrder.value = newSortOrder;

	form.submit();
}
/**
 * Called by the onclick event of the next and previous page buttons
 */
function changePage(form,action)
{
	resetStartRecord(form);
	changeActionAndSumbit(form,action);
}
/**
 * This method sets the dirty flag to true and then sets the foreground color and font weight of the paging display
 */
function makeFilterDirty(elementId,fgColor,fontWeight)
{
	filterDirtyFlag = true;
	changeFGColor(elementId,fgColor);
	changeFontWeight(elementId,fontWeight);
}
		
/**
 * This method just sets the editDirtyFlag to true
 * It is used by the transaction pages.
 */
function makeEditDirty(chkbxElementId)
{
	editDirtyFlag = true;

	 //hide objects
	 hideObj('copySelDiv');
	 hideObj('delSelDiv');
	 hideObj('pubSelDiv');
	 hideObj('copyAllDiv');
	 hideObj('delAllDiv');
	 hideObj('pubAllDiv');
	 hideObj('modelDsp');

	 showObj('cancelDiv');
	 showObj('saveDiv');

 /* Hide the next and previous page buttons*/
	hideObj('prevPageDiv');
	hideObj('nextPageDiv');

	setSelected(chkbxElementId);
}
/**
 * This method just sets the addNewDirtyFlag to true
 */
function makeAddNewDirty()
{
	addNewDirtyFlag = true;
}
/**
 * Changes the foreground color of the element specified.  The object must be assigned an id.
 */
function changeFGColor(elementId,color)
{
	document.getElementById(elementId).style.color=color;
}
/**
 * Changes the foreground weight of the element specified.  The object must be assigned an id.
 */
function changeFontWeight(elementId,weight)
{
	document.getElementById(elementId).style.fontWeight=weight;
}
/**
 * Changes the background color of the element specified.  The object must be assigned an id.
 */
function changeBGColor(elementId,color)
{
	document.getElementById(elementId).style.backgroundColor=color;
}
/**
 * Sets the focus to an object identified by elementId.
 * This method cannot be called from the onLoad event because it does not perform consistently.
 * This method also does not perform on a screen refresh because the refresh moves the cursor back
 * to the location it was in prior to the refresh.
 */
function setFocus(elementId)
{
	document.getElementById(elementId).focus();
}
/**
 * Sets the focus to an object identified by elementId and repostions the page to a hyperlink.
 * This method cannot be called from the onLoad event because it does not perform consistently.
 * This method also does not perform on a screen refresh because the refresh moves the cursor back
 * to the location it was in prior to the refresh.
 */
function setFocusReposition(elementId)
{

	if((elementId == 'asrListItem[0].productOrigin')     ||
	   (elementId == 'asrTranListItem[0].actionCode')    ||
	   (elementId == 'bpcsListItem[0].rptAff')       ||
	   (elementId == 'bpcsTranListItem[0].actionCode')   ||
	   (elementId == 'bpcRevListItem[0].rptAff')     ||
	   (elementId == 'bpcRevTranListItem[0].actionCode') ||
	   (elementId == 'notesListItem[0].rptAff')     ||
	   (elementId == 'notesTranListItem[0].actionCode') ||		   
	   (elementId == 'rateExListItem[0].endAff')     ||
	   (elementId == 'rateExTranListItem[0].actionCode') ||	   
	   (elementId == 'bpcExListItem[0].rptAff')      ||
	   (elementId == 'bpcExTranListItem[0].actionCode'))
	{
		document.location.href="#ChangeMultipleRowView"
	}
	if((elementId == 'searchObject.productOrigin')  ||
	  (elementId == 'searchObject.endAff') ||
	  (elementId == 'searchObject.rptAff') ||
	  (elementId == 'searchObject.actionCode')) 
	{
		document.location.href="#FilterView"
	}
	if(elementId == 'addNew.actionCode')
	{
		document.location.href="#FilterView"
	}

	document.getElementById(elementId).focus();
}
/**
 * if filterDirtyFlag is true then set the startRecord to 1 and display a message
 *
 */
function checkFilterDirtyFlag(form,action)
{
	if (filterDirtyFlag)
	{
		action = "";
		document.getElementById('pagingFilter.startRecord').value = 1;
		alert("The filter criteria specified has changed since the last fetch.  Clicking this button will retrieve a new set of data and position the page at record 1");
	}
	changeCmdAndSubmit(form,action);
}

var selectAll = false;

/**
 * Sets the value of checkboxex in the list to on or off
 */
function toggleSelectAll(listObjectName,propertyName,listSize)
{
	var cmd = "";

	if(selectAll)
	{
		selectAll = false;
	}
	else
	{
		selectAll = true;
	}

	for(i = 0; i < listSize; i++)
	{
		var elementId = listObjectName + "[" + i + "]." + propertyName;
		document.getElementById(elementId).checked = selectAll;
	}

	return false;
}

/**
 * Given the element id of a checkbox, sets the box to be checked.
 */
function setSelected(elementId)
{
	document.getElementById(elementId).checked = true;
}

function getSelectedOptionText(selectObject)
{
	return selectObject.options[selectObject.selectedIndex].text
}

function optionExists(selList, sName)
{
	for ( var i=0; i< selList.options.length; i++ )
	{
		if (selList.options[i].text == sName)
			return true;
   }
   return false;
}

/**
* Acepts an element id or an object reference
* Sets the class of the given element to hidden
* The hidden style is defined in master.css
*/
function hideObj(obj)
{
	//If the obj is a string then it is an element id and we need to get the object associated with it
	if(typeof obj == "string")
	{
		obj = document.getElementById(obj);
	}

	 if(obj != null)
	 {
		obj.className = "hidden";
	 }
}
/**
 * Acepts an element id or an object reference
 * Sets the class of the given element to visible
 * The visible style is defineed in master.css
 */
function showObj(obj)
{
	//If the obj is a string then it is an element id and we need to get the object associated with it
   if(typeof obj == "string")
   {
		obj = document.getElementById(obj);
	}

	if(obj != null)
	{
		obj.className="visible";
	}
}

        /********************** BEGIN TRIMMING FUNCTIONS *******************************************************************/
        /**
         * Include all methods and class definitions to make this functional.
         * In using the keyword this in each of these methods, we are anticipating the fact that the method will be added
         * to the String object, in which case this represents the object.  Do not call the methods directly as a regular method.
         * Call them from a string object.
         * To complete our solution, all we need to do is to add the methods to the String object via the prototype property as follows:
         */
        function strTrimLeft()
        {
            //Match spaces at beginning of text and replace with a null string
            return this.replace(/^\s+/,'');
        }
        function strTrimRight()
        {
            //Match spaces at end of text and replace with a null string
            return this.replace(/\s+$/,'');
        }
        function strTrim()
        {
            //Match spaces at beginning and end of text and replace with null strings
            return this.replace(/^\s+/,'').replace(/\s+$/,'');
        }
        String.prototype.trimLeft = strTrimLeft;
        String.prototype.trimRight = strTrimRight;
        String.prototype.trim = strTrim;
        /********************** END TRIMMING FUNCTIONS *******************************************************************/
        /********************** PADDING FUNCTIONS *******************************************************************/

        /**
         * IMPORTANT!!!!:  In order for the padding functions to work the trimming functions defined above must also be available.
         */

        /**
         * If the object passed in has a value that is not blank then pad with the specified char.
         */
        function checkPadRight(object,padChar,padLength)
        {
            if(object.value.trim() != "")
            {
                object.value = padRight(object.value,padChar,padLength);
            }
        }
        /**
         * If the object passed in has a value that is not blank then pad with the specified char.
         */
        function checkPadLeft(object,padChar,padLength)
        {
            if(object.value.trim() != "")
            {
                object.value = padLeft(object.value,padChar,padLength);
            }
        }
        /**
         * Right pads a string.  Calls pad to do the actual work
         * @param stringToPad The string that should be padded with the padChar
         * @param padChar The character to pad the string with
         * @param padLength Represents the ending length of the string after padding
         * @return padded string
         */
        function padRight(stringToPad, padChar, padLength)
        {
            return pad(stringToPad,padChar,padLength,false);
        }
        /**
         * Left pads a string.  Calls pad to do the actual work
         * @param stringToPad The string that should be padded with the padChar
         * @param padChar The character to pad the string with
         * @param padLength Represents the ending length of the string after padding
         * @return padded string
         */
        function padLeft(stringToPad,padChar,padLength)
        {
            return pad(stringToPad,padChar,padLength,true);
        }
        /**
         * This function will pad the given string with the specified character until it reaches the given length.<br>
         * If the stringToPad is null the method returns null<br>
         * If the padChar is null the method returns the untouched stringToPad<br>
         * If the padLength - stringToPad.length < 1 the method returns the untouched stringToPad<br>
         * If the padChar.length is > 1 the method throws a TCGMException<br>
         *
         * @param stringToPad The string that should be padded with the padChar
         * @param padChar The character to pad the string with
         * @param padLength Represents the ending length of the string after padding
         * @param padLeft Boolean to indicate if padding should be left or right.
         * @return padded string
         */
        function pad(stringToPad, padChar, padLength, padLeft)
        {
            var returnVal = null;
            if(stringToPad != null)
            {
                returnVal = stringToPad;
                var stringToPadLength = stringToPad.length;
                var lengthDifference = padLength - stringToPadLength;
                if(padChar != null && lengthDifference > 0)
                {
                    if (padChar.length != 1)
                    {
                        alert("Invalid Pad Character:" +"'"+ padChar +"'.  " + "A single character must be passed in");
                    }
                    else
                    {
                        for (var i=0;i < lengthDifference ;i++)
                        {
                            if(padLeft)
                            {
                            returnVal = padChar + returnVal;
                        }
                        else
                        {
                            returnVal = returnVal + padChar;
                        }
                        }
                    }
                }
            }
            return returnVal;
        }


        function addJob(myForm, jobName) {
            myForm.cmd.value = 'ADD_JOB';
            myForm.jobName.value = jobName;
            myForm.submit();
        }
       function confirmAndAddJob(myForm, jobName) {
        if ( confirm("Do you really want to export details ?") ) 
        {
            myForm.cmd.value = 'ADD_JOB';
            myForm.jobName.value = jobName;
            myForm.submit();
        }
        }
        function submitRestrictions(myForm) {
            myForm.restrictionEntered.checked=true;
            myForm.action=myForm.formHandler.value;
            myForm.submit();
			}

		function cancelAddJob(myForm) {
			myForm.cmd.value= 'CANCEL';
			myForm.action=myForm.formHandler.value;
			myForm.submit();
			}
  /**
         * This function validates the form data (Create Analysis, Perpetual, Cost Exchange Forms).<br>
         * Checking the Memo field consists of Single Quote or not.<br>
         */			
function validateFormData(form,cmd){
	 var memo = form.memo.value;
	 if(validateSingleQuote(memo)== 'true'){
 		  changeCmdAndSubmit(form,cmd);
	  }else{
 		  alert('In Memo should not contain special character Single Quote (\').');
	      form.memo.focus();
	  }
 }
 
function validateSingleQuote(varValue){
	 var len = varValue.length;
	 var blnValue = 'true';
	 for(i=0;i<len;i++){
 		if(varValue.charAt(i)== '\''){
 			blnValue = 'false';
 			break;
 		}
 	}
 	return blnValue;

 }
 function retrieveURL(url,nameOfFormToPost,select) {
	//alert('in retrieveURL');
    removeAllOptions(document.getElementById(select));
    //Do the Ajax call
    if (window.XMLHttpRequest) { // Non-IE browsers
    	//alert('non ie');
      req = new XMLHttpRequest();
     // req.onreadystatechange = processStateChange;
      try {
      	req.open("POST", url, false); //was get
      } catch (e) {
        alert("Problem Communicating with Server\n"+e);
      }
      	        
        req.setRequestHeader("Method", "POST "+url+" HTTP/1.1"); 
        
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded" );           
        
        req.send(null);        
        processStateChange(req,select);
    } else if (window.ActiveXObject) { // IE
          	//alert(' ie');
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
      	
        //req.onreadystatechange = processStateChange;
        req.open("POST", url, false);      
        
        req.setRequestHeader("Method", "POST "+url+" HTTP/1.1"); 
        
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded" );           
        
        req.send(null);        
        processStateChange(req,select);
      }
    }
  }
 function processStateChange(req,select) {  

  	  if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response

        if(req.responseText!=''){
        var v = document.getElementById(select);
        
		var countries=req.responseText.split("*");
		removeAllOptions(v);
		for(var i=0; i < countries.length;++i){
		var valarray=countries[i].split(",");		
			addOption(document.getElementById(select), valarray[1], valarray[0]);			
			
		}
		}
        
      } else {
        alert("Problem with server response:\n " + req.statusText);
      }
    }
  } 
function addOption(selectbox,text,value)
{
	var optn = document.createElement("OPTION");
	optn.text = trim(text);
	optn.value = trim(value);
	selectbox.options.add(optn);
}
function removeAllOptions(selectbox)
{
	var i;
	if(selectbox.options.length!=-1){
		for(i=selectbox.options.length-1;i>=0;i--)
		{
			selectbox.remove(i);
		}
	}
}
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function retrieveURLWithOption(url,nameOfFormToPost,select) {
	//alert('in retrieveURL');
    removeAllOptions(document.getElementById(select));
    //Do the Ajax call
    if (window.XMLHttpRequest) { // Non-IE browsers
    	//alert('non ie');
      req = new XMLHttpRequest();
     // req.onreadystatechange = processStateChange;
      try {
      	req.open("POST", url, false); //was get
      } catch (e) {
        alert("Problem Communicating with Server\n"+e);
      }
      	        
        req.setRequestHeader("Method", "POST "+url+" HTTP/1.1"); 
        
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded" );           
        
        req.send(null);        
        processStateChangeOption(req,select);
    } else if (window.ActiveXObject) { // IE
          	//alert(' ie');
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
      	
        //req.onreadystatechange = processStateChange;
        req.open("POST", url, false);      
        
        req.setRequestHeader("Method", "POST "+url+" HTTP/1.1"); 
        
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded" );           
        
        req.send(null);        
        processStateChangeOption(req,select);
      }
    }
  }
 function processStateChangeOption(req,select) {  

  	  if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response

        if(req.responseText!=''){
        var v = document.getElementById(select);
        
		var countries=req.responseText.split("*");
		removeAllOptions(v);
		addOption(document.getElementById(select), "Select Affiliate", "-1");
		for(var i=0; i < countries.length;++i){
		var valarray=countries[i].split(",");		
			addOption(document.getElementById(select), valarray[1], valarray[0]);			
			
		}
		}
        
      } else {
        alert("Problem with server response:\n " + req.statusText);
      }
    }
  } 
function processActiveURL(url,select) {


    //Do the Ajax call
    if (window.XMLHttpRequest) { // Non-IE browsers
    	//alert('non ie');
      req = new XMLHttpRequest();
     // req.onreadystatechange = processStateChange;
      try {
      	req.open("POST", url, false); //was get
      } catch (e) {
        alert("Problem Communicating with Server\n"+e);
      }
      	        
        req.setRequestHeader("Method", "POST "+url+" HTTP/1.1"); 
        
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded" );           
        
        req.send(null);        
        processActVal(req,select);
    } else if (window.ActiveXObject) { // IE
          	//alert(' ie');
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
      	
        //req.onreadystatechange = processStateChange;
        req.open("POST", url, false);      
        
        req.setRequestHeader("Method", "POST "+url+" HTTP/1.1"); 
        
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded" );           
        
        req.send(null);        
        processActVal(req,select);
      }
    }
  }
 function processActVal(req,select) {  

  	  if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response
        if(req.responseText!=''){                      
        
		document.getElementById(select).innerHTML=req.responseText;
		}
        
      } else {
        alert("Problem with server response:\n " + req.statusText);
      }
    }
  } 