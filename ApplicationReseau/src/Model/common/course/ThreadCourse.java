package Model.common.course;

import Model.Server.Server;
import Model.common.Cheval;
import Model.common.Message;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private List<Cheval> classement = new ArrayList<>();

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

        System.out.println("CHEVAL 1 : " + classement.get(0).getNumero() + " - " + classement.get(0).getNom());
        System.out.println("CHEVAL 2 : " + classement.get(1).getNumero() + " - " + classement.get(1).getNom());
        System.out.println("CHEVAL 3 : " + classement.get(2).getNumero() + " - " + classement.get(2).getNom());


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
        if (avancementDernierCheval >= UtilCourse.DISTANCE_POURCENTAGE) {

            uneCourseGeneral.setEstTerminee(true);
        }
        return uneCourseGeneral.getEstTerminee();
    }


    private void concatenationJsonObject() throws JSONException {


        int etapeAjoutJson = 0;
        int idPremierCheval = -1;
        int idSecondCheval = -1;
        int idTroisiemeCheval = -1;

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


            if (cheval.hasEndedRace() && !classement.contains(cheval)) {
                classement.add(cheval);
                cheval.setPositionInRace(classement.indexOf(cheval)+1);
            }

            if (cheval.getPositionInRace() == 1) {
                idPremierCheval = cheval.getNumero();
            } else if (cheval.getPositionInRace() == 2) {
                idSecondCheval = cheval.getNumero();
            } else if (cheval.getPositionInRace() == 3) {
                idTroisiemeCheval = cheval.getNumero();
            }

            jsonConcatenationCourse.put("idChevalAtClassement1", idPremierCheval);
            jsonConcatenationCourse.put("idChevalAtClassement2", idSecondCheval);
            jsonConcatenationCourse.put("idChevalAtClassement3", idTroisiemeCheval);
        }
    }
}
