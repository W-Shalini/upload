package Invoice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/saveEditedInvoice")
public class SaveEditedInvoiceServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read form parameters
        String invoiceNumber = request.getParameter("invoiceNumber");
        String invoiceDate = request.getParameter("invoiceDate");
        String dueDate = request.getParameter("dueDate");
        String fromName = request.getParameter("fromName");
        String fromAddress = request.getParameter("fromAddress");
        String fromEmail = request.getParameter("fromEmail");
        String fromMobile = request.getParameter("fromMobile");
        String toName = request.getParameter("toName");
        String toAddress = request.getParameter("toAddress");
        String toEmail = request.getParameter("toEmail");
        String toMobile = request.getParameter("toMobile");
        

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice", "root", "Db@123root");

            // Update the invoice details in the database
            String sql = "UPDATE invoices SET invoiceDate = ?, dueDate = ?, fromName = ?, fromAddress = ?, fromEmail = ?, fromMobile = ?, toName = ?, toAddress = ?, toEmail = ?, toMobile = ?  WHERE invoiceNumber = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, invoiceDate);
            pstmt.setString(2, dueDate);
            pstmt.setString(3, fromName);
            pstmt.setString(4, fromAddress);
            pstmt.setString(5, fromEmail);
            pstmt.setString(6, fromMobile);
            pstmt.setString(7, toName);
            pstmt.setString(8, toAddress);
            pstmt.setString(9, toEmail);
            pstmt.setString(10, toMobile);
             pstmt.setString(11, invoiceNumber);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
            	  response.sendRedirect("displayInvoice?invoiceNumber=" + invoiceNumber);
            } else {
                response.getWriter().println("Error updating invoice. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database connection error: " + e.getMessage());
        } finally {
            // Close the resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

