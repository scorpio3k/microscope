package org.scorpio.microscope.enginex.logging;

import org.scorpio.microscope.enginex.common.*;
import org.scorpio.microscope.enginex.core.*;

import java.sql.*;

public class MLogCallableStatement extends MCallableStatement implements CallableStatement {


    // ---------------------------------------------------------------------------------------
    // considered delegation for this, but that doesn't quite work because MCallableStatement
    // manipulates some values - so we would have to make MCallableStatement delegate as well,
    // which really defeats the purpose.  this means we do have to copy all of the methods
    // we want to use in MStatement and MPreparedStatement.  to understand why we are doing this
    // realize that MLogCallableStatement inherits from P6Callabletatement which inherits from
    // MPreparedStatement, which in turn inherits from MStatement.  So MLogCallableStatement
    // never inherits from MLogPreparedStatement and therefore it does not inherit any of the
    // functionality we define in P6PreparedLogStatement.
    // ---------------------------------------------------------------------------------------

    public MLogCallableStatement(MFactory factory, CallableStatement statement, MConnection conn, String query) {
        super(factory, statement, conn, query);
    }

    public void addBatch() throws SQLException {
        statementQuery = getQueryFromPreparedStatement();
        long startTime = System.currentTimeMillis();
        try {
            prepStmtPassthru.addBatch();
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "batch", preparedQuery, getQueryFromPreparedStatement());
        }
    }

    public boolean execute() throws SQLException {
        long startTime = System.currentTimeMillis();

        try {
            return prepStmtPassthru.execute();
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", preparedQuery, getQueryFromPreparedStatement());
        }
    }

    public ResultSet executeQuery() throws SQLException {
        long startTime = System.currentTimeMillis();

        try {
            return getMFactory().getResultSet(prepStmtPassthru.executeQuery(), this, preparedQuery, getQueryFromPreparedStatement());
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", preparedQuery, getQueryFromPreparedStatement());
        }
    }

    public int executeUpdate() throws SQLException {
        long startTime = System.currentTimeMillis();

        try {
            return prepStmtPassthru.executeUpdate();
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", preparedQuery, getQueryFromPreparedStatement());
        }
    }

    public boolean execute(String p0) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return passthru.execute(p0);
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    // Since JDK 1.4
    public boolean execute(String p0, int p1) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return passthru.execute(p0, p1);
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    // Since JDK 1.4
    public boolean execute(String p0, int p1[]) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return passthru.execute(p0, p1);
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    // Since JDK 1.4
    public boolean execute(String p0, String p1[]) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return passthru.execute(p0, p1);
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    public ResultSet executeQuery(String p0) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return getMFactory().getResultSet(passthru.executeQuery(p0), this, "", p0);
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    public int executeUpdate(String p0) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return (passthru.executeUpdate(p0));
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    // Since JDK 1.4
    public int executeUpdate(String p0, int p1) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return (passthru.executeUpdate(p0, p1));
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    // Since JDK 1.4
    public int executeUpdate(String p0, int p1[]) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return (passthru.executeUpdate(p0, p1));
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    // Since JDK 1.4
    public int executeUpdate(String p0, String p1[]) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            return (passthru.executeUpdate(p0, p1));
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", p0);
        }
    }

    public void addBatch(String p0) throws SQLException {
        statementQuery = p0;
        long startTime = System.currentTimeMillis();

        try {
            passthru.addBatch(p0);
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "batch", "", p0);
        }
    }

    public int[] executeBatch() throws SQLException {
        long startTime = System.currentTimeMillis();

        try {
            return (passthru.executeBatch());
        } finally {
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", preparedQuery, statementQuery);
        }
    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        //TODO by kevin
        return null;
    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        //TODO by kevin
        return null;
    }
}
