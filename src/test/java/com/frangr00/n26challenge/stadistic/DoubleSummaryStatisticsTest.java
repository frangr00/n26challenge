package com.frangr00.n26challenge.stadistic;

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
	public void stadisticShouldBeCreatedOk() {
		DoubleSummaryStatistics stadistic = new DoubleSummaryStatistics();
		stadistic.accept(10d);
		
		then(stadistic.getAverage()).isEqualTo(10d); 
		then(stadistic.getMax()).isEqualTo(10d); 
		then(stadistic.getMin()).isEqualTo(10d); 
		then(stadistic.getSum()).isEqualTo(10d); 
		then(stadistic.getCount()).isEqualTo(1l); 
	}
	
	@Test
	public void stadisticShouldBeMergedOk() {
		DoubleSummaryStatistics stadistic = new DoubleSummaryStatistics();
		stadistic.accept(10d);
		stadistic.accept(30d);
		
		then(stadistic.getAverage()).isEqualTo(20d); 
		then(stadistic.getMax()).isEqualTo(30d); 
		then(stadistic.getMin()).isEqualTo(10d); 
		then(stadistic.getSum()).isEqualTo(40d); 
		then(stadistic.getCount()).isEqualTo(2l); 
	}
	
}
