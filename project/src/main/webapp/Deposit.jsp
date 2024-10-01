

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Deposit Amount</title>
</head>
<body>
    <h2>Deposit Amount</h2>
    <form action="Deposit" method="post">
        <label>Enter Amount: </label><input type="number" name="amount" placeholder="Enetr your amount" required><br><br>
       <label>Account Number :</label> <input type="number" name="account_number" placeholder="Enter your account number !"><br><br>
        <button type="submit">Deposit</button>
    </form>
</body>
</html>
