package org.leber.log.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public final class LoggerManagerAgent {

    public static void run() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("org.leber.slf4jloggerext:name=Logger");
            if (!server.isRegistered(name)) {
                server.registerMBean(LoggerManager.getInstance(), name);
            }
        } catch (MalformedObjectNameException | MBeanRegistrationException | NotCompliantMBeanException | InstanceAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
