package Model.common.Message;

import java.io.Serializable;

public class Message implements Serializable {

    private String sender;
    private String content;
    private int id;

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
