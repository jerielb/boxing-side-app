package com.jerielb.bsa.model;

import java.util.List;

public class Matchup {
	Boxer boxer1;
	Boxer boxer2;
	Boxer winner;
	
	public Matchup(Boxer boxer1, Boxer boxer2) {
		this.boxer1 = boxer1;
		this.boxer2 = boxer2;
	}
	
	public Matchup(Boxer boxer1, Boxer boxer2, boolean selected) {
		this.boxer1 = boxer1;
		this.boxer2 = boxer2;
		if (selected) {
			this.winner = boxer1;
		}
	}
	
	public Boxer getBoxer1() {
		return boxer1;
	}
	
	public void setBoxer1(Boxer boxer1) {
		this.boxer1 = boxer1;
	}
	
	public Boxer getBoxer2() {
		return boxer2;
	}
	
	public void setBoxer2(Boxer boxer2) {
		this.boxer2 = boxer2;
	}
	
	public Boxer getWinner() {
		if (winner == null) {
			this.winner = (Math.random() < 0.5) ? boxer1 : boxer2;
		}
		return winner;
	}
	
	public void setWinner(Boxer winner) {
		this.winner = winner;
	}
	
	@Override
	public String toString() {
		return "Matchup{" +
				"boxer1=" + boxer1 +
				", boxer2=" + boxer2 +
				'}';
	}
}
