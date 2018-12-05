package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import common.Message;

public class Client {
	
	private int port;
	private String address;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Message message;
	
	public Client(int port, String address)
	{
		this.port = port;
		this.address = address;
		
		try {
			this.socket = new Socket(address, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread sendMessages = new Thread(new ClientSend(this.socket, this.out));
		sendMessages.start();
		
		Thread getMessages = new Thread(new ClientReceive(this, socket));
		getMessages.start();
		
	}
	
	public void disconnectedServer()
	{
		
		try {
			this.out.close();
			this.socket.close();
			if(this.in != null)
				this.in.close();
			System.exit(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Message messageReceived(Message mess)
	{
		this.message = mess;
		
		return this.message;
	}

}
