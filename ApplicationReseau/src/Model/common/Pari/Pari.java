package Model.common.Pari;

public class Pari {

    //Id du pari
    private int id;
    //Id de l'utilisateur qui a parié
    private int idUser;
    //Id du cheval sur lequel l'utilisateur a parié
    private int idCheval;
    //Id de la course que l'utilisateur a parié
    private int idCourse;
    //Le montant du pari
    private float montant;

    /**
     * Constructeur vide d'un pari
     */
    public Pari() {

    }

    /**
     * Cronstructeur d'un pari
     * @param id
     * @param idUser
     * @param idCheval
     * @param idCourse
     * @param montant
     */
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
