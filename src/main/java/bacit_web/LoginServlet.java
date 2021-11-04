package bacit_web;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username1");
        String password = request.getParameter("password1");

        Connection db = null;

        try{
            db = DBUtils.getINSTANCE().getConnection(out);


            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("select * from Ansatte where AnsattID= '"+username+"' and ATelefon= '"+password+"'");

            if(rs.next()){
                response.sendRedirect("Home.html");
            }else {
                response.sendRedirect("Innstillinger.html");
            }

            db.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




    }


}


