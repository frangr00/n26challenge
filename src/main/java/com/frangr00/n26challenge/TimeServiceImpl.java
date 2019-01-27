package com.frangr00.n26challenge;

import java.time.Clock;
import java.time.Instant;

import org.springframework.stereotype.Service;

@Service
class TimeServiceImpl implements TimeService {

	@Override
	public Instant newInstant(long timestamp) {
		return Instant.ofEpochMilli(timestamp);
	}

	@Override
	public Instant nowInstant() {
		return Instant.now(Clock.systemDefaultZone());
	}
}
