<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="mainMenu.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 


	<table summary="System properties" class="dataTable">
		   <caption>System Properties</caption>
			<thead>
			  <tr>
				<th class="thclass">Name</th>
				<th class="thclass">Value</th>
				<th class="thclass">Description</th>
			  </tr>
			</thead>
			<tbody>
			<c:forEach items="${requestScope.systemProperties}" var="property">
				
				<tr>
				<td class="tdclass">${property.prop_key}</td>
				<td class="tdclass">${property.prop_value}</td>
				<td class="tdclass">${property.description}</td>
				</tr>	
			</c:forEach>
			</tbody>
			</table>
</div>
	</div>
</div>
</body>
</html>