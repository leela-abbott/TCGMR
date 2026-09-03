/*
 * Created on Sep 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package abbott.ai.tcgm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.TCGMUtil;
import abbott.ai.tcgm.entities.AnalysisModel;
import abbott.ai.tcgm.entities.CostExchModel;
import abbott.ai.tcgm.entities.FactorModel;
import abbott.ai.tcgm.entities.PerpetualModel;
import abbott.ai.tcgm.entities.TCGMModel;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.DatasetMngr;
import abbott.ai.tcgm.helpers.ModelMngr;
import abbott.ai.tcgm.helpers.RptUserMngr;


/**
 * @author goshirk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TCGMAjax extends TCGMAction {

	/**
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return the page or action to forward control to
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward perform(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
	{
		String methodName = "perform";
		

		String cascadingCmd= null;
		String cascadingVal=null;
		String cascadingSecVal=null;
		if(! this.isSessionValid(request)){
			return mapping.findForward(TCGMConstants.FORWARD_FAILURE);
		}
		try
		{
			cascadingCmd = request.getParameter("cascadingCmd");
			cascadingVal = request.getParameter("cascadingVal");
			
			if(null==cascadingVal){
				cascadingVal="";
			}
			if(null==cascadingCmd){
				cascadingCmd="";
			}

			if(cascadingCmd.equals("Country")){
				setCountry(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("Area")){
				setArea(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("Sector")){
				setSector(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("BurstAff")){
				setBurstAffiliate(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("BurstArea")){
				setBurstArea(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("BurstSector")){
				setBurstSector(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("SalesType")){
				setSalesType(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("SalesId")){
				setSalesId(cascadingVal,response,request);
			}
			if(cascadingCmd.equals("ActInact")){
				
				cascadingSecVal = request.getParameter("cascadingSecVal");
				getAffiliateStaus(cascadingVal,cascadingSecVal,response,request);
			}
			if(cascadingCmd.equals("ModelInfo")){
				cascadingSecVal = request.getParameter("cascadingSecVal");
				getModelInfoString(cascadingVal,cascadingSecVal,response,request);
			}
					}
		catch (Exception ex)
		{
		    request.setAttribute(TCGMConstants.SESSION_NAME_EXCEPTION, ex);

		}


		return mapping.findForward(null);
	}
	public void setCountry(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		RptUserMngr userMaintMgr = new RptUserMngr();
		if(value!=null && !value.equals("") && !value.equals("ALL")){
			map=userMaintMgr.getTAffiliates(value);
			HashMap hmpSortCountry=TCGMUtil.getSortedMap(map,TCGMConstants.AFFILIATE);
			Set st=(Set)hmpSortCountry.keySet();
            Iterator it=st.iterator();
            String x="";
            sb.append("-1,Select One*");
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+hmpSortCountry.get(x)+"*");            	
            }
		}else{
			throw new TCGMException();
		}


		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void setSector(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		RptUserMngr userMaintMgr = new RptUserMngr();
		if(value!=null && !value.equals("") && !value.equals("ALL")){
			map=userMaintMgr.getTSectors(value);
			HashMap hmpSortCountry=TCGMUtil.getSortedMap(map,TCGMConstants.SECTOR);
			Set st=(Set)hmpSortCountry.keySet();
            Iterator it=st.iterator();
            String x="";
            sb.append("-1,Select One*");
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+hmpSortCountry.get(x)+"*");            	
            }
		}else{
			throw new TCGMException();
		}


		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void setArea(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		RptUserMngr userMaintMgr = new RptUserMngr();
		if(value!=null && !value.equals("") && !value.equals("ALL")){
			map=userMaintMgr.getTAreas(value);
			HashMap hmpSortCountry=TCGMUtil.getSortedMap(map,TCGMConstants.AREA);
			Set st=(Set)hmpSortCountry.keySet();
            Iterator it=st.iterator();
            String x="";
            sb.append("-1,Select One*");
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+hmpSortCountry.get(x)+"*");            	
            }
		}else{
			throw new TCGMException();
		}
		
		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void setBurstAffiliate(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		RptUserMngr userMaintMgr = new RptUserMngr();
		if(value!=null && !value.equals("") && !value.equals("ALL")){
			map=userMaintMgr.getBurstAffiliates(value);
			HashMap hmpSortCountry=TCGMUtil.getSortedMap(map,TCGMConstants.AFFILIATE);
			Set st=(Set)hmpSortCountry.keySet();
            Iterator it=st.iterator();
            String x="";            
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+hmpSortCountry.get(x)+"*");            	
            }
		}else{
			throw new TCGMException();
		}


		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void setBurstSector(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		RptUserMngr userMaintMgr = new RptUserMngr();
		if(value!=null && !value.equals("") && !value.equals("ALL")){
			map=userMaintMgr.getBurstSectors(value);
			HashMap hmpSortCountry=TCGMUtil.getSortedMap(map,TCGMConstants.SECTOR);
			Set st=(Set)hmpSortCountry.keySet();
            Iterator it=st.iterator();
            String x="";            
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+hmpSortCountry.get(x)+"*");            	
            }
		}else{
			throw new TCGMException();
		}


		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void setBurstArea(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		RptUserMngr userMaintMgr = new RptUserMngr();
		if(value!=null && !value.equals("") && !value.equals("ALL")){
			map=userMaintMgr.getBurstAreas(value);
			HashMap hmpSortCountry=TCGMUtil.getSortedMap(map,TCGMConstants.AREA);
			Set st=(Set)hmpSortCountry.keySet();
            Iterator it=st.iterator();
            String x="";            
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+hmpSortCountry.get(x)+"*");            	
            }
		}else{
			throw new TCGMException();
		}
		
		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void setSalesType(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		UserToken ut = this.getUserToken(request);
		DatasetMngr dm = new DatasetMngr();
		if(value!=null && !value.equals("")){
			map=dm.getSalesType(ut);			
			Set st=(Set)map.keySet();
            Iterator it=st.iterator();
            String x="";
            sb.append("ALL,ALL*");
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+map.get(x)+"*");            	
            }
		}else{
			throw new TCGMException();
		}


		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void setSalesId(String value,HttpServletResponse response,HttpServletRequest request)throws Exception{
		StringBuffer sb=new StringBuffer();		
		HashMap map=null;
		UserToken ut = this.getUserToken(request);
		DatasetMngr dm = new DatasetMngr();
		if(value!=null && !value.equals("")){
			map=TCGMUtil.getSortedHashMap(dm.getSalesList(ut,value));			
			Set st=(Set)map.keySet();
            Iterator it=st.iterator();
            String x="";            
            while(it.hasNext()){
            	x=(String)it.next();
            	sb.append(x+","+map.get(x)+"*");            	
            }            
		}else{
			throw new TCGMException();
		}


		response.getWriter().write(sb.toString().substring(0,(sb.toString().length()-1)));
	}
	
	public void getAffiliateStaus(String value,String aff,HttpServletResponse response,HttpServletRequest request)throws Exception{
		String sb="";		
		RptUserMngr userMaintMgr = new RptUserMngr();			   
		//sb=userMaintMgr.getAffiliateStatus(value,aff);		
		response.getWriter().write(sb.toString());
	}
	
	public void getModelInfoString(String modelId,String type,HttpServletResponse response,HttpServletRequest request)throws Exception{
		UserToken ut = this.getUserToken(request);
		StringBuffer sb = new StringBuffer("");
		String none = TCGMConstants.NONE_SEL_HTML;
		
		
		try{
			if(type.equalsIgnoreCase("ANALYSIS")){
				AnalysisModel currentModel = (AnalysisModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.ANALYSIS  );
				
				sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentModel.getDesc() ) + "<br>" );		
				sb.append( "<b>Base Factor Model:</b> " + ( currentModel.getBaseModel() == null ? none : currentModel.getBaseModel().getName() ) + ", Period: " + currentModel.getBaseModelPeriod() + "<br>" );
				sb.append( "<b> Analysis Model:</b> " + ( currentModel.getAnalysisModel() == null ? none : currentModel.getAnalysisModel().getName() ) + ", Period: " + currentModel.getAnalysisModelPeriod() + "<br>" );
				sb.append( "<b>Base Units:</b> " + ( currentModel.getAnalysisUnits() == null ? none : currentModel.getAnalysisUnits().getDatasetName() ) + ", Period: " + currentModel.getAnalysisUnitsPeriod() + "<br>" );		
				sb.append( "<b>Analysis Units:</b> " + ( currentModel.getVolumeUnits() == null ? none : currentModel.getVolumeUnits().getDatasetName() ) + ", Period: " + currentModel.getVolumeUnitsPeriod() + "<br>" );
		        String temp = fixMemo(currentModel.getMemo());
				sb.append( "<b> Memo:</b> " + (  temp == "" ? none : temp)  + "<br>" );
				
				
			}
			else if(type.equalsIgnoreCase("FACTOR")){
				FactorModel fm = (FactorModel) new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.FACTOR );
			}
			else if(type.equalsIgnoreCase("PERPETUAL")){
				PerpetualModel currentModel = (PerpetualModel)new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.PERPETUAL);
				
				sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentModel.getDesc() ) + "<br>" );
				sb.append( "<b>Starting Model:</b> " + ( currentModel.getStartingModel() == null ? none : currentModel.getStartingModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getStartingInvUnits() == null ? none : currentModel.getStartingInvUnits().getDatasetName() ) + "<br>" );
				sb.append( "<b>Current Model:</b> " + ( currentModel.getCurrentYearActualModel() == null ? none : currentModel.getCurrentYearActualModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getCurrentYearActualUnits() == null ? none : currentModel.getCurrentYearActualUnits().getDatasetName() ) + "<br>" );
				sb.append( "<b>Previous Model:</b> " + ( currentModel.getLastYearActualModel() == null ? none : currentModel.getLastYearActualModel().getName() ) );
				sb.append( "<b>, Units:</b> " + ( currentModel.getLastYearActualUnits() == null ? none : currentModel.getLastYearActualUnits().getDatasetName() ) + "<br>" );
				sb.append( "<b>Costing Model:</b> " + ( currentModel.getCostingModel() == null ? none: currentModel.getCostingModel().getName() ) + "<br>"  );
				sb.append( "<b>Ending Inventory Model:</b> " + ( currentModel.getEndingModel() == null ? none: currentModel.getEndingModel().getName() ) );
				sb.append( "<b>Units:</b> " + ( currentModel.getEndingInvUnits() == null ? none : currentModel.getEndingInvUnits().getDatasetName() ) + "<br>"  );
				sb.append( "<b>Start Period:</b> " + currentModel.getStartPeriod() + ", " + currentModel.getStartYear()  + "<br>" );
				sb.append( "<b>End Period:</b> " + currentModel.getEndPeriod() + ", " + currentModel.getEndYear() + "<br>" );
				String temp = fixMemo(currentModel.getMemo());
				sb.append( "<b> Memo:</b> " + (  temp == "" ? none : temp)  + "<br>" );
			}
			else if(type.equalsIgnoreCase("COSTEXCH")){
				CostExchModel currentModel = (CostExchModel)new ModelMngr().getModelFromId(ut, Integer.parseInt(modelId), TCGMModel.Type.COSTEXCH );
				sb.append( "<b>Desc:</b> " + TCGMUtil.escapeString( currentModel.getDesc() ) + "<br>" );
				sb.append( "<b>Rate Set:</b> " );
				if (!currentModel.getRateSet().getDatasetName().equals(""))
					sb.append( currentModel.getRateSet().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");

				sb.append( "<b>Starting RGM Ver:</b> " );
				if (!currentModel.getStartingSalesData().getDatasetName().equals(""))
					sb.append( currentModel.getStartingSalesData().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");

				sb.append( "<b>Ending RGM Ver:</b> ");
				if (!currentModel.getEndingSalesData().getDatasetName().equals(""))
					sb.append( currentModel.getEndingSalesData().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");

				sb.append( "<b>Start Period:</b> " + currentModel.getStartPeriod() + ", " + currentModel.getStartYear()  + "<br>" );
				sb.append( "<b>End Period:</b> " + currentModel.getEndPeriod() + ", " + currentModel.getEndYear() + "<br>" );

				sb.append( "<b>Cost Exch Units:</b> ");
				if (!currentModel.getCostExchUnits().getDatasetName().equals(""))
					sb.append( currentModel.getCostExchUnits().getDatasetName() + "<br>" );
				else
					sb.append(TCGMConstants.NONE_SEL_HTML + "<br>");
				
				sb.append( "<b>Memo:</b> " + currentModel.getMemo() + "<br>" );
				sb.append( "<b>Factor Model:</b> " + currentModel.getFactorModelName() + "<br>" );
			}
			response.getWriter().write(sb.toString());
			
		}catch (TCGMException ex)
		{
			sb.append( "<b>Exception:</b> "+ ex.toString());
		}
		
	}
	
	private String fixMemo(String input)
   	{
  		String out ="";
   		char tt =' ';
   		for(int i = 0; i< input.length(); i++)
   		{
   	  		tt = input.charAt(i);
   			if (Character.isISOControl(tt))
   			{	 
	 			out = out + " ";
    			
   		 	}  
   		 	else
   		 	{
				out = out + input.substring(i,i+1);
   		 	}
   		}
   		return out;   	
  	}
}


