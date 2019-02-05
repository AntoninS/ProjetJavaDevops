package Model.common.course;

import Model.Server.Server;
import Model.common.Cheval;
import Model.common.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ThreadCourse implements Runnable {

    private CourseGeneral uneCourseGeneral;
    private int tempsAvantLancement;
    private Double avancementDernierCheval = 0.0;
    JSONObject jsonConcatenationCourse;
    private Server server;

    @Override
    public void run() {

        tempsAvantLancement = uneCourseGeneral.TEMPS_DES_PARIS;

        do {
            try {

                if (tempsAvantLancement != 0) {

                    concatenationJsonObject();
                    tempsAvantLancement--;
                    System.out.println(tempsAvantLancement);
                } else {
                    getAvancementDernierCheval();
                    uneCourseGeneral.modifierAvancementTousLesChevaux();
                    concatenationJsonObject();
                    System.out.println(tempsAvantLancement);
                }
                Thread.sleep(1000);

            } catch (InterruptedException | JSONException e) {
                e.printStackTrace();
            }

            Message mess = new Message("Controller/client", jsonConcatenationCourse.toString());
            this.server.broadcastMessage(mess, -1);

        }
        while (!uneCourseGeneral.getEstTerminee());

    }

    public ThreadCourse(String pNomCourse, Server pServer) {
        this.server = pServer;
        uneCourseGeneral = new CourseGeneral();
        uneCourseGeneral.setNomCourse(pNomCourse);
        uneCourseGeneral.setEstTerminee(false);
    }

    public CourseGeneral getCourse() {
        return uneCourseGeneral;
    }


    private void getAvancementDernierCheval() {
        ArrayList<Double> plusPetit = new ArrayList<>();

        for (Cheval chevalParcourue : uneCourseGeneral.getListChevalCourse()) {

            plusPetit.add(chevalParcourue.getAvancementCourse());
        }
        Collections.sort(plusPetit);
        avancementDernierCheval = plusPetit.get(0);
    }

    public boolean courseFini() {
        uneCourseGeneral.setEstTerminee(false);
        if (avancementDernierCheval >= GestionnaireCourses.DISTANCE_POURCENTAGE) {

            uneCourseGeneral.setEstTerminee(true);
        }
        return uneCourseGeneral.getEstTerminee();
    }


    private void concatenationJsonObject() throws JSONException {


        int etapeAjoutJson = 0;

        jsonConcatenationCourse = new JSONObject();
        jsonConcatenationCourse.put("balise", "course");
        jsonConcatenationCourse.put("nomCourse", "uneCourse");
        jsonConcatenationCourse.put("tempsLancement", tempsAvantLancement);
        jsonConcatenationCourse.put("courseEtat", courseFini());


        for (Cheval cheval : uneCourseGeneral.getListChevalCourse()) {
            etapeAjoutJson++;
            jsonConcatenationCourse.put(Integer.toString(etapeAjoutJson), cheval.getAvancementCourse());
            jsonConcatenationCourse.put(Integer.toString(etapeAjoutJson + 10), cheval.getNumero());
            jsonConcatenationCourse.put(Integer.toString(etapeAjoutJson + 20), cheval.getVitesse());
            jsonConcatenationCourse.put(Integer.toString(etapeAjoutJson + 30), cheval.getNom());

        }
    }
}
