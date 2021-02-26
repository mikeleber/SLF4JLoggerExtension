# SLF4JLoggerExtension

This is a SLF4J extension providing a dynamic log level mapping to  make it possible to change the log level on the fly without the need of redeployement of the application. Moreover it provides a buffer, recording all log entries at every level, that can be pushed out triggered by a simple reqular expression. The level of the buffered output can be different as the current   log level. 
