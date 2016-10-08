package org.scorpio.microscope.enginex;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.scorpio.microscope.enginex.core.MDriverCore;
import org.scorpio.microscope.enginex.core.Setting;


public class NstcDataSource implements DataSource {
    private String driverClass;
    private String jdbcUrl;
    private String user;
    private String password;
    private MDriverCore driver;

    public NstcDataSource() {
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUrl(String jdbcUrl) {
        setJdbcUrl(jdbcUrl);
    }

    public void setUsername(String user) {
        setUser(user);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MDriverCore getDriver() {
        return driver;
    }

    public void setDriver(MDriverCore driver) {
        this.driver = driver;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(getDriverClass());
            setP6SpyDriverCore();
            if (driver != null && !DriverManager.getDrivers().hasMoreElements()) {
                DriverManager.registerDriver(driver);
            }
            conn = DriverManager.getConnection(getJdbcUrl(), getUser(),
                    getPassword());
        } catch (ClassNotFoundException e) {
            throw new SQLException("数据库驱动无法找到-ClassNotFoundException"
                    + e.getMessage());
        } catch (SQLException e) {
            throw e;
        }
        if (!Setting.isCheckConnLeaks()) {
            Setting.enableConnLeaks();
        }
        ConnLeaksCheckUtils.addConn2Thread(conn);
        return conn;
    }

    public void setP6SpyDriverCore() {
        if (this.driver != null) {
            return;
        }
        for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements(); ) {
            Object dr = e.nextElement();
            if (dr instanceof MDriverCore) {
                this.driver = (MDriverCore) dr;
            }
        }
    }

    public Connection getConnection(String arg0, String arg1)
            throws SQLException {
        return null;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO by kevin
        return null;
    }

    public void setLogWriter(PrintWriter arg0) throws SQLException {

    }

    public void setLoginTimeout(int arg0) throws SQLException {

    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
