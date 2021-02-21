package org.slf4j.impl;

import org.leber.log.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder {

    private static StaticLoggerBinder instance = new StaticLoggerBinder();

    public static StaticLoggerBinder getSingleton() {
        return instance;
    }

    @Override
    public LoggerFactory getLoggerFactory() {
        return LoggerFactory.getInstance();
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return LoggerFactory.class.getName();
    }

}