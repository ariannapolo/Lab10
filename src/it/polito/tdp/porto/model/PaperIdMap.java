package it.polito.tdp.porto.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PaperIdMap {
	private Map<Integer, Paper> paperIdMap;

	public PaperIdMap() {
		super();
		paperIdMap = new HashMap<Integer,Paper>();
	}
	
	public Paper put(Paper paper){
		Paper p = paperIdMap.get(paper.getEprintid());
		if(p==null){
			paperIdMap.put(paper.getEprintid(), paper);
			return paper;
		}else
			return p;
	}
	
	public Paper getPaper(int idPaper){
		return paperIdMap.get(idPaper);
	}

	public Collection<Paper> getMapVaues(){
		
		return paperIdMap.values();
	}
}
