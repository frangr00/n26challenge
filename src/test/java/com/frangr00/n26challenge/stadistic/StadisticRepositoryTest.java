package com.frangr00.n26challenge.stadistic;

import static com.frangr00.n26challenge.transaction.TransactionTestFixture.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

import java.time.Clock;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.frangr00.n26challenge.TimeService;
import com.frangr00.n26challenge.transaction.Transaction;
import com.frangr00.n26challenge.transaction.TransactionFactory;

@RunWith(MockitoJUnitRunner.class)
public class StadisticRepositoryTest {

	@InjectMocks
	private StadisticRepository stadisticRepository;
	@Mock
	private TimeService timeService;
	private Instant nowInstant = Instant.now(Clock.systemDefaultZone());
	@InjectMocks
	private TransactionFactory transactionFactory;

	public StadisticRepositoryTest() {
		stadisticRepository = new StadisticRepositoryInMemory(null);
	}

	@Before
	public void init() {
		BDDMockito.given(timeService.nowInstant())
				.willReturn(nowInstant);
		BDDMockito.given(timeService.newInstant(Mockito.anyLong()))
				.willReturn(nowInstant);
	}

	@Test
	public void transactionShouldBeStoredOk() {
		Transaction transaction = transactionFactory.create(10d, nowInstant.toEpochMilli())
				.get();

		stadisticRepository.save(transaction);

		then(stadisticRepository.getStadistics()).satisfies(stats -> {
			assertThat(stats.getAverage()).isEqualTo(10d);
			assertThat(stats.getCount()).isEqualTo(1);
			assertThat(stats.getMin()).isEqualTo(10d);
			assertThat(stats.getMax()).isEqualTo(10d);
		});
	}

	@Test
	public void givenRepositoryWithTransactionInSameSecond_whenGetStats_thenAllTransactionAreTakingIntoAccount() {
		given(nowInstant)
				.transactionNow(10d)
				.transactionNow(15d)
				.transactionNow(20d)
				.inRepo(stadisticRepository);

		Stadistic stadistic = stadisticRepository.getStadistics();

		then(stadistic.getSum()).isEqualTo(45d);
		then(stadistic.getCount()).isEqualTo(3);
	}

	@Test
	public void givenRepositoryWithTransaction_whenGetStats_thenAllTransactionAreTakingIntoAccount() {
		given(nowInstant)
				.transactionNow(10d)
				.transactionNow(1d)
				.transaction30segBefore(15d)
				.transaction59segBefore(20d)
				.inRepo(stadisticRepository);

		Stadistic stadistic = stadisticRepository.getStadistics();

		then(stadistic.getSum()).isEqualTo(46d);
		then(stadistic.getCount()).isEqualTo(4);
	}

	@Test
	public void given2TransactionInTheSameSecond_whenGetStats_thenTheOlderTransactionIsIgnored() {
		given(nowInstant)
				.transactionNow(10d)
				.transaction60SegAfter(20d)
				.inRepo(stadisticRepository);
		givenCurrentInstantPlusSeconds(60);

		Stadistic stadistic = stadisticRepository.getStadistics();

		then(stadistic.getSum()).isEqualTo(20d);
		then(stadistic.getCount()).isEqualTo(1);
	}

	@Test
	public void givenRepositoryWithObsoleteTransaction_whenGetStats_thenObsoleteTransactionIsIgnored() {
		given(nowInstant)
				.transactionNow(10d)
				.transaction30segBefore(10d)
				.inRepo(stadisticRepository);
		givenCurrentInstantPlusSeconds(30);

		Stadistic stadistic = stadisticRepository.getStadistics();

		then(stadistic.getSum()).isEqualTo(10d);
	}

	@Test
	public void givenRepositoryWithRandomValues_whenGetStat_thenRightStadisticsAreReturned() {
		DoubleSummaryStatistics stats = given(nowInstant)
				.randomTransactions(1_000_000)
				.inRepo(stadisticRepository);

		Stadistic stadistic = stadisticRepository.getStadistics();

		then(stadistic.getCount()).isEqualTo(1_000_000);
		then(stadistic.getSum()).isEqualTo(stats.getSum());
		then(stadistic.getAverage()).isEqualTo(stats.getAverage());
		then(stadistic.getMax()).isEqualTo(stats.getMax());
		then(stadistic.getMin()).isEqualTo(stats.getMin());
	}

	private void givenCurrentInstantPlusSeconds(int seconds) {
		BDDMockito.given(timeService.nowInstant())
				.willReturn(nowInstant.plusSeconds(seconds));
	}
}
