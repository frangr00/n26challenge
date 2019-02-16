package com.frangr00.n26challenge.statistic;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;

class PartialStatistic {
	private PartialDuration partialDuration;
	private DoubleSummaryStatistics summaryStatistics;

	public PartialStatistic(PartialDuration partialDuration) {
		summaryStatistics = new DoubleSummaryStatistics();
		this.partialDuration = partialDuration;
	}

	public PartialStatistic accept(PartialDuration partialDuration, double amount) {
		if (!this.partialDuration.isSameInstantSlot(partialDuration)) {
			this.partialDuration = partialDuration;
			summaryStatistics = new DoubleSummaryStatistics();
		}
		summaryStatistics.accept(amount);
		return this;
	}

	public DoubleSummaryStatistics getSummaryStatistics() {
		return summaryStatistics;
	}

	public boolean isObsoleteTime(Instant instant) {
		return partialDuration.isBiggerThan(instant);
	}
}