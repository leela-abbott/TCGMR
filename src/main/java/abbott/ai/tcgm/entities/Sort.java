package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.data.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class Sort implements java.io.Serializable
{
	private String sortColumn="";
	private String sortOrder=DBConst.SORT_ASC;

	/**
	 * There was a problem on the AsrTran page.  When the JSP engine compiled it into a servlet
	 * the page stopped loading and gave me invalid branch errors.  After looking this error up
	 * it was determined that it was caused by having a method that was greater than 32kb.  I had
	 * a lot of logic:equal tags on the page.  In order to remove the logic tags and make the page smaller
	 * I added this property.  The getter method will determine the sort order from the sortObject and return
	 * the appropriate graphic.
	 */
	private String sortImg = DBConst.SORT_IMG_ASC;
	private String sortImgAltTxt = DBConst.SORT_IMG_TXT_ASC;
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Sort()
	{
	}
	/**
	 *
	 * @param sortColumn Column to sort by
	 * @param sortOrder Order of sort
	 */
	public Sort(String sortColumn,String sortOrder)
	{
		this.setSortColumn(sortColumn);
		this.setSortOrder(sortOrder);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return column to sort by
	 */
	public String getSortColumn()
	{
		return this.sortColumn;
	}
	/**
	 *
	 * @param sortColumn column to sort by
	 */
	public void setSortColumn(String sortColumn)
	{
		this.sortColumn = sortColumn;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param sortOrder order of sort
	 */
	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
	}
	/**
	 *
	 * @return order of sort
	 */
	public String getSortOrder()
	{
		if(this.sortOrder == null || this.sortOrder.trim().equals(""))
		{
			this.sortOrder = DBConst.SORT_ASC;
		}
		return this.sortOrder;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return The sorting img that indicates the sort is ascending or descending.
	 */
	public String getSortImg()
	{
		if(this.getSortOrder().equals(DBConst.SORT_ASC))
		{
			this.sortImg = DBConst.SORT_IMG_ASC;
		}
		else
		{
			this.sortImg = DBConst.SORT_IMG_DESC;
		}
		return this.sortImg;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return the text to display on the img tag
	 */
	public String getSortImgAltTxt()
	{
		if(this.getSortOrder().equals(DBConst.SORT_ASC))
		{
			this.sortImgAltTxt = DBConst.SORT_IMG_TXT_ASC;
		}
		else
		{
			this.sortImgAltTxt = DBConst.SORT_IMG_TXT_DESC;
		}
		return this.sortImgAltTxt;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Sort Column: ");
		sb.append(this.getSortColumn());
		sb.append("\nSort Order: ");
		sb.append(this.getSortOrder());
		sb.append("\nSort Img: ");
		sb.append(this.getSortImg());
		sb.append("\nSort Img Alt Txt: ");
		sb.append(this.getSortImgAltTxt());

		return sb.toString();
	}
}