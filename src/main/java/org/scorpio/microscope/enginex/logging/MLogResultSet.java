package org.scorpio.microscope.enginex.logging;

import org.scorpio.microscope.enginex.core.*;

import java.sql.*;

public class MLogResultSet extends MResultSet implements ResultSet {
    public MLogResultSet(MFactory factory, ResultSet resultSet,
                         MStatement statement, String preparedQuery, String query) {
        super(factory, resultSet, statement, preparedQuery, query);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        // TODO by kevin
        return null;
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        // TODO by kevin
        return null;
    }
}
