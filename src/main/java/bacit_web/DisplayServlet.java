package bacit_web;

import bacit_web.DBUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(urlPatterns = {"/DisplayServlet"})
public class DisplayServlet extends HttpServlet
    //Kobler til DB, og viser verktøy fra "Verktøy" tabellen
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");
        Connection db;
        try
        //statement for å spørre i database
        {
            db = DBUtils.getINSTANCE().getConnection();
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Verktoy");
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr>VerktoyID<th></th><th>VerktoyNavn</th><th>VerktoyType</th><th>VerktoyBeskrivelse</th><th>VerktoySkadet</th><th>VerktoyLedig</th><tr>");
            while (rs.next())
            {
                //Definerer strings
                String id = rs.getString("VerktoyID");
                String vn = rs.getString("VerktoyNavn");
                String vt = rs.getString("VerktoyType");
                String vb = rs.getString("VerktoyBeskrivelse");
                String vs = rs.getString("VerktoySkadet");
                String vl = rs.getString("VerktoyLedig");
                out.println("<tr><td>" + id + "</td><td>" + vn + "</td><td>" + vt + "</td><td>" + vb + "</td><td>" + vs + "</td><td>" + vl + "</td></tr>");
            }
            //definerer videresending
            out.println("</table>");
            out.println("<button onclick=\"window.location.href='Home.html'\">Tilbake\n" + "</button>");
            out.println("</html></body>");
            db.close();
        }
        catch (Exception e)
        {
            out.println("error");
        }
    }
}