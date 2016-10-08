package org.scorpio.microscope.enginex.core;


import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.scorpio.microscope.enginex.NstcDataSource;
import org.scorpio.microscope.enginex.logging.MLogFactory;
import org.scorpio.microscope.enginex.common.MLogQuery;

public class MDriverCore implements Driver {
    /**
     * ��������������ʵ��
     */
    protected Driver passthru = null;

    /**
     * ��ʼ������ִ����ϵı�ʶ
     */
    protected static boolean initialized = false;

    /**
     * ��ű�P6�йܵ����ݿ���������
     */
    protected static ArrayList realDrivers = new ArrayList();

    protected static Map p6dsMap = new HashMap();

    /**
     * ��֤��ʼ���������̰߳�ȫ
     *
     * @param driverNames
     */
    public synchronized static void init() {
        if (initialized)// ��ʶ��ʼ������ִֻ��һ��
            return;
        deregisterDefaultDriver();
        List driverNames = loadDriverNames();//�������ļ�������ʵ������������
        if (driverNames == null || driverNames.size() == 0)
            return;
        String className = null;
        try {
            for (int i = 0; i < driverNames.size(); i++) {
                MDriverCore spy = new MDriverCore();// ����һ��P6����ʵ��
                DriverManager.registerDriver(spy);// ע��P6����ʵ��
                className = (String) driverNames.get(i);
                deregister(className);// ������������������p6ǰע������,���з�ע��
                try {
                    Driver realDriver = (Driver) forName(className)
                            .newInstance();// ��������������
                    spy.setPassthru(realDriver);// ������������ע�뵽P6������,��ΪP6���������һ������
                    realDrivers.add(realDriver);// ��������������ӵ�P6����ʵ�������������
                    MLogQuery.logInfo("Found Driver " + className);
                } catch (ClassNotFoundException e) {
                    DriverManager.deregisterDriver(spy);// �Ҳ���������������ʱ,��ע���P6����ʵ��,��Ϊ���ʵ�������д��ڵļ�ֵ��.
                    continue;
                }
            }
            printRegisteredDrivers();// ����Ѿ�ע���������Ϣ������̨
            ChgClassPathUtils.modifyClassPath();
        } catch (Exception e) {
            String err = "P6SPY Error registering  [" + className + "]\nCaused By: "
                    + e.toString();
            MLogQuery.logError(err);
            throw new Error(err);
        } finally {
            initialized = true;
        }
    }

    /**
     * ��ӡ��ǰ�Ѿ�ע�������
     */
    private static void printRegisteredDrivers() {
        for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements(); ) {
            Object dr = e.nextElement();
            String msg = dr.toString();
            if (dr instanceof MDriverCore) {
                msg = "(Proxy)"
                        + ((MDriverCore) dr).getPassthru().getClass()
                        .getName().toString();
                MLogQuery.logDebug("DriverManager registered:" + msg);
            } else {
                MLogQuery.logDebug("DriverManager registered:" + msg);
            }
        }
        String str = "\n�����ܣߨq�q�q�q�q�ߣ��� ��";
        str += "\n�������������������������ܣ��� ";
        str += "\n�������񡡡��������񡡩���� ";
        str += "\n�����𡡨t�ЩЩШs���𩦣����� ";
        str += "\n�����������t���s�������� ��";
        str += "\n  �t���С𡪡������С�s";
        str += "\n�����q���������������r";
        str += "\n�����t�ء����������بs";
        System.out.println(str);
    }

    /**
     * ʹ�õ�ǰ�̵߳������������Ŀ����,���ʧ��ʹ�õ�ǰ��������������
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    static Class forName(String name) throws ClassNotFoundException {
        ClassLoader ctxLoader = null;
        try {
            ctxLoader = Thread.currentThread().getContextClassLoader();
            return Class.forName(name, true, ctxLoader);

        } catch (ClassNotFoundException ex) {
            System.out.println("����: ClassNotFound " + name);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return Class.forName(name);
    }

    /**
     * ��������p6ǰע����������з�ע��
     *
     * @param className
     * @throws SQLException
     */
    static void deregister(String className) throws SQLException {
        ArrayList dereg = new ArrayList();
        for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements(); ) {
            Driver driver = (Driver) e.nextElement();
            if (driver instanceof MDriverCore) {
                continue;
            }
            // now you have to be careful of concurrent update
            // exceptions here, so save the drivers for later
            // deregistration
            if (driver.getClass().getName().equals(className)) {
                dereg.add(driver);
            }
        }

        // if you found any drivers let's dereg them now
        int size = dereg.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Driver driver = (Driver) dereg.get(i);
                DriverManager.deregisterDriver(driver);
                System.out.println("����: DeregisterDriver " + className);
            }
        }
    }

    /**
     * ������������Կ������ӱ�������װ��
     *
     * @param realConnection
     * @return
     * @throws SQLException
     */
    public static Connection wrapConnection(Connection realConnection)
            throws SQLException {
        if (realConnection instanceof MConnection) {
            return realConnection;
        } else {
            return new MLogFactory().getConnection(realConnection);
        }
    }

    private Driver getPassthru() {
        return passthru;
    }

    private void setPassthru(Driver inVar) {
        passthru = inVar;
    }

    // the remaining methods are for the Driver interface
    public Connection connect(String realUrl, java.util.Properties p1)
            throws SQLException {
        if (realUrl == null) {
            throw new SQLException("Database URL Is Null");
        }
        findPassthru(realUrl);
        if (passthru == null) // �����������Ҳ���ƥ�������
            throw new SQLException("Unable to find a driver that accepts "
                    + realUrl);
        Connection conn = passthru.connect(realUrl, p1);
        if (conn != null)
            conn = wrapConnection(conn);
        String p6dsKey = realUrl + p1.getProperty("user");
        if (!p6dsMap.containsKey(p6dsKey)) {
            NstcDataSource nds = null;
            try {
                nds = new NstcDataSource();
                nds.setDriverClass(this.getClass().getName());
                nds.setDriver(this);
                nds.setUrl(realUrl);
                nds.setUser(p1.getProperty("user"));
                nds.setPassword(p1.getProperty("password"));
                String dbn = p1.getProperty("DatabaseName");
                if (dbn == null || "".equals(dbn)) {
                    dbn = p1.getProperty("databaseName");
                }
                if (dbn == null || "".equals(dbn)) {
                    dbn = getDbNameFromUrl(realUrl);
                }
                String jndiName = "P6DS_" + dbn + "_" + nds.getUser();
                new javax.naming.InitialContext().bind(jndiName, nds);
                System.out.println("P6 JDBCα���ӳ�ע�ᵽJNDI:" + jndiName + ",�����ڼ������й¶.");
            } catch (Throwable e) {
                e.printStackTrace();
            }
            p6dsMap.put(p6dsKey, nds);
        }
        MLogQuery.logDriverInfo(realUrl, passthru, p1);
        return conn;
    }

    private static String getDbNameFromUrl(String url) {
        try {
            int idx = url.lastIndexOf("/");
            String dbName = null;
            if (idx > 0) {
                dbName = url.substring(idx + 1);
            } else {
                idx = url.lastIndexOf(":");
                if (idx > 0) {
                    dbName = url.substring(idx + 1);
                }
            }
            if (dbName != null) {
                dbName = dbName.replaceAll("\\.", "");
                return dbName;
            }
            return "";
        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * ����ʵ�ʣգң�Ѱ����ʵ����,passthru����Ϊ��MDriver�������ʵ����ʵ��
     *
     * @param url
     */
    private void findPassthru(String url) {
        try {
            if (passthru != null && passthru.acceptsURL(url)) {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Iterator i = realDrivers.iterator();
        while (i.hasNext()) {
            Driver driver = (Driver) i.next();
            try {
                if (driver.acceptsURL(url)) {
                    passthru = driver;
                    MLogQuery.logDebug("found new driver "
                            + driver.getClass().getName());
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �жϸ������Ƿ�ƥ��URL,��DriverManager.getDriver(url)�е��ô˷�����
     */
    public boolean acceptsURL(String realUrl) throws SQLException {
        boolean accepts = false;
        // somehow we get initilized but no driver is created,
        // lets try findPassthru
        if (passthru == null && initialized) {
            if (realDrivers.size() == 0) {
                throw new SQLException("P6 has no drivers registered");
            } else {
                findPassthru(realUrl);
                // if we are still null, we have issues
                if (passthru == null)
                    throw new SQLException(
                            "P6 can't find a driver to accept url ("
                                    + realUrl
                                    + ") from the "
                                    + realDrivers.size()
                                    + " drivers P6 knows about. The current driver is null");
            }
        }

        if (realUrl != null) {
            accepts = passthru.acceptsURL(realUrl);
        }
        return accepts;
    }

    public DriverPropertyInfo[] getPropertyInfo(String p0,
                                                java.util.Properties p1) throws SQLException {
        return (passthru.getPropertyInfo(p0, p1));
    }

    public int getMajorVersion() {
        return (passthru.getMajorVersion());
    }

    public int getMinorVersion() {
        return (passthru.getMinorVersion());
    }

    public boolean jdbcCompliant() {
        return (passthru.jdbcCompliant());
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        // TODO by kevin
        return null;
    }

    /**
     * �������ļ�������ʵ������������
     *
     * @return
     */
    private static List loadDriverNames() {
        List driverNames = new ArrayList();
        try {
            ResourceBundle resources = ResourceBundle.getBundle("core-drivers");
            Enumeration keys = resources.getKeys();
            while (keys.hasMoreElements()) {
                driverNames.add(resources.getString(keys.nextElement()
                        .toString().trim()));
            }
            return driverNames;
        } catch (Throwable e) {
            e.printStackTrace();
            driverNames.add("oracle.jdbc.driver.OracleDriver");
            driverNames.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            driverNames.add("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        }
        return null;
    }

    /**
     * ��ע���OJDBC6����ʱ���Զ�ע���������
     */
    private static void deregisterDefaultDriver() {
        for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements(); ) {
            try {
                DriverManager.deregisterDriver((Driver) e.nextElement());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
