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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

	private static ArrayList<String> commandLog;
	
	/*
	 * Dialog to take in user input, 
	 */
	public void clearHandler(Stage primaryStage) throws Exception {
		//Do you want to clear the network?
		Alert clear = new Alert(AlertType.CONFIRMATION);
		clear.setHeaderText("Do you want to clear the network?");
		Button ok = (Button)clear.getDialogPane().lookupButton(ButtonType.OK);
		Button cancel = (Button)clear.getDialogPane().lookupButton(ButtonType.CANCEL);
		ok.setOnAction(action -> {
			network.clearNetwork();
			centralUser = null;
			clear.close();
		});
		cancel.setOnAction(action -> {
			//close dialog, do nothing
			clear.close();
		});
		clear.show();
		start(primaryStage);
	}

	/*
	 * Dialog to take in user input, and add a new user
	 */
	public void addUserDialog(Stage primaryStage) throws Exception {
		TextInputDialog addDialog = new TextInputDialog("Enter name");
		addDialog.setTitle("Add a user");
		addDialog.show();
		addDialog.getEditor().setOnAction(event -> { //user hits enter key
			String result = addDialog.getEditor().getText();
			network.addPerson(result);
			if(centralUser == null) {
				centralUser = new Person(result);
			}
			commandLog.add("a " + result);
			addDialog.close();
			//do something else with it
			try {
				start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	/*
	 * Dialog to take in user input, and add a new friendship
	 */
	public void addFriendDialog(Stage primaryStage) throws Exception {
		Stage addDialog = new Stage();
		Scene temp;
		BorderPane root = new BorderPane();
		addDialog.setTitle("Add a friendship");
		Button ok = new Button("OK");
		Button cancel = new Button("Cancel");
		TextField user1 = new TextField("Enter a user");
		TextField user2 = new TextField("Enter a user");
		HBox top = new HBox();
		HBox bot = new HBox();
		top.getChildren().add(user1);
		top.getChildren().add(user2);
		bot.getChildren().add(ok);
		bot.getChildren().add(cancel);
		root.setTop(top);
		root.setCenter(bot);
		temp = new Scene(root, 100, 100);
		addDialog.setScene(temp);
		addDialog.show();
		ok.setOnAction(event -> {
			String f1 = user1.getText();
			String f2 = user2.getText();
			addDialog.close();
			try {
				addFriendHandler(f1, f2, primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		cancel.setOnAction(event -> {
			addDialog.close();
		});
	}

	private void addFriendHandler(String f1, String f2, Stage primaryStage) {
		network.addFriend(f1, f2);
		commandLog.add("a " + f1 + " " + f2);
		try {
			start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Dialog to take in user input, and search for a user
	 */
	public void searchUserDialog() throws Exception {
		TextInputDialog addDialog = new TextInputDialog("Enter a name");
		addDialog.setTitle("Search for a user");
		addDialog.show();
		addDialog.getEditor().setOnAction(event -> { //user hits enter key
			String result = addDialog.getEditor().getText();
			//network.addPerson(result);
			System.out.println(result);
			addDialog.close();
			//do something else with it
		});
	}

	/*
	 * Dialog to take in user input, 
	 */
	public void removeDialog(Stage primaryStage) throws Exception {
		TextInputDialog removeDialog = new TextInputDialog("Enter a name");
		removeDialog.setTitle("Remove a user");
		removeDialog.show();
		removeDialog.getEditor().setOnAction(event -> { //user hits enter key
			String result = removeDialog.getEditor().getText();
			try {
				removeHandler(result, primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(result);
			String command = "r " + result;
			commandLog.add(command);
			removeDialog.close();
			//do something else with it
		});
	}
	
	/*
	 * Dialog to take in user input, 
	 */
	public void removeFriendDialog(Stage primaryStage) throws Exception {
		Stage removeDialog = new Stage();
		Scene temp;
		BorderPane root = new BorderPane();
		removeDialog.setTitle("Remove a friendship");
		Button ok = new Button("OK");
		Button cancel = new Button("Cancel");
		TextField user1 = new TextField("Enter a user");
		TextField user2 = new TextField("Enter a user");
		HBox top = new HBox();
		HBox bot = new HBox();
		top.getChildren().add(user1);
		top.getChildren().add(user2);
		bot.getChildren().add(ok);
		bot.getChildren().add(cancel);
		root.setTop(top);
		root.setCenter(bot);
		temp = new Scene(root, 100, 100);
		removeDialog.setScene(temp);
		removeDialog.show();
		ok.setOnAction(event -> {
			String f1 = user1.getText();
			String f2 = user2.getText();
			removeDialog.close();
			try {
				removeRelationHandler(f1, f2, primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		cancel.setOnAction(event -> {
			removeDialog.close();
		});
	}
	
	/*
	 * Handler to remove a friendship from the network and update the GUI
	 */
	public void removeRelationHandler(String f1, String f2, Stage primaryStage) throws Exception {
		network.removeFriend(f1, f2);
		commandLog.add("r " + f1 + " " + f2);
		start(primaryStage); //update GUI
	}

	/*
	 * Handler to remove a person from the network
	 */
	public void removeHandler(String name, Stage primaryStage) throws Exception {
		network.removePerson(name);
		start(primaryStage);
		//.System.out.println("Success");
	}

	/*
	 * Dialog to take in user input, and load in a file with the name given
	 */
	public void loadDialog(Stage primaryStage) throws Exception {
		TextInputDialog removeDialog = new TextInputDialog("Enter a file name");
		removeDialog.setTitle("Load a network");
		removeDialog.show();
		removeDialog.getEditor().setOnAction(event -> { //user hits enter key
			String result = removeDialog.getEditor().getText();
			//Call file loader
			System.out.println(result);
			removeDialog.close();
			//display new window
			try {
				start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/*
	 * Dialog to take in user input, and save the current network
	 */
	public void saveDialog() throws Exception {
		TextInputDialog removeDialog = new TextInputDialog("Enter a file name");
		removeDialog.setTitle("Save the network");
		removeDialog.show();
		removeDialog.getEditor().setOnAction(event -> { //user hits enter key
			String result = removeDialog.getEditor().getText();
			//Call file saver
			System.out.println(result);
			removeDialog.close();
			//do something else with it
		});
	}
	
	private void pathDialog(Stage primaryStage) {
		TextInputDialog pathDialog = new TextInputDialog("Enter a username");
		pathDialog.setTitle("Save the network");
		pathDialog.show();
		pathDialog.getEditor().setOnAction(event -> { //user hits enter key
			String result = pathDialog.getEditor().getText();
			//Call file saver
			System.out.println(result);
			pathDialog.close();
			//do something else with it
		});
	}
	
	/*
	 * Switches central user and updates the GUI
	 */
	public void changeUser(Stage primaryStage, String newUser) {
		commandLog.add("s " + newUser);
		if(network.getElements().contains(newUser)) {
			centralUser = new Person(newUser);
		}
		try {
			start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();
		GridPane gridpane = new GridPane();
		// Create a vertical box with Hello labels for each args
		try {
			VBox topBox = new VBox();
			VBox leftBox = new VBox();
			Label centralName = new Label("No central user set!");
			if (centralUser != null) {
				centralName = new Label(centralUser.getName() + " - " + network.getNumberOfFriends(centralUser.getName()) + " Friends");
				List<String> names = centralUser.getFriendsList(network);
				// TODO: Change to current user and friends
				for (int i = 0; i < names.size(); i++) {
					String userName = centralUser.getFriendsList(network).get(i);
					Button name = new Button(userName);
					name.setOnAction(action -> {
						changeUser(primaryStage, userName);
					});
					name.setMinWidth(90);
					name.setMaxWidth(90);
					gridpane.add(name, 0, i + 1);
				}
			}
			Label labelName = new Label("Name");
			Label labelRemove = new Label("Remove");
			gridpane.add(labelName, 0, 0);
			gridpane.add(labelRemove, 1, 0);
			gridpane.getColumnConstraints().add(new ColumnConstraints(100));

			topBox.getChildren().add(centralName);
			leftBox.getChildren().add(gridpane);
			
			//GUI elements for controls
			VBox rightBox = new VBox();
			Button clear = new Button("Clear");
			clear.setMinWidth(120);
			clear.setOnAction(event -> {
				try {
					clearHandler(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button addUser = new Button("Add user");
			addUser.setMinWidth(120);
			addUser.setOnAction(event -> {
				try {
					addUserDialog(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button addFriend = new Button("Add friendship");
			addFriend.setMinWidth(120);
			addFriend.setOnAction(event -> {
				try {
					addFriendDialog(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button searchUser = new Button("Search user");
			searchUser.setMinWidth(120);
			searchUser.setOnAction(event -> {
				try {
					searchUserDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button removeUser = new Button("Remove user");
			removeUser.setMinWidth(120);
			removeUser.setOnAction(event -> {
				try {
					removeDialog(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button removeFriend = new Button("Remove friendship");
			removeFriend.setMinWidth(120);
			removeFriend.setOnAction(event -> {
				try {
					removeFriendDialog(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button loadFile = new Button("Load File");
			loadFile.setMinWidth(120);
			loadFile.setOnAction(event -> {
				try {
					loadDialog(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button saveChanges = new Button("Save changes");
			saveChanges.setMinWidth(120);
			saveChanges.setOnAction(event -> {
				try {
					saveDialog();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Button shortestPath = new Button("Find shortest path");
			shortestPath.setMinWidth(120);
			shortestPath.setOnAction(event -> {
				try {
					pathDialog(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			shortestPath.setMinWidth(110);
			rightBox.getChildren().add(clear);
			rightBox.getChildren().add(addUser);
			rightBox.getChildren().add(addFriend);
			rightBox.getChildren().add(searchUser);
			rightBox.getChildren().add(removeUser);
			rightBox.getChildren().add(removeFriend);
			rightBox.getChildren().add(loadFile);
			rightBox.getChildren().add(saveChanges);
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
			e.printStackTrace();
			System.out.println("Exception!!!");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		network = new NetworkGraph();
		commandLog = new ArrayList<String>();
		launch(args);
	}
}