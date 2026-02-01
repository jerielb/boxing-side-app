package com.jerielb.bsa.service;

import com.jerielb.bsa.model.Boxer;
import com.jerielb.bsa.repository.BoxerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RosterService {
	private final Logger LOGGER = LogManager.getLogger(RosterService.class);
	private final BoxerRepository BOXER_REPOSITORY;
	
	@Autowired
	public RosterService (BoxerRepository boxerRepository) {
		this.BOXER_REPOSITORY = boxerRepository;
	}
	
	public List<Boxer> getAllBoxers() {
		List<Boxer> boxers = BOXER_REPOSITORY.findAll();
		LOGGER.info("Getting all boxers from BOXER table");
		return boxers;
	}
}
