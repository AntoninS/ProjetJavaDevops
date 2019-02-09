package Model.common.course;

import Controller.service.HorseService;
import Model.common.Cheval;
import View.gui.EcranPrincipalController;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireCourses {

    private List<Course> listeDesCoursesEnCours = null;

    private boolean uneCourseEstEnCours = false;

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

        for (int i = 0; i < UtilCourse.NOMBRE_CHEVAUX_COURSE; i++) {
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
        setUneCourseEstEnCours(true);
        ecranController.btnConsulterCourseDisable(false);
        ecranController.ajouterCourseListView();
    }

    private void majCourse(Course course, JSONObject courseJsonObject) throws JSONException
    {

       modificationAvancementChevaux(courseJsonObject, course.getListChevalCourse());
        course.setTempsLancement(courseJsonObject.getInt("tempsLancement"));

        if (courseJsonObject.getBoolean("courseEtat")) {
            setUneCourseEstEnCours(false);
            ecranController.btnConsulterCourseDisable(true);
            listeDesCoursesEnCours.clear();

            List<Cheval> classementPodium = new ArrayList<>();
            classementPodium.add(HorseService.getInstance().getHorse(getIdChevalAtClassement(courseJsonObject, 1)));
            classementPodium.add(HorseService.getInstance().getHorse(getIdChevalAtClassement(courseJsonObject, 2)));
            classementPodium.add(HorseService.getInstance().getHorse(getIdChevalAtClassement(courseJsonObject, 3)));
            ecranController.handleEndOfCourse(classementPodium);
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


    public List<Course> getListeDesCoursesEnCours() {
        return listeDesCoursesEnCours;
    }

    public boolean getUneCourseEstEnCours() {
        return uneCourseEstEnCours;
    }
    public void setUneCourseEstEnCours (boolean b )
    {
        uneCourseEstEnCours = b;
    }


    /**
     *
     * @param courseJsonObject le flux Json
     * @param position La position de classem
     */
    public int getIdChevalAtClassement(JSONObject courseJsonObject, int position) throws JSONException {
        return courseJsonObject.getInt("idChevalAtClassement".concat(String.valueOf(position)));
    }
}
