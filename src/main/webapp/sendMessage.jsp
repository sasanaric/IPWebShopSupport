<%--
  Created by IntelliJ IDEA.
  User: snaric
  Date: 19-Aug-23
  Time: 12:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="supportService" class="shop.support.ipwebshopsupport.services.SupportService" scope="application"></jsp:useBean>
<jsp:useBean id="messageService" class="shop.support.ipwebshopsupport.services.MessageService" scope="application"></jsp:useBean>
<%
    if(session.getAttribute("isLoggedIn") == null || !(Boolean)session.getAttribute("isLoggedIn")){
        response.sendRedirect("login.jsp");
    }
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="styles/form.css" type="text/css" rel="stylesheet">
    <title>Send Message</title>
</head>
<body>
<%
    if (request.getParameter("submit") != null) {
        String messageText = request.getParameter("message");
        if (messageText != null && !messageText.trim().isEmpty()) {
            if (session.getAttribute("receiverId") != null) {
                String receiverEmail = supportService.getUserEmailById((Integer) session.getAttribute("receiverId"));
                if (messageService.sendEmail(receiverEmail, messageText)) {
                    session.removeAttribute("receiverId");
                    response.sendRedirect("messages.jsp");
                } else {
                    session.setAttribute("notification", "Došlo je do greške! Poruka nije poslata.INNER");
                }
            } else {
                session.setAttribute("notification", "Došlo je do greške! Poruka nije poslata.OUT");
            }
        } else {
            session.setAttribute("notification", "Poruka ne može biti prazna.");
        }
    } else {
        session.setAttribute("notification", "");
    }
%>

<div class="container">
    <div class="login-frame">
        <div class="login-form">
            <form method="POST" action="sendMessage.jsp"> <!-- renamed the action URL -->
                <div class="form-row">
                    Poruka<br />
                    <label for="message">
                        <textarea name="message" id="message" rows="10" style="resize: none;width: 100%;padding: 10px"></textarea><br />
                    </label>
                </div>
                <button type="submit" value="Pošalji" name="submit">Pošalji</button>
                <h3><%=session.getAttribute("notification").toString() %></h3>
            </form>
        </div>
    </div>
</div>
</body>
</html>
