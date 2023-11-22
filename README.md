# SLF4JLoggerExtension

This is a SLF4J extension providing a dynamic log level mapping to  make it possible to change the log level on the fly without the need of redeployement of the application. Moreover it provides a buffer, recording all log entries at every level, that can be pushed out triggered by a simple reqular expression. The level of the buffered output can be different as the current   log level. 

#Usage
You can use the SLF4JLoggerExtension like the standard SLF4J Logger Factory. The only thing you have to do is to use the Logger and LoggerFactory from the org.leber.log package.



Logger.getLogger(LoggerManagerTest.class);
LoggerFactory.getLogger(LoggerManagerTest.class);

##General usage:


##Log Level mapping:
Logger.setLogLevelMapping("info2error",...)

##Buffer usage:

##Enable Buffering:
Logger.setBuffering(true);

##Define a Flush Signal:
Logger.setBufferFlushSignal("3 5");

##Define the Loglevel of the output buffer
Logger.setBufferOutLevel(Logger.I_LEVEL_INFO);

