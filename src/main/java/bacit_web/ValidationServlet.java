package bacit_web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidationServlet{
    public boolean validateLogin(String email, String password, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Connection db;
        PreparedStatement ps;
        PrintWriter out = response.getWriter();

        try {
            db = DBUtils.getINSTANCE().getConnection();
            String query = "SELECT AnsattMail, AnsattPassord FROM Ansatt WHERE AnsattMail = ? AND AnsattPassord = ?";
            ResultSet rs;
            ps = db.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            rs.next();
            String dbEmail = rs.getString("AnsattMail");
            String dbPassword = rs.getString("AnsattPassord");
            if (dbEmail.equals(email) && dbPassword.equals(password)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateAdmin(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Connection db;
        PreparedStatement ps;
        String email = (String) session.getAttribute("email");
        PrintWriter out = response.getWriter();
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String query = "SELECT AnsattAdmin FROM Ansatt WHERE AnsattMail = ?";
            ResultSet rs;
            ps = db.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            rs.next();
            String dbAdmin = rs.getString("Admin");
            if (dbAdmin.equals("1")) {
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