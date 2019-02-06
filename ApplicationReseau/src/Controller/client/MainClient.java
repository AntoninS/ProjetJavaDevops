package Controller.client;

import Model.Client.Client;
import View.Authentication;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainClient extends Application{

	public static void main(String[] args) throws UnknownHostException, IOException {

		if(args.length != 2) {
			printUsage();
			//String address = "127.0.0.1";
			//Integer port = new Integer(1420);
			//Client c = new Client(port, address);
			
			MainClient.launch(Authentication.class);
		}
		else
		{
			String address = args[0];
			Integer port = new Integer(args[1]);
			Client c = new Client(port, address, null);
		}

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
