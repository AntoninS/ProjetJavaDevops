package Model.common.course;

import Model.common.Cheval;
import Model.common.ListCheval;

import java.util.List;

public abstract class Course
{
    protected List<Cheval> listChevalCourse;
    protected int numeroCourse;
    protected String nomCourse;
    protected String typeCourse;
    protected Double niveauAvancementCourse;

    public Course()
    {
        listChevalCourse = ListCheval.getChevaux();
    }

    protected void afficherCourseResultat()
    {

    }


    protected void lancerCourse()
    {

    }

    protected void arreterCourse()
    {

    }

    protected void affecterCourse()
    {

    }

    public Double getChevalLent()
    {
        Double vitesse = 100000.0;
        for (Cheval cheval : listChevalCourse)
        {
            if (vitesse > cheval.getVitesse())
            {
                vitesse = cheval.getVitesse();
            }
        }
        return vitesse;
    }

    public List<Cheval> getListChevalCourse() {
        return this.listChevalCourse;
    }

    public String getNomCourse()
    {
        return this.nomCourse;
    }

    public int getNumeroCourse() {
        return numeroCourse;
    }

    public void setNomCourse (String pNomCourse) {this.nomCourse = pNomCourse;}

    public void setListChevalCourse (List<Cheval> listeC)
    {
        this.listChevalCourse = listeC;
    }
}
