import java.sql.*;

public class ConnectionDemo {

    public static void main(String[] args){
        String username, password, url, driver;
        Connection con;
        Statement st;


        username = "root";
        password = "Testingtesting1234";
        url = "jdbc:mariadb://localhost:3306/Amv";
        driver = "org.mariadb.jdbc.Driver";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
            System.out.println("Connection is successful");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
