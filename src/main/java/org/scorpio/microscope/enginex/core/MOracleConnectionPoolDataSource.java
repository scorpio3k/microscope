package org.scorpio.microscope.enginex.core;


import java.sql.Connection;
import java.sql.SQLException;

import org.scorpio.microscope.enginex.logging.MLogFactory;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

public class MOracleConnectionPoolDataSource extends OracleConnectionPoolDataSource {
    /**
     * P6���Ĺ���
     */
    protected static MFactory mFactory = new MLogFactory();

    public MOracleConnectionPoolDataSource() throws SQLException {
        super();
    }

    public Connection getConnection() throws SQLException {
        return new MConnection(mFactory, super.getConnection());
    }

}
