package com.jerielb.bsa.controller;

import com.jerielb.bsa.model.Boxer;
import com.jerielb.bsa.model.TournamentForm;
import com.jerielb.bsa.service.TournamentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TournamentController {
	private final Logger LOGGER = LogManager.getLogger(TournamentController.class);
	private final TournamentService TOURNAMENT_SERVICE;
	
	@Autowired
	public TournamentController(TournamentService TOURNAMENT_SERVICE) {
		this.TOURNAMENT_SERVICE = TOURNAMENT_SERVICE;
	}
	
	@RequestMapping(path="/tournament_select_weight", method= RequestMethod.GET)
	public String getTournamentWeightSelectionPage(Model model) {
		model.addAttribute("tournamentForm", new TournamentForm());
		return "tournament_select_weight";
	}
	
	@RequestMapping(path="/tournament_select_boxer", method= RequestMethod.POST)
	public String getTournamentBoxerSelectionPage(@ModelAttribute("tournamentForm") TournamentForm form, Model model) {
		System.out.println("DEBUG - weight class: " + form.getWeightClass());
		List<Boxer> roster = TOURNAMENT_SERVICE.getWeightClassBoxers(form.getWeightClass());
		roster.forEach(System.out::println);

		// displayPage - is for the ROSTER page limit of 8 boxers per slide
		List<Boxer> displayPage = new ArrayList<>();
		List<List<Boxer>> displayPages = new ArrayList<>();
		int max = 10;
		for (Boxer boxer : roster) {
			displayPage.add(boxer);

			if (displayPage.size() == max) {
				displayPages.add(displayPage);
				displayPage = new ArrayList<>();
			}
		}
		if (!displayPage.isEmpty()) {
			while (displayPage.size() < 10) {
				Boxer dummy = new Boxer(9999);
				displayPage.add(dummy);
			}
			displayPages.add(displayPage);
		}

		model.addAttribute("displayPages", displayPages);
		model.addAttribute("tournamentForm", form);
		
		return "tournament_select_boxer";
	}
	
	@RequestMapping(path="/tournament", method= RequestMethod.POST)
	public String getTournamentPage(@ModelAttribute("tournamentForm") TournamentForm form, Model model) {
		System.out.println("\tDEBUG - boxer selected: " + form.getBoxer());
		
		return "tournament";
	}
}
