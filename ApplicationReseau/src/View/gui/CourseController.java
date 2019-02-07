package View.gui;

import Model.Client.ThreadCourseGraphique;
import Model.common.Cheval;
import Model.common.course.Course;
import Model.common.course.UtilCourse;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CourseController implements Initializable {

    private ImageView img;


    private List<ImageView> listImagesChevaux;


    private static boolean affichageActif = false;

    private EcranPrincipalController ecranController;

    private ThreadCourseGraphique courseGraphique;

    private CourseController cc;


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
    private AnchorPane ap;

    Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cc = this;
        lancementThread1();

    }

    private void lancementThread() {
        affichageActif = true;
        cc = this;
        courseGraphique = new ThreadCourseGraphique(ecranController,this);
        Thread courseThread = new Thread(courseGraphique);
        courseThread.start();
    }




    private void lancementThread1() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    public void run() {

                        affichageActif = true;
                        courseGraphique = new ThreadCourseGraphique(ecranController,cc);
                        Thread courseThread = new Thread(courseGraphique);
                        courseThread.start();
                    }

                });
                return null;
            }

        };
        new Thread(task).start();
    }

    public List<ImageView> getListImageViewChevaux() {
        listImagesChevaux = new ArrayList<>();
        listImagesChevaux.add(chevalLigne1);
        listImagesChevaux.add(chevalLigne2);
        listImagesChevaux.add(chevalLigne3);
        listImagesChevaux.add(chevalLigne4);
        listImagesChevaux.add(chevalLigne5);
        listImagesChevaux.add(chevalLigne6);
        return listImagesChevaux;
    }


    public void attribuerChevalImage(List<Cheval> listeDeCheval, List<ImageView> listeImageCheval) {
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
