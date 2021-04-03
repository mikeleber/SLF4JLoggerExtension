package org.leber.log;

import org.apache.commons.lang3.StringUtils;
import org.leber.log.jmx.LoggerManagerAgent;
import org.slf4j.ILoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LoggerFactory implements ILoggerFactory {

    private final ConcurrentMap<String, org.slf4j.Logger> loggerMap;
    org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Logger.class);

    private LoggerFactory() {
        loggerMap = new ConcurrentHashMap<>();
        initialize();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static LoggerFactory getInstance() {
        return InstanceHolder.instance;
    }

    public static org.slf4j.Logger getLogger(Class clazz) {
        String name = clazz.getName();
        return getInstance().getLogger(clazz.getName());
    }

    private void initialize() {
        String logMapping = System.getenv("org.modelui.log.mapping");
        if (StringUtils.isEmpty(logMapping)) {
            logMapping = System.getProperty("org.modelui.log.mapping");
        }
        try {
            if (!StringUtils.isEmpty(logMapping)) {
                Logger.setLogLevelMapping(logMapping);
            }
        } catch (Exception e) {
            LOGGER.error("can't init looger mapping from environment {}", logMapping);
        }

        LoggerManagerAgent.run();
    }

    @Override
    public synchronized org.slf4j.Logger getLogger(String name) {
        org.slf4j.Logger logger = loggerMap.get(name);
        if (logger != null) {
            return logger;
        } else {
            org.slf4j.Logger newInstance = new Logger(org.slf4j.LoggerFactory.getLogger(name));
            org.slf4j.Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }

    private static class InstanceHolder {
        /**
         * The constant instance.
         */
        public static final LoggerFactory instance = new LoggerFactory();
    }
}

