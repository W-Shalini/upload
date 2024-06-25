package Invoice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/displayInvoice")
public class DisplayInvoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static class InvoiceItem {
        private int id;
        private String description;
        private double rate;
        private int quantity;
        private double amount;

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public double getRate() { return rate; }
        public void setRate(double rate) { this.rate = rate; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String invoiceNumber = request.getParameter("invoiceNumber");

        String jdbcURL = "jdbc:mysql://localhost:3306/invoice";
        String dbUser = "root";
        String dbPassword = "Db@123root";

        Connection connection = null;
        PreparedStatement invoiceQueryStatement = null;
        PreparedStatement itemsQueryStatement = null;
        ResultSet invoiceResultSet = null;
        ResultSet itemsResultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Retrieve invoice details
            String invoiceQuerySql = "SELECT * FROM invoices WHERE invoiceNumber = ?";
            invoiceQueryStatement = connection.prepareStatement(invoiceQuerySql);
            invoiceQueryStatement.setString(1, invoiceNumber);
            invoiceResultSet = invoiceQueryStatement.executeQuery();

            int invoiceId = 0;
            if (invoiceResultSet.next()) {
                invoiceId = invoiceResultSet.getInt("id");
                request.setAttribute("invoiceNumber", invoiceResultSet.getString("invoiceNumber"));
                request.setAttribute("invoiceDate", invoiceResultSet.getString("invoiceDate"));
                request.setAttribute("dueDate", invoiceResultSet.getString("dueDate"));
                request.setAttribute("fromName", invoiceResultSet.getString("fromName"));
                request.setAttribute("fromAddress", invoiceResultSet.getString("fromAddress"));
                request.setAttribute("fromEmail", invoiceResultSet.getString("fromEmail"));
                request.setAttribute("fromMobile", invoiceResultSet.getString("fromMobile"));
                request.setAttribute("toName", invoiceResultSet.getString("toName"));
                request.setAttribute("toAddress", invoiceResultSet.getString("toAddress"));
                request.setAttribute("toEmail", invoiceResultSet.getString("toEmail"));
                request.setAttribute("toMobile", invoiceResultSet.getString("toMobile"));
            }

            // Retrieve invoice items
            String itemsQuerySql = "SELECT * FROM invoice_items WHERE invoiceId = ?";
            itemsQueryStatement = connection.prepareStatement(itemsQuerySql);
            itemsQueryStatement.setInt(1, invoiceId);
            itemsResultSet = itemsQueryStatement.executeQuery();

            ArrayList<InvoiceItem> items = new ArrayList<>();
            while (itemsResultSet.next()) {
                InvoiceItem item = new InvoiceItem();
                item.setId(itemsResultSet.getInt("id"));
                item.setDescription(itemsResultSet.getString("description"));
                item.setRate(itemsResultSet.getDouble("rate"));
                item.setQuantity(itemsResultSet.getInt("quantity"));
                item.setAmount(itemsResultSet.getDouble("amount"));
                items.add(item);
            }
            request.setAttribute("items", items);

            // Forward to the JSP page
            request.getRequestDispatcher("editInvoice.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(response.getWriter());
        } finally {
            // Close resources
            try {
                if (invoiceResultSet != null) invoiceResultSet.close();
                if (itemsResultSet != null) itemsResultSet.close();
                if (invoiceQueryStatement != null) invoiceQueryStatement.close();
                if (itemsQueryStatement != null) itemsQueryStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(response.getWriter());
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
