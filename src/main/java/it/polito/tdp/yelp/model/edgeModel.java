package it.polito.tdp.yelp.model;

public class edgeModel {

	private User u1;
	private User u2;
	private double peso;
	
	public edgeModel(User u1, User u2, double peso) {
		super();
		this.u1 = u1;
		this.u2 = u2;
		this.peso = peso;
	}

	public User getU1() {
		return u1;
	}

	public void setU1(User u1) {
		this.u1 = u1;
	}

	public User getU2() {
		return u2;
	}

	public void setU2(User u2) {
		this.u2 = u2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	
	
}
