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
import java.sql.SQLException;

public class CreateNewAccount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String mail = request.getParameter("mail");
        String aadhar = request.getParameter("aadhar");
        String password = request.getParameter("password");        
        String gender = request.getParameter("gender");
        String mpin = request.getParameter("mpin");
        String age = request.getParameter("age");
        long accountNumber = System.currentTimeMillis();
        double balance = 0.0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String Url= "jdbc:mysql://localhost:3306/bank";
            String Uname = "root";
            String Pass= "root";
            Connection con = DriverManager.getConnection(Url,Uname,Pass);
            String Q = "INSERT INTO account_table(name, phone, email, aadhar, password, gender, mpin, age, account_number, account_balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(Q);        
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, mail);
            ps.setString(4, aadhar);
            ps.setString(5, password);
            ps.setString(6, gender);
            ps.setString(7, mpin);
            ps.setString(8, age);
            ps.setLong(9, accountNumber);
            ps.setDouble(10, balance);    
            int i = ps.executeUpdate();
            if(i > 0) {
                out.println("<h3>Account Created Successfully!</h3>");
                out.println("Your Account Number is: " + accountNumber);
            }            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); 
            out.println("<h3>Account Creation Failed. Please Try Again Later...</h3>");            
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Account Creation Failed. Please Try Again Later...</h3>");
        }catch (Exception e) {
        	out.println(e);
        }
        
    }
}