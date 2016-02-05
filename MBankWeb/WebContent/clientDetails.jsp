<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="mainMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="client" class="bank_beans.Client" scope="request"></jsp:useBean>
<table summary="Client Details" class="dataTable">
 	 <caption>My Details</caption>
        <thead>
          <tr>
            <th class="thclass">Client ID</th>
            <th class="thclass">Name</th>
            <th class="thclass">Client Type</th>
            <th class="thclass">Address</th>
            <th class="thclass">Email</th>
            <th class="thclass">Phone Number</th>
          </tr>
        </thead>
        
 		<tbody>
          <tr>
            <td class="tdclass">${client.client_id}</td>
            <td class="tdclass">${client.client_name}</td>
            <td class="tdclass">${client.type}</td>
            <td class="tdclass">${client.address}</td>
            <td class="tdclass">${client.email}</td>
            <td class="tdclass">${client.phone}</td>
          </tr>
        </tbody>
	</table>
	
	<br/>
	
	
	<input type="button" onclick="javascript:showHide(3)" value="Edit Your Details"> 
	<span class="error">${update_details_error}</span>
	<span class="info">${update_details_success}</span>
	<br/>
	<form name="editDetails" id="3" action="Controller?command=update_details"  onsubmit="return validateNewDetails();" style="display:none" method="post">
 	<table>
	 	<tr>	
	 		<td>Address</td>
	 		<td><input type="text" id="new_address" name="new_address"></input></td>
	 	</tr>
	 	<tr>	
	 		<td>Email</td>
	 		<td><input type="text" id="new_email" name="new_email"></input></td>
	 	</tr>
	 	<tr>	
	 		<td>Phone Number</td>
	 		<td><input type="text" id="new_phone" name="new_phone"></input></td>
	 	</tr>
	 	<tr>
	 		<td><input type="submit" value="Update"></input></td>
	 	</tr>
    </table>
    </br>
   </form>
   </div>
  </div>
 </div>
 
 <script type="text/javascript"> 
 	function validateNewDetails() {
 		if (false == checkIfAllEmpty()) {
 			alert("Please enter at least one detail to update.");
 	        return false;
 	 	} 
 		else 
 	 	if (false == validateEmail()) {
 	 		alert("This is not a valid e-mail address.");
 	 		return false;
 	 	}
 	 	else 
 	 	if (false == validatePhone()) {
 	 		alert("This is not a valid phone number.");
 	 		return false;
 	 	}
 	 	else {
			return true;
 	 	}
 	    
 	} 

 	function checkIfAllEmpty() {
 		var address = document.forms["editDetails"]["new_address"].value;
 		var email = document.forms["editDetails"]["new_email"].value;
 		var phone = document.forms["editDetails"]["new_phone"].value;
 		if ((address == null || address == "") && (email == null || email == "") && (phone == null || phone == "")) {
 	        return false;
 	 	}
 	}	

 	function validateEmail() {
 		var email = document.forms["editDetails"]["new_email"].value;
		if (email == null || email == "") {
			return true;
		}
		else 
			if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {  
 		    return (true)  ;
 		}  
 		else {
 			 return (false);
 	 	}
 		     
 	}  

 	function validatePhone() {
 		var phone = document.forms["editDetails"]["new_phone"].value;
 		var stripped = phone.replace(/[\(\)\.\-\ ]/g, '');
 		if (phone == null || phone == "") {
			return true;
 		}
 		else 
 		if (isNaN(parseInt(stripped))) {
				return false;
 	 	} else 
 	 	if (stripped.length > 10 || stripped.length < 10) {
 	 			return false;
 	 	}
 	    else {
			return true;
 	 	 }
 		
 	 }
 </script>
</body>
</html>