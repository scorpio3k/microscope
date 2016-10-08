package org.scorpio.microscope;

import org.scorpio.microscope.enginex.common.MLogApi;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.common.MThreadImp;
import org.scorpio.microscope.enginex.core.MDriverCore;

public class MThread extends MDriverCore {
    private static MLogApi log = new MThreadImp();

    static {
        MLogQuery.addP6Log(log);
        MDriverCore.init();
    }
}
