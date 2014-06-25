<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="inc/style.css"
	<p> Welcome on Youtunes ! <p>
 </head>
 <body>
 <div>
 <form method="get" action="Connexion">
 <fieldset>
 <legend>Connection to Youtube (Google account)</legend>
 
 <label for="Login">Login <span
class="requis"></span></label>
 <input type="text" id="loginClient"
name="loginClient" value="" size="50" maxlength="50" />
 <br />
 
 <label for="adresseClient"> Password
<span class="requis"></span></label>
 <input type="password" id="passClient"
name="passClient" value="" size="20" maxlength="20" />
 <br />

 </fieldset>
 <input type="submit" value="Ok" />
<a href="https://accounts.google.com/SignUp?hl=fr" target="_blank" >No Google account?  </a>
 </form>
 </div>
 
 <%  
	 String attribut = (String) request.getAttribute("message");
	if(attribut==null)
	{
		out.println(" ");
	}
	else 
	{
 	out.println( attribut );
	}
 %>
 </body>
</html>
