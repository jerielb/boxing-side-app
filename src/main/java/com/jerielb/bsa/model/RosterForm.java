package com.jerielb.bsa.model;

import java.util.List;

public class RosterForm {
	private List<String> weightClasses;
	private int version;
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public List<String> getWeightClasses() {
		return weightClasses;
	}
	
	public void setWeightClasses(List<String> weightClasses) {
		this.weightClasses = weightClasses;
	}
}
