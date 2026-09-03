package abbott.ai.tcgm.data.oracle;

//import org.apache.log4j.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.RowSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.data.AffBpcDao;
import abbott.ai.tcgm.data.DBConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.entities.AffBpc;
import abbott.ai.tcgm.entities.PagingFilter;
import abbott.ai.tcgm.entities.Search;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Brian A. Dennis
 * @version 1.0
 */
public class OracleAffBpcDao extends OracleDao implements AffBpcDao
{
	private AffBpc searchObject = null;
	private PagingFilter pagingFilter = null;
	private Sort sortObject = DBConst.DEF_SORT_AFF_BPC;
//	private final String SELECT = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID,RPT_AFF,SUP_AFF, " +
	private final String SUP_AFF_SELECT = "SELECT AFF_GROUP, max(CREATE_DATETIME) as CREATE_DATETIME,cycle_id FROM " ;
	private final String MIDDLE_SELECT_START = "SELECT ROWNUM AS RN,DATASET_TABLE_ID,MODEL_ID,RPT_AFF,SUP_AFF, " +
				 "SUP_INV_CD,SUP_LIST,SUP_LABEL,SUP_SIZE,SUP_PACK,BP_CUR_CD,COST_CUR_CD, " +
				 "BILL_PRICE, COST_PRICE, ACD, CREATE_USERNAME,CREATE_DATETIME,MODIFY_USERNAME,MODIFY_DATETIME, AFFILIATE,CYCLE_ID " +
				 "FROM (";
	/*****************************************************************************************/

	/**
	 *
	 * @param userToken
	 * @param searchObject
	 * @param pagingFilter
	 * @param sortObject
	 */
	public OracleAffBpcDao(UserToken userToken,AffBpc searchObject,PagingFilter pagingFilter,Sort sortObject)
	{
		this.setEntityTable(DBConst.TABLE_AFFBPC_FILE);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
		this.sortObject = sortObject;
	}
	/**
	 *
	 * @param userToken UserToken object
	 * @param searchObject AffBpc object
	 * @param pagingFilter PagingFilter object
	 */
	public OracleAffBpcDao(UserToken userToken,AffBpc searchObject, PagingFilter pagingFilter)
	{
		this.setEntityTable(DBConst.TABLE_AFFBPC_FILE);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
		this.pagingFilter = pagingFilter;
	}
	/**
	 *
	 * @param userToken UserToken object
	 * @param searchObject AffBpc object
	 */
	public OracleAffBpcDao(UserToken userToken,AffBpc searchObject)
	{
		this.setEntityTable(DBConst.TABLE_AFFBPC_FILE);
		this.userToken = userToken;
		this.setSearchObject(searchObject);
	}
	/**
	 *
	 * @param userToken
	 */
	public OracleAffBpcDao(UserToken userToken)
	{
		this.setEntityTable(DBConst.TABLE_AFFBPC_FILE);
		this.userToken = userToken;
	}
	/*****************************************************************************************/
	/**
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @param searchObject AffBpc object with search criteria
	 * @param sortObject Sort object with sort criteria
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS(AffBpc searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getRS(AffBpc,Sort)";
		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getRS();
	}
	/**
	 *
	 * @param searchObject
	 * @return
	 * @throws TCGMException
	 */
	public RowSet getRS(AffBpc searchObject) throws TCGMException
	{
		String methodName = "getRS(AffBpc)";
		this.setSearchObject(searchObject);
		return this.getRS();
	}
	/**
	 *
	 * This method queries the database for a RowSet.  It does NOT close the RowSet.
	 * If you use this method make sure the calling class closes the RowSet when it is finished with it.
	 * @return RowSet
	 * @throws TCGMException
	 */
	public RowSet getRS() throws TCGMException
	{
		String methodName = "getRS()";

		if (this.sortObject.getSortColumn().equalsIgnoreCase(DBConst.COL_DEF))
		{
			this.sortObject.setSortColumn(DBConst.COL_AFF_BPC_DEF);
		}

		try
		{
			String query = this.MIDDLE_SELECT_START +
				  this.INNER_SELECT +
				  this.getEntity() +
				  this.genWhereClause() +
				  this.buildEBCDICSortClause(this.sortObject) +
				  //this.buildSortClause(this.sortObject) +
				  this.MIDDLE_SELECT_END;

			//if a paging filter exists then we need to change the sql to add the outer sql clause
			if(this.pagingFilter != null)
			{
				query = this.OUTER_SELECT + query + this.OUTER_WHERE_MIN_BOUND + this.pagingFilter.getStartRecord() + this.OUTER_WHERE_MAX_BOUND + this.pagingFilter.getEndRecord();
			}

			this.logger.debug("\n\nOracleAffBpcDao - getRS QUERY: \n" + query);
			logger.error("Query from BPC Upload :"+query);

			this.initRS(query,TCGMConstants.JDBC_ROWSET);
			rs.execute();

			return rs;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param searchObject
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public Vector getVO(AffBpc searchObject,Sort sortObject) throws TCGMException
	{
		String methodName = "getVO(AffBpc,Sort)";

		this.setSearchObject(searchObject);
		this.sortObject = sortObject;
		return this.getVO();
	}
	/**
	 *
	 * @param searchObject
	 * @return
	 * @throws TCGMException
	 */
	public Vector getVO(AffBpc searchObject) throws TCGMException
	{
		String methodName="getVO(AffBpc)";

		this.setSearchObject(searchObject);
		return this.getVO();
	}

	/**
	 *
	 * @return Vector of AffBpc objects
	 * @throws TCGMException
	 */
	public Vector getVO() throws TCGMException
	{
		String methodName = "getVO";

		Vector vec = new Vector();

		try
		{
			this.getRS();

			while (rs.next())
			{
				vec.add(this.getAffBpcFromCurrentRow(rs));
			}
			return vec;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(this.className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(this.className,methodName,e.toString());
		}
		finally
		{
			SQLUtil.closeRowSet(rs);
		}
	}
	/**
	 * This method will be used to convert the "next()" RowSet ojbect to an ASR object
	 * @param rs RowSet
	 * @return AffBpc
	 * @throws TCGMException
	 */
	public AffBpc getAffBpcFromCurrentRow(RowSet rs) throws TCGMException
	{
		String methodName = "getAffBpcFromCurrentRow(RowSet)";

		AffBpc affBpc = new AffBpc();

		try
		{
			affBpc.setDatasetTableId(rs.getString(DBConst.COL_DATASET_TABLE_ID));
			affBpc.setModelIdInt(rs.getInt(DBConst.COL_MODEL_ID));

			affBpc.setRptAff(rs.getString(DBConst.COL_RPT_AFF));
			affBpc.setSupAff(rs.getString(DBConst.COL_SUP_AFF));

			affBpc.getSupProduct().setInvCode(rs.getString(DBConst.COL_SUP_INV_CD));
			affBpc.getSupProduct().setList(rs.getString(DBConst.COL_SUP_LIST));
			affBpc.getSupProduct().setLabel(rs.getString(DBConst.COL_SUP_LABEL));
			affBpc.getSupProduct().setSize(rs.getString(DBConst.COL_SUP_SIZE));
			affBpc.getSupProduct().setPack(rs.getString(DBConst.COL_SUP_PACK));

			affBpc.setBpCurCode(rs.getString(DBConst.COL_BP_CUR_CD));
			affBpc.setCostCurCode(rs.getString(DBConst.COL_COST_CUR_CD));
			affBpc.setBillPrice(rs.getString(DBConst.COL_BILL_PRICE));
			affBpc.setCostPrice(rs.getString(DBConst.COL_COST_PRICE));

			affBpc.getCreateLog().setUserName(rs.getString(DBConst.COL_CREATE_USERNAME));
			affBpc.getCreateLog().setDate(rs.getDate(DBConst.COL_CREATE_DATETIME));

			affBpc.getModifyLog().setUserName(rs.getString(DBConst.COL_MODIFY_USERNAME));
			affBpc.getModifyLog().setDate(rs.getDate(DBConst.COL_MODIFY_DATETIME));
			
			affBpc.setAffiliate(rs.getString(DBConst.COL_AFFILIATE));
			affBpc.setCycleId(rs.getString("CYCLE_ID"));
			return affBpc;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className,methodName,e.toString());
		}
	}


	/*****************************************************************************************/
	/**
	 * @param affBpc AffBpc object
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public void delete(AffBpc affBpc,Connection conn) throws TCGMException
	{
		String methodName = "delete(AffBpc,Connection)";
		boolean connWasNull = false;

		this.setSearchObject(affBpc);

		PreparedStatement ps = null;
		String sql = this.DELETE_FROM + this.getEntity();

		/**
		 * If we have a search object that contains an asrTranId value
		 * then we know that the user performed a delete selected and we
		 * can delete based on the id (it will be unique).
		 * If we don't have that value then the user did a delete all and we
		 * are deleting based on the filter criteria so build a where clause
		 * using the object passed in as a searchObject.
		 */
		if(affBpc.getAffBpcId().equals(""))
		{
			sql += this.genWhereClause();
		}
		else
		{
			sql += " where AFFBPC_ID = " + affBpc.getAffBpcId();
		}
		this.logger.debug("\nSQL: " + sql);

		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs
				//to be closed here.
				connWasNull = true;
			}

			ps = conn.prepareStatement(sql);

			ps.execute();
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className, methodName, e.toString());
		}
		finally
		{
			SQLUtil.closePS(ps);
			if(connWasNull)
			{
				//The connection was created within the method and not passed in
				//So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}

	/*****************************************************************************************/
	/**
	 * @param conn Connection
	 * @throws TCGMException
	 */
	public ArrayList getSupAff(Connection conn) throws TCGMException
	{
		String methodName = "getSupAff(Connection conn)";
		boolean connWasNull = false;

		Statement stmt = null;
		String sql = this.SUP_AFF_SELECT + this.getEntity() + "group by AFF_GROUP,cycle_id order by cycle_id,AFF_GROUP";
		this.logger.debug("\nSQL: " + sql);
		
		ArrayList supAff = new ArrayList();
		try
		{
			if(conn == null)
			{
				conn = SQLUtil.openConnection();
				//Set this so that we know the connection was not created externally and needs
				//to be closed here.
				connWasNull = true;
			}
			stmt = conn.createStatement();
			logger.error("SQL is "+sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				supAff.add(rs.getString("cycle_id").trim()+ "::" +rs.getString("AFF_GROUP").trim() + "::" + rs.getDate("CREATE_DATETIME"));
			}
			return supAff;
		}
		catch(SQLException sqle)
		{
			logException(className,methodName,sqle);
			throw new TCGMException(className,methodName,sqle.toString());
		}
		catch(Exception e)
		{
			logException(className,methodName,e);
			throw new TCGMException(className, methodName, e.toString());
		}
		finally
		{
			SQLUtil.closeResultSet(rs);
			SQLUtil.closeStatment(stmt);
			if(connWasNull)
			{
				//The connection was created within the method and not passed in
				//So close it here.
				SQLUtil.closeConnection(conn);
			}
		}
	}

	/**
	 * @param affBpcList Vector
	 * @throws TCGMException
	 */
	public void delete(Vector affBpcList) throws TCGMException
	{
		String methodName = "delete(Vector)";

		Connection conn = null;
		try
		{
			conn = SQLUtil.openConnection( );

			for(int i = 0; i < affBpcList.size();i++)
			{
				this.delete((AffBpc)affBpcList.elementAt(i),conn);
			}
		}
		finally
		{
			SQLUtil.closeConnection(conn);
		}
	 }

	/**
	 *
	 * @return
	 */
	private void buildSearchList()
	{
		this.searchList = new Vector();

		//need to build a search object and then loop through it to get the clause.
		this.searchList.add(new Search(DBConst.COL_MODEL_ID,searchObject.getModelId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_DATASET_TABLE_ID,searchObject.getDatasetTableId(),TCGMConstants.ORACLE_EQUALS_COMPARISON,false));
		this.searchList.add(new Search(DBConst.COL_RPT_AFF,searchObject.getRptAff(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_AFF,searchObject.getSupAff(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_INV_CD,searchObject.getSupProduct().getInvCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LIST,searchObject.getSupProduct().getList(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_LABEL,searchObject.getSupProduct().getLabel(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_SIZE,searchObject.getSupProduct().getSize(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_SUP_PACK,searchObject.getSupProduct().getPack(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BP_CUR_CD,searchObject.getBpCurCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COST_CUR_CD,searchObject.getCostCurCode(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_BILL_PRICE,searchObject.getBillPrice(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_COST_PRICE,searchObject.getCostPrice(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search(DBConst.COL_AFFILIATE,searchObject.getAffiliate(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search("AFF_GROUP",searchObject.getAffiliateGroup(),TCGMConstants.ORACLE_LIKE_COMPARISON));
		this.searchList.add(new Search("CYCLE_ID",searchObject.getCycleId(),TCGMConstants.ORACLE_EQUALS_COMPARISON));

	}

	/**
	 *
	 * @return
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.className);
		sb.append(", \n");
		sb.append("User Token,\n");
		sb.append(this.userToken.toString());
		sb.append("\nEntity: ");
		sb.append(this.getEntity());
		sb.append("\n");
		sb.append(this.pagingFilter.toString());
		sb.append("\n");
		sb.append(this.sortObject.toString());
		sb.append("\nSearch Object: ");
		sb.append(this.searchObject);

		return sb.toString();
	}

	/**
	 * Sets the searchObject and calls buildSearchList
	 * @param searchObject AffBpc
	 */
	private void setSearchObject(AffBpc searchObject)
	{
		this.searchObject = searchObject;
		this.buildSearchList();
	}
	/**
	 *
	 * @return SearchObject
	 */
	private AffBpc getSearchObject()
	{
		return this.searchObject;
	}
	
	public void upload(String fileName,String cycle) throws TCGMException,IOException{
		String methodName = "upload(String fileName)";
		String sql = "SELECT DATAFEED_AS_LOC FROM TCGM.DATA_FEED WHERE DATAFEED_PROC='AFFBPC_LOAD'";
		Connection conn=null;
		ResultSet rstSet=null;
		Statement stmt = null;
		String fileNameCreate = "";
		FileWriter out = null;
		try
		{
			conn = SQLUtil.openConnection();
			stmt=conn.createStatement();
			rstSet=stmt.executeQuery(sql);
			if(rstSet.next()){
				fileNameCreate=rstSet.getString(1);

			}		
			fileNameCreate=TCGMUtil.escapeString(fileNameCreate+"\\ABPTXALL-"+cycle+".affbpc");
			
			HSSFWorkbook wb=null;
			POIFSFileSystem fs;	    
			Sheet sheetXls = null;   
			Row sheet;
			InputStream inp=null;
			Workbook wb_xssf=null; //Declare XSSF WorkBook
			Workbook wb_hssf=null; 
			
			String fileExtn = GetFileExtension(fileName);
			inp = new FileInputStream(fileName);
			if (fileExtn.equalsIgnoreCase("xlsx"))
			      {

				       wb_xssf = new XSSFWorkbook(inp);
				       sheetXls = wb_xssf.getSheetAt(0); //Read the first worksheet
				       
			      }
			     if (fileExtn.equalsIgnoreCase("xls"))
			      {
				  
//				      fs = new POIFSFileSystem(inp);
			    	  wb_hssf = new HSSFWorkbook(inp);				    	  
			    	  sheetXls = wb_hssf.getSheetAt(0);
			      }

			out = new FileWriter(new File(fileNameCreate));

			String arrayStr[]=null;
			String appendString="";
			double bill_value_added =0;
			String cost_value_added;
			for (int j = 1; j < sheetXls.getPhysicalNumberOfRows(); j++) {
				sheet=(Row)sheetXls.getRow(j);
				appendString="";
				if(isNotBlank(sheet.getCell(2))){
					break;
				}
				if(getCellStringValue(sheet.getCell(1)).trim().length()>4){
					appendString+=getCellStringValue(sheet.getCell(1)).trim().substring(0,4)+",";
				}else{
					appendString+=getCellStringValue(sheet.getCell(1)).trim()+",";
				}
				if(getCellStringValue(sheet.getCell(0)).trim().length()>4){
					appendString+=getCellStringValue(sheet.getCell(0)).trim().substring(0,4)+",";	
				}else{
					appendString+=getCellStringValue(sheet.getCell(0)).trim()+",";
				}
				
				arrayStr=getCellStringValue(sheet.getCell(2)).trim().split("-");
				appendString+=arrayStr[0]+arrayStr[1]+arrayStr[2]+arrayStr[3]+arrayStr[4]+",";
				
				//bill_value_added =	new Double(getCellDoubleValue(sheet.getCell(5))).doubleValue();
				//appendString+=round(bill_value_added);
				appendString+=returnDouble(getCellDoubleValue(sheet.getCell(5)))+",";
											
				appendString+=getCellStringValue(sheet.getCell(6)).trim()+",";
				
				//cost_value_added=new Double(getCellDoubleValue(sheet.getCell(3))).doubleValue();
				//appendString+=round(cost_value_added);
				appendString+=returnDouble(getCellDoubleValue(sheet.getCell(3)))+",";
							
				appendString+=getCellStringValue(sheet.getCell(4)).trim()+"\n";
				out.write(appendString);
				
			}
			if (out != null ) {
				out.close();
			}
							
		}
		catch(IOException ex){
			if ( out != null ) {
				out.close();
			}
			
			logException(className,methodName,ex);
			throw new TCGMException(className, methodName, ex.toString());
		}
		catch(Exception e)
		{
			if ( out != null ) {
				out.close();
			}
				
			logException(className,methodName,e);
			throw new TCGMException(className, methodName, e.toString());
		}
		finally
		{
			SQLUtil.closeResultSet(rstSet);
			SQLUtil.closeStatment(stmt);
			SQLUtil.closeConnection(conn);
			
		}
	}
	
	
	public String round(String value) {
		String val="";		
		
		
		val=""+returnDouble(value);
		while(val.length()<14){
			val="0"+val;
		}
		
		return val;
	}
	
	private static String GetFileExtension(String fname2)
	{
	    String fileName = fname2;
	    String fname="";
	    String ext="";
	    int mid= fileName.lastIndexOf(".");
	    fname=fileName.substring(0,mid);
	    ext=fileName.substring(mid+1,fileName.length());
	    return ext;
	}

	private boolean isNotBlank(Cell cell)
	{
		if(null==cell){
			return true;
		}
		if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
			return true;
		}
		return false;
	}
	public String getCellStringValue(Cell cell){
		String retVal="";
		//logger.error("Cell Type is  "+cell.getCellType());
		
		try{
		if(null==cell){
			retVal="";
		}
		if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
			retVal="";
		}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
			logger.error("The ratval is "+retVal);
			
			retVal=""+cell.getNumericCellValue();
			logger.error("The ratval is "+retVal);
		}else{
			//retVal=cell.getRichStringCellValue().getString();
			retVal=cell.getStringCellValue();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return retVal.trim();
	}
	public String getCellStringDoubleValue(Cell cell){
		String retVal="";
		int x=0;
		try{
		if(null==cell){
			retVal="";
		}
		if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
			retVal="";
		}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){		
			retVal=""+((int)cell.getNumericCellValue());
		}else{
			retVal=cell.getRichStringCellValue().getString();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return retVal.trim();
	}
	
	public String getCellDoubleValue(Cell cell){
		String retVal="";
		int x=0;
		try{
		if(null==cell){
			retVal="";
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
			retVal="";
		}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){		
			retVal=""+cell.getNumericCellValue();
		}
		else if(cell.getCellType()==Cell.CELL_TYPE_STRING){
			
			retVal=cell.getRichStringCellValue().getString().replaceAll(",", "");			
			retVal=cell.getRichStringCellValue().getString().replaceAll(",", "");
		}
		else{
			retVal=cell.getRichStringCellValue().getString();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return retVal.trim();
	}
	
	
	
	/*
	 * public Long returnDouble(String dob) {
	 * 
	 * String absVal = ""; Long retval = 0L; if (dob.indexOf(".") > 0) { if
	 * (dob.substring(dob.indexOf(".") + 1, dob.length()).length() > 4)
	 * 
	 * {
	 * 
	 * int fourChar = Integer.parseInt(dob.substring(dob.indexOf(".") +
	 * 1,dob.indexOf(".") + 5));
	 * 
	 * int fifthChar = Integer.parseInt(dob.substring(dob.indexOf(".") + 5,
	 * dob.indexOf(".") + 6)); if (fifthChar > 4) { fourChar = fourChar + 1; }
	 * retval = Long.valueOf((Integer.parseInt(dob.substring(0, dob.indexOf("."))) *
	 * 10000) + (fourChar)); } else if (dob.substring(dob.indexOf(".") + 1,
	 * dob.length()).length() == 4) {
	 * 
	 * // retval=Long.valueOf((Integer.parseInt(dob.substring(0,dob.indexOf(".")))*
	 * 10000)+(Integer.parseInt(dob.substring(dob.indexOf(".")+1,dob.length()))));
	 * retval = Long.valueOf( dob.substring(0,
	 * dob.indexOf(".")).concat(dob.substring(dob.indexOf(".") + 1, dob.length())));
	 * } else if (dob.substring(dob.indexOf(".") + 1, dob.length()).length() == 3) {
	 * 
	 * retval = Long.valueOf((Integer.parseInt(dob.substring(0, dob.indexOf("."))) *
	 * 10000) + (Integer.parseInt(dob.substring(dob.indexOf(".") + 1, dob.length()))
	 * 10)); } else if (dob.substring(dob.indexOf(".") + 1, dob.length()).length()
	 * == 2) {
	 * 
	 * retval = Long.valueOf((Integer.parseInt(dob.substring(0, dob.indexOf("."))) *
	 * 10000) + (Integer.parseInt(dob.substring(dob.indexOf(".") + 1, dob.length()))
	 * 100)); } else if (dob.substring(dob.indexOf(".") + 1, dob.length()).length()
	 * == 6) {
	 * 
	 * // retval=Long.valueOf((Integer.parseInt(dob.substring(0,dob.indexOf(".")))*
	 * 10000)+(Integer.parseInt(dob.substring(dob.indexOf(".")+1,dob.length()))*100)
	 * );
	 * 
	 * retval = Long.valueOf( dob.substring(0,
	 * dob.indexOf(".")).concat(dob.substring(dob.indexOf(".") + 1, dob.length())));
	 * } else {
	 * 
	 * retval = Long.valueOf((Integer.parseInt(dob.substring(0, dob.indexOf("."))) *
	 * 10000) + (Integer.parseInt(dob.substring(dob.indexOf(".") + 1, dob.length()))
	 * 1000)); }
	 * 
	 * } else {
	 * 
	 * retval = Long.valueOf((Integer.parseInt(dob) * 10000)); } return retval;//
	 * Double.parseDouble(absVal); }
	 */
	 	
	public String returnDouble(String dob) {
		String absVal = "";
		String retval = "";
		String prexretval;
		String suffretval;
		if (dob.indexOf(".") > 0) {
			if (dob.substring(dob.indexOf(".") + 1, dob.length()).length() > 0) {
				prexretval = dob.substring(0, dob.indexOf("."));
				suffretval = dob.substring(dob.indexOf(".") + 1, dob.length());
				while (prexretval.length() < 15) {

					if (prexretval.length() == 14) {
						break;
					}
					prexretval = "0" + prexretval;
				}

				while (suffretval.length() < 7) {

					if (suffretval.length() == 6) {
						break;
					}
					suffretval = suffretval + "0";
				}

				retval = prexretval +","+ suffretval;

			} else if (dob.substring(dob.indexOf(".") + 1, dob.length()).length() == 4) {

				retval = dob.substring(0, dob.indexOf(".")).concat(dob.substring(dob.indexOf(".") + 1, dob.length()));
			}

		} else {

			// retval = dob.substring(dob.indexOf(".") + 1, dob.length());

			prexretval = dob.substring(0, dob.length());
			suffretval = "000000";

			while (prexretval.length() < 15) {

				if (prexretval.length() == 14) {
					break;
				}
				prexretval = "0" + prexretval;
			}

			retval = prexretval +","+ suffretval;
		}
		return retval;
	}
	 
}