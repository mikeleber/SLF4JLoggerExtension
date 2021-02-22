package org.leber.log.jmx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.leber.log.LoggerFactory;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LoggerManagerTest {

    @Test
    public void checkImpl(){
        Logger LOG = LoggerFactory.getLogger(LoggerManager.class);
        String loggerName = LOG.getClass().getName();
    //    Assertions.assertEquals("",loggerName);
        for (int i=0;i<1000;i++){
            try {
                System.out.println("sleep");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}