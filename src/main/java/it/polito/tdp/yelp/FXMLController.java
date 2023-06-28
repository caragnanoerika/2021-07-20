/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Giornalista;
import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	try {
    		Integer n = Integer.parseInt(this.txtN.getText());
    		if(n>0) {
    			if(this.cmbAnno.getValue()!=null) {
    				this.model.creaGrafo(n, this.cmbAnno.getValue());
    				this.txtResult.setText("Grafo creato!");
    				this.txtResult.appendText("Archi: " + this.model.getGrafo().vertexSet().size() + "\n");
    				this.txtResult.appendText("Vertici: " + this.model.getGrafo().edgeSet().size() + "\n");
    				
    				this.cmbUtente.getItems().setAll(this.model.getGrafo().vertexSet());
    			}else {
    				this.txtResult.setText("Per favore, selezionare l'anno dal menu a tendina.");
    			}
    		}else {
    			this.txtResult.setText("Formato del numero di recensioni non valido. Deve essere un intero positivo.");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Formato del numero di recensioni non valido. Deve essere un intero positivo.");
    	}
    }

    @FXML
    void doUtenteSimile(ActionEvent event) {

    	if(this.cmbUtente.getValue()!=null) {
    		User u = this.cmbUtente.getValue();
    		List<User> simili = new ArrayList<>(this.model.calcolaSimile(u));
    		this.txtResult.appendText("\n\nUtenti più simili a " + u.getName());
    		for (User user : simili) {
    			double grado = this.model.getGrafo().getEdgeWeight(this.model.getGrafo().getEdge(u,user));
    			this.txtResult.appendText("\n" + user.getName() + " : " + grado );
    		}
    	}else {
    		this.txtResult.setText("Prego, selezionare un utente dal menu a tendina.");
    	}
    }
    
    @FXML
    void doSimula(ActionEvent event) {

    	try {
    		Integer nIntervistatori = Integer.parseInt(this.txtX1.getText());
    		Integer nIntervistati = Integer.parseInt(this.txtX2.getText());
    		this.model.simula(nIntervistatori, nIntervistati);
    		
        	this.txtResult.appendText("\n\nSimulazione a eventi con " + nIntervistatori + " giornalisti e " + nIntervistati + " utenti:");
        	this.txtResult.appendText("Numero giorni: " + this.model.getnGiorni());
        	for (Giornalista g : this.model.getGiornalisti()) {
        		this.txtResult.appendText("\ngiornalista " + g.getID() + " ha intervistato " + g.getNumeroIntervistati() + " utenti");
        	}
    	}catch(NumberFormatException e ) {
    		this.txtResult.appendText("Metti un numero valido");
    	}
    	
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbAnno.getItems().setAll(this.model.getAnni());
    }
}
