package Model.common.course;

import Model.common.course.ThreadCourse;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.UnknownHostException;

public class testCourse extends Application
{
/*
    public static void main(String[] args) throws UnknownHostException, IOException {

        ThreadCourse course= new ThreadCourse( "FCKebab");
        Thread courseThread= new Thread(course);
        courseThread.start();
       // courseThread.join();
*/

        @Override
        public void start(Stage primaryStage) {

            try {
                AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("AffichageCourse.fxml"));
                primaryStage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root, 843, 476);
//                scene.getStylesheets().add(getClass().getResource("gui/application.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }


            Button btn = new Button();
            btn.setLayoutX(10);
            btn.setText("Hello World");
            btn.setOnAction(event -> {


                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(btn.translateXProperty(), 100, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(5), kv);
                timeline.getKeyFrames().add(kf);


                timeline.setOnFinished(t -> {
                    btn.translateXProperty().set(10);
                });

                timeline.play();
            });


        }

        public static void main(String[] args) {
            launch(args);

    }
}
