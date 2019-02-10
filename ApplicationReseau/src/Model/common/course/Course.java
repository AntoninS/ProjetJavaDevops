package Model.common.course;

import Controller.service.HorseService;
import Controller.service.RaceService;
import Model.common.Cheval.Cheval;

import java.util.ArrayList;
import java.util.List;

public abstract class Course {

    private int id;
    private List<Cheval> listChevalCourse;
    private String nomCourse;
    private Boolean estTerminee = false;
    private Integer tempsLancement;
    public ArrayList<Integer> chevalArriver;



    public Course() {
        id = RaceService.getInstance().getLastRaceId() + 1;
        listChevalCourse = HorseService.getInstance().getListHorsesLimitAndRandom();

        RaceService.getInstance().insertRace(id);
    }


    public void modifierAvancementTousLesChevaux ()
    {
        for (Cheval ch: listChevalCourse)
        {
            ch.modifierAvancement();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
