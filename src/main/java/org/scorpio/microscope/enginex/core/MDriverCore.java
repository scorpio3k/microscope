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

import org.scorpio.microscope.enginex.MDataSource;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.logging.MLogFactory;

public class MDriverCore implements Driver {
    /**
     * 真正的驱动对象实例
     */
    protected Driver passthru = null;

    /**
     * 初始化工作执行完毕的标识
     */
    protected static boolean initialized = false;

    /**
     * 存放被P6托管的数据库驱动容器
     */
    protected static ArrayList realDrivers = new ArrayList();

    protected static Map p6dsMap=new HashMap();

    /**
     * 保证初始化工作的线程安全
     *
     */
    public synchronized static void init() {
        if (initialized)// 标识初始化工作只执行一次
            return;
        deregisterDefaultDriver();
        List driverNames=loadDriverNames();//从配置文件加载真实驱动的驱动名
        if (driverNames == null || driverNames.size() == 0)
            return;
        String className = null;
        try {
            for (int i = 0; i < driverNames.size(); i++) {
                MDriverCore spy = new MDriverCore();// 创建一个P6驱动实例
                DriverManager.registerDriver(spy);// 注册P6驱动实例
                className = (String) driverNames.get(i);
                deregister(className);// 处理真正的驱动抢在p6前注册的情况,进行反注册
                try {
                    Driver realDriver = (Driver) forName(className)
                            .newInstance();// 加载真正的驱动
                    spy.setPassthru(realDriver);// 将真正的驱动注入到P6驱动中,做为P6驱动对象的一个属性
                    realDrivers.add(realDriver);// 将真正的驱动添加到P6驱动实例共享的容器中
                    MLogQuery.logInfo("Found Driver " + className);
                } catch (ClassNotFoundException e) {
                    DriverManager.deregisterDriver(spy);// 找不到真正的驱动类时,反注册掉P6驱动实例,因为这个实例不具有存在的价值了.
                    continue;
                }
            }
            printRegisteredDrivers();// 输出已经注册的驱动信息到控制台
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
     * 打印当前已经注册的驱动
     */
    private static void printRegisteredDrivers() {
        for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();) {
            Object dr = e.nextElement();
            String msg = dr.toString();
            if (dr instanceof MDriverCore) {
                msg = "(Proxy)"
                        + ((MDriverCore) dr).getPassthru().getClass()
                        .getName().toString();
                MLogQuery.logDebug("DriverManager registered:" + msg);
            }else{
                MLogQuery.logDebug("DriverManager registered:" + msg);
            }
        }
        String str = "\n　│＼＿╭╭╭╭╭＿／│ 　";
        str += "\n　│　　　　　　　　　│＼｜／ ";
        str += "\n　│　●　　　　　●　│—☆— ";
        str += "\n　│○　╰┬┬┬╯　○│／｜＼ ";
        str += "\n　│　　　╰—╯　　　／ 　";
        str += "\n  ╰—┬○————┬○╯";
        str += "\n　　╭│　　　　　│╮";
        str += "\n　　╰┴—————┴╯";
        System.out.println(str);
    }

    /**
     * 使用当前线程的类加载器加载目标类,如果失败使用当前类的类加载器重试
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
            System.out.println("警告: ClassNotFound " + name);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return Class.forName(name);
    }

    /**
     * 查找抢在p6前注册的驱动进行反注册
     *
     * @param className
     * @throws SQLException
     */
    static void deregister(String className) throws SQLException {
        ArrayList dereg = new ArrayList();
        for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();) {
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
                System.out.println("警告: DeregisterDriver " + className);
            }
        }
    }

    /**
     * 从这里可以明显看到连接被工厂包装了
     *
     * @param realConnection
     * @return
     * @throws SQLException
     */
    public static Connection wrapConnection(Connection realConnection)
            throws SQLException {
        if(realConnection instanceof MConnection){
            return realConnection;
        }else{
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
        if (passthru == null) // 在驱动池中找不到匹配的驱动
            throw new SQLException("Unable to find a driver that accepts "
                    + realUrl);
        Connection conn = passthru.connect(realUrl, p1);
        if (conn != null)
            conn = wrapConnection(conn);
        String p6dsKey = realUrl + p1.getProperty("user");
        if (!p6dsMap.containsKey(p6dsKey)) {
            MDataSource nds = null;
            try {
                nds = new MDataSource();
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
                String jndiName = "P6DS_" + dbn +"_"+ nds.getUser();
                new javax.naming.InitialContext().bind(jndiName, nds);
                System.out.println("P6 JDBC伪连接池注册到JNDI:" + jndiName+",可用于检测连接泄露.");
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
     * 根据实际ＵＲＬ寻找真实驱动,passthru属性为该P6Driver代理的真实驱动实例
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
     * 判断该驱动是否匹配URL,在DriverManager.getDriver(url)中调用此方法。
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
     * 从配置文件加载真实驱动的驱动名
     * @return
     */
    private static List loadDriverNames() {
        List driverNames = new ArrayList();
        try {
            ResourceBundle resources = ResourceBundle.getBundle("spy-drivers");
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
     * 反注册掉OJDBC6启动时，自动注册的驱动。
     */
    private static void deregisterDefaultDriver(){
        for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();) {
            try {
                DriverManager.deregisterDriver((Driver)e.nextElement());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
