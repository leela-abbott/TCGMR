/*
 * Created on Nov 18, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package abbott.ai.tcgm.helpers;

import java.util.ArrayList;

import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author annampx
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DatabaseUsersThread implements Runnable {
	
	private User user = null;
	
	private ArrayList al=null;
	
	public DatabaseUsersThread() {
		
	}
	
	public DatabaseUsersThread(User user, ArrayList al) {
		this.user = user;
		this.al = al;
	}

	public void run() {
		try {

			System.out.println("inside run of DatabaseUsersThread");
			ReportOverview ro= new ReportOverview();
			ro.checkUsers(al);
			
			SendEmail s = new SendEmail();
			s.sendDatabaseUserMail(user);
			
		} catch (TCGMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Thread executed!");
	}

	public static void main(String[] args) {

		//create a Thread object and pass it an object of type Runnable
		Thread thread = new Thread(new DatabaseUsersThread(new User(), new ArrayList()));
		thread.start();
	}
}
