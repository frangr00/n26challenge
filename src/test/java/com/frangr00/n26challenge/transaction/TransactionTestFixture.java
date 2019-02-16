package com.frangr00.n26challenge.transaction;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.frangr00.n26challenge.statistic.StatisticRepository;

public class TransactionTestFixture {

	private Instant now;
	private List<Transaction> transactions;
	private DoubleSummaryStatistics summaryStats;

	public static TransactionTestFixture given(Instant now) {
		return new TransactionTestFixture(now);
	}

	public TransactionTestFixture(Instant now) {
		super();
		this.now = now;
		transactions = new ArrayList<>();
		summaryStats = new DoubleSummaryStatistics();
	}

	public TransactionTestFixture transactionNow(double amount) {
		transactions.add(new Transaction(amount, now));
		return this;
	}

	public TransactionTestFixture transaction30segBefore(double amount) {
		transactions.add(new Transaction(amount, now.minusSeconds(30)));
		return this;
	}

	public TransactionTestFixture transaction60SegAfter(double amount) {
		transactions.add(new Transaction(amount, now.plusSeconds(60)));
		return this;
	}

	public TransactionTestFixture transaction59segBefore(double amount) {
		transactions.add(new Transaction(amount, now.minusSeconds(59)));
		return this;
	}

	public DoubleSummaryStatistics inRepo(StatisticRepository statisticRepository) {
		transactions.stream()
				.forEach(t -> statisticRepository.save(t));
		return summaryStats;
	}

	public TransactionTestFixture randomTransactions(int numTransactions) {
		IntStream.range(0, numTransactions).forEach(num -> {
			Double amount = randomPrice();
			summaryStats.accept(amount);
			Instant instant = now.truncatedTo(ChronoUnit.SECONDS).minusSeconds(randomSec());
			transactions.add(new Transaction(amount, instant));
		});
		return this;
	}

	private Double randomPrice() {
		double leftLimit = 1D;
		double rightLimit = 100D;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private int randomSec() {
		Random random = new Random();

		return random.nextInt(60);
	}

}
