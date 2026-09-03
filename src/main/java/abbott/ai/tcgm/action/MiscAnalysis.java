package abbott.ai.tcgm.action;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import abbott.ai.tcgm.*;
import abbott.ai.tcgm.action.form.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.process.*;

import org.apache.struts.action.*;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott Laboratories</p>
 * @author David Fields
 * @version 1.0
 */
public class MiscAnalysis extends TCGMAction
{
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
    {
        try
        {
            if ( this.isSessionValid( request ) && this.isModelSelected( request ) )
            {
                MiscAnalysisForm myForm = null;
                myForm = (MiscAnalysisForm) form;
                UserToken ut = this.getUserToken(request);
                this.errors.clear();

                if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) && !myForm.getCmd().equals("CANCEL") )  {
                    // check for restriction still to be entered.
                    if ( myForm.isChkRestrict() && !myForm.isRestrictionEntered() ) {
                        // also place form object under common restriction attribute for retrieval and population by restriction jsp
                        // these changed should be present under the specific form attribute.
                        request.getSession().setAttribute("restrictionForm", myForm);
                        this.setForward(TCGMConstants.G_FORWARD_RESTRICTIONS);
                    }
                    else {
                        // process specific command
                        processCmd(myForm, request);
                        this.setupForm(request);
                        this.setForward(TCGMConstants.FORWARD_SUCCESS);
                    }
                }
                else {
                    this.setupForm(request);
                    this.setForward(TCGMConstants.FORWARD_INPUT);
                }
            }
        }
        catch (TCGMException ex)
        {
            request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);
            this.setForward(TCGMConstants.G_FORWARD_EXCEPTION);
        }
        this.logger.debug(this.className + " - Forward to " + this.getForward() );
        return mapping.findForward( this.getForward() );
    }

    private void processCmd(MiscAnalysisForm myForm, HttpServletRequest request) throws TCGMException {
        UserToken ut = this.getUserToken(request);
        String cmd = myForm.getCmd(); // should correspond to a direct command
        String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
        boolean flag=true;
        if ( cmd.equalsIgnoreCase("ADD_JOB") ) {

			ModelMngr mm = new ModelMngr();
			TCGMState state = this.getState(request);
			AnalysisModel am = (AnalysisModel) mm.getModelFromId(ut, state.getCurrentModelId(), TCGMModel.Type.ANALYSIS );
			ProcessMngr pm = new ProcessMngr();
			if(myForm.getJobName().equals("SND_NET_ANL"))
			{
				String modelDescription=am.getDesc();
				String retVal="";
				if(modelDescription.length()>50)
				{
					modelDescription=modelDescription.substring(0,49);
				}
				retVal=pm.sendNetAnl(ut,am.getModelId(),modelDescription);
				if(retVal.equals("")){
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.send.ccs", "") );
				}else{
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.send.ccs", retVal) );
				}
			}
			else{	
            
            JobInstance job = pm.createJob(modelId, "-1", myForm);
            job.setDesc(job.getJobDef().getJobName() + TCGMConstants.STR_SEP + am.getName() );
			DatasetMngr dm = new DatasetMngr();
			String unitSetIdStr = myForm.getSelExUnitSet();

/* Deferred Margin Settings Section */
            // KGA Reports
            if (job.getJobDef().equals(JobDefinition.KGA_REPORTS)) {
            	// 10-18-05 Remove KGA Report Option per Henry's ticket
                //job.addJobParm("", myForm.getKgaReportOption() ); // 9-29-05 ?? Need Parm name; Parm is not used in oracle procedure - OK
				job.addJobParm(JobConstants.PN_XCHG_UNITS, myForm.getSelExUnitSet() );
				mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS, myForm.getSelExUnitSet());
				job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName() );
				mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
				//UNIT_HDR1
				if (am.getUnitHdr1()!=null)
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, am.getUnitHdr1());
				}
				else
				{
					job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, "");
				}
				// DIS_VERSION
				if (am.getDesc() != null)
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, "" );
				}

            }
			// Deferred Margin Summary – COS/Src
            else if (job.getJobDef().equals(JobDefinition.DFRD_RPT02)) {

            }
            /*
//			Deferred Margin Summary – COS
			 else if (job.getJobDef().equals(JobDefinition.DFRD_RPT_SUM_COS)) {

			 }
//			Deferred Margin Summary – SRC
			 else if (job.getJobDef().equals(JobDefinition.DFRD_RPT_SUM_SRC)) {

			 }*/
			// Deferred Margin Summary
			else if (job.getJobDef().equals(JobDefinition.DFRD_MARGIN)) {
// 10-17-05 Parms are not accurate because of discrepancy w/ Exchange Exposure unitset parm
				//Addning new parameter Report Name Suffix
				//|| job.getJobDef().equals(JobDefinition.SEND_P34)
				job.addJobParm("SEND_P34_AUTORUN", myForm.getAutoRun());
				mm.setModelParmII(ut, am, "SEND_P34_AUTORUN", myForm.getAutoRun());
				
				myForm.setSalesType(TCGMUtil.getSortedHashMap(dm.getSalesType(ut)));
				myForm.setSalesList(TCGMUtil.getSortedHashMap(dm.getSalesList(ut,myForm.getSelExUnitSet())));
				String slsType="";
				if(null==myForm.getSelExSalesType() || myForm.getSelExSalesType().equals("-1"))
				{
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invalid.tcgm.units") ); 
					flag=false;
				}else{
					
						job.addJobParm("DFRD_SLS_TYPE", myForm.getSelExSalesType());
						mm.setModelParmII(ut, am, "DFRD_SLS_TYPE", myForm.getSelExSalesType());
				}				
				String slsList="";
				if(null==myForm.getSelSalesList() || myForm.getSelSalesList().length==0)
				{
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invalid.tcgm.saleslist") ); 
					flag=false;
				}else{
					
						for(int i=0;i<myForm.getSelSalesList().length;i++){
							slsList=slsList+myForm.getSelSalesList()[i]+"|";
						}
						if(slsList.length()>0)
						{
							slsList=slsList.substring(0,slsList.length()-1);
						}
					
						job.addJobParm("DFRD_SLS_ID_LIST", slsList);
						mm.setModelParmII(ut, am, "DFRD_SLS_ID_LIST", slsList);
						myForm.setSelSalesList(null);
				}
				
				if(myForm.getReportNameSuf()!=null)
				{
					job.addJobParm(JobConstants.PN_RPT_NAME_SUP, myForm.getReportNameSuf() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_RPT_NAME_SUP, "" );
				}

				mm.setModelParmII(ut, am, JobConstants.PN_RPT_NAME_SUP, myForm.getReportNameSuf());
				//INP_PERIOD
				if(myForm.getSelExPeriod()!=null)
				{
					job.addJobParm(JobConstants.PN_INP_PERIOD, myForm.getSelExPeriod() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_INP_PERIOD, "" );
				}

				mm.setModelParmII(ut, am, JobConstants.PN_INP_PERIOD, myForm.getSelExPeriod());
				// Dataset Id
				if (myForm.getSelExUnitSet() !=null)
				{
					job.addJobParm(JobConstants.PN_XCHG_UNITS, myForm.getSelExUnitSet() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_XCHG_UNITS, "" );
				}
				String defErn=dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName().trim();
				defErn=defErn.substring(3,4);
				if(defErn.equals("A") || defErn.equals("P") || defErn.equals("U")){
				
					job.addJobParm("DFRD_TITLE", "Earned" );
				}else{				
					job.addJobParm("DFRD_TITLE", "Deferred" );
				}
				
				mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS, myForm.getSelExUnitSet());
				// Dataset Name
				job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName() );
				mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
				// Dataset Id
				/*job.addJobParm(JobConstants.PN_UNIT_XCHG,  myForm.getSelExUnitSet() );
				mm.setModelParmII(ut, am, JobConstants.PN_UNIT_XCHG, myForm.getSelExUnitSet());*/
				// DIS_VERSION
				if (am.getDesc() != null)
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, "" );
				}
			}
			// Deferred Margin Detail - COS
			else if (job.getJobDef().equals(JobDefinition.DFRD_RPT_COS)) {
				//Addning new parameter Report Name Suffix
				  if(myForm.getReportNameSuf()!=null)
				  {
					  job.addJobParm(JobConstants.PN_RPT_NAME_SUP, myForm.getReportNameSuf() );
				  }
				  else
				  {
					  job.addJobParm(JobConstants.PN_RPT_NAME_SUP, "" );
				  }
	
				  mm.setModelParmII(ut, am, JobConstants.PN_RPT_NAME_SUP, myForm.getReportNameSuf());
				
				job.addJobParm(JobConstants.PN_INP_PERIOD, myForm.getSelExPeriod() );
				mm.setModelParmII(ut, am, JobConstants.PN_INP_PERIOD, myForm.getSelExPeriod());
				// Dataset Name
				job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName() );
				mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
				// DIS_VERSION
				if (am.getDesc() != null)
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, "" );
				}

			}
			// Deferred Margin Detail - Src
			else if (job.getJobDef().equals(JobDefinition.DFRD_RPT_SRC)
					|| job.getJobDef().equals(JobDefinition.DFRD_RPT_SUM_COS) 
					|| job.getJobDef().equals(JobDefinition.DFRD_RPT_SUM_SRC)) {
				//Addning new parameter Report Name Suffix
				if(myForm.getReportNameSuf()!=null)
				{
					job.addJobParm(JobConstants.PN_RPT_NAME_SUP, myForm.getReportNameSuf() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_RPT_NAME_SUP, "" );
				}

				mm.setModelParmII(ut, am, JobConstants.PN_RPT_NAME_SUP, myForm.getReportNameSuf());						
				// XCHG_UNITS
				if ( myForm.getSelExUnitSet()!=null)
				{
					job.addJobParm(JobConstants.PN_XCHG_UNITS, myForm.getSelExUnitSet() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_XCHG_UNITS, "" );
				}
				// Dataset Name
				job.addJobParm(JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName() );
				mm.setModelParmII(ut, am, JobConstants.PN_XCHG_UNITS_NAME, dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
				// INP_PERIOD
				if (myForm.getSelExPeriod() != null)
				{
					job.addJobParm(JobConstants.PN_INP_PERIOD, myForm.getSelExPeriod() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_INP_PERIOD, "" );
				}
				// DIS_VERSION
				if (am.getDesc() != null)
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, "" );
				}
			}
/* Exchange Exposure Section */
			// 9-19-05 Added Hookup for Exchange Exposure Summary
			// 10-17-05 For the 2 jobs in this block, all parms are the same
			// 11-17-06 XHG_ANALYSIS job combined with other two
			else if (job.getJobDef().equals(JobDefinition.XHG_EXPOSURE) ||
			         job.getJobDef().equals(JobDefinition.XHG_EXP_DTL) ||
					 job.getJobDef().equals(JobDefinition.XHG_ANALYSIS)) {
				unitSetIdStr = myForm.getSelHedExUnitSet();
				// Dataset Id
				job.addJobParm("HDGE_UNITS", myForm.getSelHedExUnitSet() );
				mm.setModelParmII(ut, am, "HDGE_UNITS", myForm.getSelHedExUnitSet());
				// Dataset Name
				job.addJobParm("HDGE_UNITS_NAME", dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName() );
				mm.setModelParmII(ut, am, "HDGE_UNITS_NAME", dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetName());
				// Dataset Id
				job.addJobParm("HDGE_UNITS",  myForm.getSelHedExUnitSet() );
				mm.setModelParmII(ut, am, "HDGE_UNITS", myForm.getSelHedExUnitSet());

				job.addJobParm("HDGE_PERIOD", myForm.getSelHedExPeriod() );
				mm.setModelParmII(ut, am, "HDGE_PERIOD", myForm.getSelHedExPeriod());
				//commented on 07/15/2011 
				job.addJobParm("E_HDGE_PERIOD", myForm.getSelHedExEndPeriod() );
				mm.setModelParmII(ut, am, "E_HDGE_PERIOD", myForm.getSelHedExEndPeriod());
				//06-08-15 Note: Adding for Hedge Category/Sales ID dropdown list start
				if(null==myForm.getSelHedExSalesType() || myForm.getSelHedExSalesType().equals("-1"))
				{
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invalid.tcgm.units") ); 
					flag=false;
				}else{
					job.addJobParm("HDGE_SLS_TYPE", myForm.getSelHedExSalesType());
					mm.setModelParmII(ut, am, "HDGE_SLS_TYPE", myForm.getSelHedExSalesType());
				}



				String slsList="";
				if(null==myForm.getSelHedSalesList() || myForm.getSelHedSalesList().length==0)
				{
					this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("invalid.tcgm.saleslist") ); 
					flag=false;
				}else{

					for(int i=0;i<myForm.getSelHedSalesList().length;i++){
						slsList=slsList+myForm.getSelHedSalesList()[i]+"|";
					}
					if(slsList.length()>0)
					{
						slsList=slsList.substring(0,slsList.length()-1);
					}
									
					job.addJobParm("HDGE_SLS_ID_LIST", slsList);
					mm.setModelParmII(ut, am, "HDGE_SLS_ID_LIST", slsList);
					myForm.setSelHedSalesList(null);
				}

				//06-08-15 Note: Adding for Hedge Category/Sales ID dropdown list end
				// 10-17-05 Note: selDmSalesId is not used int he DM section although its on
				// the screen in that section. The parm list says that its only used here
				// in Exchange Exposure so I may have to get the Dm form value
				//job.addJobParm(JobConstants.PN_SALES_ID, myForm.getSelExSalesId() );
				//mm.setModelParmII(ut, am, JobConstants.PN_SALES_ID, myForm.getSelExSalesId());
				
				//UNIT_HDR_1
				if (am.getUnitHdr1() != null)
				{
					job.addJobParm(JobConstants.PN_UNIT_HDR_1, am.getUnitHdr1());
				}
				else
				{
					job.addJobParm(JobConstants.PN_UNIT_HDR_1, "");
				}
				//DIS_VERSION
				if (unitSetIdStr != null)
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION,  dm.getDatasetById(ut, Integer.parseInt(unitSetIdStr)).getDatasetDesc());
				}
				else
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, "");
				}
				
				if(myForm.getHedgereportNameSuf()!=null)
				{
					job.addJobParm("HDGE_RPT_NAME_SUF", myForm.getHedgereportNameSuf() );
				}
				else
				{
					job.addJobParm("HDGE_RPT_NAME_SUF", "" );
				}

			}

/* Standard Cost Settings */
			// 9-29-05 Added Hookup for Std Cost Analysis Base/Unit
			// 9-29-05 Added Hookup for Std Val Added Cost of Sales
			// 9-29-05 Added Hookup for Std Val Added Cost of Sales
			// 10-13-05 For the 3 jobs in this block, all parms are the same
			else if (job.getJobDef().equals(JobDefinition.STD_CST_RPT) ||
					 job.getJobDef().equals(JobDefinition.STD_VA_CST)  ||
		  			 job.getJobDef().equals(JobDefinition.GEN_NET_COST)||
					 job.getJobDef().equals(JobDefinition.STD_VA_COS))
			{

				if(myForm.getSelScRatePeriod() != null)
				{
					job.addJobParm(JobConstants.PN_NET_RT_PER, myForm.getSelScRatePeriod() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_NET_RT_PER, "" );
				}
				mm.setModelParmII(ut, am, JobConstants.PN_NET_RT_PER, myForm.getSelScRatePeriod());
 				job.addJobParm(JobConstants.PN_NET_UNITS_ID, myForm.getSelScUnitSet() );
				mm.setModelParmII(ut, am, JobConstants.PN_NET_UNITS_ID, myForm.getSelScUnitSet());
				String netUnitsId = myForm.getSelScUnitSet();
				//NET_UNITS
				if(netUnitsId != null)
				{
					job.addJobParm(JobConstants.PN_NET_UNITS, dm.getDatasetById(ut, Integer.parseInt(netUnitsId)).getDatasetName() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_NET_UNITS, "" );
				}
				mm.setModelParmII(ut, am, JobConstants.PN_NET_UNITS, dm.getDatasetById(ut, Integer.parseInt(netUnitsId)).getDatasetName());

				//NET_BASE
				/* Changed to Current Model the model selected in the 'Select or Create Model Page  instead of
				 * getSelected model as before and removed the drop down list for base plan for
				 * Standard cost setting*/
				 /*
				  * NET_BASE parameter value changed from model ID to model name.
				  */
				if (myForm.getSelScFactorModel() != null)
				{
					job.addJobParm(JobConstants.PN_NET_BASE, this.getState(request).getCurrentModelIdString() );
					job.addJobParm(JobConstants.PN_NET_BASE_NAME, this.getState(request).getCurrentModelName()); //getCurrentModelIdString() );

				}
				else
				{
					job.addJobParm(JobConstants.PN_NET_BASE, "" );
				}
				mm.setModelParmII(ut, am, JobConstants.PN_NET_BASE, myForm.getSelScFactorModel());
				job.addJobParm(JobConstants.PN_RATE_2, myForm.getSelScRateSet() );
				mm.setModelParmII(ut, am, JobConstants.PN_RATE_2, myForm.getSelScRateSet());
				job.addJobParm(JobConstants.PN_STDCST_SLSID, myForm.getSelScSalesId() );
				mm.setModelParmII(ut, am, JobConstants.PN_STDCST_SLSID, myForm.getSelScSalesId());
				if (myForm.getSelScSalesId().equals("1"))
				{
					job.addJobParm(JobConstants.PN_SLSID_TITLE, "Sample" );
				}
				else
				{
					job.addJobParm(JobConstants.PN_SLSID_TITLE, "Sales" );
				}
			}

            //XHG_ANALYSIS Reports GAin 03-15
			/*else if (job.getJobDef().equals(JobDefinition.XHG_ANALYSIS)) {

				//UNIT_HDR1
				if (am.getUnitHdr1()!=null)
				{
					 job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, am.getUnitHdr1());
				}
				else
				{
					 job.addJobParm(AnalysisModel.PN_UNIT_HDR_1, "");
				}
				// DIS_VERSION
				if (am.getDesc() != null)
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, am.getDesc() );
				}
				else
				{
				 	job.addJobParm(JobConstants.PN_DIS_VERSION, "" );
				}

				if ( myForm.getSelExUnitSet()!=null)
				{
					job.addJobParm(JobConstants.PN_XCHG_UNITS, myForm.getSelExUnitSet() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_XCHG_UNITS, "" );
				}

			}*/

			// Add Job
			if(flag)
			{
				pm.addJob(job, ut);
				this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName()) ); // not really an error, just feedback
			}
			
			}//end of if
            this.saveErrors(request, this.errors);
        }
    }

    public void setupForm(HttpServletRequest request) throws TCGMException {
        UserToken ut = this.getUserToken(request);
        DatasetMngr dm = new DatasetMngr();
        MiscAnalysisForm maf = new MiscAnalysisForm();
        maf.reset();
        
        String jobStatus="";

		int modelIdInt = Integer.parseInt(((TCGMState) this.getState(request)).getCurrentModelIdString());

        maf.setUnitSets( dm.getDatasetByTableName(ut,DBConst.TABLE_UNIT_DATA) );
        maf.setRateSets( dm.getDatasetByTableName(ut,DBConst.TABLE_RATE_DATA) );

		//Sridevi.K 7/1/05: Code added for only displaying the open models.
		FactorModel fm = new FactorModel();
		ModelMngr mm = new ModelMngr();
		fm.setStatus(TCGMModel.Status.OPEN);
		maf.setFactorModels( mm.getModels(ut, fm ) );
		//Sridevi.K 7/1/05: End of code for displaying only open models.


        // 10-18-05 Retrieve all parms from PARAMETER table for selected model
        //          and populated screen parms with these values.

        // Special Note 1-5-06: Some of the parameters on this page (Exchange Units,
        //   Sales ID, Period) may be the same for all jobs on the page. I think this
        //   is the way they had it on System-W. I have already changed the Exchange
        //   Units parm for Deferred Margin & Exchange Exposure jobs to use the same parm.
        //   May have to do the same for the Standard Cost job parms later.

		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.ANALYSIS);

		//maf.setSelDmSalesId( md.getModelParm(modelIdInt, JobConstants.PN_STDCST_SLSID));
		maf.setSelScSalesId( md.getModelParm(modelIdInt, JobConstants.PN_STDCST_SLSID));
		//maf.setSelExSalesId( md.getModelParm(modelIdInt, JobConstants.PN_EXEXP_SLSID));
		
		//29 is for DFRD MARGIN
		jobStatus=md.getModelJobStatus(modelIdInt,"DFRD_MARGIN");
		
		//maf.setSelDmUnitSet( md.getModelParm(modelIdInt, JobConstants.PN_NET_UNITS_ID));
		maf.setSelScUnitSet( md.getModelParm(modelIdInt, JobConstants.PN_NET_UNITS_ID));
		maf.setSelExUnitSet( md.getModelParm(modelIdInt, JobConstants.PN_XCHG_UNITS));
		
		maf.setSalesType(TCGMUtil.getSortedHashMap(dm.getSalesType(ut)));
		maf.setSalesList(TCGMUtil.getSortedHashMap(dm.getSalesList(ut,maf.getSelExUnitSet())));
		//maf.setSelDmPeriod( md.getModelParm(modelIdInt, JobConstants.PN_INP_PERIOD));
		maf.setSelScRatePeriod( md.getModelParm(modelIdInt, JobConstants.PN_NET_RT_PER));
		maf.setSelExPeriod( md.getModelParm(modelIdInt, JobConstants.PN_PERIOD));

 		maf.setSelScRateSet( md.getModelParm(modelIdInt, JobConstants.PN_RATE_2));
		maf.setSelScFactorModel(md.getModelParm(modelIdInt, JobConstants.PN_NET_BASE));
	
        //maf.setFactorModels( new ModelMngr().getModels(ut, new FactorModel() ) );
		request.getSession(false).setAttribute("jobStatus", jobStatus );
		
        request.getSession(false).setAttribute("miscAnalysisForm", maf );
    }

    public MiscAnalysis()
    {
        super();
    }

}
