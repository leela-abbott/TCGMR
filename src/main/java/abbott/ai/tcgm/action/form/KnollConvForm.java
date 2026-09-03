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
public class KnollConvForm extends TCGMForm
{
	private Vector knollConvList = new Vector();
	private Sort sortObject = DBConst.DEF_SORT_KNOLLCONV;
	private KnollConv knollConvToEdit = new KnollConv();

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public KnollConvForm()
	{
		super();
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
			this.sortObject = DBConst.DEF_SORT_KNOLLCONV;
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
	public Vector getKnollConvList()
	{
		if(this.knollConvList == null)
		{
			this.knollConvList = new Vector();
		}
		return this.knollConvList;
	}
	/**
	 *
	 * @param index int
	 * @return KnollConv
	 */
	public KnollConv getKnollConvList(int index)
	{
		KnollConv knollConv = null;
		if(index >= 0 && index < this.getKnollConvList().size())
		{
			knollConv = (KnollConv)this.getKnollConvList().elementAt(index);
		}
		return knollConv;
	}
	/**
	 *
	 * @param knollConvList Vector
	 */
	public void setKnollConvList(Vector knollConvList)
	{
		this.knollConvList = knollConvList;
	}
	/**
	 *
	 * @param index int
	 * @param knollConv KnollConv
	 */
	public void setKnollConvList(int index,KnollConv knollConv)
	{
		this.getKnollConvList().setElementAt(knollConv,index);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return int
	 */
	public int getKnollConvListSize()
	{
		return this.getKnollConvList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return KnollConv
	 */
	public KnollConv getKnollConvToEdit()
	{
		if(this.knollConvToEdit == null)
		{
			this.knollConvToEdit = new KnollConv();
		}
		return this.knollConvToEdit;
	}
	/**
	 *
	 * @param knollConvToEdit KnollConv
	 */
	public void setKnollConvToEdit(KnollConv knollConvToEdit)
	{
		this.knollConvToEdit = knollConvToEdit;
	}
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
			this.setKnollConvToEdit(new KnollConv());
		}
	}
}