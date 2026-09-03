package abbott.ai.tcgm.data;

import java.sql.Connection;

import abbott.ai.tcgm.entities.*;
//import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.oracle.*;
//import abbott.ai.tcgm.data.as400.*;
import abbott.ai.tcgm.exception.*;

/**
 *
 * <p>Title: TCGM Application</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public abstract class DaoFactory
{
	/**
	 * Constant to get an Oracle factory
	 */
	public static final int ORACLE = 1;
	/*****************************************************************************************/
	/**
	 *
	 * @param whichFactory Specifies the factory type to create
	 * @return DaoFactory
	 */
	public static DaoFactory getDaoFactory(int whichFactory)
	{
		switch (whichFactory)
		{
			case ORACLE:
			{
				return new OracleDaoFactory();
			}
			default:
			{
				return null;
			}
		}
	}
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return UserDao object
	 */
	public abstract RptUserDao getRptUserDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param searchObject UserBean object with search criteria
	 * @return userDao object
	 * @throws GPSException
	 */
	public abstract RptUserDao getRptUserDao() throws TCGMException;
	/**
	 *
	 * @param searchObject UserBean object with search criteria
	 * @return userDao object
	 * @throws GPSException
	 */
	public abstract RptUserDao getRptUserDao(RptUser searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject User object with search criteria
	 * @throws TCGMException
	 * @return UserDao object
	 */
	
	public abstract RptUserDao getRptUserDao(UserToken userToken,RptUser searchObject) throws TCGMException;

	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject User
	 * @param sortObject Sort
	 * @return UserDao
	 * @throws TCGMException
	 */
	public abstract RptUserDao getRptUserDao(UserToken userToken,RptUser searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @param userToken
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public abstract RptUserDao getRptUserDao(UserToken userToken,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return UserDao object
	 */
	public abstract UserDao getUserDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject User object with search criteria
	 * @throws TCGMException
	 * @return UserDao object
	 */
	public abstract UserDao getUserDao(UserToken userToken,User searchObject) throws TCGMException;

	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject User
	 * @param sortObject Sort
	 * @return UserDao
	 * @throws TCGMException
	 */
	public abstract UserDao getUserDao(UserToken userToken,User searchObject,Sort sortObject) throws TCGMException;

	/**
	 * @param userToken
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public abstract UserDao getUserDao(UserToken userToken,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return AsrDao object
	 */
	public abstract AsrDao getAsrDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Asr with search criteria
	 * @throws TCGMException
	 * @return AsrDao object
	 */
	public abstract AsrDao getAsrDao(UserToken userToken,Asr searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Asr with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @throws TCGMException
	 * @return AsrDao object
	 */
	public abstract AsrDao getAsrDao(UserToken userToken,Asr searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject Asr with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @param sortObject Sort object with sort criteria
	 * @throws TCGMException
	 * @return AsrDao object
	 */
	public abstract AsrDao getAsrDao(UserToken userToken,Asr searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return AsrTranDao object
	 */
	public abstract AsrTranDao getAsrTranDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AsrTran with search criteria
	 * @throws TCGMException
	 * @return AsrTranDao object
	 */
	public abstract AsrTranDao getAsrTranDao(UserToken userToken,AsrTran searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AsrTran with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @throws TCGMException
	 * @return AsrTranDao object
	 */
	public abstract AsrTranDao getAsrTranDao(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AsrTran with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @param sortObject Sort object with sort criteria
	 * @throws TCGMException
	 * @return AsrTranDao object
	 */
	public abstract AsrTranDao getAsrTranDao(UserToken userToken,AsrTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return BpcsDao object
	 */
	public abstract BpcsDao getBpcsDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Bpcs object with search criteria
	 * @return BpcsDao object
	 * @throws TCGMException
	 */
	public abstract BpcsDao getBpcsDao(UserToken userToken, Bpcs searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Bpcs object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return BpcsDao object
	 * @throws TCGMException
	 */
	public abstract BpcsDao getBpcsDao(UserToken userToken, Bpcs searchObject, PagingFilter pagingFilter) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Bpcs object
	 * @param pagingFilter PaginFilter object
	 * @param sortObject Sort object
	 * @return BpcsDao
	 * @throws TCGMException
	 */
	public abstract BpcsDao getBpcsDao(UserToken userToken,Bpcs searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return BpcsTranDao object
	 */
	public abstract BpcsTranDao getBpcsTranDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcsTran object with search criteria
	 * @return BpcsTranDao object
	 * @throws TCGMException
	 */
	public abstract BpcsTranDao getBpcsTranDao(UserToken userToken, BpcsTran searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcsTran object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return BpcsTranDao object
	 * @throws TCGMException
	 */
	public abstract BpcsTranDao getBpcsTranDao(UserToken userToken, BpcsTran searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcsTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 * @return BpcsTranDao
	 * @throws TCGMException
	 */
	public abstract BpcsTranDao getBpcsTranDao(UserToken userToken,BpcsTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return BpcRevDao object
	 */
	public abstract BpcRevDao getBpcRevDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRev object with search criteria
	 * @return BpcRevDao object
	 * @throws TCGMException
	 */
	public abstract BpcRevDao getBpcRevDao(UserToken userToken, BpcRev searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRev object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return BpcRevDao object
	 * @throws TCGMException
	 */
	public abstract BpcRevDao getBpcRevDao(UserToken userToken, BpcRev searchObject, PagingFilter pagingFilter) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRev object
	 * @param pagingFilter PaginFilter object
	 * @param sortObject Sort object
	 * @return BpcRevDao
	 * @throws TCGMException
	 */
	public abstract BpcRevDao getBpcRevDao(UserToken userToken,BpcRev searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return BpcRevTranDao object
	 */
	public abstract BpcRevTranDao getBpcRevTranDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRevTran object with search criteria
	 * @return BpcRevTranDao object
	 * @throws TCGMException
	 */
	public abstract BpcRevTranDao getBpcRevTranDao(UserToken userToken, BpcRevTran searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRevTran object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return BpcRevTranDao object
	 * @throws TCGMException
	 */
	public abstract BpcRevTranDao getBpcRevTranDao(UserToken userToken, BpcRevTran searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcRevTran object
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 * @return BpcRevTranDao
	 * @throws TCGMException
	 */
	public abstract BpcRevTranDao getBpcRevTranDao(UserToken userToken,BpcRevTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return RateExDao object
	 */
	public abstract RateExDao getRateExDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateEx object with search criteria
	 * @return RateExDao object
	 * @throws TCGMException
	 */
	public abstract RateExDao getRateExDao(UserToken userToken, RateEx searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateEx object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return RateDataEx object
	 * @throws TCGMException
	 */
	public abstract RateExDao getRateExDao(UserToken userToken, RateEx searchObject, PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateEx object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 * @return RateExDao
	 * @throws TCGMException
	 */
	public abstract RateExDao getRateExDao(UserToken userToken, RateEx searchObject, PagingFilter pagingFilter, Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return RateExTranDao object
	 */
	public abstract RateExTranDao getRateExTranDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateExTran object with search criteria
	 * @return RateExTranDao object
	 * @throws TCGMException
	 */
	public abstract RateExTranDao getRateExTranDao(UserToken userToken, RateExTran searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateExTran object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return RateDataEx object
	 * @throws TCGMException
	 */
	public abstract RateExTranDao getRateExTranDao(UserToken userToken, RateExTran searchObject, PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateExTran object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 * @return RateExTranDao
	 * @throws TCGMException
	 */
	public abstract RateExTranDao getRateExTranDao(UserToken userToken, RateExTran searchObject, PagingFilter pagingFilter, Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return BpcExDao object
	 */
	public abstract BpcExDao getBpcExDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcEx with search criteria
	 * @throws TCGMException
	 * @return BpcExDao object
	 */
	public abstract BpcExDao getBpcExDao(UserToken userToken,BpcEx searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcEx with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @throws TCGMException
	 * @return BpcExDao object
	 */
	public abstract BpcExDao getBpcExDao(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcEx with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @param sortObject Sort object with sort criteria
	 * @throws TCGMException
	 * @return BpcExDao object
	 */
	public abstract BpcExDao getBpcExDao(UserToken userToken,BpcEx searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return BpcExTranDao object
	 */
	public abstract BpcExTranDao getBpcExTranDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcExTran with search criteria
	 * @throws TCGMException
	 * @return BpcExTranDao object
	 */
	public abstract BpcExTranDao getBpcExTranDao(UserToken userToken,BpcExTran searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcExTran with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @throws TCGMException
	 * @return BpcExTranDao object
	 */
	public abstract BpcExTranDao getBpcExTranDao(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject BpcExTran with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @param sortObject Sort object with sort criteria
	 * @throws TCGMException
	 * @return BpcExTranDao object
	 */
	public abstract BpcExTranDao getBpcExTranDao(UserToken userToken,BpcExTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return AffBpcDao object
	 */
	public abstract AffBpcDao getAffBpcDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AffBpc with search criteria
	 * @throws TCGMException
	 * @return AffBpcDao object
	 */
	public abstract AffBpcDao getAffBpcDao(UserToken userToken,AffBpc searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AffBpc with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @throws TCGMException
	 * @return AffBpcDao object
	 */
	public abstract AffBpcDao getAffBpcDao(UserToken userToken,AffBpc searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject AffBpc with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @param sortObject Sort object with sort criteria
	 * @throws TCGMException
	 * @return AffBpcDao object
	 */
	public abstract AffBpcDao getAffBpcDao(UserToken userToken,AffBpc searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return ProcessDao object
	 * @throws TCGMException
	 */
	public abstract ProcessDao getProcessDao(UserToken userToken) throws TCGMException;
	public abstract ProcessDao getProcessDao(UserToken userToken, Connection conn) throws TCGMException;
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Dataset with the search criteria
	 * @return DatasetDao object
	 * @throws TCGMException
	 */
	public abstract DatasetDao getDatasetDao(UserToken userToken,Dataset searchObject) throws TCGMException;

	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return DatasetDao object
	 * @throws TCGMException
	 */
	public abstract DatasetDao getDatasetDao(UserToken userToken) throws TCGMException;
	public abstract DatasetDao getDatasetDao(UserToken userToken, Connection conn) throws TCGMException;

	public abstract UnitDao getUnitDao(UserToken userToken) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return RateDataDao object
	 */
	public abstract RateDataDao getRateDataDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateData object with search criteria
	 * @return RateDataDao object
	 * @throws TCGMException
	 */
	public abstract RateDataDao getRateDataDao(UserToken userToken, RateData searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateData object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return RateDataDao object
	 * @throws TCGMException
	 */
	public abstract RateDataDao getRateDataDao(UserToken userToken, RateData searchObject, PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateData object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 * @return RateDataDao
	 * @throws TCGMException
	 */
	public abstract RateDataDao getRateDataDao(UserToken userToken, RateData searchObject, PagingFilter pagingFilter, Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return RateDataTranDao object
	 */
	public abstract RateDataTranDao getRateDataTranDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateDataTran object with search criteria
	 * @return RateDataTranDao object
	 * @throws TCGMException
	 */
	public abstract RateDataTranDao getRateDataTranDao(UserToken userToken, RateDataTran searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateDataTran object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @return RateDataTranDao object
	 * @throws TCGMException
	 */
	public abstract RateDataTranDao getRateDataTranDao(UserToken userToken, RateDataTran searchObject, PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject RateDataTran object with search criteria
	 * @param pagingFilter PagingFilter object
	 * @param sortObject Sort object
	 * @return RateDataTranDao
	 * @throws TCGMException
	 */
	public abstract RateDataTranDao getRateDataTranDao(UserToken userToken, RateDataTran searchObject, PagingFilter pagingFilter, Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @param modelType TCGMModel.Type
	 * @return ModelDao
	 * @throws TCGMException
	 */
	public abstract ModelDao getModelDao(UserToken userToken, TCGMModel.Type modelType) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @return ModelDao
	 * @throws TCGMException
	 */
	public abstract ModelDao getModelDao(UserToken userToken) throws TCGMException;
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @return Report Dao
	 * @throws TCGMException
	 */
	public abstract ReportDao getReportDao(UserToken userToken) throws TCGMException;
	public abstract ReportDao getReportDao(UserToken userToken, Connection conn) throws TCGMException;
		/*****************************************************************************************/
		/**
		 *
		 * @param userToken Contains the user id and password
		 * @return ReportInstance Dao
		 * @throws TCGMException
		 */
	public abstract ReportInstanceDao getReportInstanceDao(UserToken userToken) throws TCGMException;

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return NotesDao object
	 */
	public abstract NotesDao getNotesDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject Notes
	 * @throws TCGMException
	 */
	public abstract NotesDao getNotesDao(UserToken userToken,Notes searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject Notes object with search criteria
	 * @throws TCGMException
	 * @return NotesDao object
	 */
	public abstract NotesDao getNotesDao(UserToken userToken,Notes searchObject,PagingFilter pagingFilter) throws TCGMException;

	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject Notes
	 * @param sortObject Sort
	 * @return NotesDao
	 * @throws TCGMException
	 */
	public abstract NotesDao getNotesDao(UserToken userToken,Notes searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;
	/**
	 * @param userToken
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public abstract NotesDao getNotesDao(UserToken userToken,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return NotesTranDao object
	 */
	public abstract NotesTranDao getNotesTranDao(UserToken userToken) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject NotesTran with search criteria
	 * @throws TCGMException
	 * @return NotesTranDao object
	 */
	public abstract NotesTranDao getNotesTranDao(UserToken userToken,NotesTran searchObject) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject NotesTran with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @throws TCGMException
	 * @return NotesTranDao object
	 */
	public abstract NotesTranDao getNotesTranDao(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter) throws TCGMException;
	/**
	 * @param userToken Contains the user id and password
	 * @param searchObject NotesTran with search criteria
	 * @param pagingFilter PagingFilter object with paging criteria
	 * @param sortObject Sort object with sort criteria
	 * @throws TCGMException
	 * @return NotesTranDao object
	 */
	public abstract NotesTranDao getNotesTranDao(UserToken userToken,NotesTran searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;

	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return CurrencyCodeDao object
	 */
	public abstract CurrencyCodeDao getCurrencyCodeDao(UserToken userToken) throws TCGMException;

	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject CurrencyCode object with search criteria
	 * @throws TCGMException
	 * @return CurrencyCodeDao object
	 */
	public abstract CurrencyCodeDao getCurrencyCodeDao(UserToken userToken,CurrencyCode searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject CurrencyCode
	 * @param sortObject Sort
	 * @return CurrencyCodeDao
	 * @throws TCGMException
	 */
	public abstract CurrencyCodeDao getCurrencyCodeDao(UserToken userToken,CurrencyCode searchObject,Sort sortObject) throws TCGMException;
	/**
	 * @param userToken
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public abstract CurrencyCodeDao getCurrencyCodeDao(UserToken userToken,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return AffCstCurDao object
	 */
	public abstract AffCstCurDao getAffCstCurDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject AffCstCur object with search criteria
	 * @throws TCGMException
	 * @return AffCstCurDao object
	 */
	public abstract AffCstCurDao getAffCstCurDao(UserToken userToken,AffCstCur searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject AffCstCur
	 * @param sortObject Sort
	 * @return AffCstCurDao
	 * @throws TCGMException
	 */
	public abstract AffCstCurDao getAffCstCurDao(UserToken userToken,AffCstCur searchObject,Sort sortObject) throws TCGMException;
	/**
	 * @param userToken
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public abstract AffCstCurDao getAffCstCurDao(UserToken userToken,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return KnollConvDao object
	 */
	public abstract KnollConvDao getKnollConvDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject KnollConv object with search criteria
	 * @throws TCGMException
	 * @return KnollConvDao object
	 */
	public abstract KnollConvDao getKnollConvDao(UserToken userToken,KnollConv searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject KnollConv
	 * @param sortObject Sort
	 * @return KnollConvDao
	 * @throws TCGMException
	 */
	public abstract KnollConvDao getKnollConvDao(UserToken userToken,KnollConv searchObject,Sort sortObject) throws TCGMException;
	/**
	 * @param userToken
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public abstract KnollConvDao getKnollConvDao(UserToken userToken,Sort sortObject) throws TCGMException;
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return PRMfgDao object
	 */
	public abstract PRMfgDao getPRMfgDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject PRMfg object with search criteria
	 * @throws TCGMException
	 * @return PRMfgDao object
	 */
	public abstract PRMfgDao getPRMfgDao(UserToken userToken,PRMfg searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject PRMfg
	 * @param sortObject Sort
	 * @return PRMfgDao
	 * @throws TCGMException
	 */
	public abstract PRMfgDao getPRMfgDao(UserToken userToken,PRMfg searchObject,Sort sortObject) throws TCGMException;
	/**
	 * @param userToken
	 * @param sortObject
	 * @return
	 * @throws TCGMException
	 */
	public abstract PRMfgDao getPRMfgDao(UserToken userToken,Sort sortObject) throws TCGMException;

	public abstract DataFeedDao getDataFeedDao(UserToken userToken) throws TCGMException;
	public abstract DataFeedLogDao getDataFeedLogDao(UserToken userToken) throws TCGMException;
	
	/*****************************************************************************************/
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @throws TCGMException
	 * @return SalesTypeDao object
	 */
	public abstract SalesTypeDao getSalesTypeDao(UserToken userToken) throws TCGMException;
	/**
	 *
	 * @param userToken Contains the user id and password
	 * @param searchObject AffCstCur object with search criteria
	 * @throws TCGMException
	 * @return SalesTypeDao object
	 */
	public abstract SalesTypeDao getSalesTypeDao(UserToken userToken,SalesType searchObject) throws TCGMException;
	/**
	 *
	 * @param userToken UserToken
	 * @param searchObject AffCstCur
	 * @param sortObject Sort
	 * @return SalesTypeDao
	 * @throws TCGMException
	 */
	public abstract SalesTypeDao getSalesTypeDao(UserToken userToken,SalesType searchObject,Sort sortObject) throws TCGMException;
	/**
	 * @param userToken
	 * @param sortObject
	 * @return SalesTypeDao
	 * @throws TCGMException
	 */
	public abstract SalesTypeDao getSalesTypeDao(UserToken userToken,Sort sortObject) throws TCGMException;
	
	public abstract AffAreaDivDao getAffAreaDivDao() throws TCGMException;
	
	public abstract AsrUsageDao getAsrUsageDao(UserToken userToken) throws TCGMException;
			
	public abstract AsrUsageDao getAsrUsageDao(UserToken userToken,ASRUsage searchObject) throws TCGMException;
	
	public abstract AsrUsageDao getAsrUsageDao(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter) throws TCGMException;
	
	public abstract AsrUsageDao getAsrUsageDao(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter,Sort sortObject) throws TCGMException;
	
	//Added By Debajyoti for ASR
	
	public abstract AsrUsageDao getAsrUsageDaoForSuffAff(UserToken userToken,ASRUsage searchObject,PagingFilter pagingFilter,Sort sortObject,String query,boolean flag) throws TCGMException;
		
}