package abbott.ai.tcgm.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import abbott.ai.tcgm.process.DaemonMngr;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

	private static final Logger logger = LogManager.getLogger(AppLifecycleListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Application tcgm context initialized successfully.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Application tcgm context destruction initiated. Starting background daemon teardown...");
		try {
			DaemonMngr.stopProcessScheduler();
			logger.info("ProcessScheduler daemon termination request sent.");
		} catch (Exception e) {
			logger.error("Error stopping ProcessScheduler daemon: " + e.getMessage(), e);
		}
		try {
			DaemonMngr.stopProcessSchedulerMonitor();
			logger.info("ProcessSchedulerMonitor daemon termination request sent.");
		} catch (Exception e) {
			logger.error("Error stopping ProcessSchedulerMonitor daemon: " + e.getMessage(), e);
		}
		try {
			DaemonMngr.stopDataFeedMonitor();
			logger.info("DataFeedMonitor daemon termination request sent.");
		} catch (Exception e) {
			logger.error("Error stopping DataFeedMonitor daemon: " + e.getMessage(), e);
		}
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				logger.info("Successfully deregistered JDBC Driver: " + driver.toString());
			} catch (Exception e) {
				logger.error("Error deregistering JDBC Driver: " + driver.toString() + " - " + e.getMessage(), e);
			}
		}
		logger.info("Application tcgm background cleanup sequence completed.");
	}
}