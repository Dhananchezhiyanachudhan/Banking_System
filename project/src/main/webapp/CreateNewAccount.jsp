<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create New Account</title>
</head>
<body>
<h2>Create New Account:</h2>
<form action="CreateNewAccount" method="get">
    <label>Name:</label><input type="text" placeholder="Enter your name" name="name" required><br><br>
    <label>Phone Number:</label><input type="text" placeholder="Your number" name="phone" required><br><br>
    <label>Email:</label><input type="email" placeholder="Email ID" name="mail" required><br><br>
    <label>Aadhar Number:</label><input type="text" placeholder="Aadhar Number" name="aadhar" required><br><br>
    <label>Password:</label><input type="password" placeholder="Enter your Password" name="password" required><br><br>
    <label>Gender:</label><input type="text" placeholder="Your gender" name="gender" required><br><br>
    <label>MPIN:</label><input type="text" placeholder="MPIN" name="mpin" required><br><br>
    <label>Age:</label><input type="text" placeholder="Enter Your Age" name="age" required><br><br><br>
    <button type="submit">Create Account</button>
</form>
</body>
</html>