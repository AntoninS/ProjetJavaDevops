package Controller.service;

import Model.common.Cheval.Cheval;
import Model.common.Pari.Pari;
import Model.common.User.User;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

public class RaceService {

    DataBaseService dbs = DataBaseService.getInstance();

    // Singleton pattern
    private static RaceService raceServiceInstance = null;

    public RaceService() {

    }

    public static RaceService getInstance() {
        if (raceServiceInstance == null) {
            raceServiceInstance = new RaceService();
        }
        return raceServiceInstance;
    }

    /**
     * Permet de récuperer en BDD l'ID de la dernière course inserée
     * @return id, id d'une course
     */
    public int getLastRaceId() {
        int lastRaceId = 0;
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = "SELECT ID FROM PJ_RACE WHERE rownum = 1 ORDER BY ID DESC";
            cs = con.createStatement();
            rs = cs.executeQuery(request);

            if (rs.next()) {
                lastRaceId = rs.getInt("ID");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lastRaceId;
    }

    /**
     * PErmet d'inseret une course en BDD
     * @param id l'id de la course
     */
    public void insertRace(int id) {
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = String.format("INSERT INTO PJ_RACE(ID) VALUES (%d)", id);
            cs = con.createStatement();
            cs.executeQuery(request);

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet d'inserer un pari en BDD
     * @param idUser l'id de l'utilisateur
     * @param idHorse l'id du cheval sur lequelle l'utilisateur a parié
     * @param idRace l'id de la course
     * @param amount le montant de la mise de l'utilisateur
     */
    public void insertBet(int idUser, int idHorse, int idRace, float amount) {
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = String.format(Locale.US, "INSERT INTO PJ_BET(ID, ID_USER, ID_HORSE, ID_RACE, BET_AMOUNT) VALUES (PJ_BET_SEQUENCE.nextval, %d, %d, %d, %.2f)", idUser, idHorse, idRace, amount);
            cs = con.createStatement();
            cs.executeQuery(request);

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de récuperer le pari d'un utilisateur sur une course
     * @param idUser l'id de l'utilisateur
     * @param idRace l'id de la course
     * @return l'objet Pari, null si l'utilisateur n'avait pas parié
     */
    public Pari getBet(int idUser, int idRace) {
        Pari pari = null;
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = String.format("SELECT ID, ID_USER, ID_HORSE, ID_RACE, BET_AMOUNT FROM PJ_BET WHERE ID_USER=%d AND ID_RACE=%d ", idUser, idRace);
            cs = con.createStatement();
            rs = cs.executeQuery(request);

            if (rs.next()) {
                pari = new Pari(
                        rs.getInt("ID"),
                        rs.getInt("ID_USER"),
                        rs.getInt("ID_HORSE"),
                        rs.getInt("ID_RACE"),
                        rs.getFloat("BET_AMOUNT")
                );
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pari;
    }

    /**
     * Permet de déterminer si un utilisateur a gagné un pari
     * @param pari
     * @param classementPodium le classement des 3 premiers chevaux
     * @return true si le cheval parié est dans le top 3 du classement
     */
    public boolean hasWonBet(Pari pari, List<Cheval> classementPodium) {
        if (null != pari) {
            int idCheval = pari.getIdCheval();
            return idCheval == classementPodium.get(0).getNumero()
                    || idCheval == classementPodium.get(1).getNumero()
                    || idCheval == classementPodium.get(2).getNumero();
        }
        return false;
    }

    /**
     * Permet de calculer les gains suite à un pari, et de mettre à jour la cagnotte de l'utilisateur
     *
     * @param pari             Le pari misé
     * @param classementPodium Le classement des chevaux à la fin de la course
     * @return le montant de la cagnotte de l'utilisateur après le résultat du pari
     */
    public float calculateGains(Pari pari, List<Cheval> classementPodium) {
        float gains = 0;
        float cagnotte = 0;

        if (null != pari) {
            User user = UserService.getInstance().getUser(pari.getIdUser());
            cagnotte = user.getMoney();
            int idChevalPari = pari.getIdCheval();
            if (hasWonBet(pari, classementPodium)) {
                if (classementPodium.get(0).getNumero() == idChevalPari) {
                    gains = pari.getMontant() * 2;
                } else if (classementPodium.get(1).getNumero() == idChevalPari) {
                    gains = pari.getMontant() * 1.5f;
                } else if (classementPodium.get(0).getNumero() == idChevalPari) {
                    gains = pari.getMontant();
                }
                cagnotte += gains;

            } else {
                cagnotte -= pari.getMontant();
                if (cagnotte < 0) {
                    cagnotte = 0;
                }
            }
            UserService.getInstance().updateMoney(user.getId(), cagnotte);
        }
        return cagnotte;
    }
}
