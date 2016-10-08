package org.scorpio.microscope;

import org.scorpio.microscope.enginex.common.MLogApi;
import org.scorpio.microscope.enginex.common.MLog4jImpl;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.core.MDriverCore;

public class MLog4J extends MDriverCore {
    private static MLogApi log = new MLog4jImpl();

    static {
        MLogQuery.addP6Log(log);
        MDriverCore.init();
    }
}
