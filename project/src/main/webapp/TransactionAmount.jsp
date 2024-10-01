<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Amount Transfer</title>
</head>
<body>
    <h2>Transfer Amount</h2>
    <form action="TransactionAmount" method="post">
        <label>Your Account Number:</label>
        <input type="text" name="from_account" placeholder="From Account Number" required><br><br>

        <label>Recipient Account Number:</label>
        <input type="text" name="to_account" placeholder="to Account Number" required><br><br>

        <label>Amount to Transfer:</label>
        <input type="number" name="amount" required><br><br>

        <button type="submit">Transfer</button>
    </form>
</body>
</html>