package org.scorpio.microscope.enginex;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.scorpio.microscope.enginex.core.Setting;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * <p>
 * Description:
 * </p>
 * <p>
 * <p>
 * Company: �����ź��ǿƼ��ɷ����޹�˾
 * </p>
 *
 * @author tzy
 * @since��2013-10-12 ����05:13:14
 */
public class NstcDbcpDataSource extends BasicDataSource {
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
