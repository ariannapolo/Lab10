package it.polito.tdp.porto.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorIdMap {
	private Map<Integer,Author> authorId;
	public AuthorIdMap(){
		authorId = new HashMap<>();
	}

	public Author get(int idAuthor){
		return authorId.get(idAuthor);
	}
	
	public Author put( Author author ){
		Author a = authorId.get(author.getId());
		if(a == null){
			authorId.put(author.getId(), author);
			return author;
		}
		return a;
	}
	
	public Collection<Author> getMapVaues(){
		
		return authorId.values();
	}
}
