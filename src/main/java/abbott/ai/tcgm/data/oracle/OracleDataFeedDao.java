package abbott.ai.tcgm.data.oracle;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;

import java.sql.*;
import javax.sql.*;

import java.util.*;


/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class OracleDataFeedDao extends OracleDao implements DataFeedDao
{
	/*****************************************************************************************/
	/**
	 * @return
	 * @throws TCGMException
	 */
	public ArrayList getAllDataFeeds() throws TCGMException
	{
		String methodName = "getAllDataFeeds()";
		String parameterList = "";

    ArrayList list = new ArrayList();

    try
        {
            this.initRS("SELECT * FROM " + this.getEntity(), TCGMConstants.JDBC_ROWSET );
            this.rs.execute();

            while (rs.next())
            {
                list.add(this.getObjectFromCurrentRow(rs));
            }
            return list;
        }
        catch(SQLException sqle)
        {
            logException(className,methodName,sqle);
            throw new TCGMException(this.className,methodName,sqle.toString());
        }
        finally
        {
            SQLUtil.closeRowSet(rs);
        }
	}


  protected Object getObjectFromCurrentRow(RowSet rs) throws TCGMException
  {

        String methodName = "getObjectFromCurrentRow(RowSet)";

        DataFeed dataFeed = null;

        try
        {
          String dbLoc = rs.getString(DBConst.COL_DF_DB_LOC);
          String asLoc = rs.getString(DBConst.COL_DF_AS_LOC);
          String ext = rs.getString(DBConst.COL_DF_EXT);
          String sp = rs.getString(DBConst.COL_DF_PROC);
		  String jobType = rs.getString(DBConst.COL_DF_JOB_TYPE);
          dataFeed = new DataFeed(dbLoc, asLoc, ext, sp, jobType);
        }
        catch(SQLException sqle)
        {
            logException(className,methodName,sqle);
            throw new TCGMException(className,methodName,sqle.toString());
        }

        return dataFeed;
  }



	/*****************************************************************************************/
	/**
	 * @param userToken
	 */
	public OracleDataFeedDao(UserToken userToken)
	{
		this.userToken = userToken;
		this.setEntityTable(DBConst.TABLE_DATA_FEED);
	}
}
