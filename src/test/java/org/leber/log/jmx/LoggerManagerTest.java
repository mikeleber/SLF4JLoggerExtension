package org.leber.log.jmx;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.leber.log.Logger;

class LoggerManagerTest {
private static final Logger LOGGER = Logger.getLogger(LoggerManagerTest.class);
@BeforeAll
    public static  void init(){
    Logger.setBuffering(true);
    Logger.setBufferFlushSignal("3 5");
    Logger.setBufferOutLevel(Logger.I_LEVEL_INFO);
}
    @Test
    public void checkImpl(){
        String loggerName = LOGGER.getClass().getName();
    //    Assertions.assertEquals("",loggerName);
        Runnable target;
      new Thread(()->createLogs(1)).start();
      new Thread(()->createLogs(2)).start();
        createLogs(3);

    }

    private void createLogs(int ti) {
        for (int i=0;i<1000;i++){
        try {
//            LOGGER.audit(ti+" audit"+ i);
//            LOGGER.error(ti+" error"+ i);
//            LOGGER.info(ti+" info"+ i);
//            LOGGER.warn(ti+" warn"+ i);
//            LOGGER.debug(ti+" debug"+ i);
//            LOGGER.trace(ti+" trace"+ i);
//            LOGGER.performance(ti+" performance"+ i);

            LOGGER.error(ti+" "+ i);


            Thread.currentThread().sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }
}