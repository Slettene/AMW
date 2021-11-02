package bacit_web;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/DisplayFileServlet"})
public class DisplayFileServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String filID = request.getParameter("filID");
        try{
            Connection db = null;


            Statement stmt = db.createStatement();
            String query = "SELECT * FROM Filer WHERE FilID = " + filID;
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next())
            {
                File blobFile = null;
                blobFile = new File(rs.getString("filID"));

                Blob blob = rs.getBlob("filID");
                InputStream in = blob.getBinaryStream();

                int length = in.available();
                byte[] blobBytes = new byte[ length ];
                in.read( blobBytes );

                FileOutputStream fos = new FileOutputStream(blobFile);
                fos.write( blobBytes );
                fos.close();
                rs.close();
                stmt.close();
                db.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
