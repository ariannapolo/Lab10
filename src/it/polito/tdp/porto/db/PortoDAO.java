package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorIdMap;
import it.polito.tdp.porto.model.Coautori;
import it.polito.tdp.porto.model.Paper;
import it.polito.tdp.porto.model.PaperIdMap;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id, AuthorIdMap aimap) {
		if(aimap.get(id)!= null){
			return aimap.get(id);
		}

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				aimap.put(autore);
				conn.close();
				return autore;
				
			}
			conn.close();
			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid, PaperIdMap pimap) {
		if(pimap.getPaper(eprintid)!=null){
			return pimap.getPaper(eprintid);
		}

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				pimap.put(paper);
				conn.close();
				return paper;
			}
			conn.close();
			return null;
			
		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Coautori> creaCoautori(){
		String sql = "select c1.authorid as a1, c2.authorid as a2 from creator c1, creator c2 where c1.eprintid = c2.eprintid and c1.authorid <> c2.authorid";
		List<Coautori> coautori = new ArrayList<Coautori>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Coautori c = new Coautori(rs.getInt("a1"),rs.getInt("a2"));
				coautori.add(c);
				
			}
			conn.close();
			return coautori;
			

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	public List<Author> getAllAuthor(){
		
		final String sql = "SELECT * FROM author";
		List<Author> autori = new ArrayList<Author>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autori.add(autore);
			}
			//System.out.println(autori);
			conn.close();
			return autori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public void setArticoli(PaperIdMap pimap, Author a){
		if(a.getArticoli().size()!=0){
			return;
		}
		String sql = "select * from creator where authorid = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper p = pimap.put(this.getArticolo(rs.getInt("eprintid"),pimap));
				a.addArticolo(p);

			}
			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		
	}
	public void setAutori(AuthorIdMap aimap, Paper p){
		if(p.getAutori().size()!=0){
			return;
		}
		String sql = "select * from creator where eprintid = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, p.getEprintid());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a = aimap.put(this.getAutore(rs.getInt("authorid"),aimap));
				p.addAutore(a);

			}
			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		
	}
	
	public List<Paper> getAllPaper(){
		final String sql = "SELECT * FROM paper";
		List<Paper> papers = new ArrayList<Paper>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				papers.add(paper);
				
			}
			//System.out.println(autori);
			conn.close();
			return papers;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}