/*
 * Created on Dec 14, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package abbott.ai.tcgm.helpers;

/**
 * @author annampx
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;

public class SendEmail {
	private final String className = this.getClass().getName();

	private static Logger myLogger = Logger.getLogger("SendEmail");

	public SendEmail() {

	}

	public void sendCognosUserMail(User userBean) throws TCGMException {
		String methodName = "sendMail(ArrayList sendList,String senderEmail)";
		String subject = "Cognos Users Report";
		String body = "Cognos Users list is attached";
		try

		{
			myLogger.info("Sending email");
			java.util.Properties mailprop = new java.util.Properties();
			String host = "mail.abbott.com";
			String fromAddress = "";
			String toAddress = "";
			String fileName = System.getProperty("catalina.home")
					+ "/webapps/tcgm/include/users.txt";
			String subj = "Cognos Users List";
			String content = "Please find attached the list";
			mailprop.put("mail.smtp.host", host);
			Session session = Session.getInstance(mailprop, null);
			MimeMessage message = new MimeMessage(session);
			if(userBean != null) {
				fromAddress = userBean.getEmail();
				toAddress = userBean.getEmail();	
			}
			
			message.setFrom(new InternetAddress(fromAddress));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toAddress));
			message.setSubject(subj);
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(content);
			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(fileName);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			message.setContent(mp);
			message.setSentDate(new java.util.Date());
			Transport.send(message);

			System.out.println("email has been sent");
			myLogger.info("email has been sent");
		}

		catch (Exception ex)

		{
			System.out.println("ERROR....." + ex.toString());
			ex.printStackTrace();
			myLogger.error(className + ":" + methodName + ":" + ex.toString(),
					ex);
			throw new TCGMException(this.className, methodName, ex.toString());

		}
	}
	
	public void sendDatabaseUserMail(User userBean) throws TCGMException {
		String methodName = "sendDatabaseUserMail(ArrayList sendList,String senderEmail)";
		String subject = "Database User List";
		String body = "Database User List is attached";
		try

		{
			myLogger.info("Sending email");
			java.util.Properties mailprop = new java.util.Properties();
			String host = "mail.abbott.com";
			String fromAddress = "";
			String toAddress = "";
			String fileName = System.getProperty("catalina.home")
					+ "/webapps/tcgm/include/report.txt";
			String subj = "Database User List";
			String content = "Please find attached the list";
			mailprop.put("mail.smtp.host", host);
			Session session = Session.getInstance(mailprop, null);
			MimeMessage message = new MimeMessage(session);
			if (userBean != null) {
				fromAddress = userBean.getEmail();
				toAddress = userBean.getEmail();
			}

			message.setFrom(new InternetAddress(fromAddress));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toAddress));
			message.setSubject(subj);
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(content);
			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(fileName);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			message.setContent(mp);
			message.setSentDate(new java.util.Date());
			Transport.send(message);

			System.out.println("email has been sent");
			myLogger.info("email has been sent");
		}

		catch (Exception ex)

		{
			System.out.println("ERROR....." + ex.toString());
			ex.printStackTrace();
			myLogger.error(className + ":" + methodName + ":" + ex.toString(),
					ex);
			throw new TCGMException(this.className, methodName, ex.toString());

		}
	}

	public void sendEMail(String toAddress,String messageBody,String subject,String fromAddress,String fileName) throws TCGMException {
		String methodName = "sendEMail(String toAddress,String message)";		
		
		try

		{
			myLogger.info("Sending email");
			java.util.Properties mailprop = new java.util.Properties();
			String host = "mail.abbott.com";
			//String fromAddress = "tcgm.no-reply@abbvie.com";
			
			/*String fileName = System.getProperty("catalina.home")
					+ "/webapps/tcgm/include/users.txt";*/			
			mailprop.put("mail.smtp.host", host);
			Session session = Session.getInstance(mailprop, null);
			MimeMessage message = new MimeMessage(session);
			if(!fromAddress.equals(""))
			{
				message.setFrom(new InternetAddress(fromAddress));
			}
			/*message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toAddress));*/
			message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(toAddress, true));
			message.setSubject(subject);
			if(!fileName.equals("")){
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText(messageBody);
				MimeBodyPart mbp2 = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(fileName);
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(mbp1);
				mp.addBodyPart(mbp2);
				message.setContent(mp);
			}else{
				message.setContent(messageBody, "text/html");
			}
			message.setSentDate(new java.util.Date());
			Transport.send(message);

			System.out.println("email has been sent");
			myLogger.info("email has been sent");
		}

		catch (Exception ex)

		{
			System.out.println("ERROR....." + ex.toString());
			ex.printStackTrace();
			myLogger.error(className + ":" + methodName + ":" + ex.toString(),
					ex);
			throw new TCGMException(this.className, methodName, ex.toString());

		}
	}
}
