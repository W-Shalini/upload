package Invoice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateInvoice")
public class UpdateInvoiceServlet extends HttpServlet {
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

        String jdbcURL = "jdbc:mysql://localhost:3306/invoice";
        String dbUser = "root";
        String dbPassword = "Db@123root";

        Connection connection = null;
        PreparedStatement invoiceUpdateStatement = null;
        PreparedStatement itemUpdateStatement = null;
        PreparedStatement invoiceIdStatement = null;
        ResultSet invoiceIdResultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Get invoice ID
            String invoiceIdSql = "SELECT id FROM invoices WHERE invoiceNumber = ?";
            invoiceIdStatement = connection.prepareStatement(invoiceIdSql);
            invoiceIdStatement.setString(1, invoiceNumber);
            invoiceIdResultSet = invoiceIdStatement.executeQuery();

            int invoiceId = 0;
            if (invoiceIdResultSet.next()) {
                invoiceId = invoiceIdResultSet.getInt("id");
            }

            // Update invoice details
            String invoiceUpdateSql = "UPDATE invoices SET invoiceDate = ?, dueDate = ?, fromName = ?, fromAddress = ?, fromEmail = ?, fromMobile = ?, toName = ?, toAddress = ?, toEmail = ?, toMobile = ? WHERE id = ?";
            invoiceUpdateStatement = connection.prepareStatement(invoiceUpdateSql);
            invoiceUpdateStatement.setString(1, invoiceDate);
            invoiceUpdateStatement.setString(2, dueDate);
            invoiceUpdateStatement.setString(3, fromName);
            invoiceUpdateStatement.setString(4, fromAddress);
            invoiceUpdateStatement.setString(5, fromEmail);
            invoiceUpdateStatement.setString(6, fromMobile);
            invoiceUpdateStatement.setString(7, toName);
            invoiceUpdateStatement.setString(8, toAddress);
            invoiceUpdateStatement.setString(9, toEmail);
            invoiceUpdateStatement.setString(10, toMobile);
            invoiceUpdateStatement.setInt(11, invoiceId);

            invoiceUpdateStatement.executeUpdate();

            // Update invoice items
            String itemUpdateSql = "UPDATE invoice_items SET description = ?, rate = ?, quantity = ?, amount = ? WHERE id = ? AND invoiceId = ?";
            itemUpdateStatement = connection.prepareStatement(itemUpdateSql);

            // Assuming item IDs are passed as hidden fields in the form
            String[] itemIds = request.getParameterValues("items.id");
            String[] descriptions = request.getParameterValues("items.description");
            String[] rates = request.getParameterValues("items.rate");
            String[] quantities = request.getParameterValues("items.quantity");
            String[] amounts = request.getParameterValues("items.amount");

            for (int i = 0; i < itemIds.length; i++) {
                itemUpdateStatement.setString(1, descriptions[i]);
                itemUpdateStatement.setDouble(2, Double.parseDouble(rates[i]));
                itemUpdateStatement.setInt(3, Integer.parseInt(quantities[i]));
                itemUpdateStatement.setDouble(4, Double.parseDouble(amounts[i]));
                itemUpdateStatement.setInt(5, Integer.parseInt(itemIds[i]));
                itemUpdateStatement.setInt(6, invoiceId);

                itemUpdateStatement.executeUpdate();
            }

            response.sendRedirect("Display?invoiceNumber=" + invoiceNumber);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(response.getWriter());
        } finally {
            // Close resources
            try {
                if (invoiceIdResultSet != null) invoiceIdResultSet.close();
                if (invoiceUpdateStatement != null) invoiceUpdateStatement.close();
                if (itemUpdateStatement != null) itemUpdateStatement.close();
                if (invoiceIdStatement != null) invoiceIdStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(response.getWriter());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
