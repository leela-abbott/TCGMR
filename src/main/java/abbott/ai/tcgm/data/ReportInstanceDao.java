package abbott.ai.tcgm.data;

import abbott.ai.tcgm.exception.TCGMException;
import abbott.ai.tcgm.entities.*;

import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface ReportInstanceDao extends TCGMDao {

    public Vector getReportInstancesByJobQId(String jobQId) throws TCGMException;
    public Vector getReportInstances() throws TCGMException;
    public ReportInstance getReportInstanceById(String reportId, String jobQueId) throws TCGMException;
    public void deleteReportInstanceById(String reportId, String jobQId) throws TCGMException;
	public String getJobQueParmName(String jobQId, String jobParmName) throws TCGMException;

}