package ch.ralscha.e4ds.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.POLL;

import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
@Lazy
public class PollService {

	private DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");

	@ExtDirectMethod(value = POLL, event = "chartdata")
	@PreAuthorize("isAuthenticated()")
	public Poll getPollData() throws MalformedObjectNameException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException {

		MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName oname = new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);
		double processCpuLoad = (double) mbeanServer.getAttribute(oname, "ProcessCpuLoad");
		double systemCpuLoad = (double) mbeanServer.getAttribute(oname, "SystemCpuLoad");
		long freePhysicalMemorySize = (long)mbeanServer.getAttribute(oname, "FreePhysicalMemorySize");
		long totalPhysicalMemorySize = (long)mbeanServer.getAttribute(oname, "TotalPhysicalMemorySize");
				
		if (processCpuLoad < 0) {
			processCpuLoad = 0;
		}

		long now = DateTime.now().getMillis();
		Poll p = new Poll(now, fmt.print(now));
		
		p.setProcessCpuLoad(processCpuLoad);
		p.setSystemCpuLoad(systemCpuLoad);
		p.setFreePhysicalMemorySize(freePhysicalMemorySize);
		p.setTotalPhysicalMemorySize(totalPhysicalMemorySize);
		
		return p;
	}
}
