package com.jerielb.bsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RosterController {
	
	@RequestMapping(path="/roster", method= RequestMethod.GET)
	public String getRosterPage() {
		return "roster";
	}
}
