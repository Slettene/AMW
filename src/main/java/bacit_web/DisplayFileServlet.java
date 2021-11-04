package bacit_web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that retrieves a file from MySQL database and lets the client
 * downloads the file.
 * @author www.codejava.net
 */
@WebServlet("/DisplayFileServlet")
public class DisplayFileServlet extends HttpServlet {

    // size of byte buffer to send file
    private static final int BUFFER_SIZE = 4096;




    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        // get upload id from URL's parameters
        String uploadId = request.getParameter("filID");
        PrintWriter out = response.getWriter();

        Connection db = null; // connection to the database

        try {
            // connects to the database
            db = DBUtils.getINSTANCE().getConnection(out);


            // queries the database
            Statement stm = db.createStatement();
            ResultSet rs = stm.executeQuery ("SELECT * FROM Filer WHERE FilID ='"+uploadId+"'");

            if (rs.next()) {
                // gets file name and file blob data
                String fileName = rs.getString("FilID");
                Blob blob = rs.getBlob("Fil");
                InputStream inputStream = blob.getBinaryStream();


                ServletContext context = getServletContext();

                // sets MIME type for the file download
                String mimeType = context.getMimeType(fileName);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }

                // set content properties and header attributes for the response
                response.setContentType(mimeType);

                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
                response.setHeader(headerKey, headerValue);

                // writes the file to the client
                OutputStream outStream = response.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outStream.close();
            } else {
                // no file found
                response.getWriter().print("File not found for the id: " + uploadId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            response.getWriter().print("SQL Error: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            response.getWriter().print("IO Error: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                // closes the database connection
                try {
                    db.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}