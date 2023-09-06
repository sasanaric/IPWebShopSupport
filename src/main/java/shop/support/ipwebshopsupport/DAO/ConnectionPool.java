package shop.support.ipwebshopsupport.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConnectionPool {

    private String jdbcURL;
    private String username;
    private String password;
    private int preconnectCount;
    private int maxIdleConnections;
    private int maxConnections;

    private int connectCount;
    private List<Connection> usedConnections;
    private List<Connection> freeConnections;

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    private ConnectionPool() {
        readConfiguration();
        try {
            freeConnections = new ArrayList<>();
            usedConnections = new ArrayList<>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < preconnectCount; i++) {
                Connection conn = DriverManager.getConnection(jdbcURL, username, password);
                freeConnections.add(conn);
            }
            connectCount = preconnectCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readConfiguration() {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("application");
        jdbcURL = bundle.getString("jdbcURL");
        username = bundle.getString("username");
        password = bundle.getString("password");
        preconnectCount = 0;
        maxIdleConnections = 100;
        maxConnections = 100;
        try {
            preconnectCount = Integer.parseInt(bundle
                    .getString("preconnectCount"));
            maxIdleConnections = Integer.parseInt(bundle
                    .getString("maxIdleConnections"));
            maxConnections = Integer.parseInt(bundle
                    .getString("maxConnections"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Connection checkOut() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        if (freeConnections.size() > 0) {
            conn = freeConnections.remove(0);
            usedConnections.add(conn);
        } else {
            if (connectCount < maxConnections) {
                conn = DriverManager.getConnection(jdbcURL, username, password);
                usedConnections.add(conn);
                connectCount++;
            } else {
                try {
                    wait();
                    conn = freeConnections.remove(0);
                    usedConnections.add(conn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return conn;
    }

    public synchronized void checkIn(Connection conn) {
        if (conn == null)
            return;
        if (usedConnections.remove(conn)) {
            freeConnections.add(conn);
            while (freeConnections.size() > maxIdleConnections) {
                int lastOne = freeConnections.size() - 1;
                Connection c = freeConnections.remove(lastOne);
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            notify();
        }
    }

    public synchronized void shutdownPool() {
        for (Connection connection : freeConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Connection connection : usedConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        freeConnections.clear();
        usedConnections.clear();
    }


}