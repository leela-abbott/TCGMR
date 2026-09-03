package abbott.ai.tcgm.servlet;

import javax.servlet.http.*;
import javax.servlet.*;
import abbott.ai.tcgm.*;
import abbott.ai.tcgm.data.*;
import java.io.*;
import org.apache.log4j.Logger;
import abbott.ai.tcgm.process.DaemonMngr;

/**
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave Fields
 * @version 1.0
 */

public class TCGMInit extends HttpServlet
{
	private static Logger logger = null;

	/**
	 * Initialize the servlet
	 */

	public void init(ServletConfig config) throws ServletException
	{
		// init the dataset id object
		DatasetConst dsConst = DatasetConst.getInstance();

		try
		{
			dsConst.init();
			AppConst.init(config);
			SQLUtil.init(config);
			DaemonMngr.init(config);
			
			
				DaemonMngr dm = DaemonMngr.getInstance();				
				dm.startProcessScheduler();
				dm.startProcessSchedulerMonitor();
				dm.startDataFeedMonitor();
	        
				
	        
		}
		catch(Exception e)
		{
			System.err.println("Error Initializing TCGM Application: " + e.getMessage() );
			throw new ServletException(e);
		}
	}

	/**
	 * Default Constructor
	 */
	public TCGMInit()
	{
		this.logger = Logger.getLogger(this.getClass());
	}

	/**
	 *
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	/**
	 *
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	public void destroy()
	{
		logger = null;
	}
}