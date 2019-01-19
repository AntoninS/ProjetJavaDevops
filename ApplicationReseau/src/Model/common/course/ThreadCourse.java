package Model.common.course;

import Model.Server.Server;
import Model.common.Cheval;
import Model.common.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ThreadCourse implements Runnable {

    private CourseGeneral uneCourseGeneral;

    private Double avancementDernierCheval = 0.0;
    JSONObject jsonConcatenationCourse;
    private Random randomGenerator;
    private Server server;

    @Override
    public void run() {
        try {
            randomGenerator = new Random();

            do {
                getAvancementDernierCheval();
                int etapeAjoutJson = 0;

                concatenationJsonObject(etapeAjoutJson, null, null, null);
                System.out.print(etapeAjoutJson);

                for (Cheval chevalParcourue : uneCourseGeneral.getListChevalCourse()) {
                    etapeAjoutJson++;
                    chevalParcourue.modifierForme();
                    concatenationJsonObject(etapeAjoutJson, chevalParcourue.getNumero(), chevalParcourue.getAvancementCourse(), chevalParcourue.getVitesse());
                }

                Thread.sleep(1000);

                Message mess = new Message("Controller/client", jsonConcatenationCourse.toString());
                this.server.broadcastMessage(mess, -1);

            }
            while (!uneCourseGeneral.getEstTerminee());


        } catch (JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ThreadCourse(String pNomCourse, Server pServer) {
        this.server = pServer;
        uneCourseGeneral = new CourseGeneral();
        uneCourseGeneral.setNomCourse(pNomCourse);
        uneCourseGeneral.setEstTerminee(true);
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

    private void concatenationJsonObject(int pEtape, Integer pNumero, Double pEtatDansLaCourse, Double pVitesse) throws JSONException {
        switch (pEtape) {
            case 0:
                jsonConcatenationCourse = new JSONObject();
                jsonConcatenationCourse.put("balise", "course");
                jsonConcatenationCourse.put("nomCourse", "uneCourse");
                jsonConcatenationCourse.put("courseEtat", courseFini());

                break;
            case 1:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 20), pVitesse);
                break;
            case 2:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 20), pVitesse);
                break;
            case 3:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 20), pVitesse);
                break;
            case 4:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 20), pVitesse);
                break;
            case 5:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 20), pVitesse);
                break;
            case 6:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 20), pVitesse);
                break;
            case 7:

                break;
        }
    }
}
