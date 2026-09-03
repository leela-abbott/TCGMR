package abbott.ai.tcgm.action.form;

//import java.util.Vector;
//import abbott.ai.tcgm.entities.TCGMModel;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Jim Watkins
 * @version 1.0
 */
public abstract class TCGMMngModelsForm extends TCGMProductionForm
{
	public abstract String getModelDetailArrayString();   
   	public String fixMemo(String input)
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

