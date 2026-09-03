package abbott.ai.tcgm.data.oracle;

import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.*;

import java.sql.Connection;
/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */
public class OracleDaoFactory extends DaoFactory
{
	String className = this.className;
	/**
	 * Default Constructor
	 */
	public OracleDaoFactory()
	{
		super();
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return UserDao
	 * @throws TCGMException
	 */
	public UserDao getUserDao(UserToken userToken) throws TCGMException
	{
		return new OracleUserDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject User object
	 * @return UserDao
	 * @throws TCGMException
	 */
	public UserDao getUserDao(UserToken userToken,User searchObject) throws TCGMException
	{
		return new OracleUserDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject User object
	 * @param sortObject Sort object
	 * @return UserDao
	 * @throws TCGMException
	 */
	public UserDao getUserDao(UserToken userToken,User searchObject,Sort sortObject) throws TCGMException
	{
		return new OracleUserDao(userToken,searchObject,sortObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param sortObject Sort object
	 * @return UserDao
	 * @throws TCGMException
	 */
	public UserDao getUserDao(UserToken userToken,Sort sortObject) throws TCGMException
	{
		return new OracleUserDao(userToken,sortObject);
	}
	/*****************************************************************************************/
	
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return RptUserDao
	 * @throws TCGMException
	 */
	public RptUserDao getRptUserDao(UserToken userToken) throws TCGMException
	{
		return new OracleRptUserDao(userToken);
	}
	/**
	 *
	 * @param searchObject UserBean object with search criteria
	 * @return userDao object
	 * @throws GPSException
	 */
	public RptUserDao getRptUserDao(RptUser searchObject) throws TCGMException
	{
		return new OracleRptUserDao(searchObject);
	}
	/**
	 *
	 * @param searchObject UserBean object with search criteria
	 * @return userDao object
	 * @throws GPSException
	 */
	public RptUserDao getRptUserDao() throws TCGMException
	{
		return new OracleRptUserDao();
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject RptUser object
	 * @return RptUserDao
	 * @throws TCGMException
	 */
	public RptUserDao getRptUserDao(UserToken userToken,RptUser searchObject) throws TCGMException
	{
		return new OracleRptUserDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject RptUser object
	 * @param sortObject Sort object
	 * @return RptUserDao
	 * @throws TCGMException
	 */
	public RptUserDao getRptUserDao(UserToken userToken,RptUser searchObject,Sort sortObject) throws TCGMException
	{
		return new OracleRptUserDao(userToken,searchObject,sortObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param sortObject Sort object
	 * @return RptUserDao
	 * @throws TCGMException
	 */
	public RptUserDao getRptUserDao(UserToken userToken,Sort sortObject) throws TCGMException
	{
		return new OracleRptUserDao(userToken,sortObject);
	}
	/*****************************************************************************************/


	/**
	 * @param userToken Contains the user id and password
	 * @return AsrDao
	 * @throws TCGMException
	 */
	public AsrDao getAsrDao(UserToken userToken) throws TCGMException
	{
		return new OracleAsrDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Asr object with search criteria
	 * @return AsrDao
	 * @throws TCGMException
	 */
	public AsrDao getAsrDao(UserToken userToken,Asr searchObject) throws TCGMException
	{
		return new OracleAsrDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Asr object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return AsrDao
	 * @throws TCGMException
	 */
	public AsrDao getAsrDao(UserToken userToken,Asr searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleAsrDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Asr object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return AsrDao
	 * @throws TCGMException
	 */
	public AsrDao getAsrDao(UserToken userToken,Asr searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleAsrDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return AstTranDao
	 * @throws TCGMException
	 */
	public AsrTranDao getAsrTranDao(UserToken userToken) throws TCGMException
	{
		return new OracleAsrTranDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject AsrTran object with search criteria
	 * @return AstTranDao
	 * @throws TCGMException
	 */
	public AsrTranDao getAsrTranDao(UserToken userToken,AsrTran searchObject) throws TCGMException
	{
		return new OracleAsrTranDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject AsrTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return AstTranDao
	 * @throws TCGMException
	 */
	public AsrTranDao getAsrTranDao(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleAsrTranDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AsrTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return AsrTranDao
	 * @throws TCGMException
	 */
	public AsrTranDao getAsrTranDao(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleAsrTranDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return BpcsDao
	 * @throws TCGMException
	 */
	public BpcsDao getBpcsDao(UserToken userToken) throws TCGMException
	{
		return new OracleBpcsDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Bpcs object
	 * @return BpcsDao object
	 * @throws TCGMException
	 */
	public BpcsDao getBpcsDao(UserToken userToken, Bpcs searchObject) throws TCGMException
	{
		return new OracleBpcsDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Bpcs object
	 * @param pagingFilter PagingFilter object
	 * @return Bpcs object
	 * @throws TCGMException
	 */
	public BpcsDao getBpcsDao(UserToken userToken, Bpcs searchObject, PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleBpcsDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Bpcs object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return BpcsDao
	 * @throws TCGMException
	 */
	public BpcsDao getBpcsDao(UserToken userToken,Bpcs searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleBpcsDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return BpcsTranDao BpcsTranDao object
	 * @throws TCGMException
	 */
	public BpcsTranDao getBpcsTranDao(UserToken userToken) throws TCGMException
	{
		return new OracleBpcsTranDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcsTran object
	 * @return BpcsTranDao object
	 * @throws TCGMException
	 */
	public BpcsTranDao getBpcsTranDao(UserToken userToken, BpcsTran searchObject) throws TCGMException
	{
		return new OracleBpcsTranDao(userToken, searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcsTran object
	 * @param pagingFilter PagingFilter object
	 * @return BpcsTranDao object
	 * @throws TCGMException
	 */
	public BpcsTranDao getBpcsTranDao(UserToken userToken, BpcsTran searchObject, PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleBpcsTranDao(userToken, searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcsTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return BpcsTranDao
	 * @throws TCGMException
	 */
	public BpcsTranDao getBpcsTranDao(UserToken userToken,BpcsTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleBpcsTranDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return BpcRevDao
	 * @throws TCGMException
	 */
	public BpcRevDao getBpcRevDao(UserToken userToken) throws TCGMException
	{
		return new OracleBpcRevDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRev object
	 * @return BpcRevDao object
	 * @throws TCGMException
	 */
	public BpcRevDao getBpcRevDao(UserToken userToken, BpcRev searchObject) throws TCGMException
	{
		return new OracleBpcRevDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRev object
	 * @param pagingFilter PagingFilter object
	 * @return BpcRev object
	 * @throws TCGMException
	 */
	public BpcRevDao getBpcRevDao(UserToken userToken, BpcRev searchObject, PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleBpcRevDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRev object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return BpcRevDao
	 * @throws TCGMException
	 */
	public BpcRevDao getBpcRevDao(UserToken userToken,BpcRev searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleBpcRevDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return BpcRevTranDao BpcRevTranDao object
	 * @throws TCGMException
	 */
	public BpcRevTranDao getBpcRevTranDao(UserToken userToken) throws TCGMException
	{
		return new OracleBpcRevTranDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRevTran object
	 * @return BpcRevTranDao object
	 * @throws TCGMException
	 */
	public BpcRevTranDao getBpcRevTranDao(UserToken userToken, BpcRevTran searchObject) throws TCGMException
	{
		return new OracleBpcRevTranDao(userToken, searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRevTran object
	 * @param pagingFilter PagingFilter object
	 * @return BpcRevTranDao object
	 * @throws TCGMException
	 */
	public BpcRevTranDao getBpcRevTranDao(UserToken userToken, BpcRevTran searchObject, PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleBpcRevTranDao(userToken, searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRevTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return BpcRevTranDao
	 * @throws TCGMException
	 */
	public BpcRevTranDao getBpcRevTranDao(UserToken userToken,BpcRevTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleBpcRevTranDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return BpcExDao
	 * @throws TCGMException
	 */
	public BpcExDao getBpcExDao(UserToken userToken) throws TCGMException
	{
		return new OracleBpcExDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcEx object with search criteria
	 * @return BpcExDao
	 * @throws TCGMException
	 */
	public BpcExDao getBpcExDao(UserToken userToken,BpcEx searchObject) throws TCGMException
	{
		return new OracleBpcExDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcEx object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return BpcExDao
	 * @throws TCGMException
	 */
	public BpcExDao getBpcExDao(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleBpcExDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcEx object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return BpcExDao
	 * @throws TCGMException
	 */
	public BpcExDao getBpcExDao(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleBpcExDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return BpcExTranDao
	 * @throws TCGMException
	 */
	public BpcExTranDao getBpcExTranDao(UserToken userToken) throws TCGMException
	{
		return new OracleBpcExTranDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcExTran object with search criteria
	 * @return BpcExTranDao
	 * @throws TCGMException
	 */
	public BpcExTranDao getBpcExTranDao(UserToken userToken,BpcExTran searchObject) throws TCGMException
	{
		return new OracleBpcExTranDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcExTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return BpcExTranDao
	 * @throws TCGMException
	 */
	public BpcExTranDao getBpcExTranDao(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleBpcExTranDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcExTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return BpcExTranDao
	 * @throws TCGMException
	 */
	public BpcExTranDao getBpcExTranDao(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleBpcExTranDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return RateDataTranDao
	 * @throws TCGMException
	 */
	public RateDataTranDao getRateDataTranDao(UserToken userToken) throws TCGMException
	{
		return new OracleRateDataTranDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateTran object with search criteria
	 * @return RateDataTranDao
	 * @throws TCGMException
	 */
	public RateDataTranDao getRateDataTranDao(UserToken userToken,RateDataTran searchObject) throws TCGMException
	{
		return new OracleRateDataTranDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return RateDataTranDao
	 * @throws TCGMException
	 */
	public RateDataTranDao getRateDataTranDao(UserToken userToken,RateDataTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleRateDataTranDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return RateDataTranDao
	 * @throws TCGMException
	 */
	public RateDataTranDao getRateDataTranDao(UserToken userToken,RateDataTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleRateDataTranDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return RateExTranDao
	 * @throws TCGMException
	 */
	public RateExTranDao getRateExTranDao(UserToken userToken) throws TCGMException
	{
		return new OracleRateExTranDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateTran object with search criteria
	 * @return RateExTranDao
	 * @throws TCGMException
	 */
	public RateExTranDao getRateExTranDao(UserToken userToken,RateExTran searchObject) throws TCGMException
	{
		return new OracleRateExTranDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return RateExTranDao
	 * @throws TCGMException
	 */
	public RateExTranDao getRateExTranDao(UserToken userToken,RateExTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleRateExTranDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return RateExTranDao
	 * @throws TCGMException
	 */
	public RateExTranDao getRateExTranDao(UserToken userToken,RateExTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleRateExTranDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return RateExDao
	 * @throws TCGMException
	 */
	public RateExDao getRateExDao(UserToken userToken) throws TCGMException
	{
		return new OracleRateExDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Rate object with search criteria
	 * @return RateExDao
	 * @throws TCGMException
	 */
	public RateExDao getRateExDao(UserToken userToken,RateEx searchObject) throws TCGMException
	{
		return new OracleRateExDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Rate object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return RateExDao
	 * @throws TCGMException
	 */
	public RateExDao getRateExDao(UserToken userToken,RateEx searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleRateExDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Rate object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return RateExDao
	 * @throws TCGMException
	 */
	public RateExDao getRateExDao(UserToken userToken,RateEx searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleRateExDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return AffBpcDao
	 * @throws TCGMException
	 */
	public AffBpcDao getAffBpcDao(UserToken userToken) throws TCGMException
	{
		return new OracleAffBpcDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AffBpc object with search criteria
	 * @return AffBpcDao
	 * @throws TCGMException
	 */
	public AffBpcDao getAffBpcDao(UserToken userToken,AffBpc searchObject) throws TCGMException
	{
		return new OracleAffBpcDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AffBpc object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return AffBpcDao
	 * @throws TCGMException
	 */
	public AffBpcDao getAffBpcDao(UserToken userToken,AffBpc searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleAffBpcDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AffBpc object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return AffBpcDao
	 * @throws TCGMException
	 */
	public AffBpcDao getAffBpcDao(UserToken userToken,AffBpc searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleAffBpcDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return ProcessDao object
	 * @throws TCGMException
	 */
	
	//The below method is added for AsrUsageUpload.
	
	/*
	 * public AsrDao getAsrUsageDao(UserToken userToken,AffBpc searchObject) throws
	 * TCGMException { return new OracleAsrDao(userToken,searchObject); }
	 */
	
	/*
	 * public AsrDao getAsrUsageDao(UserToken userToken) throws TCGMException {
	 * return new OracleAsrDao(userToken); }
	 */
	
	//The below method is added ended here for AsrUsageUpload.
	public ProcessDao getProcessDao (UserToken userToken) throws TCGMException
	{
		return new OracleProcessDao(userToken);
	}

	public ProcessDao getProcessDao (UserToken userToken, Connection conn) throws TCGMException
	{
		return new OracleProcessDao(userToken, conn);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Dataset object with search criteria
	 * @return DatasetDao
	 * @throws TCGMException
	 */
	public DatasetDao getDatasetDao(UserToken userToken,Dataset searchObject) throws TCGMException
	{
		return new OracleDatasetDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return DatasetDao
	 * @throws TCGMException
	 */
	public DatasetDao getDatasetDao(UserToken userToken) throws TCGMException
	{
		return new OracleDatasetDao(userToken);
	}

	public DatasetDao getDatasetDao(UserToken userToken, Connection conn) throws TCGMException
	{
		return new OracleDatasetDao(userToken, conn);
	}

	public UnitDao getUnitDao(UserToken userToken) throws TCGMException
	{
		return new OracleUnitDao(userToken);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return RateDataDao
	 * @throws TCGMException
	 */
	public RateDataDao getRateDataDao(UserToken userToken) throws TCGMException
	{
		return new OracleRateDataDao(userToken);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Rate object with search criteria
	 * @return RateDataDao
	 * @throws TCGMException
	 */
	public RateDataDao getRateDataDao(UserToken userToken,RateData searchObject) throws TCGMException
	{
		return new OracleRateDataDao(userToken,searchObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Rate object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return RateDataDao
	 * @throws TCGMException
	 */
	public RateDataDao getRateDataDao(UserToken userToken,RateData searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleRateDataDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Rate object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return RateDataDao
	 * @throws TCGMException
	 */
	public RateDataDao getRateDataDao(UserToken userToken,RateData searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleRateDataDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return ModelDao
	 * @throws TCGMException
	 */
	public ModelDao getModelDao(UserToken userToken) throws TCGMException
	{
		String methodName = "getModelDao(UserToken)";

		throw new TCGMException(className,methodName,"Method Not Supported");

	}
	/**
	 * @param userToken Contains the user id and password
	 * @param mtype TCGMModel.Type
	 * @return ModelDao
	 * @throws TCGMException
	 */
	public ModelDao getModelDao(UserToken userToken, TCGMModel.Type mtype) throws TCGMException
	{
		if (mtype == TCGMModel.Type.ANALYSIS)
		{
			return new OracleAnalysisModelDao(userToken);
		}
		else if (mtype == TCGMModel.Type.FACTOR)
		{
			return new OracleFactorModelDao(userToken);
		}
		else if (mtype == TCGMModel.Type.PERPETUAL)
		{
			return new OraclePerpetualModelDao(userToken);
		}
		else if (mtype == TCGMModel.Type.COSTEXCH)
		{
			return new OracleCostExchModelDao(userToken);
		}
		else
		{
			throw new TCGMException(this.getClass().toString(), "getModelDao(UserToken, ModelType)", "Invalid model type specified for dao factory");
		}
	}
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @return Report Dao
	 * @throws abbott.ai.tcgm.exception.TCGMException
	 */
	public ReportDao getReportDao(UserToken userToken) throws abbott.ai.tcgm.exception.TCGMException
	{
		// Essbase loads are not managed through the Oracle
		return new OracleReportDao(userToken);
	}
	public ReportDao getReportDao(UserToken userToken, Connection conn) throws abbott.ai.tcgm.exception.TCGMException
	{
		// Essbase loads are not managed through the Oracle
		return new OracleReportDao(userToken, conn);
	}

		/*****************************************************************************************/
		/**
		 * @param userToken Contains the user id and password
		 * @return ReportInstanceDao
		 * @throws abbott.ai.tcgm.exception.TCGMException
		 */
		public ReportInstanceDao getReportInstanceDao(UserToken userToken) throws abbott.ai.tcgm.exception.TCGMException
		{
				// Essbase loads are not managed through the Oracle
				return new OracleReportInstanceDao(userToken);
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return NotesDao object
	 */
	public NotesDao getNotesDao(UserToken userToken) throws TCGMException
	{
		return new OracleNotesDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Notes object with search criteria
	 * @throws TCGMException
	 * @return NotesDao object
	 */
	public NotesDao getNotesDao(UserToken userToken,Notes searchObject) throws TCGMException
	{
		return new OracleNotesDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Notes object with search criteria
	 * @throws TCGMException
	 * @return NotesDao object
	 */
	public NotesDao getNotesDao(UserToken userToken,Notes searchObject, PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleNotesDao(userToken,searchObject,pagingFilter);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject Notes
	 * @param sortObject Sort
	 * @return NotesDao
	 * @throws TCGMException
	 */
	public NotesDao getNotesDao(UserToken userToken,Notes searchObject, PagingFilter pagingFilter, Sort sortObject) throws TCGMException
	{
		return new OracleNotesDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param sortObject Sort object
	 * @return NotesDao
	 * @throws TCGMException
	 */
	public NotesDao getNotesDao(UserToken userToken, PagingFilter pagingFilter, Sort sortObject) throws TCGMException
	{
		return new OracleNotesDao(userToken,pagingFilter,sortObject);
	}

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return NotesTranDao
	 * @throws TCGMException
	 */
	public NotesTranDao getNotesTranDao(UserToken userToken) throws TCGMException
	{
		return new OracleNotesTranDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject NotesTran object with search criteria
	 * @return NotesTranDao
	 * @throws TCGMException
	 */
	public NotesTranDao getNotesTranDao(UserToken userToken,NotesTran searchObject) throws TCGMException
	{
		return new OracleNotesTranDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject NotesTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @return NotesTranDao
	 * @throws TCGMException
	 */
	public NotesTranDao getNotesTranDao(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter) throws TCGMException
	{
		return new OracleNotesTranDao(userToken,searchObject,pagingFilter);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject NotesTran object with search criteria
	 * @param pagingFilter contains paging criteria
	 * @param sortObject contains sort criteria
	 * @return NotesTranDao
	 * @throws TCGMException
	 */
	public NotesTranDao getNotesTranDao(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException
	{
		return new OracleNotesTranDao(userToken,searchObject,pagingFilter,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return CurrencyCodeDao object
	 */
	public CurrencyCodeDao getCurrencyCodeDao(UserToken userToken) throws TCGMException
	{
		return new OracleCurrencyCodeDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject CurrencyCode object with search criteria
	 * @throws TCGMException
	 * @return CurrencyCodeDao object
	 */
	public CurrencyCodeDao getCurrencyCodeDao(UserToken userToken,CurrencyCode searchObject) throws TCGMException
	{
		return new OracleCurrencyCodeDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject CurrencyCode
	 * @param sortObject Sort
	 * @return CurrencyCodeDao
	 * @throws TCGMException
	 */
	public CurrencyCodeDao getCurrencyCodeDao(UserToken userToken,CurrencyCode searchObject,Sort sortObject) throws TCGMException
	{
		return new OracleCurrencyCodeDao(userToken,searchObject,sortObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param sortObject Sort object
	 * @return CurrencyCodeDao
	 * @throws TCGMException
	 */
	public CurrencyCodeDao getCurrencyCodeDao(UserToken userToken,Sort sortObject) throws TCGMException
	{
		return new OracleCurrencyCodeDao(userToken,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return AffCstCurDao object
	 */
	public AffCstCurDao getAffCstCurDao(UserToken userToken) throws TCGMException
	{
		return new OracleAffCstCurDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject AffCstCur object with search criteria
	 * @throws TCGMException
	 * @return AffCstCurDao object
	 */
	public AffCstCurDao getAffCstCurDao(UserToken userToken,AffCstCur searchObject) throws TCGMException
	{
		return new OracleAffCstCurDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject AffCstCur
	 * @param sortObject Sort
	 * @return AffCstCurDao
	 * @throws TCGMException
	 */
	public AffCstCurDao getAffCstCurDao(UserToken userToken,AffCstCur searchObject,Sort sortObject) throws TCGMException
	{
		return new OracleAffCstCurDao(userToken,searchObject,sortObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param sortObject Sort object
	 * @return AffCstCurDao
	 * @throws TCGMException
	 */
	public AffCstCurDao getAffCstCurDao(UserToken userToken,Sort sortObject) throws TCGMException
	{
		return new OracleAffCstCurDao(userToken,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return KnollConvDao object
	 */
	public KnollConvDao getKnollConvDao(UserToken userToken) throws TCGMException
	{
		return new OracleKnollConvDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject KnollConv object with search criteria
	 * @throws TCGMException
	 * @return KnollConvDao object
	 */
	public KnollConvDao getKnollConvDao(UserToken userToken,KnollConv searchObject) throws TCGMException
	{
		return new OracleKnollConvDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject KnollConv
	 * @param sortObject Sort
	 * @return KnollConvDao
	 * @throws TCGMException
	 */
	public KnollConvDao getKnollConvDao(UserToken userToken,KnollConv searchObject,Sort sortObject) throws TCGMException
	{
		return new OracleKnollConvDao(userToken,searchObject,sortObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param sortObject Sort object
	 * @return KnollConvDao
	 * @throws TCGMException
	 */
	public KnollConvDao getKnollConvDao(UserToken userToken,Sort sortObject) throws TCGMException
	{
		return new OracleKnollConvDao(userToken,sortObject);
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return PRMfgDao object
	 */
	public PRMfgDao getPRMfgDao(UserToken userToken) throws TCGMException
	{
		return new OraclePRMfgDao(userToken);
	}
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject PRMfg object with search criteria
	 * @throws TCGMException
	 * @return PRMfgDao object
	 */
	public PRMfgDao getPRMfgDao(UserToken userToken,PRMfg searchObject) throws TCGMException
	{
		return new OraclePRMfgDao(userToken,searchObject);
	}
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject PRMfg
	 * @param sortObject Sort
	 * @return PRMfgDao
	 * @throws TCGMException
	 */
	public PRMfgDao getPRMfgDao(UserToken userToken,PRMfg searchObject,Sort sortObject) throws TCGMException
	{
		return new OraclePRMfgDao(userToken,searchObject,sortObject);
	}
	/**
	 * @param userToken Contains the user id and password
	 * @param sortObject Sort object
	 * @return PRMfgDao
	 * @throws TCGMException
	 */
	public PRMfgDao getPRMfgDao(UserToken userToken,Sort sortObject) throws TCGMException
	{
		return new OraclePRMfgDao(userToken,sortObject); 
	}

  public DataFeedDao getDataFeedDao(UserToken userToken) throws TCGMException
  {
	return new OracleDataFeedDao(userToken);
  }

  public DataFeedLogDao getDataFeedLogDao(UserToken userToken) throws TCGMException
  {
	return new OracleDataFeedLogDao(userToken);
  }
  
  /**
   *
   * @param userToken Contains the user id and password
   * @throws TCGMException
   * @return SalesTypeDao object
   */
  public SalesTypeDao getSalesTypeDao(UserToken userToken) throws TCGMException
  {
	  return new OracleSalesTypeDao(userToken);
  }
  /**
   *
   * @param userToken Contains the user id and password
   * @param searchObject AffCstCur object with search criteria
   * @throws TCGMException
   * @return SalesTypeDao object
   */
  public SalesTypeDao getSalesTypeDao(UserToken userToken,SalesType searchObject) throws TCGMException
  {
	  return new OracleSalesTypeDao(userToken,searchObject);
  }
  /**
   *
   * @param userToken UserToken
   * @param searchObject AffCstCur
   * @param sortObject Sort
   * @return SalesTypeDao
   * @throws TCGMException
   */
  public SalesTypeDao getSalesTypeDao(UserToken userToken,SalesType searchObject,Sort sortObject) throws TCGMException
  {
	  return new OracleSalesTypeDao(userToken,searchObject,sortObject);
  }
  /**
   * @param userToken Contains the user id and password
   * @param sortObject Sort object
   * @return SalesTypeDao
   * @throws TCGMException
   */
  public SalesTypeDao getSalesTypeDao(UserToken userToken,Sort sortObject) throws TCGMException
  {
	  return new OracleSalesTypeDao(userToken,sortObject);
  }
  
  public AffAreaDivDao getAffAreaDivDao() throws TCGMException
	  {
		  return new OracleAffAreaDivDao();
	  }
	  
	public  AsrUsageDao getAsrUsageDao(UserToken userToken) throws TCGMException{
			return new OracleAsrUsage(userToken);
		}
			
	public  AsrUsageDao getAsrUsageDao(UserToken userToken,ASRUsage searchObject) throws TCGMException{
		return new OracleAsrUsage(userToken,searchObject);
	}

	public AsrUsageDao getAsrUsageDao(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter) throws TCGMException{
		return new OracleAsrUsage(userToken,searchObject,pagingFilter);
	}

	public AsrUsageDao getAsrUsageDao(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException{
		return new OracleAsrUsage(userToken,searchObject,pagingFilter,sortObject);
	
	}

	@Override
	//Added for ASR SUFF AFF BY Debajyoti
	public AsrUsageDao getAsrUsageDaoForSuffAff(UserToken userToken, ASRUsage searchObject, PagingFilter pagingFilter,
			Sort sortObject, String query, boolean flag) throws TCGMException {		
		return new OracleAsrUsage(userToken,searchObject,pagingFilter,sortObject,query,flag);
	}

  /*****************************************************************************************/
}