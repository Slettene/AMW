package bacit_web;

import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
//Endrer brukere i databasen
@WebServlet(urlPatterns = {"/ServletUpdateUser"})
public class ServletUpdateUser extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Connection db = null;
        PreparedStatement pst = null;

        //Henter input for hva som skal slettes

        try{
            String AnsattID = request.getParameter("AnsattID2");
            String AnsattMail = request.getParameter("AnsattMail2");
            String AnsattTelefon = request.getParameter("AnsattTelefon2");
            String AnsattOrganisert = request.getParameter("AnsattOrganisert2");
            String AnsattAdmin = request.getParameter("AnsattAdmin2");
            db = DBUtils.getINSTANCE().getConnection();

            //Sletter bruker via prepared statement

            if(request.getParameter("AnsattID")==null)
                pst = db.prepareStatement("update Ansatt set AnsattMail=?, AnsattTelefon=?, AnsattOrganisert=?, AnsattAdmin=? where AnsattID=?");
                pst.setString(1,AnsattMail);
                pst.setString(2,AnsattTelefon);
                pst.setString(3,AnsattOrganisert);
                pst.setString(4,AnsattAdmin);
                pst.setString(5,AnsattID);
                int i = pst.executeUpdate();
                out.println(i);
            if(i!=0){
                out.println("<br>Bruker er oppdatert");
                out.println("<button onclick=\"window.location.href='AdminPage.html'\">Tilbake\n" + "</button>");
            }
            else{
                out.println("Kunne ikke oppdatere brukeren");
            }
        }
        catch (Exception e){
            out.println(e);
        }
    }
}