package org.scorpio.microscope.enginex.util;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.scorpio.microscope.enginex.common.MLogQuery;

public class ServerInfo {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * Empty parameter signature for reflective calls
     */
    private static final Class[] NO_PARAMS_SIG = new Class[0];

    /**
     * Empty paramater list for reflective calls
     */
    private static final Object[] NO_PARAMS = new Object[0];

    private static final String BR = "\r\n";

    /**
     * The cached host name for the server.
     */
    private static String hostName;

    /**
     * The cached host address for the server.
     */
    private static String hostAddress;

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    public static String getJavaHome() {
        return System.getProperty("java.home");
    }

    public static String getJavaVMName() {
        return System.getProperty("java.vm.name");
    }

    public static String getJavaVMVersion() {
        return System.getProperty("java.vm.version");
    }

    public static String getJavaVMVendor() {
        return System.getProperty("java.vm.vendor");
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static String getOSArch() {
        return System.getProperty("os.arch");
    }

    public static Long getTotalMemory() {
        return new Long(Runtime.getRuntime().totalMemory());
    }

    public static Long getFreeMemory() {
        return new Long(Runtime.getRuntime().freeMemory());
    }

    /**
     * Returns <tt>Runtime.getRuntime().maxMemory()<tt> on
     * JDK 1.4 vms or -1 on previous versions.
     */
    public static Long getMaxMemory() {
        try {
            Runtime rt = Runtime.getRuntime();
            Method m = rt.getClass().getMethod("maxMemory", NO_PARAMS_SIG);
            Long r = (Long) m.invoke(rt, NO_PARAMS);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Long(-1);
    }

    /**
     * Returns <tt>Runtime.getRuntime().availableProcessors()</tt> on JDK 1.4
     * vms or -1 on previous versions.
     */
    public static Integer getAvailableProcessors() {
        // Uncomment when JDK 1.4 is the base JVM
        // return new Integer(Runtime.getRuntime().availableProcessors());

        // until then use reflection to do the job
        try {
            Runtime rt = Runtime.getRuntime();
            Method m = rt.getClass().getMethod("availableProcessors",
                    NO_PARAMS_SIG);
            return (Integer) m.invoke(rt, NO_PARAMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Integer(-1);
    }

    /**
     * Returns InetAddress.getLocalHost().getHostName();
     */
    public static String getHostName() {
        if (hostName == null) {
            try {
                hostName = java.net.InetAddress.getLocalHost().getHostName();
            } catch (java.net.UnknownHostException e) {
                hostName = "<unknown>";
            }
        }

        return hostName;
    }

    /**
     * Returns InetAddress.getLocalHost().getHostAddress();
     */
    public static String getHostAddress() {
        if (hostAddress == null) {
            try {
                hostAddress = java.net.InetAddress.getLocalHost()
                        .getHostAddress();
            } catch (java.net.UnknownHostException e) {
                hostAddress = "<unknown>";
            }
        }

        return hostAddress;
    }

    public static Integer getActiveThreadCount() {
        return new Integer(getRootThreadGroup().activeCount());
    }

    public static Integer getActiveThreadGroupCount() {
        return new Integer(getRootThreadGroup().activeGroupCount());
    }

    /*
     * Traverse to the root thread group
     */
    private static ThreadGroup getRootThreadGroup() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group.getParent() != null) {
            group = group.getParent();
        }
        return group;
    }

    public static String buildReport() {
        StringBuffer sb = new StringBuffer();
        sb.append("ͳ��ʱ��: ").append(dateFormat.format(new Date())).append(BR);
        sb.append("����ϵͳ��").append(getOSName()).append(" ").append(getOSArch())
                .append(" ").append(ServerInfo.getOSVersion()).append(BR);
        sb.append("��ǰʹ��JAVA_HOME: ").append(getJavaHome()).append(BR);
        sb.append("JAVA���������(java.vm.name): ").append(getJavaVMName()).append(
                BR);
        sb.append("JAVA�������Ӧ��(java.vm.vendor): ").append(getJavaVMVendor())
                .append(BR);
        sb.append("JAVA������汾(java.vm.version): ").append(getJavaVMVersion())
                .append(BR);
        sb.append("JAVA�汾(java.version): ").append(getJavaVersion()).append(BR);
        sb.append("JAVA�汾(java.vendor): ").append(getJavaVendor()).append(BR);
        sb.append("��ǰռ���ڴ�����: ").append(
                getTotalMemory().longValue() / (1024 * 1024)).append("MB")
                .append(BR);
        sb.append("��ǰռ���ڴ�ʣ��: ").append(
                getFreeMemory().longValue() / (1024 * 1024)).append("MB")
                .append(BR);
        sb.append("����ռ�õ��ڴ�: ").append(
                getMaxMemory().longValue() / (1024 * 1024)).append("MB")
                .append(BR);
        sb.append("��ǰ��߳�������: ").append(getActiveThreadGroupCount()).append(BR);
        sb.append("��ǰ��߳�����: ").append(getActiveThreadCount()).append(BR);
        sb.append("CPU�ں���: ").append(getAvailableProcessors()).append(BR);
        sb.append("��ǰ����IP��ַ: ").append(getHostAddress()).append(BR);
        sb.append("��ǰ��������: ").append(getHostName()).append(BR);
        sb.append("��ǰ����·��: ").append(
                parseJarFile(ServerInfo.class.getResource(getClassName(ServerInfo.class)))).append(BR);
        sb.append("��ǰע���SQL������������ࣺ").append(BR);
        List list = MLogQuery.getLogs();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getClass()).append(BR);
        }
        return sb.toString();
    }

    public static String getClassName(Class clazz) {
        String packageName = clazz.getPackage().getName();
        String className = clazz.getName();
        return className.substring(packageName.length() + 1) + ".class";
    }

    public static String parseJarFile(URL path) {
        String file = path == null ? "" : path.getFile();
        try {
            int idx = file.lastIndexOf("jar!");
            if (idx == -1)
                return file;
            file = file.substring(0, idx + 3);
            return file.replaceFirst("file:/", "");
        } catch (Exception e) {
            return file;
        }
    }

    public static void main(String[] args) {
        System.out.println(ServerInfo.buildReport());
    }

}
