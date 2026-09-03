package abbott.ai.tcgm.entities;

import abbott.ai.tcgm.*;
/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class Search implements java.io.Serializable
{
	private String columnName = "";  //name of the table column
	private String value = "";       //value to compare against
	private String compareType = TCGMConstants.ORACLE_EQUALS_COMPARISON; //indicates the comparision type LIKE or =
	private boolean quoted = true;   //indicates if the parameter needs to be quoted
	/*****************************************************************************************/
	/**
	 * Default Constructor
	 */
	public Search()
	{
	}
	/**
	 *
	 * @param columnName Name of the column to search on
	 * @param value Value to search on
	 */
	public Search(String columnName,String value)
	{
		this.setColumnName(columnName);
		this.setValue(value);
	}
	/**
	 *
	 * @param columnName
	 * @param o
	 */
	public Search(String columnName,Object o)
	{
		this.setColumnName(columnName);
		if (o == null)
		{
			this.setValue("");
		}
		else
		{
			this.setValue( o.toString() );
		}
	}


	/**
	 *
	 * @param columnName Name of the column to search on
	 * @param value Value to search on
	 * @param compareType Type of comparision
	 */
	public Search(String columnName,String value,String compareType)
	{
		this.setColumnName(columnName);
		this.setValue(value);
		this.setCompareType(compareType);
	}
	/**
	 *
	 * @param columnName Name of the column to search on
	 * @param value Value to search on
	 * @param compareType Type of comparison
	 * @param quoted Indicates if the clause should be quoted
	 */
	public Search(String columnName,String value,String compareType,boolean quoted)
	{
		this.setColumnName(columnName);
		this.setValue(value);
		this.setCompareType(compareType);
		this.setQuoted(quoted);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return compareType
	 */
	public String getCompareType()
	{
		if(this.compareType == null)
		{
			this.compareType = TCGMConstants.ORACLE_EQUALS_COMPARISON;
		}
		return this.compareType;
	}
	/**
	 *
	 * @param compareType Type of comparison
	 */
	public void setCompareType(String compareType)
	{
		this.compareType = compareType;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param quoted Indicates if the clause should be quoted
	 */
	public void setQuoted(boolean quoted)
	{
		this.quoted = quoted;
	}
	/**
	 *
	 * @return quoted
	 */
	public boolean isQuoted()
	{
		return this.quoted;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param columnName name of the column to search
	 */
	public void setColumnName(String columnName)
	{
		this.columnName = columnName;
	}
	/**
	 *
	 * @return columnName
	 */
	public String getColumnName()
	{
		return this.columnName;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param value Value to search for
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	/**
	 *
	 * @return value
	 */
	public String getValue()
	{
		if(this.value == null)
		{
			this.value = "";
		}
		return this.value;
	}
	/*****************************************************************************************/
	/**
	 * @param currentClause current clause
	 * @return clause
	 */
	public String generateClause(String currentClause)
	{
		String clause = getClause();

		if(! clause.trim().equals(""))
		{
			if(! currentClause.trim().equals(""))
			{
				clause = " AND " + getClause();
			}
		}
		return clause;
	}
	/**
	 *
	 * @return clause
	 */
	public String generateClause()
	{
		return getClause();
	}
	/**
	 *
	 * @return clause
	 */
	private String getClause()
	{
		String clause = "";
		if(! this.getValue().equals("")) //if not "" (getValue will force "" if null)
		{
			clause = " " + this.getColumnName();

			if(this.getCompareType().equals(TCGMConstants.ORACLE_LIKE_COMPARISON))
			{
				clause += " LIKE '%" + value + "%' ";
			}
			else if(this.getCompareType().equals(TCGMConstants.ORACLE_IN_COMPARISON))
			{
				clause += " IN ('" + value.replaceAll(",","','") + "') ";
			}
			else
			{
				if(this.isQuoted())
				{
					clause += " = '" + value + "'";
				}
				else
				{
					clause += " = " + value + " ";
				}
			}
		}
		return clause;
	}
	/*****************************************************************************************/
	/**
	 *
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("Column Name: ");
		sb.append(this.getColumnName());
		sb.append("\nValue: ");
		sb.append(this.getValue());
		sb.append("\nCompare Type: ");
		sb.append(this.getCompareType());
		sb.append("\nQuoted: ");
		sb.append(this.isQuoted());

		return sb.toString();
	}
}