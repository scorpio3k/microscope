package org.scorpio.microscope.enginex.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.scorpio.microscope.enginex.common.MThreadAIP;

/**
 * <p>
 * Title:p6 Ĭ�ϵ��̸߳����ࡣ
 * </p>
 * <p>
 * <p>
 * Description:�����Լ����ñ��������࣬���ڸ߼�Ӧ�ã�û����֮ǰ�򲻿��޸ġ�
 * </p>
 * <p>
 * <p>
 * Company: �����ź��ǿƼ��ɷ����޹�˾
 * </p>
 *
 * @author ���ڼ�
 * @since��2012-11-23 ����11:30:48
 */
public class MFileWriter implements MThreadAIP {
    private DateFormat fmt = new SimpleDateFormat("yyMMdd-HHmmss");
    private DecimalFormat ddf = new DecimalFormat("00000");
    private PrintWriter writer;
    private File logFile = null;
    private File logDir = null;
    private String logName = null;
    private int maxfileSize = 10240000;
    private int lineCount;

    public void setLogDir(String logDir)
            throws IOException {
        setLogFilePath(logDir, logName);
    }

    public void setLogFilePath(String logDir, String logName)
            throws IOException {
        if ((this.logDir != null && !this.logDir.equals(logDir))
                || this.logName != null && !this.logName.equals(logName)) {
            this.logDir = new File(logDir);
            this.logName = logName;
            createLogFile();
        } else {
            this.logDir = new File(logDir);
            this.logName = logName;
        }
    }

    public void writeLine(String msg) throws IOException {
        if (lineCount > 1000) {// �������Ч��ÿ1000�е�ʱ���ж��Ƿ���Ҫ�½��ļ���������ÿ�ζ��жϡ�
            lineCount = 0;
            logFile.lastModified();// ǿ��JVM�����ļ���Ϣ����Ҫ���ļ�������Ϣ.
            if (logFile.length() > maxfileSize) {
                createLogFile();
            }
        } else if (logFile == null) {
            createLogFile();
        }
        lineCount++;
        if (msg.startsWith("@>")) {
            writer.println(msg.trim());
        } else {
            writer.println(createPrefix() + msg.trim());
        }
    }

    private String createPrefix() {
        String p = fmt.format(new Date());
        return "@>" + p + " INFO SYSTEM S [" + getCurrentThreadName() + "] ";
    }

    private String getCurrentThreadName() {
        String s = Thread.currentThread().getName();
        // if(s.indexOf("[")>=0){
        // s=s.replaceAll("[", "_");
        // s=s.replaceAll("]", "_");
        // }
        return s;
    }

    public void createLogFile() throws IOException {
        if (this.logDir == null || this.logName == null) {
            throw new IOException("��־Ŀ¼Ϊ�ջ���־�ļ���Ϊ��.");
        }
        if (!this.logDir.exists()) {
            this.logDir.mkdir();
        }
        File newFile = null;
        try {
            int fileNum = getMaxFileNum();
            newFile = new File(logDir, logName + ddf.format(fileNum));
            if (newFile.exists()) {
                newFile.lastModified();
                if (newFile.length() > maxfileSize) {
                    newFile = new File(logDir, logName
                            + ddf.format(fileNum + 1));
                }
            }
        } catch (Throwable e) {
            newFile = new File(logDir, logName + System.currentTimeMillis());
        }
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        PrintWriter oldWriter = this.writer;
        boolean isNeedClose = false;
        try {
            this.writer = new PrintWriter(new FileWriter(newFile, true), true);
            isNeedClose = newFile.equals(this.logFile);
            this.logFile = newFile;
        } catch (Throwable e) {
            // ����ط����ܵ���printStackTrace��System.out����System.err��ֻ�����������쳣�����߲����κδ���.
            this.writer = oldWriter;
        }
        try {
            if (isNeedClose)
                oldWriter.close();
        } catch (Throwable e) {
            // ����ط����ܵ���printStackTrace��System.out����System.err��ֻ�����������쳣�����߲����κδ���.
        }
    }

    private int getMaxFileNum() {
        File[] files = this.logDir.listFiles();
        int maxNum = 0;
        for (int i = 0, size = files.length; i < size; i++) {
            String fileName = files[i].getName();
            if (!fileName.startsWith(logName)
                    || fileName.length() != logName.length() + 5) {
                continue;
            }
            try {
                String tmp = fileName.substring(logName.length());
                if (tmp.length() > 0) {
                    int num = new Integer(tmp).intValue();
                    if (num > maxNum) {
                        maxNum = num;
                    }
                }
            } catch (NumberFormatException e) {
                // ����ط����ܵ���printStackTrace��System.out����System.err
                continue;
            }
        }
        return maxNum;
    }

    public void log(String s) {
        try {
            writeLine(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        MFileWriter rfw = new MFileWriter();
        rfw.setLogFilePath("C:/TEMP", "test1");
        rfw.writeLine("1");
        rfw.writeLine("2");
        rfw.writeLine("3");
        rfw.setLogFilePath("C:/TEMP", "test2");
        rfw.writeLine("A");
        rfw.writeLine("B");
        rfw.writeLine("C");
        rfw.setLogFilePath("C:/TEMP", "test1");
        rfw.writeLine("4");
        rfw.writeLine("5");
        rfw.writeLine("6");
        rfw.setLogFilePath("C:/TEMP", "test2");
        rfw.writeLine("D");
        rfw.writeLine("E");
        rfw.writeLine("F");
    }

}
