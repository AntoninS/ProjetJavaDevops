package Model.Client;

import Model.common.Cheval;
import Model.common.course.Course;
import Model.common.course.UtilCourse;
import View.gui.CourseController;
import View.gui.EcranPrincipalController;
import javafx.application.Platform;

import java.util.List;

public class ThreadCourseGraphique  implements Runnable {

    private static boolean affichageActif = false;

    private List<Cheval> listeChevalCourse;

    private int chevalIndex;

    private Course course;

    private EcranPrincipalController ecranController;

    private CourseController courseController;


    public ThreadCourseGraphique  (EcranPrincipalController pEcranController, CourseController pCourseController)
    {
        course = pEcranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0);
        ecranController = pEcranController;
        courseController = pCourseController;
    }

    @Override
    public void run()
    {
        listeChevalCourse = ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getListChevalCourse();

        courseController.attribuerChevalImage(listeChevalCourse, courseController.getListImageViewChevaux());

        while(ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getTempsLancement() != 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(ecranController.getGestionnaireMessaire().getGc().getUneCourseEstEnCours())
        {
                    listeChevalCourse = ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getListChevalCourse();

                    for (Cheval ch : listeChevalCourse) {
                        System.out.println("Cheval numero " + ch.getNumero() + "Avancement " + ch.getAvancementCourse());
                        if (chevalIndex < UtilCourse.nombreChevauxCourse  && !ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getEstTerminee() && ch.getAvancementCourse() <= UtilCourse.DISTANCE_POURCENTAGE && ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getTempsLancement() == 0 )
                        {
                            courseController.lancerTranslation(ch);
                            chevalIndex++;
                        }
                        else if (ch.getAvancementCourse() > UtilCourse.DISTANCE_POURCENTAGE && chevalIndex < UtilCourse.nombreChevauxCourse && ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getTempsLancement() == 0)
                        {
                            ch.getImageCheval().setLayoutX(UtilCourse.LONGUEUR_DIFF_PLUS_PIXEL);
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

        }

        System.out.println("La course d'affichage est fini");
        }
    }

