<%--
  Created by IntelliJ IDEA.
  User: snaric
  Date: 18-Aug-23
  Time: 4:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="shop.support.ipwebshopsupport.services.MessageService" scope="application"/>
  <%@ page import="shop.support.ipwebshopsupport.beans.MessageBean" %>
<%
  if(session.getAttribute("isLoggedIn") == null || !(Boolean)session.getAttribute("isLoggedIn")){
    response.sendRedirect("login.jsp");
  }
%>
<html>
<%
  if (request.getParameter("receiverId") != null) {
    int messageId = Integer.parseInt(request.getParameter("messageId"));
    Integer receiverId = Integer.parseInt(request.getParameter("receiverId"));
    messageService.setSeen(messageId);
    session.setAttribute("receiverId", receiverId);
    response.sendRedirect("sendMessage.jsp");
    return;
  }
%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="styles/messages.css" type="text/css" rel="stylesheet">
    <title>Poruke</title>
</head>
<body>
<div class="table-container">
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Tekst</th>
      <th>Pregledana</th>
      <th>#</th>
    </tr>
    </thead>
    <tbody>
    <% for (MessageBean message : messageService.getAll()) { %>
    <tr>
      <td><%=message.getId()%></td>
      <td><%=message.getText()%></td>
      <td><%=message.isSeen() ? "DA" : "NE"%></td>
      <td>
        <form action="messages.jsp" method="post">
          <input type="hidden" name="receiverId" value="<%=message.getUserId()%>"/>
          <input type="hidden" name="messageId" value="<%=message.getId()%>"/>
          <button type="submit" style="color: #f2f2f2;">Ogovori</button>
        </form>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
</body>
</html>
