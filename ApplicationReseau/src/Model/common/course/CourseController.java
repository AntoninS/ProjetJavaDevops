package Model.common.course;

import Model.common.Cheval;
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
        listeChevalCourse = GestionnaireCourses.getListeDesCoursesEnCours().get(0).getListChevalCourse();

        while(GestionnaireCourses.getListeDesCoursesEnCours().get(0).getTempsLancement() != 0)
        {
            System.out.println();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        lancerThreadDeplacement();
    }

    private void lancerThreadDeplacement() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                test();
                attribuerChevalImage(listeChevalCourse, test);

                for (int i = 0; i < 100; i++) {

                    Platform.runLater(new Runnable() {
                        public void run() {
                            listeChevalCourse = GestionnaireCourses.getListeDesCoursesEnCours().get(0).getListChevalCourse();
                            for (Cheval ch : listeChevalCourse) {
                                System.out.println("Cheval numero " + ch.getNumero() + "Avancement " + ch.getAvancementCourse());
                                if (stepsdada < 6 && !GestionnaireCourses.getListeDesCoursesEnCours().get(0).getEstTerminee() && ch.getAvancementCourse() <= GestionnaireCourses.DISTANCE_POURCENTAGE) {
                                    lancerTranslation(ch);
                                } else if (ch.getAvancementCourse() > GestionnaireCourses.DISTANCE_POURCENTAGE && stepsdada < 6) {
                                    ch.getImageCheval().setLayoutX(550.0);
                                }
                                stepsdada++;
                                System.out.println("Position " + ch.getPosition());
                            }
                        }
                    });
                    if (GestionnaireCourses.getListeDesCoursesEnCours().get(0).getEstTerminee() || !affichageActif) {
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
        for (int pos = 0; pos < 6; pos++) {
            listeDeCheval.get(pos).setImageCheva(listeImageCheval.get(pos));
        }
    }

    public void lancerTranslation(Cheval cheval) {
        if (cheval != null) {
            img = cheval.getImageCheval();
            img.setLayoutX(GestionnaireCourses.calculAffichagePosition(cheval.getAvancementCourse()));

            if (img != null) {
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(img.translateXProperty(), 550.0 - (GestionnaireCourses.calculAffichagePosition(cheval.getAvancementCourse())), Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(GestionnaireCourses.calculVitesseCheval(cheval.getAvancementCourse(), cheval.getVitesse())), kv);
                timeline.getKeyFrames().add(kf);

                timeline.play();
            }
        }
    }

    public static void setAffichageActif(boolean pAffichageActif) {
        affichageActif = pAffichageActif;
    }
}
