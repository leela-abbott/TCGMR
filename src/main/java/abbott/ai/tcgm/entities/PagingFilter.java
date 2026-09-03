package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: Used to page through oracle result sets</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class PagingFilter implements java.io.Serializable
{
	private int startRecord=1;
	private int recordsToRetrieve=TCGMConstants.MAX_RECS_TO_RETRIEVE;
	private long totalRecordsInSet=0;
	private long recordNumberInSet=1;

	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public PagingFilter()
	{
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return first record to retrieve from the database (lower bounds)
	 */
	public int getStartRecord()
	{
		return this.startRecord;
	}
	/**
	 * Convenience method
	 * @return first record to retrieve from the database (lower bounds)
	 */
	public String getStartRecordString()
	{
		return new Integer(this.getStartRecord()).toString();
	}
	/**
	 *
	 * @param startRecord first record to retrieve from the database (lower bounds)
	*/
	public void setStartRecord(int startRecord)
	{
		this.startRecord = startRecord;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param recordsToRetrieve the total number of records to retrieve from the database for each page
	 */
	public void setRecordsToRetrieve(int recordsToRetrieve)
	{
		this.recordsToRetrieve = recordsToRetrieve;
	}
	/**
	 *
	 * @return the total number of records to retireve from the database for each page
	 */
	public int getRecordsToRetrieve()
	{
		return this.recordsToRetrieve;
	}
	/**
	 * Convenience method
	 * @return the total number of records to retireve from the database for each page
	 */
	public String getRecordsToRetrieveString()
	{
		return new Integer(this.getRecordsToRetrieve()).toString();
	}
	/*****************************************************************************************/
	/**
	 * For our purposes we only return a portion of those records (identified by recordsToRetrieve) but
	 * we want to display the total available.  This also lets us know how many pages of data will be
	 * available.
	 * @param totalRecordsInSet the total number of records that could be returned from the query.
	 */
	public void setTotalRecordsInSet(long totalRecordsInSet)
	{
		this.totalRecordsInSet = totalRecordsInSet;
	}
	/**
	 *
	 * @return the total number of records that could be returned from the query.
	 */
	public long getTotalRecordsInSet()
	{
		return this.totalRecordsInSet;
	}
	/**
	 * Convenience method
	 * @return the total number of records that could be returned from the query.
	 */
	public String getTotalRecordsInSetString()
	{
		return new Long(this.getTotalRecordsInSet()).toString();
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return end record to retrieve from database
	 */
	public long getEndRecord()
	{
		return this.startRecord + this.recordsToRetrieve - 1;
	}
	/**
	 *
	 * @return the record to display as the last record on the screen.  It may not be the endRecord value
	 * if the endRecord value is not the same as the totalRecordsInSet
	 */
	public long getEndRecordForDisplay()
	{
		long endRecord = this.getEndRecord();

		if( endRecord > this.totalRecordsInSet )
		{
			endRecord = this.totalRecordsInSet;
		}
		return endRecord;
	}
	/*****************************************************************************************/
	/**
	 * If the last record in the display (ie: 101 - 200) is less than
	 * the totalRecordsInSet (ie: 238) then return true so we can hide the
	 * next page button
	 * @return a boolean to indicate if the next pag button should be displayed
	 */
	public boolean getDispNextPage()
	{
		boolean dispNext = true;
		if( this.getEndRecord() >= this.totalRecordsInSet )
		{
			dispNext = false;
		}
		return dispNext;
	}
	/**
	 * If the startRecord in the display (ie: 1 - 100) is less than or equal to 1
	 * then return false so we can hide the prev page button
	 * @return a boolean to indicate if the prev page button should be displayed
	 */
	public boolean getDispPrevPage()
	{
		boolean dispPrev = true;
		if(this.startRecord <= 1)
		{
			dispPrev = false;
		}
		return dispPrev;
	}

	/*****************************************************************************************/
	/** Calculates and sets the start record number of the page based on the
	 *  user entered record number.
	 */
	public void setDispSelectedRecordPage()
	{
		boolean dispSelected = true;
		if((this.recordNumberInSet < 1) ||
		   (this.recordNumberInSet > this.totalRecordsInSet))
		{
			dispSelected = false;
		}
		else
		{
			int varInt = (int) ((this.recordNumberInSet - 1)/recordsToRetrieve);
			this.setStartRecord(varInt * recordsToRetrieve + 1);

		}
	}

	/*****************************************************************************************/
	/**
	 * If not on the last page then set the start record forward 1 page
	 *
	 */
	public void setNextpage()
	{
		if(this.getEndRecord() < this.getTotalRecordsInSet())
		{
			this.startRecord += this.recordsToRetrieve;
		}
	}
	/**
	 * If not on the first page then set the start record back 1 page
	 */
	public void setPrevPage()
	{
		if(this.startRecord > 1)
		{
			this.startRecord -= this.recordsToRetrieve;
		}
	}
	/*****************************************************************************************/
	/* @param recordNumberInSet the record number in set that could be returned from the query.
	 */
	public void setRecordNumberInSet(long recordNumberInSet)
	{
		this.recordNumberInSet = recordNumberInSet;
	}
	/**
	 *
	 * @return the record number in set that could be returned from the query.
	 */
	public long getRecordNumberInSet()
	{
		return this.recordNumberInSet;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return string representation of the properties and values in this object
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Records To Retrieve: ");
		sb.append(this.getRecordsToRetrieve());
		sb.append("\nStart Record: " );
		sb.append(this.getStartRecord());
		sb.append("\nEnd Record: " );
		sb.append(this.getEndRecord());
		sb.append("\nTotal Records in Set: ");
		sb.append(this.getTotalRecordsInSet());

		return sb.toString();
	}
}