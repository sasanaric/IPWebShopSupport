package shop.support.ipwebshopsupport.DAO;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.shutdownPool();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }
}
