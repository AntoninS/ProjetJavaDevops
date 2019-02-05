package View.gui;

import Model.common.Cheval;
import Model.common.course.GestionnaireCourses;
import Model.common.course.UtilCourse;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class CourseController {

    private ImageView img;

    private int stepsdada;

    private List<ImageView> test;

    private List<Cheval> listeChevalCourse;

    private static boolean affichageActif = false;

    private EcranPrincipalController ecranController;

    @FXML
    private ImageView chevalLigne1;
    @FXML
    private ImageView chevalLigne2;
    @FXML
    private ImageView chevalLigne3;
    @FXML
    private ImageView chevalLigne4;
    @FXML
    private ImageView chevalLigne5;
    @FXML
    private ImageView chevalLigne6;


    @FXML
    public void initialize() {
        affichageActif = true;
        stepsdada = 0;

        lancerThreadDeplacement();
    }

    private void lancerThreadDeplacement() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                listeChevalCourse = ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getListChevalCourse();

                test();
                attribuerChevalImage(listeChevalCourse, test);

                for (int i = 0; i < 100; i++) {

                    Platform.runLater(new Runnable() {
                        public void run() {
                            /*
                            while(ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getTempsLancement() != 0)
                            {
                                System.out.println("test");

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                            */
                            listeChevalCourse = ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getListChevalCourse();

                            for (Cheval ch : listeChevalCourse) {
                                System.out.println("Cheval numero " + ch.getNumero() + "Avancement " + ch.getAvancementCourse());
                                if (stepsdada < 6 /*&& !ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getEstTerminee() && ch.getAvancementCourse() <= UtilCourse.DISTANCE_POURCENTAGE*/) {
                                    lancerTranslation(ch);
                                } /*else if (ch.getAvancementCourse() > UtilCourse.DISTANCE_POURCENTAGE && stepsdada < UtilCourse.nombreChevauxCourse) {
                                    ch.getImageCheval().setLayoutX(UtilCourse.LONGUEUR_DIFF_PLUS_PIXEL);
                                }*/
                                stepsdada++;
                                System.out.println("Position " + ch.getPosition());
                            }
                        }
                    });
                    if (ecranController.getGestionnaireMessaire().getGc().getListeDesCoursesEnCours().get(0).getEstTerminee() || !affichageActif) {
                        System.out.println("La course d'affichage est fini");
                        break;
                    }

                    Thread.sleep(1000);
                    System.out.println("AfficahgeInc");


                }
                return null;
            }
        };
        new Thread(task).start();
    }

    private void test() {
        test = new ArrayList<>();
        test.add(chevalLigne1);
        test.add(chevalLigne2);
        test.add(chevalLigne3);
        test.add(chevalLigne4);
        test.add(chevalLigne5);
        test.add(chevalLigne6);
    }


    private void attribuerChevalImage(List<Cheval> listeDeCheval, List<ImageView> listeImageCheval) {
        for (int pos = 0; pos < UtilCourse.nombreChevauxCourse; pos++) {
            listeDeCheval.get(pos).setImageCheva(listeImageCheval.get(pos));
        }
    }

    public void lancerTranslation(Cheval cheval) {
        if (cheval != null) {
            img = cheval.getImageCheval();
            img.setLayoutX(UtilCourse.calculAffichagePosition(cheval.getAvancementCourse()));

            if (img != null) {
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(img.translateXProperty(), UtilCourse.LONGUEUR_DIFF_PLUS_PIXEL - (UtilCourse.calculAffichagePosition(cheval.getAvancementCourse())), Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(UtilCourse.calculVitesseCheval(cheval.getAvancementCourse(), cheval.getVitesse())), kv);
                timeline.getKeyFrames().add(kf);

                timeline.play();
            }
        }
    }

    public static void setAffichageActif(boolean pAffichageActif) {
        affichageActif = pAffichageActif;
    }

    public void setEcranController (EcranPrincipalController ec)
    {
       this.ecranController = ec;
    }
}
