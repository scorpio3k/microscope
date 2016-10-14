package org.scorpio.microscope.enginex;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.scorpio.microscope.enginex.core.Setting;


public class MDbcpDataSource extends BasicDataSource {
    public Connection getConnection() throws SQLException {
        Connection conn = super.getConnection();
        return proxyConnection(conn);
    }

    public Connection getConnection(String username, String password)
            throws SQLException {
        Connection conn = super.getConnection(username, password);
        return proxyConnection(conn);
    }

    private Connection proxyConnection(Connection conn) throws SQLException {
        if (Setting.isCheckConnLeaks()) {
            ConnLeaksCheckUtils.addConn2Thread(conn);
        }
        return conn;
    }
}
