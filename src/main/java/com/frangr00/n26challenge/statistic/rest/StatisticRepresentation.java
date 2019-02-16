package com.frangr00.n26challenge.statistic.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.frangr00.n26challenge.statistic.Statistic;

class StatisticRepresentation {

	private double sum;
	@JsonProperty(value = "avg")
	private double average;
	private double max;
	private double min;
	private long count;

	public StatisticRepresentation(Statistic statistics) {
		sum = statistics.getSum();
		average = statistics.getAverage();
		max = statistics.getMax();
		min = statistics.getMin();
		count = statistics.getCount();
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
