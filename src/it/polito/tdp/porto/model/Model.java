package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private SimpleGraph<Author, DefaultEdge> graph;
	private AuthorIdMap aimap = new AuthorIdMap();
	private PaperIdMap pimap = new PaperIdMap();
	
	
	public SimpleGraph<Author, DefaultEdge> creaGrafo(){
		if(graph != null){
			return graph;
		}
		graph = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		PortoDAO dao = new PortoDAO();
		
		Graphs.addAllVertices(graph, this.getAllAuthor());
		List<Coautori> coautori =  dao.creaCoautori();
		for(Coautori c : coautori){
			graph.addEdge(aimap.get(c.getA1()),aimap.get(c.getA2()));
			//System.out.println(graph);
		}
		return graph;
	}
	
	public Collection<Author> getAllAuthor(){
		if(aimap.getMapVaues().size()!=0){
			return aimap.getMapVaues();
		}
		PortoDAO dao = new PortoDAO();
		for(Author a : dao.getAllAuthor()){
			aimap.put(a);
			dao.setArticoli(pimap, a);
		}
		return aimap.getMapVaues();
	}
	public Collection<Paper> getAllPaper(){
		if(pimap.getMapVaues().size()!=0){
			return pimap.getMapVaues();
		}
		PortoDAO dao = new PortoDAO();
		for(Paper p: dao.getAllPaper()){
			pimap.put(p);
		}
		return pimap.getMapVaues();
	}
	public List<Author> getListaCoautori(Author autore){
		this.creaGrafo();
		List<Author> coautori = new ArrayList<Author>();
		coautori = Graphs.neighborListOf(graph, autore);
		return coautori;
	}

	public List<Paper> creaSequenzaArticoliOM(Author a1, Author a2){
		this.creaGrafo();
		this.getAllAuthor();
		this.getAllPaper();
		DijkstraShortestPath<Author,DefaultEdge> percorsoMinimo = new DijkstraShortestPath<Author,DefaultEdge>(graph, a1, a2);
		GraphPath<Author, DefaultEdge> percorso = percorsoMinimo.getPath();
		System.out.println(a1+" "+a2+" "+percorso);
		if(percorso == null){
			return null;
		}
		
		List<Paper> sequenza = new ArrayList<Paper>();
		
		for(DefaultEdge e : percorso.getEdgeList()){
			boolean trovato = false;
			for(Paper p: graph.getEdgeSource(e).getArticoli()){
				if(graph.getEdgeTarget(e).getArticoli().contains(p) && trovato == false){
					sequenza.add(p);
					trovato = true;
				}
					
			}
		}
		return sequenza;
	}
}
