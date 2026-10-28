package abbott.ai.tcgm.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseCleanupListener implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger(DatabaseCleanupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Cleaning up JDBC drivers and background threads to prevent Tomcat memory leaks...");

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOG.info("Successfully deregistered JDBC driver: {}", driver);
            } catch (Exception e) {
                LOG.error("Error deregistering driver " + driver, e);
            }
        }

        try {
            Class<?> oracleDriverClass = Class.forName("oracle.jdbc.driver.OracleDriver");
            java.lang.reflect.Method cleanupMethod = oracleDriverClass.getMethod("deregister");
            if (cleanupMethod != null) {
                cleanupMethod.invoke(null);
                LOG.info("Triggered Oracle driver internal thread deregistration cleanup.");
            }
        } catch (ClassNotFoundException e) {
        } catch (Exception e) {
            LOG.error("Failed to run Oracle thread cleanup reflection routine", e);
        }
    }
}
