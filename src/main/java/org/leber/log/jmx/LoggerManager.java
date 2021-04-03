package org.leber.log.jmx;

import org.leber.log.Logger;

import java.io.IOException;

public class LoggerManager implements LoggerManagerMBean {

    public LoggerManager() {
        super();
    }

    @Override
    public String getRootLogLevel() throws IOException {
        return Logger.getGlobalLevels();
    }

    @Override
    public void setRootLogLevel(String levels) throws IOException {
        System.out.println("LoggerManger: " + levels);
        Logger.setGlobalLogLevels(levels);
    }

    @Override
    public String getFilter() throws IOException {
        return Logger.getLogFilterString();
    }

    @Override
    public void setFilter(String filter) throws IOException {
        Logger.setLogFilter(filter);
    }

    @Override
    public String getBufferFlushSignal() throws IOException {
        return Logger.getBufferFlushSignalString();
    }

    @Override
    public void setBufferFlushSignal(String filter) throws IOException {
        Logger.setBufferFlushSignal(filter);
    }

    @Override
    public boolean getBuffering() throws IOException {
        return Logger.isBuffering();
    }

    @Override
    public void setBuffering(boolean buffer) throws IOException {
        Logger.setBuffering(buffer);
    }

    @Override
    public String getMdcEntryBufferFilter() throws IOException {
        return Logger.getMdcEntryBufferFilter();
    }

    @Override
    public void setMdcEntryBufferFilter(String marker) throws IOException {
        Logger.setMdcEntryBufferFilter(marker);
    }

    @Override
    public int getBufferCapacity() {
        return Logger.getBufferCapacity();
    }

    @Override
    public void setBufferCapacity(int bufferCapacity) {
        Logger.setBufferCapacity(bufferCapacity);
    }

    @Override
    public int getMaxBufferOutLevel() throws IOException {
        return Logger.getMaxBufferOutLevel();
    }

    @Override
    public void setMaxBufferOutLevel(int level) throws IOException {
        Logger.setMaxBufferOutLevel(level);
    }

    /**
     * Returns the current Log-level mapping.
     *
     * @throws IOException
     */
    @Override
    public String getLevelMapping() throws IOException {
        return Logger.getLogLevelMapping();
    }

    /**
     * With this method you can set a Log-level mapping. This means you can route a log entry from the original level like debug
     * to another target level like info. The syntax is debug2info. To change more as one mapping separate the entries with ",".
     * debug2info,trace2info
     *
     * @param mapping
     * @throws IOException
     */
    @Override
    public void setLevelMapping(String mapping) throws IOException {
        Logger.setLogLevelMapping(mapping);
    }
}
