package org.leber.log.jmx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class LoggerManagerTest {

    @Test
    public void checkImpl(){
        Logger LOG =LoggerFactory.getLogger(LoggerManager.class);
        String loggerName = LOG.getClass().getName();
        Assertions.assertEquals("",loggerName);
    }


}