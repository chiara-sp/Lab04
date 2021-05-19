package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private List<Corso> corsi;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> boxCorsi;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCerca;
    
    @FXML
    private CheckBox btnCheckIscritto;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;
    
    @FXML
    void doCercaStudente(ActionEvent event) {

    	txtResult.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	try {
    		int matricola = Integer.parseInt(txtMatricola.getText());
    		Studente studente= model.getStudente(matricola);
    		
    		
    		if(studente==null) {
    			txtResult.appendText("Nessun Risultato: matricola inesistente");
    			return;
    		}
    		txtNome.setText(studente.getNome());
    		txtCognome.setText(studente.getCognome());
    	}catch(NumberFormatException ne) {
    		txtResult.setText("inserire una matricola nel formato corretto");
    	}
    }

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	try {
    		int matricola= Integer.parseInt(txtMatricola.getText());
    		
    		Studente studente= model.getStudente(matricola);
    		if(studente== null) {
    			txtResult.appendText("matricola non presente");
    			return;
    		}
    		List<Corso> corsi= model.getCorsiStudente(matricola);
    		
    		StringBuilder sb= new StringBuilder();
    		for(Corso c: corsi) {
    			sb.append(String.format("%-8s", c.getCodins()));
    			sb.append(String.format("%-4s", c.getCrediti()));
    			sb.append(String.format("%-45s", c.getNome()));
    			sb.append(String.format("%-4s", c.getPd()));
    			sb.append("\n");
    		}
    		txtResult.setText(sb.toString());
    	}catch(NumberFormatException e) {
    		txtResult.setText("inserire matricola nel formato corretto");
    	}catch(RuntimeException e) {
    		txtResult.setText("errore di connessione al database");
    	}

    }

    @FXML
    void doCercaIscritti(ActionEvent event) {

    	txtResult.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	try {
    		Corso corso= this.boxCorsi.getValue();
    		if(corso==null) {
    			txtResult.setText("selezionare un corso");
    			return;
    		}
    		List<Studente> studenti= model.studentiIscrittiCorso(corso);
    		
    		StringBuilder sb = new StringBuilder();
    		
    		for(Studente s: studenti) {
    			sb.append(String.format("%-10s", s.getMatricola()));
    			sb.append(String.format("%-20s", s.getCognome()));
    			sb.append(String.format("%-20s", s.getNome()));
    			sb.append(String.format("%-10s", s.getCds()));
    			sb.append("\n");
    		}
    		txtResult.appendText(sb.toString());
    	}catch(RuntimeException e) {
    		txtResult.setText("errore di connessione al database");
    	}
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
    	
    	if(txtMatricola.getText().isEmpty()) {
    		txtResult.setText("inserire matricola");
    		return;
    	}
    		
    	if(this.boxCorsi.getValue()==null) {
    		txtResult.setText("selezionare un corso");
    		return;
    	}
    	int matricola= Integer.parseInt(txtMatricola.getText());
    	
    	Studente studente= model.getStudente(matricola);
    	if(studente==null) {
    		txtResult.appendText("matricola inesistente");
    		return;
    	}
    	txtNome.setText(studente.getNome());
    	txtCognome.setText(studente.getCognome());
    	Corso corso= boxCorsi.getValue();
    	
    	if(model.studenteIscrittoCorso(studente, corso)) {
    		txtResult.appendText("studente già iscritto al corso");
    		return;
    	}
    	/*else {
    		txtResult.setText("studente non iscritto al corso");
    		return;
    	}*/
    	
    	if(!model.iscriviStudenteCorso(studente,corso)) {
    		txtResult.appendText("Errore durante l'isrizione al corso");
    		return;
    	}else {
    		txtResult.appendText("studente iscritto al corso!");
    	}
    	}catch(NumberFormatException e) {
    		txtResult.appendText("inserire la matricola nel formato corretto");
    		return;
    	}catch(RuntimeException e) {
    		txtResult.appendText("errore di connessione al database");
    	}
    		

    }

    @FXML
    void doReset(ActionEvent event) {

    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtCognome.clear();
    	this.txtResult.clear();
    }
    
 
    private void setComboItems() {
    	corsi= model.listaCorsi();
    	this.boxCorsi.getItems().addAll(corsi);
    }

    @FXML
    void initialize() {
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    } 
    public void setModel(Model model) {
    	this.model=model;
    	setComboItems();
    	//corsi= model.listaCorsi();
    	//this.boxCorsi.getItems().addAll(corsi);
    }
}
