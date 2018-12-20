package View.gui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EcranPrincipalController {
	
	@FXML
	private JFXButton btnConsulterCourse;
	
	@FXML
	private void consulterLaCourse(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
		{
			//Parent rootAffichageCourse;
			System.out.println("ok");	
			//rootAffichageCourse = FXMLLoader.load(getClass().getResource("/View/gui/EcranPrincipal.fxml"));
			//Stage stage = new Stage();
			//stage.initStyle(StageStyle.UNDECORATED);
			//stage.setScene(new Scene(rootAffichageCourse, 940,622));
			//stage.show();
		}
	}
	
	
	//Ferme l'application sur un clique gauche
	@FXML
	private void closeOnClick(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			System.exit(0);
	}
	
	
	//Réduit l'écran dans la barre des tâches
	@FXML
	private void reduireEcran(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
	}

}
