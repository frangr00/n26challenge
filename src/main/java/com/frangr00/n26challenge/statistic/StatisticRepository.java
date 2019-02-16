package com.frangr00.n26challenge.statistic;

import com.frangr00.n26challenge.transaction.Transaction;

public interface StatisticRepository {

	void save(Transaction transaction);

	Statistic getStatistics();

}
