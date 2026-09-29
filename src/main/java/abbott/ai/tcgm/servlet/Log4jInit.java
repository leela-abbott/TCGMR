package abbott.ai.tcgm.servlet;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class Log4jInit extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger myLogger = LogManager.getLogger("Log4jInit");

    public Log4jInit() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        String basePath = context.getRealPath("/");
        String initFile = config.getInitParameter("log4j-init-file");

        if (basePath == null) {
            basePath = "";
        }

        if (!basePath.endsWith(File.separator)) {
            basePath = basePath + File.separator;
        }

        if (initFile != null) {
            String fullPath = basePath + initFile.replace("\\", File.separator).replace("/", File.separator);
            File log4jFile = new File(fullPath);

            if (log4jFile.exists()) {
                LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
                loggerContext.setConfigLocation(log4jFile.toURI());
            } else {
                System.err.println("Log4jInit ERROR: Configuration file not found at " + fullPath);
                throw new ServletException("Unable to locate Log4j configuration file at: " + fullPath);
            }
        } else {
            myLogger.error("path: " + basePath);
            myLogger.error("file: " + initFile);
            throw new ServletException("Unable to locate \"log4j-init-file\" init-param");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
