<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="mainMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="account" class="bank_beans.Account" scope="request"></jsp:useBean>

 <table summary="Account Details" class="dataTable">
		  <caption>Account Details</caption>
		        <thead>
		          <tr>
		            <th class="thclass">Client ID</th>
		            <th class="thclass">Account ID</th>
		            <th class="thclass">Current Balance($)</th>
		            <th class="thclass">Credit Limit($)</th>
		            <th class="thclass">Comment</th>
		          </tr>
		        </thead>
		        
		 		<tbody>
		          <tr>
		            <td class="tdclass">${account.client_id}</td> 
		            <td class="tdclass">${account.account_id}</td>
		            <td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${account.balance}"/></td>
		            <td class="tdclass">${account.credit_limit}</td>
		            <td class="tdclass">${account.comment}</td>
		          </tr>
		        </tbody>
			 </table>
		 <br/>
		 <br/>
		 
		 <p>*Commission for each withdraw or deposit is ${commission}$</p>	 
		 <input type="button" onclick="javascript:showHide(1)" value="Withdraw from Account"> 
		 <td><span class="error">${withdraw_error}</span></td>
	     <td><span class="info">${withdraw_info}</span></td>
		 <br/> 
		 
		 <form id="1" name="withdraw" action="Controller?command=withdraw" method="post" onsubmit="return validateWithdrawAmount();" style="display:none">
				<table>
			 	<tr>	
			 		<td>(*)Withdrawal amount: </td>
			 		<td><input type="text" id="withdraw_amount" name="withdraw_amount"></input></td>
			 	</tr>
			 	<tr>
			 		<td><input type="submit" value="Withdraw"></input></td>
			 	</tr>
			 </table>
		 	</form>
		  
		  <br/>
		  
		   <input type="button" onclick="javascript:showHide(2)" value="Deposit into Account"> 
		   <td><span class="error">${deposit_error}</span></td>
		   <td><span class="info">${deposit_info}</span></td> 
		   <br/> 
			 <form id="2" name="dposit" action="Controller?command=deposit" method="post" onsubmit="return validateDepositAmount();" style="display:none">
			 	<table>
				<tr>	
			 			<td>(*)Deposit amount: </td> 				
			 			<td><input type="text" id="deposit_amount" name="deposit_amount"></td>
	
			 	</tr>
			 	<tr>
			 		<td><input type="submit" value="Deposit"></input></td>
			 	</tr>
			 	</table>
			 </form>
		 <br/>
		 
		 	  </div>
    </div>
  </div>
</body>
</html>


 <script type="text/javascript"> 
  
 	function validateDepositAmount() {
 		var amount = document.forms["dposit"]["deposit_amount"].value;
 		if (/^[+]?([0-9]+(?:[\.][0-9]*)?|\.[0-9]+)$/.test(amount)) {
				return true;
 	 	} else {
 	 		alert("Please enter a positive amount to deposit.");	
	 		return false;
 	 	}
 	    
 	}

 	function validateWithdrawAmount() {
 		var amount = document.forms["withdraw"]["withdraw_amount"].value;
 		if (/^[+]?([0-9]+(?:[\.][0-9]*)?|\.[0-9]+)$/.test(amount)) {
				return true;
 	 	} else {
 	 		alert("Please enter a positive amount to withdraw.");	
	 		return false;
 	 	}
 	    
 	}

 </script>
