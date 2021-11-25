package bacit_web;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Lager objektet som kobler til DB
public class DBUtils {
    private static final DBUtils INSTANCE = new DBUtils();
    static Connection connection;

    public static DBUtils getINSTANCE() {
        return INSTANCE;
    }

    public Connection getConnection() throws ClassNotFoundException {
        Connection toReturn = null;
        Class.forName("org.mariadb.jdbc.Driver");
        try {
            toReturn = (connection != null) ? connection : DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/Amv", "root", "12345");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}