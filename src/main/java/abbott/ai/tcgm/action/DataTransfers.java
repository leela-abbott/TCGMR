package abbott.ai.tcgm.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

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
public class DataTransfers extends TCGMAction
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
			if ( this.isSessionValid( request ) && this.isModelSelected( request ))
			{
				DataTransfersForm myForm = null;
				myForm = (DataTransfersForm) form;
				UserToken ut = this.getUserToken(request);
				String modelName = null;
				String jobName = null;
				this.errors.clear();

				if (myForm!=null && !TCGMUtil.isEmpty(myForm.getCmd()) )  {
					// check for restriction still to be entered.
					if ( myForm.isChkRestrict() && !myForm.isRestrictionEntered() ) {
						// also place form object under common restriction attribute for retrieval and population by restriction jsp
						// these changed should be present under the specific form attribute.
						request.getSession().setAttribute("restrictionForm", myForm);
						this.setForward(TCGMConstants.G_FORWARD_RESTRICTIONS);
					}
					else {
						// process specific command
						if(myForm.getCmd().equalsIgnoreCase("upload")){
							String fileName = myForm.getTheFile().getFileName();
							if(!fileName.equalsIgnoreCase("tpss_rslt_out_ccs.txt"))
							{
								fileName="tpss_rslt_out_ccs.txt";
							}
							String filePath = AppConst.getFileUploadDirectory();
					        File fileToCreate = new File(filePath, fileName);
					        
						        if (!fileName.equals("") && (!fileToCreate.exists())) {
						            FileOutputStream fileOutStream = new FileOutputStream(
						                    fileToCreate);
						            fileOutStream.write(myForm.getTheFile().getFileData());
						            fileOutStream.flush();
						            fileOutStream.close();
						        }
							//RSystem.RGM.sendFactCCS(fileToCreate);
							//fileToCreate.delete();
							fileToCreate=null;
							myForm.setCmd("");
							this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.fileupload.copied") ); // not really an error, just feedback
							this.saveErrors(request, this.errors);
						}
						if (!(myForm.getJobName() == null))
						{
							jobName = myForm.getJobName();
							if ((jobName.equals(JobConstants.PN_PUB_FCT_RPT)) || (jobName.equals(JobConstants.PN_UNPUB_FCT_RPT)) ||
							     (jobName.equals(JobConstants.PN_PUB_NET_COST))  || (jobName.equals(JobConstants.PN_UNPUB_NET_COST)) )
							{
								String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
								FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.FACTOR);
								ModelMngr mm = new ModelMngr();
								modelName = mm.getModelName(ut, Integer.parseInt(modelId));
								publishRecords(jobName, Integer.parseInt(modelId), modelName, ut, request);
	
							}
	
							if ((jobName.equals(JobConstants.PN_GEN_FCT_RPT)) || (jobName.equals(JobConstants.PN_UNGEN_FCT_RPT)) ||
								 (jobName.equals(JobConstants.PN_GEN_NET_COST))  || (jobName.equals(JobConstants.PN_UNGEN_NET_COST)) )
							{
								String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
								FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.FACTOR);
								ModelMngr mm = new ModelMngr();
								modelName = mm.getModelName(ut, Integer.parseInt(modelId));
								generateRecords(jobName, Integer.parseInt(modelId), modelName, ut, request);
	
							}
							if (!((jobName.equals(JobConstants.PN_UNGEN_FCT_RPT)) || (jobName.equals(JobConstants.PN_UNGEN_NET_COST))
							||(jobName.equals(JobConstants.PN_UNPUB_FCT_RPT)) || (jobName.equals(JobConstants.PN_UNPUB_NET_COST))))
							{
								processCmd(myForm, request);
							}
						}
						
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

	private void processCmd(DataTransfersForm myForm, HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		String cmd = myForm.getCmd(); // should correspond to a direct command
		String modelId = ((TCGMState) this.getState(request)).getCurrentModelIdString();
		FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.FACTOR);

		if ( cmd.equalsIgnoreCase("ADD_JOB") ) {

			ProcessMngr pm = new ProcessMngr();
			JobInstance job = pm.createJob(modelId, "-1", myForm);
			ModelMngr mm = new ModelMngr();
			TCGMState state = this.getState(request);

			job.setDesc( job.getJobDef().getJobName()  + TCGMConstants.STR_SEP + fm.getName() );

			if (job.getJobDef().equals(JobDefinition.SEND_PRICING) )
			{
			//	job.addJobParm( JobConstants.PN_MODEL_TYPE, myForm.getSelNetCostOption() );
			}
			else if (job.getJobDef().equals(JobDefinition.GEN_NET_COST))
			{
				job.addJobParm(JobConstants.PN_NET_RT_PER, String.valueOf(TCGMConstants.MAX_PERIODS));

			}
			else if (job.getJobDef().equals(JobDefinition.GEN_NET_COST_RPT))
			{
				job.addJobParm(JobConstants.PN_NET_RT_PER, String.valueOf(TCGMConstants.MAX_PERIODS));

			}
			else if (job.getJobDef().equals(JobDefinition.GEN_FCT_RPT) || job.getJobDef().equals(JobDefinition.PUB_FCT_RPT) )
			{
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
				job.addJobParm(JobConstants.PN_STD_CST_RATE_EXCHANGE,dd.getDatasetById(Integer.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_COST))).getDatasetName());
				job.addJobParm(JobConstants.PN_FACTOR_RATE_EXCHANGE,dd.getDatasetById(Integer.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_FACTOR_ACTUAL))).getDatasetName());
				job.addJobParm(JobConstants.PN_REVISION_RATE_EXCHANGE,dd.getDatasetById(Integer.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_REVISION))).getDatasetName());

			}
			else if (job.getJobDef().equals(JobDefinition.GEN_FCT_REPORT) )
			{
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);
				job.addJobParm(JobConstants.PN_STD_CST_RATE_EXCHANGE,dd.getDatasetById(Integer.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_COST))).getDatasetName());
				job.addJobParm(JobConstants.PN_FACTOR_RATE_EXCHANGE,dd.getDatasetById(Integer.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_FACTOR_ACTUAL))).getDatasetName());
				job.addJobParm(JobConstants.PN_REVISION_RATE_EXCHANGE,dd.getDatasetById(Integer.parseInt(md.getModelParm(Integer.parseInt(modelId), FactorModel.PN_RATESET_REVISION))).getDatasetName());

			}
			else if (job.getJobDef().equals(JobDefinition.PUB_PROD_NET_RPT) )
			{
				// no parms
			}
			else if (job.getJobDef().equals(JobDefinition.SEND_TREE_TO_MVS) )
			{
				job.addJobParm(JobConstants.PN_TREE_FILENAME, myForm.getTreeFilename());
				/********************************************************************************
				  Udaya B Aravapalli -- 12/20/2005
				  The following code will get the Tree File Name entered by the user and
				  insert into the  parameter table with parm_name = 'SENDTREEMVS'.
				 *********************************************************************************/
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				myForm.setTreeFilename(myForm.getTreeFilename().toUpperCase());
				md.setModelParmII(-1, TCGMConstants.DT_SEND_TREE_TO_MVS, myForm.getTreeFilename()+ TCGMConstants.DT_DELIMITER);
				// Adding the period as one of the parameter to this job
				job.addJobParm("INP_PERIOD", myForm.getSendTreeToMVSPeriod());
				md.setModelParmII(Integer.parseInt(modelId), "INP_PERIOD", myForm.getSendTreeToMVSPeriod() );
			}
			// Print Factor Changes
			else if (job.getJobDef().equals(JobDefinition.GEN_FCT_CHGS_RPT) || job.getJobDef().equals(JobDefinition.PUB_FCT_CHGS))
			{
				/* 10-11-05 From my discussions w/ Henry on this job, it appears
				 * that I am adding more parms than need be. He informed me that
				 * there are no parms for this job so I don't know what this stuff
				 * is below. I will leave the rest of this stuff here for now.
				 */
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				// 9-19-03 I don't know if I need to save this here to the model; Chk with an oracle developer; this
				//         Saved Factor Date may have already been set as a parm and all I need to do is add it to the job.
				fm.setSavedFactorDate( md.getModelParm(Integer.parseInt(modelId), fm.PN_SAVED_FACTOR_DATE) );
				job.addJobParm(fm.PN_SAVED_FACTOR_DATE, fm.getSavedFactorDate());
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
				String modelVersion = md.getModelParm(Integer.parseInt(modelId), JobConstants.PN_MOD_VERSION);
				job.addJobParm( JobConstants.PN_MOD_VERSION, modelVersion);

			}
			// Print TCGM Changes
			else if (job.getJobDef().equals(JobDefinition.GEN_TCGM_CHGS_RPT) || job.getJobDef().equals(JobDefinition.PUB_TCGM_CHGS))
			{
				/* 10-11-05 From my discussions w/ Henry on this job, it appears
				 * that I am adding way more stuff (parms) than need be. He informed me that all
				 * I need is the saved units and current units parms. That is what is on the UI
				 * screen. But I will leave the rest of this stuff here for now. And
				 * what that sql screen is below, I have no idea.
				 */
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				DatasetDao dd = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getDatasetDao(ut);

				if (!(md.getModelParm(Integer.parseInt(modelId), fm.PN_SAVED_FACTOR_DATE).trim().equals("")
				    || md.getModelParm(Integer.parseInt(modelId), fm.PN_SAVED_FACTOR_DATE)==null))
				{
					fm.setSavedFactorDate( md.getModelParm(Integer.parseInt(modelId), fm.PN_SAVED_FACTOR_DATE) );
				}

				String modelVersion = md.getModelParm(Integer.parseInt(modelId), JobConstants.PN_MOD_VERSION);
				if (!(modelVersion == null || modelVersion.trim().equals("")))
				{
					job.addJobParm( JobConstants.PN_MOD_VERSION, modelVersion);
				}
				else
				{
					job.addJobParm( JobConstants.PN_MOD_VERSION, "");
				}

				if (!(myForm.getSavedUnitsSelected()==null || myForm.getSavedUnitsSelected().trim().equals("")))
				{
					job.addJobParm( JobConstants.PN_SAVED_UNITS, myForm.getSavedUnitsSelected());
					job.addJobParm( JobConstants.PN_SAVED_UNITS_NAME, dd.getDatasetById(Integer.parseInt(myForm.getSavedUnitsSelected())).getDatasetName());
					mm.setModelParmII(ut, fm, JobConstants.PN_SAVED_UNITS, myForm.getSavedUnitsSelected());
					mm.setModelParmII(ut, fm, JobConstants.PN_SAVED_UNITS + "::" + ut.getUserid(), myForm.getSavedUnitsSelected());
					String sql = JobConstants.PN_SQL_SAVED_UNITS_DATE + " AND dataset_table_id = " + myForm.getSavedUnitsSelected();
					try
					{
						job.addJobParm(JobConstants.PN_SAVED_UNITS_DATE, md.getStringValueFromSql(sql));
					}
					catch(TCGMException ex)
					{
						// 9-26-03 Commented out for now which means the job would not be passed this parm;
						//         Remove comments to abort and give user this message
						//String errMsg = "Error retrieving Saved Units parm.";
						//throw new TCGMException("DataTransfers", "processCmd()", errMsg);
					}
					mm.setModelParmII(ut, fm, JobConstants.PN_CURR_UNITS, myForm.getCurrUnitsSelected());
					mm.setModelParmII(ut, fm, JobConstants.PN_CURR_UNITS + "::" + ut.getUserid(), myForm.getCurrUnitsSelected());

				}
				else
				{
					job.addJobParm( JobConstants.PN_SAVED_UNITS, "");
					job.addJobParm( JobConstants.PN_SAVED_UNITS_NAME, "");
					job.addJobParm(JobConstants.PN_SAVED_UNITS_DATE, "");
				}

				if (!(myForm.getCurrUnitsSelected()==null || myForm.getCurrUnitsSelected().trim().equals("")))
				{
					String currUnitsName = dd.getDatasetById(Integer.parseInt(myForm.getCurrUnitsSelected())).getDatasetName();
					fm.setCurrUnitsName(currUnitsName);
					job.addJobParm( fm.PN_CURR_UNITS_NAME, fm.getCurrUnitsName());
					job.addJobParm( JobConstants.PN_CURR_UNITS, myForm.getCurrUnitsSelected());
				}
				else
				{
					job.addJobParm( fm.PN_CURR_UNITS_NAME, "");
					job.addJobParm( JobConstants.PN_CURR_UNITS, "");
				}

				if (!(fm.getSavedFactorDate()==null || fm.getSavedFactorDate().trim().equals("")))
				{
					job.addJobParm(fm.PN_SAVED_FACTOR_DATE, fm.getSavedFactorDate());
				}
				else
				{
					job.addJobParm(fm.PN_SAVED_FACTOR_DATE, "");
				}
				if (!(fm.getDesc()==null || fm.getDesc().trim().equals("")))
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
				}
				else
				{
					job.addJobParm(JobConstants.PN_DIS_VERSION, "" );
				}
			}
			else if (job.getJobDef().equals(JobDefinition.GEN_FACTR_SUMMRY) || job.getJobDef().equals(JobDefinition.PUB_FCT_SMRY))
			{
				job.addJobParm(JobConstants.PN_DIS_VERSION, fm.getDesc() );
			}
			else if(job.getJobDef().equals(JobDefinition.WRITE_T_PRODUCT_TRNS) )
			{
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_T_PRODUCT_FILENAME,md.getModelParm(-1, JobConstants.PN_T_PRODUCT_FILENAME));
				//job.addJobParm(JobConstants.PN_AFF_FILENAME,md.getModelParm(-1, JobConstants.PN_AFF_FILENAME));
			}
			else if(job.getJobDef().equals(JobDefinition.WRITE_AFF_TRNS) )
			{
				ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.FACTOR);
				job.addJobParm(JobConstants.PN_AFF_FILENAME,md.getModelParm(-1, JobConstants.PN_AFF_FILENAME));
			}

			pm.addJob(job, ut);
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.job.add", job.getJobDef().getJobName()) ); // not really an error, just feedback
			this.saveErrors(request, this.errors);
		}
	}


	public void setupForm(HttpServletRequest request) throws TCGMException {
		UserToken ut = this.getUserToken(request);
		DataTransfersForm myForm = new DataTransfersForm();
		myForm.reset();
		int modelIdInt = Integer.parseInt(((TCGMState) this.getState(request)).getCurrentModelIdString());
		DatasetMngr dm = new DatasetMngr();
		ModelDao md = DaoFactory.getDaoFactory(DaoFactory.ORACLE).getModelDao(ut, TCGMModel.Type.ANALYSIS);
		Vector utSets=dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA);
		Vector utSavedSets=dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA_SAVE);
		myForm.setUnitSets(utSets);
		myForm.setSavedSets(utSavedSets);
		//myForm.setSavedSets(this.setSavedSets(utSets));

		myForm.setUnitSets(dm.getDatasetByTableName(ut, DBConst.TABLE_UNIT_DATA));
		myForm.setSavedUnitsSelected( md.getModelParm(modelIdInt, JobConstants.PN_SAVED_UNITS + "::" + ut.getUserid()));
		myForm.setCurrUnitsSelected( md.getModelParm(modelIdInt, JobConstants.PN_CURR_UNITS   + "::" + ut.getUserid()));


		request.getSession(false).setAttribute("dataTransfersForm", myForm );
	}

	public Vector setSavedSets(Vector vect) throws TCGMException {
		Dataset dataset = new Dataset();
	String dataSetName = null;

	Vector savedUnitSets = new Vector();

	for (int i = 0; i < vect.size(); i++) {
		dataset = (Dataset) vect.elementAt(i);
		dataSetName = dataset.getDatasetName();
		if(dataSetName!=null){

			if (dataSetName.trim().endsWith("_SAVE")
						|| dataSetName.trim().endsWith("_save")) {
							savedUnitSets.addElement(dataset);

					}
		}

	}

   return 	savedUnitSets;

  }

  public void publishRecords(String jobName, int modelId, String modelName, UserToken ut, HttpServletRequest request )throws TCGMException
  {
	ProcessMngr pm = new ProcessMngr();
	int inqReportId =0;
	String flag = "N";
	if ((jobName.equals(JobConstants.PN_PUB_FCT_RPT)) || (jobName.equals(JobConstants.PN_UNPUB_FCT_RPT)))
	{
		inqReportId = 1;
		if (jobName.equals(JobConstants.PN_PUB_FCT_RPT))
		{
			flag = "Y";
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.publish.model"));
		}

		else
		{
			flag = "N";
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.unpublish.model"));
		}
		this.saveErrors(request, this.errors);
	}
	else if ((jobName.equals(JobConstants.PN_PUB_NET_COST))  || (jobName.equals(JobConstants.PN_UNPUB_NET_COST)))
	{
		inqReportId = 2;
		if (jobName.equals(JobConstants.PN_PUB_NET_COST))
		{
			flag = "Y";
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.publish.model"));
		}
		else
		{
			flag = "N";
			this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.unpublish.model"));
		}
		this.saveErrors(request, this.errors);
	}

	try
	{
		pm.PublishRecord(inqReportId,modelId, flag, modelName, ut);
	}
	catch (TCGMException e)
	{
		this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("unsuccessful.publish.model"));
		this.saveErrors(request, this.errors);
		throw new TCGMException(e);
	}

  }

  public void generateRecords(String jobName, int modelId, String modelName, UserToken ut, HttpServletRequest request )throws TCGMException
	{
	  ProcessMngr pm = new ProcessMngr();
	  int inqReportId =0;
	  String flag = "N";
	  if ((jobName.equals(JobConstants.PN_GEN_FCT_RPT)) || (jobName.equals(JobConstants.PN_UNGEN_FCT_RPT)))
	  {
		  inqReportId = 1;
		  if (jobName.equals(JobConstants.PN_GEN_FCT_RPT))
		  {
			  flag = "Y";
			  this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.generate.model"));
		  }

		  else
		  {
			  flag = "N";
			  this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ungenerate.model"));
		  }
		  this.saveErrors(request, this.errors);
	  }
	  else if ((jobName.equals(JobConstants.PN_GEN_NET_COST))  || (jobName.equals(JobConstants.PN_UNGEN_NET_COST)))
	  {
		  inqReportId = 2;
		  if (jobName.equals(JobConstants.PN_GEN_NET_COST))
		  {
			  flag = "Y";
			  this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.generate.model"));
		  }
		  else
		  {
			  flag = "N";
			  this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("success.ungenerate.model"));
		  }
		  this.saveErrors(request, this.errors);
	  }

	  try
	  {
		  pm.GenerateRecord(inqReportId,modelId, flag, modelName, ut);
	  }
	  catch (TCGMException e)
	  {
		  this.errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("unsuccessful.generate.model"));
		  this.saveErrors(request, this.errors);
		  throw new TCGMException(e);
	  }

	}


	public DataTransfers()
	{
		super();
	}

}