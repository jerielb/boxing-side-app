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
	
	private static List<Matchup> eightMatchups;
	private static List<Matchup> fourMatchups;
	private static List<Matchup> twoMatchups;
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
	
	public List<Matchup> setEightMatchups(Boxer boxer) {
		// 16 boxers
		List<Boxer> boxers = getBoxersTournamentBoxers(boxer);
		boxers.remove(boxer);
		// get 15 random boxers
		List<Boxer> tournamentRoster = getTournamentRoster(boxers, 15);
		
		// matchups
		eightMatchups = new ArrayList<>();
		// selected boxer always advances
		eightMatchups.add(new Matchup(boxer, tournamentRoster.get(7), true));
		for (int i = 0; i < 7; i++) {
			eightMatchups.add(new Matchup(tournamentRoster.get(i), tournamentRoster.get(15-1-i)));
		}
		System.out.println("DEBUG - matchups:");
		eightMatchups.forEach(x -> System.out.printf("\t%s\n", x));
		
		// dummy fill matchups - four; two; finals;
		Boxer dummy = new Boxer(9999);
		fourMatchups = new ArrayList<>();
		twoMatchups = new ArrayList<>();
		finalsMatchups = new ArrayList<>();
		for (int i=0; i<4; i++) {
			fourMatchups.add(new Matchup(dummy, dummy));
			if (i<2) {
				twoMatchups.add(new Matchup(dummy, dummy));
				finalsMatchups.add(new Matchup(dummy, dummy));
			}
		}
		return eightMatchups;
	}
	
	public List<Matchup> setFourMatchups(Boxer boxer) {
		// 8 boxers
		List<Boxer> boxers = getBoxersTournamentBoxers(boxer);
		boxers.remove(boxer);
		// get 7 random boxers
		List<Boxer> tournamentRoster = getTournamentRoster(boxers, 7);
		
		// matchups
		fourMatchups = new ArrayList<>();
		// selected boxer always advances
		fourMatchups.add(new Matchup(boxer, tournamentRoster.get(3), true));
		for (int i = 0; i < 3; i++) {
			fourMatchups.add(new Matchup(tournamentRoster.get(i), tournamentRoster.get(7-1-i)));
		}
		System.out.println("DEBUG - matchups:");
		fourMatchups.forEach(x -> System.out.printf("\t%s\n", x));
		
		// dummy fill matchups - two; finals;
		Boxer dummy = new Boxer(9999);
		twoMatchups = new ArrayList<>();
		finalsMatchups = new ArrayList<>();
		for (int i=0; i<2; i++) {
			twoMatchups.add(new Matchup(dummy, dummy));
			finalsMatchups.add(new Matchup(dummy, dummy));
		}
		return fourMatchups;
	}
	
	public List<Boxer> getBoxersTournamentBoxers(Boxer boxer) {
		List<String> input = new ArrayList<>();
		List<String> boxerIds = new ArrayList<>();
		List<Boxer> roster;
		switch (boxer.getWeightclass()) {
			case "bantamweight":
				input.add("bantamweight");
				input.add("featherweight");
				input.add("lightweight");
				boxerIds.add("-1");
				break;
			case "featherweight":
				input.add("featherweight");
				input.add("lightweight");
				input.add("welterweight");
				// remove duplicates
				boxerIds.add("70"); // pacman (LW[90], WW[70])
				boxerIds.add("75"); // duran (LW[94], WW[75])
				break;
			case "lightweight":
				input.add("lightweight");
				input.add("welterweight");
				input.add("middleweight");
				// remove duplicates
				boxerIds.add("70"); // pacman (LW[90], WW[70])
				boxerIds.add("75"); // duran (LW[94], WW[75])
				boxerIds.add("58"); // crawford (LW[99], MW[58])
				boxerIds.add("57"); // hearns (WW[78], MW[57])
				break;
			case "welterweight":
				input.add("welterweight");
				input.add("middleweight");
				input.add("light-heavyweight");
				// remove duplicates + creed
				boxerIds.add("57"); // hearns (WW[78], MW[57])
				boxerIds.add("33"); // rjj (MW[53], LHW[33])
				boxerIds.add("25"); // creed (HW[36], LHW[25])
				break;
			case "middleweight":
				input.add("middleweight");
				input.add("light-heavyweight");
				input.add("heavyweight");
				// remove duplicates + creed
				boxerIds.add("33"); // rjj (MW[53], LHW[33])
				boxerIds.add("6"); // foreman (young[7], old[6])
				boxerIds.add("36"); // creed (HW[36], LHW[25])
				boxerIds.add("25"); // creed (HW[36], LHW[25])
				break;
			case "light-heavyweight":
				input.add("light-heavyweight");
				input.add("heavyweight");
				// remove duplicates + creed
				boxerIds.add("6"); // foreman (young[7], old[6])
				boxerIds.add("36"); // creed (HW[36], LHW[25])
				boxerIds.add("25"); // creed (HW[36], LHW[25])
				break;
			default:
				input.add("heavyweight");
				// remove duplicates + creed
				boxerIds.add("36"); // creed (HW[36], LHW[25])
				if (boxer.getBoxerId() == 6) {
					boxerIds.add("7"); // foreman (young[7], old[6])
				} else {
					boxerIds.add("6"); // foreman (young[7], old[6])
				}
				break;
		}
		return BOXER_REPOSITORY.findWeightClassesBoxers(input, boxerIds);
	}
	
	public List<Boxer> getTournamentRoster(List<Boxer> boxers, int count) {
		Collections.shuffle(boxers);
		return boxers.subList(0, count);
	}
	
	public List<Matchup> getFourMatchups() {
		return fourMatchups;
	}
	
	public List<Matchup> getTwoMatchups() {
		return twoMatchups;
	}
	
	public List<Matchup> getFinalsMatchups() {
		return finalsMatchups;
	}
}
