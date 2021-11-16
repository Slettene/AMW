package bacit_web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/ServletLogin")
public class ServletLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String ansattID = request.getParameter("AnsattID1");
        String ansattTelefon = request.getParameter("ATelefon1");

        PrintWriter out = response.getWriter();

        ValidateUtils v = new ValidateUtils();

        if (v.validateLogin(ansattID, ansattTelefon) == true) {
            HttpSession session = request.getSession();
            session.setAttribute("AnsattID", ansattID);

            //out.println("Du er nå logget inn!");
            response.sendRedirect("Home.html");

        } else {
            out.println("OBS! Enten AnsattID eller ATelefon er feil! prøv en gang til!");
        }
    }
}
