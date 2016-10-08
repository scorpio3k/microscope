package org.scorpio.microscope.enginex.logging;

import java.sql.*;

import org.scorpio.microscope.enginex.common.*;
import org.scorpio.microscope.enginex.core.*;

public class MLogStatement extends MStatement implements Statement {


    public MLogStatement(MFactory factory, Statement statement, MConnection conn) {
        super(factory, statement, conn);
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
        // TODO by kevin
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        // TODO by kevin
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
            MLogQuery.logElapsed(this.connection.getId(), startTime, "statement", "", statementQuery);
        }
    }
}
