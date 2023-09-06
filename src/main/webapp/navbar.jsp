<%--
  Created by IntelliJ IDEA.
  User: snaric
  Date: 18-Aug-23
  Time: 12:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="styles/navbar.css" type="text/css" rel="stylesheet">
    <title>Poruke</title>
</head>
<div class="topnav">
    <% if(session.getAttribute("isLoggedIn") != null && (boolean)session.getAttribute("isLoggedIn")) { %>
    <a href="messages.jsp">Poruke</a>
    <% } %>

    <div class="topnav-right">
        <% if(session.getAttribute("isLoggedIn") == null || !(boolean)session.getAttribute("isLoggedIn")) { %>
        <a href="login.jsp">Prijavi se</a>
        <% } else { %>
        <a class="logout-button" href="logout.jsp">Odjavi se</a>
        <% } %>
    </div>
</div>
</html>

