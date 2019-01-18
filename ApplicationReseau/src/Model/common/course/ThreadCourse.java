package Model.common.course;

import Model.Server.Server;
import Model.common.Cheval;
import Model.common.Message;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ThreadCourse implements Runnable
{

    private CourseGeneral uneCourseGeneral;

    private Double avancementDernierCheval = 0.0;
    private boolean courseEstFini;
    JSONObject jsonConcatenationCourse;
    private static Random randomGenerator;
    private Server server;

    @Override
    public void run() {
        randomGenerator = new Random();

        do
        {
            getAvancementDernierCheval();
            int etapeAjoutJson = 0;

            try {
                concatenationJsonObject(etapeAjoutJson,null,null);
                System.out.print(etapeAjoutJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (Cheval chevalParcourue: uneCourseGeneral.getListChevalCourse()) {
                etapeAjoutJson++;

                chevalParcourue.modifierForme();
                try {
                    concatenationJsonObject(etapeAjoutJson, chevalParcourue.getNumero(), chevalParcourue.getAvancementCourse());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
           }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message mess = new Message("Controller/client", jsonConcatenationCourse.toString());
            this.server.broadcastMessage(mess, -1);

        }

        while(!courseFini());
    }

    public ThreadCourse(String pNomCourse, Server pServer)
    {
        this.server = pServer;
        uneCourseGeneral = new CourseGeneral();
        uneCourseGeneral.setNomCourse(pNomCourse);
    }

    public CourseGeneral getCourse()
    {
        return uneCourseGeneral;
    }


    private void getAvancementDernierCheval()
    {
        ArrayList<Double> plusPetit =new ArrayList<>();

       for (Cheval chevalParcourue: uneCourseGeneral.getListChevalCourse()) {

          plusPetit.add(chevalParcourue.getAvancementCourse());
        }
       Collections.sort(plusPetit);
       avancementDernierCheval = plusPetit.get(0);
    }

    public boolean courseFini()
    {
        courseEstFini = false;
        if(avancementDernierCheval >= GestionnaireCourses.DISTANCE_POURCENTAGE)
        {
            courseEstFini = true;
            GestionnaireCourses.courseFini(uneCourseGeneral);
        }
        return courseEstFini;
    }

    private void concatenationJsonObject(int pEtape, Integer pNumero, Double pEtatDansLaCourse) throws JSONException {
        switch(pEtape)
        {
            case 0:
                jsonConcatenationCourse = new JSONObject();
                jsonConcatenationCourse.put("balise", "course");
                jsonConcatenationCourse.put("nomCourse", "uneCourse");
                break;
            case 1:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                break;
            case 2:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                break;
            case 3:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                break;
            case 4:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                break;
            case 5:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape + 10), pNumero);
                break;
            case 6:
                jsonConcatenationCourse.put(Integer.toString(pEtape), pEtatDansLaCourse);
                jsonConcatenationCourse.put(Integer.toString(pEtape+ 10), pNumero);
                break;
            case 7 :

                break;
        }
    }
}
