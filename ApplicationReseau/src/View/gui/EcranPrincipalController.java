package View.gui;

import java.io.IOException;

import Model.common.course.CourseController;
import Model.common.course.ThreadCourse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import Model.Server.Server;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class EcranPrincipalController {
	
	private Server server;
	
	@FXML
	private AnchorPane panelEcranPrincipal;
	private double posX;
	private double posY;
	
	@FXML
	private JFXTextArea msgField;
	
	@FXML
	private JFXButton btnConsulterCourse;
	
	public void getServer(Server server)
	{
		this.server = server;
	}
	

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
				stage.setScene(new Scene(rootAffichageCourse, 800,450));
				stage.show();
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent we) {
						CourseController.setAffichageActif(false);
					}
				});

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void msgFieldPlein(KeyEvent event)
	{	
		ScrollBar scrollBarText = (ScrollBar) this.msgField.lookup(".scroll-bar:vertical");
		
		if(scrollBarText.isVisible()==true)
		{
			this.msgField.setLayoutY(this.msgField.getLayoutY()-10);
			this.msgField.setPrefHeight(this.msgField.getPrefHeight()+10);
		}
		
		if(event.getCode() == KeyCode.BACK_SPACE && this.msgField.getLayoutY() != 557)
		{
			this.msgField.setLayoutY(this.msgField.getLayoutY()+10);
			this.msgField.setPrefHeight(this.msgField.getPrefHeight()-10);
		}
			
	}
	
	@FXML
	private void msgFieldVide(KeyEvent event)
	{
		if(this.msgField.getText().trim().isEmpty())
		{
			this.msgField.setLayoutY(557);
			this.msgField.setPrefHeight(35);
		}
	}
	
	//Permet de faire bouger l'�cran
	@FXML
	private void moveOnDrag(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
		{
			this.posX = event.getSceneX();
			this.posY = event.getSceneY();
		}
	}
	
	@FXML
	private void setOnMouseDrag(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
		{
			this.panelEcranPrincipal.getScene().getWindow().setX(event.getScreenX() - this.posX);
			this.panelEcranPrincipal.getScene().getWindow().setY(event.getScreenY() - this.posY);
		}
	}
	
	@FXML
	private void lancerLaCourse(MouseEvent event)
	{
		//course = new ThreadCourse( "FCKebab");
		//Thread courseThread= new Thread(course);
		//courseThread.start();
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
