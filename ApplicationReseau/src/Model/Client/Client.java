package Model.Client;

import Model.common.GestionnaireMessages;
import Model.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.jfoenix.controls.JFXTextArea;

import Model.common.Message;

public class Client {
	
	private int port;
	private String address;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Message message;
	private GestionnaireMessages gm;
	private Message messageReceived;
	private String nom;
	
	public Client(int port, String address, String nom, GestionnaireMessages gestionnaireMessages)
	{
		this.port = port;
		this.address = address;
		this.gm = gestionnaireMessages;
		this.nom = nom;
		
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
		
		System.out.println("Bonjour " + this.nom);
		
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
		this.messageReceived = mess;
		
		return this.messageReceived;
	}
	
	public ObjectOutputStream getOutPutStream()
	{
		return this.out;
	}
	
	public ObjectInputStream getInputStream()
	{
		return this.in;
	}
	
	public Socket getSocket()
	{
		return this.socket;
	}
	
	public String getNom()
	{
		return this.nom;
	}

	public GestionnaireMessages getGm() {
		return gm;
	}
}
