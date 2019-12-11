/**
 * 
 */
package application;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 200;
	private static final String APP_TITLE = "Social Network";

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// Create a vertical box with Hello labels for each args
		VBox topBox = new VBox();
		VBox leftBox = new VBox();
		Label centralName = new Label("John - " + "5 Friends");

		GridPane gridpane = new GridPane();

		Label labelName = new Label("Name");
		Label labelRemove = new Label("Remove");

		Button name1 = new Button("Aidan");
		Button removeName1 = new Button("remove");
		name1.setMinWidth(90);

		Button name2 = new Button("James");
		Button removeName2 = new Button("remove");
		name2.setMinWidth(90);

		Button name3 = new Button("Zach");
		Button removeName3 = new Button("remove");
		name3.setMinWidth(90);

		Button name4 = new Button("Christian");
		Button removeName4 = new Button("remove");
		name4.setMinWidth(90);

		Button name5 = new Button("Jack");
		Button removeName5 = new Button("remove");
		name5.setMinWidth(90);

		gridpane.add(labelName, 0, 0);
		gridpane.add(labelRemove, 1, 0);
		gridpane.add(name1, 0, 1);
		gridpane.add(removeName1, 1, 1);
		gridpane.add(name2, 0, 2);
		gridpane.add(removeName2, 1, 2);
		gridpane.add(name3, 0, 3);
		gridpane.add(removeName3, 1, 3);
		gridpane.add(name4, 0, 4);
		gridpane.add(removeName4, 1, 4);
		gridpane.add(name5, 0, 5);
		gridpane.add(removeName5, 1, 5);

		gridpane.getColumnConstraints().add(new ColumnConstraints(100));

		// Label friendsLabel = new Label("Friends List");
		topBox.getChildren().add(centralName);
		// leftBox.getChildren().add(friendsLabel);
		leftBox.getChildren().add(gridpane);
		VBox rightBox = new VBox();
		Button clear = new Button("Clear");
		clear.setMinWidth(90);
		Button addUser = new Button("Add user");
		addUser.setMinWidth(90);
		Button searchUser = new Button("Search user");
		searchUser.setMinWidth(90);
		Button removeUser = new Button("Remove user");
		removeUser.setMinWidth(90);
		Button loadFile = new Button("Load File");
		loadFile.setMinWidth(90);
		Button saveChanges = new Button("Save changes");
		saveChanges.setMinWidth(90);
		Button exportNetwork = new Button("Export");
		exportNetwork.setMinWidth(90);
		rightBox.getChildren().add(clear);
		rightBox.getChildren().add(addUser);
		rightBox.getChildren().add(searchUser);
		rightBox.getChildren().add(removeUser);
		rightBox.getChildren().add(loadFile);
		rightBox.getChildren().add(saveChanges);
		rightBox.getChildren().add(exportNetwork);

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the vertical box to the center of the root pane
		root.setCenter(leftBox);
		// root.setLeft(comboBox);
		// root.setBottom(button);
		root.setTop(topBox);
		root.setRight(rightBox);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}