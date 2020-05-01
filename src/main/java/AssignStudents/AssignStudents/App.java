package AssignStudents.AssignStudents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

//import com.sun.org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;




public class App extends Application {
	Stage window;
	//Scene menuScene, startScene, prjListScene, uploadStudentsScene;
	Scene borderScene;
	int pCount;
	List<String> projects = new ArrayList<String>();
	List<String> students = new ArrayList<String>();
	String[][] importData = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setMinHeight(500);
		window.setMinWidth(800);
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		// Setting up Outer BorderPane Layout for all inner scenes
		BorderPane outerLayout = new BorderPane();

		// Layout for to top area scene for title & close button
		HBox topMenu = new HBox(75);

		
		// Setting up layouts for each inner "scene"		
		VBox s1Layout = new VBox(20);
		VBox s2Layout = new VBox(20);
		VBox s3Layout = new VBox(20);
		HBox s4Layout = new HBox(20);
		
		
		// Setting up scenes and action processes for Next and Back buttons
		StackPane LeftMenu = new StackPane();
		LeftMenu.setAlignment(Pos.CENTER);
		LeftMenu.setPrefWidth(80);
		StackPane RightMenu = new StackPane();
		RightMenu.setAlignment(Pos.CENTER);
		RightMenu.setPrefWidth(80);

		//Buttons for all Next/Back functionality
		Region padder = new Region();
		padder.prefWidthProperty().bind(LeftMenu.prefWidthProperty());
		Button s1Next = new Button("Next");
		Button s2Back = new Button("Back");
		Button s2Next = new Button("Next");
		Button s3Back = new Button("Back");
		Button s3Next = new Button("Next");
		Button s4Back = new Button("Back");


		// Assigning Scene 1 Next Button as initial scene
		RightMenu.getChildren().addAll(s1Next);
		LeftMenu.getChildren().add(padder);
		
		
		// Top Inner Scene, for Title and Close Button
		Label mainTitle = new Label("Program to Assign Students to Projects");
		mainTitle.setAlignment(Pos.CENTER_RIGHT);
		mainTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
		mainTitle.prefWidth(250);
		Button closeButton = new Button("Close Program");
		closeButton.prefWidth(100);
		closeButton.setOnAction(e -> closeProgram());
		Button restartButton = new Button("Restart");
		restartButton.prefWidth(100);
		VBox menuButtons = new VBox(5);
		menuButtons.getChildren().addAll(restartButton, closeButton);
		topMenu.getChildren().addAll(mainTitle, menuButtons);
		
		//Start Page (Scene 1)
		Label getPrjCount = new Label("Welcome!\nTo begin, please upload the list of project names:");
		getPrjCount.setAlignment(Pos.TOP_CENTER);
		getPrjCount.setTextAlignment(TextAlignment.CENTER);
		TextField prjCount = new TextField();
		prjCount.setMaxWidth(15);
		prjCount.setMaxHeight(15);
		s1Layout.getChildren().addAll(getPrjCount, prjCount);
		s1Layout.setAlignment(Pos.CENTER);
		
		
		//Adding Project Details (Scene 2)
		Label prjListTitle = new Label("Please Add Project Name and Number of Available Slots For Each Project:");
		Label prjNameLbl = new Label("Project Name:");
		TextField prjName = new TextField();
		prjCount.setMaxWidth(50);
		prjCount.setMaxHeight(15);
		Label slotCountLbl = new Label("Available Slots:");
		TextField slotCount = new TextField();
		slotCount.setMaxWidth(50);
		slotCount.setMaxHeight(15);
		Button addProject = new Button("Add Project");
		HBox enterPrj = new HBox(5);
		enterPrj.getChildren().addAll(prjNameLbl, prjName, slotCountLbl, slotCount);
		GridPane prjList = new GridPane();
		prjList.setHgap(5);
		prjList.setVgap(5);
		s2Layout.getChildren().addAll(prjListTitle, enterPrj, addProject, prjList);
		s2Layout.setAlignment(Pos.CENTER);
		
		
		//Uploading Students & Preferences (Scene 3)
		Label uploadStudentsTitle = new Label("Please Upload All Student Names and Their Preferences Here:");
		Button uploadStudents = new Button("Upload Student Info");
		Label label = new Label();
		uploadStudents.setOnAction(e -> {
			FileChooser getPrjs = new FileChooser();
			File prjListFile = getPrjs.showOpenDialog(primaryStage); 			  
            if (prjListFile != null) {
                readFile r = new readFile();
        		try {
        			r.openFile(prjListFile);
        			importData = r.read();
        			r.close();
                    label.setText(prjListFile.getAbsolutePath() + "  successfully uploaded.\n" + importData.length + " Rows were read.");
        		}
        		catch (IOException | InvalidFormatException e1) {
        			e1.printStackTrace();
        		}
            }
            else {
            	label.setText("No File Selected");
            }
            s3Layout.getChildren().add(label);
		});
		s3Layout.getChildren().addAll(uploadStudentsTitle, uploadStudents);
		s3Layout.setAlignment(Pos.CENTER);
		
		
		// Viewing Results (Scene 4)
		Label results  = new Label("Results:");
		s4Layout.getChildren().addAll(results);
		s4Layout.setAlignment(Pos.CENTER);
		
		
		// Scene 1 button actions (only next)		
		s1Next.setOnAction(e -> {
			RightMenu.getChildren().removeAll(s1Next);
			RightMenu.getChildren().add(s2Next);
			LeftMenu.getChildren().removeAll(padder);
			LeftMenu.getChildren().add(s2Back);
			
			// adding all text areas for projects
			// and their available slots based on value entered in prjCount
			pCount = Integer.parseInt(prjCount.getText());
			for (int i = 0; i < pCount; i++) {
				prjList.add(new Label(i+1 +"."), 0, i);
			}
			outerLayout.setCenter(s2Layout);
		});
		
		// Scene 2 button actions (add project, next, and back)
		addProject.setOnAction(e -> {
			if (projects.size() < pCount) {
				String tempPrjName = prjName.getText();
				int tempslots = Integer.parseInt(slotCount.getText());
				projects.add(tempPrjName);
				prjList.add(new Label(tempPrjName), 1, projects.indexOf(tempPrjName));
				prjList.add(new Label(tempslots + " Slots"), 2, projects.indexOf(tempPrjName));
				prjName.clear();
				slotCount.clear();
			}
			else {
				AlertBox.display("Project List Full", "You have entered the full number of projects you indicated. Please hit next to continue, or hit restart to change the number of projects.");
			}
		});
		s2Back.setOnAction(e -> {
			RightMenu.getChildren().removeAll(s2Next);
			RightMenu.getChildren().add(s1Next);
			LeftMenu.getChildren().removeAll(s2Back);
			outerLayout.setCenter(s1Layout);
		});
		s2Next.setOnAction(e -> {
			RightMenu.getChildren().removeAll(s2Next);
			RightMenu.getChildren().add(s3Next);
			LeftMenu.getChildren().removeAll(s2Back);
			LeftMenu.getChildren().add(s3Back);
			outerLayout.setCenter(s3Layout);
		});

		// Scene 3 button actions (next and back)
		s3Back.setOnAction(e -> {
			RightMenu.getChildren().removeAll(s3Next);
			RightMenu.getChildren().add(s2Next);
			LeftMenu.getChildren().removeAll(s3Back);
			LeftMenu.getChildren().add(s2Back);
			outerLayout.setCenter(s2Layout);
		});
		s3Next.setOnAction(e -> {
			RightMenu.getChildren().removeAll(s3Next);
			RightMenu.getChildren().add(padder);
			LeftMenu.getChildren().removeAll(s3Back);
			LeftMenu.getChildren().add(s4Back);
			outerLayout.setCenter(s4Layout);
		});
		
		// Scene 4 button actions (back only)
		s4Back.setOnAction(e -> {
			RightMenu.getChildren().removeAll(padder);
			RightMenu.getChildren().add(s3Next);
			LeftMenu.getChildren().removeAll(s4Back);
			LeftMenu.getChildren().add(s3Back);
			outerLayout.setCenter(s3Layout);
		});
		
		// restart button action - has to clear most text areas and other memory variables
		restartButton.setOnAction(e -> {
			boolean result = ConfirmBox.display("Confirm", "Are you sure you want to restart? Any data already entered will be deleted.");
			if (result) {
				RightMenu.getChildren().clear();
				RightMenu.getChildren().add(s1Next);
				LeftMenu.getChildren().clear();
				LeftMenu.getChildren().add(padder);
				prjCount.clear();
				label.setText("");
				prjName.clear();
				slotCount.clear();
				prjList.getChildren().clear();
				pCount = 0;
				projects.clear();
				students.clear();
				outerLayout.setCenter(s1Layout);
			}
		});

		
		// Setting initial scenes in outer layout
		outerLayout.setTop(topMenu);
		BorderPane.setMargin(topMenu, new Insets(25, 10, 10, 10));
		outerLayout.setLeft(LeftMenu);
		BorderPane.setMargin(LeftMenu, new Insets(10, 10, 10, 10));
		outerLayout.setRight(RightMenu);
		BorderPane.setMargin(RightMenu, new Insets(10, 10, 10, 10));
		outerLayout.setCenter(s1Layout);
		borderScene  = new Scene(outerLayout, 500, 500);

		
		//Setting Window Details & Showing
		window.setScene(borderScene);
		window.setTitle("Project Assigment Program");
		window.show();
	}
	
	private void closeProgram() {
		//System.out.println("Program Completed, Assignments Exported.");
		
		Boolean answer = ConfirmBox.display("Confirm Close", "Are you sure you want to exit the program? Data may be lost.");
		if(answer) {
			window.close();			
		}
	}
}