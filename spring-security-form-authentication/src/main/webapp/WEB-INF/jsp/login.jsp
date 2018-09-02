<%@ include file="common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>Spring Security Example</title>
</head>
<body>
	<c:url value="/login" var="loginUrl" />
	
	<form action="${loginUrl}" method="post">
		<c:if test="${param.error != null}">
			<p>Invalid username and password.</p>
		</c:if>
	
		<c:if test="${param.logout != null}">
			<p>You have been logged out.</p>
		</c:if>
		<p>
			<label for="username">Username</label> <input type="text" id="username" name="username" value="<c:out value="${param.username}"/>"/>
		</p>
		<p>
			<label for="password">Password</label> <input type="password" id="password" name="password" />
		</p>
		<sec:csrfInput />
		<button type="submit" class="btn">Log in</button>
	</form>
</body>
</html>