import bacit_web.DBUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(urlPatterns = {"/DisplayServlet"})
public class DisplayServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html><body>");
        Connection db = null;
        try
        {
            db = DBUtils.getINSTANCE().getConnection(out);
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Verktoy");
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr>VerktoyID<th></th><th>VerktoyNavn</th><th>VerktoyBeskrivelse</th><tr>");
            while (rs.next())
            {
                String n = rs.getString("VerktoyID");
                String nm = rs.getString("VerktoyNavn");
                String s = rs.getString("VerktoyBeskrivelse");
                out.println("<tr><td>" + n + "</td><td>" + nm + "</td><td>" + s + "</td></tr>");
            }
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