package Model.Client;

import Model.common.Cheval;
import Model.common.course.UtilCourse;
import View.gui.CourseController;
import View.gui.EcranPrincipalController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class ThreadCourseGraphique  implements Runnable {

    /** Liste des chevaux de la course */
    private List<Cheval> listeChevauxCourse;

    /** index de cheval permettant de faire qu'une itération dans l'affichage graphique */
    private int chevalIndex;

    /** Ecran controller pour récuperer les informations de l'affichage */
    private EcranPrincipalController ecranController;

    /** Permet de lancer la translation des cheavaux de manière graphique */
    private CourseController courseController;

    /** Thread permetant d'afficher les chevaux sans bloquer toute l'application */
    public ThreadCourseGraphique  (EcranPrincipalController pEcranController, CourseController pCourseController)
    {
        ecranController = pEcranController;
        courseController = pCourseController;
    }

    @Override
    public void run()
    {
        listeChevauxCourse = ecranController.getCourse().getListChevalCourse();

        courseController.attribuerChevalImage(listeChevauxCourse, courseController.getListImageViewChevaux());

        while(ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getTempsLancement() != 0){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(ecranController.getGestionnaireMessaire().getGc().getUneCourseEstEnCours() && CourseController.affichageActif)
        {
                    listeChevauxCourse = ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getListChevalCourse();

                    for (Cheval ch : listeChevauxCourse) {
                        System.out.println("Cheval numero " + ch.getNumero() + "Avancement " + ch.getAvancementCourse());
                        if (chevalIndex < UtilCourse.NOMBRE_CHEVAUX_COURSE &&
                                !ecranController.getCourse().getEstTerminee() &&
                                ch.getAvancementCourse() <= UtilCourse.DISTANCE_POURCENTAGE &&
                                ecranController.getCourse().getTempsLancement() == 0 )
                        {
                            courseController.lancerTranslation(ch);
                        }
                        else if (ch.getAvancementCourse() > UtilCourse.DISTANCE_POURCENTAGE
                         && chevalIndex < UtilCourse.NOMBRE_CHEVAUX_COURSE
                         && ecranController.getCourse().getTempsLancement() == 0)
                        {
                            ch.getImageCheval().setLayoutX(UtilCourse.LONGUEUR_DIFF_PLUS_PIXEL);
                        }
                        chevalIndex++;

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

        }

        System.out.println("La course d'affichage est fini");

        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to format your system?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {

                    }
                }
        );

        }
    }

