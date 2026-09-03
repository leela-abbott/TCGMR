//Source file: C:\\DATA\\jbproject\\TCGM\\src\\abbott\\ai\\tcgm\\helpers\\S390RSystem.java

package abbott.ai.tcgm.helpers;

import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.comm.FTPSession;
import abbott.ai.tcgm.entities.UserToken;
import abbott.ai.tcgm.exception.TCGMException;

public class RSystem 
{
   private static Logger logger = Logger.getLogger("TCGM.Helpers.RSystem");
   private final String name;
   public static final RSystem RBL = new RSystem ("RBL");
   public static final RSystem RBB = new RSystem ("RBB");
   public static final RSystem RTC = new RSystem ("RTC");
   public static final RSystem RGM = new RSystem ("RGM");

   private static final String RSYSTEM_ADDRESS = AppConst.getRSystemAddress();
   private static final UserToken userToken = new UserToken( AppConst.getRSystemUsername(), AppConst.getRSystemPassword());
   
   /**
   @param name
   @roseuid 3D77C4D90302
	*/
   private RSystem(String name)
   {this.name = name;
   }

   /**
   @return java.lang.String
   @roseuid 3D77C4DA0000
	*/
   public String toString()
   {return name;
   }

   public static UserToken getUserToken() {
	   return userToken;
   }

   public static RSystem getObjFromName(String name) {
	   if (name.toUpperCase().equals("RBL") )
		   return RBL;
	   else if (name.toUpperCase().equals("RBB") )
		   return RBB;
	   else if (name.toUpperCase().equals("RTC") )
		   return RTC;
	   else if (name.toUpperCase().equals("RGM") )
		   return RGM;
	   else return null;
   }

   public static RSystem getObjFromCycle(String cycle) {
	   /*if (cycle.toUpperCase().equals("PLN") )
		   return RBL;
	   else if (cycle.toUpperCase().equals("UPD") )
		   return RBB;
	   else if (cycle.toUpperCase().equals("INV") )
		   return RTC;
	   else if (cycle.toUpperCase().equals("ACT") )
		   return RGM;
	   else return null;*/
   	if (cycle.toUpperCase().equals("P") )
		   return RBL;
	   else if (cycle.toUpperCase().equals("U") )
		   return RBB;	   
	   else if (cycle.toUpperCase().equals("A") )
		   return RGM;
	   else if (cycle.toUpperCase().equals("1") )
		   return RTC;
	   else if (cycle.toUpperCase().equals("2") )
		   return RTC;
	   else if (cycle.toUpperCase().equals("3") )
		   return RTC;
	   else return null;
   }

   public void sendJCL(String jcl) throws TCGMException {
	   try{
		   File tmp = File.createTempFile("jcl", null);
		   
		   FileWriter out = new FileWriter(tmp);
		   out.write(jcl);
		   out.close();

		   this.sendJCL(tmp);
		   
	   }catch(TCGMException te){
	   	throw new TCGMException(te);
	   }
	   catch (Exception ex) {
	   	throw new TCGMException("RSystem","sendJcl",ex.getMessage());
	   }

//		   if (! tmp.delete() )
//			   tmp.deleteOnExit();
	   
   }

   public void sendJCL(File file) throws TCGMException {
	   try {
		   FTPSession ftp = new FTPSession(RSYSTEM_ADDRESS);
		   ftp.connect();
		   ftp.login( this.userToken.getUserid(), this.userToken.getPassword() );
		   logger.debug(ftp.lastReply());
		   if (ftp.lastCode() != ftp.CODE_LOGGEDIN) {
			   logger.error("Failed to Log in\n");
			   throw new TCGMException("RSystem","sendJcl","Failed to Log in");
		   }

		   ftp.setMode(ftp.MODE_JES);
		   ftp.upload(file.getAbsolutePath(), "JOB31888");

		   logger.debug(ftp.lastReply());
		   if (ftp.lastCode() != ftp.CODE_CD_OK)
		   {
			   logger.error("error while uploading.");
			   ftp.disconnect();
			   return;
		   }

		   ftp.disconnect();
		   logger.debug(ftp.lastReply());
		   if (ftp.lastCode() != ftp.CODE_DISCONNECT_OK)
		   {
			   logger.error("disconnection failed.");
			   return;
		   }

		   logger.debug("OK.");
	   }
	   catch (Exception ex) {
	   		ex.printStackTrace();
	   		logger.error("Exception: " + ex.toString() );
	   		throw new TCGMException("RSystem","sendJcl",ex.getMessage());
		   
		}

   }
   public void sendFactCCS(String file)throws TCGMException {
		try {
		 File tmp = new File(file);
		 this.sendFactCCS(tmp);
		 tmp=null;
	
		 }
		 catch (Exception ex) {
			 this.logger.error("Exception: " + ex.getMessage() );
			 throw new TCGMException("RSystem","sendFactCCS",ex.getMessage());
		  }

	  }

	public void sendFactCCS(File file) throws TCGMException {
		   try {
			   FTPSession ftp = new FTPSession(AppConst.getCcshost());//"uapd5005p.northamerica.intra.abbott.com");//uapd5004q.northamerica.intra.abbott.com
			   ftp.connect();
			   //ftp.login( "ftp_ccs", "CC$ftp123" );
			   ftp.login( AppConst.getCcsuser(), AppConst.getCcspassword() );
			   logger.debug(ftp.lastReply());
			   if (ftp.lastCode() != ftp.CODE_LOGGEDIN) {
				   logger.error("Failed to Log in\n");
				   throw new TCGMException("RSystem","sendFactCCS","Failed to Log in");
			   }
			   //ftp.cd("/prod/ai/usrdat/ccs");
			   ftp.cd(AppConst.getCcslocation());
			   //ftp.setMode(ftp.MODE_JES);
			   ftp.setMode(ftp.MODE_ASCII);
			   ftp.upload(file.getAbsolutePath(), file.getName());

			   logger.debug(ftp.lastReply());
			   if (ftp.lastCode() != ftp.CODE_CD_OK)
			   {
				   logger.error("error while uploading.");
				   ftp.disconnect();
				   return;
			   }

			   ftp.disconnect();
			   logger.debug(ftp.lastReply());
			   if (ftp.lastCode() != ftp.CODE_DISCONNECT_OK)
			   {
				   logger.error("disconnection failed.");
				   return;
			   }

			   logger.debug("OK.");
		   }
		   catch (Exception ex) {
				ex.printStackTrace();
			   logger.error("Exception: " + ex.toString() );
			   throw new TCGMException("RSystem","sendFactCCS",ex.getMessage());
			}

	   }
	
	public void sendFactIPS(String file) {
		try {
		 File tmp = new File(file);
		 this.sendFactIPS(tmp);
		 tmp=null;
	
		 }
		 catch (Exception ex) {
			 this.logger.error("Exception: " + ex.getMessage() );
		  }

	  }
	
	public void sendFactIPS(File file)throws TCGMException  {
		   try {
			   FTPSession ftp = new FTPSession(AppConst.getCcshost()); //AppConst.getCcshost()
			   ftp.connect();
			   //ftp.login( "ftpaiusr", "Ftp$8108" );
			   ftp.login( AppConst.getIpsuser(), AppConst.getIpspassword() );
			   logger.debug(ftp.lastReply());
			   if (ftp.lastCode() != ftp.CODE_LOGGEDIN) {
				   logger.error("Failed to Log in\n");
				   throw new TCGMException("RSystem","sendFactIPS","Failed to Log in");
			   }
			   //ftp.cd("/ora02/test/ai/usrdat/ips/");
			   ftp.cd(AppConst.getIpslocation());
			   //ftp.setMode(ftp.MODE_JES);
			   ftp.setMode(ftp.MODE_ASCII);
			   ftp.upload(file.getAbsolutePath(), "asr_tree.txt");
			   
			   String filePath = System.getProperty("catalina.home")+"/webapps/tcgm/include/asr_tree.imp";

			   ftp.upload(filePath, "asr_tree.imp");
			   
			   logger.debug(ftp.lastReply());
			   if (ftp.lastCode() != ftp.CODE_CD_OK)
			   {
				   logger.error("error while uploading.");
				   ftp.disconnect();
				   return;
			   }

			   ftp.disconnect();
			   logger.debug(ftp.lastReply());
			   if (ftp.lastCode() != ftp.CODE_DISCONNECT_OK)
			   {
				   logger.error("disconnection failed.");
				   return;
			   }

			   logger.debug("OK.");
		   }
		   catch (Exception ex) {
				ex.printStackTrace();
			   logger.error("Exception: " + ex.toString() );
			   throw new TCGMException("RSystem","sendFactIPS",ex.getMessage());
			}

	   }
}
