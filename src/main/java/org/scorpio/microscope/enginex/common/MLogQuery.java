package org.scorpio.microscope.enginex.common;

import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.scorpio.microscope.enginex.core.Setting;

public class MLogQuery {
    private static List logs = new ArrayList();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd-HHmmss.SSS");

    static public void logElapsed(int connectionId, long startTime,
                                  String category, String prepared, String sql) {
        try {
            if (!Setting.isEnableLog()) {
                return;
            }
            long endTime = System.currentTimeMillis();
            String t = startTime == 0 ? "?" : ("" + (endTime - startTime));
            String f = "/*" + dateFormat.format(new Date(endTime)) + " |" + Thread.currentThread().getName() + "| ID=" + connectionId + ",MD="
                    + category + ",ET=" + t + "ms*/";
            log(f, sql);
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("Monitor SQL:" + e.getMessage());
        }
    }

    static private void log(String f, String s) {
        for (int i = 0; i < logs.size(); i++) {
            MLogApi log = (MLogApi) logs.get(i);
            if (log != null)
                log.log(f, s);
        }
    }

    static public void logInfo(String m) {
        System.err.println("Info: " + m);
    }

    static public void logError(String m) {
        System.err.println("Warning: " + m);
    }

    static public void logDebug(String m) {
        System.err.println("Debug: " + m);
    }

    static public void logDriverInfo(String realUrl, Driver passthru,
                                     java.util.Properties p1) {
        for (int i = 0; i < logs.size(); i++) {
            MLogApi log = (MLogApi) logs.get(i);
            if (log != null)
                log.logDriverInfo(realUrl, passthru, p1);
        }
    }

    static public void addP6Log(MLogApi log) {
        if (!logs.contains(log)) {
            logs.add(log);
        }
    }

    static public void removeP6Log(MLogApi log) {
        logs.remove(log);
    }

    public static List getLogs() {
        return logs;
    }

    public static void setLogs(List logs) {
        MLogQuery.logs = logs;
    }

}
