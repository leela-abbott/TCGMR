/*
 * Created on Nov 18, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package abbott.ai.tcgm.helpers;

import abbott.ai.tcgm.entities.User;
import abbott.ai.tcgm.exception.TCGMException;

/**
 * @author annampx
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CognosUsersThread implements Runnable {
	
	private User user = null;
	
	public CognosUsersThread() {
		
	}
	
	public CognosUsersThread(User user) {
		this.user = user;
	}

	public void run() {
		try {

			System.out.println("inside run");
			SecurityOverview.generateUsers();
			
			SendEmail s = new SendEmail();
			s.sendCognosUserMail(user);
			
		} catch (TCGMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Thread executed!");
	}

	public static void main(String[] args) {

		//create a Thread object and pass it an object of type Runnable
		Thread thread = new Thread(new CognosUsersThread(new User()));
		thread.start();
	}
}
