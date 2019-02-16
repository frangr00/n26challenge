package com.frangr00.n26challenge.statistic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frangr00.n26challenge.statistic.StatisticRepository;

@RestController
@RequestMapping(path = "/statistics")
class StatisticControler {

	@Autowired
	private StatisticRepository statisticRepository;

	@GetMapping
	public ResponseEntity<StatisticRepresentation> getStatistics() {
		StatisticRepresentation statistic = new StatisticRepresentation(statisticRepository.getStatistics());
		
		return new ResponseEntity<StatisticRepresentation>(statistic, HttpStatus.OK);
	}

}
