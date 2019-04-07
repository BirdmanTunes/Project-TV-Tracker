package application.controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import application.EditCell;
import application.Series;

import javafx.animation.PauseTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;


public class Tab3Controller {
	
	private PauseTransition delay = new PauseTransition(Duration.millis(3000));
	private static final String PATH2 = System.getProperty("user.dir") + "/fellbehind.json";
	private static final String PATH = "fellbehind.json";
	private static final Type TYPE = new TypeToken<List<Series>>() {}.getType();
	private static final Gson GSON = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private static ObservableList<Series> list;
	private MainController mainController;
	
	@FXML private TableView<Series> table;
	@FXML private TableColumn<Series, String> name, season, episode, date, comment;
	@FXML private Button save, add, delete, ongoing, fellBehind, seasonEnded, endedForGood;
	@FXML private Label message;
	@FXML private TextField nameField, seasonField, episodeField, dateField, commentField;
	
	@FXML
	public void initialize() throws IOException {
		editFields();
		readFile();
		table.setItems(list);
		checkIfTextThere();
	}
	
	public static ObservableList<Series> getList(){
		return list;
	}
	
	public static String getPath() {
		return PATH2;
	}
	
	
	// injects the main controller into this class, not really useful right now but kept as an option
    public void injectMainController(MainController mainController){
        this.mainController = mainController;
    }
	
    // saves all the information changed in all tabs simultaneously by pressing save on any page
	public void saveToAllTabs() throws Exception {
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		saveLoop(Tab2Controller.getList(), Tab2Controller.getPath());
		saveLoop(list, PATH2);
		saveLoop(Tab4Controller.getList(), Tab4Controller.getPath());
		saveLoop(Tab5Controller.getList(), Tab5Controller.getPath());
		saveLoop(Tab6Controller.getList(), Tab6Controller.getPath());
		message.setText("         Saved to file.");
		delay.play();
	}
	
	// the loop used for the universal save button, it parses the table information into a json file
	public void saveLoop(ObservableList<Series> list, String path) throws IOException {
		FileWriter writer = new FileWriter(path);
		List<Series> target2 = new LinkedList<Series>();
		target2 = list;
		GSON.toJson(target2, TYPE, writer);
		writer.close();
	}
	
	// this reads from an external json file and parses it into the table
	public void readFile() throws IOException {
		FileReader reader = new FileReader(PATH2);
		List<Series> target = GSON.fromJson(reader, new TypeToken<List<Series>>(){}.getType());
		list = FXCollections.observableArrayList(target);
		reader.close();
	}
	
	// adds a new series to the current list and resets all fields to empty
	public void add() {
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		list.add(new Series(nameField.getText(), seasonField.getText(), episodeField.getText(), dateField.getText(), commentField.getText()));
		Arrays.asList(nameField, seasonField, episodeField, dateField, commentField).forEach(field -> field.setText(""));
		message.setText("         Series added.");
		delay.play();
	}
	
	// deletes a series from the list
	public void delete() {
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		ObservableList<Series> selectedSeries, allSeries;
		allSeries = table.getItems();
		selectedSeries = table.getSelectionModel().getSelectedItems();
		selectedSeries.forEach(allSeries::remove);
		message.setText("         Series deleted.");
		delay.play();
	}
	
	// moves the current selected them the current list to the Ongoing list
	public void toOngoing(){
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		ObservableList<Series> selectedSeries;
		selectedSeries = table.getSelectionModel().getSelectedItems();
		selectedSeries.forEach(Tab2Controller.getList()::add);
		delete();
		message.setText("      Sent to Ongoing.");
		delay.play();
	}
	
	// moves the current selected them the current list to the to Be Started list
	public void toBeStarted(){
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		ObservableList<Series> selectedSeries;
		selectedSeries = table.getSelectionModel().getSelectedItems();
		selectedSeries.forEach(Tab4Controller.getList()::add);
		delete();
		message.setText("    Sent to Be Started.");
		delay.play();
	}
	
	// moves the current selected them the current list to the Season Ended list
	public void toSeasonEnded(){
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		ObservableList<Series> selectedSeries;
		selectedSeries = table.getSelectionModel().getSelectedItems();
		selectedSeries.forEach(Tab5Controller.getList()::add);
		delete();
		message.setText("Sent to Season Ended.");
		delay.play();
	}
	
	// moves the current selected them the current list to the Ended for Good list
	public void toEndedForGood(){
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		ObservableList<Series> selectedSeries;
		selectedSeries = table.getSelectionModel().getSelectedItems();
		selectedSeries.forEach(Tab6Controller.getList()::add);
		delete();
		message.setText("Sent to Ended for Good.");
		delay.play();
	}
	
	// boolean bind method used to check if there is any text in the first fields to prevent empty entires
	public void checkIfTextThere() {
		BooleanBinding bb = new BooleanBinding() {
		    {
		        super.bind(nameField.textProperty(),
		        		seasonField.textProperty(),
		        		episodeField.textProperty());
		    }

		    @Override
		    protected boolean computeValue() {
		        return (nameField.getText().isEmpty()
		                || seasonField.getText().isEmpty()
		                || episodeField.getText().isEmpty());
		    }
		};
		add.disableProperty().bind(bb);
	}
	
	// sets the factory values for the cells allowing them to commit changes when clicking out and editing in general
	public void setfactory(TableColumn<Series, String> name, Function<Series, StringProperty> property) {
        name.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        name.setCellFactory(column -> EditCell.createStringEditCell());
	}
	
	// sets the factory values to all the individual columns
	public void editFields() {
		setfactory(name, Series::nameProperty);
		setfactory(season, Series::seasonProperty);
		setfactory(episode, Series::episodeProperty);
		setfactory(date, Series::dateProperty);
		setfactory(comment, Series::commentProperty);
	}

	
}
