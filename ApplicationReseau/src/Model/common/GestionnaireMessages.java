package Model.common;

import Model.common.course.GestionnaireCourses;
import org.json.JSONException;
import org.json.JSONObject;


public class GestionnaireMessages {

    GestionnaireCourses gc;
    public void gestionMessage(Message msg) throws JSONException {


        JSONObject jresponse = new JSONObject(msg.toString());
        String balise = jresponse.getString("balise");

        if(balise.equals("course"))
        {
            gc.gererCourse(jresponse);
        }
    }

    public void setGc(GestionnaireCourses gc) {
        this.gc = gc;
    }

    public GestionnaireCourses getGc() {
        return gc;
    }
}
