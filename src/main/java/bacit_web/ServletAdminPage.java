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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        ValidateUtils v = new ValidateUtils();

        if (v.validateAdmin(session) == true) {
            request.getRequestDispatcher("AdminPage.html").forward(request, response);
        } else {
            out.println("Du er ikke admin!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Connection con;
        PreparedStatement ps;

        String ansattID = request.getParameter("AnsattID1");

        try {
            con = DBUtils.getINSTANCE().getConnection();
            String query = "delete from Amv.Ansatt where AnsattAdmin = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, ansattID);
            ps.execute();
            response.sendRedirect("AdminPage.html");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
