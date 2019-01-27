package com.frangr00.n26challenge.transaction.rest;

class TransactionRepresentation {

	private double amount;
	private long timestamp;

	TransactionRepresentation() {
		super();
	}

	public TransactionRepresentation(double amount, long timestamp) {
		super();
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public long getTimestamp() {
		return timestamp;
	}
}