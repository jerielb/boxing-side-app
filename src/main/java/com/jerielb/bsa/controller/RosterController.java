package com.jerielb.bsa.controller;

import com.jerielb.bsa.model.Boxer;
import com.jerielb.bsa.model.RosterForm;
import com.jerielb.bsa.service.RosterService;
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
public class RosterController {
	private final Logger LOGGER = LogManager.getLogger(RosterController.class);
	private final RosterService ROSTER_SERVICE;
	
	@Autowired
	public RosterController(RosterService ROSTER_SERVICE) {
		this.ROSTER_SERVICE = ROSTER_SERVICE;
	}
	
	@RequestMapping(path="/roster_options", method= RequestMethod.GET)
	public String getRosterPage(Model model) {
		model.addAttribute("rosterForm", new RosterForm());
		return "roster_options";
	}
	
	@RequestMapping(path="/roster", method= RequestMethod.POST)
	public String getRosterPage(@ModelAttribute("rosterForm") RosterForm form, Model model) {		
		List<Boxer> roster = switch (form.getVersion()) {
			case 1 ->
				// Fight Night Champion roster
					ROSTER_SERVICE.getFNCRoster();
			case 2 ->
				// Fight Night Forever roster
					ROSTER_SERVICE.getFNFRoster();
			case 3 ->
				// Custom roster
					ROSTER_SERVICE.getCustomRoster();
			default ->
				// All boxers
					ROSTER_SERVICE.getAllBoxers();
		};
		
		// FILTER for weight classes (if selected)
		List <Boxer> boxers = new ArrayList<>();
		if (!form.getWeightClasses().isEmpty()) {
			LOGGER.info("Filtering roster for selected weight classes");
			for (Boxer boxer : roster) {
				if (form.getWeightClasses().contains(boxer.getWeightclass())) {
					boxers.add(boxer);
				}
			}
		} else {
			boxers = roster;
		}
		
		// displayPage - is for the ROSTER page limit of 8 boxers per slide
		List<Boxer> displayPage = new ArrayList<>();
		List<List<Boxer>> displayPages = new ArrayList<>();
		int max = 10;
		for (Boxer boxer : boxers) {
			displayPage.add(boxer);
			
			if (displayPage.size() == max) {
				displayPages.add(displayPage);
				displayPage = new ArrayList<>();
			}
		}
		while (displayPage.size() < 10) {
			Boxer dummy = new Boxer(9999);
			displayPage.add(dummy);
		}
		displayPages.add(displayPage);
		
		model.addAttribute("displayPages", displayPages);
		
		LOGGER.info("Redirecting to Roster page");
		return "roster";
	}
}
