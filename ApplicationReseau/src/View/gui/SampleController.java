package View.gui;

import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SampleController {
	
	@FXML
	private AnchorPane panelPrincipal;
	private double posX;
	private double posY;
	
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

	//Méthode lié à l'écran
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
	
	//Ferme l'application sur un clique gauche
	@FXML
	private void closeOnClick(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			System.exit(0);
	}
	
	//Si l'utilisateur clique sur Créer un compte
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
	private void reduireEcran(MouseEvent event)
	{
		if(event.getButton() == MouseButton.PRIMARY)
			((Stage)((ImageView)event.getSource()).getScene().getWindow()).setIconified(true);
	}
	
}
