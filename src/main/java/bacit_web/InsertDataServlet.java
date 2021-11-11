package bacit_web;

import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/InsertDataServlet"})
public class InsertDataServlet extends HttpServlet{
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Connection db = null;
        String VerktoyNavn = request.getParameter("VerktoyNavn1");
        String VerktoyType = request.getParameter("VerktoyType1");
        String VerktoyBeskrivelse = request.getParameter("VerktoyBeskrivelse1");
        String VerktoySkadet = request.getParameter("VerktoySkadet1");
        String VerktoyLedig = request.getParameter("VerktoyLedig1");
        try{
            db = DBUtils.getINSTANCE().getConnection(out);
            PreparedStatement pst = db.prepareStatement("insert into Verktoy(VerktoyNavn, VerktoyType, VerktoyBeskrivelse, VerktoySkadet, VerktoyLedig) values(?,?,?,?,?)");
            pst.setString(1,VerktoyNavn);
            pst.setString(2,VerktoyType);
            pst.setString(3,VerktoyBeskrivelse);
            pst.setString(4,VerktoySkadet);
            pst.setString(5,VerktoyLedig);
            int i = pst.executeUpdate();
            if(i!=0){
                out.println("<br>Data lagt til i database");
                out.println("<button onclick=\"window.location.href='Home.html'\">Tilbake\n" + "</button>");
            }
            else{
                out.println("Kunne ikke legge til data i databasen");
            }
        }
        catch (Exception e){
            out.println(e);
        }
    }
}