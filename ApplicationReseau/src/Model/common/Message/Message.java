package Model.common.Message;

import java.io.Serializable;

public class Message implements Serializable {

    //Personne qui envoi le message
    private String sender;
    //Le contenu du message 
    private String content;
    private int id;

    /**
     * Constructeur d'un message
     * @param sender
     * @param content
     */
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String toString() {
        String res = this.content;

        return res;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIdCounter() {
        if (this.content.contains("nbPersonneCo"))
            return true;

        return false;
    }

}
