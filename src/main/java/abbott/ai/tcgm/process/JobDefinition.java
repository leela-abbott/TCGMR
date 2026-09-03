package abbott.ai.tcgm.process;

//import java.lang.reflect.*;
import abbott.ai.tcgm.exception.*;
//import java.util.Properties;
import java.sql.*;

import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.process.javajob.*;
import abbott.ai.tcgm.data.*;
//import abbott.ai.tcgm.data.oracle.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */

public final class JobDefinition extends TCGMEntity
{
  private final String name;
  private JobType type;

  public static final JobDefinition AFFBPC_LOAD = new JobDefinition("AFFBPC_LOAD00");
  //public static final JobDefinition AFFBPC_UPDT = new JobDefinition("AFFBPC_UPDT");

  // jobs related to analysis models
  /*******************************************/
  public static final JobDefinition ANL_CALC = new JobDefinition("ANL_CALC");
  public static final JobDefinition ANL_DETAIL = new JobDefinition("ANL_DETAIL");
  public static final JobDefinition ANL_REJECTS = new JobDefinition("ANL_REJECTS");
  public static final JobDefinition ANL_PUSUMMRY = new JobDefinition("ANL_PUSUMMRY");
  public static final JobDefinition ANL_PUSM_400 = new JobDefinition("ANL_PUSM_400");
  public static final JobDefinition ANL_XSSM_400 = new JobDefinition("ANL_XSSM_400");
  public static final JobDefinition ANL_XSEC_SUM = new JobDefinition("ANL_XSEC_SUM");
  public static final JobDefinition ANL_SAMSEC_SUM = new JobDefinition("ANL_SAMSEC_SUM");
  public static final JobDefinition ANL_WSUMMRY = new JobDefinition("ANL_WSUMMRY");
  public static final JobDefinition ANL_SAMPW = new JobDefinition("ANL_SAMPW");
  public static final JobDefinition ANL_EXSUMMRY = new JobDefinition("ANL_EXSUMMRY");
  public static final JobDefinition ANL_PSSES_AREA = new JobDefinition("ANL_PSSES_AREA");
  public static final JobDefinition ANL_SAMPEX = new JobDefinition("ANL_SAMPEX");
  public static final JobDefinition ANL_ARSUMMRY = new JobDefinition("ANL_ARSUMMRY");
  public static final JobDefinition ANL_SAMPAR = new JobDefinition("ANL_SAMPAR");
  public static final JobDefinition ANL_PUSRCSUM = new JobDefinition("ANL_PUSRCSUM");

  public static final JobDefinition FETCH_HDR_TEST = new JobDefinition("FETCH_HDR_TEST");
  public static final JobDefinition FETCH_HDR_PROD = new JobDefinition("FETCH_HDR_PROD");

  //Below Job is replaced by AL_PSUM_PUB  03-28-06-  Gain
  public static final JobDefinition ANL_PSUM_400 = new JobDefinition("ANL_PSUM_400");
  public static final JobDefinition ANL_XTSM_400 = new JobDefinition("ANL_XTSM_400");
  public static final JobDefinition SEND_ANL_FLEX_ESS = new JobDefinition("SEND_ANL_FLEX_ESS");
  // Publish Per Unit Summary 03-27-06-  Gain
  public static final JobDefinition ANL_PSUM_PUB = new JobDefinition("ANL_PSUM_PUB");

  /*******************************************/


  // jobs related to cost exchange models
  /*******************************************/
  public static final JobDefinition CXCHG_FLEX = new JobDefinition("CXCHG_FLEX");
  public static final JobDefinition CXCHG_FLEX5 = new JobDefinition("CXCHG_FLEX5");
  public static final JobDefinition CXCHG_CSTSL1 = new JobDefinition("CXCHG_CSTSL1");
  public static final JobDefinition CXCHG_CSTSL2 = new JobDefinition("CXCHG_CSTSL2");
  public static final JobDefinition CXCHG_CSMP1C = new JobDefinition("CXCHG_CSMP1C");
  public static final JobDefinition CXCHG_CSMP2 = new JobDefinition("CXCHG_CSMP2");
  public static final JobDefinition CXCHG_CSMP3 = new JobDefinition("CXCHG_CSMP3");
  public static final JobDefinition CXCHG_CSMP4 = new JobDefinition("CXCHG_CSMP4");
  public static final JobDefinition CXC_ANL04E = new JobDefinition("CXC_ANL04E");
  public static final JobDefinition CXC_ANL12E = new JobDefinition("CXC_ANL12E");
  public static final JobDefinition CXC_ANL14S = new JobDefinition("CXC_ANL14S");
  public static final JobDefinition CXC_ANL14C = new JobDefinition("CXC_ANL14C");
  /*******************************************/

  // jobs related to factor calculation
  /*******************************************/
  public static final JobDefinition APPLY_MAINTENANCE = new JobDefinition("APPLY_MAINTENANCE");
  public static final JobDefinition AUDIT_TRAIL = new JobDefinition("AUDIT_TRAIL");
  public static final JobDefinition BUILD_ASR_TREE = new JobDefinition("BUILD_ASR_TREE");
  public static final JobDefinition CALCULATE_FACTORS = new JobDefinition("CALCULATE_FACTORS");
  public static final JobDefinition SET_FACTOR_MODEL_PARMS = new JobDefinition("SET_FACTOR_MODEL_PARMS");
  /*******************************************/

  // jobs related to misc reports
  /*******************************************/
  public static final JobDefinition PRINT_INVCUR = new JobDefinition("PRINT_INVCUR");
  public static final JobDefinition PRINT_BP_FRZ = new JobDefinition("PRINT_BP_FRZ");
  public static final JobDefinition PRINT_MISM = new JobDefinition("PRINT_MISM");
  public static final JobDefinition MISS_FACTOR = new JobDefinition("MISS_FACTOR");
  public static final JobDefinition PRINT_VAL_AD = new JobDefinition("PRINT_VAL_AD");
  public static final JobDefinition GEN_FACTR_SUMMRY = new JobDefinition("GEN_FACTR_SUMMRY");
  public static final JobDefinition BLNDD_SUMMRY = new JobDefinition("BLNDD_SUMMRY");
  public static final JobDefinition BPC_PRT0CST = new JobDefinition("BPC_PRT0CST");
  public static final JobDefinition BPC_PRT0BP = new JobDefinition("BPC_PRT0BP");
  public static final JobDefinition PRINT_BPC_FRZ = new JobDefinition("PRINT_BPC_FRZ");
  public static final JobDefinition ASR_SRC_LIST = new JobDefinition("ASR_SRC_LIST");
  // 8-6-03 bd; Per Oracle development team, WHERE_USED can be removed.
  //public static final JobDefinition WHERE_USED = new JobDefinition("WHERE_USED");
  public static final JobDefinition BPC_0PLNVSIM = new JobDefinition("BPC_0PLNVSIM");
  public static final JobDefinition BPC_ANALYS = new JobDefinition("BPC_ANALYS");
  public static final JobDefinition DELETE_AUDIT = new JobDefinition("DELETE_AUDIT");
  public static final JobDefinition BPC_DELETE = new JobDefinition("BPC_DELETE");
  public static final JobDefinition ASR_DELETE = new JobDefinition("ASR_DELETE");
  public static final JobDefinition AUDIT_FACTORS = new JobDefinition("AUDIT_FACTORS");
  public static final JobDefinition ASR_DLT_PART = new JobDefinition("ASR_DLT_PART");
  public static final JobDefinition PRINT_DUB_SUP = new JobDefinition("PRINT_DUB_SUP");
  public static final JobDefinition ROUTE_EXCEPT = new JobDefinition("ROUTE_EXCEPT");
  public static final JobDefinition ROUTE_EXC_O = new JobDefinition("ROUTE_EXC_O");
  /*******************************************/

  // jobs related to misc analysis
  /*******************************************/
  public static final JobDefinition KGA_REPORTS = new JobDefinition("KGA_REPORTS");
  public static final JobDefinition DFRD_RPT02 = new JobDefinition("DFRD_RPT02");
  public static final JobDefinition DFRD_MARGIN = new JobDefinition("DFRD_MARGIN");
  public static final JobDefinition SEND_P34 = new JobDefinition("SEND_P34");
  public static final JobDefinition DFRD_RPT_SRC = new JobDefinition("DFRD_RPT_SRC");
  public static final JobDefinition DFRD_RPT_COS = new JobDefinition("DFRD_RPT_COS");
  public static final JobDefinition STD_CST_RPT = new JobDefinition("STD_CST_RPT");
  public static final JobDefinition STD_VA_CST = new JobDefinition("STD_VA_CST");
  public static final JobDefinition STD_VA_COS = new JobDefinition("STD_VA_COS");
  // 8-6-03 bd; Per Oracle development team, job name should be XHG_EXPOSURE
  //public static final JobDefinition RUN_XCHG_EXP = new JobDefinition("RUN_XCHG_EXP");
  public static final JobDefinition XHG_EXPOSURE = new JobDefinition("XHG_EXPOSURE");
  // 8-6-03 bd; Per Oracle development team, job name should be XHG_EXP_DTL
  //public static final JobDefinition RUN_XCHG_DTL = new JobDefinition("RUN_XCHG_DTL");
  public static final JobDefinition XHG_EXP_DTL = new JobDefinition("XHG_EXP_DTL");
  public static final JobDefinition XHG_ANALYSIS = new JobDefinition("XHG_ANALYSIS");
  public static final JobDefinition DFRD_RPT_SUM_COS = new JobDefinition("DFRD_RPT_SUM_COS");
  public static final JobDefinition DFRD_RPT_SUM_SRC = new JobDefinition("DFRD_RPT_SUM_SRC");

  /*******************************************/


  // model action jobs
  /*******************************************/
  public static final JobDefinition MODEL_COMPACT = new JobDefinition("MODEL_COMPACT");
  public static final JobDefinition MODEL_CLOSE = new JobDefinition("MODEL_CLOSE");
  public static final JobDefinition MODEL_DELETE = new JobDefinition("MODEL_DELETE");
  /*******************************************/


  public static final JobDefinition EXTRACT_TREE = new JobDefinition("EXTRACT_TREE");

  public static final JobDefinition LOAD_ESSBASE_RGM = new JobDefinition("LOAD_ESSBASE_RGM");
  public static final JobDefinition N_ADT_TRAIL = new JobDefinition("N_ADT_TRAIL");
  public static final JobDefinition N_CALC_WTD = new JobDefinition("N_CALC_WTD");
  public static final JobDefinition N_SPLT_PERID = new JobDefinition("N_SPLT_PERID");

  // jobs related the perpetual models
  public static final JobDefinition PERP_TOTCALC = new JobDefinition("PERP_TOTCALC");
  public static final JobDefinition PERP_ENDCALC = new JobDefinition("PERP_ENDCALC");
  public static final JobDefinition PPT_INVCALC1 = new JobDefinition("PPT_INVCALC1");
  public static final JobDefinition PERP_SUMMARY = new JobDefinition("PERP_SUMMARY");
  public static final JobDefinition PERP_DETAIL = new JobDefinition("PERP_DETAIL");
  public static final JobDefinition PERP_INVXCPT = new JobDefinition("PERP_INVXCPT");
  public static final JobDefinition PPT_EQVXCPT1 = new JobDefinition("PPT_EQVXCPT1");

  // System Compact and Model Purge jobs
  /*******************************************/
  public static final JobDefinition SYSTEM_COMPACT = new JobDefinition("SYSTEM_COMPACT");
  public static final JobDefinition MODEL_PURGE = new JobDefinition("MODEL_PURGE");
  /*******************************************/

  // jobs related to data transfers
  //10-25-05 Job Name made Obsolete with ReportNet Integration
  //public static final JobDefinition SEND_FACTOR_CHANGES = new JobDefinition("EXC_FCT_TRF");
  // 03-14-2005. New Job Definition for AFFBPC Report.
  public static final JobDefinition AFFBPC_REPORT = new JobDefinition("AFFBPC_REPORT");

  //10-25-05 Job Name made Obsolete with ReportNet Integration
  //public static final JobDefinition SEND_TCGM_CHANGES = new JobDefinition("EXC_TCGM_TRF");

  public static final JobDefinition SEND_FACTOR_SUMMARY = new JobDefinition("FCTR_SUM_400");
  public static final JobDefinition SEND_TREE_TO_MVS = new JobDefinition("SEND_TREE_TO_MVS");
  public static final JobDefinition SEND_PRICING = new JobDefinition("SEND_PRICING");

  // 10-25-05 Job Name made Obsolete with ReportNet Integration
  //public static final JobDefinition SEND_FACRPT = new JobDefinition("SEND_FACRPT");
  // Print Factor Report
  public static final JobDefinition GEN_FCT_RPT = new JobDefinition("GEN_FCT_RPT");
  public static final JobDefinition GEN_FCT_REPORT = new JobDefinition("GEN_FCT_REPORT");
  
  //Print Factor Changes
  public static final JobDefinition GEN_FCT_CHGS_RPT = new JobDefinition("GEN_FCT_CHGS_RPT");
  // Print TCGM Changes
  public static final JobDefinition GEN_TCGM_CHGS_RPT = new JobDefinition("GEN_TCGM_CHGS_RPT");

  public static final JobDefinition PUB_FCT_RPT = new JobDefinition("PUB_FCT_RPT");
  public static final JobDefinition PUB_TCGM_CHGS = new JobDefinition("PUB_TCGM_CHGS");
  public static final JobDefinition PUB_FCT_SMRY = new JobDefinition("PUB_FCT_SMRY");
  public static final JobDefinition PUB_FCT_CHGS = new JobDefinition("PUB_FCT_CHGS");
  public static final JobDefinition PUB_PROD_NET_RPT = new JobDefinition("PUB_PROD_NET_RPT");
  public static final JobDefinition GEN_NET_COST = new JobDefinition("GEN_NET_COST");
  public static final JobDefinition GEN_NET_COST_RPT = new JobDefinition("GEN_NET_COST_RPT");
  public static final JobDefinition PUB_NET_COST = new JobDefinition("PUB_NET_COST");
  public static final JobDefinition WRITE_T_PRODUCT_TRNS = new JobDefinition("WRITE_T_PRODUCT_TRNS");
  public static final JobDefinition WRITE_AFF_TRNS = new JobDefinition("WRITE_AFF_TRNS");
  /*******************************************/


  public static final JobDefinition ASR_TREE = new JobDefinition("ASR_TREE");
  public static final JobDefinition PRINT_BPCOST = new JobDefinition("PRINT_BPCOST");
  public static final JobDefinition PRINT_EXCEPT = new JobDefinition("PRINT_EXCEPT");
  public static final JobDefinition BP_VS_TPSS_RPT1_PRE = new JobDefinition("BP_VS_TPSS_RPT1_PRE");
  public static final JobDefinition PRINT_EXRATE = new JobDefinition("PRINT_EXRATE");
  public static final JobDefinition PRINT_REPORT = new JobDefinition("PRINT_REPORT");

  public static final JobDefinition DUP_RPT01 = new JobDefinition("DUP_RPT01");

  public static final JobDefinition SEND_FACTORS = new JobDefinition("SEND_FACTORS");
  public static final JobDefinition SEND_ALL_FACTORS = new JobDefinition("SEND_ALL_FACTORS");
  public static final JobDefinition SAVE_FACTORS_13 = new JobDefinition("SAVE_FACTORS_13");
  public static final JobDefinition SEND_CCSH = new JobDefinition("SEND_CCSH");
  public static final JobDefinition SPLIT_RPT = new JobDefinition("SPLIT_RPT");
  public static final JobDefinition NETCOST_RPT_PUB_PRE = new JobDefinition("NETCOST_RPT_PUB_PRE");
  public static final JobDefinition SET_COMP_MODEL = new JobDefinition("SET_COMP_MODEL");
  
  


  public static final JobDefinition EXPORT_ASR_TREE = new JobDefinition("EXPORT_ASR_TREE" );


  private JobDefinition(String name)
  {
	this.name = name;
	this.type = null;
  }

  public void populateDetail() throws TCGMException {
	  JobDefinition jd = new ProcessMngr().getJobDefinition(name);
	  this.setJobType( jd.getJobType() );
	  this.setCmdName( jd.getCmdName() );
	  this.setDesc( jd.getDesc() );
  }


  public void populateDetail(Connection c) throws TCGMException {
	  // ok I'm bypassing the managers in this context to go directly to a dao.
	  // seems prefereable to passing a connection object through the manager layer.
	  ProcessDao pd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getProcessDao(SQLUtil.getOracleAdmin(), c);
	  JobDefinition jd = pd.getJobDefinition( this.getJobName() );

	  this.setJobType( jd.getJobType() );
	  this.setCmdName( jd.getCmdName() );
	  this.setDesc( jd.getDesc() );
  }


  public static JobDefinition getJobDefFromName(String name) throws TCGMException
  {
	final String q = "abbott.ai.tcgm.process.JobDefinition";
	final String methodName = "getJobDefFromName(String)";
	JobDefinition j = null;

	// This method retrieves the member variable that matches the Job Name in the database
	// and returns it as a Job Object.
	try
	{
	  j = (JobDefinition) Class.forName(q).getDeclaredField(name).get(j);
	}
	catch (Exception e)
	{
	  throw new TCGMException( "JobDefinition", methodName, name, e.toString() );
	}
	return j;
  }


  public static class JobType {
	private final String name;

	public static final JobType StoredProcedure = new JobType ("StoredProcedure");
	public static final JobType Java = new JobType ("Java");

	private JobType(String name) { this.name = name; }
	public String toString() { return this.name; }
  }



  //private static final UserToken userToken;
  private String desc;
  private String cmdName;

  public JobType getJobType() {
	return this.type;
  }

  public void setJobTypeString(String typeCode) {
	if (typeCode.trim().equalsIgnoreCase("JV"))
	  this.type = JobType.Java;
	else  if (typeCode.trim().equalsIgnoreCase("SP"))
	  this.type = JobType.StoredProcedure;
	else throw new IllegalArgumentException("Invalid Job Type supplied. Must be JV or SP");
  }

  public void setJobType(JobType jobType) {
	  this.type = jobType;
  }

  public String getJobName() {
	return this.name;
  }

  //public UserToken getUserToken() {
  //  return userToken;
  // }

  public String toShortString() {
	StringBuffer sb = new StringBuffer();
	sb.append( this.getJobName() );

	return sb.toString();
  }

  public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("Job Name: " + this.getJobName() + "\n");
	sb.append("Job Type: " + this.getJobType() + "\n");
	return sb.toString();
  }

  public void setDesc(String desc) {
	this.desc = desc;
  }

  public String getDesc() {
	return desc;
  }
  public void setCmdName(String cmdName) {
	this.cmdName = cmdName;
  }
  public String getCmdName() {
	return cmdName;
  }
}