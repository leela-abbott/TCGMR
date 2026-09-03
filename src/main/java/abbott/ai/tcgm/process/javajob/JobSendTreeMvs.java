package abbott.ai.tcgm.process.javajob;

//import java.util.Properties;

import abbott.ai.tcgm.exception.*;
//import abbott.ai.tcgm.data.*;
import abbott.ai.tcgm.helpers.*;
//import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.process.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobSendTreeMvs implements JavaJob {

    public JobSendTreeMvs()  {    }

	public void perform(JobInstance jobInstance) throws TCGMException {
		perform(jobInstance.getModel());
	}

    public void perform(String fileName) throws TCGMException {

        // fetch production headers...

        new ExportMngr().exportMVSTree(fileName);
    }
}