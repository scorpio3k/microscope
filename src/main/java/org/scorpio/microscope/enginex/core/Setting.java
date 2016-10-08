package org.scorpio.microscope.enginex.core;

public class Setting {
    private static boolean isEnableLog = true;
    private static Boolean isCheckConnLeaks = null;

    public static boolean isEnableLog() {
        return isEnableLog;
    }

    public static void enableLog() {
        Setting.isEnableLog = true;
    }

    public static void disableLog() {
        Setting.isEnableLog = false;
    }

    public static boolean isCheckConnLeaks() {
        if (isCheckConnLeaks == null) {
            String s = (String) System.getProperties().get("isCheckConnLeaks");
            isCheckConnLeaks = new Boolean("true".equalsIgnoreCase(s));
        }
        return isCheckConnLeaks.booleanValue();
    }

    public static void enableConnLeaks() {
        System.getProperties().put("isCheckConnLeaks", "true");
        isCheckConnLeaks = Boolean.TRUE;
    }

    public static void disableConnLeaks() {
        System.getProperties().put("isCheckConnLeaks", "false");
        isCheckConnLeaks = Boolean.FALSE;
    }
}
