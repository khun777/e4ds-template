package ch.ralscha.e4ds.service;

public class Poll {
	private long id;
	private String time;
	private double processCpuLoad;
	private double systemCpuLoad;
	private long freePhysicalMemorySize;
	private long totalPhysicalMemorySize;

	public Poll(final long id, final String time) {
		this.id = id;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public double getProcessCpuLoad() {
		return processCpuLoad;
	}

	public double getSystemCpuLoad() {
		return systemCpuLoad;
	}

	public long getId() {
		return id;
	}

	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public long getTotalPhysicalMemorySize() {
		return totalPhysicalMemorySize;
	}

	public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
		this.totalPhysicalMemorySize = totalPhysicalMemorySize;
	}

	public void setProcessCpuLoad(double processCpuLoad) {
		this.processCpuLoad = processCpuLoad;
	}

	public void setSystemCpuLoad(double systemCpuLoad) {
		this.systemCpuLoad = systemCpuLoad;
	}

}
