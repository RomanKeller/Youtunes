<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Playlist added</title>
</head>
<body>
<t1>
<%
	String compute = (String) request.getAttribute("compute");
	Boolean ok = (Boolean) request.getAttribute("okCompute");
	if(ok)
	{
	String htmlLink = (String) request.getAttribute("hmtlLink"); 
 	htmlLink = htmlLink + ";autoplay=1";
 	String path =  (String) request.getAttribute("path");
 	out.println("The QR Code has been created and uploaded here :"+path);%>
 		<p>
 		</p>
	
 <iframe id="ytplayer" type="text/html" width="1000" height="1000"
 			  src=<%=htmlLink%>
 			  frameborder="0"/>
 
 
 <%
 	
	}
 %>
 
 </t1>
		
</body>
</html>