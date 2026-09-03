/*
 * Created on Jun 17, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package abbott.ai.tcgm.data;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import javax.sql.RowSet;

import abbott.ai.tcgm.entities.ASRUsage;
import abbott.ai.tcgm.entities.Sort;
import abbott.ai.tcgm.exception.TCGMDuplicateItemException;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author goshirk
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface AsrUsageDao extends TCGMDao{
	public RowSet getRS() throws TCGMException;

		/**
		 * @param searchObject Asr object
		 * @return RowSet
		 * @throws TCGMException
		 */
		public RowSet getRS(ASRUsage searchObject) throws TCGMException;

		/**
		 * @param searchObject Asr object
		 * @param sortObject contains sort criteria
		 * @return RowSet
		 * @throws TCGMException
		 */
		public RowSet getRS(ASRUsage searchObject,Sort sortObject) throws TCGMException;

		/**
		 * @return Vector of Asr objects
		 * @throws TCGMException
		 */
		public Vector getVO() throws TCGMException;

		/**
		 * @param searchObject Asr object
		 * @return Vector of Asr objects
		 * @throws TCGMException
		 */
		public Vector getVO(ASRUsage searchObject) throws TCGMException;


		/**
		 * @param searchObject Asr object
		 * @param sortObject contains sort criteria
		 * @return Vector of Asr objects
		 * @throws TCGMException
		 */
		public Vector getVO(ASRUsage searchObject,Sort sortObject) throws TCGMException;
		
		//The below abs method is added for ASR SUFFAFF selection by Deba
		
		public Vector getVoForSuffAffSelected(String query,boolean flag) throws TCGMException;
		
		/**
		 * @return number of records returned from query
		 * @throws TCGMException
		 */
		public long getCount() throws TCGMException;

		/**
		 *
		 * @param rs
		 * @return
		 * @throws TCGMException
		 */
		public ASRUsage getAsrFromCurrentRow(RowSet rs) throws TCGMException;
		
		public ArrayList getSupAff(Connection conn)throws TCGMException;
	    public void delete(Vector affBpcList) throws TCGMException;
		public void delete(ASRUsage searchObject,Connection conn,String aff,String cycleId) throws TCGMException;
		public boolean insert(Vector asrTransList,int modelId,String acd,Connection conn,String userId) throws TCGMException, TCGMDuplicateItemException;
		public boolean insert(int modelId,String acd,String afiliate,Connection conn,String userId,String cycleId) throws TCGMException, TCGMDuplicateItemException;
		public boolean update(Vector asrTranList) throws TCGMException, TCGMDuplicateItemException;
		
		//Newly added for ASRUsage Upload on 2024 May.
		public void upload(String fileName,String cycle) throws TCGMException,IOException;
		

}
