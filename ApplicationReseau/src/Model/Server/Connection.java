package Model.Server;

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
		
		//Attend les nouvelles connection et r�cup�e un socket quand un Controller.client se connecte
		while(true)
		{
			System.out.println("Attente de nouvelle co");
			try {
				//la m�thode accepte est bloquante et va donc arreter l'ex�cution de l'application
				Socket sockNewClient = this.serverSocket.accept();
				//On cr�er un nouvel objet ConnectedClient
				ConnectedClient newClient = new ConnectedClient(this.server, sockNewClient);
				//Et on ajoute au serveur ce nouveau Controller.client connect�
				this.server.addClient(newClient);
				//Et nous lan�ons un thread � partir de l'objet ConnectedClient cr��
				Thread threadNewClient = new Thread(newClient);
				threadNewClient.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	

}
