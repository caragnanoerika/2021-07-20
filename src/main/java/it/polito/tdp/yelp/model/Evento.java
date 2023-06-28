package it.polito.tdp.yelp.model;

public class Evento implements Comparable<Evento>{

	public enum EventType{
		INTERVIEW,
		DAYOFF
	}
	
	private EventType type;
	private Giornalista g;
	private int time;
	private User u;
	
	public Evento(EventType type, Giornalista g, int time, User u) {
		super();
		this.type = type;
		this.g = g;
		this.time = time;
		this.u = u;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Giornalista getG() {
		return g;
	}

	public void setG(Giornalista g) {
		this.g = g;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((g == null) ? 0 : g.hashCode());
		result = prime * result + time;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((u == null) ? 0 : u.hashCode());
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
		Evento other = (Evento) obj;
		if (g == null) {
			if (other.g != null)
				return false;
		} else if (!g.equals(other.g))
			return false;
		if (time != other.time)
			return false;
		if (type != other.type)
			return false;
		if (u == null) {
			if (other.u != null)
				return false;
		} else if (!u.equals(other.u))
			return false;
		return true;
	}

	@Override
	public int compareTo(Evento o) {
		
		return this.time + o.getTime();
	}
	
	
	
	
}
