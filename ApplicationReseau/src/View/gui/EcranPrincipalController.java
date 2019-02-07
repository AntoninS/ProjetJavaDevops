package View.gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import Model.common.Message;
import Model.common.course.ThreadCourse;
import Model.Server.Server;
import Model.common.Cheval;
import Model.common.GestionnaireMessages;
import Model.common.course.GestionnaireCourses;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;

import Model.Client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EcranPrincipalController implements Initializable {
	
	private static ThreadCourse course;
	private Client client;
	
	@FXML
	private AnchorPane panelEcranPrincipal;
	private double posX;
	private double posY;
	
	@FXML
	private JFXTextArea msgField;
	
	@FXML
	private ImageView btnEnvoyer;
	
	@FXML
	private JFXButton btnConsulterCourse;

	@FXML
	private JFXListView<String> fxListeCheval;
	
	@FXML
	private Label lblUtilisateur;

	@FXML
	private Label lblCagnotte;
	
//	public void initiate()
//	{
//		Message mess; 
//		
//		ObjectInputStream in = this.client.getInputStream();
//		Socket socket = this.client.getSocket();
//		
//		try {
//			in = new ObjectInputStream(socket.getInputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		boolean isActive = true;
//		while(isActive)
//		{
//			try
//			{
//				mess = (Message) in.readObject();
//				if(mess !=null)
//				{
//					System.out.println("\nMessage re�u : " + mess);
//					this.client.messageReceived(mess);
//				}
//				else
//				{
//					isActive = false;
//				}
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//		client.disconnectedServer();
//	}
	
	public void getClient(Client client)
	{
		this.client = client;
	}
	
	public void setLblUtilisateur(String nomUtilisateur)
	{
		this.lblUtilisateur.setText("Bonjour, " + nomUtilisateur);
	}

	public void setLblCagnotte(float montantCagnotte)
	{
        this.lblCagnotte.setText(String.format("Cagnotte : %.2f €", montantCagnotte));
	}

	private GestionnaireMessages gestionnaireMessages;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void consulterLaCourse(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
		{
			Parent rootAffichageCourse;
			try
			{
				System.out.println("ok");
				//rootAffichageCourse = FXMLLoader.load(getClass().getResource("/View/gui/AffichageCourse.fxml"));
				Stage stage = new Stage();

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("AffichageCourse.fxml"));
				rootAffichageCourse = loader.load();

				CourseController controllerCourse = loader.getController();
				controllerCourse.setEcranController(this);

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
	
	@FXML
	private void envoyerMessage(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
		{
			Message mess = new Message(this.client.getNom(), this.msgField.getText());
			try {
				client.getOutPutStream().writeObject(mess);
				this.client.getOutPutStream().flush();
				this.msgField.setText("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void ajouterCourseListView()
	{
		ObservableList<String> listeChevaux = FXCollections.<String>observableArrayList();


		for (Cheval cheval  : gestionnaireMessages.getGc().getListeDesCoursesEnCours().get(0).getListChevalCourse())
		{
			String stringListView = cheval.getNom();
			listeChevaux.add(stringListView);
		//	fxListeCheval.getItems().add(stringListView);
		}
		//fxListeCheval.getItems().add("Test");
		ObservableList<String> seasonList = FXCollections.observableArrayList("Spring", "Summer", "Fall", "Winter");
		fxListeCheval.setItems(seasonList);
		fxListeCheval.setMinHeight(100.0);
		ObservableList<String> vdsvsd = FXCollections.observableArrayList("Spring", "Summer", "Fall", "Winter");

	}

	public void setGestionnaireMessage(GestionnaireMessages gm)
	{
		gestionnaireMessages = gm;
		gestionnaireMessages.getGc().setEcranController(this);

	}

	public GestionnaireMessages getGestionnaireMessaire()
	{
		return gestionnaireMessages;
	}
}
