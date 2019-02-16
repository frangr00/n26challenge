package com.frangr00.n26challenge.statistic.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.frangr00.n26challenge.StatisticsApplication;
import com.frangr00.n26challenge.statistic.Statistic;
import com.frangr00.n26challenge.statistic.StatisticRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = { StatisticsApplication.class })
@AutoConfigureMockMvc
public class StatisticControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private StatisticRepository repo;
	@Test
	public void givenStadisticInRepo_whenGetStats_thenResourceIsReturned() throws Exception {
		BDDMockito.given(repo.getStatistics()).willReturn(statisticTest());
		
		
		mvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				
				.andExpect(jsonPath("sum", is(10d)))
				.andExpect(jsonPath("avg", is(10d)))
				.andExpect(jsonPath("max", is(10d)))
				.andExpect(jsonPath("min", is(10d)))
				.andExpect(jsonPath("count", is(1)));
		
	}
	private Statistic statisticTest() {
		return new Statistic(10d, 10d, 10d, 10d, 1);
	}

}
