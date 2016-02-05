<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
<title>MBank</title>
</head>
<body> 
	<h1>Welcome to MBank Website!</h1>
	<h2>Please Login:</h2>
	
	<form action="Controller" method="post">
	
			Please enter your client ID:  		
			<input type="text" name="user_id"/><br>		
			
			Please enter your password:
			<input type="password" name="password"/>
			
			<input type="submit" name="command" id="command" value="login">	
			<br/>
			<p style="color: red;">${error}</p>		
		
	</form>
</body>
</html>