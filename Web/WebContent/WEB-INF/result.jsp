<html>
<head>
<link type="text/css" rel="stylesheet" href="inc/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload the playlist</title>
</head>

<body>
	<div>
		 <%  
	
	 Boolean result = (Boolean) request.getAttribute("upResult");
	Boolean compute = (Boolean) request.getAttribute("okCompute");
	if(result!=null)
	{
		if(result)
		{
			%>
			<h1>You're playlist has been added</h1>
			<%
			String res = (String) request.getAttribute("message");
			String name = (String) request.getAttribute("name");
			out.println(res);%>
		
		<tr><td>
		<form method="get" action="Compute">
		<input type="submit" name="name" value="<%=name%>" ></td></tr>
		<%
		}
	}
	else 
	{
		%>
		<h1>Error</h1>
		<%
		String res = (String) request.getAttribute("message");
		out.println(res);
	}

	
	if(compute!=null)
	{
		if(compute)
		{
			out.println((String) request.getAttribute("compute"));
		}
	}
	
 %>
</body>
</html>