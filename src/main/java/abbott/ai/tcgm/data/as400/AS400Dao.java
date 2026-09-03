package abbott.ai.tcgm.data.as400;

import java.sql.*;
//import com.ibm.as400.access.*;
import abbott.ai.tcgm.data.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public abstract class AS400Dao
{
	protected final String className = this.getClass().getName();//convenience property
	protected Connection _conn = null;

        public AS400Dao()
        {
	}

        abstract Connection getConnection() throws SQLException;


	public void destroy()
	{
		SQLUtil.closeConnection(this._conn);
	}
}