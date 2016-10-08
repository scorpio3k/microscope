package org.scorpio.microscope.enginex.core;

import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.Map;

public class MCallableStatement extends MPreparedStatement implements CallableStatement {


    protected CallableStatement callStmtPassthru;
    protected String callableQuery;

    public MCallableStatement(MFactory factory, CallableStatement statement, MConnection conn, String query) {
        super(factory, statement, conn, query);
        this.callableQuery = query;
        this.callStmtPassthru = statement;
    }

    public String getString(int p0) throws SQLException {
        return callStmtPassthru.getString(p0);
    }

    public void registerOutParameter(int p0, int p1) throws SQLException {
        if (p0 >= values.length) {
            growValues(p0);
        }
        callStmtPassthru.registerOutParameter(p0, p1);
    }

    public void registerOutParameter(int p0, int p1, int p2) throws SQLException {
        if (p0 >= values.length) {
            growValues(p0);
        }
        callStmtPassthru.registerOutParameter(p0, p1, p2);
    }

    public void registerOutParameter(int p0, int p1, String p2) throws SQLException {
        if (p0 >= values.length) {
            growValues(p0);
        }
        callStmtPassthru.registerOutParameter(p0, p1, p2);
    }

    public boolean wasNull() throws SQLException {
        return callStmtPassthru.wasNull();
    }

    public Array getArray(int p0) throws SQLException {
        return getMFactory().getArray(callStmtPassthru.getArray(p0), this, callableQuery, getQueryFromPreparedStatement());
    }

    public java.math.BigDecimal getBigDecimal(int p0) throws SQLException {
        return callStmtPassthru.getBigDecimal(p0);
    }

    public java.math.BigDecimal getBigDecimal(int p0, int p1) throws SQLException {
        return callStmtPassthru.getBigDecimal(p0, p1);
    }

    public Blob getBlob(int p0) throws SQLException {
        return callStmtPassthru.getBlob(p0);
    }

    public boolean getBoolean(int p0) throws SQLException {
        return callStmtPassthru.getBoolean(p0);
    }

    public byte getByte(int p0) throws SQLException {
        return callStmtPassthru.getByte(p0);
    }

    public byte[] getBytes(int p0) throws SQLException {
        return callStmtPassthru.getBytes(p0);
    }

    public Clob getClob(int p0) throws SQLException {
        return callStmtPassthru.getClob(p0);
    }

    public Date getDate(int p0) throws SQLException {
        return callStmtPassthru.getDate(p0);
    }

    public Date getDate(int p0, java.util.Calendar calendar) throws SQLException {
        return callStmtPassthru.getDate(p0, calendar);
    }

    public double getDouble(int p0) throws SQLException {
        return callStmtPassthru.getDouble(p0);
    }

    public float getFloat(int p0) throws SQLException {
        return callStmtPassthru.getFloat(p0);
    }

    public int getInt(int p0) throws SQLException {
        return callStmtPassthru.getInt(p0);
    }

    public long getLong(int p0) throws SQLException {
        return callStmtPassthru.getLong(p0);
    }

    public Object getObject(int p0) throws SQLException {
        return callStmtPassthru.getObject(p0);
    }


    public Ref getRef(int p0) throws SQLException {
        return callStmtPassthru.getRef(p0);
    }

    public short getShort(int p0) throws SQLException {
        return callStmtPassthru.getShort(p0);
    }

    public Time getTime(int p0) throws SQLException {
        return callStmtPassthru.getTime(p0);
    }

    public Time getTime(int p0, java.util.Calendar p1) throws SQLException {
        return callStmtPassthru.getTime(p0, p1);
    }

    public Timestamp getTimestamp(int p0) throws SQLException {
        return callStmtPassthru.getTimestamp(p0);
    }

    public Timestamp getTimestamp(int p0, java.util.Calendar p1) throws SQLException {
        return callStmtPassthru.getTimestamp(p0, p1);
    }

    // Since JDK 1.4
    public void registerOutParameter(String p0, int p1) throws SQLException {
        callStmtPassthru.registerOutParameter(p0, p1);
    }

    // Since JDK 1.4
    public void registerOutParameter(String p0, int p1, int p2) throws SQLException {
        callStmtPassthru.registerOutParameter(p0, p1, p2);
    }

    // Since JDK 1.4
    public void registerOutParameter(String p0, int p1, String p2) throws SQLException {
        callStmtPassthru.registerOutParameter(p0, p1, p2);
    }

    // Since JDK 1.4
    public java.net.URL getURL(int p0) throws SQLException {
        return (callStmtPassthru.getURL(p0));
    }

    // Since JDK 1.4
    public void setURL(String p0, java.net.URL p1) throws SQLException {
        callStmtPassthru.setURL(p0, p1);
    }

    // Since JDK 1.4
    public void setNull(String p0, int p1) throws SQLException {
        callStmtPassthru.setNull(p0, p1);
    }

    // Since JDK 1.4
    public void setBoolean(String p0, boolean p1) throws SQLException {
        callStmtPassthru.setBoolean(p0, p1);
    }

    // Since JDK 1.4
    public void setByte(String p0, byte p1) throws SQLException {
        callStmtPassthru.setByte(p0, p1);
    }

    // Since JDK 1.4
    public void setShort(String p0, short p1) throws SQLException {
        callStmtPassthru.setShort(p0, p1);
    }

    // Since JDK 1.4
    public void setInt(String p0, int p1) throws SQLException {
        callStmtPassthru.setInt(p0, p1);
    }

    // Since JDK 1.4
    public void setLong(String p0, long p1) throws SQLException {
        callStmtPassthru.setLong(p0, p1);
    }

    // Since JDK 1.4
    public void setFloat(String p0, float p1) throws SQLException {
        callStmtPassthru.setFloat(p0, p1);
    }

    // Since JDK 1.4
    public void setDouble(String p0, double p1) throws SQLException {
        callStmtPassthru.setDouble(p0, p1);
    }

    // Since JDK 1.4
    public void setBigDecimal(String p0, java.math.BigDecimal p1) throws SQLException {
        callStmtPassthru.setBigDecimal(p0, p1);
    }

    // Since JDK 1.4
    public void setString(String p0, String p1) throws SQLException {
        callStmtPassthru.setString(p0, p1);
    }

    // Since JDK 1.4
    public void setBytes(String p0, byte p1[]) throws SQLException {
        callStmtPassthru.setBytes(p0, p1);
    }

    // Since JDK 1.4
    public void setDate(String p0, Date p1) throws SQLException {
        callStmtPassthru.setDate(p0, p1);
    }

    // Since JDK 1.4
    public void setTime(String p0, Time p1) throws SQLException {
        callStmtPassthru.setTime(p0, p1);
    }

    // Since JDK 1.4
    public void setTimestamp(String p0, Timestamp p1) throws SQLException {
        callStmtPassthru.setTimestamp(p0, p1);
    }

    // Since JDK 1.4
    public void setAsciiStream(String p0, InputStream p1, int p2) throws SQLException {
        callStmtPassthru.setAsciiStream(p0, p1, p2);
    }

    // Since JDK 1.4
    public void setBinaryStream(String p0, InputStream p1, int p2) throws SQLException {
        callStmtPassthru.setBinaryStream(p0, p1, p2);
    }

    // Since JDK 1.4
    public void setObject(String p0, Object p1, int p2, int p3) throws SQLException {
        callStmtPassthru.setObject(p0, p1, p2, p3);
    }

    // Since JDK 1.4
    public void setObject(String p0, Object p1, int p2) throws SQLException {
        callStmtPassthru.setObject(p0, p1, p2);
    }

    // Since JDK 1.4
    public void setObject(String p0, Object p1) throws SQLException {
        callStmtPassthru.setObject(p0, p1);
    }

    // Since JDK 1.4
    public void setCharacterStream(String p0, Reader p1, int p2) throws SQLException {
        callStmtPassthru.setCharacterStream(p0, p1, p2);
    }

    // Since JDK 1.4
    public void setDate(String p0, Date p1, java.util.Calendar p2) throws SQLException {
        callStmtPassthru.setDate(p0, p1, p2);
    }

    // Since JDK 1.4
    public void setTime(String p0, Time p1, java.util.Calendar p2) throws SQLException {
        callStmtPassthru.setTime(p0, p1, p2);
    }

    // Since JDK 1.4
    public void setTimestamp(String p0, Timestamp p1, java.util.Calendar p2) throws SQLException {
        callStmtPassthru.setTimestamp(p0, p1, p2);
    }

    // Since JDK 1.4
    public void setNull(String p0, int p1, String p2) throws SQLException {
        callStmtPassthru.setNull(p0, p1, p2);
    }

    // Since JDK 1.4
    public String getString(String p0) throws SQLException {
        return (callStmtPassthru.getString(p0));
    }

    // Since JDK 1.4
    public boolean getBoolean(String p0) throws SQLException {
        return (callStmtPassthru.getBoolean(p0));
    }

    // Since JDK 1.4
    public byte getByte(String p0) throws SQLException {
        return (callStmtPassthru.getByte(p0));
    }

    // Since JDK 1.4
    public short getShort(String p0) throws SQLException {
        return (callStmtPassthru.getShort(p0));
    }

    // Since JDK 1.4
    public int getInt(String p0) throws SQLException {
        return (callStmtPassthru.getInt(p0));
    }

    // Since JDK 1.4
    public long getLong(String p0) throws SQLException {
        return (callStmtPassthru.getLong(p0));
    }

    // Since JDK 1.4
    public float getFloat(String p0) throws SQLException {
        return (callStmtPassthru.getFloat(p0));
    }

    // Since JDK 1.4
    public double getDouble(String p0) throws SQLException {
        return (callStmtPassthru.getDouble(p0));
    }

    // Since JDK 1.4
    public byte[] getBytes(String p0) throws SQLException {
        return (callStmtPassthru.getBytes(p0));
    }

    // Since JDK 1.4
    public Date getDate(String p0) throws SQLException {
        return (callStmtPassthru.getDate(p0));
    }

    // Since JDK 1.4
    public Time getTime(String p0) throws SQLException {
        return (callStmtPassthru.getTime(p0));
    }

    // Since JDK 1.4
    public Timestamp getTimestamp(String p0) throws SQLException {
        return (callStmtPassthru.getTimestamp(p0));
    }

    // Since JDK 1.4
    public Object getObject(String p0) throws SQLException {
        return (callStmtPassthru.getObject(p0));
    }

    // Since JDK 1.4
    public java.math.BigDecimal getBigDecimal(String p0) throws SQLException {
        return (callStmtPassthru.getBigDecimal(p0));
    }


    // Since JDK 1.4
    public Ref getRef(String p0) throws SQLException {
        return (callStmtPassthru.getRef(p0));
    }

    // Since JDK 1.4
    public Blob getBlob(String p0) throws SQLException {
        return (callStmtPassthru.getBlob(p0));
    }

    // Since JDK 1.4
    public Clob getClob(String p0) throws SQLException {
        return (callStmtPassthru.getClob(p0));
    }

    // Since JDK 1.4
    public Array getArray(String p0) throws SQLException {
        return (callStmtPassthru.getArray(p0));
    }

    // Since JDK 1.4
    public Date getDate(String p0, java.util.Calendar p1) throws SQLException {
        return (callStmtPassthru.getDate(p0, p1));
    }

    // Since JDK 1.4
    public Time getTime(String p0, java.util.Calendar p1) throws SQLException {
        return (callStmtPassthru.getTime(p0, p1));
    }

    // Since JDK 1.4
    public Timestamp getTimestamp(String p0, java.util.Calendar p1) throws SQLException {
        return (callStmtPassthru.getTimestamp(p0, p1));
    }

    // Since JDK 1.4
    public java.net.URL getURL(String p0) throws SQLException {
        return (callStmtPassthru.getURL(p0));
    }

    /**
     * Returns the underlying JDBC object (in this case, a
     * java.sql.CallableStatement).
     * <p>
     * The returned object is a java.sql.Statement due
     * to inheritance reasons, so you'll need to cast 
     * appropriately.
     *
     * @return the wrapped JDBC object 
     */
    public Statement getJDBC() {
        Statement wrapped = (callStmtPassthru instanceof MStatement) ?
                ((MStatement) callStmtPassthru).getJDBC() :
                callStmtPassthru;

        return wrapped;
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        callStmtPassthru.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value)
            throws SQLException {
        callStmtPassthru.setNString(parameterIndex, value);

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value,
                                    long length) throws SQLException {
        callStmtPassthru.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        callStmtPassthru.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        callStmtPassthru.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SQLException {
        callStmtPassthru.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        callStmtPassthru.setNClob(parameterIndex, reader, length);

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
            throws SQLException {
        callStmtPassthru.setSQLXML(parameterIndex, xmlObject);

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        callStmtPassthru.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        callStmtPassthru.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader,
                                   long length) throws SQLException {
        callStmtPassthru.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x)
            throws SQLException {
        callStmtPassthru.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x)
            throws SQLException {
        callStmtPassthru.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        callStmtPassthru.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value)
            throws SQLException {
        callStmtPassthru.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        callStmtPassthru.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        callStmtPassthru.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        callStmtPassthru.setNClob(parameterIndex, reader);
    }

    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map)
            throws SQLException {
        return callStmtPassthru.getObject(parameterIndex, map);
    }

    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map)
            throws SQLException {
        return callStmtPassthru.getObject(parameterName, map);
    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        return callStmtPassthru.getRowId(parameterIndex);
    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        return callStmtPassthru.getRowId(parameterName);
    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        callStmtPassthru.setRowId(parameterName, x);
    }

    @Override
    public void setNString(String parameterName, String value)
            throws SQLException {
        callStmtPassthru.setNString(parameterName, value);
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value,
                                    long length) throws SQLException {
        callStmtPassthru.setNCharacterStream(parameterName, value, length);
    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        callStmtPassthru.setNClob(parameterName, value);
    }

    @Override
    public void setClob(String parameterName, Reader reader, long length)
            throws SQLException {
        callStmtPassthru.setClob(parameterName, reader, length);
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream,
                        long length) throws SQLException {
        callStmtPassthru.setBlob(parameterName, inputStream, length);
    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length)
            throws SQLException {
        callStmtPassthru.setNClob(parameterName, reader, length);
    }

    @Override
    public NClob getNClob(int parameterIndex) throws SQLException {
        return callStmtPassthru.getNClob(parameterIndex);
    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        return callStmtPassthru.getNClob(parameterName);
    }

    @Override
    public void setSQLXML(String parameterName, SQLXML xmlObject)
            throws SQLException {
        callStmtPassthru.setSQLXML(parameterName, xmlObject);
    }

    @Override
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return callStmtPassthru.getSQLXML(parameterIndex);
    }

    @Override
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return callStmtPassthru.getSQLXML(parameterName);
    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        return callStmtPassthru.getNString(parameterIndex);
    }

    @Override
    public String getNString(String parameterName) throws SQLException {
        return callStmtPassthru.getNString(parameterName);
    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return callStmtPassthru.getNCharacterStream(parameterIndex);
    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return callStmtPassthru.getNCharacterStream(parameterName);
    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return callStmtPassthru.getCharacterStream(parameterIndex);
    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        return callStmtPassthru.getCharacterStream(parameterName);
    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        callStmtPassthru.setBlob(parameterName, x);
    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        callStmtPassthru.setClob(parameterName, x);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length)
            throws SQLException {
        callStmtPassthru.setAsciiStream(parameterName, x, length);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length)
            throws SQLException {
        callStmtPassthru.setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader,
                                   long length) throws SQLException {
        callStmtPassthru.setCharacterStream(parameterName, reader, length);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x)
            throws SQLException {
        callStmtPassthru.setAsciiStream(parameterName, x);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x)
            throws SQLException {
        callStmtPassthru.setBinaryStream(parameterName, x);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader)
            throws SQLException {
        callStmtPassthru.setCharacterStream(parameterName, reader);
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value)
            throws SQLException {
        callStmtPassthru.setNCharacterStream(parameterName, value);
    }

    @Override
    public void setClob(String parameterName, Reader reader)
            throws SQLException {
        callStmtPassthru.setClob(parameterName, reader);
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream)
            throws SQLException {
        callStmtPassthru.setBlob(parameterName, inputStream);
    }

    @Override
    public void setNClob(String parameterName, Reader reader)
            throws SQLException {
        callStmtPassthru.setNClob(parameterName, reader);
    }


}
