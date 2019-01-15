package View.gui;

import java.io.IOException;

import Model.common.course.CourseController;
import Model.common.course.ThreadCourse;
import com.jfoenix.controls.JFXButton;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class EcranPrincipalController {

	@FXML
	private JFXButton btnConsulterCourse;

	private static ThreadCourse course;


	@FXML
	private void consulterLaCourse(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
		{
			Parent rootAffichageCourse;
			try
			{
				System.out.println("ok");
				rootAffichageCourse = FXMLLoader.load(getClass().getResource("/View/gui/AffichageCourse.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(new Scene(rootAffichageCourse, 700,450));
				stage.show();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent we) {
						CourseController.setAffichageActif(false);
					}
				});
				//	stage.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void lancerLaCourse(MouseEvent event)
	{
		course = new ThreadCourse( "FCKebab");
		Thread courseThread= new Thread(course);
		courseThread.start();
	}


	//Ferme l'application sur un clique gauche
	@FXML
	private void closeOnClick(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			System.exit(0);
	}


	//R�duit l'�cran dans la barre des t�ches
	@FXML
	private void reduireEcran(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
	}


	public static ThreadCourse  getCourse() {
		return course;
	}
}
