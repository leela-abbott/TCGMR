package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.exception.*;

import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class SalesTypeForm extends TCGMForm
{
	private SalesType searchObject = new SalesType();
	private Vector slsTypeList = new Vector();
	private Sort sortObject = new Sort("SLS_TYP","ASC");
	private SalesType slsTypeToEdit = new SalesType();

	private Vector divCodes = new Vector();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public SalesTypeForm()
	{
		super();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return AffCstCur
	 */
	public SalesType getSearchObject()
	{
		if(this.searchObject == null)
		{
			this.searchObject = new SalesType();
		}
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject AffCstCur
	 */
	public void setSearchObject(SalesType searchObject)
	{
		this.searchObject = searchObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Sort
	 */
	public Sort getSortObject()
	{
		if(this.sortObject == null)
		{
			this.sortObject = new Sort("SLS_TYP","ASC");
		}
		return this.sortObject;
	}
	/**
	 *
	 * @param sortObject Sort
	 */
	public void setSortObject(Sort sortObject)
	{
		this.sortObject = sortObject;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return Vector
	 */
	public Vector getSlsTypeList()
	{
		if(this.slsTypeList == null)
		{
			this.slsTypeList = new Vector();
		}
		return this.slsTypeList;
	}
	/**
	 *
	 * @param index int
	 * @return AffCstCur
	 */
	public SalesType getSlsTypelist(int index)
	{
		SalesType slsType = null;
		if(index >= 0 && index < this.getSlsTypeList().size())
		{
			slsType = (SalesType)this.getSlsTypeList().elementAt(index);
		}
		return slsType;
	}
	/**
	 *
	 * @param affCstCurList Vector
	 */
	public void setSlsTypeList(Vector slsTypeList)
	{
		this.slsTypeList = slsTypeList;
	}
	/**
	 *
	 * @param index int
	 * @param affCstCur AffCstCur
	 */
	public void setSlsTypelist(int index,SalesType slsType)
	{
		this.getSlsTypeList().setElementAt(slsType,index);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return int
	 */
	public int getSlsTypeListSize()
	{
		return this.getSlsTypeList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return AffCstCur
	 */
	public SalesType getSlsTypeToEdit()
	{
		if(this.slsTypeToEdit == null)
		{
			this.slsTypeToEdit = new SalesType();
		}
		return this.slsTypeToEdit;
	}
	/**
	 *
	 * @param affCstCurToEdit AffCstCur
	 */
	public void setSlsTypeToEdit(SalesType slsTypeToEdit)
	{
		this.slsTypeToEdit = slsTypeToEdit;
	}
	/*****************************************************************************************/

	/*****************************************************************************************/
	/**
	 * The reset method is called by the action servlet on every request.  The reset
	 * method is intended to set all properties to their default values.  After they are set
	 * to their defaults then they will be populuated with values in the request/session.
	 * For checkboxes it is not possible to detect if they are unchecked because they do
	 * not get posted when unchecked.
	 *
	 * @param mapping Struts Action Mapping
	 * @param request HttpServletRequest
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.setSearchObject(new SalesType());//ok to reset this as a new object.  The values are available in fields on the jsp page.
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		//put validation code here...

		if(errors.empty())
		{
			return null;
		}
		else
		{
			return errors;
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	public void processCmd(ActionMapping mapping,HttpServletRequest request)
	{
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CANCEL))
		{
			this.setSlsTypeToEdit(new SalesType());
			this.setSearchObject(new SalesType());
		}
	}
	/*****************************************************************************************/
	
	/**
	 * @param index
	 * @return
	 */
	public String getDivCode(int index)
	{
		return (String)this.getDivCodes().elementAt(index);
	}
	/**
	 * @param index
	 * @param curCode
	 */
	public void setDivCode(int index,String divCode)
	{
		this.getDivCodes().set(index,divCode);
	}
	/**
	 * @return
	 */
	public Vector getDivCodes() {
		return divCodes;
	}

	/**
	 * @param vector
	 */
	public void setDivCodes(Vector vector) {
		divCodes = vector;
	}

}