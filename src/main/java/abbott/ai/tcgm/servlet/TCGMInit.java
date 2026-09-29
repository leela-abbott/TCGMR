package abbott.ai.tcgm.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import abbott.ai.tcgm.AppConst;
import abbott.ai.tcgm.data.DatasetConst;
import abbott.ai.tcgm.data.SQLUtil;
import abbott.ai.tcgm.process.DaemonMngr;

public class TCGMInit extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(TCGMInit.class);

    public TCGMInit() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DatasetConst dsConst = DatasetConst.getInstance();

        try {
            dsConst.init();
            AppConst.init(config);
            SQLUtil.init(config);
            DaemonMngr.init(config);
            
            DaemonMngr dm = DaemonMngr.getInstance();                
            dm.startProcessScheduler();
            dm.startProcessSchedulerMonitor();
            dm.startDataFeedMonitor();
            
        } catch(Exception e) {
            System.err.println("Error Initializing TCGM Application: " + e.getMessage());
            throw new ServletException(e);
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
