package org.scorpio.microscope.enginex;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

/**
 * ���ݿ�����й¶��⹤��
 *
 * @author sunyujia
 */
public class ConnLeaksCheckUtils {
    /**
     * ��һ���̻߳�ȡ�����ݿ��������������ֵʱ������Ԥ����
     */
    private static int CHECK_SIZE = 40;
    private static ThreadLocal resources = new ThreadLocal();

    public static void addConn2Thread(Connection conn) {
        if (getConnectionsSet().size() == CHECK_SIZE) {
            System.out.println("P6SPY��鵽��ǰ�̻߳�ȡ���ݿ����ӹ���(" + CHECK_SIZE
                    + ")���ó�����ܴ�������ȱ��,�߳�:" + Thread.currentThread().getName());
        }
        getConnectionsSet().add(conn);
        getStackTraceList().add(new RuntimeException());
    }

    public static String getStackTrace() {
        String s = "";
        List list = getStackTraceList();
        for (int i = 0; i < list.size(); i++) {
            Exception re = (Exception) list.get(i);
            s += stackTraceToString(re) + "\r\n";
        }
        return s;
    }

    private static String stackTraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        try {
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    public static void removeConnOnThread(Connection conn) {
        getConnectionsSet().remove(conn);
    }

    private static List getStackTraceList() {
        Map m = getThreadMap();
        List s = (List) m.get("StackTraceList");
        if (s == null) {
            s = new ArrayList();
            m.put("StackTraceList", s);
        }
        return s;
    }

    private static Set getConnectionsSet() {
        Map m = getThreadMap();
        Set s = (Set) m.get("Connections");
        if (s == null) {
            s = new HashSet();
            m.put("Connections", s);
        }
        return s;
    }

    private static Map getThreadMap() {
        Map s = (Map) resources.get();
        if (s == null) {
            s = new HashMap();
            resources.set(s);
        }
        return s;
    }

    public static int getConnSizeOfCurrentThread() {
        return getConnectionsSet().size();
    }

    public static void clearConnOnThread() {
        getConnectionsSet().clear();
    }

    /**
     * �жϵ�ǰ�߳��Ƿ�������й¶
     *
     * @return
     */
    public static boolean isConnLeaks() {
        if (getConnectionsSet().size() >= CHECK_SIZE) {
            System.out.println("P6SPY��鵽��ǰ�̻߳�ȡ���ݿ����ӹ���("
                    + getConnectionsSet().size() + ")���ó�����ܴ�������ȱ��,�߳�:"
                    + Thread.currentThread().getName());
        }
        Iterator iterator = getConnectionsSet().iterator();
        boolean isConnLeaks = false;
        while (iterator.hasNext()) {
            Connection conn = (Connection) iterator.next();
            try {
                if (!conn.isClosed()) {
                    isConnLeaks = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isConnLeaks;
    }

    /**
     * �жϵ�ǰ�߳��Ƿ�������й¶,�������ǿ�йر�����
     *
     * @return
     */
    public static void closeConnOnThread() {
        try {
            Connection[] cons = (Connection[]) getConnectionsSet().toArray(
                    new Connection[0]);
            for (int i = 0; i < cons.length; i++) {
                Connection conn = (Connection) cons[i];
                try {
                    if (!conn.isClosed()) {
                        conn.close();
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            clearConnOnThread();
        }
    }
}
