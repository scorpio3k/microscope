package org.scorpio.microscope.enginex.core;

import java.sql.*;
import javax.sql.*;


public class MPooledConnection implements PooledConnection {

    protected PooledConnection passthru;

    public MPooledConnection(PooledConnection connection) {
        passthru = connection;
    }


    public Connection getConnection() throws SQLException {
        return MDriverCore.wrapConnection(passthru.getConnection());
    }

    public void close() throws SQLException {
        passthru.close();
    }

    public void addConnectionEventListener(ConnectionEventListener eventTarget) {
        passthru.addConnectionEventListener(eventTarget);
    }


    public void removeConnectionEventListener(ConnectionEventListener eventTarget) {
        passthru.removeConnectionEventListener(eventTarget);
    }


    @Override
    public void addStatementEventListener(StatementEventListener listener) {
        passthru.addStatementEventListener(listener);
    }


    @Override
    public void removeStatementEventListener(StatementEventListener listener) {
        passthru.removeStatementEventListener(listener);
    }

}
