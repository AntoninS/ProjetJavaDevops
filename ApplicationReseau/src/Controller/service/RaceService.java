package Controller.service;

import Model.common.Cheval;
import Model.common.Pari;
import Model.common.User;
import com.sun.tools.javac.util.ArrayUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RaceService {

    DataBaseService dbs = DataBaseService.getInstance();

    private static RaceService raceServiceInstance = null;

    public RaceService() {

    }

    public static RaceService getInstance() {
        if (raceServiceInstance == null) {
            raceServiceInstance = new RaceService();
        }
        return raceServiceInstance;
    }

    public int getLastRaceId() {
        int lastRaceId = 0;
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = "SELECT ID FROM PJ_RACE WHERE rownum = 1 ORDER BY ID DESC";
            cs = con.createStatement();
            rs = cs.executeQuery(request);

            if(rs.next()) {
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

    public void insertBet(int idUser, int idHorse, int idRace, float amount) {
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = String.format(Locale.US,"INSERT INTO PJ_BET(ID, ID_USER, ID_HORSE, ID_RACE, BET_AMOUNT) VALUES (PJ_BET_SEQUENCE.nextval, %d, %d, %d, %.2f)", idUser, idHorse, idRace, amount);
            cs = con.createStatement();
            cs.executeQuery(request);

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Pari getBet(int idUser, int idRace) {
        Pari pari = null;
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = String.format("SELECT ID, ID_USER, ID_HORSE, ID_RACE, BET_AMOUNT FROM PJ_BET WHERE ID_USER=%d AND ID_RACE=%d ", idUser, idRace);
            cs = con.createStatement();
            rs = cs.executeQuery(request);

            if(rs.next()) {
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

    public boolean hasWonBet(Pari pari, List<Cheval> classementPodium) {
        int idCheval = pari.getIdCheval();
        return idCheval == classementPodium.get(0).getNumero()
                || idCheval == classementPodium.get(1).getNumero()
                || idCheval == classementPodium.get(2).getNumero();
    }

    /**
     * Permet de calculer les gains suite à un pari, et de mettre à jour la cagnotte de l'utilisateur
     * @param pari Le pari misé
     * @param classementPodium Le classement des chevaux à la fin de la course
     * @return le montant de la cagnotte de l'utilisateur après le résultat du pari
     */
    public float calculateGains(Pari pari, List<Cheval> classementPodium) {
        float gains = 0;
        int idChevalPari = pari.getIdCheval();
        User user = UserService.getInstance().getUser(pari.getIdUser());
        float cagnotte = user.getMoney();

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

        return cagnotte;
    }

}
