<%--
  Created by IntelliJ IDEA.
  User: snaric
  Date: 17-Aug-23
  Time: 6:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="shop.support.ipwebshopsupport.beans.SupportBean"%>
<jsp:useBean id="supportBean" class="shop.support.ipwebshopsupport.beans.SupportBean" scope="session"></jsp:useBean>
<jsp:useBean id="supportService" class="shop.support.ipwebshopsupport.services.SupportService" scope="application"></jsp:useBean>
<jsp:setProperty property="username" name="supportBean" param="username" />
<jsp:setProperty property="password" name="supportBean" param="password" />

<!DOCTYPE html>
<%
  if (request.getParameter("submit") != null) {
    SupportBean user = supportService.getSupportUser(supportBean.getUsername(), supportBean.getPassword());
    if (user != null) {
      supportBean.setFirstName(user.getFirstName());
      supportBean.setLastName(user.getLastName());
      supportBean.setLoggedIn(true);
      session.setAttribute("notification", "");
      session.setAttribute("isLoggedIn",true);
      response.sendRedirect("messages.jsp");
    } else {
      session.setAttribute("notification", "Unijeli ste neispravno korisničko ime ili lozinku.");
      supportBean.setLoggedIn(false);
    }
  } else {
    session.setAttribute("notification", "");
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="styles/form.css" type="text/css" rel="stylesheet">
  <title>Your Page Title</title>
</head>
<body>
<div class="container">
  <div class="login-frame">
    <div class="login-form">
      <form method="POST" action="login.jsp">
        <div class="form-row">
          Korisničko ime<br />
          <label for="username">
            <input type="text" name="username" id="username" /><br />
          </label>
        </div>
        <div class="form-row">
          Lozinka <br />
          <label for="password">
            <input type="password" name="password" id="password" /><br />
          </label>
        </div>
        <button type="submit" value="Prijavi se" name="submit">Prijavi se</button>
        <h3><%=session.getAttribute("notification").toString() %></h3> <br />
        <div class="register-link">
          <p>Nemate nalog? <a href="register.jsp">Registruj se</a></p>
        </div>
      </form>
    </div>
    </div>
</div>
</body>
</html>