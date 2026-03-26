package com.jerielb.bsa.service;

import com.jerielb.bsa.model.Boxer;
import com.jerielb.bsa.model.Matchup;
import com.jerielb.bsa.repository.BoxerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TournamentService {
	private final Logger LOGGER = LogManager.getLogger(RosterService.class);
	private final BoxerRepository BOXER_REPOSITORY;
	
	private static List<Matchup> quartersMatchups;
	private static List<Matchup> semisMatchups;
	private static List<Matchup> finalsMatchups;
	
	@Autowired
	public TournamentService (BoxerRepository boxerRepository) {
		this.BOXER_REPOSITORY = boxerRepository;
	}
	
	public List<Boxer> getWeightClassBoxers(String weight) {
		List<Boxer> boxers = BOXER_REPOSITORY.findWeightClassBoxers(weight);
		LOGGER.info("Getting Custom roster boxers from BOXER table for weight class {}", weight);
		return boxers;
	}
	
	public List<Matchup> setQuarterfinals(Boxer boxer) {
		// 16 boxers
		List<Boxer> boxers = getBoxersTournamentBoxers(boxer.getWeightclass());
		boxers.remove(boxer);
		// get 15 random boxers
		List<Boxer> tournamentRoster = getTournamentRoster(boxers, 15);
		
		// matchups
		quartersMatchups = new ArrayList<>();
		// selected boxer always advances
		quartersMatchups.add(new Matchup(boxer, tournamentRoster.get(7), true));
		for (int i = 0; i < 7; i++) {
			quartersMatchups.add(new Matchup(tournamentRoster.get(i), tournamentRoster.get(15-1-i)));
		}
		System.out.println("DEBUG - matchups:");
		quartersMatchups.forEach(x -> System.out.printf("\t%s\n", x));
		return quartersMatchups;
	}
	
	public List<Matchup> setSemifinals(Boxer boxer) {
		// 8 boxers
		List<Boxer> boxers = getBoxersTournamentBoxers(boxer.getWeightclass());		
		boxers.remove(boxer);
		// get 7 random boxers
		List<Boxer> tournamentRoster = getTournamentRoster(boxers, 7);
		
		// matchups
		semisMatchups = new ArrayList<>();
		// selected boxer always advances
		semisMatchups.add(new Matchup(boxer, tournamentRoster.get(3), true));
		for (int i = 0; i < 3; i++) {
			semisMatchups.add(new Matchup(tournamentRoster.get(i), tournamentRoster.get(7-1-i)));
		}
		System.out.println("DEBUG - matchups:");
		semisMatchups.forEach(x -> System.out.printf("\t%s\n", x));
		return semisMatchups;
	}

//	public List<Matchup> getSemifinals(Boxer boxer) {
//		// 4 boxers
//		return null;
//	}
//	
//	public Matchup getFinals(List<Boxer> boxers) {
//		// 2 boxers
//		return new Matchup(boxers.get(0), boxers.get(1));
//	}
	
	public List<Boxer> getBoxersTournamentBoxers(String weightclass) {
		List<String> input = new ArrayList<>();
		List<Boxer> roster;
		switch (weightclass) {
			case "bantamweight":
				input.add("bantamweight");
				input.add("featherweight");
				input.add("lightweight");
				roster = BOXER_REPOSITORY.findWeightClassesBoxers(input);
				break;
			case "featherweight":
				input.add("featherweight");
				input.add("lightweight");
				input.add("welterweight");
				roster = BOXER_REPOSITORY.findWeightClassesBoxers(input);
				break;
			case "lightweight":
				input.add("lightweight");
				input.add("welterweight");
				input.add("middleweight");
				roster = BOXER_REPOSITORY.findWeightClassesBoxers(input);
				break;
			case "welterweight":
				input.add("welterweight");
				input.add("middleweight");
				input.add("light-heavyweight");
				roster = BOXER_REPOSITORY.findWeightClassesBoxers(input);
				break;
			case "middleweight":
				input.add("middleweight");
				input.add("light-heavyweight");
				input.add("heavyweight");
				roster = BOXER_REPOSITORY.findWeightClassesBoxers(input);
				break;
			case "light-heavyweight":
				input.add("light-heavyweight");
				input.add("heavyweight");
				roster = BOXER_REPOSITORY.findWeightClassesBoxers(input);
				break;
			default:
				input.add("heavyweight");
				roster = BOXER_REPOSITORY.findWeightClassesBoxers(input);
				break;
		}
		return roster;
	}
	
	public List<Boxer> getTournamentRoster(List<Boxer> boxers, int count) {
		Collections.shuffle(boxers);
		return boxers.subList(0, count);
	}
}
