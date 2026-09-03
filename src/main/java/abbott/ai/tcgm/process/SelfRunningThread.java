package abbott.ai.tcgm.process;

import org.apache.log4j.Logger;

public abstract class SelfRunningThread {

	private Thread internalThread;
	protected volatile boolean noStopRequested;
	protected int sleeptime;

	private static Logger logger = Logger.getLogger("TCGM.Process.SelfRunningThread");

	public SelfRunningThread(int sleeptime) {
		this.sleeptime = sleeptime;
		logger.debug("In constructor. Initializing.");
		noStopRequested = true;
	}

	protected void start() {
		Runnable r = new Runnable() {
			public void run() {
				try {
					runWork();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};

		internalThread = new Thread(r);
		internalThread.start();
	}

	protected abstract void runWork();

	public void stopRequested() {
		noStopRequested = false;
		internalThread.interrupt();
	}

	public boolean isAlive() {
		return internalThread.isAlive();
	}
}
