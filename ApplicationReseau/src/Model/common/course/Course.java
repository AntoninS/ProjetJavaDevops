package Model.common.course;

import Controller.service.HorseService;
import Controller.service.RaceService;
import Model.common.Cheval.Cheval;

import java.util.ArrayList;
import java.util.List;

public abstract class Course {
    //Liste des chevaux arrivés dans la course dans l'ordre du premier au troisieme
    public ArrayList<Integer> chevauxArrive;
    // id de la course
    private int id;
    //Liste de chevaux d'une course
    private List<Cheval> listeChevauxCourse;
    //Nom d'une course de chevaux
    private String nomCourse;
    //Permet de savoir si la course est terminée
    private Boolean estTerminee = false;
    //Temps avant lancement d'une course
    private Integer tempsLancement;

    /**
     * Constructeur d'une course et permet de créer une liste de chevaux à partir des informations en BDD
     */
    public Course() {
        id = RaceService.getInstance().getLastRaceId() + 1;
        listeChevauxCourse = HorseService.getInstance().getListHorsesLimitAndRandom();

        RaceService.getInstance().insertRace(id);
    }

    //Permet de modifier l'avancement de tous les chevaux
    public void modifierAvancementTousLesChevaux() {
        for (Cheval ch : listeChevauxCourse) {
            ch.modifierAvancement();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cheval> getListeChevauxCourse() {
        return this.listeChevauxCourse;
    }

    public void setListeChevauxCourse(List<Cheval> listeC) {
        this.listeChevauxCourse = listeC;
    }

    public String getNomCourse() {
        return this.nomCourse;
    }

    public void setNomCourse(String pNomCourse) {
        this.nomCourse = pNomCourse;
    }

    public Boolean getEstTerminee() {
        return estTerminee;
    }

    public void setEstTerminee(Boolean pEnCours) {
        this.estTerminee = pEnCours;
    }

    public Integer getTempsLancement() {
        return tempsLancement;
    }

    public void setTempsLancement(Integer tempsLancement) {
        this.tempsLancement = tempsLancement;
    }
}
