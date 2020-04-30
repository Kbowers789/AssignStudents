package AssignStudents.AssignStudents;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class AlertBox {
	
	static void display(String title, String message) {
		Stage alertWindow = new Stage();
		
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle(title);
		alertWindow.setMinWidth(200);
		
		Label alertLabel = new Label();
		alertLabel.setText(message);
		
		Button alertButton = new Button("Close");
		alertButton.setOnAction(e -> alertWindow.close());
		
		VBox alertLayout = new VBox(10);
		alertLayout.getChildren().addAll(alertLabel, alertButton);
		alertLayout.setAlignment(Pos.CENTER);
		
		Scene alertScene = new Scene(alertLayout);
		alertWindow.setScene(alertScene);
		
		alertWindow.showAndWait();	
	}
}