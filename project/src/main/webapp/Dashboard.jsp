<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Dashboard</title>
</head>
<body>
<%
HttpSession s = request.getSession(false);
if (s != null && s.getAttribute("username") != null) {
    String username = (String) s.getAttribute("username");
%>
    <h1>Welcome to your Dashboard, <%= username %>!</h1>
    <nav>
        <li><a href="BalanceEnquiry.jsp">Balance Enquiry</a></li>
        <li><a href="Deposit.jsp">Deposit</a></li>
        <li><a href="TransactionHistory.jsp">Transaction History</a></li>
        <li><a href="Withdrow.jsp">Withdrawal Amount</a></li>
        <li><a href="TransactionAmount.jsp">Transaction Amount</a></li>
        <li><a href="Backtohomepage.jsp">Logout</a></li>    
    </nav>
<%
} else {
    response.sendRedirect("Login.jsp");
}
%>
</body>
</html>