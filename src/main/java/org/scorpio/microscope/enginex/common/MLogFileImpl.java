package org.scorpio.microscope.enginex.common;

import java.io.File;
import java.sql.Driver;

import org.scorpio.microscope.enginex.util.MFileWriter;

public class MLogFileImpl extends MLogApi {
    private MFileWriter m = null;
    private File fileDir = null;

    public MLogFileImpl() {
        this.fileDir = new File("C:/");
        if (!fileDir.exists()) {
            fileDir = new File("/usr/");
        }
        if (!fileDir.exists()) {
            fileDir = new File(".");
        }
        System.out.println("日志文件位置： " + fileDir);
        m = new MFileWriter();
        try {
            m.setLogFilePath(this.fileDir.getAbsolutePath(), "microscope");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MLogFileImpl(File dir) {
        this.fileDir = dir;
        m = new MFileWriter();
        try {
            m.setLogFilePath(this.fileDir.getAbsolutePath(), "microscope");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void log(String f, String s) {
        m.log(formatSQL(f, s));
    }

    public void logDriverInfo(String realUrl, Driver passthru,
                              java.util.Properties p1) {
        String user = p1.getProperty("user");
        String password = p1.getProperty("password");
        String path = parseJarFile(passthru.getClass().getResource(
                getClassName(passthru.getClass())));
        String msg = "  URL:" + realUrl + " 用户：" + user + "  密码：******  驱动文件路径：" + path + "\r\n";
        m.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        m.log(msg);
        m.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public int hashCode() {
        return this.fileDir.hashCode();
    }
}
