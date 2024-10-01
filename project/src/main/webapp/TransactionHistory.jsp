<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transaction History</title>
</head>
<body>
    <form method="get" action="">
        <label>Enter Your Account Number: </label>
        <input type="number" placeholder="Enter your Account Number!" name="Ac" required><br><br>
        <button type="submit">View Transactions</button>
    </form>

    <h2>Your Transaction History</h2>
    <%
        String accountNumber = request.getParameter("Ac");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (accountNumber != null && !accountNumber.isEmpty()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

                String query = "SELECT * FROM transactions_ WHERE account_number = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, accountNumber);
                rs = ps.executeQuery();
    %>
    <table border="1">
        <tr>
            <th>Transaction ID</th>
            <th>Date</th>
            <th>Type</th>
            <th>Amount</th>
        </tr>
    <%
                if (!rs.isBeforeFirst()) {
                    out.println("<tr><td colspan='4'>No transactions found for this account.</td></tr>");
                } else {
                    while (rs.next()) {
    %>
        <tr>
            <td><%= rs.getInt("transaction_id") %></td>
            <td><%= rs.getTimestamp("transaction_date") %></td>
            <td><%= rs.getString("transaction_type") %></td>
            <td>₹<%= rs.getDouble("amount") %></td>
        </tr>
    <%
                    }
                }
    %>
    </table>
    <%
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<p>Error: " + e.getMessage() + "</p>");
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            out.println("<p>Please provide a valid account number.</p>");
        }
    %>
</body>
</html>