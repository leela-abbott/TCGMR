package abbott.ai.tcgm.process.javajob;

import java.util.StringTokenizer;

import abbott.ai.tcgm.TCGMConstants;
import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.helpers.ExportMngr;
import abbott.ai.tcgm.process.JobInstance;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JobInterCompTrsfRpt implements JavaJob {

    public JobInterCompTrsfRpt()  {    }

	public void perform(JobInstance jobInstance) throws TCGMException {
		perform(jobInstance.getModel());
	}

    public void perform(String fileName) throws TCGMException {

		StringTokenizer tokens = new StringTokenizer(fileName, TCGMConstants.DT_HYPHEN_DELIMITER);
		int tokenCount = 0;
		String period = null;
		while(tokens.hasMoreTokens())
		{
			if ((tokenCount == 0))
			{
				fileName = tokens.nextToken();
			}

			if ((tokenCount == 1))
			{
				period = tokens.nextToken();
				if (period.length() == 1)
				{
					period = "0" + period; //Add  0 infront to make it two digits always.
				}
			}
		tokenCount++;	
		}

        new ExportMngr().exportInterCompTrsfr(fileName, period);
    }
}