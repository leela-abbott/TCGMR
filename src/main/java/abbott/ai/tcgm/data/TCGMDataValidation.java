package abbott.ai.tcgm.data;

import abbott.ai.tcgm.*;
//import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.entities.*;
import org.apache.struts.action.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class TCGMDataValidation
{
	public final String className = this.getClass().getName();
	private String pageName = "";
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public TCGMDataValidation()
	{
	}
	/**
	 * @param pageName Name of the page being validated.  This is used in case some pages
	 * have different requirements for the same column name.
	 */
	public TCGMDataValidation(String pageName)
	{
		this.setPageName(pageName);
	}
	/*****************************************************************************************/
	/**
	 * @param pageName Name of the page calling the validation routines
	 */
	public void setPageName(String pageName)
	{
		this.pageName = pageName;
	}
	/**
	 * @return Name of the page calling the validation routines
	 */
	public String getPageName()
	{
		return this.pageName;
	}


	/**        IMPORTANT - PLEASE READ!!!!!           IMPORTANT - PLEASE READ!!!!!
	 * In some cases objects are allowed to be blank but if filled must contain a correct value.
	 * For example:
	 *      When doing a mass update the user must still fill in the value of A, C, or D for
	 *      the action code but many of the other cols can be left blank.
	 *      When doing an add most of the cols cannot be blank.
	 * I will add a parameter to each validation method to indicate if the value can be blank or null and will
	 * validate based on this.  I think it will make the methods flexible.  I will only add it where I
	 * find that it is useful or necessary.
	 */


	/*****************************************************************************************/
	/**
	 * @param actionCode Action Code for the transaction record
	 * @return String
	 */
	public String valActionCode(String actionCode)
	{
		String methodName = "valActionCode";
		String errorMessage = "";

		if((this.getPageName() == "ASR")      ||
		   (this.getPageName() == "AFFBPC")   ||
		   (this.getPageName() == "BPCEX")    ||
		   (this.getPageName() == "BPCS")     ||
		   (this.getPageName() == "NOTES")    ||
		   (this.getPageName() == "RATEDATA") ||
		   (this.getPageName() == "RATEEX"))
		{
			if(! (actionCode.equals(TCGMConstants.ACT_CD_ADD) ||
				  actionCode.equals(TCGMConstants.ACT_CD_CHG) ||
				  actionCode.equals(TCGMConstants.ACT_CD_DEL)))
			{
				errorMessage = "The action code must be A, C, or D" + TCGMConstants.BR;
			}
		}else if(this.getPageName() == "BPCREV"){
			if(! (actionCode.equals(TCGMConstants.ACT_CD_CHG)))
			{
				errorMessage = "The action code must be C " + TCGMConstants.BR;
			}
		}
		else
		{
			errorMessage = "Invalid page name: " + this.getPageName();
		}

		return errorMessage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param prdOrig Product Origin
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valProdOrigin(String prdOrig,boolean canBeBlank)
	{
		String methodName = "valProdOrigin";
		String errorMessage = "";

		//1.  If it can be and is blank then return no error messages
		//2.  If it cannot be but is blank then return an error
		//3.  If it is not blank then validate the values.
		if(canBeBlank && prdOrig.equals(""))
		{
			errorMessage = "";
		}
		else if(! canBeBlank && prdOrig.equals(""))
		{
			errorMessage = "Product Origin cannot be blank" + TCGMConstants.BR;
		}
		else if( ! (prdOrig.equals(TCGMConstants.PROD_ORIG_C) ||
			   prdOrig.equals(TCGMConstants.PROD_ORIG_F) ||
			   prdOrig.equals(TCGMConstants.PROD_ORIG_K) ||
			   prdOrig.equals(TCGMConstants.PROD_ORIG_M) ||
			   prdOrig.equals(TCGMConstants.PROD_ORIG_P) ||
			   prdOrig.equals(TCGMConstants.PROD_ORIG_R) ||
			   prdOrig.equals(TCGMConstants.PROD_ORIG_T) ||
			   prdOrig.equals(TCGMConstants.PROD_ORIG_Z)))
		{
				errorMessage = "Product Origin must be C, F, K, M, P, R, T, or Z" + TCGMConstants.BR;
		}
		return errorMessage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param aff Affiliate
	 * @param affType Indicates if this aff is Rpt, End, or Sup
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valAff(String aff,String affType,boolean canBeBlank)
	{
		String methodName = "valAff";
		String errorMessage = "";

		if(!canBeBlank && aff.equals(""))
		{
			errorMessage = affType + " Aff cannot be blank" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param invCode Inventory Code
	 * @param invCodeType Inventory Type
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valInvCode(String invCode,String invCodeType,boolean canBeBlank)
	{
		String methodName = "valInvCode";
		String errorMessage = "";

		if(!canBeBlank && invCode.equals(""))
		{
			errorMessage = invCodeType + " Inv Code cannot be blank" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param list List
	 * @param listType List type
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valList(String list,String listType,boolean canBeBlank)
	{
		String methodName = "valList";
		String errorMessage = "";

		if(!canBeBlank && list.equals(""))
		{
			errorMessage = listType + " List cannot be blank" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param pack Pack Code
	 * @param packType pack type
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valPack(String pack,String packType,boolean canBeBlank)
	{
		String methodName = "valPack";
		String errorMessage = "";

		if(!canBeBlank && pack.equals(""))
		{
			errorMessage = packType + " Pack cannot be blank" + TCGMConstants.BR;
		}
		return errorMessage;
	}
	
	/**
		 *
		 * @param note
		 * @param canBeBlank
		 * @return errorMessage
		 */
		public String valNote(String note,String noteType,boolean canBeBlank)
		{
			String methodName = "valNote";
			String errorMessage = "";

			if(!canBeBlank && ( note.trim().length()>255) )
			{
				errorMessage = "Note cannot be blank or more than 255 characters" + TCGMConstants.BR;
			}

			return errorMessage;
		}

	/**
	 *
	 * @param freezeCost
	 * @param canBeBlank
	 * @return
	 */
	public String valFreezeCost(String freezeCost,boolean canBeBlank)
	{
		String methodName = "valFreezeCost";
		String errorMessage = "";

		//if(!canBeBlank && (!(freezeCost.trim().equals("")) || !(freezeCost.trim().equals("Y"))))
		if( (!(freezeCost.trim().equals("F"))) &&
			  !(freezeCost.trim().equals("U")) &&
			  !(freezeCost.trim().equals("")) )
		{
			errorMessage = "Freeze Cost must equal F, U, or blank" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param usage Usage Factor
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valUsage(String usage,boolean canBeBlank)
	{
		String methodName = "valUsage";
		String errorMessage = "";

		if(!canBeBlank && usage.equals(""))
		{
			errorMessage = "Usage cannot be blank" + TCGMConstants.BR;
		}

		try
		{
			if(! usage.equals(""))
			{
				Double usageDbl = new Double(usage);				
			}
		}
		catch(Exception e)
		{
			errorMessage += "Usage must be a numeric value" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param curCode
	 * @param canBeBlank
	 * @return
	 */
	public String valCurCode(String curCode,boolean canBeBlank)
	{
		String methodName = "valCurCode";
		String errorMessage = "";

		if(!canBeBlank && curCode.trim().equals(""))
		{
			errorMessage = "Currency Code cannot be blank" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param bpCurCode
	 * @param canBeBlank
	 * @return
	 */
	public String valBpCurCode(String bpCurCode,boolean canBeBlank)
	{
		String methodName = "valBpCurCode";
		String errorMessage = "";

		if(!canBeBlank && bpCurCode.trim().equals(""))
		{
			errorMessage = "BP Cur Code cannot be blank" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param costCurCode
	 * @param canBeBlank
	 * @return
	 */
	public String valCostCurCode(String costCurCode,boolean canBeBlank)
	{
		String methodName = "valCostCurCode";
		String errorMessage = "";

		if(!canBeBlank && costCurCode.trim().equals(""))
		{
			errorMessage = "Cost Cur Code cannot be blank" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param begPeriod
	 * @param canBeBlank
	 * @return
	 */
	public String valBegPeriod(String begPeriod, boolean canBeBlank)
	{
		String methodName = "valBegPeriod";
		String errorMessage = "";

		//Begin Validate Beg Period.
		String begStr = begPeriod.trim();
		boolean begValid = true;
		int begInt = 0;
		try
		{
			begInt = Integer.parseInt(begStr);

			if( begStr.equals("") || begInt < 1 || begInt > 12 )
			{
				begValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			begValid = false;
		}
		//if(!canBeBlank && !begValid)
		if(!begValid)
		{
			errorMessage = "Beginning Period cannot be blank, must be a value between 1 and 12, and must be less than the ending period." + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param endPeriod
	 * @param canBeBlank
	 * @return
	 */
	public String valEndPeriod(String endPeriod, boolean canBeBlank)
	{
		String methodName = "valEndPeriod";
		String errorMessage = "";

		//End Validate Beg Period.
		String endStr = endPeriod.trim();
		int endInt = 0;
		boolean endValid = true;
		try
		{
			endInt = Integer.parseInt(endStr);
			if( endStr.equals("") ||
				endInt < 1 ||
				endInt > 12 )
			{
				endValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			endValid = false;
		}
		//if(!canBeBlank && !endValid)
		if(!endValid)
		{
			errorMessage = "Ending Period cannot be blank and must be a value between 1 and 12." + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param begPeriod
	 * @param endPeriod
	 * @param canBeBlank
	 * @return
	 */
	public String valPeriodRange(String begPeriod, String endPeriod, boolean canBeBlank)
	{
		String methodName = "valPeriodRange";
		String errorMessage = "";

		String begStr = begPeriod.trim();
		String endStr = endPeriod.trim();
		int begInt = 0;
		int endInt = 0;
		boolean periodRangeValid = true;
		try
		{
			begInt = Integer.parseInt(begStr);
			endInt = Integer.parseInt(endStr);
			if(endInt < begInt)
			{
				periodRangeValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			periodRangeValid = false;
		}
		//if(!canBeBlank && !periodRangeValid)
		if(!periodRangeValid)
		{
			errorMessage = "Ending Period must be greater than the beginning period." + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param billPrice
	 * @param canBeBlank
	 * @return
	 */
	public String valBillPrice(String billPrice, boolean canBeBlank)
	{
		String methodName = "valBillPrice";
		String errorMessage = "";

		String billPriceStr = billPrice.trim();
		double billPriceDbl = 0;
		boolean billPriceValid = true;
		try
		{
			if (!billPriceStr.equals("")&&(!billPriceStr.equalsIgnoreCase(TCGMConstants.LBL_COST)))
			{
				billPriceDbl = Double.parseDouble(billPriceStr);
			}
			// 4-14-03 Error only if Price < 0
			//if( billPriceStr.equals("") || billPriceDbl < 0 )
			//if( billPriceDbl < 0 )
			if( billPriceDbl < 0 )
			{
				billPriceValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			billPriceValid = false;
		}
		//if(!canBeBlank && !billPriceValid && !billPriceStr.equals(""))
		if(!billPriceValid && !billPriceStr.equals(""))
		{
			errorMessage = "Bill Price cannot be less than 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param billPrice
	 * @param canBeBlank
	 * @return
	 */
	public String valRevBillPrice(String billPrice, boolean canBeBlank)
	{
		String methodName = "valRevBillPrice";
		String errorMessage = "";

		String billPriceStr = billPrice.trim();
		double billPriceDbl = 0;
		boolean billPriceValid = true;
		try
		{
			billPriceDbl = Double.parseDouble(billPriceStr);
			// 4-29-03 Error only if Price <= 0
			//if( billPriceStr.equals("") || billPriceDbl <== 0 )
			if( (billPriceDbl < 0) || (billPriceDbl == 0))
			{
				billPriceValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			billPriceValid = false;
		}
		if(!canBeBlank && !billPriceValid)
		{
			errorMessage = "Bill Price must be greater than 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param costPrice
	 * @param canBeBlank
	 * @return
	 */
	public String valCostPrice(String costPrice, boolean canBeBlank)
	{
		String methodName = "valCostPrice";
		String errorMessage = "";

		String costPriceStr = costPrice.trim();
		double costPriceDbl = 0;
		boolean costPriceValid = true;
		try
		{
			if (!costPriceStr.equals("")&&(!costPriceStr.equalsIgnoreCase(TCGMConstants.LBL_BP)))
			{
				costPriceDbl = Double.parseDouble(costPriceStr);
			}
			// 4-14-03 Error only if Cost < 0
			//if( costPriceStr.equals("") || costPriceDbl < 0 )
			if( costPriceDbl < 0 )
			{
				costPriceValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			costPriceValid = false;
		}
		if(!canBeBlank && !costPriceValid && !costPriceStr.equals(""))
		{
			errorMessage = "Cost Price cannot be less than 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param rate
	 * @param curCode
	 * @param canBeBlank
	 * @return
	 */
	public String valRateWithCurCode(String rate,String curCode)
	{
		String methodName = "valRateWithCurCode";
		String errorMessage = "";
		String rateStr = rate.trim();
		double rateDbl = 0;
		boolean rateValid = true;

		try
		{
			rateDbl = Double.parseDouble(rateStr);
			if(rateDbl > 0 && curCode.trim().equals(""))
			{
				errorMessage = "Currency Code cannot be blank when Rate is filled in" + TCGMConstants.BR;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseDouble fails.
		{
			rateValid = false;
		}
		if(!rateValid && !rateStr.equals(""))
		{
			errorMessage = "Rate is invalid" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param costPrice
	 * @param costCurCode
	 * @param canBeBlank
	 * @return
	 */
	public String valCostWithCurCode(String costPrice,String costCurCode)
	{
		String methodName = "valCostWithCurCode";
		String errorMessage = "";
		String costPriceStr = costPrice.trim();
		double costPriceDbl = 0;
		boolean costPriceValid = true;

		try
		{
			if ((!costPriceStr.equals("")) && (!costPriceStr.equalsIgnoreCase(TCGMConstants.LBL_BP)))
			{ 
				costPriceDbl = Double.parseDouble(costPriceStr);
			}
			if(costPriceDbl > 0 && costCurCode.trim().equals(""))
			{
				errorMessage = "Cost Cur Code cannot be blank when Cost Price is filled in" + TCGMConstants.BR;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseDouble fails.
		{
			costPriceValid = false;
		}
		if(!costPriceValid && !costPriceStr.equals(""))
		{
			errorMessage = "Cost Price is invalid" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param costPrice
	 * @param costCurCode
	 * @param canBeBlank
	 * @return
	 */
	public String valPriceWithCurCode(String billPrice,String bpCurCode)
	{
		String methodName = "valPriceWithCurCode";
		String errorMessage = "";
		String billPriceStr = billPrice.trim();
		double billPriceDbl = 0;
		boolean billPriceValid = true;

		try
		{
			if ((!billPriceStr.equals("")) && (!billPriceStr.equalsIgnoreCase(TCGMConstants.LBL_COST)))
			{
				billPriceDbl = Double.parseDouble(billPriceStr);
			}
			if(billPriceDbl > 0 && bpCurCode.trim().equals(""))
			{
				errorMessage = "Bp Cur Code cannot be blank when Bill Price is filled in" + TCGMConstants.BR;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseDouble fails.
		{
			billPriceValid = false;
		}
		if(!billPriceValid && !billPriceStr.equals(""))
		{
			errorMessage = "Bill Price is invalid" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param actionCode
	 * @param billPrice
	 * @param bpCurCode
	 * @param costPrice
	 * @param costCurCode
	 * @return
	 */
	public String valPriceWithCost(String actionCode,String billPrice,String bpCurCode,
													String costPrice,String costCurCode)
	{
		String methodName = "valPriceWithCost";
		String errorMessage = "";
		String actionCodeStr = actionCode.trim();
		String billPriceStr = billPrice.trim();
		String costPriceStr = costPrice.trim();
		String bpCurCodeStr = bpCurCode.trim();
		String costCurCodeStr = costCurCode.trim();

		if(actionCodeStr.equals(TCGMConstants.ACT_CD_ADD))
		{
			if(billPriceStr.equals("") || bpCurCode.equals("") ||
			   costPriceStr.equals("") || bpCurCode.equals(""))
			{
				errorMessage = "Price, Cost, and Currency Codes cannot be blank on an Add Transaction" + TCGMConstants.BR;
			}
		}
		if(actionCodeStr.equals(TCGMConstants.ACT_CD_CHG))
		{
			
			if(!( 
				  ((billPriceStr.equals("")) && (bpCurCodeStr.equals(""))) ||  // both of them are null/empty
				  ((!billPriceStr.equals("")) && (!bpCurCodeStr.equals("")))   // both of them are not null
				 )
			  )
			  {
				errorMessage = "Please enter values for Bill Price and BP Currency Code. " + TCGMConstants.BR;
			  }
			
			if(!( 
				  ((costPriceStr.equals("")) && (costCurCodeStr.equals(""))) ||  // both of them are null/empty
				  ((!costPriceStr.equals("")) && (!costCurCodeStr.equals("")))   // both of them are not null
				 )
			  )
			  {
				errorMessage = errorMessage + "Please enter values for Cost and Cost Currency Code. " + TCGMConstants.BR;
			  }
			  
			if(( (errorMessage.equals("")) &&
				  ((billPriceStr.equals("")) && (costPriceStr.equals("")))   // both of them are null/empty
				 )
			  )
			  {
				errorMessage = "Either Bill Price and BP Currency Code or Cost and Cost Currency Code should be entered. " + TCGMConstants.BR;
			  }

		}		
		return errorMessage;
	}

	/**
	 *
	 * @param actionCode
	 * @param billPrice
	 * @param bpCurCode
	 * @param costPrice
	 * @param costCurCode
	 * @return
	 */
	public String valBPCExcAddNewRecord(String actionCode,String billPrice,String bpCurCode,
													String costPrice,String costCurCode)
	{
		String methodName = "valBPCExcAddNewRecord";
		String errorMessage = "";
		String actionCodeStr = actionCode.trim();
		String billPriceStr = billPrice.trim();
		String costPriceStr = costPrice.trim();
		String bpCurCodeStr = bpCurCode.trim();
		String costCurCodeStr = costCurCode.trim();

		if(actionCodeStr.equals(TCGMConstants.ACT_CD_ADD))
		{
			
			if(!( 
			      ((billPriceStr.equals("")) && (bpCurCodeStr.equals(""))) ||  // both of them are null/empty
			      ((!billPriceStr.equals("")) && (!bpCurCodeStr.equals("")))   // both of them are not null
			     )
			  )
			  {
				errorMessage = "Please enter values for Bill Price and BP Currency Code. " + TCGMConstants.BR;
			  }
			
			if(!( 
				  ((costPriceStr.equals("")) && (costCurCodeStr.equals(""))) ||  // both of them are null/empty
				  ((!costPriceStr.equals("")) && (!costCurCodeStr.equals("")))   // both of them are not null
				 )
			  )
			  {
				errorMessage = errorMessage + "Please enter values for Cost and Cost Currency Code. " + TCGMConstants.BR;
			  }

		}
		return errorMessage;
	}

	/**
	 *
	 * @param rate
	 * @param canBeBlank
	 * @return
	 */
	public String valRate(String rate, boolean canBeBlank)
	{
		String methodName = "valRate";
		String errorMessage = "";

		String rateStr = rate.trim();
		double rateDbl = 0;
		boolean rateValid = true;
		try
		{
			rateDbl = Double.parseDouble(rateStr);
			if( rateStr.equals("") || rateDbl <= 0 )
			{
				rateValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			rateValid = false;
		}
		if(!canBeBlank && !rateValid)
		{
			errorMessage = "Rate must be > 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param bpfRate
	 * @param canBeBlank
	 * @return
	 */
	public String valBpfRate(String bpfRate, boolean canBeBlank)
	{
		String methodName = "valBpfRate";
		String errorMessage = "";

		String bpfRateStr = bpfRate.trim();
		double bpfRateDbl = 0;
		boolean bpfRateValid = true;
		try
		{
			bpfRateDbl = Double.parseDouble(bpfRateStr);
			if( bpfRateStr.equals("") || bpfRateDbl < 0 )
			{
				bpfRateValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			bpfRateValid = false;
		}
		if(!canBeBlank && !bpfRateValid)
		{
			errorMessage = "BP Factor Factor cannot be blank and must be > 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param costfRate
	 * @param canBeBlank
	 * @return
	 */
	public String valCostfRate(String costfRate, boolean canBeBlank)
	{
		String methodName = "valCostfRate";
		String errorMessage = "";

		String costfRateStr = costfRate.trim();
		double costfRateDbl = 0;
		boolean costfRateValid = true;
		try
		{
			costfRateDbl = Double.parseDouble(costfRateStr);
			if( costfRateStr.equals("") || costfRateDbl < 0 )
			{
				costfRateValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			costfRateValid = false;
		}
		if(!canBeBlank && !costfRateValid)
		{
			errorMessage = "Cost Factor Rate cannot be blank and must be > 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param bppRate
	 * @param canBeBlank
	 * @return
	 */
	public String valBppRate(String bppRate, boolean canBeBlank)
	{
		String methodName = "valBppRate";
		String errorMessage = "";

		String bppRateStr = bppRate.trim();
		double bppRateDbl = 0;
		boolean bppRateValid = true;
		try
		{
			bppRateDbl = Double.parseDouble(bppRateStr);
			if( bppRateStr.equals("") || bppRateDbl < 0 )
			{
				bppRateValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			bppRateValid = false;
		}
		if(!canBeBlank && !bppRateValid)
		{
			errorMessage = "BP Plan Rate cannot be blank and must be > 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param costpRate
	 * @param canBeBlank
	 * @return
	 */
	public String valCostpRate(String costpRate, boolean canBeBlank)
	{
		String methodName = "valCostpRate";
		String errorMessage = "";

		String costpRateStr = costpRate.trim();
		double costpRateDbl = 0;
		boolean costpRateValid = true;
		try
		{
			costpRateDbl = Double.parseDouble(costpRateStr);
			if( costpRateStr.equals("") || costpRateDbl < 0 )
			{
				costpRateValid = false;
			}
		}
		catch(Exception e) // An Exception might be thrown if parseInt fails.
		{
			costpRateValid = false;
		}
		if(!canBeBlank && !costpRateValid)
		{
			errorMessage = "Cost Plan Rate cannot be blank and must be > 0" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param Rev Type
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valRevType(String revType,boolean canBeBlank)
	{
		String methodName = "valRevType";
		String errorMessage = "";

		if(!canBeBlank && !(revType.equals("1")) && !(revType.equals("2")))
		{
			errorMessage = "Rev Type must = 1 or 2" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param supKey
	 * @param supAff
	 * @param canBeBlank
	 * @return
	 */
	public String valSupAffAndSupKey(String supKey,String supAff,boolean canBeBlank)
	{
		String methodName = "valSupAffAndSupKey";
		String errorMessage = "";

		if(!canBeBlank && (supAff.equals("3RD") && !supKey.equals("B")) )
		{
			errorMessage = "Sup Key must = B when SupAff = 3RD" + TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param supKey
	 * @param productOrigin
	 * @param canBeBlank
	 * @return
	 */
	public String valProductOriginAndSupKey(String supKey,String productOrigin,boolean canBeBlank)
	{
		String methodName = "valProductOriginAndSupKey";
		String errorMessage = "";

		if(!canBeBlank && (productOrigin.equals("Z") && !supKey.equals("B")) )
		{
			errorMessage = "Sup Key must = B when Product Origin = Z" + TCGMConstants.BR;
		}

		if(!canBeBlank && (productOrigin.equals("T") && !supKey.equals("B")) )
		{
			errorMessage = "Sup Key must = B when Product Origin = T" + TCGMConstants.BR;
		}
	
		return errorMessage;
	}

	/**
	 *
	 * @param rptAff
	 * @param supAff
	 * @param productOrigin
	 * @param canBeBlank
	 * @return
	 */
	public String valProductOriginRptAffSupAff(String rptAff, String supAff,String productOrigin,boolean canBeBlank)
	{
		String methodName = "valProductOriginRptAffSupAff";
		String errorMessage = "";

		if(!canBeBlank && (productOrigin.equals("P") && supAff.equals(rptAff)) )
		{
			errorMessage = "Rpt Aff cannot equal Sup Aff When Product Origin = P" + TCGMConstants.BR;
		}		
		if(!canBeBlank && (productOrigin.equals("M") || productOrigin.equals("T") || productOrigin.equals("R")))
		{ 
			if (!supAff.equals(rptAff))
			{
				errorMessage = "Rpt Aff must equal Sup Aff When Product Origin = M or T or R" + TCGMConstants.BR;
			}
		}
		return errorMessage;
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param rptAff Reporting Affiliate
	 * @param rptInvCode Reporting Inventory Code
	 * @param rptProductId Reporting Product
	 * @param supAff Supplying Affiliate
	 * @param supInvCode Supplying Inventory Code
	 * @param supProductId Supplying Product
	 * @param supKey Supplying Key
	 * @param canBeBlank boolean
	 * @return String
	 */
	public String valLoopChk(String rptAff, String rptInvCode, String rptProductId,
							 String supAff, String supInvCode, String supProductId,
							 String supKey, boolean canBeBlank)
	{
		String methodName = "valLoopChk";
		String errorMessage = "";
		String beginningLayer = "B";

		String rptCombo = rptAff + rptInvCode + rptProductId;
		String supCombo = supAff + supInvCode + supProductId;

		if(!canBeBlank && ((rptCombo.equals(supCombo)) && (!(supKey.equals(beginningLayer))))) {
			errorMessage = "SupKey must be B if reporting and supplying affiliate info are equal" +  TCGMConstants.BR;
		}

		return errorMessage;
	}

	/**
	 *
	 * @param modelId
	 * @param state
	 * @param errors
	 * @return
	 */
	public boolean validateModelCopy(String modelId, TCGMState state, ActionErrors errors) {
		String methodName = "validateModelCopy(String modelId, TCGMState state)";

		if ( modelId.equals(state.getCurrentModelIdString() ) )
		{
			 errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.validation.trancopy.current"));
			 return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 *
	 * @param modelId
	 * @param errors
	 * @return
	 */
	public boolean validateModelCopy(String modelId, ActionErrors errors) {
		String methodName = "validateModelCopy(String modelId, TCGMState state)";

		if ( modelId.trim().equals("") || modelId.trim().equals(null) || modelId.trim().equals("none"))
		{
			 errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.validation.trancopy.notspecified"));
			 return false;
		}
		else
		{
			return true;
		}
	}

	/*****************************************************************************************/
	/**
	 * @param asrTran AsrTran
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return boolean
	 */
	public boolean validateAsrTran(AsrTran asrTran,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(AsrTran,ActionErrors,boolean)";
		boolean validate = true;

		validate = validateAsr(asrTran.getAsr(),errors,canBeBlank);

		asrTran.getAsr().appendMsg(this.valActionCode(asrTran.getActionCode()));

		//if validate is still true then asr validation passed.  check for error messages and
		//if found add an ActionError to the list.
		if(validate)
		{
			if(! asrTran.getAsr().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
				validate = false;
			}
		}

		return validate;
	}

	/**
	 * @param asr Asr
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateAsr(Asr asr,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(Asr,ActionErrors,boolean)";
		boolean validate = true;
		asr.clearMsg();

		validate = validateAsr(asr,canBeBlank);
			if(!validate){
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));	
			}
		return validate;
	}
	
	/**
	 * This method is to validate the ASR objects before submitting to back end.
	 * @param asr Asr
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateAsr(Asr asr,boolean canBeBlank)
	{
		String methodName = "validate(Asr,boolean)";
		boolean validate = true;

		asr.clearMsg();

		asr.appendMsg(this.valProdOrigin(asr.getProductOrigin(),canBeBlank));
		asr.appendMsg(this.valAff(asr.getRptAff(),"Rpt",canBeBlank));
		asr.appendMsg(this.valInvCode(asr.getRptProduct().getInvCode(),"Rpt",canBeBlank));
		asr.appendMsg(this.valList(asr.getRptProduct().getList(),"Rpt",canBeBlank));
		asr.appendMsg(this.valPack(asr.getRptProduct().getPack(),"Rpt",canBeBlank));
		asr.appendMsg(this.valAff(asr.getSupAff(),"Sup",canBeBlank));
		asr.appendMsg(this.valInvCode(asr.getSupProduct().getInvCode(),"Sup",canBeBlank));
		asr.appendMsg(this.valList(asr.getSupProduct().getList(),"Sup",canBeBlank));
		asr.appendMsg(this.valPack(asr.getSupProduct().getPack(),"Sup",canBeBlank));
		asr.appendMsg(this.valUsage(asr.getUsage(),canBeBlank));
		asr.appendMsg(this.valLoopChk(asr.getRptAff(),asr.getRptProduct().getInvCode(),
									  asr.getRptProduct().getFullId(),
										asr.getSupAff(), asr.getSupProduct().getInvCode(),
										 asr.getSupProduct().getFullId(),
										asr.getSupKey(),canBeBlank));
		asr.appendMsg(this.valSupAffAndSupKey(asr.getSupKey(),asr.getSupAff(),canBeBlank));
		asr.appendMsg(this.valProductOriginAndSupKey(asr.getSupKey(),asr.getProductOrigin(),canBeBlank));
		//asr.appendMsg(this.valProductOriginRptAffSupAff(asr.getRptAff(),asr.getSupAff(),asr.getProductOrigin(),canBeBlank));		

		if(! asr.getMsg().equals(""))
		{
			validate = false;
		}

		return validate;
	}


	/*****************************************************************************************/
	/**
	 * @param bpcExTran BpcExTran
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return boolean
	 */
	public boolean validateBpcExTran(BpcExTran bpcExTran,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(BpcExTran,ActionErrors,boolean)";
		boolean validate = true;

		validate = validateBpcEx(bpcExTran.getBpcEx(),errors,canBeBlank);

		bpcExTran.getBpcEx().appendMsg(this.valActionCode(bpcExTran.getActionCode()));
		bpcExTran.getBpcEx().appendMsg(this.valBPCExcAddNewRecord(bpcExTran.getActionCode(),
											 bpcExTran.getBpcEx().getBillPrice(),bpcExTran.getBpcEx().getBpCurCode(),
											 bpcExTran.getBpcEx().getCostPrice(),bpcExTran.getBpcEx().getCostCurCode()));

		//if validate is still true then bpcs validation passed.  check for error messages and
		//if found add an ActionError to the list.
		if(validate)
		{
			if(! bpcExTran.getBpcEx().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
				validate = false;
			}
		}

		return validate;
	}

	/**
	 * @param bpcEx BpcEx
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateBpcEx(BpcEx bpcEx,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(BpcEx,ActionErrors,boolean)";
		boolean validate = true;

		bpcEx.clearMsg();

		bpcEx.appendMsg(this.valAff(bpcEx.getEndAff(),"End",canBeBlank));
		bpcEx.appendMsg(this.valInvCode(bpcEx.getEndProduct().getInvCode(),"End",canBeBlank));
		bpcEx.appendMsg(this.valList(bpcEx.getEndProduct().getList(),"End",canBeBlank));
		bpcEx.appendMsg(this.valPack(bpcEx.getEndProduct().getPack(),"End",canBeBlank));

		bpcEx.appendMsg(this.valAff(bpcEx.getRptAff(),"Rpt",canBeBlank));
		bpcEx.appendMsg(this.valInvCode(bpcEx.getRptProduct().getInvCode(),"Rpt",canBeBlank));
		bpcEx.appendMsg(this.valList(bpcEx.getRptProduct().getList(),"Rpt",canBeBlank));
		bpcEx.appendMsg(this.valPack(bpcEx.getRptProduct().getPack(),"Rpt",canBeBlank));

		bpcEx.appendMsg(this.valAff(bpcEx.getSupAff(),"Sup",canBeBlank));
		bpcEx.appendMsg(this.valInvCode(bpcEx.getSupProduct().getInvCode(),"Sup",canBeBlank));
		bpcEx.appendMsg(this.valList(bpcEx.getSupProduct().getList(),"Sup",canBeBlank));
		bpcEx.appendMsg(this.valPack(bpcEx.getSupProduct().getPack(),"Sup",canBeBlank));

		bpcEx.appendMsg(this.valFreezeCost(bpcEx.getFreezeCost().toUpperCase(),canBeBlank));

		// 5-1-03 Per DC, Not applicable on the Exceptions UI
		//bpcEx.appendMsg(this.valUsage(bpcEx.getUsage(),canBeBlank));

		bpcEx.appendMsg(this.valBegPeriod(bpcEx.getBegPeriod(),canBeBlank));
		bpcEx.appendMsg(this.valEndPeriod(bpcEx.getEndPeriod(),canBeBlank));
		bpcEx.appendMsg(this.valPeriodRange(bpcEx.getBegPeriod(),bpcEx.getEndPeriod(),canBeBlank));

		bpcEx.appendMsg(this.valBillPrice(bpcEx.getBillPrice(),canBeBlank));
		bpcEx.appendMsg(this.valCostPrice(bpcEx.getCostPrice(),canBeBlank));

		//bpcEx.appendMsg(this.valBpCurCode(bpcEx.getBpCurCode(),canBeBlank));
		//bpcEx.appendMsg(this.valCostCurCode(bpcEx.getCostCurCode(),canBeBlank));
		bpcEx.appendMsg(this.valCostWithCurCode(bpcEx.getCostPrice(),bpcEx.getCostCurCode()));
		bpcEx.appendMsg(this.valPriceWithCurCode(bpcEx.getBillPrice(),bpcEx.getBpCurCode()));

		if(! bpcEx.getMsg().equals(""))
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
			validate = false;
		}

		return validate;
	}
	/*****************************************************************************************/
	/**
	 * @param bpcRevTran BpcRevTran
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return boolean
	 */
	public boolean validateBpcRevTran(BpcRevTran bpcRevTran,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(BpcRevTran,ActionErrors,boolean)";
		boolean validate = true;

		validate = validateBpcRev(bpcRevTran.getBpcRev(),errors,canBeBlank);

		bpcRevTran.getBpcRev().appendMsg(this.valActionCode(bpcRevTran.getActionCode()));

		// 4-25-03 Check no longer applicable because cost does not appear on rate screen
//		bpcRevTran.getBpcRev().appendMsg(this.valPriceWithCost(bpcRevTran.getActionCode(),
//											 bpcRevTran.getBpcRev().getBillPrice(),bpcRevTran.getBpcRev().getBpCurCode(),
//											 bpcRevTran.getBpcRev().getCostPrice(),bpcRevTran.getBpcRev().getCostCurCode()));

		//if validate is still true then bpcRev validation passed.  check for error messages and
		//if found add an ActionError to the list.
		if(validate)
		{
			if(! bpcRevTran.getBpcRev().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
				validate = false;
			}
		}

		return validate;
	}

	/**
	 * @param bpcRev BpcRev
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateBpcRev(BpcRev bpcRev,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(BpcRev,ActionErrors,boolean)";
		boolean validate = true;

		bpcRev.clearMsg();
		bpcRev.appendMsg(this.valRevType(bpcRev.getRevType(),canBeBlank));

		bpcRev.appendMsg(this.valAff(bpcRev.getRptAff(),"Rpt",canBeBlank));

		bpcRev.appendMsg(this.valAff(bpcRev.getSupAff(),"Sup",canBeBlank));
		bpcRev.appendMsg(this.valInvCode(bpcRev.getSupProduct().getInvCode(),"Sup",canBeBlank));
		bpcRev.appendMsg(this.valList(bpcRev.getSupProduct().getList(),"Sup",canBeBlank));
		bpcRev.appendMsg(this.valPack(bpcRev.getSupProduct().getPack(),"Sup",canBeBlank));

		//bpcRev.appendMsg(this.valRevBillPrice(bpcRev.getBillPrice(),canBeBlank));
		bpcRev.appendMsg(this.valBillPrice(bpcRev.getBillPrice(),canBeBlank));

		// 4-25-03 Field N/A in BpcRev UI
		//bpcRev.appendMsg(this.valCostPrice(bpcRev.getCostPrice(),canBeBlank));

		//bpcRev.appendMsg(this.valBpCurCode(bpcRev.getBpCurCode(),canBeBlank));
		//bpcRev.appendMsg(this.valCostCurCode(bpcRev.getCostCurCode(),canBeBlank));

		// 4-25-03 Field N/A in BpcRev UI
		//bpcRev.appendMsg(this.valCostWithCurCode(bpcRev.getCostPrice(),bpcRev.getCostCurCode()));

		bpcRev.appendMsg(this.valPriceWithCurCode(bpcRev.getBillPrice(),bpcRev.getBpCurCode()));

		// 4-25-03 Field N/A in BpcRev UI
		//bpcRev.appendMsg(this.valFreezeCost(bpcRev.getFreezeCost().toUpperCase(),canBeBlank));

		bpcRev.appendMsg(this.valBegPeriod(bpcRev.getBegPeriod(),canBeBlank));
		bpcRev.appendMsg(this.valEndPeriod(bpcRev.getEndPeriod(),canBeBlank));
		bpcRev.appendMsg(this.valPeriodRange(bpcRev.getBegPeriod(),bpcRev.getEndPeriod(),canBeBlank));

		if(! bpcRev.getMsg().equals(""))
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
			validate = false;
		}
		return validate;
	}

	/*****************************************************************************************/
	/**
	 * @param bpcsTran BpcsTran
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return boolean
	 */
	public boolean validateBpcsTran(BpcsTran bpcsTran,ActionErrors errors,boolean canBeBlank,String strAction)
	{
		String methodName = "validate(BpcsTran,ActionErrors,boolean)";
		boolean validate = true;

		validate = validateBpcs(bpcsTran.getBpcs(),errors,canBeBlank);

		bpcsTran.getBpcs().appendMsg(this.valActionCode(bpcsTran.getActionCode()));
		if(!strAction.equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_MASS_UPDATE)){
		bpcsTran.getBpcs().appendMsg(this.valPriceWithCost(bpcsTran.getActionCode(),
											 bpcsTran.getBpcs().getBillPrice(),bpcsTran.getBpcs().getBpCurCode(),
											 bpcsTran.getBpcs().getCostPrice(),bpcsTran.getBpcs().getCostCurCode()));
		}
		if(strAction.equalsIgnoreCase(TCGMConstants.URL_PARM_VAL_MASS_UPDATE)){
			if(bpcsTran.getBpcs().getBpCurCode().equalsIgnoreCase(TCGMConstants.LBL_CCOST)
			&& bpcsTran.getBpcs().getCostCurCode().equalsIgnoreCase(TCGMConstants.LBL_CBP)){
				bpcsTran.getBpcs().appendMsg("BP and Cost Cur Codes swapping not allowed in a single transaction" + TCGMConstants.BR);	
			}
			
			if(bpcsTran.getBpcs().getBillPrice().equalsIgnoreCase(TCGMConstants.LBL_COST)
			&& bpcsTran.getBpcs().getCostPrice().equalsIgnoreCase(TCGMConstants.LBL_BP)){
				bpcsTran.getBpcs().appendMsg("Bill Price and Cost Price swapping not allowed in a single transaction" + TCGMConstants.BR);	
			}
		}
		//if validate is still true then bpcs validation passed.  check for error messages and
		//if found add an ActionError to the list.
		if(validate)
		{
			if(! bpcsTran.getBpcs().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
				validate = false;
			}
		}

		return validate;
	}

	/**
	 * @param  bpcs Bpcs
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateBpcs(Bpcs bpcs,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(Bpcs,ActionErrors,boolean)";
		boolean validate = true;

		bpcs.clearMsg();

		bpcs.appendMsg(this.valAff(bpcs.getRptAff(),"Rpt",canBeBlank));

		bpcs.appendMsg(this.valAff(bpcs.getSupAff(),"Sup",canBeBlank));
		bpcs.appendMsg(this.valInvCode(bpcs.getSupProduct().getInvCode(),"Sup",canBeBlank));
		bpcs.appendMsg(this.valList(bpcs.getSupProduct().getList(),"Sup",canBeBlank));
		bpcs.appendMsg(this.valPack(bpcs.getSupProduct().getPack(),"Sup",canBeBlank));

		bpcs.appendMsg(this.valBillPrice(bpcs.getBillPrice(),canBeBlank));
		bpcs.appendMsg(this.valCostPrice(bpcs.getCostPrice(),canBeBlank));

		//bpcs.appendMsg(this.valBpCurCode(bpcs.getBpCurCode(),canBeBlank));
		//bpcs.appendMsg(this.valCostCurCode(bpcs.getCostCurCode(),canBeBlank));

		bpcs.appendMsg(this.valCostWithCurCode(bpcs.getCostPrice(),bpcs.getCostCurCode()));
		bpcs.appendMsg(this.valPriceWithCurCode(bpcs.getBillPrice(),bpcs.getBpCurCode()));

		bpcs.appendMsg(this.valFreezeCost(bpcs.getFreezeCost().toUpperCase(),canBeBlank));

		bpcs.appendMsg(this.valBegPeriod(bpcs.getBegPeriod(),canBeBlank));
		bpcs.appendMsg(this.valEndPeriod(bpcs.getEndPeriod(),canBeBlank));
		bpcs.appendMsg(this.valPeriodRange(bpcs.getBegPeriod(),bpcs.getEndPeriod(),canBeBlank));

		if(! bpcs.getMsg().equals(""))
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
			validate = false;
		}
		return validate;
	}

	/*****************************************************************************************/
	/**
	 * @param notesTran NotesTran
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return boolean
	 */
	public boolean validateNotesTran(NotesTran notesTran,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(NotesTran,ActionErrors,boolean)";
		boolean validate = true;

		validate = validateNotes(notesTran.getNotes(),errors,canBeBlank);

		notesTran.getNotes().appendMsg(this.valActionCode(notesTran.getActionCode()));

		//if validate is still true then notes validation passed.  check for error messages and
		//if found add an ActionError to the list.
		if(validate)
		{
			if(! notesTran.getNotes().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
				validate = false;
			}
		}

		return validate;
	}

	/**
	 * @param notes Notes
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateNotes(Notes notes,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(Notes,ActionErrors,boolean)";
		boolean validate = true;

		notes.clearMsg();

		notes.appendMsg(this.valAff(notes.getRptAff(),"Rpt",canBeBlank));
		notes.appendMsg(this.valInvCode(notes.getRptProduct().getInvCode(),"Rpt",canBeBlank));
		notes.appendMsg(this.valList(notes.getRptProduct().getList(),"Rpt",canBeBlank));
		notes.appendMsg(this.valPack(notes.getRptProduct().getPack(),"Rpt",canBeBlank));
		notes.appendMsg(this.valNote(notes.getNote(),"Note",canBeBlank));

		if(! notes.getMsg().equals(""))
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
			validate = false;
		}

		return validate;
	}

	/*****************************************************************************************/
	/**
	 * @param rateDataTran RateDataTran
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return boolean
	 */
	public boolean validateRateDataTran(RateDataTran rateDataTran,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(RateDataTran,ActionErrors,boolean)";
		boolean validate = true;

		validate = validateRateData(rateDataTran.getRateData(),errors,canBeBlank);

		rateDataTran.getRateData().appendMsg(this.valActionCode(rateDataTran.getActionCode()));

		//if validate is still true then rateData validation passed.  check for error messages and
		//if found add an ActionError to the list.
		if(validate)
		{
			if(! rateDataTran.getRateData().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
				validate = false;
			}
		}

		return validate;
	}

	/**
	 * @param  rateData RateData
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateRateData(RateData rateData,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(RateData,ActionErrors,boolean)";
		boolean validate = true;

		rateData.clearMsg();

		rateData.appendMsg(this.valRate(rateData.getRate(),canBeBlank));
		//rateData.appendMsg(this.valCurCode(rateData.getCurCode(),canBeBlank));
		rateData.appendMsg(this.valRateWithCurCode(rateData.getRate(),rateData.getCurCode()));

		rateData.appendMsg(this.valBegPeriod(rateData.getBegPeriod(),canBeBlank));
		rateData.appendMsg(this.valEndPeriod(rateData.getEndPeriod(),canBeBlank));
		rateData.appendMsg(this.valPeriodRange(rateData.getBegPeriod(),rateData.getEndPeriod(),canBeBlank));

		if(! rateData.getMsg().equals(""))
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
			validate = false;
		}
		return validate;
	}

	/**
	 * @param rateExTran RateExTran
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return boolean
	 */
	public boolean validateRateExTran(RateExTran rateExTran,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(RateExTran,ActionErrors,boolean)";
		boolean validate = true;

		validate = validateRateEx(rateExTran.getRateEx(),errors,canBeBlank);

		rateExTran.getRateEx().appendMsg(this.valActionCode(rateExTran.getActionCode()));

		//if validate is still true then rateEx validation passed.  check for error messages and
		//if found add an ActionError to the list.
		if(validate)
		{
			if(! rateExTran.getRateEx().getMsg().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
				validate = false;
			}
		}

		return validate;
	}
	/**
	 * @param asr RateEx
	 * @param errors ActionErrors
	 * @param canBeBlank boolean
	 * @return true if validation passes
	 */
	public boolean validateRateEx(RateEx rateEx,ActionErrors errors,boolean canBeBlank)
	{
		String methodName = "validate(RateEx,ActionErrors,boolean)";
		boolean validate = true;

		rateEx.clearMsg();

		rateEx.appendMsg(this.valAff(rateEx.getEndAff(),"End",canBeBlank));
		rateEx.appendMsg(this.valInvCode(rateEx.getEndProduct().getInvCode(),"End",canBeBlank));
		rateEx.appendMsg(this.valList(rateEx.getEndProduct().getList(),"End",canBeBlank));
		rateEx.appendMsg(this.valPack(rateEx.getEndProduct().getPack(),"End",canBeBlank));

		rateEx.appendMsg(this.valAff(rateEx.getRptAff(),"Rpt",canBeBlank));
		rateEx.appendMsg(this.valInvCode(rateEx.getRptProduct().getInvCode(),"Rpt",canBeBlank));
		rateEx.appendMsg(this.valList(rateEx.getRptProduct().getList(),"Rpt",canBeBlank));
		rateEx.appendMsg(this.valPack(rateEx.getRptProduct().getPack(),"Rpt",canBeBlank));

		rateEx.appendMsg(this.valAff(rateEx.getSupAff(),"Sup",canBeBlank));
		rateEx.appendMsg(this.valInvCode(rateEx.getSupProduct().getInvCode(),"Sup",canBeBlank));
		rateEx.appendMsg(this.valList(rateEx.getSupProduct().getList(),"Sup",canBeBlank));
		rateEx.appendMsg(this.valPack(rateEx.getSupProduct().getPack(),"Sup",canBeBlank));

		rateEx.appendMsg(this.valBegPeriod(rateEx.getBegPeriod(),canBeBlank));
		rateEx.appendMsg(this.valEndPeriod(rateEx.getEndPeriod(),canBeBlank));
		rateEx.appendMsg(this.valPeriodRange(rateEx.getBegPeriod(),rateEx.getEndPeriod(),canBeBlank));

		rateEx.appendMsg(this.valBpfRate(rateEx.getBpfRate(),canBeBlank));
		rateEx.appendMsg(this.valCostfRate(rateEx.getCostfRate(),canBeBlank));

		rateEx.appendMsg(this.valBppRate(rateEx.getBppRate(),canBeBlank));
		rateEx.appendMsg(this.valCostpRate(rateEx.getCostpRate(),canBeBlank));

		if(! rateEx.getMsg().equals(""))
		{
			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
			validate = false;
		}

		return validate;
	}

//	/**
//	 * @param afffBpc AffBpc
//	 * @param errors ActionErrors
//	 * @param canBeBlank boolean
//	 * @return true if validation passes
//	 */
//	public boolean validateAffBpc(AffBpc afffBpc,ActionErrors errors,boolean canBeBlank)
//	{
//		String methodName = "validate(AffBpc,ActionErrors,boolean)";
//		boolean validate = true;
//
//		afffBpc.clearMsg();
//
//		afffBpc.appendMsg(this.validateModelCopy(afffBpc.getSelModelId(),canBeBlank));
//
//		if(! afffBpc.getMsg().equals(""))
//		{
//			errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.validation"));
//			validate = false;
//		}
//
//		return validate;
//	}
	/*****************************************************************************************/

}