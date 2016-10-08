package org.scorpio.microscope.enginex.common;

import java.sql.Driver;
import java.util.Properties;

import org.scorpio.microscope.enginex.util.ThreadResources;

public class MThreadImp extends MLogApi {

    public void log(String f, String s) {
        MThreadAIP pt6 = (MThreadAIP) ThreadResources.getResource("MThread");
        if (pt6 != null) {
            pt6.log(formatSQL(f, s));
        }
    }

    public void logDriverInfo(String realUrl, Driver passthru, Properties p1) {

    }

}
