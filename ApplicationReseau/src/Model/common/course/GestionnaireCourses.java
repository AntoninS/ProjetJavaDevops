package Model.common.course;

import Model.common.Cheval;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireCourses {
    private static List<Course> listeDesCoursesEnCours = null;
    private static List<Course> listeDesCoursesFini = null;

    //Cette distance est un pourcentage ramené a 10000 au lieu de 100 pour eviter des problèmes au niveau de l'affichage graphique
    public final static Double DISTANCE_POURCENTAGE = 10000.0;
    //La distance en pixel de la course
    public final static Double LONGUEUR_PIXEL_COURSE = 500.0;
    //Différence entre le bord de la fenetre est le début de la course
    public final static Double LONG_DIFF_FEN_DEB_COURSE = 50.0;

    public List<Course> getListeDesCourses() {
        return listeDesCoursesEnCours;
    }

    //On ajoute a la liste des courses en cours
    public static void ajouterCourse(Course pCourse) {
        if (listeDesCoursesEnCours == null) {
            listeDesCoursesEnCours = new ArrayList<>();
        }
        listeDesCoursesEnCours.add(pCourse);
    }

    // Quand une course est fini on la supprime des courses en cours et on l'ajoute aux courses fini
    public static void courseFini(Course pCourse) {
        for (Course uneCourse : listeDesCoursesEnCours) {
            if (uneCourse.equals(pCourse)) {
                listeDesCoursesEnCours.remove(uneCourse);
                if (listeDesCoursesFini == null) {
                    listeDesCoursesFini = new ArrayList<>();
                }
                listeDesCoursesFini.add(uneCourse);
                break;
            }
        }
    }

    /**
     * On vient ici créer ou modifier les données de la course qu'on reçoit
     */
    public static void creerCourse(JSONObject courseJsonObject) throws JSONException {

        String nomDeLaCourse = courseJsonObject.getString("nomCourse");
        boolean courseExistente = false;
        Course course = new CourseGeneral();

        List<Cheval> listeCh = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            Cheval ch = new Cheval(courseJsonObject.getDouble(Integer.toString(i)), courseJsonObject.getInt(Integer.toString(i + 10)), courseJsonObject.getDouble(Integer.toString(i + 20)));
            listeCh.add(ch);
        }

        if (listeDesCoursesEnCours == null) {
            listeDesCoursesEnCours = new ArrayList<>();
        }

        courseExistente = false;
        for (Course cs : listeDesCoursesEnCours) {
            if (cs.getNomCourse().equals(nomDeLaCourse)) {
                courseExistente = true;
                course = cs;
                break;
            }
        }
        if (courseExistente) {
            course.setListChevalCourse(listeCh);
            if (courseJsonObject.getBoolean("courseEtat")) {
                courseFini(course);
            }
        } else {
            course.setListChevalCourse(listeCh);
            course.setNomCourse(nomDeLaCourse);
            course.setEstTerminee(false);
            ajouterCourse(course);
        }
    }

    public static void afficherCourseFini() {
        if (listeDesCoursesFini == null) {
            listeDesCoursesFini = new ArrayList<>();
        }
        for (Course cs : listeDesCoursesFini) {
            System.out.println("Course fini " + cs.getNomCourse());
        }
    }

    public static double calculVitesseCheval(Double avancementCourseCheval, Double vitesse) {
        //final = 10000.
        double tempsDejaFait = (avancementCourseCheval / vitesse);// ce qu'il a déjà parcouru en temps.
        double tempsTotal = DISTANCE_POURCENTAGE / vitesse;
        return (tempsTotal - tempsDejaFait);
    }

    public static double calculAffichagePosition(Double avancementCourseCheval) {
        //Avancement sur 10000
        //Nombre de pixel de base 50
        //Distance de 500 px
        return (((avancementCourseCheval / DISTANCE_POURCENTAGE) * LONGUEUR_PIXEL_COURSE) + LONG_DIFF_FEN_DEB_COURSE);

    }

    private static Double tourBoucleNecessaireRestant(Cheval ch) {
        return (DISTANCE_POURCENTAGE - ch.getAvancementCourse()) / ch.getVitesse();
    }

    public static List<Course> getListeDesCoursesEnCours() {
        return listeDesCoursesEnCours;
    }
}
