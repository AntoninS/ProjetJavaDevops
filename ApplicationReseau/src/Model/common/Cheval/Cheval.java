package Model.common.Cheval;

import Model.common.course.UtilCourse;
import javafx.scene.image.ImageView;

public class Cheval {
    //Nom d'un cheval
    private String nom;
    //La forme d'un cheval
    private int forme;
    //Le numéro d'un cheval
    private int numero;
    //Un avancemement d'une course
    private Double avancementCourse;
    //La vitesse d'un cheval
    private Double vitesse;
    //L'image view d'un cheval
    private ImageView imageCheval;
    //La position dans la course
    private int positionInRace = -1;

    /**
     * Connstructeur d'un cheval
     *
     * @param pNom
     * @param pForme
     * @param pNumero
     * @param pAvancementCourse
     * @param pVitesse
     */
    public Cheval(String pNom, int pForme, int pNumero, Double pAvancementCourse, Double pVitesse) {
        this.nom = pNom;
        this.forme = pForme;
        this.numero = pNumero;
        this.avancementCourse = pAvancementCourse;
        this.vitesse = pVitesse;
    }

    /**
     * Constructeur vide d'un cheval
     */
    public Cheval() {
    }

    /**
     * Constructeur d'un cheval standard
     * @param pAvancementCourse
     * @param pNumero
     * @param pVitesse
     * @param pNom
     */
    public Cheval(Double pAvancementCourse, int pNumero, Double pVitesse, String pNom) {
        this.numero = pNumero;
        this.avancementCourse = pAvancementCourse;
        this.vitesse = pVitesse;
        this.nom = pNom;
    }


    /**
     * permet de modifier l'avancement d'un cheval
     */
    public void modifierAvancement() {
        if (getAvancementCourse() < UtilCourse.DISTANCE_POURCENTAGE) {
            setAvancementCourse(getAvancementCourse() + getVitesse());
        }
        if (getAvancementCourse() >= UtilCourse.DISTANCE_POURCENTAGE) {
            setAvancementCourse(10001.0);
        }
    }

    public String getNom() {
        return this.nom;
    }


    public int getNumero() {
        return this.numero;
    }

    public Double getVitesse() {
        return this.vitesse;
    }

    public Double getAvancementCourse() {
        return this.avancementCourse;
    }

    public ImageView getImageCheval() {
        return imageCheval;
    }

    public void setAvancementCourse(Double pAvancementForme) {
        this.avancementCourse = pAvancementForme;
    }

    public void setImageCheva(ImageView pImageView) {
        imageCheval = pImageView;
    }

    public int getPositionInRace() {
        return positionInRace;
    }

    public void setPositionInRace(int positionInRace) {
        this.positionInRace = positionInRace;
    }

    public boolean hasEndedRace() {
        return this.avancementCourse > UtilCourse.DISTANCE_POURCENTAGE;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getForme() {
        return forme;
    }

    public void setForme(int forme) {
        this.forme = forme;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setVitesse(Double vitesse) {
        this.vitesse = vitesse;
    }

    public void setImageCheval(ImageView imageCheval) {
        this.imageCheval = imageCheval;
    }
}

