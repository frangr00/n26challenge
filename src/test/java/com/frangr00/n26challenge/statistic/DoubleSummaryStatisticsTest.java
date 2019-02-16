package com.frangr00.n26challenge.statistic;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.DoubleSummaryStatistics;

import org.junit.Test;

/**
 * I'm not testing the JVM implementation, just I'm making sure I fully understand the implementation playing around with it.
 * 
 * @author Fran
 *
 */
public class DoubleSummaryStatisticsTest {

	@Test
	public void statisticShouldBeCreatedOk() {
		DoubleSummaryStatistics statistic = new DoubleSummaryStatistics();
		statistic.accept(10d);
		
		then(statistic.getAverage()).isEqualTo(10d); 
		then(statistic.getMax()).isEqualTo(10d); 
		then(statistic.getMin()).isEqualTo(10d); 
		then(statistic.getSum()).isEqualTo(10d); 
		then(statistic.getCount()).isEqualTo(1l); 
	}
	
	@Test
	public void statisticShouldBeMergedOk() {
		DoubleSummaryStatistics statistic = new DoubleSummaryStatistics();
		statistic.accept(10d);
		statistic.accept(30d);
		
		then(statistic.getAverage()).isEqualTo(20d); 
		then(statistic.getMax()).isEqualTo(30d); 
		then(statistic.getMin()).isEqualTo(10d); 
		then(statistic.getSum()).isEqualTo(40d); 
		then(statistic.getCount()).isEqualTo(2l); 
	}
	
}
