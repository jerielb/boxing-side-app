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
	private static boolean customBracket = false; 
	
	private static int round = 0;
	private static int customRound = 0;
	
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
		customBracket = false;
		round = 0;
		
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
		customBracket = true;
		customRound = 0;
		
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
				boxerIds.add("36"); // creed (HW[25], LHW[36])
				boxerIds.add("25"); // creed (HW[25], LHW[36])
				break;
			default:
				input.add("heavyweight");
				// remove duplicates + creed
				boxerIds.add("25"); // creed (HW[25], LHW[36])
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
	
	public Boolean getCustomBracket() {
		return  customBracket;
	}
	
	public List<Matchup> getEightMatchups() {
		if (round == 0) {
			// set Matchup winners
			for (int i=1; i<8; i++) {
				eightMatchups.get(i).setWinner();
			}
			
			// set next round Matchups
			fourMatchups = new ArrayList<>();
			// selected boxer always advances
			fourMatchups.add(new Matchup(eightMatchups.get(0).getWinner(), eightMatchups.get(1).getWinner(), true));
			for (int i = 2; i<7; i+=2) {
				fourMatchups.add(new Matchup(eightMatchups.get(i).getWinner(), eightMatchups.get(i+1).getWinner()));
			}
		}
		return eightMatchups;
	}
	
	public List<Matchup> getFourMatchups() {
		if ((customRound == 0 && customBracket) || (round == 1 && !customBracket)) {
			// set Matchup winners
			for (int i=1; i<4; i++) {
				fourMatchups.get(i).setWinner();
				System.out.println("DEBUG - winner: " + fourMatchups.get(i).getWinner());
			}
			
			// set next round Matchups
			twoMatchups = new ArrayList<>();
			// selected boxer always advances
			twoMatchups.add(new Matchup(fourMatchups.get(0).getWinner(), fourMatchups.get(1).getWinner(), true));
			twoMatchups.add(new Matchup(fourMatchups.get(2).getWinner(), fourMatchups.get(3).getWinner()));
		}
		return fourMatchups;
	}
	
	public List<Matchup> getTwoMatchups() {
		if ((customRound == 1 && customBracket) || (round == 2 && !customBracket)) {
			// set Matchup winners
			for (int i=1; i<2; i++) {
				twoMatchups.get(i).setWinner();
				System.out.println("DEBUG - winner: " + twoMatchups.get(i).getWinner());
			}
			
			// set next round Matchups
			finalsMatchups = new ArrayList<>();
			// selected boxer always advances
			finalsMatchups.add(new Matchup(twoMatchups.get(0).getWinner(), twoMatchups.get(1).getWinner(), true));
		}
		return twoMatchups;
	}
	
	public List<Matchup> getFinalsMatchups() {
		return finalsMatchups;
	}
	
	public void updateRound() {
		round++;
		customRound++;
	}
}
