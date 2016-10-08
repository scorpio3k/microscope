package org.scorpio.microscope.enginex.common;

import java.sql.Driver;

import org.apache.log4j.Logger;


public class MLog4jImpl extends MLogApi {
    private static Logger logger = Logger.getLogger(MLog4jImpl.class);

    public void log(String f, String s) {
        logger.info(formatSQL(f, s));
    }

    public void logDriverInfo(String realUrl, Driver passthru, java.util.Properties p1) {
        String user = p1.getProperty("user");
        String password = p1.getProperty("password");
        String path = parseJarFile(passthru.getClass().getResource(
                getClassName(passthru.getClass())));
        String msg = "  URL:" + realUrl + "  用户：" + user + "  密码：*****  驱动文件路径:" + path + "\r\n";
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.debug(msg);
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
