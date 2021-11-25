package bacit_web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(urlPatterns = {"/DisplayUsersServlet"})
public class DisplayUsersServlet extends HttpServlet
    //Viser ansatte i databasen
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");
        Connection db = null;
        try
        {
            db = DBUtils.getINSTANCE().getConnection();
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Ansatt");
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr>AnsattID<th></th><th>AnsattMail</th><th>AnsattTelefon</th><th>AnsattOrganisert</th><th>AnsattAdmin</th><tr>");
            while (rs.next())
            {
                String n = rs.getString("AnsattID");
                String nm = rs.getString("AnsattMail");
                String nt = rs.getString("AnsattTelefon");
                String s = rs.getString("AnsattOrganisert");
                String ns = rs.getString("AnsattAdmin");
                out.println("<tr><td>" + n + "</td><td>" + nm + "</td><td>" + nt + "</td><td>" + s + "</td><td>" + ns + "</td></tr>");
            }
            out.println("</table>");
            out.println("<button onclick=\"window.location.href='AdminPage.html'\">Tilbake\n" + "</button>");
            out.println("</html></body>");
            db.close();
        }
        catch (Exception e)
        {
            out.println("error");
        }
    }
}