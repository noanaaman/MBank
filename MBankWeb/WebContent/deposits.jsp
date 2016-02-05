<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="mainMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

  <table summary="Deposits" class="dataTable">
  <caption>Deposits</caption>
	<thead>
	  <tr>
		<th class="thclass">Deposit ID</th>
		<th class="thclass">Current Balance</th>
		<th class="thclass">Type</th>
		<th class="thclass">Estimated Balance</th>
		<th class="thclass">Opening Date</th>
		<th class="thclass">Closing Date</th>
		<th class="thclass">Pre-Open Commission</th>
	  </tr>
	</thead>
	<tbody>
	<c:forEach items="${requestScope.clientDeposits}" var="deposit">
		<tr>
		<td class="tdclass">${deposit.deposit_id}</td>
		<td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${deposit.balance}"/></td>
		<td class="tdclass">${deposit.type.toString()}</td>
		<td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${deposit.estimated_balance}"/></td>
		<td class="tdclass"><fmt:formatDate value="${deposit.opening_date.time}"/></td>
		<td class="tdclass"><fmt:formatDate value="${deposit.closing_date.time}"/></td>
		<td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${deposit.estimated_balance * commission}"/></td>
		</tr>	
	</c:forEach>
	</tbody>
	</table>
	<br/>
	
	<input type="button" onclick="javascript:showHide(4)" value="Create New Deposit">
	<span class="error">${new_deposit_error}</span>
	<span class="info">${new_deposit_success}</span> 
	<br/>
	
	<form id="4" name="new_deposit" action="Controller?command=new_deposit" onsubmit="return validateNewDeposit();" style="display:none" method="post">	
	 <table>
		 	<tr>	
		 		<td>Deposit amount: </td>
		 		<td><input type="text" id="new_deposit_amount" name="new_deposit_amount"></td>
		 	</tr>
		 	<tr>	
		 		<td>Deposit Type: </td>
		 		<td>
		 			<input type="radio" name="new_deposit_type" value="long_deposit" checked>Long Deposit
		 			<input type="radio" name="new_deposit_type" value="short_deposit" checked>Short Deposit
		 		</td>
		 	</tr>
		 	<tr>
		 		<td><input type="submit" value="Create deposit"></td>
		 	</tr>
	 </table>
	 <br/>

	</form>
	 <br/>
	 
	<input type="button" onclick="javascript:showHide(5)" value="Pre-open Long-Term Deposit">
	<span class="error">${preopen_error}</span>
	<span class="info">${preopen_success}</span>  
	 <br/>
	 <form id="5" name="preopen" action="Controller?command=preopen_deposit" onsubmit="return validateDepositId();" style="display:none" method="post">
		<table>
		 	<tr>	
		 		<td>Deposit ID: </td>
		 		<td><input type="text" id="deposit_id" name="deposit_id"></td>
		 	</tr>
		 	<tr></tr>
		 	<tr>
		 		<td><input type="submit" value="Pre-open deposit"></td>
		 	</tr>
		 </table>
		 <br/>
	 </form>
	</div>
   </div>
  </div>	 
</body>
</html>

 <script type="text/javascript"> 
 	function validateNewDeposit() {
 		if (false == checkEmptyNewDeposit()) {
 			alert("Please enter an amount to deposit and a deposit type.");
 	        return false;
 	 	} 
 		else 
 	 	if (false == validateType()) {
 	 		alert("Please choose either a short or long term deposit.");
 	 		return false;
 	 	}
 	 	else 
 	 	if (false == validateAmount()) {
 	 		alert("This is not a valid amount.");
 	 		return false;
 	 	}
 	 	else {
			return true;
 	 	}
 	    
 	} 

 	function checkEmptyNewDeposit() {
 		var amount = document.forms["new_deposit"]["new_deposit_amount"].value;
 		var type = document.forms["new_deposit"]["new_deposit_type"].value;
 		if ((amount == null || amount == "") || (type == null || type == "")) {
 	        return false;
 	 	}
 		else {return true;}
 	}	
  
 	function validateAmount() {
 		var amount = document.forms["new_deposit"]["new_deposit_amount"].value;
 		if (/^[+]?([0-9]+(?:[\.][0-9]*)?|\.[0-9]+)$/.test(amount)) {
				return true;
 	 	} else {
 	 			return false;
 	 	}
 	    
 	}

 	function validateType() {
		var type = document.forms["new_deposit"]["new_deposit_type"].value;
		if (type == "long_deposit" || type == "short_deposit") {
			return true;
		} else {return false;}
 	}

 	function validateDepositId() {
		var depositId = document.forms["preopen"]["deposit_id"].value;
		if (/^(?=\d*[1-9])\d+$/.test(depositId)) {
			return true;
		} else {
			alert("Please enter an existing deposit ID.");
			return false;
		}
 	 }

	
 </script>
	 