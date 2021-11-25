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

//Sletter verktøy fra databasen
@WebServlet("/ServletDeleteData")
public class ServletDeleteData extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Connection db;
        PreparedStatement ps;
        PrintWriter out = response.getWriter();

        String verktoyID = request.getParameter("VerktoyID1");
        //Prepared statement for å slette verktøy
        try {
            db = DBUtils.getINSTANCE().getConnection();
            String query = "delete from Amv.Verktoy where VerktoyID = ?";
            ps = db.prepareStatement(query);
            ps.setString(1, verktoyID);
            ps.execute();
            out.println("<br>Verktøy er fjernet");
            out.println("<button onclick=\"window.location.href='AdminPage.html'\">Tilbake\n" + "</button>");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
