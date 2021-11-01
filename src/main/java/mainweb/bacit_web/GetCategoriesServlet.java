package mainweb.bacit_web;

import mainweb.bacit_models.CategoryModel;
import mainweb.bacit_utilities.HtmlHelper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name = "getCategories", value = "/getCategories")
public class GetCategoriesServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStart(out, "Categories");
        try{
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Name</th>");
        out.println("<th>Image</th>");
        out.println("</tr>");

        Collection<CategoryModel> categories = getCategories(out);
        for(CategoryModel category : categories)
        {
            out.println("<tr>");
            out.println("<td>"+category.getId()+"</td>");
            out.println("<td>"+category.getName()+"</td>");
            out.println("<td><img src='"+category.getImagePath()+"' alt='"+category.getName()+"'/></td>");
            out.println("</tr>");
        }
        out.println("</table>");
        }
        catch(SQLException ex){
            out.println(ex.getMessage());
        }
        HtmlHelper.writeHtmlEnd(out);
    }

    private Collection<CategoryModel> getCategories(PrintWriter out)throws SQLException {
        ArrayList<CategoryModel> result =  new ArrayList<CategoryModel>();

        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query3 = "select * from categories";
        PreparedStatement statement = db.prepareStatement(query3);
        ResultSet rs =  statement.executeQuery();

        while (rs.next()) {
             result.add(new CategoryModel(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("ImagePath")
                            ));
        }
        return result;
    }


}
