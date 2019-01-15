package Controller.service;

import Model.common.User;
import Util.MD5Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    DataBaseService dbs = DataBaseService.getInstance();

    private static UserService userServiceInstance = null;

    public UserService() {

    }

    public static UserService getInstance() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserService();
        }
        return userServiceInstance;
    }

    public User getUser(String pseudo) {
        User user = new User();
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement cs = null;
            ResultSet rs = null;

            String request = String.format("SELECT ID, PSEUDO, PASSWORD FROM PJ_USER WHERE PSEUDO = '%s'", pseudo);
            cs = con.createStatement();
            rs = cs.executeQuery(request);

            if(rs.next()) {
                user.setId(rs.getInt("ID"));
                user.setPseudo(rs.getString("PSEUDO"));
                user.setPassword(rs.getString("PASSWORD"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean isPasswordCorrect(String pseudo, String password) {
        boolean isPasswordCorrect = false;
        User userFromDb = getUser(pseudo);
        if ((userFromDb.getPassword() != null) && !userFromDb.getPassword().trim().isEmpty()) {
            isPasswordCorrect = MD5Util.getMD5(password.trim()).equals(userFromDb.getPassword().trim());
        }
        return isPasswordCorrect;
    }


}
