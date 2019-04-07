package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Series {

	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty season = new SimpleStringProperty();
	private final StringProperty episode = new SimpleStringProperty();
	private final StringProperty date = new SimpleStringProperty();
	private final StringProperty comment = new SimpleStringProperty();

	public Series(String name, String season, String episode, String date, String comment) {
		setName(name);
		setSeason(season);
		setEpisode(episode);
		setDate(date);
		setComment(comment);
	}
	
    public final StringProperty nameProperty() {
        return name;
    }

    public final java.lang.String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final java.lang.String name) {
        this.nameProperty().set(name);
    }
    
	public StringProperty seasonProperty() {
		return season;
	}
	
    public final java.lang.String getSeason() {
        return this.seasonProperty().get();
    }

    public final void setSeason(final java.lang.String season) {
        this.seasonProperty().set(season);
    }

	public StringProperty episodeProperty() {
		return episode;
	}
	
    public final java.lang.String getEpisode() {
        return this.episodeProperty().get();
    }

    public final void setEpisode(final java.lang.String episode) {
        this.episodeProperty().set(episode);
    }

	public StringProperty dateProperty() {
		return date;
	}
	
    public final java.lang.String getDate() {
        return this.dateProperty().get();
    }

    public final void setDate(final java.lang.String date) {
        this.dateProperty().set(date);
    }

	public StringProperty commentProperty() {
		return comment;
	}
	
    public final java.lang.String getComment() {
        return this.commentProperty().get();
    }

    public final void setComment(final java.lang.String comment) {
        this.commentProperty().set(comment);
    }

	

	public String toString() {
		if (comment.equals("") && date.equals(""))
			return name + " - " + season + " - " + episode;
		else if (comment.equals(""))
			return name + " - " + season + " - " + episode + " - " + date;
		else if (date.equals("")) 
			return name + " - " + season + " - " + episode + " -- " + comment;
		else
			return name + " - " + season + " - " + episode + " - " + date + " - " + comment;
			
		
	}

}
