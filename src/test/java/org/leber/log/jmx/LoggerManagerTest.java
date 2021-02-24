package org.leber.log.jmx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.leber.log.LoggerFactory;
import org.leber.log.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LoggerManagerTest {
private static final Logger LOGGER = Logger.getLogger(LoggerManagerTest.class);
@BeforeAll
    public static  void init(){
    Logger.setBuffering(true);
    Logger.setBufferFlushSignal("trace5");
    Logger.setBufferOutLevel(Logger.I_LEVEL_INFO);
}
    @Test
    public void checkImpl(){
        String loggerName = LOGGER.getClass().getName();
    //    Assertions.assertEquals("",loggerName);
        for (int i=0;i<1000;i++){
            try {
                System.out.println("sleep");
                LOGGER.audit("audit"+i);
                LOGGER.error("error"+i);
                LOGGER.info("info"+i);
                LOGGER.warn("warn"+i);
                LOGGER.debug("debug"+i);
                LOGGER.trace("trace"+i);
                LOGGER.performance("performance"+i);

                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}