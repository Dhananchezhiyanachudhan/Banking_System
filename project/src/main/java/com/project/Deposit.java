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

public class Deposit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String accountNumber = request.getParameter("account_number");
        String amountParam = request.getParameter("amount");

        if (accountNumber == null || accountNumber.isEmpty()) {
            out.println("<p>Invalid account number. Please try again.</p>");
            return;
        }

        long accountNumberLong;
        try {
            accountNumberLong = Long.parseLong(accountNumber);
        } catch (NumberFormatException e) {
            out.println("<p>Invalid account number. Please enter a valid number.</p>");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountParam);
        } catch (NumberFormatException e) {
            out.println("<p>Invalid amount. Please enter a valid number.</p>");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

            String query = "UPDATE account_table SET account_balance = account_balance + ? WHERE account_number = ?";
            ps = con.prepareStatement(query);
            ps.setDouble(1, amount);
            ps.setLong(2, accountNumberLong);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<p>Deposit successful! Amount: " + amount + "</p>");
            } else {
                out.println("<p>Deposit failed. Please try again.</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}