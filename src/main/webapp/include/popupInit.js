// ===================================================================
// This script is used to initialize the variables and objects needed
// to display the popup windows.  It needs to be included in every page
// that will display the popup windows.  It needs to be included AFTER
// including the AnchorPosition.js and the PopupWindow.js script files.
// ===================================================================

// Create PopupWindow objects
var msgPopup = new PopupWindow('msgPopupDiv');
msgPopup.offsetY = 0;
msgPopup.offsetX = 30;
msgPopup.autoHide();

function hideMsgPopup()
{
	msgPopup.hidePopup();
}
function showMsgPopup(anchorname, popuptext) 
{
	msgPopup.populate(popuptext);
	msgPopup.showPopup(anchorname);
}