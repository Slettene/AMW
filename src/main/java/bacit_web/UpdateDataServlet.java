package bacit_web;

import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//Endre verktøy i databasen

@WebServlet(urlPatterns = {"/UpdateDataServlet"})
public class UpdateDataServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Connection db = null;
        PreparedStatement pst = null;

        //Henter input

        try{
            String VerktoyID = request.getParameter("VerktoyID2");
            String VerktoyNavn = request.getParameter("VerktoyNavn2");
            String VerktoyType = request.getParameter("VerktoyType2");
            String VerktoyBeskrivelse = request.getParameter("VerktoyBeskrivelse2");
            String VerktoySkadet = request.getParameter("VerktoySkadet2");
            String VerktoyLedig = request.getParameter("VerktoyLedig2");
            db = DBUtils.getINSTANCE().getConnection();

            //Utfører endring i databasen

            if(request.getParameter("VerktoyID")==null)
                pst = db.prepareStatement("update Verktoy set VerktoyNavn=?, VerktoyType=?, VerktoyBeskrivelse=?, VerktoySkadet=?, VerktoyLedig=? where VerktoyID=?");
            pst.setString(1,VerktoyNavn);
            pst.setString(2,VerktoyType);
            pst.setString(3,VerktoyBeskrivelse);
            pst.setString(4,VerktoySkadet);
            pst.setString(5,VerktoyLedig);
            pst.setString(6,VerktoyID);
            int i = pst.executeUpdate();
            out.println(i);
            if(i!=0){
                out.println("<br>Verktøy er oppdatert");
                out.println("<button onclick=\"window.location.href='AdminPage.html'\">Tilbake\n" + "</button>");
            }
            else{
                out.println("Kunne ikke oppdatere verktøy");
            }
        }
        catch (Exception e){
            out.println(e);
        }
    }
}