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
public class PRMfgForm extends TCGMForm
{
	private Vector prMfgList = new Vector(); 
	private Sort sortObject = DBConst.DEF_SORT_PRMFG;
	private PRMfg prMfgToEdit = new PRMfg();

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public PRMfgForm()
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
			this.sortObject = DBConst.DEF_SORT_PRMFG;
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
	public Vector getPrmfglist()
	{
		if(this.prMfgList == null)
		{
			this.prMfgList = new Vector();
		}
		return this.prMfgList;
	}
	/**
	 *
	 * @param index int
	 * @return PRMfg
	 */
	public PRMfg getPrMfgList(int index)
	{
		PRMfg prMfg = null;
		if(index >= 0 && index < this.getPrmfglist().size())
		{
			prMfg = (PRMfg)this.getPrmfglist().elementAt(index);
		}
		return prMfg;
	}
	/**
	 *
	 * @param prMfgList Vector
	 */
	public void setPrmfglist(Vector prMfgList)
	{
		this.prMfgList = prMfgList;
	}
	/**
	 *
	 * @param index int
	 * @param prMfg PRMfg
	 */
	public void setPrMfgList(int index,PRMfg prMfg)
	{
		this.getPrmfglist().setElementAt(prMfg,index);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return int
	 */
	public int getPrMfgListSize()
	{
		return this.getPrmfglist().size();
	}
	/*****************************************************************************************/
	/** 
	 *
	 * @return PRMfg
	 */
	public PRMfg getPrMfgToEdit()
	{
		if(this.prMfgToEdit == null)
		{
			this.prMfgToEdit = new PRMfg();
		}
		return this.prMfgToEdit;
	}
	/**
	 *
	 * @param prMfgToEdit PRMfg
	 */
	public void setPrMfgToEdit(PRMfg prMfgToEdit)
	{
		this.prMfgToEdit = prMfgToEdit;
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
			this.setPrMfgToEdit(new PRMfg());
		}
	}
}