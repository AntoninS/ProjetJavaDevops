package Model.common.course;

import Model.common.Cheval;
import Model.common.ListCheval;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ThreadCourse implements Runnable
{

    private CourseGeneral uneCourseGeneral;

    private Double avancementDernierCheval = 0.0;
    private boolean courseEstFini;
    private static Random randomGenerator;

    @Override
    public void run() {
        randomGenerator = new Random();

        do
        {
            getAvancementDernierCheval();
            for (Cheval chevalParcourue: uneCourseGeneral.getListChevalCourse()) {
                chevalParcourue.modifierForme();

                System.out.println("Niveau avancement :" + avancementDernierCheval + " Cheval numero " + chevalParcourue.getNumero() + " Son etatDansLaCourse " + chevalParcourue.getAvancementCourse());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(!courseFini());
    }

    public ThreadCourse(String pNomCourse)
    {
        uneCourseGeneral = new CourseGeneral();
        uneCourseGeneral.setNomCourse(pNomCourse);
        GestionnaireCourses.ajouterCourse(uneCourseGeneral);
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
}