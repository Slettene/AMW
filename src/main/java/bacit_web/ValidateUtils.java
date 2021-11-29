package bacit_web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//Sjekker om input stemmer med databasen
public class ValidateUtils {
    public boolean validateLogin(String ansattID, String ansattTelefon) {
        Connection db;
        PreparedStatement ps;
        ResultSet rs;
        try {
            db = DBUtils.getINSTANCE().getConnection();
            String query = "SELECT AnsattID, AnsattTelefon FROM Amv.Ansatt WHERE AnsattID = ? AND AnsattTelefon = ?";
            ps = db.prepareStatement(query);
            ps.setString(1, ansattID);
            ps.setString(2, ansattTelefon);
            rs = ps.executeQuery();

            //Validerer om brukernavn og passord stemmer overens

            rs.next();
            String dbAnsattID = rs.getString("AnsattID");
            String dbAnsattTelefon = rs.getString("AnsattTelefon");
            if (dbAnsattID.equals(ansattID) && dbAnsattTelefon.equals(ansattTelefon)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
//Sjekker om bruker er admin i databasen
    public boolean validateAdmin(HttpSession session) {
        Connection db;
        PreparedStatement ps;
        String ansattID = (String) session.getAttribute("AnsattID");
        try {
            db = DBUtils.getINSTANCE().getConnection();
            String query = "SELECT AnsattAdmin FROM Amv.Ansatt WHERE AnsattID = ?";
            ResultSet rs;
            ps = db.prepareStatement(query);
            ps.setString(1, ansattID);
            rs = ps.executeQuery();

            rs.next();
            String dbAnsattAdmin = rs.getString("AnsattAdmin");
            if (dbAnsattAdmin.equals("1")) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}