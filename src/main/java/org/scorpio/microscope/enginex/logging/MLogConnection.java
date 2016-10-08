package org.scorpio.microscope.enginex.logging;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.concurrent.Executor;

import org.scorpio.microscope.enginex.ConnLeaksCheckUtils;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.core.MConnection;
import org.scorpio.microscope.enginex.core.MFactory;
import org.scorpio.microscope.enginex.core.Setting;


public class MLogConnection extends MConnection implements
        Connection {


    public MLogConnection(MFactory factory, Connection conn)
            throws SQLException {
        super(factory, conn);
        MLogQuery.logElapsed(this.getId(), 0, "getConn", "", "");
    }

    public boolean getAutoCommit() throws SQLException {
        return super.getAutoCommit();
    }

    public void commit() throws SQLException {
        long startTime = System.currentTimeMillis();

        try {
            passthru.commit();
        } finally {
            MLogQuery.logElapsed(this.getId(), startTime, "commit", "", "");
        }
    }

    public void rollback() throws SQLException {
        long startTime = System.currentTimeMillis();

        try {
            passthru.rollback();
        } finally {
            MLogQuery.logElapsed(this.getId(), startTime, "rollback", "", "");
        }
    }

    public void rollback(Savepoint p0) throws SQLException {
        long startTime = System.currentTimeMillis();

        try {
            passthru.rollback(p0);
        } finally {
            MLogQuery.logElapsed(this.getId(), startTime, "rollback", "", "");
        }
    }

    @Override
    public void setSchema(String schema) throws SQLException {
       //TODO by kevin
    }

    @Override
    public String getSchema() throws SQLException {
        //TODO by kevin
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        //TODO by kevin
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        //TODO by kevin
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        // TODO by kevin
        return 0;
    }

    public void close() throws SQLException {
        try {
            super.close();
        } finally {
            MLogQuery.logElapsed(this.getId(), 0, "close", "", "");
            if (Setting.isCheckConnLeaks()) {
                ConnLeaksCheckUtils.removeConnOnThread(this);
            }
        }
    }
}
