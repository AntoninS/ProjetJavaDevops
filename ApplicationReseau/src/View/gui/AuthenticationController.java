package View.gui;

import Controller.service.UserService;
import Model.Client.Client;
import Model.common.GestionnaireMessages;
import Model.common.course.GestionnaireCourses;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AuthenticationController {
	
	@FXML
	private AnchorPane panelPrincipal;
	private double posX;
	private double posY;
	
	@FXML
	private StackPane paneMsgBox;
	
	@FXML
	private Label boutonConnexion;
	
	@FXML
	private Label boutonCreerCompte;
	
	@FXML
	private JFXTextField loginText;
	
	@FXML
	private JFXPasswordField passwordText;
	
	@FXML
	private JFXButton boutonSeConnecter;
	
	@FXML
	private Hyperlink lienMdpOublie;

	//M�thode li� � l'�cran
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
			this.panelPrincipal.getScene().getWindow().setX(event.getScreenX() - this.posX);
			this.panelPrincipal.getScene().getWindow().setY(event.getScreenY() - this.posY);
		}
	}
	
	//Si l'utilisateur clique sur Cr�er un compte
	@FXML
	private void clickCreerCompte(MouseEvent event)
	{
		this.boutonConnexion.setUnderline(false);
		this.boutonCreerCompte.setUnderline(true);
		this.loginText.setVisible(false);
		this.passwordText.setVisible(false);
		this.boutonSeConnecter.setVisible(false);
		this.lienMdpOublie.setVisible(false);
	}
	
	//Si l'utilisateur clique sur le bouton Connexion
	@FXML
	private void clickConnexion(MouseEvent event)
	{
		this.boutonCreerCompte.setUnderline(false);
		this.boutonConnexion.setUnderline(true);
		this.loginText.setVisible(true);
		this.passwordText.setVisible(true);
		this.boutonSeConnecter.setVisible(true);
		this.lienMdpOublie.setVisible(true);
	}
	
	@FXML
	private void verificationConnexion(MouseEvent mouseEvent) throws IOException
	{
		//TODO G�rer connexion BDD
		if(mouseEvent.getButton() == MouseButton.PRIMARY)
		{
			String loginForm = this.loginText.getText().trim();
			String passwordForm = this.passwordText.getText().trim();

			if(loginForm.isEmpty() || passwordForm.isEmpty())
			{
				this.paneMsgBox.setDisable(false);
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text("Erreur de connexion"));
				content.setBody(new Text("Veuillez renseigner un nom d'utilisateur et un mot de passe!"));
				
				JFXDialog msgBox = new JFXDialog(this.paneMsgBox, content, JFXDialog.DialogTransition.CENTER);
				
				JFXButton button = new JFXButton("D'accord");
				button.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						msgBox.close();
						paneMsgBox.setDisable(true);
					}
				});
				content.setActions(button);
				
				msgBox.show();
			}
			else {
				if (UserService.getInstance().isPasswordCorrect(loginForm, passwordForm)) {
					Parent rootAffichageEcranPrincipalParent;
					try {
						System.out.println("ok");
						
						//Connexion au serveur
						String address = "127.0.0.1";
						Integer port = new Integer(1420);
						GestionnaireMessages gm = new GestionnaireMessages();
						Client unClient = new Client(port, address, this.loginText.getText(), gm);
						
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("EcranPrincipal.fxml"));
						rootAffichageEcranPrincipalParent = loader.load();
						
						EcranPrincipalController controlleur = loader.getController();
						controlleur.getClient(unClient);
						controlleur.setLblUtilisateur(unClient.getNom());
						
						GestionnaireCourses gc = new GestionnaireCourses();
						gm.setGc(gc);
						controlleur.setGestionnaireMessage(gm);

						Stage stage = new Stage();
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setScene(new Scene(rootAffichageEcranPrincipalParent, 940,622));
						stage.show();
						((Node)mouseEvent.getSource()).getScene().getWindow().hide();
						
					} catch(IOException e) {
						e.printStackTrace();
					}
				} else {
					this.paneMsgBox.setDisable(false);
					JFXDialogLayout content = new JFXDialogLayout();
					content.setHeading(new Text("Erreur de connexion"));
					content.setBody(new Text("Pseudo ou Mot de passe incorrect"));

					JFXDialog msgBox = new JFXDialog(this.paneMsgBox, content, JFXDialog.DialogTransition.CENTER);

					JFXButton button = new JFXButton("D'accord");
					button.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							msgBox.close();
							paneMsgBox.setDisable(true);
						}
					});
					content.setActions(button);

					msgBox.show();
				}
			}
		}
	}
	
	//Ferme l'application sur un clique gauche
	@FXML
	private void closeOnClick(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			System.exit(0);
	}
	
	//Code pour r�udire l'�cran dans la barre des t�ches
	@FXML
	private void reduireEcran(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
	}
	
}
