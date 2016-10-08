package org.scorpio.microscope.enginex.common;

import java.sql.Driver;

public class MLogSystemOutImpl extends MLogApi {
    public void log(String f, String s) {
        System.out.println(formatSQL(f, s));
    }

    public void logDriverInfo(String realUrl, Driver passthru,
                              java.util.Properties p1) {
        String user = p1.getProperty("user");
        String password = p1.getProperty("password");
        String path = parseJarFile(passthru.getClass().getResource(
                getClassName(passthru.getClass())));
        String msg = "  URL:" + realUrl + "  �û�:" + user + "  ����:******  �����ļ�·��:" + path + "\r\n";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(msg);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
