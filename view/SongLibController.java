// Name:		Liam Davies,		Kevin Lee
// netid:		lmd312,				kjl156

package view;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

public class SongLibController {
	@FXML TextField songE;
	@FXML TextField artistE;
	@FXML TextField albumE;
	@FXML TextField yearE;

	@FXML TextField songA;
	@FXML TextField artistA;
	@FXML TextField albumA;
	@FXML TextField yearA;

	@FXML Button edit;
	@FXML Button delete;
	@FXML Button add;

	@FXML ListView<Song> library;

	private static ObservableList<Song> obsList;

	public static ObservableList<Song> getObList() {
		return obsList;
	}

	public void fillList() throws IOException {
		File file = new File("src/logs/ListofSong.txt");
		File dir = new File("src/logs");
		if(!dir.exists()) {
			dir.mkdirs();
			file.createNewFile();
			return;
		}
		if (!file.exists()) {
			file.createNewFile();
			return;
		}
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()){
		    String line = scanner.nextLine();
		    if (line.isEmpty()) {
		        continue;
		    }
		    String [] newToken = new String[] {"", "", "", ""};
		    String[] tokens = line.split("\\t");
		    for (String token : tokens) {
		        if (token.isEmpty()) {
		        		continue;
		        }
		    }

		    	for(int x = 0; x < tokens.length; x++) {
		    		newToken[x] = tokens[x];
		    	}

			obsList.add(new Song(newToken[0], newToken[1], newToken[2], newToken[3]));
		}
		scanner.close();
	}

	public void start(Stage mainStage) throws IOException {
		// create an ObservableList
		obsList = FXCollections.observableArrayList();

		//Populate the list
		fillList();

		// Commit the list
		library.setItems(obsList);

		// Pre-Select and display info for an item if one exists
		if(obsList.size() > 0) {
			library.getSelectionModel().select(0);

			displayInfo();
		}
	}

	public void displayInfo() {
		// Get the selected song
		Song s = library.getSelectionModel().getSelectedItems().get(0);

		// Display the info
		songE.setText(s.song);
		artistE.setText(s.artist);
		albumE.setText(s.album);
		yearE.setText(s.year);
	}

	public void add() {
		// Ensure Song and Artist fields are filled
		if(songA.getText().trim().equals("") || artistA.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR, "Add: The song must have at least a song title and artist.", ButtonType.OK);
			alert.showAndWait();
			return;
		}

		// Check if Year is an integer
		String year = yearA.getText().trim();
		for(int i = 0; i < year.length(); i++) {
			if(!Character.isDigit(year.charAt(i))) {
				Alert alert = new Alert(AlertType.ERROR, "Add: The year must consist of only numeric characters.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		}

		// Create the new song
		Song s = new Song(songA.getText().trim(), artistA.getText().trim(), albumA.getText().trim(), yearA.getText().trim());

		// Check if the song is valid
		for(int i = 0; i < obsList.size(); i++) {
			Song temp = obsList.get(i);
			if(temp.equals(s)) {
				Alert alert = new Alert(AlertType.ERROR, "Cannot add a song with the same artist and song title.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		}

		// Get confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION, "Add " + s.toString() + "?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.NO)
			return;

		// Add the song to the list
		obsList.add(s);

		// Commit the list
		library.setItems(obsList);

		// Clear the info
		songA.setText("");
		artistA.setText("");
		albumA.setText("");
		yearA.setText("");

		// Check if this is only song - display info if so
		if(obsList.size() == 1) {
			library.getSelectionModel().select(0);

			displayInfo();
		}
	}

	public void delete() {
		// Check if they can delete anything
		if(obsList.size() < 1) {
			Alert alert = new Alert(AlertType.ERROR, "No songs to delete.", ButtonType.OK);
			alert.showAndWait();
			return;
		}

		// Get the song
		Song s = library.getSelectionModel().getSelectedItems().get(0);

		// Get confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + s.toString() + "?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.NO)
		    return;

		// Remove the song from the list
		obsList.remove(s);

		// Commit the list
		library.setItems(obsList);

		// Pre-Select and display info for an item if one exists
		if(obsList.size() > 0) {
			library.getSelectionModel().select(0);

			displayInfo();
		} else {
			// Clear the info
			songE.setText("");
			artistE.setText("");
			albumE.setText("");
			yearE.setText("");
		}
	}

	public void edit() {
		// Ensure Song and Artist fields are filled
		if(songE.getText().trim().equals("") || artistE.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR, "Edit: The song must have at least a song title and artist.", ButtonType.OK);
			alert.showAndWait();
			return;
		}

		// Check if Year is an integer
		String year = yearE.getText().trim();
		for(int i = 0; i < year.length(); i++) {
			if(!Character.isDigit(year.charAt(i))) {
				Alert alert = new Alert(AlertType.ERROR, "Edit: The year must consist of only numeric characters.", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		}

		// Check if they can edit anything
		if(obsList.size() < 1) {
			Alert alert = new Alert(AlertType.ERROR, "No songs to edit.", ButtonType.OK);
			alert.showAndWait();
			return;
		}

		// Get the selected song
		Song s = library.getSelectionModel().getSelectedItems().get(0);

		// Create updated version
		Song newS = new Song(songE.getText().trim(), artistE.getText().trim(), albumE.getText().trim(), yearE.getText().trim());

		// Check if the new info is valid
		for(int i = 0; i < obsList.size(); i++) {
			Song temp = obsList.get(i);
			if(temp.equals(newS) && !(temp == s)) {
				Alert alert = new Alert(AlertType.ERROR, "Cannot edit a song to be the same as an existing artist and song title.",
											ButtonType.OK);
				alert.showAndWait();
				return;
			}
		}

		// Get confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION, "Edit " + s.toString() + "?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.NO)
			return;

		// Edit the song on the list
		obsList.remove(s);

		obsList.add(newS);

		// Commit the list
		library.setItems(obsList);

		// Select and Display the editied song
		library.getSelectionModel().select(newS);

		displayInfo();
	}

}
