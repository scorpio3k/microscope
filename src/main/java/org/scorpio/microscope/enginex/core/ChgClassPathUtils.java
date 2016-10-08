package org.scorpio.microscope.enginex.core;

import java.io.File;
import java.io.IOException;

import org.scorpio.microscope.enginex.util.IOUtil;

public class ChgClassPathUtils {
    private static File getStartFile(String path) {
        if (path.startsWith("jar:")) {
            path = path.substring(4);
        }
        if (path.startsWith("file:")) {
            path = path.substring(5);
        }
        String fileExtName = "";
        if (isWindows()) {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            fileExtName = ".cmd";
        } else {
            if (path.startsWith("//")) {
                path = path.substring(1);
            }
            fileExtName = ".sh";
        }
        File libDir = new File(path);
        if (!(libDir.isDirectory() && libDir.exists())) {
            return null;
        }
        File domainDir = libDir.getParentFile();
        File startFile = new File(domainDir.getAbsoluteFile() + File.separator
                + "bin" + File.separator + "startWebLogic" + fileExtName);
        if (!(startFile.isFile() && startFile.exists())) {
            return null;
        }
        return startFile;
    }

    private static String getJarName(String path) {
        int idx = path.toLowerCase().indexOf(".jar");
        return path.substring(0, idx + 4);
    }

    public static void modifyClassPath() {
        try {
            if (!"16".equals(MVersion.getDriverVersion())) {
                return;
            }
            String path = ChgClassPathUtils.class.getResource(
                    "ChgClassPathUtils.class").getPath();
            int idx = path.toLowerCase().indexOf(
                    "/a-simple-p6spy" + MVersion.getDriverVersion());
            String tempPath1 = null, tempPath2 = null;
            if (idx > 0) {
                tempPath1 = path.substring(0, idx);
                tempPath2 = path.substring(idx + 1);
            }
            File startFile = getStartFile(tempPath1);
            String jarName = getJarName(tempPath2);
            if (startFile != null && jarName != null) {
                chgStartFile(startFile, jarName);
            }
        } catch (Throwable e) {
            System.out.println("加入classpath失败：" + e.getMessage());
        }
    }

    private static void chgStartFile(File file, String jarName)
            throws IOException {
        String prefixName = getPrefixName();
        String oldText = IOUtil.readFileToString(file, getFileEncoding());
        String startMark = prefixName + "START WEBLOGIC";
        String endMark = "P6-END";
        String text = oldText;
        int sidx = text.toLowerCase().indexOf(startMark.toLowerCase());
        int eidx = text.toLowerCase().lastIndexOf(endMark.toLowerCase());
        if (sidx >= 0 && eidx >= 0) {
            text = text.substring(0, sidx + startMark.length()).trim() + "\r\n"
                    + text.substring(eidx + endMark.length()).trim();
        }
        if (sidx > 0) {
            bakFile(file);
            String temp = "\r\n" + prefixName + "P6-START\r\n";
            temp += prefixName + "该段代码为P6自动修改该文件加入的，非手工加入，请保留格式，包括空格和标点符号。\r\n";
            temp += "SET CLASSPATH=%DOMAIN_HOME%" + File.separator + "lib"
                    + File.separator + jarName + ";%CLASSPATH%\r\n";
            temp += prefixName + endMark + "\r\n";
            String newText = text.substring(0, sidx + startMark.length())
                    .trim()
                    + temp
                    + text.substring(sidx + startMark.length()).trim();
            if (!newText.equals(oldText)) {
                IOUtil.writeStringToFile(file, newText, getFileEncoding());
                System.out.println("将" + jarName + "加入到" + file
                        + "文件的classpath变量中");
            }
        }
    }

    public static boolean isWindows() {
        boolean flag = false;
        if (System.getProperties().getProperty("os.name").toUpperCase()
                .indexOf("WINDOW") != -1) {
            flag = true;
        }
        return flag;
    }

    private static void bakFile(File file) throws IOException {
        File bFile = new File(file.getAbsolutePath() + ".P6Bak");
        if (!bFile.exists()) {
            System.out.println("备份:" + bFile.getAbsolutePath());
            IOUtil.copyFile(file, bFile, false);
        }
    }

    public static String getPrefixName() {
        return isWindows() ? "@REM " : "# ";
    }


    public static String getFileEncoding() {
        return isWindows() ? "GBK" : "UTF-8";
    }
}
