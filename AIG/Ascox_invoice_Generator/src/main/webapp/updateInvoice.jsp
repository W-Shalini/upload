<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Database Retrieval Example</title>
</head>
<body>
    <h2>Database Retrieval Example</h2>
    <%
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/invoice";
        String username = "root";
        String password = "Db@123root";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            conn = DriverManager.getConnection(url, username, password);
            
            // Create statement
            stmt = conn.createStatement();
            
            // Execute query
            String sql = "SELECT * FROM invoice_items";
            rs = stmt.executeQuery(sql);
            
            // Process the result set
            if (!rs.isBeforeFirst()) {
                out.println("No data found.<br>");
            } else {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String description = rs.getString("description");
                    int rate = rs.getInt("rate");
                    int amount = rs.getInt("amount");
                    // Retrieve other columns as needed

                    out.println("ID: " + id + ", Description: " + description + ", Rate: " + rate + ", Amount: " + amount + "<br>");
                }
            }
        } catch (ClassNotFoundException e) {
            out.println("JDBC Driver not found: " + e.getMessage() + "<br>");
            e.printStackTrace(new java.io.PrintWriter(out));
        } catch (SQLException e) {
            out.println("SQL Exception: " + e.getMessage() + "<br>");
            e.printStackTrace(new java.io.PrintWriter(out));
        } catch (Exception e) {
            out.println("Exception: " + e.getMessage() + "<br>");
            e.printStackTrace(new java.io.PrintWriter(out));
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                out.println("Error closing resources: " + e.getMessage() + "<br>");
                e.printStackTrace(new java.io.PrintWriter(out));
            }
        }
    %>
</body>
</html>
