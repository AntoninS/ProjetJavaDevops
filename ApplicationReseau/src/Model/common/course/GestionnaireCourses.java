package Model.common.course;

import Model.common.Cheval;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireCourses
{
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
    public static void ajouterCourse(Course pCourse)
    {
        if(listeDesCoursesEnCours == null)
        {
            listeDesCoursesEnCours = new ArrayList<>();
        }
        listeDesCoursesEnCours.add(pCourse);
    }
    // Quand une course est fini on la supprime des courses en cours et on l'ajoute aux courses fini
    public static void courseFini (Course pCourse)
    {
        for (Course uneCourse : listeDesCoursesEnCours)
        {
            if(uneCourse.equals(pCourse))
            {
                listeDesCoursesEnCours.remove(uneCourse);
                if(listeDesCoursesFini == null)
                {
                    listeDesCoursesFini = new ArrayList<>();
                }
                listeDesCoursesFini.add(uneCourse);
                break;
            }
        }
    }

    public static void afficherCourseFini()
    {
        if(listeDesCoursesFini == null)
        {
            listeDesCoursesFini = new ArrayList<>();
        }
        for (Course cs : listeDesCoursesFini)
        {
            System.out.println("Course fini " + cs.nomCourse);
        }
    }

    public static double calculVitesseCheval(Double avancementCourseCheval, Double vitesse)
    {
        //final = 10000.
        double tempsDejaFait = (avancementCourseCheval / vitesse);// ce qu'il a déjà parcouru en temps.
        double tempsTotal = DISTANCE_POURCENTAGE/vitesse;
        return (tempsTotal -  tempsDejaFait);
    }

    public static double calculAffichagePosition(Double avancementCourseCheval)
    {
        //Avancement sur 10000
        //Nombre de pixel de base 50
        //Distance de 500 px
        return (((avancementCourseCheval / DISTANCE_POURCENTAGE ) * LONGUEUR_PIXEL_COURSE) + LONG_DIFF_FEN_DEB_COURSE);

    }

    private static Double tourBoucleNecessaireRestant(Cheval ch)
    {
        return (DISTANCE_POURCENTAGE - ch.getAvancementCourse()) / ch.getVitesse();
    }

    public void attribuerChevalImageView()
    {

    }
}
