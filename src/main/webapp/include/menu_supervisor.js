
//The following line is critical for menu operation, and MUST APPEAR ONLY ONCE. If you have more than one menu_array.js file rem out this line in subsequent files
menunum=0;menus=new Array();_d=document;function addmenu(){menunum++;menus[menunum]=menu;}function dumpmenus(){mt="<script language=javascript>";for(a=1;a<menus.length;a++){mt+=" menu"+a+"=menus["+a+"];"}mt+="<\/script>";_d.write(mt)}
//Please leave the above line intact. The above also needs to be enabled if it not already enabled unless this file is part of a multi pack.



////////////////////////////////////
// Editable properties START here //
////////////////////////////////////

if(navigator.appVersion.indexOf("MSIE 6.0")>0)
{
	effect = "Fade(duration=0.2);Shadow(color='#777777', Direction=135, Strength=5)"
}
else
{
	effect = "Shadow(color='#777777', Direction=135, Strength=5)" // Stop IE5.5 bug when using more than one filter
}


timegap=500				// The time delay for menus to remain visible
followspeed=5			// Follow Scrolling speed
followrate=40			// Follow Scrolling Rate
suboffset_top=0;		// Sub menu offset Top position
suboffset_left=-2;		// Sub menu offset Left position

style1=[				// style1 is an array of properties. You can have as many property arrays as you need. This means that menus can have their own style.
"0A4876",				// Mouse Off Font Color
"b4d8f4",				// Mouse Off Background Color
"0A4876",				// Mouse On Font Color
"FFDF31",				// Mouse On Background Color
"b4d8f4",				// Menu Border Color
11,						// Font Size in pixels
"normal",				// Font Style (italic or normal)
"bold",					// Font Weight (bold or normal)
"Arial",		// Font Name
3,						// Menu Item Padding
"images/arrow.gif",			// Sub Menu Image (Leave this blank if not needed)
,						// 3D Border & Separator bar
"66ffff",				// 3D High Color
"000099",				// 3D Low Color
"white",				// Current Page Item Font Color (leave this blank to disable)
"0A4876",				// Current Page Item Background Color (leave this blank to disable)
,			// Top Bar image (Leave this blank to disable)
"ffffff",				// Menu Header Font Color (Leave blank if headers are not needed)
"000099",				// Menu Header Background Color (Leave blank if headers are not needed)
]



addmenu(menu=[		// This is the array that contains your menu properties and details
"mainmenu",			// Menu Name - This is needed in order for the menu to be called
2,					// Menu Top - The Top position of the menu in pixels
75,				// Menu Left - The Left position of the menu in pixels
,					// Menu Width - Menus width in pixels
1,					// Menu Border Width
,					// Screen Position - here you can use "center;left;right;middle;top;bottom" or a combination of "center:middle"
style1,				// Properties Array - this is set higher up, as above
1,					// Always Visible - allows the menu item to be visible at all time (1=on/0=off)
"left",				// Alignment - sets the menu elements text alignment, values valid here are: left, right or center
,				// Filter - Text variable for setting transitional effects on menu activation - see above for more info
,					// Follow Scrolling - Tells the menu item to follow the user down the screen (visible at all times) (1=on/0=off)
1, 					// Horizontal Menu - Tells the menu to become horizontal instead of top to bottom style (1=on/0=off)
,					// Keep Alive - Keeps the menu visible until the user moves over another menu or clicks elsewhere on the page (1=on/0=off)
,					// Position of TOP sub image left:center:right
,					// Set the Overall Width of Horizontal Menu to 100% and height to the specified amount (Leave blank to disable)
,					// Right To Left - Used in Hebrew for example. (1=on/0=off)
,					// Open the Menus OnClick - leave blank for OnMouseover (1=on/0=off)
,		            // ID of the div you want to hide on MouseOver (useful for hiding form elements)
,					// Reserved for future use
,					// Reserved for future use
,					// Reserved for future use
,"Maintenance","show-menu=ModelMgmt",,"Manage Models",1 // "Description Text", "URL", "Alternate URL", "Status", "Separator Bar"
,"Production","show-menu=Production",,"",1
,"Job Que","show-menu=Jobs",,"",1
,"Import/Export","show-menu=ImportExport",,"",1
,"Admin","show-menu=Admin",,"",1
,"Main Menu","main.do",,"Main Menu",1 // "Description Text", "URL", "Alternate URL", "Status", "Separator Bar"
])

	addmenu(menu=["ModelMgmt",
	28,,120,1,"",style1,,"left",effect,,,,,,,,,,,,
	,"Maintenance","show-menu=ModelMaintenance",,,1
	,"Rate Models","show-menu=RateMgmt",,,1
	,"_______________", "",,,1
	,"Select Factor Model","mngFactorModels.do",,,1

	])
	addmenu(menu=["ModelMaintenance",
	,,100,1,"top",style1,,"left",effect,,,,,,,,,,,,
	,"ASR","show-menu=ASR",,,1
	,"BPC","show-menu=BPC",,,1
	,"BPC Rev","show-menu=BPC Rev",,,1
	,"BPC Exc","show-menu=BPC Exc",,,1
	,"Notes","show-menu=Notes",,,1
	])

	addmenu(menu=["ASR",
	,,100,1,"top",style1,,"left",effect,,,,,,,,,,,,
	,"Data","asrMaintenance.do",,,0
	,"Maintenance","asrTranMaintenance.do",,,0
	])

	addmenu(menu=["BPC",
	,,100,1,"top",style1,,"left",effect,,,,,,,,,,,,
	,"Data","bpcsMaint.do",,,0
	,"Maintenance","bpcsTranMaint.do",,,0
	])

	addmenu(menu=["BPC Rev",
	,,100,1,"top",style1,,"left",effect,,,,,,,,,,,,
	,"Data","bpcRevMaint.do",,,0
	,"Maintenance","bpcRevTranMaint.do",,,0
	])

	addmenu(menu=["BPC Exc",
	,,100,1,"top",style1,,"left",effect,,,,,,,,,,,,
	,"Data","bpcExMaint.do",,,0
	,"Maintenance","bpcExTranMaint.do",,,0
	])

	addmenu(menu=["Notes",
	,,100,1,"top",style1,,"left",effect,,,,,,,,,,,,
	,"Data","notesMaint.do",,,0
	,"Maintenance","notesTranMaint.do",,,0
	])
	
 addmenu(menu=["RateMgmt",
  ,,170,1,"",style1,,"left",effect,,,,,,,,,,,,
  ,"Select","openMngRateSets.do",,,1
  ,"Rate Data","rateDataMaint.do",,,0
  ,"Rate Maint","rateDataTranMaint.do",,,0
  ])



addmenu(menu=["Production",
28,,170,1,"",style1,,"left",effect,,,,,,,,,,,,
,"Factors", "mngFactors.do",,,1
,"Factor Analysis","mngAnalysisModels.do",,,1
,"Perpetual","mngPerpetualModels.do",,,1
,"Mgn Bill Exch Flex","mngCostExchModels.do",,,1
,"Misc. Analysis","miscAnalysis.do",,,1
,"Misc. Reports","miscReports.do",,,1
])

addmenu(menu=["Jobs",
28,,140,1,"",style1,,"",effect,,,,,,,,,,,,
,"Manage Jobs", "processMgmt.do",,,1
,"Job Reports", "mngReportJobs.do",,,1

])

   addmenu(menu=["ImportExport",
   28,,140,1,"",style1,,"",effect,,,,,,,,,,,,
   ,"Unit Data", "mngUnits.do",,,1
//   ,"Mgn Bill Exch Flex Data", "mngCostExchData.do",,,1
   ,"Affiliate BPC Import","affBpcMaint.do",,,1
   ,"Affiliate ASR Usage","affAsrMaint.do",,,1
   ,"Data Transfers", "dataTransfers.do",,,1
	,"Transfer Log", "mngDataFeedLog.do",,,1
	,"Active Affiliate","rptUserMaint.do?cmd=activeaff",,,1   
	])




addmenu(menu=["Admin",
	28,,130,1,"",style1,,"left",effect,,,,,,,,,,,,
//	,"User Maintenance","userMaint.do",,,1
	,"Currency Codes","currencyCodeMaint.do",,,1
	,"Aff Curr Maint","affCstCurMaint.do",,,1
	,"Sales Type Maint","salesTypeMaint.do",,,1
//	,"PR Mfg","prMfgMaint.do",,,1
	//,"Essbase&nbsp;Mgmt", "openEssbaseMgmt.do",,,1
	,"Manage Daemons", "mngDaemons.do",,,1
	,"Aff Area Unwanted Division", "affAreaMaint.do",,,1	
	,"FileUpload", "fileUpload.do",,,1
	,"DeleteFile", "deleteFile.do?cmd=dir",,,1
	,"Report Search", "rptUserMaint.do?cmd=filter",,,1
])


dumpmenus()