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
public class AffCstCurForm extends TCGMForm
{
	private AffCstCur searchObject = new AffCstCur();
	private Vector affCstCurList = new Vector();
	private Sort sortObject = DBConst.DEF_SORT_AFFCSTCUR;
	private AffCstCur affCstCurToEdit = new AffCstCur();

	private Vector curCodes = new Vector();
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public AffCstCurForm()
	{
		super();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return AffCstCur
	 */
	public AffCstCur getSearchObject()
	{
		if(this.searchObject == null)
		{
			this.searchObject = new AffCstCur();
		}
		return this.searchObject;
	}
	/**
	 *
	 * @param searchObject AffCstCur
	 */
	public void setSearchObject(AffCstCur searchObject)
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
			this.sortObject = DBConst.DEF_SORT_AFFCSTCUR;
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
	public Vector getAffCstCurList()
	{
		if(this.affCstCurList == null)
		{
			this.affCstCurList = new Vector();
		}
		return this.affCstCurList;
	}
	/**
	 *
	 * @param index int
	 * @return AffCstCur
	 */
	public AffCstCur getAffCstCurlist(int index)
	{
		AffCstCur affCstCur = null;
		if(index >= 0 && index < this.getAffCstCurList().size())
		{
			affCstCur = (AffCstCur)this.getAffCstCurList().elementAt(index);
		}
		return affCstCur;
	}
	/**
	 *
	 * @param affCstCurList Vector
	 */
	public void setAffCstCurList(Vector affCstCurlist)
	{
		this.affCstCurList = affCstCurlist;
	}
	/**
	 *
	 * @param index int
	 * @param affCstCur AffCstCur
	 */
	public void setAffCstCurlist(int index,AffCstCur affCstCur)
	{
		this.getAffCstCurList().setElementAt(affCstCur,index);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return int
	 */
	public int getAffCstCurListSize()
	{
		return this.getAffCstCurList().size();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return AffCstCur
	 */
	public AffCstCur getAffCstCurToEdit()
	{
		if(this.affCstCurToEdit == null)
		{
			this.affCstCurToEdit = new AffCstCur();
		}
		return this.affCstCurToEdit;
	}
	/**
	 *
	 * @param affCstCurToEdit AffCstCur
	 */
	public void setAffCstCurToEdit(AffCstCur affCstCurToEdit)
	{
		this.affCstCurToEdit = affCstCurToEdit;
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
		this.setSearchObject(new AffCstCur());//ok to reset this as a new object.  The values are available in fields on the jsp page.
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
			this.setAffCstCurToEdit(new AffCstCur());
			this.setSearchObject(new AffCstCur());
		}
	}
	/*****************************************************************************************/
	/**
	 * @return
	 */
	public Vector getCurCodes()
	{
		return this.curCodes;
	}
	/**
	 * @param curCodes
	 */
	public void setCurCodes(Vector curCodes)
	{
		this.curCodes = curCodes;
	}
	/**
	 * @param index
	 * @return
	 */
	public String getCurCode(int index)
	{
		return (String)this.getCurCodes().elementAt(index);
	}
	/**
	 * @param index
	 * @param curCode
	 */
	public void setCurCode(int index,String curCode)
	{
		this.getCurCodes().set(index,curCode);
	}
}