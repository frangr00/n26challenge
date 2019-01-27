package com.frangr00.n26challenge.stadistic;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.frangr00.n26challenge.TimeService;
import com.frangr00.n26challenge.transaction.Transaction;

@Repository
class StadisticRepositoryInMemory implements StadisticRepository {

	private Map<Integer, PartialStadistic> partialStadistics;
	private TimeService timeService;

	public StadisticRepositoryInMemory(TimeService timeServiceImpl) {
		super();
		partialStadistics = new ConcurrentHashMap<>(60);
		this.timeService = timeServiceImpl;
	}

	@Override
	public void save(Transaction transaction) {
		PartialDuration partialDuration = new PartialDuration(transaction.getInstant());
		partialStadistics.computeIfAbsent(partialDuration.getSecond(), key -> new PartialStadistic(partialDuration));
		partialStadistics.compute(partialDuration.getSecond(), (key, partialStadistic) -> partialStadistic.accept(partialDuration, transaction.getAmount()));
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
	public Stadistic getStadistics() {
		Instant instant60secBefore = timeService.nowInstant()
				.minusSeconds(60);
		DoubleSummaryStatistics sumary = partialStadistics.entrySet()
				.stream()
				.map(Map.Entry::getValue)
				.filter(partialStat -> partialStat.isObsoleteTime(instant60secBefore))
				.map(PartialStadistic::getSummaryStatistics)
				.collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::combine, DoubleSummaryStatistics::combine);
		return Stadistic.of(sumary);
	}
}