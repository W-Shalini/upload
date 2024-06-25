<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Invoice</title>
    <style>
        @media print {
            .hidden-print {
                display: none !important;
            }
        }
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .invoice-box {
            width: 800px;
            margin: auto;
            padding: 30px;
            border: 1px solid #eee;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
            background-color: #fff;
            font-size: 16px;
            line-height: 24px;
            color: #555;
        }
        .invoice-box table {
            width: 100%;
            line-height: inherit;
            text-align: left;
            border-collapse: collapse;
        }
        .invoice-box table td {
            padding: 5px;
            vertical-align: top;
            margin-left: 100px;
        }
        .invoice-box table tr td:nth-child(2) {
            text-align:  left;
        }
        .invoice-box table tr.top table td {
            padding-bottom: 20px;
        }
        .invoice-box table tr.top table td.title {
            font-size: 45px;
            line-height: 45px;
            color: #333;
        }
        .invoice-box table tr.information table td {
            padding-bottom: 40px;
        }
        .invoice-box table tr.heading td {
            background: #eee;
            border-bottom: 1px solid #ddd;
            font-weight: bold;
            padding: 10px;
        }
        .invoice-box table tr.details td {
            padding-bottom: 20px;
        }
        .invoice-box table tr.item td {
            border-bottom: 1px solid #eee;
            padding: 10px;
        }
        .invoice-box table tr.item.last td {
            border-bottom: none;
        }
        .invoice-box table tr.total td:nth-child(2) {
            border-top: 2px solid #eee;
            font-weight: bold;
            text-align: right;
        }
        .terms-conditions, .acceptance {
            margin-top: 30px;
        }
        .terms-conditions p, .acceptance p {
            margin: 0;
        }
        .box2{
              width:80%;
              position:relative;
             left:10%;
            
             
             
        }
        .box1{
      
        
       
        }
        .tab{
      
        width:100%;
     
       
        }
         .tab .box1{
          width: 80%;
       
         }
         
    </style>
</head>
<body>
    <button id="editBtn" class="hidden-print">Edit</button>
    <button id="printBtn" class="hidden-print" onclick="window.print()">Print</button>
    <div class="invoice-box" id="invoice">
        <table >
            <tr class="top">
                <td colspan="4">
                    <table>
                        <tr>
                            <td class="title">
                                <img src="./images/logo for rope (1).png" style="width:100%; max-width:300px;">
                            </td>
                            <td class="tag" >
                                <strong>Invoice Number:</strong> <%= request.getAttribute("invoiceNumber") %><br>
                                <strong>Invoice Date:</strong> <%= request.getAttribute("invoiceDate") %><br>
                                <strong>Due Date:</strong> <%= request.getAttribute("dueDate") %>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr class="information">
                <td >
                    <table class="tab">
                        <tr>
                            <td  class="box1">
                                <strong>From:</strong><br>
                                <b><%= request.getAttribute("fromName") %></b><br>
                                <%= request.getAttribute("fromAddress") %><br>
                                <%= request.getAttribute("fromEmail") %><br>
                                <%= request.getAttribute("fromMobile") %>
                            </td>
                           
                            <td  class="box2"  >
                                <strong>To:</strong><br>
                                <b><%= request.getAttribute("toName") %></b><br>
                                <%= request.getAttribute("toAddress") %><br>
                                <%= request.getAttribute("toEmail") %><br>
                                <%= request.getAttribute("toMobile") %>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr class="heading">
                <td>Description</td>
                <td>Rate</td>
                <td>Quantity</td>
                <td>Amount</td>
            </tr>
            <c:forEach var="item" items="${items}" varStatus="loop">
                <tr class="item">
                    <td>${item.description}</td>
                    <td>₹${item.rate}</td>
                    <td>${item.quantity}</td>
                    <td>
                        ₹<span id="amount${loop.index}">${item.amount}</span>
                        <input type="hidden" id="hiddenAmount${loop.index}" name="items[${loop.index}].amount" value="${item.amount}">
                    </td>
                </tr>
            </c:forEach>
            <tr class="total">
                <td colspan="3" style="text-align: right;">Subtotal:</td>
                <td>₹<span id="subtotal"><%= request.getAttribute("subtotal") %></span></td>
            </tr>
            <tr class="total">
                <td colspan="3" style="text-align: right;">Total:</td>
                <td>₹<span id="total"><%= request.getAttribute("total") %></span></td>
            </tr>
            <tr class="total">
                <td colspan="3" style="text-align: right;"><strong>Balance Due:</strong></td>
                <td>₹<span id="balanceDue"><%= request.getAttribute("total") %></span></td>
            </tr>
        </table>
        <div class="terms-conditions">
            <h3>TERMS AND CONDITIONS:</h3>
            <p>• Any additional features or changes requested by the client outside the scope of this estimate will be subject to additional charges.</p>
            <p>• AMC will be 35% of the Annual Charges.</p>
        </div>
        <div class="acceptance">
            <h3>ACCEPTANCE:</h3>
            <p>By signing below, the client agrees to the terms and conditions outlined in this estimate and authorizes Ascox Technosoft Solution Pvt Ltd, located at Madurai, Tamil Nadu to proceed with the development of the software as described.</p>
            <br><br>
            <p>CLIENT SIGNATURE: __________________________</p>
            <br>
        </div>
    </div>

    <form id="editForm" action="displayInvoice" method="get" style="display:none;">
        <input type="hidden" name="invoiceNumber" value="<%= request.getAttribute("invoiceNumber") %>">
        <input type="hidden" name="invoiceDate" value="<%= request.getAttribute("invoiceDate") %>">
        <input type="hidden" name="dueDate" value="<%= request.getAttribute("dueDate") %>">
        <input type="hidden" name="fromName" value="<%= request.getAttribute("fromName") %>">
        <input type="hidden" name="fromAddress" value="<%= request.getAttribute("fromAddress") %>">
        <input type="hidden" name="fromEmail" value="<%= request.getAttribute("fromEmail") %>">
        <input type="hidden" name="fromMobile" value="<%= request.getAttribute("fromMobile") %>">
        <input type="hidden" name="toName" value="<%= request.getAttribute("toName") %>">
        <input type="hidden" name="toAddress" value="<%= request.getAttribute("toAddress") %>">
        <input type="hidden" name="toEmail" value="<%= request.getAttribute("toEmail") %>">
        <input type="hidden" name="toMobile" value="<%= request.getAttribute("toMobile") %>">
    </form>

    <script>
        document.getElementById("editBtn").addEventListener("click", function() {
            document.getElementById("editForm").submit();
        });

        // Function to calculate amount based on rate and quantity
        function calculateAmount(index) {
            var rate = parseFloat(document.getElementById('itemRate' + index).value);
            var quantity = parseInt(document.getElementById('itemQuantity' + index).value);
            var amount = rate * quantity;
            document.getElementById('amount' + index).innerText = amount.toFixed(2); // Update displayed amount
            document.getElementById('hiddenAmount' + index).value = amount.toFixed(2); // Update hidden input value for form submission

            // Recalculate subtotal, total, and balance due
            calculateSubtotal();
            calculateTotal();
            calculateBalanceDue();
        }

        // Function to calculate subtotal
        function calculateSubtotal() {
            var subtotal = 0.0;
            var itemAmounts = document.querySelectorAll('tr.item');
            itemAmounts.forEach(function(item) {
                subtotal += parseFloat(item.querySelector('span').innerText);
            });
            document.getElementById('subtotal').innerText = subtotal.toFixed(2);
        }

        // Function to calculate total
        function calculateTotal() {
            var total = parseFloat(document.getElementById('subtotal').innerText);
            document.getElementById('total').innerText = total.toFixed(2);
        }

        // Function to calculate balance due (assuming it's the same as total in this example)
        function calculateBalanceDue() {
            var balanceDue = parseFloat(document.getElementById('total').innerText);
            document.getElementById('balanceDue').innerText = balanceDue.toFixed(2);
        }

        // Call the initial calculation functions
        calculateSubtotal();
        calculateTotal();
        calculateBalanceDue();
    </script>
</body>
</html>
