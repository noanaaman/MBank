<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="mainMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table summary="Client Activities" class="dataTable">
		<caption>My Activities</caption>
			<thead>
			  <tr>
				<th class="thclass">Activity ID</th>
				<th class="thclass">Amount</th>
				<th class="thclass">Commission($)</th>
				<th class="thclass">Description</th>
				<th class="thclass">Date</th>
			  </tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.clientActivities}" var="activity">
					<tr class="light">
					<td class="tdclass">${activity.id}</td>
					<td class="tdclass"><fmt:formatNumber type="number" maxFractionDigits="2" value="${activity.amount}"/></td>
					<td class="tdclass">${activity.commission}</td>
					<td class="tdclass">${activity.description}</td>
					<td class="tdclass"><fmt:formatDate value="${activity.activity_date.time}"/></td>
					
					</tr>	
				</c:forEach>
			</tbody>
		  </table>
		</div>
	  </div>
	</div>
  </body>
</html>
