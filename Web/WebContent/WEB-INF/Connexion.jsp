<html>
<head>
<link type="text/css" rel="stylesheet" href="inc/style.css"
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload the playlist</title>
</head>

<body>
	<div>
		<h3>Choose Playlist(.XML) to Upload in Server</h3>

		<form method="post" action="UploadServlet"
			enctype="multipart/form-data">
			Select playlist :  <input type="file" name="dataFile"
				id="fileChooser" /><br />
			<br /> <input type="submit" value="Upload" />
			
			<br />
		</form>
</body>
</html>