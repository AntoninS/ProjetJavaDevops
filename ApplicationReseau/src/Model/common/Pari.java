package Model.common;

public class Pari {

    private int id;
    private int idUser;
    private int idCheval;
    private int idCourse;
    private float montant;

    public Pari() {

    }

    public Pari(int id, int idUser, int idCheval, int idCourse, float montant) {
        this.id = id;
        this.idUser = idUser;
        this.idCheval = idCheval;
        this.idCourse = idCourse;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCheval() {
        return idCheval;
    }

    public void setIdCheval(int idCheval) {
        this.idCheval = idCheval;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
}
