package View;

import View.gui.ClientPanel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGui extends Application{

	public static void main(String[] args)
	{
		Application.launch(MainGui.class, args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		ClientPanel clientPanel = new ClientPanel();
		
		Group root = new Group();
		
		root.getChildren().add(clientPanel);
		
		Scene scene = new Scene(root, 500, 650);
		
		stage.setTitle("Mon application");
		
		stage.setScene(scene);
		
		stage.show();
		
	}
	
}
