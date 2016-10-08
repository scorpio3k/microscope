package org.scorpio.microscope.enginex.util;

public class P6DebugLogger {
    private static MFileWriter p6ThreadLogImpl;

    public P6DebugLogger(String dir) {
        try {
            p6ThreadLogImpl = new MFileWriter();
            p6ThreadLogImpl.setLogDir(dir);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public P6DebugLogger() {
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
            p6ThreadLogImpl.log("P6 DEBUG START!" + mark);
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
            p6ThreadLogImpl.log("P6 DEBUG STOP!" + mark);
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
