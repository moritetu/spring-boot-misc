<%@ include file="../common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Dashboard</title>
</head>
<body>
	<sec:authentication property="principal.username" var="username" />

	<form action="/logout" method="post">
		Hello,
		<c:out value="${username}" />
		<c:if test="${!empty message}">
			<p style="color:red;"><c:out value="${message}" /></p>
		</c:if>
	
		<sec:csrfInput />
		<button>Log Out</button>
	</form>

	<h3>Users</h3>
	<table border="1" style="width:300px;">
		<thead>
			<tr>
				<th>User</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="u" items="${userList}">
				<tr>
					<spring:url var="url" value="/user/del/{username}"><spring:param name="username" value="${u.username}"/></spring:url>
					<td width="50%"><c:out value="${u.username}"/></td>
					<td width="50%"><a href="${url}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<p><a href="/user/edit">New User</a> | <a href="/h2-console">H2 Console</a></p>

</body>
</html>