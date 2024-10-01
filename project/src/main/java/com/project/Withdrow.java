package com.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Withdrow extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String accountNumber = request.getParameter("account_number");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
            String checkBalanceQuery = "SELECT account_balance FROM account_table WHERE account_number = ?";
            ps = con.prepareStatement(checkBalanceQuery);
            ps.setString(1, accountNumber);
            rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("account_balance");

                if (balance >= amount) {
                    String updateQuery = "UPDATE account_table SET account_balance = account_balance - ? WHERE account_number = ?";
                    ps = con.prepareStatement(updateQuery);
                    ps.setDouble(1, amount);
                    ps.setString(2, accountNumber);

                    int result = ps.executeUpdate();
                    if (result > 0) {
                        out.println("<p>Withdrawal successful! Amount: " + amount + "</p>");
                    } else {
                        out.println("<p>Withdrawal failed. Please try again.</p>");
                    }
                } else {
                    out.println("<p>Insufficient funds. Your current balance is: " + balance + "</p>");
                }
            } else {
                out.println("<p>Account not found. Please check your account number.</p>");
            }

        } catch (ClassNotFoundException e) {
            out.println("<p>Error loading database driver: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p>Database error: " + e.getMessage() + "</p>");
        } catch (NumberFormatException e) {
            out.println("<p>Invalid input. Please enter a valid number for the amount.</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                out.println("<p>Error closing database resources: " + e.getMessage() + "</p>");
            }
        }
    }
}