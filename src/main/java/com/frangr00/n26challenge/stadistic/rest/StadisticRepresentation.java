package com.frangr00.n26challenge.stadistic.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.frangr00.n26challenge.stadistic.Stadistic;

class StadisticRepresentation {

	private double sum;
	@JsonProperty(value = "avg")
	private double average;
	private double max;
	private double min;
	private long count;

	public StadisticRepresentation(Stadistic stadistics) {
		sum = stadistics.getSum();
		average = stadistics.getAverage();
		max = stadistics.getMax();
		min = stadistics.getMin();
		count = stadistics.getCount();
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
