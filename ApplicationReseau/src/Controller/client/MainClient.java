package Controller.client;

import Model.Client.Client;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		
		if(args.length != 2) {
			printUsage();
		}
		else
		{
			String address = args[0];
			Integer port = new Integer(args[1]);
			Client c = new Client(port, address);
		}

	}
	
	private static void printUsage()
	{
		System.out.println("java Model.Client.Client<address> <port>");
		System.out.println("\t<address>: Controller.server's ip address");
		System.out.println("\t<port>: Controller.server's port");
	}

}