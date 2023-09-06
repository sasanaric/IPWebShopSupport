<%--
  Created by IntelliJ IDEA.
  User: snaric
  Date: 18-Aug-23
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="navbar.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="newSupportUser" class="shop.support.ipwebshopsupport.beans.SupportBean" scope="request"></jsp:useBean>
<jsp:useBean id="supportService" class="shop.support.ipwebshopsupport.services.SupportService" scope="application"></jsp:useBean>
<jsp:setProperty property="username" name="newSupportUser" param="username" />
<jsp:setProperty property="password" name="newSupportUser" param="password" />
<jsp:setProperty property="lastName" name="newSupportUser" param="lastName" />
<jsp:setProperty property="firstName" name="newSupportUser" param="firstName" />

<!DOCTYPE html>
<%
    if(request.getParameter("submit") != null) {
        if("".equals(newSupportUser.getUsername().trim()) || "".equals(newSupportUser.getPassword().trim())
                || "".equals(newSupportUser.getLastName()) || "".equals(newSupportUser.getFirstName())) {
            session.setAttribute("notification", "Morate popuniti sva polja.");
        } else {
            if(supportService.registerSupportUser(newSupportUser)) {
                session.setAttribute("notification", "");
                session.setAttribute("isLoggedIn",true);
                response.sendRedirect("messages.jsp");
            } else {
                session.setAttribute("notification","Uneseno korisničko ime je zauzeto.");
            }
        }
    } else {
        session.setAttribute("notification","");
    }
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="styles/form.css" type="text/css" rel="stylesheet">
    <title>Kreiranje novog naloga za podršku</title>
</head>
<body>
<div class="container">
    <div class="login-frame">
        <div class="login-form">
            <form method="POST" action="register.jsp">
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
                <div class="form-row">
                    Prezime <br />
                    <label for="lastName">
                        <input type="text" name="lastName" id="lastName" /><br />
                    </label>
                </div>
                <div class="form-row">
                    Ime <br />
                    <label for="firstName">
                        <input type="text" name="firstName" id="firstName" /><br />
                    </label>
                </div>

                <button type="submit" value="Registruj nalog" name="submit">Registruj se</button>
                <h3><%=session.getAttribute("notification").toString() %></h3> <br />
            </form>
        </div>
    </div>
</div>
</body>
</html>