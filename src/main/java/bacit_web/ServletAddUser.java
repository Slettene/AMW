package bacit_web;

import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/ServletAddUser"})
//Legge til i databasen
public class ServletAddUser extends HttpServlet{
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Connection db = null;
        String AnsattMail = request.getParameter("AnsattMail1");
        String AnsattTelefon = request.getParameter("AnsattTelefon1");
        String AnsattOrganisert = request.getParameter("AnsattOrganisert1");
        String AnsattAdmin = request.getParameter("AnsattAdmin1");
        try{
            //Legge til ansatte
            db = DBUtils.getINSTANCE().getConnection();
            PreparedStatement pst = db.prepareStatement("insert into Ansatt(AnsattMail, AnsattTelefon, AnsattOrganisert, AnsattAdmin) values(?,?,?,?)");
            pst.setString(1,AnsattMail);
            pst.setString(2,AnsattTelefon);
            pst.setString(3,AnsattOrganisert);
            pst.setString(4,AnsattAdmin);
            int i = pst.executeUpdate();
            if(i!=0){
                out.println("<br>Bruker lagt til i database");
                out.println("<button onclick=\"window.location.href='AdminPage.html'\">Tilbake\n" + "</button>");
            }
            else{
                out.println("Kunne ikke legge til brukeren i databasen");
            }
        }
        catch (Exception e){
            out.println(e);
        }
    }
}