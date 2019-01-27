package com.frangr00.n26challenge.transaction;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.frangr00.n26challenge.TimeService;

import io.vavr.control.Either;

@Component
public class TransactionFactory {

	private TimeService timeService;

	public TransactionFactory(TimeService timeServiceImpl) {
		super();
		this.timeService = timeServiceImpl;
	}

	public Either<String, Transaction> create(double amount, long timestamp) {
		if (isBefore60Seg(timestamp)) {
			return Either.left("Time is out of range");
		}
		if (amount < 0d) {
			return Either.left("Amount must be a positive number");
		}
		return Either.right(new Transaction(amount, timeService.newInstant(timestamp)));
	}

	private boolean isBefore60Seg(long timestamp) {
		return Instant.ofEpochMilli(timestamp)
				.isBefore(timeService.nowInstant()
						.minusSeconds(60));

	}

}
