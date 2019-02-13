package Controller.server;

import Model.Server.Server;
import Model.common.Cheval.Cheval;
import Model.common.Message.Message;
import Model.common.course.CourseGeneral;
import Model.common.course.UtilCourse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadCourse implements Runnable {

    //Implementation d'une course general, si on avait évolué le projet nous auriont du évoluer cette partie pour choisir le service threader pour la course
    private CourseGeneral uneCourseGeneral;
    //Temps avant le lancement d'une course
    private int tempsAvantLancement;
    //avancement d'une dernier cheval
    private Double avancementDernierCheval = 0.0;
    //Le json concaténer
    private JSONObject jsonConcatenationCourse;
    //Le server
    private Server server;
    //Classement des chevaux
    private List<Cheval> classement = new ArrayList<>();

    //Thread d'une course graphique
    public ThreadCourse(String pNomCourse, Server pServer) {
        this.server = pServer;
        uneCourseGeneral = new CourseGeneral();
        uneCourseGeneral.setNomCourse(pNomCourse);
        uneCourseGeneral.setEstTerminee(false);
    }

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
    public CourseGeneral getCourse() {
        return uneCourseGeneral;
    }

    //Récuperer l'avancement du dernier cheval
    private void getAvancementDernierCheval() {
        ArrayList<Double> plusPetit = new ArrayList<>();

        for (Cheval chevalParcourue : uneCourseGeneral.getListeChevauxCourse()) {

            plusPetit.add(chevalParcourue.getAvancementCourse());
        }
        Collections.sort(plusPetit);
        avancementDernierCheval = plusPetit.get(0);
    }

    //La course est terminé
    public boolean courseFini() {
        uneCourseGeneral.setEstTerminee(false);
        if (avancementDernierCheval >= UtilCourse.DISTANCE_POURCENTAGE) {

            uneCourseGeneral.setEstTerminee(true);
        }
        return uneCourseGeneral.getEstTerminee();
    }

    //Permet de concaténer une course en Json pour l'envoyer sur le serveuer
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


        for (Cheval cheval : uneCourseGeneral.getListeChevauxCourse()) {
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
