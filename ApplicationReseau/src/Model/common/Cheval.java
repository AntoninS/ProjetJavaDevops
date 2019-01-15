package Model.common;

import Model.common.course.GestionnaireCourses;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Cheval {
    private String nom;
    private int forme;
    private int numero;
    private Double avancementCourse;
    private Double vitesse;
    private int position;
    private ImageView imageCheval;

    public Cheval(String pNom, int pForme, int pNumero, Double pAvancementCourse, Double pVitesse) {
        this.nom = pNom;
        this.forme = pForme;
        this.numero = pNumero;
        this.avancementCourse = pAvancementCourse;
        this.vitesse = pVitesse;
    }

    public Cheval() {
    }

    /**
     * Cette méthode permet de redéfinir la forme du cheval pour la course suivante en fonction de son classement
     */
    public void definirForme(int bonus, int malus) {

    }

    public void modifierForme()
    {
        if(getAvancementCourse() < GestionnaireCourses.DISTANCE_POURCENTAGE){
            setAvancementCourse(getAvancementCourse() + getVitesse());
        }
        if( getAvancementCourse() >= GestionnaireCourses.DISTANCE_POURCENTAGE )
        {
            setAvancementCourse(10001.0);
        }
    }

    public String getNom() {
        return this.nom;
    }

    public int getForme() {
        return this.forme;
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

    public void setForme(int pForme) { this.forme = pForme; }

    public void setAvancementCourse(Double pAvancementForme) { this.avancementCourse = pAvancementForme; }

    public int getPosition (){return position;}
    public void setImageCheva (ImageView pImageView)
        {
            imageCheval = pImageView;
        }
}

