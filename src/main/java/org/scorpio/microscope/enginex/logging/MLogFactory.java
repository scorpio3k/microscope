package org.scorpio.microscope.enginex.logging;

import org.scorpio.microscope.enginex.core.MConnection;
import org.scorpio.microscope.enginex.core.MFactory;
import org.scorpio.microscope.enginex.core.MStatement;

import java.sql.*;

public class MLogFactory implements MFactory {

    public MLogFactory() {
    }

    public Connection getConnection(Connection conn) throws SQLException {
        return new MLogConnection(this, conn);
    }

    public PreparedStatement getPreparedStatement(PreparedStatement real,
                                                  MConnection conn, String p0) throws SQLException {
        return new MLogPreparedStatement(this, real, conn, p0);
    }

    public Statement getStatement(Statement statement, MConnection conn)
            throws SQLException {
        return new MLogStatement(this, statement, conn);
    }

    public CallableStatement getCallableStatement(CallableStatement real,
                                                  MConnection conn, String p0) throws SQLException {
        return new MLogCallableStatement(this, real, conn, p0);
    }

    public ResultSet getResultSet(ResultSet real, MStatement statement,
                                  String preparedQuery, String query) throws SQLException {
//		return new MLogResultSet(this, real, statement, preparedQuery, query);
        return real;
    }

    public DatabaseMetaData getDatabaseMetaData(DatabaseMetaData real,
                                                MConnection conn) throws SQLException {
//		return new MDatabaseMetaData(this, real, conn);
        return real;
    }

    public Array getArray(Array real, MStatement statement,
                          String preparedQuery, String query) throws SQLException {
//		return new MArray(this, real, statement, preparedQuery, query);
        return real;
    }

    public ResultSetMetaData getResultSetMetaData(ResultSetMetaData real)
            throws SQLException {
//		return new MResultSetMetaData(this, real);
        return real;
    }

}
