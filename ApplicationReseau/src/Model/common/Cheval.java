package Model.common;

import Model.common.course.UtilCourse;
import javafx.scene.image.ImageView;

public class Cheval {
    private String nom;
    private int forme;
    private int numero;
    private Double avancementCourse;
    private Double vitesse;
    private ImageView imageCheval;
    private int positionInRace = -1;

    public Cheval(String pNom, int pForme, int pNumero, Double pAvancementCourse, Double pVitesse) {
        this.nom = pNom;
        this.forme = pForme;
        this.numero = pNumero;
        this.avancementCourse = pAvancementCourse;
        this.vitesse = pVitesse;
    }

    public Cheval() {
    }

    public Cheval(Double pAvancementCourse, int pNumero, Double pVitesse, String pNom)
    {
        this.numero = pNumero;
        this.avancementCourse = pAvancementCourse;
        this.vitesse = pVitesse;
        this.nom = pNom;
    }


    /** permet de modifier l'avancement d'un cheval */
    public void modifierAvancement()
    {
        if(getAvancementCourse() < UtilCourse.DISTANCE_POURCENTAGE){
            setAvancementCourse(getAvancementCourse() + getVitesse());
        }
        if( getAvancementCourse() >= UtilCourse.DISTANCE_POURCENTAGE )
        {
            setAvancementCourse(10001.0);
        }
    }

    public String getNom() {
        return this.nom;
    }


    public int getNumero() {
        return this.numero;
    }

    public Double getVitesse() { return this.vitesse; }

    public Double getAvancementCourse() {
        return this.avancementCourse;
    }

    public ImageView getImageCheval() {
        return imageCheval;
    }

    public void setAvancementCourse(Double pAvancementForme) { this.avancementCourse = pAvancementForme; }

    public void setImageCheva (ImageView pImageView)
    {
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

