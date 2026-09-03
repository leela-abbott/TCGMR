package abbott.ai.tcgm.helpers;

import java.io.*;
import abbott.ai.tcgm.process.*;
import org.apache.log4j.Logger;

/**
 *
 * <p>Title: TCGM</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Abbott International</p>
 * @author Jim Watkins
 * @version 1.0
 */
public class FileWatcher
{
	private static Logger logger = Logger.getLogger("TCGM.Helpers.FileWatcher");
	private Thread myThread;
	private volatile boolean stopRequested = false;
	private long start;
	private long timeout;
	private File file;
	private volatile boolean success=false;

	/**
	 *
	 * @param f
	 * @param timeout
	 * @param jobToPerform
	 */
	public FileWatcher(File f, long timeout, JobDefinition jobToPerform)
	{
		file = f;
		this.timeout = timeout;
		start = System.currentTimeMillis();

		Runnable r = new Runnable()
		{
			public void run()
			{
				doWork();
			}
		};
		myThread = new Thread(r);
		myThread.start();
	}

	/**
	 *
	 */
	private void doWork()
	{
		logger.debug("FileWatcher Started. Watching for " + file.getAbsolutePath() );
		try
		{
			while (!stopRequested & !timedout() )
			{
				this.myThread.sleep(3000);
				if ( file.exists() ) ; // perform task;
			}
		}
		catch (InterruptedException iex)
		{
			// this isn't necessarily bad, reassert and just keep going.
			myThread.interrupt();
		}
		catch (Exception ex)
		{
			this.requestStop();
			logger.error("Exception initiating unit import:" + ex.toString() );
		}
		if (!success)
		{
			logger.debug("Failed to perform task");
			file.renameTo(new File(file.getAbsolutePath() + ".failed" ) );
		}
	}

	/**
	 *
	 * @return
	 */
	private boolean timedout()
	{
		if (System.currentTimeMillis() - start < timeout)
		{
			return false;
		}
		else
		{
			logger.debug("FileWatcher timing out...");
			return true;
		}
	}

	/**
	 *
	 */
	public void requestStop()
	{
		this.stopRequested = true;
		this.myThread.interrupt();
	}
}