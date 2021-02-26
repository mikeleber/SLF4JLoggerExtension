package org.leber.log;

import org.apache.commons.lang3.StringUtils;

import org.leber.log.list.RollingArray;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Logger implements org.slf4j.Logger {
    public static final String LEVEL_NONE = "none";
    public static final String LEVEL_AUDIT = "audit";
    public static final String LEVEL_ERROR = "error";
    public static final String LEVEL_INFO = "info";
    public static final String LEVEL_WARN = "warn";
    public static final String LEVEL_DEBUG = "debug";
    public static final String LEVEL_TRACE = "trace";
    public static final String LEVEL_PERFORMANCE = "performance";
    public static final int I_LEVEL_NONE = 0;
    public static final int I_LEVEL_AUDIT = 2;
    public static final int I_LEVEL_INFO = 3;
    public static final int I_LEVEL_WARN = 4;
    public static final int I_LEVEL_DEBUG = 5;
    public static final int I_LEVEL_TRACE = 6;
    public static final int I_LEVEL_PERFORMANCE = 7;
    public static final int I_LEVEL_ERROR = 1;
    public static  int BUFFER_CAPACITY = 20;
    public static final int MTHD_IDX = 0;
    public static final int THIS_IDX = 1;
    public static final int LVL_IDX = 2;
    public static final int MDC_IDX = 3;
    public static final int MSG_IDX = 4;
    public static final int FRMT_IDX = 4;
    public static final int ARG_IDX = 5;
    public static final int THRD_IDX = 5;
    public static Pattern logFilter;
    public static String logFilterString;
    public static Pattern bufferFlushSignal;
    public static String bufferFlushSignalString;
    private static int[] logLevelMapping = createIntSequence(0, 8, THIS_IDX);
    private static boolean globalInfoEnabled = true;
    private static boolean globalErrorEnabled = true;
    private static boolean globalDebugEnabled = true;
    private static boolean globalTraceEnabled = true;
    private static boolean globalPerformanceEnabled = true;
    private static boolean globalWarnEnabled = true;
    private static boolean globalAuditEnabled = true;
    private static RollingArray<Object[]> bufferedLogs = null;
    private final org.slf4j.Logger delegate;
    private boolean infoEnabled = true;
    private boolean errorEnabled = true;
    private boolean debugEnabled = true;
    private boolean traceEnabled = true;
    private boolean performanceEnabled = true;
    private boolean warnEnabled = true;
    private boolean auditEnabled = true;
    private static boolean useBuffering = false;
    public static String bufferGroupMarker;
    public static int bufferOutLevel = I_LEVEL_PERFORMANCE;
    public Logger(org.slf4j.Logger logger) {
        delegate = logger;
    }

    public static Logger getLogger(Class clazz) {
        return (Logger) LoggerFactory.getInstance().getLogger(clazz.getName());
    }

    public static boolean isGlobalInfoEnabled() {
        return globalInfoEnabled;
    }

    public static void setGlobalInfoEnabled(boolean enabled) {
        globalInfoEnabled = enabled;
    }

    public static boolean isGlobalErrorEnabled() {
        return globalErrorEnabled;
    }

    public static void setGlobalErrorEnabled(boolean enabled) {
        globalErrorEnabled = enabled;
    }

    public static boolean isGlobalDebugEnabled() {
        return globalDebugEnabled;
    }

    public static void setGlobalDebugEnabled(boolean enabled) {
        globalDebugEnabled = enabled;
    }

    public static boolean isGlobalTraceEnabled() {
        return globalTraceEnabled;
    }

    public static void setGlobalTraceEnabled(boolean enabled) {
        globalTraceEnabled = enabled;
    }

    public static boolean isGlobalPerformanceEnabled() {
        return globalPerformanceEnabled;
    }

    public static void setGlobalPerformanceEnabled(boolean enabled) {
        globalPerformanceEnabled = enabled;
    }

    public static boolean isGlobalWarnEnabled() {
        return globalWarnEnabled;
    }

    public static void setGlobalWarnEnabled(boolean enabled) {
        globalWarnEnabled = enabled;
    }

    public static boolean isGlobalAuditEnabled() {
        return globalAuditEnabled;
    }

    public static void setGlobalAuditEnabled(boolean enabled) {
        globalAuditEnabled = enabled;
    }
    public static int getBufferOutLevel() {
        return bufferOutLevel;
    }

    public static void setBufferOutLevel(int level) {
        bufferOutLevel = level;
    }

    public static String getBufferGroupMarker() {
        return bufferGroupMarker;
    }

    public static void setBufferGroupMarker(String marker) {
        bufferGroupMarker = marker;
    }
    public static String getGlobalLevels() {
        List<String> levels = new ArrayList<>();
        if (isGlobalInfoEnabled()) {
            levels.add(LEVEL_INFO);
        }
        if (isGlobalErrorEnabled()) {
            levels.add(LEVEL_ERROR);
        }
        if (isGlobalDebugEnabled()) {
            levels.add(LEVEL_DEBUG);
        }
        if (isGlobalTraceEnabled()) {
            levels.add(LEVEL_TRACE);
        }
        if (isGlobalWarnEnabled()) {
            levels.add(LEVEL_WARN);
        }
        if (isGlobalAuditEnabled()) {
            levels.add(LEVEL_AUDIT);
        }

        return StringUtils.join(levels, ",");
    }

    public static void setGlobalLogLevels(String levels) {

        if (levels != null) {
            String[] levelsSplit = levels.split(",");
            for (String aLevel : levelsSplit) {
                switch (aLevel.trim().toLowerCase()) {
                    case LEVEL_AUDIT:
                        Logger.setGlobalAuditEnabled(true);
                        break;
                    case LEVEL_ERROR:
                        Logger.setGlobalErrorEnabled(true);
                        break;
                    case LEVEL_INFO:
                        Logger.setGlobalInfoEnabled(true);
                        break;
                    case LEVEL_WARN:
                        Logger.setGlobalWarnEnabled(true);
                        break;
                    case LEVEL_DEBUG:
                        Logger.setGlobalDebugEnabled(true);
                        break;
                    case LEVEL_TRACE:
                        Logger.setGlobalTraceEnabled(true);
                        break;
                    case LEVEL_PERFORMANCE:
                        Logger.setGlobalPerformanceEnabled(true);
                        break;
                }
                if (!isStringInList(levelsSplit, LEVEL_AUDIT)) {
                    setGlobalAuditEnabled(false);
                }
                if (!isStringInList(levelsSplit, LEVEL_ERROR)) {
                    setGlobalErrorEnabled(false);
                }
                if (!isStringInList(levelsSplit, LEVEL_DEBUG)) {
                    setGlobalDebugEnabled(false);
                }
                if (!isStringInList(levelsSplit, LEVEL_INFO)) {
                    setGlobalInfoEnabled(false);
                }
                if (!isStringInList(levelsSplit, LEVEL_TRACE)) {
                    setGlobalTraceEnabled(false);
                }
                if (!isStringInList(levelsSplit, LEVEL_PERFORMANCE)) {
                    setGlobalPerformanceEnabled(false);
                }
                if (!isStringInList(levelsSplit, LEVEL_WARN)) {
                    setGlobalWarnEnabled(false);
                }
            }
        }
    }

    private static int evaluateLevelFor(String loglevel) {
        switch (loglevel) {
            case LEVEL_AUDIT:
                return I_LEVEL_AUDIT;
            case LEVEL_ERROR:
                return I_LEVEL_ERROR;
            case LEVEL_INFO:
                return I_LEVEL_INFO;
            case LEVEL_WARN:
                return I_LEVEL_WARN;
            case LEVEL_DEBUG:
                return I_LEVEL_DEBUG;
            case LEVEL_TRACE:
                return I_LEVEL_TRACE;
            case LEVEL_PERFORMANCE:
                return I_LEVEL_PERFORMANCE;
            default:
                return I_LEVEL_NONE;
        }
    }

    private static String evaluateLevelFor(int loglevel) {
        switch (loglevel) {
            case I_LEVEL_AUDIT:
                return LEVEL_AUDIT;
            case I_LEVEL_ERROR:
                return LEVEL_ERROR;
            case I_LEVEL_INFO:
                return LEVEL_INFO;
            case I_LEVEL_WARN:
                return LEVEL_WARN;
            case I_LEVEL_DEBUG:
                return LEVEL_DEBUG;
            case I_LEVEL_TRACE:
                return LEVEL_TRACE;
            case I_LEVEL_PERFORMANCE:
                return LEVEL_PERFORMANCE;
            default:
                return LEVEL_NONE;
        }
    }

    public static boolean isStringInList(List<String> myList, String stringToFind) {
        return myList.stream().anyMatch(s -> s.equalsIgnoreCase(stringToFind));
    }

    public static boolean isStringInList(String[] myList, String stringToFind) {
        return isStringInList(Arrays.asList(myList), stringToFind);
    }

    public static boolean matchFilter(String format, Object... args) {
        if (format != null && logFilter != null) {
            if (args != null && args.length > 0) {
                format = MessageFormatter.arrayFormat(format, args).getMessage();
            }
            return logFilter.matcher(format).find();
        } else {
            return true;
        }
    }

    public static void handleFlushSignal(String format, Object... args) {
        if (format != null && bufferFlushSignal != null) {
            if (args != null && args.length > 0) {
                format = MessageFormatter.arrayFormat(format, args).getMessage();
            }
            if (bufferFlushSignal.matcher(format).find()) {
                System.out.println("*********************HandleFlush***************");
                bufferedLogs.traverseAsync(e -> performTriggeredLog(e),bufferedLogs.toArray());
                bufferedLogs.makeEmpty();
            }
        }
    }

    public static String getLogLevelMapping() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < logLevelMapping.length; i++) {
            int targetLevel = logLevelMapping[i];
            if (targetLevel > 0) {
                result.append(evaluateLevelFor(i));
                result.append("2");
                result.append(evaluateLevelFor(targetLevel));
                result.append(",");
            }
        }

        return result.toString().trim();
    }

    public static void setLogLevelMapping(String mapping) {
        if (mapping != null) {
            String[] levelsSplit = mapping.split(",");
            if (levelsSplit.length > 0) {
                for (String aLevel : levelsSplit) {
                    String[] mappingSplit = aLevel.split("2");
                    //check for right size!
                    if (mappingSplit.length == 2) {
                        int targetIDX = evaluateLevelFor(mappingSplit[THIS_IDX]);
                        if (StringUtils.equals(mappingSplit[0], "*")) {
                            //wildcard set all to target level
                            for (int i = 0; i < logLevelMapping.length; i++) {
                                int targetLevel = logLevelMapping[i];
                                logLevelMapping[i] = targetIDX;
                            }
                        } else {
                            int srcIDX = evaluateLevelFor(mappingSplit[0]);
                            if (srcIDX > 0) {
                                logLevelMapping[srcIDX] = targetIDX;
                            }
                        }
                    }
                }
            } else {
                //reset
                logLevelMapping = createIntSequence(0, 8, THIS_IDX);
            }
        }
    }

    public static Pattern getBufferFlushSignal() {
        return bufferFlushSignal;
    }

    public static void setBufferFlushSignal(Pattern signal) {
        bufferFlushSignal = signal;
    }

    public static String getBufferFlushSignalString() {
        return bufferFlushSignalString;
    }

    public static void setBufferFlushSignal(String signal) {
        bufferFlushSignalString=signal;
        if (signal != null) {
            Logger.bufferFlushSignal = Pattern.compile(bufferFlushSignalString);
        } else {
            Logger.bufferFlushSignal = null;
        }
    }

    public static Pattern getLogFilter() {
        return logFilter;
    }

    public static void setLogFilter(String logFilter) {
        if (logFilter != null) {
            Logger.logFilterString = logFilter;
            Logger.logFilter = Pattern.compile(logFilter);
        } else {
            Logger.logFilter = null;
        }
    }

    public static String getLogFilterString() {
        return logFilterString;
    }

    private static RollingArray<Object[]> getCircularList() {
        if (bufferedLogs == null) {
            bufferedLogs = new RollingArray<>(Object[].class, BUFFER_CAPACITY);
            for (int i = 0; i < bufferedLogs.getArray().length; i++) {
                bufferedLogs.getArray()[i] = new Object[7];
            }
        }
        return bufferedLogs;
    }

    public static void setBufferCapacity(int bufferCapacity) {
        BUFFER_CAPACITY = bufferCapacity;
        bufferedLogs=null;
    }

    public static int getBufferCapacity() {
        return BUFFER_CAPACITY;
    }

    private static Object[] getBufferEntry() {
        Object[] buffer;
        if (getCircularList().size() == 0) {
            buffer = new Object[7];
            getCircularList().push(buffer);
        } else {
            getCircularList().moveVorward();
            buffer = getCircularList().peek();
        }

        return buffer;
    }
public static void performTriggeredLog(Object[] entry) {
       // System.out.println(Arrays.toString(entry));
        int m = (int) entry[MTHD_IDX];
        int level = I_LEVEL_ERROR;//(int) entry[LVL_IDX];
        int realLevel = (int) entry[LVL_IDX];
        if (realLevel>getBufferOutLevel()){
            return;
        }

        Map<String, String> mdcCopy = (Map) entry[MDC_IDX];
        MDC.setContextMap(mdcCopy);
        String realLevelTxt=evaluateLevelFor(realLevel);
        switch (m) {
            case THIS_IDX:
                ((Logger) entry[THIS_IDX]).dispatchLog1(level,  (String)entry[MSG_IDX], false);
                break;
            case 2:
                ((Logger) entry[THIS_IDX]).dispatchLog2(level, (String) entry[FRMT_IDX], false, (Object[]) entry[ARG_IDX]);
                break;
            case 3:
                ((Logger) entry[THIS_IDX]).dispatchLog3(level, (String) entry[MSG_IDX], entry[ARG_IDX], false);
                break;
            case 4:
                ((Logger) entry[THIS_IDX]).dispatchLog4(level, (String) entry[MSG_IDX], entry[4], entry[5], false);
                break;
            case 5:
                ((Logger) entry[THIS_IDX]).dispatchLog5(level, (String) entry[MSG_IDX], (Throwable) entry[THRD_IDX], false);
                break;
        }
    }

    public static void performTriggeredLog() {
        synchronized (getCircularList()){getCircularList().drainOut(a -> performTriggeredLog(a));}
    }

    public static boolean isBuffering() {return useBuffering;
    }

    public void logAction(ACTION action) {
        dispatchLog3(I_LEVEL_INFO, "{}", action.getAction(), useBuffering);
    }

    public void logAction(ACTION action, String parameters) {
        dispatchLog4(I_LEVEL_INFO, "{} {}", action.getAction(), parameters, useBuffering);
    }

    public void logAction(String viewName, ACTION action) {
        dispatchLog4(I_LEVEL_INFO, "{} view_name={}", action.getAction(), viewName, useBuffering);
    }

    public void logAction(String viewName, ACTION action, String parameters) {
        dispatchLog2(I_LEVEL_INFO, "{} view_name={} {}", useBuffering, action.getAction(), viewName, parameters);
    }

    @Override
    public boolean isTraceEnabled() {
        return globalTraceEnabled && traceEnabled;
    }

    public void setTraceEnabled(boolean traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    public boolean isPerformanceEnabled() {
        return globalPerformanceEnabled && performanceEnabled;
    }

    public void setPerformanceEnabled(boolean enabled) {
        performanceEnabled = enabled;
    }

    @Override
    public void trace(String msg) {
        dispatchLog1(I_LEVEL_TRACE, msg, useBuffering);
    }

    @Override
    public void trace(String format, Object arg) {
        dispatchLog3(I_LEVEL_TRACE, format, arg, useBuffering);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        dispatchLog4(I_LEVEL_TRACE, format, arg1, arg2, useBuffering);
    }

    @Override
    public void trace(String format, Object... arguments) {

        dispatchLog2(I_LEVEL_TRACE, format, useBuffering, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        dispatchLog5(I_LEVEL_TRACE, msg, t, useBuffering);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return delegate.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {
        delegate.trace(marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        delegate.trace(marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        delegate.trace(marker, format, arg1, arg2);
    }
    private void updateBuffer(int mthdIdx,Object logger,Map mdc, int level,  String msgOrFormat,Object... arg5) {
        synchronized (getCircularList()){
        Object[] current = getBufferEntry();
        current[MTHD_IDX] = Integer.valueOf(mthdIdx);
        current[THIS_IDX] = logger;
        current[MDC_IDX] = mdc;
        current[LVL_IDX] = Integer.valueOf(level);
        current[4] = msgOrFormat;
        current[5] = arg5;

        handleFlushSignal(msgOrFormat,arg5);}
    }
    private void dispatchLog1(int level, String msg, boolean useBuffer) {
        if (useBuffer) {
            updateBuffer(1,this,MDC.getCopyOfContextMap(),level,msg,null);

        }

        switch (evaluateTargetLevel(level)) {
            case I_LEVEL_AUDIT:
                delegate.info(msg);
                break;
            case I_LEVEL_ERROR:
                delegate.error(msg);
                break;
            case I_LEVEL_INFO:
                if (matchFilter(msg)) {
                    delegate.info(msg);
                }
                break;
            case I_LEVEL_WARN:
                delegate.warn(msg);
                break;
            case I_LEVEL_DEBUG:
                if (matchFilter(msg)) {
                    delegate.debug(msg);
                }
                break;
            case I_LEVEL_TRACE:
                if (matchFilter(msg)) {
                    delegate.trace(msg);
                }
                break;
            case I_LEVEL_PERFORMANCE:
                if (matchFilter(msg)) {
                    delegate.info(msg);
                }
                break;
            case I_LEVEL_NONE:
                //forget about this
                break;
        }
    }

    private void dispatchLog3(int level, String msg, Object arg, boolean useBuffer) {
        if (useBuffer) {
            updateBuffer(3,this,MDC.getCopyOfContextMap(),level,msg,arg);

        }

        switch (evaluateTargetLevel(level)) {
            case I_LEVEL_AUDIT:
                delegate.info(msg, arg);
                break;
            case I_LEVEL_ERROR:
                delegate.error(msg, arg);
                break;
            case I_LEVEL_INFO:
                if (matchFilter(msg, arg)) {
                    delegate.info(msg, arg);
                }
                break;
            case I_LEVEL_WARN:
                delegate.warn(msg, arg);
                break;
            case I_LEVEL_DEBUG:
                if (matchFilter(msg, arg)) {
                    delegate.debug(msg, arg);
                }
                break;
            case I_LEVEL_TRACE:
                if (matchFilter(msg, arg)) {
                    delegate.trace(msg, arg);
                }
                break;
            case I_LEVEL_PERFORMANCE:
                if (matchFilter(msg, arg)) {
                    delegate.info(msg, arg);
                }
                break;
            case I_LEVEL_NONE:
                //forget about this
                break;
        }
    }

    private boolean isEnabled(int level) {
        switch (level) {
            case I_LEVEL_AUDIT:
                return isAuditEnabled();
            case I_LEVEL_ERROR:
                return isErrorEnabled();
            case I_LEVEL_INFO:
                return isInfoEnabled();
            case I_LEVEL_WARN:
                return isWarnEnabled();
            case I_LEVEL_DEBUG:
                return isDebugEnabled();
            case I_LEVEL_TRACE:
                return isTraceEnabled();
            case I_LEVEL_PERFORMANCE:
                return isPerformanceEnabled();
        }
        return true;
    }

    private int evaluateTargetLevel(int level) {
        boolean enabled = true;
        boolean delegateEnabled = true;
        int targetLevel = logLevelMapping[level];
        switch (targetLevel) {
            case I_LEVEL_AUDIT:
                enabled = isAuditEnabled();
                delegateEnabled = delegate.isInfoEnabled();
                break;
            case I_LEVEL_ERROR:
                enabled = isErrorEnabled();
                delegateEnabled = delegate.isErrorEnabled();
                break;
            case I_LEVEL_INFO:
                enabled = isInfoEnabled();
                delegateEnabled = delegate.isInfoEnabled();
                break;
            case I_LEVEL_WARN:
                enabled = isWarnEnabled();
                delegateEnabled = delegate.isWarnEnabled();
                break;
            case I_LEVEL_DEBUG:
                enabled = isDebugEnabled();
                delegateEnabled = delegate.isDebugEnabled();
                break;
            case I_LEVEL_TRACE:
                enabled = isTraceEnabled();
                delegateEnabled = delegate.isTraceEnabled();
                break;
            case I_LEVEL_PERFORMANCE:
                enabled = isPerformanceEnabled();
                delegateEnabled = delegate.isInfoEnabled();
                break;
            case I_LEVEL_NONE:
                //forget about this
                break;
        }
        if (enabled && delegateEnabled) {
            return targetLevel;
        } else if (enabled) {
            return level;
        } else {
            return I_LEVEL_NONE;
        }
    }

    private void dispatchLog4(int level, String msg, Object arg1, Object arg2, boolean useBuffer) {
        if (useBuffer) {
            updateBuffer(4,this,MDC.getCopyOfContextMap(),level,msg,arg1,arg2);

        }

        switch (evaluateTargetLevel(level)) {
            case I_LEVEL_AUDIT:
                delegate.info(msg, arg1, arg2);
                break;
            case I_LEVEL_ERROR:
                delegate.error(msg, arg1, arg2);
                break;
            case I_LEVEL_INFO:
                if (matchFilter(msg, arg1, arg2)) {
                    delegate.info(msg, arg1, arg2);
                }
                break;
            case I_LEVEL_WARN:
                delegate.warn(msg, arg1, arg2);
                break;
            case I_LEVEL_DEBUG:
                if (matchFilter(msg, arg1, arg2)) {
                    delegate.debug(msg, arg1, arg2);
                }
                break;
            case I_LEVEL_TRACE:
                if (matchFilter(msg, arg1, arg2)) {
                    delegate.trace(msg, arg1, arg2);
                }
                break;
            case I_LEVEL_PERFORMANCE:
                if (matchFilter(msg, arg1, arg2)) {
                    delegate.info(msg, arg1, arg2);
                }
                break;
            case I_LEVEL_NONE:
                //forget about this
                break;
        }
    }

    private void dispatchLog5(int level, String msg, Throwable t, boolean useBuffer) {
        if (useBuffer) {
            updateBuffer(5,this,MDC.getCopyOfContextMap(),level,msg,t);

        }
        switch (evaluateTargetLevel(level)) {
            case I_LEVEL_AUDIT:
                delegate.info(msg, t);
                break;
            case I_LEVEL_ERROR:
                delegate.error(msg, t);
                break;
            case I_LEVEL_INFO:
                if (matchFilter(msg)) {
                    delegate.info(msg, t);
                }
                break;
            case I_LEVEL_WARN:
                delegate.warn(msg, t);
                break;
            case I_LEVEL_DEBUG:
                if (matchFilter(msg)) {
                    delegate.debug(msg, t);
                }
                break;
            case I_LEVEL_TRACE:
                if (matchFilter(msg)) {
                    delegate.trace(msg, t);
                }
                break;
            case I_LEVEL_PERFORMANCE:
                if (matchFilter(msg)) {
                    delegate.info(msg, t);
                }
                break;
            case I_LEVEL_NONE:
                //forget about this
                break;
        }
    }

    private void dispatchLog2(int level, String format, boolean useBuffer, Object... arguments) {
        if (useBuffer) {
            updateBuffer(1,this,MDC.getCopyOfContextMap(),level,format,arguments);

        }
        switch (evaluateTargetLevel(level)) {
            case I_LEVEL_AUDIT:
                delegate.info(format, arguments);
                break;
            case I_LEVEL_ERROR:
                delegate.error(format, arguments);
                break;
            case I_LEVEL_INFO:
                if (matchFilter(format, arguments)) {
                    delegate.info(format, arguments);
                }
                break;
            case I_LEVEL_WARN:
                delegate.warn(format, arguments);
                break;
            case I_LEVEL_DEBUG:
                if (matchFilter(format, arguments)) {
                    delegate.debug(format, arguments);
                }
                break;
            case I_LEVEL_TRACE:
                if (matchFilter(format, arguments)) {
                    delegate.trace(format, arguments);
                }
                break;
            case I_LEVEL_PERFORMANCE:
                if (matchFilter(format, arguments)) {
                    delegate.info(format, arguments);
                }
                break;
            case I_LEVEL_NONE:
                //forget about this
                break;
        }
    }

    @Override
    public void trace(Marker marker, String format, Object... arguments) {
        delegate.trace(marker, format, arguments);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        delegate.trace(marker, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return globalDebugEnabled && debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    @Override
    public void debug(String msg) {
        dispatchLog1(I_LEVEL_DEBUG, msg, useBuffering);
    }

    @Override
    public void debug(String format, Object arg) {
        dispatchLog3(I_LEVEL_DEBUG, format, arg, useBuffering);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        dispatchLog4(I_LEVEL_DEBUG, format, arg1, arg2, useBuffering);
    }

    @Override
    public void debug(String format, Object... arguments) {
        dispatchLog2(I_LEVEL_DEBUG, format, useBuffering, arguments);
    }

    public static void setBuffering(boolean use) {
        useBuffering = use;
    }

    @Override
    public void debug(String msg, Throwable t) {

        dispatchLog5(I_LEVEL_DEBUG, msg, t, useBuffering);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return delegate.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {
        delegate.debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        delegate.debug(marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {

        delegate.debug(marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        delegate.debug(marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {

        delegate.debug(marker, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {

        return globalInfoEnabled && infoEnabled;
    }

    public void setInfoEnabled(boolean infoEnabled) {
        this.infoEnabled = infoEnabled;
    }

    @Override
    public void info(String msg) {
        dispatchLog1(I_LEVEL_INFO, msg, useBuffering);
    }

    @Override
    public void info(String format, Object arg) {
        dispatchLog3(I_LEVEL_INFO, format, arg, useBuffering);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        dispatchLog4(I_LEVEL_INFO, format, arg1, arg2, useBuffering);
    }

    @Override
    public void info(String format, Object... arguments) {
        dispatchLog2(I_LEVEL_INFO, format, useBuffering, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        dispatchLog5(I_LEVEL_INFO, msg, t, useBuffering);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return delegate.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {
        delegate.info(marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        delegate.info(marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        delegate.info(marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        delegate.info(marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        delegate.info(marker, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return globalWarnEnabled && warnEnabled;
    }

    public void setWarnEnabled(boolean warnEnabled) {
        this.warnEnabled = warnEnabled;
    }

    @Override
    public void warn(String msg) {
        dispatchLog1(I_LEVEL_WARN, msg, useBuffering);
    }

    @Override
    public void warn(String format, Object arg) {
        dispatchLog3(I_LEVEL_WARN, format, arg, useBuffering);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        dispatchLog4(I_LEVEL_WARN, format, arg1, arg2, useBuffering);
    }

    @Override
    public void warn(String format, Object... arguments) {
        dispatchLog2(I_LEVEL_WARN, format, useBuffering, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        dispatchLog5(I_LEVEL_WARN, msg, t, useBuffering);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return delegate.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {
        delegate.warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        delegate.warn(marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        delegate.warn(marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        delegate.warn(marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        delegate.warn(marker, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return globalErrorEnabled && errorEnabled;
    }

    public void setErrorEnabled(boolean errorEnabled) {
        this.errorEnabled = errorEnabled;
    }

    @Override
    public void error(String msg) {

        dispatchLog1(I_LEVEL_ERROR, msg, useBuffering);
    }

    @Override
    public void error(String format, Object arg) {
        dispatchLog3(I_LEVEL_ERROR, format, arg, useBuffering);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        dispatchLog4(I_LEVEL_ERROR, format, arg1, arg2, useBuffering);
    }

    @Override
    public void error(String format, Object... arguments) {
        dispatchLog2(I_LEVEL_ERROR, format, useBuffering, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        dispatchLog5(I_LEVEL_ERROR, msg, t, useBuffering);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return delegate.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {
        delegate.error(marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        delegate.error(marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        delegate.error(marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {

        delegate.error(marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        delegate.error(marker, msg, t);
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    public boolean isAuditEnabled() {
        return globalAuditEnabled && auditEnabled;
    }

    public void setAuditEnabled(boolean auditEnabled) {
        this.auditEnabled = auditEnabled;
    }

    public boolean isAuditEnabled(Marker marker) {
        return isAuditEnabled();
    }

    /**
     * Log a message at the AUDIT level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the AUDIT level. </p>
     *
     * @param msg the format string
     */
    public void audit(String msg) {
        dispatchLog1(I_LEVEL_AUDIT, msg, useBuffering);
    }

    /**
     * Log a message at the AUDIT level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the AUDIT level. </p>
     *
     * @param format the format string
     * @param arg    the first argument
     */
    public void audit(String format, Object arg) {
        dispatchLog2(I_LEVEL_AUDIT,format,  useBuffering,arg);
    }

    /**
     * Log a message at the AUDIT level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the AUDIT level. </p>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void audit(String format, Object arg1, Object arg2) {
        dispatchLog4(I_LEVEL_AUDIT, format, arg1, arg2, useBuffering);
    }

    /**
     * Log a message at the AUDIT level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the AUDIT level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for AUDIT. The variants taking {@link #audit(String, Object) one} and
     * {@link #trace(String, Object, Object) two} arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void performance(String format, Object... arguments) {
        dispatchLog2(I_LEVEL_PERFORMANCE, format, useBuffering, arguments);
    }
    public void performance(String message) {
        dispatchLog1(I_LEVEL_PERFORMANCE, message, useBuffering);
    }
    /**
     * Log a message at the AUDIT level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the AUDIT level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for AUDIT. The variants taking {@link #audit(String, Object) one} and
     * {@link #trace(String, Object, Object) two} arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void audit(String format, Object... arguments) {
        dispatchLog2(I_LEVEL_AUDIT, format, useBuffering, arguments);
    }

    public String getLevels() {
        StringBuilder result = new StringBuilder();
        if (isInfoEnabled()) {
            result.append(LEVEL_INFO + " ");
        }
        if (isErrorEnabled()) {
            result.append(LEVEL_ERROR + " ");
        }
        if (isDebugEnabled()) {
            result.append(LEVEL_DEBUG + " ");
        }
        if (isTraceEnabled()) {
            result.append(LEVEL_TRACE + " ");
        }
        if (isWarnEnabled()) {
            result.append(LEVEL_WARN + " ");
        }
        if (isAuditEnabled()) {
            result.append(LEVEL_AUDIT + " ");
        }
        if (isPerformanceEnabled()) {
            result.append(LEVEL_PERFORMANCE + " ");
        }
        return result.toString().trim();
    }

    public interface ACTION {
        String getAction();
    }
    /**
     * Create int sequence int [ ].
     *
     * @param start  the start
     * @param length the length
     * @param step   the step
     * @return the int [ ]
     */
    public static final int[] createIntSequence(int start, int length, int step) {
        int[] result = new int[length];
        int j = start;
        int end = j + length;
        int val = start;
        for (; j < end; j++) {
            result[j] = val;
            val += step;
        }
        return result;
    }
}
