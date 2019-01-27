package com.frangr00.n26challenge.stadistic;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class PartialDuration {

	private int second;
	private Instant instantTruncated;

	public PartialDuration(Instant instant) {
		LocalDateTime date =
			    LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		this.second = date.getSecond();
		this.instantTruncated = instant.truncatedTo(ChronoUnit.SECONDS);
	}

	public int getSecond() {
		return second;
	}
	
	public boolean isSameInstantSlot(PartialDuration partialDuration) {
		return instantTruncated.equals(partialDuration.instantTruncated);
	}

	public boolean isBiggerThan(Instant instant) {
		return instantTruncated.isAfter(instant);
	}

}
