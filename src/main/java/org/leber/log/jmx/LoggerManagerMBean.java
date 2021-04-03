package org.leber.log.jmx;

import java.io.IOException;

public interface LoggerManagerMBean {
    String getRootLogLevel();

    void setRootLogLevel(String level);

    int getBufferCapacity();

    void setBufferCapacity(int bufferCapacity);

    String getBufferFlushSignal();

    void setBufferFlushSignal(String filter);

    boolean getBuffering();

    void setBuffering(boolean buffer);

    String getMdcEntryBufferFilter();

    void setMdcEntryBufferFilter(String marker);

    int getMaxBufferOutLevel();

    void setMaxBufferOutLevel(int level);

    /**
     * Returns the current Log-level mapping.
     *
     */
    String getLevelMapping();

    /**
     * With this method you can set a Log-level mapping. This means you can route a log entry from the original level like debug
     * to another target level like info. The syntax is debug2info. To change more as one mapping separate the entries with ",".
     * debug2info,trace2info
     *
     * @param mapping
     */
    void setLevelMapping(String mapping);

    String getFilter();

    void setFilter(String filter);
}
