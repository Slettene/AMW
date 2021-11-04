package bacit_web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
@WebServlet("/downloadFileServlet")
public class FileDownload extends HttpServlet {

    // size of byte buffer to send file
    private static final int BUFFER_SIZE = 4096;




    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        // get upload id from URL's parameters
        int uploadId = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();

        Connection db = null; // connection to the database

        try {
            // connects to the database
            db = DBUtils.getINSTANCE().getConnection(out);


            // queries the database
            String sql = "SELECT * FROM Filer WHERE FilID = ?";
            PreparedStatement statement = db.prepareStatement(sql);
            statement.setInt(1, uploadId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                // gets file name and file blob data
                String Fil = result.getString("FilID");
                Blob blob = result.getBlob("Fil");
                InputStream inputStream = blob.getBinaryStream();
                int fileLength = (int) blob.length();

                System.out.println("fileLength = " + fileLength);

                ServletContext context = getServletContext();

                // sets MIME type for the file download
                String mimeType = context.getMimeType(Fil);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }

                response.reset();

                // set content properties and header attributes for the response
                response.setContentType(mimeType);
                response.setContentLength(fileLength);
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", "pdf.pdf");
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