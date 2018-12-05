package Model.Server;

import java.util.ArrayList;
import java.util.List;

import Model.common.Message;

public class Server {
	
	private List<ConnectedClient> clients;
	private int port;
	
	public Server(int port)
	{
		this.clients = new ArrayList<ConnectedClient>();
		this.port = port;
		
		Thread threadConnection = new Thread(new Connection(this));
		threadConnection.start();
	}
	
	//annonce l'arriv�e d'un nouveau Controller.client et l'ajoute � la liste des clients
	public void addClient(ConnectedClient newClient)
	{
		for(ConnectedClient client : this.clients)
		{
			Message m = new Message("Server","Le Controller.client "+ newClient.getId() + " viens de se connecter!");
			client.sendMessage(m);
		}
		this.clients.add(newClient);
	}
	
	//Envoie un message aux clients sauf a celui dont l'id est sp�cifi�
	public void broadcastMessage(Message mess, int id)
	{
		for(ConnectedClient client : this.clients)
		{
			if(client.getId() != id)
			{
				client.sendMessage(mess);
			}
		}
	}
	
	public void disconnectedClient(ConnectedClient discClient)
	{
		ConnectedClient.decreaseId();
		this.clients.remove(discClient);
		for(ConnectedClient client : clients)
		{
			client.sendMessage(new Message("Controller/server","Le Controller.client " + discClient.getId() + " nous a quitt�!"));
		}
	}
	
	public int getPort()
	{
		return this.port;
	}

}
