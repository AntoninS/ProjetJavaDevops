package Model.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.json.JSONException;

import Model.Server.Server;
import Model.common.Message;

public class ConnectedClient implements Runnable{
	
	private static int idCounter;
	private int id;
	private Server server;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ConnectedClient(Server server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
		
		this.idCounter++;
		this.id = this.idCounter;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Nouvelle connexion, id = " + this.id);
		
	}

	//réceptionne les messages du Controller.client
	@Override
	public void run() 
	{
		boolean isActive = true;
		try 
		{
			this.in = new ObjectInputStream(this.socket.getInputStream());
			isActive = true;
			while(isActive)
			{
				Message mess = (Message) this.in.readObject();
				if(mess != null)
				{
					mess.setId(this.id);
					this.server.broadcastMessage(mess, this.id);
				}
				else
				{
					this.server.disconnectedClient(this);
					isActive = false;
				}
			}
		}
		catch(Exception e)
		{
			isActive = false;
			try {
				this.server.disconnectedClient(this);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.getStackTrace();
		}
	}
	
	//envoie le message au Controller.client en utlisant out
	public void sendMessage(Message m)
	{
		try {
			this.out.writeObject(m);
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//ferme flux int et out ainsi que le socket
	public void closeClient()
	{
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public static void decreaseId()
	{
		idCounter --;
	}
	
}
