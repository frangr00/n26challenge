package com.frangr00.n26challenge.transaction;

import java.time.Instant;

public class Transaction {

	private double amount;
	private Instant instant;

	Transaction(double amount, Instant instant) {
		super();
		this.amount = amount;
		this.instant = instant;
	}

	public double getAmount() {
		return amount;
	}

	public Instant getInstant() {
		return instant;
	}
}
