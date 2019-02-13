package Controller.client;

import Controller.service.UserService;
import Model.Client.Client;
import Model.common.User.User;
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
	
	private boolean connexion = true;

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
		this.boutonSeConnecter.setText("Cr�er un compte");
		this.lienMdpOublie.setVisible(false);
		this.connexion = false;
	}

	//Si l'utilisateur clique sur le bouton Connexion
	@FXML
	private void clickConnexion(MouseEvent event)
	{
		this.boutonCreerCompte.setUnderline(false);
		this.boutonConnexion.setUnderline(true);
		this.loginText.setVisible(true);
		this.passwordText.setVisible(true);
		this.boutonSeConnecter.setText("Se connecter");
		this.lienMdpOublie.setVisible(true);
		this.connexion = true;
		System.out.println(this.connexion);
	}

	@FXML
	private void verificationConnexion(MouseEvent mouseEvent) throws IOException
	{
		//TODO G�rer connexion BDD
		if(mouseEvent.getButton() == MouseButton.PRIMARY)
		{
			if(this.connexion)
			{
				String loginForm = this.loginText.getText().trim();
				String passwordForm = this.passwordText.getText().trim();

				if(loginForm.isEmpty() || passwordForm.isEmpty())
				{
					boxErreur("Le login et/ou le mot de passe ne sont pas saisi(s)");
				}
				else {
					if (UserService.getInstance().isPasswordCorrect(loginForm, passwordForm)) {
						//Récupération de l'utilisateur en BDD
						User utilisateur = UserService.getInstance().getUser(loginForm);

						Parent rootAffichageEcranPrincipalParent;
						try {
							System.out.println("ok");

							//Connexion au serveur
							String address = "127.0.0.1";
							Integer port = new Integer(1420);
							GestionnaireMessages gm = new GestionnaireMessages();
							Client unClient = new Client(port, address, this.loginText.getText(), gm);

							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("../../View/gui/EcranPrincipal.fxml"));
							rootAffichageEcranPrincipalParent = loader.load();

							EcranPrincipalController controlleur = loader.getController();
							controlleur.getClient(unClient);
							controlleur.setLblUtilisateur(unClient.getNom());
							unClient.setEc(controlleur);

							//Affichage du montant disponible dans la cagnotte
							controlleur.setLblCagnotte(utilisateur.getMoney());

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
						boxErreur("Pseudo ou Mot de passe incorrect");
					}
				}
			}
			else
			{
				System.out.println(this.connexion);
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

	private void boxErreur( String messageErreur)
	{
		this.paneMsgBox.setDisable(false);
		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("Erreur de connexion"));
		content.setBody(new Text(messageErreur));

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
