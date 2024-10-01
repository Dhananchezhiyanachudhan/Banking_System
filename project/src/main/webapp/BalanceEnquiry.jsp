<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Balance Enquiry</title>
</head>
<body>
 <form method="get" action="">
        <label>Enter Your Account Number: </label>
        <input type="number" placeholder="Enter your Account Number!" name="Ac" required><br><br>
        <button type="submit">View Balance</button>
    </form>
    
    <h2>Your Account Balance</h2>
    <%
        String accountNumber = request.getParameter("Ac");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

            String query = "SELECT account_balance FROM account_table WHERE account_number = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, accountNumber);
            rs = ps.executeQuery();

            if (rs.next()) {
    %>
    <p>Your current balance is: ₹<%= rs.getDouble("account_balance") %></p>
    <%
            } else {
                out.println("<p>No account found.</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    %>
</body>
</html>