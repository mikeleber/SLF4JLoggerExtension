package org.basetools.log.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public final class LoggerManagerAgent {

    public static void run() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("org.modelui:name=Logger");
            if (!server.isRegistered(name)) {
                server.registerMBean(new LoggerManager(), name);
            }
        } catch (MalformedObjectNameException | MBeanRegistrationException | NotCompliantMBeanException | InstanceAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
