package org.leber.log.jmx;

import org.leber.log.Logger;

public class LoggerManager implements LoggerManagerMBean {
    private static  LoggerManager INSTANCE=new LoggerManager();

    public LoggerManager() {
        super();
    }

    public static LoggerManager getInstance() {
        return INSTANCE;
    }

    @Override
    public String getRootLogLevel()  {
        return Logger.getGlobalLevels();
    }

    @Override
    public void setRootLogLevel(String levels)   {
        System.out.println("LoggerManger: " + levels);
        Logger.setGlobalLogLevels(levels);
    }

    @Override
    public String getFilter()  {
        return Logger.getLogFilterString();
    }

    @Override
    public void setFilter(String filter)  {
        Logger.setLogFilter(filter);
    }

    @Override
    public String getBufferFlushSignal()  {
        return Logger.getBufferFlushSignalString();
    }

    @Override
    public void setBufferFlushSignal(String filter)  {
        Logger.setBufferFlushSignal(filter);
    }

    @Override
    public boolean getBuffering()  {
        return Logger.isBuffering();
    }

    @Override
    public void setBuffering(boolean buffer)  {
        Logger.setBuffering(buffer);
    }

    @Override
    public String getMdcEntryBufferFilter()  {
        return Logger.getMdcEntryBufferFilter();
    }

    @Override
    public void setMdcEntryBufferFilter(String marker)  {
        Logger.setMdcEntryBufferFilter(marker);
    }

    @Override
    public void setMinFlushTriggerLevel(int minLevel) {
        Logger.setMinFlushTriggerLevel(minLevel);
    }

    @Override
    public int getMinFlushTriggerLevel() {
        return Logger.getMinFlushTriggerLevel();
    }
    public int getBufferCapacity() {
        return Logger.getBufferCapacity();
    }

    @Override
    public void setBufferCapacity(int bufferCapacity) {
        Logger.setBufferCapacity(bufferCapacity);
    }

    @Override
    public int getMaxBufferOutLevel()  {
        return Logger.getMaxBufferOutLevel();
    }

    @Override
    public void setMaxBufferOutLevel(int level)  {
        Logger.setMaxBufferOutLevel(level);
    }

    /**
     * Returns the current Log-level mapping.
     *
    
     */
    @Override
    public String getLevelMapping()  {
        return Logger.getLogLevelMapping();
    }

    /**
     * With this method you can set a Log-level mapping. This means you can route a log entry from the original level like debug
     * to another target level like info. The syntax is debug2info. To change more as one mapping separate the entries with ",".
     * debug2info,trace2info
     *
    param mapping
    
     */
    @Override
    public void setLevelMapping(String mapping)  {
        Logger.setLogLevelMapping(mapping);
    }
}
