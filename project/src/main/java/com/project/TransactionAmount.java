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
import java.sql.Timestamp;

public class TransactionAmount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String fromAccount = request.getParameter("from_account");
        String toAccount = request.getParameter("to_account");
        double amount = Double.parseDouble(request.getParameter("amount"));

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
            con.setAutoCommit(false);  // Start transaction

            // Check balance in sender's account
            String checkBalanceQuery = "SELECT account_balance FROM account_table WHERE account_number = ?";
            ps = con.prepareStatement(checkBalanceQuery);
            ps.setString(1, fromAccount);
            rs = ps.executeQuery();

            if (rs.next()) {
                double senderBalance = rs.getDouble("account_balance");

                if (senderBalance >= amount) {
                    // Deduct amount from sender's account
                    String deductQuery = "UPDATE account_table SET account_balance = account_balance - ? WHERE account_number = ?";
                    ps = con.prepareStatement(deductQuery);
                    ps.setDouble(1, amount);
                    ps.setString(2, fromAccount);
                    ps.executeUpdate();

                    // Add amount to receiver's account
                    String addQuery = "UPDATE account_table SET account_balance = account_balance + ? WHERE account_number = ?";
                    ps = con.prepareStatement(addQuery);
                    ps.setDouble(1, amount);
                    ps.setString(2, toAccount);
                    ps.executeUpdate();

                    // Get the current timestamp
                    Timestamp transactionTimestamp = new Timestamp(System.currentTimeMillis());

                    // Log the transaction for sender (Debit)
                    String senderTransactionQuery = "INSERT INTO transactions_ (account_number, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";
                    ps = con.prepareStatement(senderTransactionQuery);
                    ps.setString(1, fromAccount);
                    ps.setString(2, "Debit");
                    ps.setDouble(3, amount);
                    ps.setTimestamp(4, transactionTimestamp);
                    ps.executeUpdate();

                    // Log the transaction for receiver (Credit)
                    String receiverTransactionQuery = "INSERT INTO transactions_ (account_number, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";
                    ps = con.prepareStatement(receiverTransactionQuery);
                    ps.setString(1, toAccount);
                    ps.setString(2, "Credit");
                    ps.setDouble(3, amount);
                    ps.setTimestamp(4, transactionTimestamp);
                    ps.executeUpdate();

                    con.commit();  // Commit transaction
                    out.println("<p>Amount of ₹" + amount + " transferred successfully from account "
                            + fromAccount + " to account " + toAccount + ".</p>");
                } else {
                    out.println("<p>Insufficient balance in your account.</p>");
                }
            } else {
                out.println("<p>Sender account does not exist.</p>");
            }
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();  // Rollback transaction in case of failure
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            out.println("<p>Transaction failed: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}