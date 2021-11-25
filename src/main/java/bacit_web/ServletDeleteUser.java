package bacit_web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Sletter bruker fra databasen
@WebServlet("/ServletDeleteUser")
public class ServletDeleteUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Connection db;
        PreparedStatement ps;
        PrintWriter out = response.getWriter();

        String ansattTelefon = request.getParameter("AnsattTelefon");
        //Prepared statement for slette i databasen
        try {
            db = DBUtils.getINSTANCE().getConnection();
            String query = "delete from Amv.Ansatt where AnsattTelefon = ?";
            ps = db.prepareStatement(query);
            ps.setString(1, ansattTelefon);
            ps.execute();
            out.println("<br>Brukeren er slettet");
            out.println("<button onclick=\"window.location.href='AdminPage.html'\">Tilbake\n" + "</button>");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
