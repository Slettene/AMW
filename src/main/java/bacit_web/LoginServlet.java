package bacit_web;

//DENNE ER IKKE I BRUK

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet (urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username1");
        String password = request.getParameter("password1");

        Connection db = null;

        try{
            db = DBUtils.getINSTANCE().getConnection();


            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from Ansatt where AnsattID= '"+username+"' and AnsattTelefon= '"+password+"'");

            if(rs.next()){
                response.sendRedirect("Home.html");
            }else {
                out.println("Feil brukernavn, eller passord");
             //   response.sendRedirect("Innstillinger.html");
                out.println("<button onclick=\"window.location.href='Login.html'\">Tilbake\n" + "</button>");
            }

            db.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




    }


}


