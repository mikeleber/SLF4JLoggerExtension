package org.leber.log.jmx;

import java.io.IOException;

public interface LoggerManagerMBean {
    String getRootLogLevel() throws IOException;

    void setRootLogLevel(String level) throws IOException;

    void setBufferFlushSignal(String filter) throws IOException;

    void setBuffering(boolean buffer) throws IOException;

    void setBufferCapacity(int bufferCapacity);

    int getBufferCapacity();

    void setMdcEntryBufferFilter(String marker) throws IOException;

    void setBufferOutLevel(int level) throws IOException;

    String getBufferFlushSignal() throws IOException;

    boolean getBuffering() throws IOException;

    String getMdcEntryBufferFilter() throws IOException;

    int getBufferOutLevel() throws IOException;

    /**
     * Returns the current Log-level mapping.
     *
     * @throws IOException
     */
    String getLevelMapping() throws IOException;

    /**
     * With this method you can set a Log-level mapping. This means you can route a log entry from the original level like debug
     * to another target level like info. The syntax is debug2info. To change more as one mapping separate the entries with ",".
     * debug2info,trace2info
     *
     * @param mapping
     * @throws IOException
     */
    void setLevelMapping(String mapping) throws IOException;

    String getFilter() throws IOException;

    void setFilter(String filter) throws IOException;
}
