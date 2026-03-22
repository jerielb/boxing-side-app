package com.jerielb.bsa.model;

import java.util.List;

public class TournamentForm {
	private String weightClass = "heavyweight";
	private Boxer boxer;
	List<Boxer> opponents;
	
	public String getWeightClass() {
		return weightClass;
	}
	
	public void setWeightClass(String weightClass) {
		this.weightClass = weightClass;
	}
	
	public Boxer getBoxer() {
		return boxer;
	}
	
	public void setBoxer(Boxer boxer) {
		this.boxer = boxer;
	}
	
	public List<Boxer> getOpponents() {
		return opponents;
	}
	
	public void setOpponents(List<Boxer> opponents) {
		this.opponents = opponents;
	}
}
