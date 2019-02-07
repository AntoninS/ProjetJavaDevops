package Model.common;

import Model.common.course.GestionnaireCourses;
import View.gui.EcranPrincipalController;

import org.json.JSONException;
import org.json.JSONObject;


public class GestionnaireMessages {

    GestionnaireCourses gc;
    EcranPrincipalController controller;

    public void gestionMessage(Message msg) throws JSONException {

        JSONObject jresponse = new JSONObject(msg.toString());
        String balise = jresponse.getString("balise");

        if(balise.equals("course"))
        {
            gc.gererCourse(jresponse);
        }
        
        if(balise.equals("message"))
        {
        	this.controller.getTchatField().appendText("\n"+jresponse.getString("messageEnvoye"));;
        }
        
    }

    public void setGc(GestionnaireCourses gc) {
        this.gc = gc;
    }

    public GestionnaireCourses getGc() {
        return gc;
    }
    
    public void setController(EcranPrincipalController ecranController)
    {
    	this.controller = ecranController;
    }
}
