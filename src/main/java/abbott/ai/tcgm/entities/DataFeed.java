package abbott.ai.tcgm.entities;

//import java.io.*;
import java.io.FilenameFilter;
import java.io.File;

public class DataFeed {
	private FilenameFilter filenameFilter = null;
	private File dbLocation = null;
	private String processName = null;
	private File asLocation;
	private String jobType;
	private String dbLoc; // Added by Udaya B Aravapalli on 01/26/2006. This is
	                      // required to get the file path from the Database.  

  	public DataFeed(String dbloc, String asloc, String fileExtension, String processName, String jobType) 
  	{
		this.dbLocation = new File(dbloc);
		this.asLocation = new File(asloc);
		this.filenameFilter = new DataFeedFileFilter(fileExtension);
		this.processName = processName;
		this.jobType = jobType;
		this.dbLoc = dbloc;
  	}

  	public FilenameFilter getFilenameFilter()
  	{
		return filenameFilter;
  	}
  	public File getDbLocation()
  	{
		return dbLocation;
  	}
  	public String getProcessName()
  	{
		return processName;
  	}
	public java.io.File getAsLocation() 
	{
		return asLocation;
	}
	public String getJobType() 
	{
		return jobType;
	}

	/**
	 * @return
	 */
	public String getDbLoc() {
		return dbLoc;
	}

	/**
	 * @param string
	 */
	public void setDbLoc(String string) {
		dbLoc = string;
	}

}

class DataFeedFileFilter implements FilenameFilter 
{
  	private String extType = null;

  	public DataFeedFileFilter(String extType) 
  	{
		this.extType = extType;
  	}

  	public boolean accept(File f, String filename) 
  	{
		return (filename.toUpperCase().endsWith("." + extType.toUpperCase() ) );
  	}
}