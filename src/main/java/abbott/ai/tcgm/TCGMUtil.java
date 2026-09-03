package abbott.ai.tcgm;

import org.apache.log4j.*;
import abbott.ai.tcgm.exception.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
//import java.util.Vector;
import java.io.*;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.helpers.TCGMStringComposer;
import abbott.ai.tcgm.data.ModelDao;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class TCGMUtil extends TCGMStringComposer
{
	private static final String className = "TCGMUtil";
	private static Logger logger = Logger.getLogger("abbott.ai.tcgm.TCGMUtil");
	private static TCGMUtil instance = null;

	// Spaces for padding
	private static final String SPACES = "                              ";

	/**
	 * Checks to see if an instance of the class has been created and if not creates a
	 * new one before returning it
	 * @return Returns an instance of the class
	 */
	public static TCGMUtil getInstance()
	{
		if(instance == null)
		{
			instance = new TCGMUtil();
		}
		return instance;
	}

	/**
	 * Default Constructor
	 */
	private TCGMUtil()
	{
	}

	/*****************************************************************************************/
	/**
	 * Right pads a string.  Calls pad to do the actual work
	 * @param stringToPad The string that should be padded with the padChar
	 * @param padChar The character to pad the string with
	 * @param padLength Represents the ending length of the string after padding
	 * @return padded string
	 * @throws TCGMException
	 * @see pad
	 */
	public static String padRight(String stringToPad, String padChar, int padLength) throws TCGMException
	{
		return pad(stringToPad,padChar,padLength,false);
	}
	/**
	 * Left pads a string.  Calls pad to do the actual work
	 * @param stringToPad The string that should be padded with the padChar
	 * @param padChar The character to pad the string with
	 * @param padLength Represents the ending length of the string after padding
	 * @return padded string
	 * @throws TCGMException
	 * @see pad
	 */
	public static String padLeft(String stringToPad, String padChar, int padLength) throws TCGMException
	{
		return pad(stringToPad,padChar,padLength,true);
	}
	/**
	 * This function will pad the given string with the specified character until it reaches the given length.<br>
	 * If the stringToPad is null the method returns null<br>
	 * If the padChar is null the method returns the untouched stringToPad<br>
	 * If the padLength - stringToPad.length < 1 the method returns the untouched stringToPad<br>
	 * If the padChar.length is > 1 the method throws a TCGMException<br>
	 *
	 * @param stringToPad The string that should be padded with the padChar
	 * @param padChar The character to pad the string with
	 * @param padLength Represents the ending length of the string after padding
	 * @param padLeft Boolean to indicate if padding should be left or right.
	 * @return padded string
	 * @throws TCGMException
	 */
	private static String pad(String stringToPad, String padChar, int padLength,boolean padLeft) throws TCGMException
	{
		final String methodName = "pad";
		String returnVal = null;

		if(stringToPad != null)
		{
			returnVal = new String(stringToPad);
			int stringToPadLength = stringToPad.length();
			int lengthDifference = padLength - stringToPadLength;

			if(padChar != null && lengthDifference > 0)
			{
				if (padChar.length() != 1)
				{
					throw new TCGMException(className,methodName,"Invalid Pad Character:" +"'"+ padChar +"'.  " + "A single character must be passed in");
				}
				else
				{
					for (int i=0;i < lengthDifference ;i++)
					{
						if(padLeft)
						{
						returnVal = padChar + returnVal;
					}
					else
					{
						returnVal = returnVal + padChar;
					}
					}
				}
			}
		}
		return returnVal;
	}
	/*****************************************************************************************/
	/**
	 * Uses substring method in this class to do the actual work
	 * @param source The source string
	 * @param stringToMatch The substring to find
	 * @return The string to the left of the specified stringToMatch
	 * @see substring(String,String,boolean)
	 */
	public static String leftString(String source,String stringToMatch)
	{
		return substring(source,stringToMatch,true);
	}
	/**
	 * Returns the left number of chars from the given string
	 * @param source The source string
	 * @param length The number of chars to return
	 * @return The specified number of characters from the left of the string that is passed in.
	 * @see substring(String,int,boolean)
	 */
	public static String leftString(String source,int length)
	{
		return substring(source,length,true);
	}
	/*****************************************************************************************/
	/**
	 * Uses substring method in this class to do the actual work
	 * @param source The source string
	 * @param stringToMatch The substring to find
	 * @return The string to the right of the specified stringToMatch
	 * @see substring(String,String,boolean)
	 */
	public static String rightString(String source,String stringToMatch)
	{
		return substring(source,stringToMatch,false);
	}
	/**
	 * Returns the right number of chars from the given string
	 * @param source source string
	 * @param length number of chars to return
	 * @return number of chars specified from the right end of thie string
	 * @see substring(String,int,boolean)
	 */
	public static String rightString(String source,int length)
	{
		return substring(source,length,false);
	}
	/*****************************************************************************************/
	/**
	 * If source is null, return null<br>
	 * If stringToMatch is null, return source
	 *
	 * @param source source string
	 * @param stringToMatch string to find
	 * @param leftSubstring true if left, false for right substring
	 * @return the left portion of the string after the stringToMatch is found
	 */
	private static String substring(String source,String stringToMatch,boolean leftSubstring)
	{
		String methodName = "substring(string,string,boolean)";

		String returnVal = null;

		if(source != null)
		{
			returnVal = new String(source);

			if(stringToMatch != null)
			{
				int stringToMatchLen = stringToMatch.length();
				int position = source.indexOf(stringToMatch);
				if(position >= 0) // then a match was found
				{
					if(leftSubstring)
					{
						returnVal = source.substring(0,position);
					}
					else
					{
						returnVal = source.substring(position + stringToMatchLen);
					}
				}
			}
		}
		return returnVal;
	}

	/**
	 *
	 * @param source The source string
	 * @param length The length of string to return
	 * @param leftSubstring Indicates if return a substring from the left or the right
	 * @return The substring
	 */
	private static String substring(String source, int length, boolean leftSubstring)
	{
		String methodName = "subtring(String,int,boolean)";

		String retVal = null;

		if (source != null)
		{
			if (source.length() < length)
			{
				retVal = source;
			}
			else
			{
				if(leftSubstring)
				{
					retVal = source.substring(0,length);
				}
				else
				{
					retVal = source.substring(source.length() - length,source.length());
				}
			}
		}
		return retVal;
	}
	/*****************************************************************************************/
	/**
	 * isEmpty returns true if the string that was passed in is null or ""
	 * @param source String object to check for empty
	 * @return boolean
	 */
	public static boolean isEmpty(String source)
	{
		boolean returnVal = false;
		if(source == null || source.trim().equals(""))
		{
			returnVal = true;
		}
		return returnVal;
	}
	/**
	 * isEmpty returns true if the Double that was passed in is null
	 * @param source object to check for empty
	 * @return boolean
	 */
	public static boolean isEmpty(Double source)
	{
		boolean returnVal = false;
		if(source == null)
		{
			returnVal = true;
		}
		return returnVal;
	}
	/**
	 * isEmpty returns true if the Float that was passed in is null
	 * @param source object to check for empty
	 * @return boolean
	 */
	public static boolean isEmpty(Float source)
	{
		boolean returnVal = false;
		if(source == null)
		{
			returnVal = true;
		}
		return returnVal;
	}
	/**
	 * isEmpty returns true if the Integer that was passed in is null
	 * @param source object to check for empty
	 * @return boolean
	 */
	public static boolean isEmpty(Integer source)
	{
		boolean returnVal = false;
		if(source == null)
		{
			returnVal = true;
		}
		return returnVal;
	}
	/**
	 * isEmpty returns true if the Long that was passed in is null
	 * @param source object to check for empty
	 * @return boolean
	 */
	public static boolean isEmpty(Long source)
	{
		boolean returnVal = false;
		if(source == null)
		{
			returnVal = true;
		}
		return returnVal;
	}
	/**
	 * isEmpty returns true if the Date that was passed in is null
	 * @param source object to check for empty
	 * @return boolean
	 */
	public static boolean isEmpty(Date source)
	{
		boolean returnVal = false;
		if(source == null)
		{
			returnVal = true;
		}
		return returnVal;
	}

	/**
	 * @param n Number
	 * @return boolean
	 */
	public static boolean isNull(Number n)
	{
		boolean returnVal = false;
		if(n == null)
		{
			returnVal = true;
		}
		return returnVal;
	}

	/**
	 * @param in String
	 * @return String
	 */
	public static String escapeString(String in)
	{
		StringBuffer sb = new StringBuffer(in);
		int i = 0;

		while (sb.toString().indexOf("\'", i) > 0)
		{
			i = sb.toString().indexOf("\'", i);
			sb.insert(i, "\\");
			i += 2; // Go 1 past the insert plus 1 for the new character.
		}

		return sb.toString();
	}

	/**
	 * @param f File
	 * @param buffersize int
	 * @return String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readTextFile(File f, int buffersize) throws FileNotFoundException, IOException
	{
		FileReader fr = new FileReader(f);
		
		StringBuffer sb = new StringBuffer();
		char[] buffer = new char[buffersize];
		
		while (( fr.read(buffer)) >= 0)
		{
			sb.append(buffer);
		}
		if(sb.toString().length()>0){
			parmReplace(sb, "%4", AppConst.jclJobLogLoc.trim());  //"MSGCLASS=R");		//Job log location DEV - R or PROD - Z
			parmReplace(sb, "%5", AppConst.jclUserId.trim());  // "NOTIFY=TSBAMD");	//Person to be notified USER ID
			parmReplace(sb, "%6", AppConst.jclReadDataSetIden.trim());  // "AB");				//Dataset Identification PROD - AB or DEV - DV
			parmReplace(sb, "%7", AppConst.jclDataSetIden.trim());  // "DV");				//Dataset Identification PROD - AB or DEV - DV
			parmReplace(sb, "%8", AppConst.jclHostName1.trim());  // "AISHRDQA");			//Main Frame Host Name
			parmReplace(sb, "%9", AppConst.jclUserPass1.trim());  // "tcgmadm Tcgm2007");	//User ID and Password
			parmReplace(sb, "%A", AppConst.jclImportsLoc.trim());  // "/ManageData/IMPORTS");//Imports directory location
			parmReplace(sb, "%B", AppConst.jclExportsLoc.trim());  // "/ManageData/EXPORTS");//Exports directory location
			parmReplace(sb, "%C", AppConst.jclHostName2.trim());  // "AP41");				 //Essbase Host Name
			parmReplace(sb, "%D", AppConst.jclUserPass2.trim());  // "DTAWHSFTP DATAWHSEIN");//User ID and Password
		}
		logger.debug(f + " File Contents \n"+sb.toString().trim());
		return sb.toString().trim();
	}

	/**
	 * It appears as if Oracle does not store the lead zero for a value similar to this: 0.00102
	 * Because of this, the like queries are not working correctly.
	 * I am going to use this method to determine if there is only 1 digit to the left of the decimal place
	 * If there is only 1 digit and the value is a zero, remove it and return the portion after the decimal place
	 * (including the decimal point itself).
	 * The only time to not remove the lead 0 is if the value is something like 0.0  In that case I will just
	 * return 0 since that is the way Oracle stores it.
	 * @return String
	 * @param source String
	 */
	public static String getNumTrimLeadZero(String source)
	{
		String methodName = "getNumTrimLeadZero(String)";

		String numTrimLeadZero = "";

		//If the source is not null and not ""
		if( source != null && !source.trim().equals("") )
		{
			numTrimLeadZero = source.trim();

			if(Double.parseDouble(numTrimLeadZero) == 0)
			{
				numTrimLeadZero = "0";
			}
			else
			{
				String leftOfDecimal = leftString(numTrimLeadZero,".");
				String rightOfDecimal = rightString(numTrimLeadZero,".");

				if(leftOfDecimal.length() == 1 && leftOfDecimal.equals("0"))
				{
					numTrimLeadZero = "." + rightOfDecimal;
				}
			}
		}
		return numTrimLeadZero;
	}

		public static String getRandomDigitStr(int numDigits) {

			if (numDigits > 15) throw new IllegalArgumentException("Number of digits must be 15 or less.");

			return String.valueOf( Math.random() ).substring(2, 2+numDigits);
		}


	/**
	 * This method checks a newValue and if not null or "" then
	 * it returns that value or else it returns the original value
	 * Do NOT trim the new value as there may be times when spaces are
	 * desired.  You may trim the resulting string if necessary or trim the
	 * value being passed in.
	 * @param currentValue
	 * @param newValue
	 * @return
	 */
	public static String getNewValue(String currentValue,String newValue)
	{
		String retVal = currentValue;

		if(newValue != null && ! newValue.equals(""))
		{
			retVal = newValue;
		}
		return retVal;
	}

	public static String deriveUnits(ModelDao md, int modelId) throws TCGMException
	{
		String methodName = "deriveUnits( )";
		String parameterList = "ModelDao: " + md + " Model ID: " + modelId;

		FactorModel fm = (FactorModel) md.getModel(modelId);
		String cycleName = fm.getModelCycleShortName();
		String cycleYear = fm.getModelYear().trim();
		String unitsName = cycleName +  cycleYear.substring(2,4);

		logger.debug("#### Units Name: " + unitsName);
		return unitsName;
	}

	/**
	 *
	 * @param invName
	 * @return
	 * @throws TCGMException
	 *
	 * 9-24-03
	 * The value returned for the unit version is essentially identical to what was done in the
	 * orignial ADL code. Although the actual java code looks different than the ADL code, the
	 * result is the same. The returning value will always be the prefix of the unit set name.
	 * According to ADL code, these values could be: PLN, ACT, APR, AUG, INV, CALCI, CEQCI
	 */
	public static String getUnitVersion(String invName) throws TCGMException
	{
		String version = "";
		version = invName.substring(0, invName.length()-2);
// 9-24-03 Its OK to return INV1, INV2, or INV3; I don't have to return only INV
//		if ((version.equals("INV1")) || (version.equals("INV2")) || (version.equals("INV3")))
//		{
//			version = "INV";
//		}

		logger.debug("TCGMUTIL.getUnitVersion(" + invName + ") returned unit version: " + version);
		return version;
	}

	/**
	 *
	 * @param invName
	 * @return
	 * @throws TCGMException
	 */
	public static String getUnitYear(String invName) throws TCGMException, NumberFormatException
	{

		logger.debug("Inv Unit Name = " + invName);

			String yearTwo, yearFour;
			yearTwo = invName.substring(invName.length()-2, invName.length());
		    logger.debug("Two year = " + yearTwo);

			if((Integer.parseInt(yearTwo) < 40))
			{
				  yearFour = new String("20" + yearTwo);
			}
			else
			{
				  yearFour = new String("19" + yearTwo);
			}

		    logger.debug("Year: " + yearTwo);
		    logger.debug("Year Four: " + yearFour);
			return yearFour;
	}

	public static boolean costExchangeNameIsValid(String createName) throws TCGMException
	{

		logger.debug("Cost Exchange Name = " + createName);

		String year, month;
		try
		{

			year = createName.substring(createName.length()-4, createName.length()-2);
			month = createName.substring(0, 2);

			// Must be 4 chars long
			if(createName.length() != 6)
			{
				return false;
			}
			else
			// Month must be between 1 & 12
			if(!((Integer.parseInt(month) >= 1) && (Integer.parseInt(month) <= 12)) )
			{
				return false;
			}
			else
			// Year must be between 0 & 99
			if(!((Integer.parseInt(month) >= 0) || (Integer.parseInt(month) <= 99)) )
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	
	/**
	 * This function usefull to eliminate the special characters like '\r\n', '&' etc 
	 * @param strString String
	 * @return String
	 */
	public static String replaceAllChars(String strString)
	{
		String strEliminateChars = "\r\n,&,`,~,!";
		StringTokenizer st = new StringTokenizer(strEliminateChars, ",");
		String strTempChar = null;
		while (st.hasMoreElements()){
			strTempChar = st.nextToken();
			strString = strString.replaceAll(strTempChar, "");
		}
		
	return strString;
	}
	public static HashMap getSortedMap(HashMap hmap, String listName)
	{
		HashMap map = new LinkedHashMap();
		List mapKeys = new ArrayList(hmap.keySet());
		List mapValues = new ArrayList(hmap.values());
		//hmap.clear();
		TreeSet sortedSet = null;
		if(listName.equalsIgnoreCase(TCGMConstants.AFFILIATE)){
			sortedSet = new TreeSet(new ListComperator(0));
		}else if(listName.equalsIgnoreCase(TCGMConstants.SECTOR)){
			sortedSet = new TreeSet(new ListComperator(0));
		}else if(listName.equalsIgnoreCase(TCGMConstants.AREA)){
			sortedSet = new TreeSet(new ListComperator(0));
		}else if(listName.equalsIgnoreCase(TCGMConstants.DIVISION)){
			sortedSet = new TreeSet(new ListComperator(0));
		}
		
		for (int j=0; j<mapValues.size(); j++){
			sortedSet.add(mapValues.get(j));
		}
		
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;
	//	 a) Ascending sort
	
		for (int i=0; i<size; i++)
		{	
			map.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), 
											  sortedArray[i]);
		}
		
		return map;
	}
	public static HashMap getSortedHashMap(HashMap hmap)
	{
		HashMap map = new LinkedHashMap();
		List mapKeys = new ArrayList(hmap.keySet());
		List mapValues = new ArrayList(hmap.values());
		//hmap.clear();
		TreeSet sortedSet = new TreeSet(mapValues);
		Object[] sortedArray = sortedSet.toArray();
		int size = sortedArray.length;
	//	 a) Ascending sort
	
		for (int i=0; i<size; i++)
		{	
			map.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), 
		                                      sortedArray[i]);
		}
		return map;
	}
}