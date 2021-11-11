/*package bacit_web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;



@WebServlet
public class ValidationServlet extends HttpServlet {
    public boolean ValidateLogin(String email, String password) {
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection db = null;
        PrintWriter out = response.getWriter();

        try{
            db = DBUtils.getINSTANCE().getConnection(out);
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * from Ansatt where Organisert= ");

    }

        try {
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    } */

