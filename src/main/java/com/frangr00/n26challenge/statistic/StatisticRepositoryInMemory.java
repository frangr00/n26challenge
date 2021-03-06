package com.frangr00.n26challenge.statistic;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.frangr00.n26challenge.TimeService;
import com.frangr00.n26challenge.transaction.Transaction;

@Repository
class StatisticRepositoryInMemory implements StatisticRepository {

	private Map<Integer, PartialStatistic> partialStatistics;
	private TimeService timeService;

	public StatisticRepositoryInMemory(TimeService timeServiceImpl) {
		super();
		partialStatistics = new ConcurrentHashMap<>(60);
		this.timeService = timeServiceImpl;
	}

	@Override
	public void save(Transaction transaction) {
		PartialDuration partialDuration = new PartialDuration(transaction.getInstant());
		partialStatistics.computeIfAbsent(partialDuration.getSecond(), key -> new PartialStatistic(partialDuration));
		partialStatistics.compute(partialDuration.getSecond(), (key, partialStatistic) -> partialStatistic.accept(partialDuration, transaction.getAmount()));
	}

	/**
	 * By using ConcurrentHashMap there is no guarantee that one thread will see the changes to the map that the
	 * other thread performs (without obtaining a new iterator from the map). The
	 * iterator is guaranteed to reflect the state of the map at the time of it's
	 * creation. Futher changes may be reflected in the iterator, but they do not
	 * have to be.
	 * 
	 * Thus, if the requirements are more strictic, an additional lock is necessary.
	 */
	@Override
	public Statistic getStatistics() {
		Instant instant60secBefore = timeService.nowInstant()
				.minusSeconds(60);
		DoubleSummaryStatistics sumary = partialStatistics.entrySet()
				.stream()
				.map(Map.Entry::getValue)
				.filter(partialStat -> partialStat.isObsoleteTime(instant60secBefore))
				.map(PartialStatistic::getSummaryStatistics)
				.collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::combine, DoubleSummaryStatistics::combine);
		return Statistic.of(sumary);
	}
}