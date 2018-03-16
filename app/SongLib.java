// Name:		Liam Davies,		Kevin Lee
// netid:		lmd312,				kjl156

package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import view.SongLibController;


public class SongLib extends Application {

	@Override
	public void stop() throws IOException {
		File file = new File("src/logs/ListofSong.txt");
		@SuppressWarnings("resource")
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for(int i = 0; i < SongLibController.getObList().size(); i++) {
			String[] temp = SongLibController.getObList().get(i).toArr();
			for(int x = 0; x < 4; x++) {
				out.write(temp[x]);
				out.write("\t");
			}
			out.write("\n");
		}
		out.flush();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load FXML file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/SongLib.fxml"));

		GridPane root = (GridPane)loader.load();

		SongLibController slc = loader.getController();
		slc.start(primaryStage);

		// Set up the scene
		Scene scene = new Scene(root,605,500);
		primaryStage.setScene(scene);

		// Other options
		primaryStage.setTitle("SongLib");

		// Show the scene
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
