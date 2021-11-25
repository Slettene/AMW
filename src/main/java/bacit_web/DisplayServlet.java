package bacit_web;

import bacit_web.DBUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet(urlPatterns = {"/bacit_web.DisplayServlet"})
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
            db = DBUtils.getINSTANCE().getConnection();
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Verktoy");
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr>VerktoyID<th></th><th>VerktoyNavn</th><th>VerktoyType</th><th>VerktoyBeskrivelse</th><th>VerktoySkadet</th><th>VerktoyLedig</th><tr>");
            while (rs.next())
            {
                String n = rs.getString("VerktoyID");
                String nm = rs.getString("VerktoyNavn");
                String nt = rs.getString("VerktoyType");
                String s = rs.getString("VerktoyBeskrivelse");
                String ns = rs.getString("VerktoySkadet");
                String nl = rs.getString("VerktoyLedig");
                out.println("<tr><td>" + n + "</td><td>" + nm + "</td><td>" + nt + "</td><td>" + s + "</td><td>" + ns + "</td><td>" + nl + "</td></tr>");
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