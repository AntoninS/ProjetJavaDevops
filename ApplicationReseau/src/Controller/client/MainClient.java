package Controller.client;

import Model.Client.Client;
import View.Authentication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainClient extends Application{

	public static void main(String[] args) throws UnknownHostException, IOException {
			
			MainClient.launch(Authentication.class);

	}
	
	private static void printUsage()
	{
		System.out.println("java Model.Client.Client<address> <port>");
		System.out.println("\t<address>: Controller.server's ip address");
		System.out.println("\t<port>: Controller.server's port");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
