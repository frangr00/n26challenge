package com.frangr00.n26challenge.stadistic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frangr00.n26challenge.stadistic.StadisticRepository;

@RestController
@RequestMapping(path = "/statistics")
class StadisticControler {

	@Autowired
	private StadisticRepository stadisticRepository;

	@GetMapping
	public ResponseEntity<StadisticRepresentation> getStadistics() {
		StadisticRepresentation stadistic = new StadisticRepresentation(stadisticRepository.getStadistics());
		
		return new ResponseEntity<StadisticRepresentation>(stadistic, HttpStatus.OK);
	}

}
