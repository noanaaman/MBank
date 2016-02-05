<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
			function showHide(id){
				if(document.getElementById(id).style.display == 'none'){
					document.getElementById(id).style.display='block';
					} else {
						document.getElementById(id).style.display = 'none';
					}
				}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="styles/styles.css" type="text/css" />
<title>MBank</title>
</head>
<body>
 <div id="header">
	<div id="logo"><h1><a href="http://localhost:8080/MBankWeb" title="Main Menu">MBank</a></h1></div>
 </div>

<div id="container">
	<div id="navcontainer">
	   	<ul class="nav">
	   	<h2>Welcome back, ${client_name}</h2>
		    <li><a href="Controller?command=client_main"><span>View Account</span></a></li>
		    <li><a href="Controller?command=activities"><span>View Activities</span></a></li>
		    <li><a href="Controller?command=view_deposits"><span>Deposits</span></a></li>
		    <li><a href="Controller?command=view_details"><span>Client Details</span></a></li>
		    <li><a href="Controller?command=view_system_properties"><span>MBank Properties</span></a></li>
		    <li><a href="Controller?command=logout"><span>Logout</span></a></li>
  		</ul>
	</div>  
  <div id="content">
  	<div id="maincontent">

<!--  </body>-->
