package com.frangr00.n26challenge;

import java.time.Instant;

public interface TimeService {

	Instant newInstant(long timestamp);

	Instant nowInstant();

}
