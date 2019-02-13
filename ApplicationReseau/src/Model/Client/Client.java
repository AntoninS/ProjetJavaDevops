package Model.Client;

import Controller.client.ClientReceive;
import Controller.client.EcranPrincipalController;
import Controller.client.GestionnaireMessages;
import Model.common.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    // le port d'un serveur
    private int port;
    // Adress d'un serveur
    private String address;
    //Le socket d'un serveur
    private Socket socket;
    //Le flux sortant
    private ObjectOutputStream out;
    // Le flux entrant
    private ObjectInputStream in;
    // Un gestionnaire de message pour recevoir la course
    private GestionnaireMessages gm;
    // Un message reçu
    private Message messageReceived;
    //Un ecran principale
    private EcranPrincipalController ec;
    //Un nom
    private String nom;

    /***
     * Constructeur d'un client
     * @param port
     * @param address
     * @param nom
     * @param gestionnaireMessages
     */
    public Client(int port, String address, String nom, GestionnaireMessages gestionnaireMessages) {
        this.port = port;
        this.address = address;
        this.gm = gestionnaireMessages;
        this.nom = nom;

        try {
            this.socket = new Socket(address, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Bonjour " + this.nom);

        Thread getMessages = new Thread(new ClientReceive(this, socket));
        getMessages.start();
    }

    //serveur qui se déco
    public void disconnectedServer() {

        try {
            this.out.close();
            this.socket.close();
            if (this.in != null)
                this.in.close();
            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Message reçu
    public Message messageReceived(Message mess) {
        this.messageReceived = mess;

        return this.messageReceived;
    }

    public ObjectOutputStream getOutPutStream() {
        return this.out;
    }

    public ObjectInputStream getInputStream() {
        return this.in;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public String getNom() {
        return this.nom;
    }

    public GestionnaireMessages getGm() {
        return gm;
    }

    public EcranPrincipalController getEc() {
        return ec;
    }

    public void setEc(EcranPrincipalController ec) {
        this.ec = ec;
    }
}
