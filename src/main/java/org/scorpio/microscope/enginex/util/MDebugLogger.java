package org.scorpio.microscope.enginex.util;

public class MDebugLogger {
    private static MFileWriter p6ThreadLogImpl;

    public MDebugLogger(String dir) {
        try {
            p6ThreadLogImpl = new MFileWriter();
            p6ThreadLogImpl.setLogDir(dir);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public MDebugLogger() {
        try {
            p6ThreadLogImpl = new MFileWriter();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void begin() {
        begin("");
    }

    public void begin(String mark) {
        try {
            p6ThreadLogImpl.log("microscope DEBUG START!" + mark);
            ThreadResources.bindResource("P6Thread", p6ThreadLogImpl);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void end() {
        end("");
    }

    public void end(String mark) {
        try {
            p6ThreadLogImpl.log("microscope DEBUG STOP!" + mark);
            ThreadResources.clear();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void log(String msg) {
        try {
            p6ThreadLogImpl.log(msg);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
