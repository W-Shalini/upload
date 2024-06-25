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


/**
 * Servlet implementation class Display
 */
@WebServlet("/Display")
public class Display extends HttpServlet {
	 private static final long serialVersionUID = 1L;

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String invoiceNumber = request.getParameter("invoiceNumber");

	        String jdbcURL = "jdbc:mysql://localhost:3306/invoice";
	        String dbUser = "root";
	        String dbPassword = "Db@123root";

	        Connection connection = null;

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

	            // Fetch invoice details
	            String invoiceSql = "SELECT * FROM invoices WHERE invoiceNumber = ?";
	            PreparedStatement invoiceStatement = connection.prepareStatement(invoiceSql);
	            invoiceStatement.setString(1, invoiceNumber);

	            ResultSet invoiceResult = invoiceStatement.executeQuery();

	            if (invoiceResult.next()) {
	                request.setAttribute("invoiceNumber", invoiceResult.getString("invoiceNumber"));
	                request.setAttribute("invoiceDate", invoiceResult.getString("invoiceDate"));
	                request.setAttribute("dueDate", invoiceResult.getString("dueDate"));
	                request.setAttribute("fromName", invoiceResult.getString("fromName"));
	                request.setAttribute("fromAddress", invoiceResult.getString("fromAddress"));
	                request.setAttribute("fromEmail", invoiceResult.getString("fromEmail"));
	                request.setAttribute("fromMobile", invoiceResult.getString("fromMobile"));
	                request.setAttribute("toName", invoiceResult.getString("toName"));
	                request.setAttribute("toAddress", invoiceResult.getString("toAddress"));
	                request.setAttribute("toEmail", invoiceResult.getString("toEmail"));
	                request.setAttribute("toMobile", invoiceResult.getString("toMobile"));

	                // Fetch invoice items
	                String itemSql = "SELECT * FROM invoice_items WHERE invoiceId = ?";
	                PreparedStatement itemStatement = connection.prepareStatement(itemSql);
	                itemStatement.setInt(1, invoiceResult.getInt("id"));

	                ResultSet itemResult = itemStatement.executeQuery();

	                ArrayList<InvoiceItem> items = new ArrayList<>();
	                while (itemResult.next()) {
	                    InvoiceItem item = new InvoiceItem();
	                    item.setDescription(itemResult.getString("description"));
	                    item.setRate(itemResult.getDouble("rate"));
	                    item.setQuantity(itemResult.getInt("quantity"));
	                    item.setAmount(itemResult.getDouble("amount"));
	                    items.add(item);
	                }

	                request.setAttribute("items", items);

	                request.getRequestDispatcher("Generator.jsp").forward(request, response);

	                itemStatement.close();
	            } else {
	                response.getWriter().println("Invoice not found.");
	            }

	            invoiceStatement.close();
	            connection.close();
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }

	    public static class InvoiceItem {
	        private String description;
	        private double rate;
	        private int quantity;
	        private double amount;

	        // Getters and setters
	        public String getDescription() {
	            return description;
	        }

	        public void setDescription(String description) {
	            this.description = description;
	        }

	        public double getRate() {
	            return rate;
	        }

	        public void setRate(double rate) {
	            this.rate = rate;
	        }

	        public int getQuantity() {
	            return quantity;
	        }

	        public void setQuantity(int quantity) {
	            this.quantity = quantity;
	        }

	        public double getAmount() {
	            return amount;
	        }

	        public void setAmount(double amount) {
	            this.amount = amount;
	        }
	    }
	}
