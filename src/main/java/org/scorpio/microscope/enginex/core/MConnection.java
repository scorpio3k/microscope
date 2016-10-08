package org.scorpio.microscope.enginex.core;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MConnection extends MBase implements Connection {
    protected Connection passthru;

    public MConnection(MFactory factory, Connection conn) throws SQLException {
        setMFactory(factory);
        this.passthru = conn;
    }

    public void setReadOnly(boolean p0) throws SQLException {
        passthru.setReadOnly(p0);
    }

    public void close() throws SQLException {
        passthru.close();
    }

    public int getId() {
        return passthru.hashCode();
    }

    public boolean isClosed() throws SQLException {
        return (passthru.isClosed());
    }

    public boolean isReadOnly() throws SQLException {
        return (passthru.isReadOnly());
    }

    public Statement createStatement() throws SQLException {
        Statement statement = getMFactory().getStatement(passthru.createStatement(), this);
        return (statement);
    }

    public Statement createStatement(int p0, int p1) throws SQLException {
        Statement statement = getMFactory().getStatement(passthru.createStatement(p0, p1), this);
        return (statement);
    }

    public PreparedStatement prepareStatement(String p0) throws SQLException {
        return (getMFactory().getPreparedStatement(passthru.prepareStatement(p0), this, p0));
    }

    public PreparedStatement prepareStatement(String p0, int p1, int p2) throws SQLException {
        return (getMFactory().getPreparedStatement(passthru.prepareStatement(p0, p1, p2), this, p0));
    }

    public CallableStatement prepareCall(String p0) throws SQLException {
        return (getMFactory().getCallableStatement(passthru.prepareCall(p0), this, p0));
    }

    public CallableStatement prepareCall(String p0, int p1, int p2) throws SQLException {
        return (getMFactory().getCallableStatement(passthru.prepareCall(p0, p1, p2), this, p0));
    }

    public String nativeSQL(String p0) throws SQLException {
        return (passthru.nativeSQL(p0));
    }

    public void setAutoCommit(boolean p0) throws SQLException {
        passthru.setAutoCommit(p0);
    }

    public boolean getAutoCommit() throws SQLException {
        return (passthru.getAutoCommit());
    }

    public void commit() throws SQLException {
        passthru.commit();
    }

    public void rollback() throws SQLException {
        passthru.rollback();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return getMFactory().getDatabaseMetaData(passthru.getMetaData(), this);
    }

    public void setCatalog(String p0) throws SQLException {
        passthru.setCatalog(p0);
    }

    public String getCatalog() throws SQLException {
        return (passthru.getCatalog());
    }

    public void setTransactionIsolation(int p0) throws SQLException {
        passthru.setTransactionIsolation(p0);
    }

    public int getTransactionIsolation() throws SQLException {
        return (passthru.getTransactionIsolation());
    }

    public SQLWarning getWarnings() throws SQLException {
        return (passthru.getWarnings());
    }

    public void clearWarnings() throws SQLException {
        passthru.clearWarnings();
    }


    // Since JDK 1.4
    public void setHoldability(int p0) throws SQLException {
        passthru.setHoldability(p0);
    }

    // Since JDK 1.4
    public int getHoldability() throws SQLException {
        return (passthru.getHoldability());
    }

    // Since JDK 1.4
    public Savepoint setSavepoint() throws SQLException {
        return (passthru.setSavepoint());
    }

    // Since JDK 1.4
    public Savepoint setSavepoint(String p0) throws SQLException {
        return (passthru.setSavepoint(p0));
    }

    // Since JDK 1.4
    public void rollback(Savepoint p0) throws SQLException {
        passthru.rollback(p0);
    }

    // Since JDK 1.4
    public void releaseSavepoint(Savepoint p0) throws SQLException {
        passthru.releaseSavepoint(p0);
    }

    // Since JDK 1.4
    public Statement createStatement(int p0, int p1, int p2) throws SQLException {
        return getMFactory().getStatement(passthru.createStatement(p0, p1, p2), this);
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, int p1, int p2, int p3) throws SQLException {
        return (getMFactory().getPreparedStatement(passthru.prepareStatement(p0, p1, p2, p3), this, p0));
    }

    // Since JDK 1.4
    public CallableStatement prepareCall(String p0, int p1, int p2, int p3) throws SQLException {
        return (getMFactory().getCallableStatement(passthru.prepareCall(p0, p1, p2, p3), this, p0));
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, int p1) throws SQLException {
        return (getMFactory().getPreparedStatement(passthru.prepareStatement(p0, p1), this, p0));
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, int p1[]) throws SQLException {
        return (getMFactory().getPreparedStatement(passthru.prepareStatement(p0, p1), this, p0));
    }

    // Since JDK 1.4
    public PreparedStatement prepareStatement(String p0, String p1[]) throws SQLException {
        return (getMFactory().getPreparedStatement(passthru.prepareStatement(p0, p1), this, p0));
    }

    public Connection getJDBC() {
        Connection wrapped = (passthru instanceof MConnection) ?
                ((MConnection) passthru).getJDBC() :
                passthru;

        return wrapped;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return passthru.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return passthru.isWrapperFor(iface);
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        passthru.setTypeMap(map);
    }

    @Override
    public Clob createClob() throws SQLException {
        return passthru.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return passthru.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return passthru.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return passthru.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return passthru.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value)
            throws SQLClientInfoException {
        passthru.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties)
            throws SQLClientInfoException {
        passthru.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return passthru.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return passthru.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements)
            throws SQLException {
        return passthru.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes)
            throws SQLException {
        return passthru.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        //TODO by kevin
    }

    @Override
    public String getSchema() throws SQLException {
        // TODO by kevin
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        // TODO by kevin
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

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return passthru.getTypeMap();
    }

}
