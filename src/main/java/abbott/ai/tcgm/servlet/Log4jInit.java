package abbott.ai.tcgm.servlet;

import org.apache.log4j.*;
import javax.servlet.http.*;
import javax.servlet.*;
//import abbott.ai.tcgm.*;
import java.io.*;


/**
 * <p>Title: TCGM</p>
 * <p>Description: Initializes Log4J</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Dave FIelds
 * @version 1.0
 */
public class Log4jInit extends HttpServlet
{
	private static Logger myLogger = Logger.getLogger( "Log4jInit" );
	/**
	 * Default Constructor
	 */
	public Log4jInit()
	{
	}

	/**
	 * Initialize the servlet
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException
	{
		ServletContext context = config.getServletContext();
		String path =  context.getRealPath("\\");
		String file = config.getInitParameter("log4j-init-file");

		// add / to path if not there for weblogic...
		if (!path.endsWith("\\") ) path = path + "\\";

		// if the log4j-init-file is not set, then no point in trying
		if(file != null)
		{
			PropertyConfigurator.configure(path + file);
			//PropertyConfigurator.configure(iStream.toString());
		}
		else
		{
			myLogger.error("\n\npath:" + path);
			myLogger.error("file: " + file);
			myLogger.error(path + file+"\n\n");
			throw new ServletException("Unable to locate \"LOG4J-INIT-FILE\" ");
		}
	}

	/**
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	/**
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

	}
}