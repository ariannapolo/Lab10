package it.polito.tdp.porto;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Coautori;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
   
    @FXML
    private Button btnCoautori;

    @FXML
    private Button btnSequenzaArticoli;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	List<Author> coautori = model.getListaCoautori(boxPrimo.getValue());
    	txtResult.setText(coautori.toString()+"\n");
    	Collection<Author> autori2 = model.getAllAuthor();
    	autori2.remove(boxPrimo.getValue());
    	autori2.removeAll(coautori);
    	boxSecondo.getItems().addAll(autori2);
    	

    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	List<Paper> l = model.creaSequenzaArticoliOM(boxPrimo.getValue(), boxSecondo.getValue());
    	
    	if(l==null){
    		txtResult.setText("Non c'è nessun cammino tra i due nodi");
    	}
    	for(Paper p : l )
    		txtResult.appendText(p.toString()+"\n");

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnCoautori != null : "fx:id=\"btnCoautori\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnSequenzaArticoli != null : "fx:id=\"btnSequenzaArticoli\" was not injected: check your FXML file 'Porto.fxml'.";
    }
    
    public void sedModel(Model model){
    	this.model = model;
    	boxPrimo.getItems().addAll(model.getAllAuthor());
    }
}
