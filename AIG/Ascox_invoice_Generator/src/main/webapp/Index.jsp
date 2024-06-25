<!DOCTYPE html>
<html>
<head>
    <title>Create Invoice</title>
    <style>
        body {
            background-image: url("https://img.freepik.com/free-vector/abstract-digital-grid-vector-black-background_53876-111550.jpg");
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }

        h2, h1 {
            color: white;
        }

        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            max-width: 900px;
            margin: 0 auto;
        }

        .form-section {
            display: flex;
            justify-content: space-between;
        }

        .form-group {
            flex: 1;
            padding: 10px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #555;
        }

        .form-group input[type="text"], .form-group input[type="date"], .form-group input[type="email"], .form-group input[type="number"], .form-group textarea {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        .form-group textarea {
            height: 100px;
            resize: vertical;
        }

        .form-group input[type="submit"] {
            background-color: #28a745;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .form-group input[type="submit"]:hover {
            background-color: #218838;
        }

        .from-group, .to-group {
            flex: 1;
        }

        #first {
            margin-left: 25%;
            padding: 15px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .add-item-btn {
            background-color: #007bff;
            color: white;
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            margin-top: 10px;
        }

        .add-item-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <form action="Display" method="get" id="first" style="background-color: aqua;; width:70%; padding:8px; position:relative; right:11%; border-radius: 8px; text-align: center; ">
        <label for="invoiceNumber" style="font-weight: bold; color:black; margin-bottom: 10px; font-size: 25px;">Invoice Number:</label>
        <input type="text" id="invoiceNumber" name="invoiceNumber" required style="font-size: 20px;">
        <input type="submit" value="Display Invoice" style="font-size: 18px;">
        <div>
            <br>
        </div>
     
    </form>

    <div class="form-container">
        <h1>Create Estimation</h1>
        <form action="saveInvoice" method="post">
            <div class="form-group">
                <label for="invoiceNumber">Invoice Number:</label>
                <input type="text" id="invoiceNumber" name="invoiceNumber" required>
            </div>
            <div class="form-group">
                <label for="invoiceDate">Invoice Date:</label>
                <input type="date" id="invoiceDate" name="invoiceDate" required>
            </div>
            <div class="form-group">
                <label for="dueDate">Due Date:</label>
                <input type="date" id="dueDate" name="dueDate" required>
            </div>
            <div class="form-section">
                <div class="from-group form-group">
                    <h3>From</h3>
                    <label for="fromName">Company Name:</label>
                    <input type="text" id="fromName" name="fromName" required>
                    <label for="fromAddress">Company Address:</label>
                    <input type="text" id="fromAddress" name="fromAddress" required>
                    <label for="fromEmail">Company Email:</label>
                    <input type="email" id="fromEmail" name="fromEmail" required>
                    <label for="fromMobile">Company Mobile:</label>
                    <input type="text" id="fromMobile" name="fromMobile" required>
                </div>
                <div class="to-group form-group">
                    <h3>Bill To</h3>
                    <label for="toName">Client Name:</label>
                    <input type="text" id="toName" name="toName" required>
                    <label for="toAddress">Client Address:</label>
                    <input type="text" id="toAddress" name="toAddress" required>
                    <label for="toEmail">Client Email:</label>
                    <input type="email" id="toEmail" name="toEmail" required>
                    <label for="toMobile">Client Mobile:</label>
                    <input type="text" id="toMobile" name="toMobile" required>
                </div>
            </div>
            <div class="form-group">
                <h3>Item Details</h3>
                <table id="itemTable">
                    <thead>
                        <tr>
                            <th>Description</th>
                            <th>Rate</th>
                            <th>Quantity</th>
                            <th>Amount</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td style="width:60%;"><textarea   name="description[]" required></textarea></td>
                            <td style="width:10%;"><input type="number" name="rate[]" step="0.01" required></td>
                            <td style="width:10%;"><input type="number" name="quantity[]" required></td>
                            <td style="width:10%;"><input type="number" name="amount[]" step="0.01" required></td>
                            <td style="width:10%;"><button type="button" class="remove-item-btn">Remove</button></td>
                        </tr>
                    </tbody>
                </table>
                <button type="button" class="add-item-btn" onclick="addItem()">Add Item</button>
            </div>
            <div class="form-group">
                <input type="submit" value="Create Invoice">
            </div>
        </form>
    </div>

    <script>
        function addItem() {
            var table = document.getElementById("itemTable").getElementsByTagName('tbody')[0];
            var newRow = table.insertRow();
            newRow.innerHTML = `
                <td><textarea   name="description[]" required></textarea></td>
                <td><input type="number" name="rate[]" step="0.01" required></td>
                <td><input type="number" name="quantity[]" required></td>
                <td><input type="number" name="amount[]" step="0.01" required></td>
                <td><button type="button" class="remove-item-btn" onclick="removeItem(this)">Remove</button></td>
            `;
        }

        function removeItem(button) {
            var row = button.parentNode.parentNode;
            row.parentNode.removeChild(row);
        }
    </script>
</body>
</html>
