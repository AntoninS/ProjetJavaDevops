package Model.common.course;

import Model.common.Cheval;
import Model.common.ListCheval;

import java.util.List;

public abstract class Course {
    private List<Cheval> listChevalCourse;
    private int numeroCourse;
    private String nomCourse;
    private Boolean estTerminee = false;
    private Integer tempsLancement;

    private String typeCourse;
    private Double niveauAvancementCourse;


    public Course() {
        listChevalCourse = ListCheval.getChevaux();
    }


    public Double getChevalLent() {
        Double vitesse = 100000.0;
        for (Cheval cheval : listChevalCourse) {
            if (vitesse > cheval.getVitesse()) {
                vitesse = cheval.getVitesse();
            }
        }
        return vitesse;
    }

    public void modifierAvancementTousLesChevaux ()
    {
        for (Cheval ch: listChevalCourse)
        {
            ch.modifierAvancement();
        }
    }

    public List<Cheval> getListChevalCourse() {
        return this.listChevalCourse;
    }

    public String getNomCourse() {
        return this.nomCourse;
    }

    public int getNumeroCourse() {
        return numeroCourse;
    }

    public void setNomCourse(String pNomCourse) {
        this.nomCourse = pNomCourse;
    }

    public void setListChevalCourse(List<Cheval> listeC) {
        this.listChevalCourse = listeC;
    }

    public Boolean getEstTerminee() {
        return estTerminee;
    }

    public Integer getTempsLancement() {
        return tempsLancement;
    }

    public void setTempsLancement(Integer tempsLancement) {
        this.tempsLancement = tempsLancement;
    }

    public void setEstTerminee(Boolean pEnCours) {
        this.estTerminee = pEnCours;
    }
}
