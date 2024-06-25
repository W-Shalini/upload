<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Invoice.DisplayInvoiceServlet.InvoiceItem" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Invoice</title>
      <style>
        body {
                    background-image: url("https://img.freepik.com/free-vector/abstract-digital-grid-vector-black-background_53876-111550.jpg");
        
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            width: 80%;
            margin: auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input[type="text"],
        input[type="email"],
        input[type="number"],
        input[type="date"] {
            width: calc(100% - 20px);
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        h3 {
            color: #333;
            margin-top: 20px;
        }
        .item-container {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            margin-top: 10px;
        }
    </style>
    
</head>
<body>
    <h2 style="color:white;">Edit Invoice</h2>
    <form action="updateInvoice" method="post">
        <label for="invoiceNumber">Invoice Number:</label>
        <input type="text" id="invoiceNumber" name="invoiceNumber" value="<%= request.getAttribute("invoiceNumber") %>" readonly><br><br>

        <label for="invoiceDate">Invoice Date:</label>
        <input type="date" id="invoiceDate" name="invoiceDate" value="<%= request.getAttribute("invoiceDate") %>"><br><br>

        <label for="dueDate">Due Date:</label>
        <input type="date" id="dueDate" name="dueDate" value="<%= request.getAttribute("dueDate") %>"><br><br>

        <h3>From</h3>
        <label for="fromName">Name:</label>
        <input type="text" id="fromName" name="fromName" value="<%= request.getAttribute("fromName") %>"><br><br>

        <label for="fromAddress">Address:</label>
        <input type="text" id="fromAddress" name="fromAddress" value="<%= request.getAttribute("fromAddress") %>"><br><br>

        <label for="fromEmail">Email:</label>
        <input type="email" id="fromEmail" name="fromEmail" value="<%= request.getAttribute("fromEmail") %>"><br><br>

        <label for="fromMobile">Mobile:</label>
        <input type="text" id="fromMobile" name="fromMobile" value="<%= request.getAttribute("fromMobile") %>"><br><br>

        <h3>To</h3>
        <label for="toName">Name:</label>
        <input type="text" id="toName" name="toName" value="<%= request.getAttribute("toName") %>"><br><br>

        <label for="toAddress">Address:</label>
        <input type="text" id="toAddress" name="toAddress" value="<%= request.getAttribute("toAddress") %>"><br><br>

        <label for="toEmail">Email:</label>
        <input type="email" id="toEmail" name="toEmail" value="<%= request.getAttribute("toEmail") %>"><br><br>

        <label for="toMobile">Mobile:</label>
        <input type="text" id="toMobile" name="toMobile" value="<%= request.getAttribute("toMobile") %>"><br><br>

        <h3>Items</h3>
        <%
            ArrayList<InvoiceItem> items = (ArrayList<InvoiceItem>) request.getAttribute("items");
            for (int i = 0; i < items.size(); i++) {
                InvoiceItem item = items.get(i);
        %>
        <div>
            <input type="hidden" name="items.id" value="<%= item.getId() %>">
            <label for="description<%= i %>">Description:</label>
            <input type="text" id="description<%= i %>" name="items.description" value="<%= item.getDescription() %>"><br><br>

            <label for="rate<%= i %>">Rate:</label>
            <input type="number" step="0.01" id="rate<%= i %>" name="items.rate" value="<%= item.getRate() %>"><br><br>

            <label for="quantity<%= i %>">Quantity:</label>
            <input type="number" id="quantity<%= i %>" name="items.quantity" value="<%= item.getQuantity() %>"><br><br>

            <label for="amount<%= i %>">Amount:</label>
            <input type="number" step="0.01" id="amount<%= i %>" name="items.amount" value="<%= item.getAmount() %>"><br><br>
        </div>
        <% } %>

        <input type="submit" value="Update Invoice">
    </form>
</body>
</html>
