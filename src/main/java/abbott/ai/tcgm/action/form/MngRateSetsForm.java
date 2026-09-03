package abbott.ai.tcgm.action.form;

import java.util.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.helpers.*;

import org.apache.log4j.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class MngRateSetsForm extends TCGMForm
{
	private static Logger logger = Logger.getLogger("abbott.ai.tcgm.action.form.MngRateSetsForm");

	/**
	 * Need a vector for list of rate sets.  The vector will
	 * hold a list of Dataset objects
	 */
	Vector rateSetList = new Vector();

	/**
	 * Need an object to hold the rate set that is currently being edited/created
	 */
	Dataset editRateSet = new Dataset(Dataset.DatasetType.RATE_SET);

	/**
	 * Need a property to hold the dataset table id for the rate set that
	 * is currently selected in the list box.
	 */
	String selectedDatasetTableId = "";

	/**
	 * Need a property to hold the dataset table id for the rate set that
	 * is currently selected in the copy from box.
	 */
	String copyFromDatasetTableId = TCGMConstants.NONE_SELECTED;

	/**
	 * Need an object to hold the rate set that is currently selected
	 */
	Dataset selectedRateSet = new Dataset(Dataset.DatasetType.RATE_SET);

	/**
	 * Default Constructor
	 */
	public MngRateSetsForm()
	{
		super();
	}

	/*****************************************************************************************/
	/**
	 * @return rateSetList size
	 */
	public long getRateSetListSize()
	{
		return this.getRateSetList().size();
	}
	/*****************************************************************************************/
	/**
	 * @return Dataset being edited
	 */
	public Dataset getEditRateSet()
	{
		return this.editRateSet;
	}
	/**
	 * @param editRateSet Dataset being edited
	 */
	public void setEditRateSet(Dataset editRateSet)
	{
		this.editRateSet = editRateSet;
	}
	/*****************************************************************************************/
	/**
	 * @return String
	 */
	public String getSelectedDatasetTableId()
	{
		//If a dataset table id is not selected then set it to the first one in the list
		//if the list is not null
		if(this.selectedDatasetTableId == null || this.selectedDatasetTableId.trim().equals(""))
		{
			if(this.getRateSetList().size() > 0)
			{
				this.selectedDatasetTableId = this.getRateSetList(0).getDatasetTableId();
			}
			else
			{
				this.selectedDatasetTableId = "";
			}
		}
		return this.selectedDatasetTableId;
	}
	/**
	 * @param selectedDatasetTableId String
	 */
	public void setSelectedDatasetTableId(String selectedDatasetTableId)
	{
		this.selectedDatasetTableId = selectedDatasetTableId;
	}
	/**
	 * @return int
	 */
	public int getSelectedDatasetTableIdInt()
	{
		return Integer.parseInt(this.getSelectedDatasetTableId());
	}
	/*****************************************************************************************/
	/**
	 * @return String
	 */
	public String getCopyFromDatasetTableId()
	{
		if(this.copyFromDatasetTableId == null || this.selectedDatasetTableId.trim().equals(""))
		{
			this.copyFromDatasetTableId = TCGMConstants.NONE_SELECTED;
		}
		return this.copyFromDatasetTableId;
	}
	/**
	 * @param copyFromDatasetTableId
	 */
	public void setCopyFromDatasetTableId(String copyFromDatasetTableId)
	{
		this.copyFromDatasetTableId = copyFromDatasetTableId;
	}
	/**
	 * @return
	 */
	public int getCopyFromDatasetTableIdInt()
	{
		return Integer.parseInt(this.getCopyFromDatasetTableId());
	}
	/*****************************************************************************************/
	/**
	 * @return Dataset
	 */
	public Dataset getSelectedRateSet()
	{
		return this.selectedRateSet;
	}
	/**
	 * @param selectedRateSet Dataset
	 */
	public void setSelectedRateSet(Dataset selectedRateSet)
	{
		this.selectedRateSet = selectedRateSet;
	}
	/*****************************************************************************************/
	/**
	 * @return Vector
	 */
	public Vector getRateSetList()
	{
		if(this.rateSetList == null)
		{
			this.rateSetList = new Vector();
		}
		return this.rateSetList;
	}
	/**
	 * @param rateSetList Vector
	 */
	public void setRateSetList(Vector rateSetList)
	{
		this.rateSetList = rateSetList;
	}
	/**
	 * @param rateSet Dataset
	 * @param index int
	 */
	public void setRateSetList(Dataset rateSet,int index)
	{
		this.rateSetList.setElementAt(rateSet,index);
	}
	/**
	 * @param index int
	 * @return Dataset
	 */
	public Dataset getRateSetList(int index)
	{
		Dataset rateSet = null;
		if(index >= 0 && index < this.getRateSetList().size())
		{
			rateSet = (Dataset)this.rateSetList.elementAt(index);
		}
		return rateSet;
	}
	/*****************************************************************************************/
	/**
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_SAVE))
		{
			if(this.getEditRateSet().getDatasetName().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation.ratesetName"));
			}

// 4-29-03 Description can be blank.
//			if(this.getEditRateSet().getDatasetDesc().equals(""))
//			{
//				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation.ratesetDesc"));
//			}

		}
		else if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_DELETE) ||
				this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MAINTAIN) ||
				this.getCmd().equals(TCGMConstants.URL_PARM_VAL_RENAME) ||
				this.getCmd().equals(TCGMConstants.URL_PARM_VAL_COPY))
		{
			if(this.getSelectedDatasetTableId().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation.select.rateset"));
			}

			// Per integration issue discovered 4/17/03, only one rate set can have pending transactions at a time.
			// For this reason "Maintain" can only be chosen if there are no pending transactions, or the rate set
			// chosen matches the rate set id of the transactions currently in the transactions table.
			// This validation check specifically addresses this issue.
			else if (this.getCmd().equals(TCGMConstants.URL_PARM_VAL_MAINTAIN) ) {
				try {
					UserToken ut = AppConst.getInstance().getJobId(); // perform validation tasks using the system/job id
					int pendingId = new RateDataMngr().getPendingRateDataTranId( ut );
					int selId = this.getSelectedDatasetTableIdInt();
					if (pendingId != -1 && pendingId != selId ) {
						// this "default id" of -1 means that no pending dataset transactions exist
						// otherwise the selected id must match the pending transaction id.

						// look up rateset name of pending transactions for error message.
						Dataset ds = new DatasetMngr().getDatasetById(ut, pendingId );

						// return parameterized error
						errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation.select.rateset.pendingtransactions", ds.getDatasetName()));
					}
				}
				catch (TCGMException tex) {
					logger.error("Validation error in MngRateSetsForm", tex);
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation.exception"));
				}
			}
		}


		if(errors.empty())
		{
			return null;
		}
		else
		{
			return errors;
		}
	}
	/**
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	public void processCmd(ActionMapping mapping,HttpServletRequest request)
	{
		if(this.getCmd().equals(TCGMConstants.URL_PARM_VAL_CANCEL))
		{
			this.setCopyFromDatasetTableId(TCGMConstants.NONE_SELECTED);
			this.reset(mapping,request);
		}
	}
	/*****************************************************************************************/
	/**
	 * The reset method is called by the action servlet on every request.  The reset
	 * method is intended to set all properties to their default values.  After they are set
	 * to their defaults then they will be populuated with values in the request/session.
	 * For checkboxes it is not possible to detect if they are unchecked because they do
	 * not get posted when unchecked.  This reset method should resetd the selected values to false.
	 * Then when the form bean is re-populated it will set the values that are checked to be true.  This
	 * allows us to know when something has been unchecked.  For collections, you will need to iterate through
	 * the objects and reset the values.  Do not create a new Vector() because it will destroy your
	 * object and the values will not be populated in the vector at all.
	 *
	 * @param mapping Struts Action Mapping
	 * @param request HttpServletRequest
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.setEditRateSet(new Dataset(Dataset.DatasetType.RATE_SET));
	}
}