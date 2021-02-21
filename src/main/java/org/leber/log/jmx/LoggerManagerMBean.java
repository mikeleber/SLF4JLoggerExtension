package org.leber.log.jmx;

import java.io.IOException;

public interface LoggerManagerMBean {
    String getRootLogLevel() throws IOException;

    void setRootLogLevel(String level) throws IOException;

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
