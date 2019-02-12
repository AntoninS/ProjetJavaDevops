package Controller.client;

import Model.Client.Client;
import Model.common.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable{
	
	private Client client;
	private Socket socket;
	private ObjectInputStream in;
	private boolean isActive = true;
	
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
			e.printStackTrace();isActive = false;
		}

		while(isActive)
		{
			try
			{
				mess = (Message) in.readObject();
					if(mess !=null)
					{
						client.getGm().gestionMessage(mess);
						this.client.messageReceived(mess);
						System.out.println("Message re�u : "+ mess);
					}
					else
					{
						isActive = false;
					}

			}
			catch(Exception e)
			{
				isActive = false;
			}
		}
		for(int i = 0; i < 5 ; i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			client.getEc().onestla(4-i);
		}
		client.disconnectedServer();
	}

	public boolean getEstActif()
	{
		return isActive;
	}


}
