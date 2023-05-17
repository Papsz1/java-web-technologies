<%@
page import="edu.bbte.idde.sdim2087.backend.model.Catering"
%>
<%@
page import="java.util.Collection"
%>
<% Collection<Catering> kaja = (Collection<Catering>) request.getAttribute("food"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
    <title>Hi</title>
</head>
<body>
<h1>  Hello  </h1>
    <% for (Catering catering : food) {%>
        <h2>
            <%= catering.getName() %>
        <h2>
        <p>
            <%= catering.getPhoneNumber() %>
        </p>
    <% }%>
<form action="/web/logout" method="post">
        <button type="submit">Logout</button>
    </form>
</body>
</html>