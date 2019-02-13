package Controller.client;

import Model.common.Cheval.Cheval;
import Model.common.course.UtilCourse;

import java.util.List;

public class ThreadCourseGraphique implements Runnable {

    /**
     * Liste des chevaux de la course
     */
    private List<Cheval> listeChevauxCourse;

    /**
     * index de cheval permettant de faire qu'une itération dans l'affichage graphique
     */
    private int chevalIndex;

    /**
     * Ecran controller pour récuperer les informations de l'affichage
     */
    private EcranPrincipalController ecranController;

    /**
     * Permet de lancer la translation des cheavaux de manière graphique
     */
    private CourseController courseController;

    /**
     * Thread permetant d'afficher les chevaux sans bloquer toute l'application
     */
    public ThreadCourseGraphique(EcranPrincipalController pEcranController, CourseController pCourseController) {
        ecranController = pEcranController;
        courseController = pCourseController;
    }

    @Override
    public void run() {
        listeChevauxCourse = ecranController.getCourse().getListChevalCourse();

        courseController.attribuerChevalImage(listeChevauxCourse, courseController.getListImageViewChevaux());

        while (ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getTempsLancement() != 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (ecranController.getGestionnaireMessaire().getGc().getUneCourseEstEnCours() && CourseController.affichageActif) {
            listeChevauxCourse = ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getListChevalCourse();

            for (Cheval ch : listeChevauxCourse) {
                System.out.println("Cheval numero " + ch.getNumero() + "Avancement " + ch.getAvancementCourse());
                if (chevalIndex < UtilCourse.NOMBRE_CHEVAUX_COURSE &&
                        !ecranController.getCourse().getEstTerminee() &&
                        ch.getAvancementCourse() <= UtilCourse.DISTANCE_POURCENTAGE &&
                        ecranController.getCourse().getTempsLancement() == 0) {
                    courseController.lancerTranslation(ch);
                } else if (ch.getAvancementCourse() > UtilCourse.DISTANCE_POURCENTAGE
                        && chevalIndex < UtilCourse.NOMBRE_CHEVAUX_COURSE
                        && ecranController.getCourse().getTempsLancement() == 0) {
                    ch.getImageCheval().setLayoutX(UtilCourse.LONGUEUR_DIFF_PLUS_PIXEL);
                }
                chevalIndex++;
            }
            //Ceci aurait du être dans une méthode mais par contrainte de temps ne sera pas faite
            if (null != ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).chevalArriver.get(0)) {
                courseController.updateAffichageCoupe(ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).chevalArriver.get(0), 0);
            }

            if (null != ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).chevalArriver.get(1)) {
                courseController.updateAffichageCoupe(ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).chevalArriver.get(1), 1);
            }
            if (null != ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).chevalArriver.get(2)) {
                courseController.updateAffichageCoupe(ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).chevalArriver.get(2), 2);
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

