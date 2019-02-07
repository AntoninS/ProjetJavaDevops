package Model.common.course;

import Controller.service.HorseService;
import Model.common.Cheval;

import java.util.List;

public abstract class Course {
    private List<Cheval> listChevalCourse;
    private String nomCourse;
    private Boolean estTerminee = false;
    private Integer tempsLancement;


    public Course() {
        listChevalCourse = HorseService.getInstance().getListHorsesLimitAndRandom();
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
