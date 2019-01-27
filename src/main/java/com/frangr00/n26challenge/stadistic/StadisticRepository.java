package com.frangr00.n26challenge.stadistic;

import com.frangr00.n26challenge.transaction.Transaction;

public interface StadisticRepository {

	void save(Transaction transaction);

	Stadistic getStadistics();

}
