package org.scorpio.microscope;

import org.scorpio.microscope.enginex.common.MLogApi;
import org.scorpio.microscope.enginex.common.MLogGuiImpl;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.core.MDriverCore;


public class MDriver extends MDriverCore {
    private static MLogApi log = new MLogGuiImpl();

    static {
        MLogQuery.addP6Log(log);
        MDriverCore.init();
    }

}
