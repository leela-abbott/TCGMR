//Source file: C:\\DATA\\jbproject\\TCGM\\src\\abbott\\ai\\tcgm\\helpers\\AS400.java

package abbott.ai.tcgm.helpers;

import abbott.ai.tcgm.comm.*;
import abbott.ai.tcgm.entities.*;
import abbott.ai.tcgm.exception.*;
import abbott.ai.tcgm.data.as400.*;

import java.io.*;
import org.apache.log4j.Logger;

public class AS400
{
   private static UserToken userToken;
   private static final String SERVER_NAME = "AP41";
   public AS400FileType theAS400FileType;
   private static Logger logger = Logger.getLogger("TCGM.Helpers.AS400");


   /**
   @roseuid 3D77D4260047
	*/
   public AS400(UserToken ut)
   {
	   this.userToken = ut;
   }

   /**
   @roseuid 3D77C5D0034B
	*/
   public void sendTextFile(File file, String targetName)
   {
	   try {
		   FTPSession ftp = new FTPSession(this.SERVER_NAME);
		   ftp.connect();
		   ftp.login( userToken.getUserid(), userToken.getPassword() );
		   logger.debug("sendTextFile signon used: " + userToken.getUserid() + "," + userToken.getPassword());
		   logger.debug(ftp.lastReply());
		   if (ftp.lastCode() != ftp.CODE_LOGGEDIN) {
			   logger.debug("Failed to Log in\n");
			   return;
		   }

		   ftp.setMode( ftp.MODE_ASCII );
		   ftp.setTransfer( ftp.TRANSFER_PASV );
		   ftp.upload(file.getAbsolutePath(), targetName);

		   this.logger.debug(ftp.lastReply());
		   if (ftp.lastCode() != ftp.CODE_CD_OK)
		   {
			   logger.debug("error while uploading.");
			   ftp.disconnect();
			   return;
		   }

		   ftp.disconnect();
		   this.logger.debug(ftp.lastReply());
		   if (ftp.lastCode() != ftp.CODE_DISCONNECT_OK)
		   {
			   logger.debug("disconnection failed.");
			   return;
		   }

		   logger.debug("OK.");
		   if (! file.delete() )
			   file.deleteOnExit();
	   }
	   catch (Exception ex) {
		   logger.error("Exception: " + ex.getMessage() );
		}
   }


   public void insertReportTriggerRecord(ReportPrintRequest rpr, ReportInstance instance, String targetFileName, String member) throws TCGMException {

	   AS400ReportQueDao rqdao = new AS400ReportQueDao();
	   rqdao.insertReportTriggerRecord(rpr, instance, targetFileName, member);
   }

   public void insertAnlFlexEssTriggerRecord(String year, String version, String atype) throws TCGMException
   {
	   AS400EssbaseLoadDao essdao = new AS400EssbaseLoadDao();
	   essdao.insertAnlFlexEssTriggerRecord(year, version, atype);
   }
   public void insertProductEssTriggerRecord(String strFlag) throws TCGMException
   {
	   AS400EssbaseLoadDao essdao = new AS400EssbaseLoadDao();
	   essdao.insertProductEssTriggerRecord(strFlag);
   }
   public void insertAffEssTriggerRecord(String strFlag) throws TCGMException
  {
	  AS400EssbaseLoadDao essdao = new AS400EssbaseLoadDao();
	  essdao.insertAffEssTriggerRecord(strFlag);
  }

   /**
   @roseuid 3D77C8F10198
	*/
   public void setFileType()
   {

   }

   /**
   @roseuid 3D77C5DF0176
	*/
   public void getFile()
   {

   }

   /**
   @roseuid 3D77C5E103B4
	*/
   public void connect()
   {

   }

   /**
   @roseuid 3D77C5E30385
	*/
   public void close()
   {

   }
}
