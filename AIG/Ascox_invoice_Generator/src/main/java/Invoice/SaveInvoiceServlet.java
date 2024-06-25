package Invoice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

@WebServlet("/saveInvoice")
public class SaveInvoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        String[] descriptions = request.getParameterValues("description[]");
        String[] rates = request.getParameterValues("rate[]");
        String[] quantities = request.getParameterValues("quantity[]");
        String[] amounts = request.getParameterValues("amount[]");

        String jdbcURL = "jdbc:mysql://localhost:3306/invoice";
        String dbUser = "root";
        String dbPassword = "Db@123root";

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            connection.setAutoCommit(false);  // Start transaction

            String invoiceSql = "INSERT INTO invoices (invoiceNumber, invoiceDate, dueDate, fromName, fromAddress, fromEmail, fromMobile, toName, toAddress, toEmail, toMobile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement invoiceStatement = connection.prepareStatement(invoiceSql, PreparedStatement.RETURN_GENERATED_KEYS);
            invoiceStatement.setString(1, invoiceNumber);
            invoiceStatement.setString(2, invoiceDate);
            invoiceStatement.setString(3, dueDate);
            invoiceStatement.setString(4, fromName);
            invoiceStatement.setString(5, fromAddress);
            invoiceStatement.setString(6, fromEmail);
            invoiceStatement.setString(7, fromMobile);
            invoiceStatement.setString(8, toName);
            invoiceStatement.setString(9, toAddress);
            invoiceStatement.setString(10, toEmail);
            invoiceStatement.setString(11, toMobile);
            invoiceStatement.executeUpdate();

            // Get the generated invoice ID
            int invoiceId;
            try (ResultSet generatedKeys = invoiceStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    invoiceId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating invoice failed, no ID obtained.");
                }
            }

            String itemSql = "INSERT INTO invoice_items (invoiceId, description, rate, quantity, amount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement itemStatement = connection.prepareStatement(itemSql);

            for (int i = 0; i < descriptions.length; i++) {
                itemStatement.setInt(1, invoiceId);
                itemStatement.setString(2, descriptions[i]);
                itemStatement.setDouble(3, Double.parseDouble(rates[i]));
                itemStatement.setInt(4, Integer.parseInt(quantities[i]));
                itemStatement.setDouble(5, Double.parseDouble(amounts[i]));
                itemStatement.addBatch();
            }

            itemStatement.executeBatch();

            connection.commit();  // Commit transaction

            // Redirect to the display page servlet with invoice number as parameter
            String redirectURL = "Display?invoiceNumber=" + invoiceNumber;
            response.sendRedirect(redirectURL);

            invoiceStatement.close();
            itemStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();  // Rollback transaction on error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
