package Controller.client;

import Model.common.Message.Message;
import org.json.JSONException;
import org.json.JSONObject;

public class GestionnaireMessages {

    /**
     * gestionnaire de course pour traiter le jsonObject d'une course
     */
    GestionnaireCourses gc;

    /**
     * reference de l'ecran principale pour y faire passer nos valeurs
     */
    EcranPrincipalController controller;

    public void gestionMessage(Message msg) throws JSONException {

        JSONObject jresponse = new JSONObject(msg.toString());
        String balise = jresponse.getString("balise");

        if (balise.equals("course")) {
            gc.gererCourse(jresponse);
        }

        if (balise.equals("message")) {
            this.controller.getTchatField().appendText("\n" + jresponse.getString("nom") + " : " + jresponse.getString("messageEnvoye"));
        }
    }

    public void setGc(GestionnaireCourses gc) {
        this.gc = gc;
    }

    public GestionnaireCourses getGc() {
        return gc;
    }

    public void setController(EcranPrincipalController ecranController) {
        this.controller = ecranController;
    }
}
