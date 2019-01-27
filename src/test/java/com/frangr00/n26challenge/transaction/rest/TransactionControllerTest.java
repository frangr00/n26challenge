package com.frangr00.n26challenge.transaction.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frangr00.n26challenge.StadisticsApplication;
import com.frangr00.n26challenge.transaction.rest.TransactionRepresentation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = { StadisticsApplication.class })
@AutoConfigureMockMvc
public class TransactionControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	private JacksonTester<TransactionRepresentation> teamJson;
	
	@Before
	public void setup() {
		JacksonTester.initFields(this, objectMapper);
	}
	
	@Test
	public void givenTransactionCorrect_whenPost_thenResponseCodeIsOk() throws Exception {
		TransactionRepresentation transactionSecInRange = new TransactionRepresentation(2.2d, Instant.now().toEpochMilli());
		
		mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(teamJson.write(transactionSecInRange)
						.getJson()))
		
		.andExpect(status().isOk());
	}
	
	@Test
	public void givenTransactionOutOfRange_whenPost_thenResponseCodeIsNoContent() throws Exception {
		TransactionRepresentation transactionOutOfRange = new TransactionRepresentation(2.2d, Instant.now().minusSeconds(61).toEpochMilli());
		
		mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(teamJson.write(transactionOutOfRange)
						.getJson()))
		
		.andExpect(status().isNoContent());
	}
}
