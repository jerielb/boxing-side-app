package com.jerielb.bsa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Boxer {
	@Id
	private long boxerId;
	private String boxerFirstName;
	private String boxerLastName;
	private String weightclass;
	private int rating;
	private String height;
	private String weight;
	private String fnc;
	private String fnf;
	private String active;
	
	public Boxer() {}
	
	public long getBoxerId() {
		return boxerId;
	}
	
	public void setBoxerId(long boxerId) {
		this.boxerId = boxerId;
	}
	
	public String getBoxerFirstName() {
		return boxerFirstName;
	}
	
	public void setBoxerFirstName(String boxerFirstName) {
		this.boxerFirstName = boxerFirstName;
	}
	
	public String getBoxerLastName() {
		return boxerLastName;
	}
	
	public void setBoxerLastName(String boxerLastName) {
		this.boxerLastName = boxerLastName;
	}
	
	public String getWeightclass() {
		return weightclass;
	}
	
	public void setWeightclass(String weightclass) {
		this.weightclass = weightclass;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getHeight() {
		return height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public String getFnc() {
		return fnc;
	}
	
	public void setFnc(String fnc) {
		this.fnc = fnc;
	}
	
	public String getFnf() {
		return fnf;
	}
	
	public void setFnf(String fnf) {
		this.fnf = fnf;
	}
	
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "Boxer{" +
				boxerFirstName + ' ' + boxerLastName +
				", " + weightclass +
				'}';
	}
}
