package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private List<Integer> anni;
	private YelpDao dao;
	private Graph<User,DefaultWeightedEdge> grafo;
	private Map<String,User> users;
	
	private int nGiorni;
	private List<Giornalista> giornalisti;
	
	
	public Model() {
		 this.anni = new ArrayList<Integer>();
		 for (int i=2005;i<2014; i++) {
			 this.anni.add(i);
		 }
		 
		 this.dao = new YelpDao();
		 
		 
	}

	public void creaGrafo(Integer n, Integer anno) {
		clearGraph();
		Graphs.addAllVertices(this.grafo, this.dao.getAllVertices(n));
		loadMappaUsers();
		
		List<edgeModel> edges = this.dao.getAllEdges(n,anno,this.users);
		for (edgeModel e : edges) {
			if (users.values().contains(e.getU1()) && users.values().contains(e.getU2()))
			Graphs.addEdge(this.grafo, e.getU1(), e.getU2(), e.getPeso());
		}
	}
	private void clearGraph() {
		this.grafo = new SimpleWeightedGraph<User,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
	}
	
	public List<User> calcolaSimile(User u) {
		List<User> simili = new ArrayList<User>();
		List<User> adiacenti = new ArrayList<User>(Graphs.neighborListOf(this.grafo, u));
		double gradoMax = 0.0;
		for (User user : adiacenti) {
			DefaultWeightedEdge e = this.grafo.getEdge(u, user);
			if (this.grafo.getEdgeWeight(e)>gradoMax) {
				gradoMax = this.grafo.getEdgeWeight(e);
			}
		}
		for (User user : adiacenti) {
			DefaultWeightedEdge e = this.grafo.getEdge(u, user);
			if (this.grafo.getEdgeWeight(e)==gradoMax) {
				simili.add(user);
			}
		}			
		return simili;
	}

	public List<Integer> getAnni() {
		return anni;
	}
	
	
	
	public Graph<User, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}


	public void loadMappaUsers() {
		this.users = new HashMap<String,User>();
		for (User u : this.grafo.vertexSet()) {
			this.users.put(u.getUserId(), u);
		}
	}
	
	public void simula(int intervistatori, int intervistati) {
		
		Simulatore sim = new Simulatore(this.grafo);
		sim.initialize(intervistatori,intervistati);
		sim.run();
		this.nGiorni = sim.getnGiorni();
		this.giornalisti = new ArrayList<Giornalista>(sim.getGiornalisti());
	}

	public int getnGiorni() {
		return nGiorni;
	}

	public void setnGiorni(int nGiorni) {
		this.nGiorni = nGiorni;
	}

	public List<Giornalista> getGiornalisti() {
		return giornalisti;
	}

	public void setGiornalisti(List<Giornalista> giornalisti) {
		this.giornalisti = giornalisti;
	}
	
	
	
}
