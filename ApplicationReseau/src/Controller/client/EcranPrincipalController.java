package Controller.client;

import java.io.IOException;
import java.io.ObjectInputStream;

import Controller.service.RaceService;
import Controller.service.UserService;
import Model.common.Pari.Pari;
import Model.common.Message.Message;
import Model.common.User.User;
import Model.common.course.Course;
import Model.common.Cheval.Cheval;
import com.jfoenix.controls.*;

import Model.Client.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;

public class EcranPrincipalController implements Initializable {

	private Client client;
	
	private JSONObject messageJSON;

	//TODO : trouver un autre moyen de get cette liste
	private List<Cheval> classementPodium;
	
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
	
	@FXML
	private JFXTextArea tchatField;

	@FXML
	private TextArea fxMontantMise;

	@FXML
	private JFXButton btnValiderMise;
	
	@FXML
	private Label lblNbPersonne;
	

	
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
		this.lblCagnotte.setText(String.format("Cagnotte : %.2f \u20ac", montantCagnotte));
	}

	public List<Cheval> getClassementPodium() {
		return classementPodium;
	}

	public void setClassementPodium(List<Cheval> classementPodium) {
		this.classementPodium = classementPodium;
	}

	private GestionnaireMessages gestionnaireMessages;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fxListeCheval.setVisible(!fxListeCheval.getItems().isEmpty());
		fxMontantMise.setVisible(fxListeCheval.isVisible());
		btnValiderMise.setVisible(fxListeCheval.isVisible());
		this.btnConsulterCourseDisable(true);
	}

	public void onestla(int dureeAvantArret)
	{
		Platform.runLater(
				() -> {

					Alert alt = new Alert(Alert.AlertType.ERROR);
					alt.setContentText("Le serveur n'est plus disponible l'application va s'arréter dans " +dureeAvantArret );
					alt.show();
				}
		);
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
				loader.setLocation(getClass().getResource("../../View/gui/AffichageCourse.fxml"));
				rootAffichageCourse = loader.load();

				CourseController controllerCourse = loader.getController();
				controllerCourse.setEcranController(this);

				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(new Scene(rootAffichageCourse, 600,450));
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
		
		if(scrollBarText.isVisible()==true && this.msgField.getLayoutY()>490)
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
	private void envoyerMessage(MouseEvent event) throws JSONException
	{
		if(event.getButton() == MouseButton.PRIMARY)
		{
			this.messageJSON = new JSONObject();
			this.messageJSON.put("balise", "message");
			this.messageJSON.put("nom", this.client.getNom());
			this.messageJSON.put("messageEnvoye", this.msgField.getText());
			this.tchatField.appendText("\nVous : " + this.msgField.getText());
			
			Message mess = new Message(this.client.getNom(), this.messageJSON.toString());
			try {
				this.client.getOutPutStream().writeObject(mess);
				this.client.getOutPutStream().flush();
				this.msgField.setText("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/** permet de gérer l'affichage du listview des chevaux */
	public void ajouterCourseListView()
	{
		Platform.runLater(
				() -> {
					ObservableList<String> listeChevaux = FXCollections.<String>observableArrayList();

					if(!fxListeCheval.getItems().isEmpty())
					{
						fxListeCheval.getItems().removeAll(fxListeCheval.getItems());
					}

					for (Cheval cheval  : getCourse().getListChevalCourse())
					{
						String stringListView = String.format("[%d] - %s", cheval.getNumero(), cheval.getNom());
						listeChevaux.add(stringListView);
					}
				fxListeCheval.setItems(listeChevaux);
					fxListeCheval.setMinHeight(100.0);

					fxListeCheval.setVisible(!fxListeCheval.getItems().isEmpty());
					fxMontantMise.setVisible(fxListeCheval.isVisible());
					btnValiderMise.setVisible(fxListeCheval.isVisible());
				}
		);

	}

	/** permet de lui attribuer un gestionnaire de message json */
	public void setGestionnaireMessage(GestionnaireMessages gm)
	{
		gestionnaireMessages = gm;
		gestionnaireMessages.getGc().setEcranController(this);
		gestionnaireMessages.setController(this);
	}

	public Course getCourse()
	{
		return this.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0);
	}

	public void btnConsulterCourseDisable(boolean b)
	{
		btnConsulterCourse.setDisable(b);
	}

	public GestionnaireMessages getGestionnaireMessaire()
	{
		return gestionnaireMessages;
	}
	
	public JFXTextArea getTchatField()
	{
		return this.tchatField;
	}


	@FXML
	private void validerMise(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY) {

			float montantMise = 0;
			String cheval = "";
			int idCheval = 0;

			// Récupération du montant misé
			if (!fxMontantMise.getText().trim().isEmpty()) {
				montantMise = Float.valueOf(fxMontantMise.getText());
			} else {

			}

			// Récupération du cheval sélectionné dans la liste
			cheval = fxListeCheval.getSelectionModel().getSelectedItem();

			if (null != cheval && !cheval.trim().isEmpty()) {
				idCheval = Integer.valueOf(cheval.substring(cheval.indexOf("[") + 1, cheval.indexOf("]")));
			} else {

			}

			// TODO : gerer montant > cagnotte
			if (montantMise > 0 && idCheval > 0) {
				fxMontantMise.setDisable(true);
				btnValiderMise.setDisable(true);
				User currentUser = UserService.getInstance().getUser(client.getNom());
				RaceService.getInstance().insertBet(currentUser.getId(), idCheval, getCourse().getId(), montantMise);
			}


		}
	}
	
	public void setLblNbPersonne(String nbPersonne)
	{
		this.lblNbPersonne.setText(nbPersonne);
	}

	public void handleEndOfCourse(List<Cheval> classementPodium) {
		this.classementPodium = classementPodium;
		User currentUser = UserService.getInstance().getUser(client.getNom());
		Pari pari = RaceService.getInstance().getBet(currentUser.getId(), getCourse().getId());


		float cagnotte = RaceService.getInstance().calculateGains(pari, classementPodium);
		Platform.runLater(
			() -> {
				setLblCagnotte(cagnotte);
			}
		);

		if (RaceService.getInstance().hasWonBet(pari, classementPodium)) {
			//popup
			System.out.println("GAGNEEEEEEEEEEEEEEEEEE");
		} else {
			//popup
			System.out.println("PERDUUUUUUUUUUUUUUUUUU");
		}


	}

}
