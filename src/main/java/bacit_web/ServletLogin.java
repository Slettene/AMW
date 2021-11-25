package bacit_web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//Validerer brukere fra databasen

@WebServlet(value = "/ServletLogin")
public class ServletLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

//Setter parameter for login

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String ansattID = request.getParameter("AnsattID1");
        String ansattTelefon = request.getParameter("ATelefon1");

        PrintWriter out = response.getWriter();

        ValidateUtils v = new ValidateUtils();

        //Validerer om parameter finnes i databasen og at de stemmer overens

        if (v.validateLogin(ansattID, ansattTelefon) == true) {
            HttpSession session = request.getSession();
            session.setAttribute("AnsattID", ansattID);

            response.sendRedirect("Home.html");

        } else {
            out.println("OBS! Enten AnsattID eller ATelefon er feil! prøv en gang til!");
        }
    }
}
