package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable{
	
	private Server server;
	private ServerSocket serverSocket;
	
	public Connection(Server server)
	{
		this.server = server;
		try {
			this.serverSocket = new ServerSocket(server.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		//Attend les nouvelles connection et récupèe un socket quand un client se connecte
		while(true)
		{
			System.out.println("Attente de nouvelle co");
			try {
				//la méthode accepte est bloquante et va donc arreter l'exécution de l'application
				Socket sockNewClient = this.serverSocket.accept();
				//On créer un nouvel objet ConnectedClient
				ConnectedClient newClient = new ConnectedClient(this.server, sockNewClient);
				//Et on ajoute au serveur ce nouveau client connecté
				this.server.addClient(newClient);
				//Et nous lançons un thread à partir de l'objet ConnectedClient créé
				Thread threadNewClient = new Thread(newClient);
				threadNewClient.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	

}
