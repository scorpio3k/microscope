package org.scorpio.microscope.enginex.common;

import java.sql.Driver;

import org.scorpio.microscope.enginex.GUI;

public class MLogGuiImpl extends MLogApi {
    public static GUI gui = new GUI();

    public void log(String f, String sql) {
        String fmSql = null;

        if (sql == null || "".equals(sql.trim())) {
            fmSql = "";
        } else if (sql.length() > 200) {
            fmSql = formatSQL(sql) + "\r\n";
        } else {
            fmSql = "\n" + simpleFormatSQL(sql) + "\r\n";
        }

        String log = "\n" + f + "\r\n" + fmSql;
        if (gui != null) {
            gui.appendAtTextArea(log, f + "\r\n" + sql.trim() + "\r\n");
        } else {
            System.out.print(log);
        }
    }

    public void logDriverInfo(String realUrl, Driver passthru,
                              java.util.Properties p1) {
        if (gui != null)
            gui.addDriverInfo(realUrl, passthru, p1);
    }

}
