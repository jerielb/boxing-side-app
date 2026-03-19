package com.jerielb.bsa.controller;

import com.jerielb.bsa.model.Boxer;
import com.jerielb.bsa.model.TournamentForm;
import com.jerielb.bsa.service.RosterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TournamentController {
	private final Logger LOGGER = LogManager.getLogger(TournamentController.class);
	private final RosterService ROSTER_SERVICE;
	
	@Autowired
	public TournamentController(RosterService ROSTER_SERVICE) {
		this.ROSTER_SERVICE = ROSTER_SERVICE;
	}
	
	@RequestMapping(path="/tournament_select_weight", method= RequestMethod.GET)
	public String getTournamentPage(Model model) {
		model.addAttribute("tournamentForm", new TournamentForm());
		return "tournament_select_weight";
	}
	
	@RequestMapping(path="/tournament_select_boxer", method= RequestMethod.POST)
	public String getRosterPage(@ModelAttribute("tournamentForm") TournamentForm form, Model model) {
		System.out.println("\nDEBUG - weight class: " + form.getWeightClass());
		List<Boxer> roster = ROSTER_SERVICE.getWeightClassBoxers(form.getWeightClass());
		roster.forEach(System.out::println);
		
//		// FILTER for weight classes (if selected)
//		List <Boxer> boxers = new ArrayList<>();
//		if (!form.getWeightClasses().isEmpty()) {
//			LOGGER.info("Filtering roster for selected weight classes");
//			for (Boxer boxer : roster) {
//				if (form.getWeightClasses().contains(boxer.getWeightclass())) {
//					boxers.add(boxer);
//				}
//			}
//		} else {
//			boxers = roster;
//		}
//		
//		// displayPage - is for the ROSTER page limit of 8 boxers per slide
//		List<Boxer> displayPage = new ArrayList<>();
//		List<List<Boxer>> displayPages = new ArrayList<>();
//		int max = 10;
//		for (Boxer boxer : boxers) {
//			displayPage.add(boxer);
//			
//			if (displayPage.size() == max) {
//				displayPages.add(displayPage);
//				displayPage = new ArrayList<>();
//			}
//		}
//		if (!displayPage.isEmpty()) {
//			while (displayPage.size() < 10) {
//				Boxer dummy = new Boxer(9999);
//				displayPage.add(dummy);
//			}
//			displayPages.add(displayPage);
//		}
//		
//		model.addAttribute("displayPages", displayPages);
		
		LOGGER.info("Redirecting to Tournament page");
		return "tournament";
	}
}
