package com.frangr00.n26challenge.stadistic;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;

class PartialStadistic {
	private PartialDuration partialDuration;
	private DoubleSummaryStatistics summaryStatistics;

	public PartialStadistic(PartialDuration partialDuration) {
		summaryStatistics = new DoubleSummaryStatistics();
		this.partialDuration = partialDuration;
	}

	public PartialStadistic accept(PartialDuration partialDuration, double amount) {
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