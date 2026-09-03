package abbott.ai.tcgm.data.as400;

import java.sql.*;
import com.ibm.as400.access.*;
import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.exception.TCGMException;
//import abbott.ai.tcgm.data.*;

public class AS400EssbaseDao extends AS400Dao {

    protected final String library = AppConst.essbaseLibrary; //"DWPROD";

    protected Connection getConnection() throws SQLException
    {
    	try{
            if (this._conn == null )
            {
                // AP41 has multiple IP addresses. This particular connection only functions through one ip address
                // DNS entries for ap41 resolve to different ip addresses throughout Abbott.
				 AS400JDBCDataSource datasource = new AS400JDBCDataSource(AppConst.essbaseHost);
                    //datasource.setUser("DTAWHSFTP");
                   //datasource.setPassword("DATAWHSEIN");
				    datasource.setUser(AppConst.getInstance().getEssbaseId().getUserid());
					datasource.setPassword(AppConst.getInstance().getEssbaseId().getPassword());

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

    public AS400EssbaseDao() {
    }
}