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

public class CurrencyCodeForm extends TCGMForm
{
	private CurrencyCode searchObject = new CurrencyCode();
	private Vector currencylist = new Vector();
	private Sort sortObject = DBConst.DEF_SORT_CURRENCY;
	private CurrencyCode currencyCodeToEdit = new CurrencyCode();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public CurrencyCodeForm()
	{
		super();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return CurrencyCode
	 */
	public CurrencyCode getSearchObject()
	{
		if(this.searchObject == null)
		{
			this.searchObject = new CurrencyCode();
		}
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject CurrencyCode
	 */
	public void setSearchObject(CurrencyCode searchObject)
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
			this.sortObject = DBConst.DEF_SORT_CURRENCY;
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
	public Vector getCurrencylist()
	{
		if(this.currencylist == null)
		{
			this.currencylist = new Vector();
		}
		return this.currencylist;
	}
	/**
	 *
	 * @param index int
	 * @return CurrencyCode
	 */
	public CurrencyCode getCurrencyList(int index)
	{
		CurrencyCode currencyCode = null;
		if(index >= 0 && index < this.getCurrencylist().size())
		{
			currencyCode = (CurrencyCode)this.getCurrencylist().elementAt(index);
		}
		return currencyCode;
	}
	/**
	 *
	 * @param currencylist Vector
	 */
	public void setCurrencylist(Vector currencyList)
	{
		this.currencylist = currencyList;
	}
	/**
	 *
	 * @param index int
	 * @param currencyCode CurrencyCode
	 */
	public void setCurrencyList(int index,CurrencyCode currencyCode)
	{
		this.getCurrencylist().setElementAt(currencyCode,index);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return int
	 */
	public int getCurrencyListSize()
	{
		return this.getCurrencylist().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return CurrencyCode
	 */
	public CurrencyCode getCurrencyCodeToEdit()
	{
		if(this.currencyCodeToEdit == null)
		{
			this.currencyCodeToEdit = new CurrencyCode();
		}
		return this.currencyCodeToEdit;
	}
	/**
	 *
	 * @param currencyCodeToEdit CurrencyCode
	 */
	public void setCurrencyCodeToEdit(CurrencyCode currencyCodeToEdit)
	{
		this.currencyCodeToEdit = currencyCodeToEdit;
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
		this.setSearchObject(new CurrencyCode());//ok to reset this as a new object.  The values are available in fields on the jsp page.
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
			this.setCurrencyCodeToEdit(new CurrencyCode());
			this.setSearchObject(new CurrencyCode());
		}
	}
}