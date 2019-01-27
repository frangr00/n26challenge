package com.frangr00.n26challenge.stadistic.rest;

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

import com.frangr00.n26challenge.StadisticsApplication;
import com.frangr00.n26challenge.stadistic.Stadistic;
import com.frangr00.n26challenge.stadistic.StadisticRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = { StadisticsApplication.class })
@AutoConfigureMockMvc
public class StadisticControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private StadisticRepository repo;
	@Test
	public void givenStadisticInRepo_whenGetStats_thenResourceIsReturned() throws Exception {
		BDDMockito.given(repo.getStadistics()).willReturn(stadisticTest());
		
		
		mvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				
				.andExpect(jsonPath("sum", is(10d)))
				.andExpect(jsonPath("avg", is(10d)))
				.andExpect(jsonPath("max", is(10d)))
				.andExpect(jsonPath("min", is(10d)))
				.andExpect(jsonPath("count", is(1)));
		
	}
	private Stadistic stadisticTest() {
		return new Stadistic(10d, 10d, 10d, 10d, 1);
	}

}
