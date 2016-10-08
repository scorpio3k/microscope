package org.scorpio.microscope.enginex.core;

import java.sql.SQLException;

public abstract class MBase {
    protected MFactory factory;

    public void setMFactory(MFactory inVar) {
        factory = inVar;
    }

    public MFactory getMFactory() {
        return factory;
    }

    public void closeOnCompletion() throws SQLException {

    }

    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return null;
    }

    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return null;
    }
}

