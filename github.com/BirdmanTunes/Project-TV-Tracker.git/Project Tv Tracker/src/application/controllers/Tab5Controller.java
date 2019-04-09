package application.controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
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


public class Tab5Controller {
	
	private PauseTransition delay = new PauseTransition(Duration.millis(3000));
	private static final String PATH2 = System.getProperty("user.dir") + "/seasonended.json";
	private static final String PATH = "seasonended.json";
	private static final Type TYPE = new TypeToken<List<Series>>() {}.getType();
	private static final Gson GSON = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private static ObservableList<Series> list;
	private MainController mainController;
	
	@FXML private TableView<Series> table;
	@FXML private TableColumn<Series, String> name, season, episode, date, comment;
	@FXML private Button save, add, delete, ongoing, fellBehind, toBeStarted, endedForGood;
	@FXML private Label message;
	@FXML private TextField nameField, seasonField, episodeField, dateField, commentField;
	
	@FXML
	public void initialize() throws IOException {
		editFields();
		readFile();
		table.setItems(list);
		checkIfTextThere();
		
		addToNewsList();
		Tab1Controller.getList2().values().forEach(Tab1Controller.getList()::add);
	}
	
	// parses data from Season Ended list and counts the number of days between the ones in the list and the curent
	// date then adds the days to a reversed TreeMap to notify the user of upcoming show seasons or new seasons
	public void addToNewsList() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		LocalDate localDate = LocalDate.now();
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy.MM.dd");

		String localCurrentDate = dtf.format(localDate);
		
		int days = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDate().length() > 0) {
				try {
				    Date date1 = myFormat.parse(list.get(i).getDate());
				    Date date2 = myFormat.parse(localCurrentDate);
				    long diff = date2.getTime() - date1.getTime();
				    days = (int)(diff / (1000*60*60*24));
				} catch (ParseException e) {
				    e.printStackTrace();
				}
				if (days>358)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": New season next week!");
				if (days>335)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": New Season next month!");
				if (days>365)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 1 New season out!");
				if (days>730)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 2 New seasons out!");
				if (days>1095)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 3 New seasons out!");
				if (days>1460)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 4 New seasons out!");
				if (days>1825)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 5 New seasons out!");
				if (days>2190)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 6 New seasons out!");
				if (days>2555)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 7 New seasons out!");
				if (days>2920)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 8 New seasons out!");
				if (days>3285)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 9 New seasons out!");
				if (days>3650)
				Tab1Controller.getList2().put(days, list.get(i).getName() + ": 10 New seasons out!");
			}
		}
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
		saveLoop(Tab3Controller.getList(), Tab3Controller.getPath());
		saveLoop(Tab4Controller.getList(), Tab4Controller.getPath());
		saveLoop(list, PATH2);
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
	
	// moves the current selected them the current list to the Fell Behind list
	public void toFellBehind(){
		delay.setOnFinished(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent t) {
	            message.setText("");
	        }
	    });
		ObservableList<Series> selectedSeries;
		selectedSeries = table.getSelectionModel().getSelectedItems();
		selectedSeries.forEach(Tab3Controller.getList()::add);
		delete();
		message.setText("    Sent to Fell Behind.");
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
