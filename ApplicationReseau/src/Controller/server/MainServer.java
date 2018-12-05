package Controller.server;

import Model.Server.Server;

import java.io.IOException;

public class MainServer {

	/**
	 * creates a new Controller.server
	 *@param args 
	 */
	public static void main(String[] args) throws IOException 
	{
		try
		{
			if (args.length != 1) {
				printUsage();
			} else {
				Integer port = new Integer(args[0]);
				Server server = new Server(port);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void printUsage() {
		System.out.println("java Model.Server.Server <port>");
		System.out.println("\t<port>: Controller.server's port");
	}

}
