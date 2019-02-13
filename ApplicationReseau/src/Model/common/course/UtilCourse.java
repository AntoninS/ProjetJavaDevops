package Model.common.course;

public class UtilCourse {
    //Cette distance est un pourcentage ramené a 10000 au lieu de 100 pour eviter des problèmes au niveau de l'affichage graphique
    public final static Double DISTANCE_POURCENTAGE = 10000.0;
    //La distance en pixel de la course
    public final static Double LONGUEUR_PIXEL_COURSE = 500.0;
    //Différence entre le bord de la fenetre est le début de la course
    public final static Double LONG_DIFF_FEN_DEB_COURSE = 50.0;
    //Total de la longueur de la course + pixel du début
    public final static Double LONGUEUR_DIFF_PLUS_PIXEL = 550.0;
    //Nombre de chevaux
    public final static Integer NOMBRE_CHEVAUX_COURSE = 6;


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

}
