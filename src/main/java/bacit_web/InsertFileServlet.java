package bacit_web;

import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/InsertFileServlet"})
public class InsertFileServlet extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection db = null;
        try{
            String file = request.getParameter("file");
            db = DBUtils.getINSTANCE().getConnection();
            PreparedStatement pst = db.prepareStatement("insert into Filer(Fil) values(?)");
            pst.setObject(1,file);

            int i = pst.executeUpdate();
            if(i!=0){
                out.println("<br>Fil lagt til i database");
                out.println("<button onclick=\"window.location.href='Home.html'\">Tilbake\n" + "</button>");
            }
            else{
                out.println("Kunne ikke legge til filen i databasen");
            }
        }
        catch (Exception e){
            out.println(e);
        }
    }
}

