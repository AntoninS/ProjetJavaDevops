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

    //Son port de connexion
    private int port;
    //L'adresse de connexion
    private String address;
    //Son socket de connexion
    private Socket socket;
    // Le flux sortant de la connexion pour envoyer des messages
    private ObjectOutputStream out;
    // FLux rentrant pour les informations d'une connexion
    private ObjectInputStream in;
    //Gestionnaire de message d'un client
    private GestionnaireMessages gm;
    //Message reçu
    private Message messageReceived;
    //L'écran controller du client
    private EcranPrincipalController ec;
    //Le nom d'un client
    private String nom;

    /**
     * Constructeur d'un client
     *
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
