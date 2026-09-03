package abbott.ai.tcgm.data.as400;

//import java.io.*;
//import java.net.*;
import java.sql.*;

//import com.ibm.as400.access.*;

//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.exception.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AS400EssbaseLoadDao extends AS400EssbaseDao
{

	/**
	 *
	 */
	public AS400EssbaseLoadDao()
	{
	}

	/**
	 *
	 * @param month
	 * @param year
	 * @param version
	 * @throws TCGMException
	 */
	public void initiateRGMLoad(String month, String year, String version) throws TCGMException
	{
		String methodName = "initiateRGMLoad(String month, String year, String version)";
		String parameterList = "Month: " + month + ", Year: " + year + ", Version: " + version;
		String sql = "INSERT INTO " + library + ".RGMTRG (RGMMNTH, RGMYEAR, RGMVERSION) VALUES (?, ?, ?)";
		try
		{
			PreparedStatement ps = this.getConnection().prepareStatement(sql);
			ps.setString(1, month);
			ps.setString(2, year);
			ps.setString(3, version);
			ps.execute();
		}
		catch (SQLException sqle)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqle.toString() );
		}
	}

	/**
	 *
	 * @param year
	 * @param version
	 * @throws TCGMException
	 */
	public void initiateVersionCopy(String year, String fversion, String tversion) throws TCGMException
	{
		String methodName = "initiateVersionCopy(String year, String version)";
		String parameterList = "Year: " + year + ", FVersion: " + fversion + ", TVersion: " + tversion;
		String sql = "INSERT INTO " + library + ".CPYVTRG (CPYYEAR, CPYFRVER, CPYTOVER) VALUES (?, ?, ?)";
		try
		{
			PreparedStatement ps = this.getConnection().prepareStatement(sql);
			ps.setString(1, year);
			ps.setString(2, fversion);
			ps.setString(3, tversion);
			ps.executeUpdate();
		}
		catch (SQLException sqle)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqle.toString() );
		}
	}

	/**
	 *
	 * @param year
	 * @param version
	 * @throws TCGMException
	 */
	public void initiateALOGLoad(String year, String version) throws TCGMException
	{
		String methodName = "initiateALOGLoad(String year, String version, String month, String loadType)";
		String parameterList = "Year: " + year + ", Version: " + version;
		String sql = "INSERT INTO " + library + ".APPVTRG (APPVYEAR, APPVVER) VALUES (?, ?)";
		try
		{
			PreparedStatement ps = this.getConnection().prepareStatement(sql);
			ps.setString(1, year);
			ps.setString(2, version);
		    
		    ps.executeUpdate();
		}
		catch (SQLException sqle)
		{
			throw new TCGMException( this.className,methodName, parameterList, sqle.toString() );
		}
	}

	public void insertAnlFlexEssTriggerRecord(String year, String version, String type) throws TCGMException
	{
		String methodName = "insertEssbaseTriggerRecord(String year, String version, String type)";
		String parameterList = "Year: " + year + ", Version: " + version + "Type: " + type;
		String sql = "INSERT INTO " + library + ".TCGMTRG (TCGMYEAR, TCGMVER, TCGMTYP) VALUES (?, ?, ?)";
		try
		{
			PreparedStatement ps = this.getConnection().prepareStatement(sql);
			ps.setString(1, year);
			ps.setString(2, version);
			ps.setString(3, type.substring(type.length()-2,type.length()));
			ps.executeUpdate();
		}
		catch (SQLException sqle)
		{
			throw new TCGMException( this.className,methodName, parameterList, sql.toString()+sqle.toString() );
		}
	}

	public void insertProductEssTriggerRecord(String strFlag) throws TCGMException
	{
		String methodName = "insertAffProductEssTriggerRecord(String strFlag)";
		String parameterList = "strFlag: " + strFlag;
		String sql = "INSERT INTO " + library + ".PRDDIMTRG (DIMBLD) VALUES (?)";
		try
		{
			PreparedStatement ps = this.getConnection().prepareStatement(sql);
			ps.setString(1, strFlag);
			ps.executeUpdate();
		}
		catch (SQLException sqle)
		{
			throw new TCGMException( this.className,methodName, parameterList, sql.toString()+sqle.toString() );
		}
	}

	public void insertAffEssTriggerRecord(String strFlag) throws TCGMException
	{
		String methodName = "insertAffProductEssTriggerRecord(String strFlag)";
		String parameterList = "strFlag: " + strFlag;
		String sql = "INSERT INTO " + library + ".LOCDIMTRG (DIMBLD) VALUES (?)";
		try
		{
			PreparedStatement ps = this.getConnection().prepareStatement(sql);
			ps.setString(1, strFlag);
			ps.executeUpdate();
		}
		catch (SQLException sqle)
		{
			throw new TCGMException( this.className,methodName, parameterList, sql.toString()+sqle.toString() );
		}
	}


}