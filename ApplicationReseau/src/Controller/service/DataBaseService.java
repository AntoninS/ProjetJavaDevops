package Controller.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBaseService {

    private static DataBaseService dataBaseServiceInstance = null;

    public DataBaseService() {

    }

    public static DataBaseService getInstance() {
        if (dataBaseServiceInstance == null) {
            dataBaseServiceInstance = new DataBaseService();
        }
        return dataBaseServiceInstance;
    }

    public Connection getDataBaseConnexion() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Properties prop = new Properties();
        try (
                FileInputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        Connection con = DriverManager.getConnection(url, user, password);

        return con;
    }
}
