package abbott.ai.tcgm.action.form;

import org.apache.struts.action.*;
import java.util.*;
import abbott.ai.tcgm.entities.*;
import java.io.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class TCGMForm extends ActionForm implements Serializable
{
	protected static final String BR = "<BR />";
	protected final String className = this.getClass().getName();
	protected Vector monthList = new Vector();
	protected Option[] monthListNumber = null;
	// 10-13-05 Per Essbase, pass month with 2 digits
	protected Option[] twoDigitMonthListNumber = null;
	protected Option[] twoDigitMonthListNumberWithLabel = null;
	protected Option[] periodMonthList = null;
	protected Option[] yearList = null;
	protected Option[] exportSystemList = null;
	private String showModels ="";
	/**
	 * This property will hold the row number from the list of data that the user clicks on when
	 * they want to populate the add new row for editing/updating.
	 */
	private String rowToCopy = "";

	private String cmd = "";
	private String focusField = "";
	private String returnLocation = "";

	private String modelSelected="";
	private Vector models = new Vector();
	private Vector closedModels = new Vector();
	private abbott.ai.tcgm.entities.TCGMModel.Type modelType;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMForm()
	{
		super();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param cmd
	 */
	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}
	/**
	 *
	 * @return
	 */
	public String getCmd()
	{
		if(this.cmd == null)
		{
			this.cmd = "";
		}
		return this.cmd;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param focusField
	 */
	public void setFocusField(String focusField)
	{
		this.focusField = focusField;
	}
	/**
	 *
	 * @return
	 */
	public String getFocusField()
	{
		if(this.focusField == null)
		{
			this.focusField = "";
		}
		return this.focusField;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return
	 */
	public Vector getMonthList()
	{
		// Lazy initialization...only do if object requested by a page.
		if (monthList == null) {
			monthList = new Vector(12);
			monthList.add( new Option("January", "01") );
			monthList.add( new Option("February", "02") );
			monthList.add( new Option("March", "03") );
			monthList.add( new Option("April", "04") );
			monthList.add( new Option("May", "05") );
			monthList.add( new Option("June", "06") );
			monthList.add( new Option("July", "07") );
			monthList.add( new Option("August", "08") );
			monthList.add( new Option("September", "09") );
			monthList.add( new Option("October", "10") );
			monthList.add( new Option("November", "11") );
			monthList.add( new Option("December", "12") );
		}
		return monthList;
	}

	public Option[] getMonthListNumber() {
		if (monthListNumber == null) {
			monthListNumber = new Option[12];
			String n = null;
			for (int i = 0; i < 12; i++) {
				n = String.valueOf(i+1);
				monthListNumber[i] = new Option( n, n );
			}
		}
		return monthListNumber;
	}

	// 10-13-05 Per Essbase, pass single digit months as 2 digits
	public Option[] getTwoDigitMonthListNumber() {
		if (twoDigitMonthListNumber == null) {
			twoDigitMonthListNumber = new Option[12];
			String n = null;
			for (int i = 0; i < 12; i++) {
				n = String.valueOf(i+1);
				if (i + 1 < 10)
				{
					twoDigitMonthListNumber[i] = new Option( n, "0" + n );
				}
				else
				{
					twoDigitMonthListNumber[i] = new Option( n, n );
				}
			}
		}
		return twoDigitMonthListNumber;
	}
	
//	10-13-05 Per Essbase, pass single digit months as 2 digits
	 public Option[] getTwoDigitMonthListNumberWithLabel() {
		 if (twoDigitMonthListNumberWithLabel == null) {
			 twoDigitMonthListNumberWithLabel = new Option[12];
			 String n = null;
			 for (int i = 0; i < 12; i++) {
				 n = String.valueOf(i+1);
				 if (i + 1 < 10)
				 {
					 twoDigitMonthListNumberWithLabel[i] = new Option( "CY "+n, "0" + n );
				 }
				 else
				 {
					 twoDigitMonthListNumberWithLabel[i] = new Option( "CY "+n, n );
				 }
			 }
		 }
		 return twoDigitMonthListNumberWithLabel;
	 }

	public Option[] getPeriodMonthList() {
		if (periodMonthList == null) {
			periodMonthList = new Option[13];
			String n = null;

			for (int i = 0; i < 13; i++) {
				n = String.valueOf(i+1);
				periodMonthList[i] = new Option( n, n );
			}
		}
		return periodMonthList;
	}

	public Option[] getYearList() {
			if (yearList == null) {
				yearList = new Option[40];
				String n = null;
				
				// Changing the logic. It will now support till 2020.
				for (int i = 0; i<=39; i++) {
					
					if(i>=9) {
						n = "20" + String.valueOf(i+1);
					}else {
						n = "200" + String.valueOf(i+1);
					}
					yearList[i] = new Option( n, n );
				}
			}
			return yearList;	
		
	}


	/*****************************************************************************************/
	/**
	 *
	 * @param returnLocation
	 */
	public void setReturnLocation(String returnLocation)
	{
		this.returnLocation = returnLocation;
	}
	/**
	 *
	 * @return
	 */
	public String getReturnLocation()
	{
		if(this.returnLocation == null)
		{
			this.returnLocation = "";
		}
		return this.returnLocation;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param modelSelected
	 */
	public void setModelSelected(String modelSelected)
	{
		this.modelSelected = modelSelected;
	}
	/**
	 *
	 * @return
	 */
	public String getModelSelected()
	{
		if(this.modelSelected == null)
		{
			this.modelSelected = "";
		}
		return this.modelSelected;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param models
	 */
	public void setModels(Vector models)
	{
		this.models = models;
	}
	/**
	 *
	 * @return
	 */
	public Vector getModels()
	{
		if(this.models == null)
		{
			this.models = new Vector();
		}
		return this.models;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param models
	 */
	public void setClosedModels(Vector closedModels)
	{
		this.closedModels = closedModels;
	}
	/**
	 *
	 * @return
	 */
	public Vector getClosedModels()
	{
		if(this.closedModels == null)
		{
			this.closedModels = new Vector();
		}
		return this.closedModels;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param modelType
	 */
	public void setModelType(abbott.ai.tcgm.entities.TCGMModel.Type modelType)
	{
		this.modelType = modelType;
	}
	/**
	 *
	 * @return
	 */
	public abbott.ai.tcgm.entities.TCGMModel.Type getModelType()
	{
		return this.modelType;
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public String getRowToCopy()
	{
		if(this.rowToCopy == null)
		{
			this.rowToCopy = "";
		}
		return this.rowToCopy;
	}
	/**
	 * @param rowToCopy
	 */
	public void setRowToCopy(String rowToCopy)
	{
		this.rowToCopy = rowToCopy;
	}
	/**
	 * @return
	 */
	public int getRowToCopyInt()
	{
		return Integer.parseInt(this.getRowToCopy());
	}
	/**
	 * @return String
	 */
	public String getShowModels() {
		if(this.showModels == null)
		{
			this.showModels = "";
		}
		return showModels;
	}

	/**
	 * @param String showModels
	 */
	public void setShowModels(String showModels) {
		this.showModels = showModels;
	}

	/**
	 *
	 * @return
	 */
	public Option[] getExportSystemList()
	{
		if (exportSystemList == null)
		{
			exportSystemList = new Option[4];
			exportSystemList[0] = new Option("RGM", "RGM");
			exportSystemList[1] = new Option("RTC", "RTC");
			exportSystemList[2] = new Option("RBB", "RBB");
			exportSystemList[3] = new Option("RBL", "RBL");
		}
		return exportSystemList;
	}


	public void reset() {
		this.cmd = "";
	}

}