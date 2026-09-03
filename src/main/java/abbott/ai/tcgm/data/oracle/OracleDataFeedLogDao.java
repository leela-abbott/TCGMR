package abbott.ai.tcgm.data.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.sql.RowSet;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.DataFeedLogDao;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.DataFeedLogEntry;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;


/**
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class OracleDataFeedLogDao extends OracleDao implements DataFeedLogDao
{
	/*****************************************************************************************/
	/**
	 * @return
	 * @throws TCGMException
	 */
	public ArrayList getAllEntities() throws TCGMException
	{
		String methodName = "getAllEntities()";
		String parameterList = "";

    ArrayList list = new ArrayList();

    try
        {
            this.initRS("SELECT * FROM " + this.getEntity() + " order by " + DBConst.COL_DFL_END_TIME  + " desc" , TCGMConstants.JDBC_ROWSET );
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

  public void writeLogEntry(DataFeedLogEntry entry) throws TCGMException {
      String methodName = "writeLogEntry(DataFeedLogEntry entry)";
      String sql = "{ CALL " + this.schema + ".DATAFEED_LOG_WRITE(?, ?, ?, ?) }";
      PreparedStatement ps = null;
      Connection conn = null;
      // PARAMETERS: filepath, completion message, start time, end time
      try {
      	  conn = this.getConnection();
          ps = conn.prepareCall(sql);
          ps.setString(1, entry.getFullFileName() );
          ps.setString(2, entry.getCompletionMsg());
          ps.setTimestamp(3, entry.getStartTime() );
          ps.setTimestamp(4, entry.getEndTime() );
          ps.execute();
      }
      catch(SQLException sqle)
      {
          logException(className,methodName,sqle);
          throw new TCGMException(this.className,methodName,sqle.toString());
      }
      finally
      {
          SQLUtil.closePS(ps);
          SQLUtil.closeConnection(conn);
      }
	}

  public void clearLog() throws TCGMException {
    String methodName = "clearLog()";
    String sql = "DELETE FROM " + this.getEntity();
    this.executeUpdateSql(sql);
  }

  protected Object getObjectFromCurrentRow(RowSet rs) throws TCGMException
  {

        String methodName = "getObjectFromCurrentRow(RowSet)";
		TimeZone tz = TimeZone.getTimeZone(AppConst.getTcgmTimeZone());
		long rawOffset = tz.getRawOffset();
		long time;
		long newTime;
		Date newDate;
		SimpleDateFormat sdf = new SimpleDateFormat(AppConst.getTcgmDateFormat());
		//if (tz.useDaylightTime())
		if(tz.inDaylightTime(new Date()))
		{
			rawOffset += Integer.parseInt(TCGMConstants.DT_OFFSET);
		}

        DataFeedLogEntry entry = null;

        try
        {
          entry = new DataFeedLogEntry();
          entry.setFileByPath(rs.getString(DBConst.COL_DFL_FILEPATH));    
		  Timestamp timeStamp = this.rs.getTimestamp(DBConst.COL_DFL_START_TIME);
		  entry.setStartTime(timeStamp);		
		  if (!(timeStamp == null))
		  {
			  time = timeStamp.getTime();
			  newTime = time;
			  if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
			  {
				  newTime += rawOffset;
			  }
			  newDate = new Date (newTime);
			  entry.setStartTimeInStringFormat(sdf.format(newDate));
		  }
		  else
		  {
			  entry.setStartTimeInStringFormat(" ");
		  }

		  timeStamp = this.rs.getTimestamp(DBConst.COL_DFL_END_TIME);
		  entry.setEndTime(timeStamp);

		  if (!(timeStamp == null))
		  {
			  time = timeStamp.getTime();
			  newTime = time;
			  if (AppConst.getServerTimeZone().equals(TCGMConstants.DT_GMT))
			  {
				  newTime += rawOffset;
			  }
			  newDate = new Date (newTime);
				
			  entry.setEndTimeInStringFormat(sdf.format(newDate));
		  }
		  else
		  {
			  entry.setEndTimeInStringFormat(" ");
		  }
				
          entry.setCompletionMsg(rs.getString(DBConst.COL_DFL_COMP_MSG) );
        }
        catch(SQLException sqle)
        {
            logException(className,methodName,sqle);
            throw new TCGMException(className,methodName,sqle.toString());
        }

        return entry;
  }



	/*****************************************************************************************/
	/**
	 * @param userToken
	 */
	public OracleDataFeedLogDao(UserToken userToken)
	{
		this.userToken = userToken;
		this.setEntityTable(DBConst.TABLE_DATA_FEED_LOG);
	}
}
