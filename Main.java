/**
 * 
 */
package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 250;
	private static final String APP_TITLE = "Social Network";

	private static NetworkGraph network;
	
	private static Person centralUser;

	public void clearDialog(Stage primaryStage) throws Exception {
		Stage dialog = new Stage();
		TextField userName = new TextField("Enter name");
		dialog.initOwner(primaryStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	public void addUserDialog(Stage primaryStage) throws Exception {
		Stage dialog = new Stage();
		TextField userName = new TextField("Enter name");
		dialog.initOwner(primaryStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	public void searchUserDialog(Stage primaryStage) throws Exception {
		Stage dialog = new Stage();
		TextField userName = new TextField("Enter name");
		dialog.initOwner(primaryStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	public void removeDialog(Stage primaryStage) throws Exception {
		Dialog<String> dialog = new Dialog();
		TextField userName = new TextField("Enter name");
		userName.setOnAction(e -> {
			try {
				removeHandler(userName.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		dialog.initOwner(primaryStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
	public void removeHandler(String name) throws Exception {
		network.removePerson(name);
		System.out.println("Success");
	}

	public void loadDialog(Stage primaryStage) throws Exception {

	}

	public void saveDialog(Stage primaryStage) throws Exception {

	}
	
	public void exportDialog(Stage primaryStage) throws Exception {
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// Create a vertical box with Hello labels for each args
		try {
		VBox topBox = new VBox();
		VBox leftBox = new VBox();
		Label centralName = new Label("John - " + "5 Friends");

		GridPane gridpane = new GridPane();
		ArrayList<String> names = new ArrayList<String>();
		//TODO: Change to current user and friends
		names.add("Aidan");
		names.add("Jack");
		names.add("Christian");
		names.add("Zach");
		names.add("James");
		for(int i = 0; i < names.size(); i++) {
			Button name = new Button(names.get(i));
			name.setOnAction(action -> {
				name.setText("clicked");
			});
			name.setMinWidth(90);
			name.setMaxWidth(90);
			gridpane.add(name, 0, i + 1);
			Button remove = new Button("remove");
			remove.setOnAction(action -> {
				try {
					removeHandler("Aidan");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			gridpane.add(remove, 1, i + 1);
		}
		Label labelName = new Label("Name");
		Label labelRemove = new Label("Remove");
		/*
		Button name1 = new Button("Aidan");
		Button removeName1 = new Button("remove");
		
		name1.setMinWidth(90);
		name1.setMaxWidth(90);
		name1.setOnAction(action -> {
			name1.setText("clicked");
		});
		removeName1.setOnAction(action -> {
			try {
				removeHandler("Aidan");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Button name2 = new Button("James");
		Button removeName2 = new Button("remove");
		name2.setMinWidth(90);
		name2.setMaxWidth(90);
		Button name3 = new Button("Zach");
		Button removeName3 = new Button("remove");
		name3.setMinWidth(90);
		name3.setMaxWidth(90);
		Button name4 = new Button("Christian");
		Button removeName4 = new Button("remove");
		name4.setMinWidth(90);
		name4.setMaxWidth(90);
		Button name5 = new Button("Jack");
		Button removeName5 = new Button("remove");
		name5.setMinWidth(90);
		name5.setMaxWidth(90); */
		gridpane.add(labelName, 0, 0);
		gridpane.add(labelRemove, 1, 0);
		/*
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
		*/
		gridpane.getColumnConstraints().add(new ColumnConstraints(100));

		// Label friendsLabel = new Label("Friends List");
		topBox.getChildren().add(centralName);
		// leftBox.getChildren().add(friendsLabel);
		leftBox.getChildren().add(gridpane);
		VBox rightBox = new VBox();
		Button clear = new Button("Clear");
		clear.setMinWidth(110);
		Button addUser = new Button("Add user");
		addUser.setMinWidth(110);
		Button searchUser = new Button("Search user");
		searchUser.setMinWidth(110);
		Button removeUser = new Button("Remove user");
		removeUser.setMinWidth(110);
		Button loadFile = new Button("Load File");
		loadFile.setMinWidth(110);
		Button saveChanges = new Button("Save changes");
		saveChanges.setMinWidth(110);
		Button exportNetwork = new Button("Export");
		exportNetwork.setMinWidth(110);
		Button shortestPath = new Button("Find shortest path");
		shortestPath.setMinWidth(110);
		rightBox.getChildren().add(clear);
		rightBox.getChildren().add(addUser);
		rightBox.getChildren().add(searchUser);
		rightBox.getChildren().add(removeUser);
		rightBox.getChildren().add(loadFile);
		rightBox.getChildren().add(saveChanges);
		rightBox.getChildren().add(exportNetwork);
		rightBox.getChildren().add(shortestPath);
		

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
		} catch (Exception e) {
			System.out.println("Exception!!!");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		network = new NetworkGraph();
		launch(args);
	}
}