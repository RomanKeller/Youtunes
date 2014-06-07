<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="Applic.*,Utils.*" %>
<html>
 <head>
 <meta charset="utf-8" />
 <title>Youtunes</title>
 </head>
 <body>
 <p>Ceci est une page générée depuis une JSP.</p>
 <p>
 <%  String attribut = (String) request.getAttribute("test");
 out.println( attribut );
 %>
 </p>
 </p>
 </body>
</html>
