package Controller.service;

import Model.common.User;

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

    // TODO : Refactor pour utiliser PS
    public User getUser(String pseudo) {
        User user = new User();
        try {
            Connection con = dbs.getDataBaseConnexion();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID, PSEUDO, PASSWORD FROM PJ_USER WHERE PSEUDO = 'Antonin'");
            if(rs.next()) {
                user.setId(rs.getInt("ID"));
                user.setPseudo(rs.getString("PSEUDO"));
                user.setPassword(rs.getString("PASSWORD"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
