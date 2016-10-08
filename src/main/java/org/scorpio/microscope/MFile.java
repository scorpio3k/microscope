package org.scorpio.microscope;

import org.scorpio.microscope.enginex.common.MLogApi;
import org.scorpio.microscope.enginex.common.MLogFileImpl;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.core.MDriverCore;

public class MFile extends MDriverCore {
    private static MLogApi log = new MLogFileImpl();

    static {
        MLogQuery.addP6Log(log);
        MDriverCore.init();
    }
}

