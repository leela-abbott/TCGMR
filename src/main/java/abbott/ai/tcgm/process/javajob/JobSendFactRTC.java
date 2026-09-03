package abbott.ai.tcgm.process.javajob;

import java.util.ResourceBundle;

import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.helpers.*;
import abbott.ai.tcgm.process.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobSendFactRTC implements JavaJob {

    public JobSendFactRTC()  {    }

	public void perform(JobInstance jobInstance) throws TCGMException {
		perform();
	}

    public void perform() throws TCGMException {
		ResourceBundle resources = ResourceBundle.getBundle("RSystem");
		
				String user = resources.getString("RGMRSystemUsername");
				String pass = resources.getString("RGMRSystemPassword");


        // fetch production headers...

        new ExportMngr().sendFactRTC(user,pass);
    }
}