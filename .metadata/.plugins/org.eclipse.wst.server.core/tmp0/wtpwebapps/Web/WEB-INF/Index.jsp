<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="inc/style.css"
	<p> Bienvenue sur Youtunes ! <p>
 </head>
 <body>
 <div>
 <form method="get" action="Connexion">
 <fieldset>
 <legend>Connexion à Youtube (compte google)</legend>
 
 <label for="Login">Login <span
class="requis"></span></label>
 <input type="text" id="loginClient"
name="loginClient" value="" size="50" maxlength="50" />
 <br />
 
 <label for="adresseClient"> Mot de passe
<span class="requis"></span></label>
 <input type="password" id="passClient"
name="passClient" value="" size="20" maxlength="20" />
 <br />

 </fieldset>
 <input type="submit" value="Valider" />
<a href="https://accounts.google.com/SignUp?hl=fr" target="_blank" >Pas de compte google?  </a>
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