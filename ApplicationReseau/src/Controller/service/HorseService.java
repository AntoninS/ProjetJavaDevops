package Controller.service;

import Model.common.Cheval.Cheval;
import Model.common.course.UtilCourse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HorseService {

    DataBaseService dbs = DataBaseService.getInstance();

    private static HorseService horseServiceInstance = null;

    public HorseService() {

    }

    public static HorseService getInstance() {
        if (horseServiceInstance == null) {
            horseServiceInstance = new HorseService();
        }
        return horseServiceInstance;
    }


    public List<Cheval> getListHorsesFull() {
        List<Cheval> list = new ArrayList<>();
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = "SELECT ID, NAME, SPEED, SHAPE FROM PJ_HORSE";
            cs = con.createStatement();
            rs = cs.executeQuery(request);

            while (rs.next()) {
                list.add(new Cheval(rs.getString("NAME"),
                        rs.getInt("SHAPE"),
                        rs.getInt("ID"),
                        0.0,
                        rs.getDouble("SPEED")));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Cheval getHorse(int id) {
        Cheval cheval = new Cheval();
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = String.format("SELECT ID, NAME, SPEED, SHAPE FROM PJ_HORSE WHERE ID = %d", id);
            cs = con.createStatement();
            rs = cs.executeQuery(request);

            if (rs.next()) {
                cheval.setNumero(rs.getInt("ID"));
                cheval.setNom(rs.getString("NAME"));
                cheval.setVitesse(rs.getDouble("SPEED"));
                cheval.setForme(rs.getInt("SHAPE"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cheval;
    }

    public List<Cheval> getListHorsesLimitAndRandom() {
        List<Cheval> list = getListHorsesFull();
        if (null != list && !list.isEmpty() && list.size() >= UtilCourse.NOMBRE_CHEVAUX_COURSE) {
            Collections.shuffle(list);
            return new ArrayList<>(list.subList(0, UtilCourse.NOMBRE_CHEVAUX_COURSE));
        }
        return list;
    }
}
