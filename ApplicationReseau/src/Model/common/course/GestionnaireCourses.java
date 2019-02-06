package Model.common.course;

import Model.common.Cheval;
import View.gui.EcranPrincipalController;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireCourses {

    private List<Course> listeDesCoursesEnCours = null;

    private List<Course> listeDesCoursesFini = null;

    private EcranPrincipalController ecranController = null;

    public void setEcranController(EcranPrincipalController ecranController) {
        this.ecranController = ecranController;
    }

    public GestionnaireCourses() {

    }

    public List<Course> getListeDesCourses() {
        return listeDesCoursesEnCours;
    }

    //On ajoute a la liste des courses en cours
    public void ajouterUneCourse(Course pCourse) {

        listeDesCoursesEnCours.add(pCourse);
    }

    // Quand une course est fini on la supprime des courses en cours et on l'ajoute aux courses fini
    public void courseFini(Course pCourse) {
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
     * Recuperation des informations JSON
     */
    private List<Cheval> recuperationListCheval(JSONObject courseJsonObject) throws JSONException {

        List<Cheval> listeCh = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            Cheval ch = new Cheval(courseJsonObject.getDouble(Integer.toString(i)), //
                    courseJsonObject.getInt(Integer.toString(i + 10)), //
                    courseJsonObject.getDouble(Integer.toString(i + 20)), //
                    courseJsonObject.getString(Integer.toString(i + 30)));
            listeCh.add(ch);
        }
        return listeCh;
    }

    private void modificationAvancementChevaux (JSONObject courseJsonObject, List<Cheval> listCheval) throws JSONException {

        for (int i = 0; i < UtilCourse.nombreChevauxCourse; i++) {
            Double nouveauAvancement = courseJsonObject.getDouble(Integer.toString(i + 1));
            listCheval.get(i).setAvancementCourse(nouveauAvancement);
        }
    }

    private String recuperationNomCourse(JSONObject courseJsonObject) throws JSONException
    {
        String nomDeLaCourse = courseJsonObject.getString("nomCourse");
        return  nomDeLaCourse;
    }


    private void ajouterCourse(JSONObject courseJsonObject )throws JSONException
    {

        Course course = new CourseGeneral();

        course.setListChevalCourse(recuperationListCheval(courseJsonObject));
        course.setNomCourse(recuperationNomCourse(courseJsonObject));
        course.setTempsLancement(courseJsonObject.getInt("tempsLancement"));
        course.setEstTerminee(false);
        ajouterUneCourse(course);
        ecranController.ajouterCourseListView();
    }

    private void majCourse(Course course, JSONObject courseJsonObject) throws JSONException
    {

       modificationAvancementChevaux(courseJsonObject, course.getListChevalCourse());
        course.setTempsLancement(courseJsonObject.getInt("tempsLancement"));

        if (courseJsonObject.getBoolean("courseEtat")) {
            courseFini(course);
        }

    }

    private Course recuperationCourse(Integer numCourse)
    {
        return listeDesCoursesEnCours.get(0);
    }

    private Boolean courseExitente(String nomDeLaCourse) {
        for (Course cs : listeDesCoursesEnCours) {
            if (cs != null && cs.getNomCourse().equals(nomDeLaCourse)) {
                return true;
            }
        }
        return false;
    }

    /**
     * On vient ici créer ou modifier les données de la course qu'on reçoit
     */
    public void gererCourse(JSONObject courseJsonObject) throws JSONException {

        if (listeDesCoursesEnCours == null) {
            listeDesCoursesEnCours = new ArrayList<>();
        }
        if (!(courseExitente(recuperationNomCourse(courseJsonObject))))
        {
            ajouterCourse(courseJsonObject);
        } else if (courseExitente(recuperationNomCourse(courseJsonObject))) {
            majCourse(recuperationCourse(0),courseJsonObject );
        }
    }

    public void afficherCourseFini() {
        if (listeDesCoursesFini == null) {
            listeDesCoursesFini = new ArrayList<>();
        }
        for (Course cs : listeDesCoursesFini) {
            System.out.println("Course fini " + cs.getNomCourse());
        }
    }

    public List<Course> getListeDesCoursesEnCours() {
        return listeDesCoursesEnCours;
    }
}
