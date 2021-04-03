package org.leber.log.jmx;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.leber.log.Logger;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

class LoggerManagerTest {
private static final Logger LOGGER = Logger.getLogger(LoggerManagerTest.class);
@BeforeAll
    public static  void init(){
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel","info");
    Logger.setBuffering(true);
    Logger.setBufferFlushSignal("3 5");
    Logger.setMdcEntryBufferFilter("cid");
    Logger.setMaxBufferOutLevel(Logger.I_LEVEL_INFO);

}
    @Test
    public void checkImpl(){
        String loggerName = LOGGER.getClass().getName();
    //    Assertions.assertEquals("",loggerName);
        Runnable target;
        while(true){
     // new Thread(()->createLogs(1)).start();
     // new Thread(()->createLogs(3)).start();
        createLogs(2);}

    }

    private void createLogs(int ti) {
    Map mdc = new HashMap();
    mdc.put("cid",String.valueOf(ti));
        MDC.setContextMap(mdc);
        for (int i=0;i<10000;i++){
       try {
           LOGGER.trace(ti+" trace"+ i);
           LOGGER.performance(ti+" performance"+ i);
           LOGGER.audit(ti+" audit"+ i);
           LOGGER.debug(ti+" debug"+ i);
           LOGGER.info(ti+" info"+ i);
           LOGGER.warn(ti+" warn"+ i);
           LOGGER.error(ti+" error"+ i);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }
}