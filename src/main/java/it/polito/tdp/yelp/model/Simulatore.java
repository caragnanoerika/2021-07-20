package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Evento.EventType;

public class Simulatore {
	
	
	//parametri di input
	
	private int nIntervistatori;
	private int nIntervistati;
	
	
	//parametri di uscita
	//Map<Giornalista,Integer> interviste = new HashMap<Giornalista,Integer>();
	
	private List<Giornalista> giornalisti;
	private int nGiorni;
	
	
	//stato del sistema
	private List<User> intervistabili;
	private Set<User> intervistati;
	private Graph<User,DefaultWeightedEdge> grafo;
	
	//coda degli eventi
	PriorityQueue<Evento> queue = new PriorityQueue<>();
	

	public Simulatore(Graph<User,DefaultWeightedEdge> grafo) {
		
		
		this.grafo = grafo;
	}


	public void initialize(int intervistatori, int intervistati) {
		this.intervistabili = new ArrayList<User>(grafo.vertexSet());
		this.nIntervistatori = intervistatori;
		this.nIntervistati = intervistati;
		this.intervistati = new HashSet<User>();
		this.nGiorni = 0;
		loadGiornalisti(intervistatori);
		for (Giornalista g : this.giornalisti) {
			this.queue.add(new Evento(EventType.INTERVIEW,g,nGiorni,selezionaPrimoIntervistato()));
		}
		
	}
	
	public void run() {
		Evento e = null;
		while (!this.queue.isEmpty()) {
			e = this.queue.poll();
			this.nGiorni= e.getTime();
			processEvent(e);
		}
		;
	}
	
	
	
	public List<Giornalista> getGiornalisti() {
		return giornalisti;
	}


	public void setGiornalisti(List<Giornalista> giornalisti) {
		this.giornalisti = giornalisti;
	}


	public int getnGiorni() {
		return nGiorni;
	}


	public void setnGiorni(int nGiorni) {
		this.nGiorni = nGiorni;
	}


	private void processEvent(Evento e) {
		 
		 switch (e.getType()) {
		 
		 case INTERVIEW:
			 double p = Math.random();
			 if (this.intervistati.size()<nIntervistati){
				 if (p<=0.6) {
					 //intervista conclusa, scelgo un intervistato per domani
					 User intervistato = selezionaIntevistato(e.getU());
					 if (intervistato!=null) {
						 this.queue.add(new Evento(EventType.INTERVIEW,e.getG(),e.getTime()+1,intervistato));
						 e.getG().setNumeroIntervistati(e.getG().getNumeroIntervistati()+1);
						 intervistati.add(intervistato);
						 
					 }
				 } else if( p<=0.8 && p>0.6) {
					 //ferie domani
					 
					 this.queue.add(new Evento(EventType.DAYOFF, e.getG(),e.getTime()+1,null));
				 } else {
					 //intervisto di nuovo il tipo di oggi
					 
					 this.queue.add(new Evento(EventType.INTERVIEW,e.getG(),e.getTime()+1,e.getU()));
				 }
			 }
			 break;
		 
		 case DAYOFF:
			 this.queue.add(new Evento(EventType.INTERVIEW,e.getG(),e.getTime()+1,selezionaPrimoIntervistato()));
			 break;
		 
		 }
		
	}


	private User selezionaIntevistato(User u) {
		Random random = new Random();
		User next ;
		List<User> simili = new ArrayList<User>(calcolaSimile(u));
		
		if(simili.size()==0) {
			next = selezionaPrimoIntervistato();
		} else {
			int randomNumber = random.nextInt(simili.size());
			next = simili.get(randomNumber);
		}
		
		intervistabili.remove(next);
		return next;
	}
	
	public List<User> calcolaSimile(User u) {
		List<User> simili = new ArrayList<User>();
		if (this.grafo.vertexSet().contains(u)) {
			List<User> adiacenti = new ArrayList<User>(Graphs.neighborListOf(this.grafo, u));
			double gradoMax = 0.0;
			for (User user : adiacenti) {
				if (this.intervistabili.contains(user)) {
					DefaultWeightedEdge e = this.grafo.getEdge(u, user);
					if (this.grafo.getEdgeWeight(e)>gradoMax) {
						gradoMax = this.grafo.getEdgeWeight(e);
					}
				}
				
			}
			for (User user : adiacenti) {
				if(this.intervistabili.contains(user)) {
					DefaultWeightedEdge e = this.grafo.getEdge(u, user);
					if (this.grafo.getEdgeWeight(e)==gradoMax) {
						simili.add(user);
					}
				}
				
			}
		}
		
		
		
		return simili;
	}


	public void loadGiornalisti(int intervistatori) {
		this.giornalisti = new ArrayList<Giornalista>();
		for (Integer i=0; i<intervistatori; i++) {
			Giornalista g = new Giornalista(i);
			this.giornalisti.add(g);
		}
	}
	
	public User selezionaPrimoIntervistato() {
		User u = null;
		if(this.intervistabili.size()!=0) {
			Random random = new Random();
			int randomNumber = random.nextInt(this.intervistabili.size());
			u = this.intervistabili.get(randomNumber);
			
			intervistabili.remove(u);
		}
		return u;
	}

}
