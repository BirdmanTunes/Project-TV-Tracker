package application.controllers;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Tab1Controller{
	
	@FXML
	private ListView<String> newsList;
	@FXML
	private static ObservableList<String> list= FXCollections.observableArrayList();
	static HashMap<Integer, String> list2 = new HashMap<Integer, String>();
	
	@FXML
	public void initialize() {
		newsList.setItems(list);
    }
	
	public static ObservableList<String> getList(){
		return list;
	}
	
	public static HashMap<Integer, String> getList2(){
		return list2;
	}
	
	
}