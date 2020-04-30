package AssignStudents.AssignStudents;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class ConfirmBox {
	
	static boolean answer;
	
	static boolean display(String title, String message) {
		Stage confirmWindow = new Stage();
		
		confirmWindow.initModality(Modality.APPLICATION_MODAL);
		confirmWindow.setTitle(title);
		confirmWindow.setMinWidth(200);
		
		Label confirmLabel = new Label();
		confirmLabel.setText(message);
		
		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(e -> {
			answer = true;
			confirmWindow.close();
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			answer = false;
			confirmWindow.close();
		});
		
		VBox confirmLayout = new VBox(10);
		confirmLayout.setPadding(new Insets(20, 20, 20, 20));
		confirmLayout.getChildren().addAll(confirmLabel, confirmButton, cancelButton);
		confirmLayout.setAlignment(Pos.CENTER);
		
		Scene confirmScene = new Scene(confirmLayout);
		confirmWindow.setScene(confirmScene);
		
		confirmWindow.showAndWait();
		
		return answer;
	}
}