package ch.ralscha.e4ds.service;

public class Poll {
	private String date;
	private double processCpuLoad;
	private double systemCpuLoad;

	public Poll(final String date, final double processCpuLoad, final double systemCpuLoad) {
		this.date = date;
		this.processCpuLoad = processCpuLoad;
		this.systemCpuLoad = systemCpuLoad;
	}

	public String getDate() {
		return date;
	}

	public double getProcessCpuLoad() {
		return processCpuLoad;
	}

	public double getSystemCpuLoad() {
		return systemCpuLoad;
	}



}
