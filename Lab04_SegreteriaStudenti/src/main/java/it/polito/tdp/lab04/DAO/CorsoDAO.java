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

public class CorsoDAO {
	
	Map<String,Corso> mapCorsi;
	
	public CorsoDAO() {
		mapCorsi= new HashMap<>();
	}
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

			//	String codins = rs.getString("codins");
			//	int numeroCrediti = rs.getInt("crediti");
			//	String nome = rs.getString("nome");
			//	int periodoDidattico = rs.getInt("pd");

			//	System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso c= new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
				corsi.add(c);
				mapCorsi.put(rs.getString("codins"), c);
			}

			conn.close();
			st.close();
			rs.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql="select s.matricola, s.cognome, s.nome, s.cds "
				+ "from iscrizione i, studente s "
				+ "where s.`matricola`=i.matricola and i.codins=?";
		List<Studente> result= new LinkedList<Studente>();
		
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs= st.executeQuery();
			
			
			while(rs.next()) {
				result.add(new Studente(rs.getInt("matricola"),rs.getString("cognome"),rs.getString("nome"),rs.getString("cds")));
			}
			conn.close();
			rs.close();
			st.close();
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	

	public boolean iscriviStudenteCorso(Studente studente, Corso corso) {
		String sql="INSERT IGNORE INTO iscritticorsi.iscrizione(matricola, codins) VALUES (?,?)";
		boolean value=false;
		
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
		    int res = st.executeUpdate();
			
			if(res==1)
				return true;
			
			conn.close();
			st.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return value;
	}

}
