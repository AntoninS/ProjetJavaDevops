package Model.common.course;

import Controller.service.HorseService;
import Controller.service.RaceService;
import Model.common.Cheval.Cheval;

import java.util.ArrayList;
import java.util.List;

public abstract class Course {
    //Un id de course
    private int id;
    //Une liste de chevaux
    private List<Cheval> listChevalCourse;
    //Nom de la course
    private String nomCourse;
    // Dit si la course est terminée
    private Boolean estTerminee = false;
    //Temps avant lancement de la course
    private Integer tempsLancement;
    //Dit si le cheval est arrivé
    public ArrayList<Integer> chevalArriver;

    /**
     * Une création de course
     */
    public Course() {
        id = RaceService.getInstance().getLastRaceId() + 1;
        listChevalCourse = HorseService.getInstance().getListHorsesLimitAndRandom();

        RaceService.getInstance().insertRace(id);
    }

    //Modifie le déplacement de tous les chevaux
    public void modifierAvancementTousLesChevaux() {
        for (Cheval ch : listChevalCourse) {
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
