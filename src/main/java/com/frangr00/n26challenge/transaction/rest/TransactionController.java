package com.frangr00.n26challenge.transaction.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frangr00.n26challenge.stadistic.StadisticRepository;
import com.frangr00.n26challenge.transaction.Transaction;
import com.frangr00.n26challenge.transaction.TransactionFactory;

import io.vavr.control.Either;

@RestController
@RequestMapping(path = "/transactions")
class TransactionController {

	private TransactionFactory transactionFactory;
	private StadisticRepository stadisticRepository;
	
	public TransactionController(TransactionFactory transactionFactory, StadisticRepository stadisticRepository) {
		super();
		this.transactionFactory = transactionFactory;
		this.stadisticRepository = stadisticRepository;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody TransactionRepresentation transactionRepresentation) {
		Either<String, Transaction> transactionResult = transactionFactory.create(transactionRepresentation.getAmount(),
				transactionRepresentation.getTimestamp());
		
		if (transactionResult.isLeft()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.build();
		}
		stadisticRepository.save(transactionResult.get());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
