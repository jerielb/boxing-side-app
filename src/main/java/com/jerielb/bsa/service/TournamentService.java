package com.jerielb.bsa.service;

import com.jerielb.bsa.model.Boxer;
import com.jerielb.bsa.repository.BoxerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {
	private final Logger LOGGER = LogManager.getLogger(RosterService.class);
	private final BoxerRepository BOXER_REPOSITORY;
	
	@Autowired
	public TournamentService (BoxerRepository boxerRepository) {
		this.BOXER_REPOSITORY = boxerRepository;
	}
	
	public List<Boxer> getWeightClassBoxers(String weight) {
		List<Boxer> boxers = BOXER_REPOSITORY.findWeightClassBoxers(weight);
		LOGGER.info("Getting Custom roster boxers from BOXER table for weight class {}", weight);
		return boxers;
	}
	
}
