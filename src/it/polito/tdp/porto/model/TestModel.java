package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		//System.out.println(model.creaGrafo());
		System.out.println(model.getAllAuthor().toArray()[0]);
		System.out.println(model.getListaCoautori((Author) model.getAllAuthor().toArray()[0]));
		
		Author a1 = new Author(4096,"Martina", "Maurizio");
		Author a2 = new Author(18432,"Casalino","Matteo Maria");
		System.out.println(model.getAllAuthor().contains(a2));
		System.out.println(model.creaSequenzaArticoliOM(a1, a2));
		
//		Author a = new Author(12,"Rossi","Mario");
//		AuthorIdMap aimap = new AuthorIdMap();
//		System.out.println(aimap.put(a));
	}

}
