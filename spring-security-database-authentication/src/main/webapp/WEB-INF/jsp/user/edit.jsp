
<%@ include file="../common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit User</title>
</head>
<body>

	<spring:hasBindErrors name="userForm">
		<c:forEach var="error" items="${errors.allErrors}">
			<span style="color:red;"><b><spring:message message="${error}" /></b></span>
			<br />
		</c:forEach>
	</spring:hasBindErrors>

	<form:form modelAttribute="userForm" action="/user/new">
		<p>
			<label for="username">Username</label>
			<form:input path="username" />
		</p>
		<p>
			<label for="password">Password</label>
			<form:password path="password" />
		</p>
		<button type="submit" class="btn">Create User</button>
	</form:form>
</body>
</html>