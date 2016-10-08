package org.scorpio.microscope.enginex.common;

import java.net.URL;
import java.sql.Driver;

import org.scorpio.microscope.enginex.util.Formatter;

public abstract class MLogApi {
    public abstract void log(String f, String s);

    public abstract void logDriverInfo(String realUrl, Driver passthru,
                                       java.util.Properties p1);

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

    public String formatSQL(String f, String sql) {
        sql = formatSQL(sql);
        return f + "\r\n" + sql + "\r\n";
    }

    public String formatSQL(String sql) {
        return new Formatter(sql).format();
    }

    public String simpleFormatSQL(String sql) {
        return sql.replaceAll("\t\t", " ").trim();
    }
}
