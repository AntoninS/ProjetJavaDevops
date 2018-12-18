package Controller.service;

import Model.common.User;

import java.sql.*;

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


}
