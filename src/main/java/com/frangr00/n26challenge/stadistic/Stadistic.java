package com.frangr00.n26challenge.stadistic;

import java.util.DoubleSummaryStatistics;

public class Stadistic {

	private double sum;
	private double average;
	private double max;
	private double min;
	private long count;

	public Stadistic(double sum, double average, double max, double min, long count) {
		super();
		this.sum = sum;
		this.average = average;
		this.max = max;
		this.min = min;
		this.count = count;
	}

	public static Stadistic of(DoubleSummaryStatistics sumary) {
		return new Stadistic(sumary.getSum(), sumary.getAverage(), sumary.getMax(), sumary.getMin(), sumary.getCount());
	}

	public double getSum() {
		return sum;
	}

	public double getAverage() {
		return average;
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}

	public long getCount() {
		return count;
	}

}
