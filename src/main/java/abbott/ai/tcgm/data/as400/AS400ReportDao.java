package abbott.ai.tcgm.data.as400;

import java.sql.*;
import com.ibm.as400.access.*;
import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.exception.TCGMException;

public class AS400ReportDao extends AS400Dao {

    protected String library = AppConst.reportLibrary;//"AITCGDVFIL";

    protected Connection getConnection() throws SQLException
    {
    	try{
    	 if (this._conn == null )
        {
            //AS400JDBCDataSource datasource = new AS400JDBCDataSource("AP41");
			AS400JDBCDataSource datasource = new AS400JDBCDataSource(AppConst.reportLibrary);
            //datasource.setUser("TCGMFTP");
            //datasource.setPassword("BRIDGE9QZ");
			datasource.setUser(AppConst.getInstance().getReportFtpId().getUserid());
			datasource.setPassword(AppConst.getInstance().getReportFtpId().getPassword());
            // Create a database connection to the iSeries.
            this._conn = datasource.getConnection();
        }
       }
	   catch(Exception e)
	   {
	   try {
		   throw new TCGMException(this.className,"getConnection()",e.toString());
	   } catch (TCGMException e1) {
		   e1.printStackTrace();
	   }
	}
        return this._conn;
    }

    public AS400ReportDao() {
    }
}