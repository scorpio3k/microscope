package org.scorpio.microscope.enginex.core;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.math.*;

public class MPreparedStatement extends MStatement implements PreparedStatement {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public final static int P6_MAX_FIELDS = 32;
    public static int P6_GROW_MAX = 32;
    protected PreparedStatement prepStmtPassthru;
    protected String preparedQuery;
    protected Object values[];
    protected boolean isString[];

    public MPreparedStatement(MFactory factory, PreparedStatement statement, MConnection conn, String query) {
        super(factory, statement, conn);
        prepStmtPassthru = statement;
        this.preparedQuery = query;
        initValues();
    }

    protected void initValues() {
        values = new Object[P6_MAX_FIELDS + 1];
        isString = new boolean[P6_MAX_FIELDS + 1];
    }

    public void addBatch() throws SQLException {
        prepStmtPassthru.addBatch();
    }

    public void clearParameters() throws SQLException {
        prepStmtPassthru.clearParameters();
    }

    public boolean execute() throws SQLException {
        return prepStmtPassthru.execute();
    }

    public ResultSet executeQuery() throws SQLException {
        ResultSet resultSet = prepStmtPassthru.executeQuery();
        return (getMFactory().getResultSet(resultSet, this, preparedQuery, getQueryFromPreparedStatement()));
    }

    public int executeUpdate() throws SQLException {
        return prepStmtPassthru.executeUpdate();
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return prepStmtPassthru.getMetaData();
    }

    public void setArray(int p0, Array p1) throws SQLException {
        setObjectAsString(p0, p1);
        // we need to make sure we get the real object in this case
        if (p1 instanceof MArray) {
            prepStmtPassthru.setArray(p0, ((MArray) p1).passthru);
        } else {
            prepStmtPassthru.setArray(p0, p1);
        }
    }

    public void setAsciiStream(int p0, InputStream p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setAsciiStream(p0, p1, p2);
    }

    public void setBigDecimal(int p0, BigDecimal p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBigDecimal(p0, p1);
    }

    public void setBinaryStream(int p0, InputStream p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBinaryStream(p0, p1, p2);
    }

    public void setBlob(int p0, Blob p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBlob(p0, p1);
    }

    public void setBoolean(int p0, boolean p1) throws SQLException {
        setObjectAsString(p0, new Boolean(p1));
        prepStmtPassthru.setBoolean(p0, p1);
    }

    public void setByte(int p0, byte p1) throws SQLException {
        setObjectAsString(p0, new Byte(p1));
        prepStmtPassthru.setByte(p0, p1);
    }

    public void setBytes(int p0, byte[] p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setBytes(p0, p1);
    }

    public void setCharacterStream(int p0, Reader p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setCharacterStream(p0, p1, p2);
    }

    public void setClob(int p0, Clob p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setClob(p0, p1);
    }

    public void setDate(int p0, Date p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setDate(p0, p1);
    }

    public void setDate(int p0, Date p1, java.util.Calendar p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setDate(p0, p1, p2);
    }

    public void setDouble(int p0, double p1) throws SQLException {
        setObjectAsInt(p0, new Double(p1));
        prepStmtPassthru.setDouble(p0, p1);
    }

    public void setFloat(int p0, float p1) throws SQLException {
        setObjectAsInt(p0, new Float(p1));
        prepStmtPassthru.setFloat(p0, p1);
    }

    public void setInt(int p0, int p1) throws SQLException {
        setObjectAsInt(p0, new Integer(p1));
        prepStmtPassthru.setInt(p0, p1);
    }

    public void setLong(int p0, long p1) throws SQLException {
        setObjectAsInt(p0, new Long(p1));
        prepStmtPassthru.setLong(p0, p1);
    }

    public void setNull(int p0, int p1, String p2) throws SQLException {
        setObjectAsString(p0, null);
        prepStmtPassthru.setNull(p0, p1, p2);
    }

    public void setNull(int p0, int p1) throws SQLException {
        setObjectAsString(p0, null);
        prepStmtPassthru.setNull(p0, p1);
    }

    public void setObject(int p0, Object p1, int p2, int p3) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setObject(p0, p1, p2, p3);
    }

    public void setObject(int p0, Object p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setObject(p0, p1, p2);
    }

    public void setObject(int p0, Object p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setObject(p0, p1);
    }

    public void setRef(int p0, Ref p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setRef(p0, p1);
    }

    public void setShort(int p0, short p1) throws SQLException {
        setObjectAsString(p0, new Short(p1));
        prepStmtPassthru.setShort(p0, p1);
    }

    public void setString(int p0, String p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setString(p0, p1);
    }

    public void setTime(int p0, Time p1, java.util.Calendar p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTime(p0, p1, p2);
    }

    public void setTime(int p0, Time p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTime(p0, p1);
    }

    public void setTimestamp(int p0, Timestamp p1, java.util.Calendar p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTimestamp(p0, p1, p2);
    }

    public void setTimestamp(int p0, Timestamp p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setTimestamp(p0, p1);
    }

    public void setUnicodeStream(int p0, InputStream p1, int p2) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setUnicodeStream(p0, p1, p2);
    }

    /* we override this because the p6statement version will not be
     * able to return the accurate prepared statement or query information
     */
    // bug 161: getResultSet() should return null if this is an update
    // count or there are not more result sets
    public ResultSet getResultSet() throws SQLException {
        ResultSet rs = passthru.getResultSet();
        return (rs == null) ? null : getMFactory().getResultSet(rs, this, preparedQuery, getQueryFromPreparedStatement());
    }

    /*
     * P6Spy specific functionality
     */
    public final String getQueryFromPreparedStatement() {
        int len = preparedQuery.length();
        StringBuffer t = new StringBuffer(len * 2);

        if (values != null) {
            int i = 1, limit = 0, base = 0;

            while ((limit = preparedQuery.indexOf('?', limit)) != -1) {
                if (isString[i]) {
                    t.append(preparedQuery.substring(base, limit));
                    t.append("'");
                    t.append(values[i]);
                    t.append("'");
                } else {
                    t.append(preparedQuery.substring(base, limit));
                    t.append(objectToString(values[i]));
                }
                i++;
                limit++;
                base = limit;
            }
            if (base < len) {
                t.append(preparedQuery.substring(base));
            }
        }

        return t.toString();
    }

    protected void growValues(int newMax) {
        int size = values.length;
        Object[] values_tmp = new Object[newMax + P6_GROW_MAX];
        boolean[] isString_tmp = new boolean[newMax + P6_GROW_MAX];
        System.arraycopy(values, 0, values_tmp, 0, size);
        values = values_tmp;
        System.arraycopy(isString, 0, isString_tmp, 0, size);
        isString = isString_tmp;
    }


    protected void setObjectAsString(int i, Object o) {
        if (values != null) {
            if (i >= 0) {
                if (i >= values.length) {
                    growValues(i);
                }
                if (o == null) {
                    values[i] = "";
                    isString[i] = true;
                } else if (o instanceof Date) {
                    values[i] = o;
                    isString[i] = false;
                } else {
                    values[i] = o.toString();
                    isString[i] = true;
                }
            }
        }
    }

    protected String objectToString(Object o) {
        if (o == null) {
            return "";
        } else if (o instanceof Date) {
            try {
                if (getConnection().getMetaData().getDriverName().toUpperCase().indexOf("ORACLE") >= 0) {
                    return "to_date('" + dateFormat.format((Date) o) + "','yyyy-mm-dd hh24:mi:ss')";
                }
            } catch (Throwable e) {
            }
            return o.toString();
        } else {
            return o.toString();
        }
    }

    protected void setObjectAsInt(int i, Object o) {
        if (values != null) {
            if (i >= 0) {
                if (i >= values.length) {
                    growValues(i);
                }
                values[i] = (o == null) ? "" : o.toString();
                isString[i] = false;
            }
        }
    }

    // Since JDK 1.4
    public void setURL(int p0, java.net.URL p1) throws SQLException {
        setObjectAsString(p0, p1);
        prepStmtPassthru.setURL(p0, p1);
    }

    // Since JDK 1.4
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return (prepStmtPassthru.getParameterMetaData());
    }

    /**
     * Returns the underlying JDBC object (in this case, a
     * java.sql.PreparedStatement).
     * <p>
     * The returned object is a java.sql.Statement due
     * to inheritance reasons, so you'll need to cast
     * appropriately.
     *
     * @return the wrapped JDBC object
     */
    public Statement getJDBC() {
        Statement wrapped = (prepStmtPassthru instanceof MStatement) ?
                ((MStatement) prepStmtPassthru).getJDBC() :
                prepStmtPassthru;

        return wrapped;
    }

    public int getValuesLength() {
        return values.length;
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        setObjectAsString(parameterIndex, x);
        prepStmtPassthru.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        setObjectAsString(parameterIndex, value);
        prepStmtPassthru.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length)
            throws SQLException {
        setObjectAsString(parameterIndex, value);
        prepStmtPassthru.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        setObjectAsString(parameterIndex, value);
        prepStmtPassthru.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        setObjectAsString(parameterIndex, reader);
        prepStmtPassthru.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SQLException {
        setObjectAsString(parameterIndex, inputStream);
        prepStmtPassthru.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        setObjectAsString(parameterIndex, reader);
        prepStmtPassthru.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        setObjectAsString(parameterIndex, xmlObject);
        prepStmtPassthru.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        setObjectAsString(parameterIndex, x);
        prepStmtPassthru.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        setObjectAsString(parameterIndex, x);
        prepStmtPassthru.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length)
            throws SQLException {
        setObjectAsString(parameterIndex, reader);
        prepStmtPassthru.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x)
            throws SQLException {
        setObjectAsString(parameterIndex, x);
        prepStmtPassthru.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x)
            throws SQLException {
        setObjectAsString(parameterIndex, x);
        prepStmtPassthru.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        setObjectAsString(parameterIndex, reader);
        prepStmtPassthru.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value)
            throws SQLException {
        setObjectAsString(parameterIndex, value);
        prepStmtPassthru.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        setObjectAsString(parameterIndex, reader);
        prepStmtPassthru.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        setObjectAsString(parameterIndex, inputStream);
        prepStmtPassthru.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        setObjectAsString(parameterIndex, reader);
        prepStmtPassthru.setNClob(parameterIndex, reader);
    }
}
