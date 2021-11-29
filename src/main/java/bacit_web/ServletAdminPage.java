package bacit_web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/ServletAdminPage")
public class ServletAdminPage extends HttpServlet {
//Sjekker om session inneholder admin
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        ValidateUtils v = new ValidateUtils();

        //Kaller på metode fra ValidateUtils som validerer om bruker er admin
        if (v.validateAdmin(session) == true) {
            request.getRequestDispatcher("AdminPage.html").forward(request, response);
        } else {
            out.println("Du er ikke admin!");
        }
    }
}
