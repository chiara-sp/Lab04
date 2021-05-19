package it.polito.tdp.lab04.DAO;

public class TestDB {

	public static void main(String[] args) {

		/*
		 * 	This is a main to check the DB connection
		 */
		
		CorsoDAO cdao = new CorsoDAO();
		StudenteDAO sdao= new StudenteDAO();
		cdao.getTuttiICorsi();
		//System.out.println(sdao.getStudente(146101));
		//System.out.print(sdao.studenteIscrittoCorso(146101, null));
		
	}

}
