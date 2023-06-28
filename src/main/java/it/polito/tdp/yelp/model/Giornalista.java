package it.polito.tdp.yelp.model;

public class Giornalista {

	Integer ID;
	Integer numeroIntervistati;
	
	
	public Giornalista(Integer iD) {
		super();
		ID = iD;
		numeroIntervistati = 0;
	}
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public Integer getNumeroIntervistati() {
		return numeroIntervistati;
	}

	public void setNumeroIntervistati(Integer numeroIntervistati) {
		this.numeroIntervistati = numeroIntervistati;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((numeroIntervistati == null) ? 0 : numeroIntervistati.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giornalista other = (Giornalista) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (numeroIntervistati == null) {
			if (other.numeroIntervistati != null)
				return false;
		} else if (!numeroIntervistati.equals(other.numeroIntervistati))
			return false;
		return true;
	}

	
	
	
}
