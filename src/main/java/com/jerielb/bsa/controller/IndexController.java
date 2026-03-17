package com.jerielb.bsa.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class IndexController {
	private final Logger LOGGER = LogManager.getLogger(RosterController.class);
	
	@RequestMapping(path="/index", method= RequestMethod.GET)
	public String getHomePage() {
		LOGGER.info("Redirecting to Home page");
		return "redirect:/";
	}
}
