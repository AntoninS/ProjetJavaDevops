package Model.Server;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Model.common.Message.Message;

public class Server {

    private List<ConnectedClient> clients;
    // port du serveur
    private int port;

    /**
     * Constructeur d'un server
     * @param port
     */
    public Server(int port) {
        this.clients = new ArrayList<ConnectedClient>();
        this.port = port;

        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    /**
     * Annonce l'arrivé d'un nouveau Controller.client et l'ajoute � la liste des clients
     * @param newClient
     * @throws JSONException
     */
    public void addClient(ConnectedClient newClient) throws JSONException {
        for (ConnectedClient client : this.clients) {
            JSONObject messageConnexion = new JSONObject();
            messageConnexion.put("balise", "message");
            messageConnexion.put("nom", "Serveur");
            messageConnexion.put("messageEnvoye", "Une nouvelle personne viens de nous rejoindre, dite bonjour !");
            Message m = new Message("Serveur", messageConnexion.toString());
            client.sendMessage(m);
        }
        this.clients.add(newClient);
    }

    //Envoie un message aux clients sauf a celui dont l'id est sp�cifi�
    public void broadcastMessage(Message mess, int id) {
        for (ConnectedClient client : this.clients) {
            if (client.getId() != id) {
                client.sendMessage(mess);
            }
        }
    }

    //Deconnexion d'un client
    public void disconnectedClient(ConnectedClient discClient) throws JSONException {
        ConnectedClient.decreaseId();
        this.clients.remove(discClient);
        for (ConnectedClient client : clients) {
            JSONObject messageDeco = new JSONObject();
            messageDeco.put("balise", "message");
            messageDeco.put("nom", "Serveur");
            messageDeco.put("messageEnvoye", "Une personne viens de nous quitter !");
            Message m = new Message("Serveur", messageDeco.toString());
            client.sendMessage(m);
        }
    }

    public int getPort() {
        return this.port;
    }
}
