package Controller.service;

import java.sql.*;

public class DataBaseService {

    private static final String DATABASE = "jdbc:oracle:thin:@134.214.112.67:1521:orcl";

    private static final String USER_DB = "p1501019";

    private static final String PWD_DB = "374438";

    private static DataBaseService dataBaseServiceInstance = null;

    public DataBaseService() {

    }

    public static DataBaseService getInstance() {
        if (dataBaseServiceInstance == null) {
            dataBaseServiceInstance = new DataBaseService();
        }
        return dataBaseServiceInstance;
    }

    public Connection getDataBaseConnexion() throws SQLException {
        Connection con = DriverManager.getConnection(DATABASE, USER_DB, PWD_DB);
        return con;
    }
}
