<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Withdraw Amount</title>
</head>
<body>
    <h2>Withdraw Amount</h2>
    <form action="Withdrow" method="post">
        <label>Account Number: </label><input type="text" name="account_number"  placeholder = "Enter your Account number"required><br><br>
        <label>Enter Amount: </label><input type="number" name="amount" placeholder = "Enter your amount" required><br><br>
        <button type="submit">Withdraw</button>
    </form>
</body>
</html>