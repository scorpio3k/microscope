package org.scorpio.microscope;

import org.scorpio.microscope.enginex.common.MLogApi;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.core.MDriverCore;

public class MMonitor extends MDriverCore {
    private static boolean hasLog = false;

    public static void init(MLogApi log) {
        if (!hasLog) {
            MLogQuery.addP6Log(log);
            hasLog = true;
        }
    }

    static {
        MDriverCore.init();
    }
}
