package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private StudenteDAO studenteDao;
	private CorsoDAO corsoDao;
	
	public Model() {
		this.corsoDao= new CorsoDAO();
		this.studenteDao= new StudenteDAO();
	}
	
	public List<Corso> listaCorsi(){
		return corsoDao.getTuttiICorsi();
	}
	public Studente getStudente(int matricola) {
		return studenteDao.getStudente(matricola);
	}
	
	public List<Studente> studentiIscrittiCorso(Corso corso){
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	public List<Corso> getCorsiStudente(int matricola){
		return studenteDao.getCorsiStudente(matricola);
	}
	public boolean studenteIscrittoCorso(Studente studente, Corso corso) {
		return studenteDao.studenteIscrittoCorso(studente, corso);
	}

	public boolean iscriviStudenteCorso(Studente studente, Corso corso) {
		return corsoDao.iscriviStudenteCorso(studente,corso);
	}
}
