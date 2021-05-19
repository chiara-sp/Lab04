package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {


	public Studente getStudente(int matricola) {
		final String sql="SELECT * FROM studente WHERE matricola=?";
		Studente s= null;
		
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st =conn.prepareStatement(sql);
			st.setInt(1, matricola);
			
			ResultSet res= st.executeQuery();
			if(res.next())
				s= new Studente(matricola,res.getString("cognome"),res.getString("nome"),res.getString("cds"));
			
			conn.close();
			res.close();
			st.close();
			}catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Errore db");
			}
		return s;
	}
	
	public boolean studenteIscrittoCorso(Studente s, Corso c) {
		final String sql="select * from iscrizione i where matricola =? and codins=?";
		boolean value=false;
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st =conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			st.setString(2, c.getCodins());
			
			ResultSet res= st.executeQuery();
			if(res.next()) {
				value=true;
			}
				
			
			conn.close();
			res.close();
			st.close();
			}catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Errore db");
			}
		return value;
		
		
	}
	public List<Corso> getCorsiStudente(int matricola){
		final String sql="select * "
				+ "from corso c, iscrizione i "
				+ "where c.codins=i.codins and i.matricola=?";
		List<Corso> corsi= new LinkedList<>();
		
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st =conn.prepareStatement(sql);
			st.setInt(1, matricola);
			
			ResultSet res= st.executeQuery();
			while(res.next())
				corsi.add(new Corso(res.getString("codins"),res.getInt("crediti"),res.getString("nome"),res.getInt("pd")));
			
			conn.close();
			res.close();
			st.close();
			}catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Errore db");
			}
		return corsi;
	}
	
}
