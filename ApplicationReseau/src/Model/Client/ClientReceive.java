package Model.Client;

import Model.common.GestionnaireMessages;
import Model.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable{
	
	private Client client;
	private Socket socket;
	private ObjectInputStream in;
	
	public ClientReceive(Client client, Socket socket)
	{
		this.client = client;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		Message mess; 
		
		try {
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean isActive = true;
		while(isActive)
		{
			try
			{
				mess = (Message) in.readObject();
				if(mess !=null)
				{
					GestionnaireMessages gm = new GestionnaireMessages();
					gm.gestionMessage(mess);
					this.client.messageReceived(mess);
				}
				else
				{
					isActive = false;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		client.disconnectedServer();
		
	}

}
